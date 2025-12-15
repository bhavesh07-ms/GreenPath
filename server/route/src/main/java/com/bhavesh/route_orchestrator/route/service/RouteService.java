package com.bhavesh.route_orchestrator.route.service;

import com.bhavesh.commonutils.commonutils.dto.PathPlannerInputDTO;
import com.bhavesh.commonutils.commonutils.dto.RedisStoreRequestDTO;
import com.bhavesh.commonutils.commonutils.dto.RouteRequestDTO;
import com.bhavesh.route_orchestrator.route.dto.RouteResponseDto;
import com.bhavesh.route_orchestrator.route.entity.CachedPathDoc;
import com.bhavesh.route_orchestrator.route.repository.CachedPathRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.Duration;
import java.util.*;

@Service
public class RouteService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final RedisLockService redisLockService;
    private final CachedPathRepository cachedPathRepo;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${path.planner.service-url}")
    private String pathPlannerUrl;

    // Configurable params
    private static final long REDIS_ROUTE_TTL_SEC = 20 * 60; // 20 minutes
    private static final long LOCK_TTL_SEC = 10; // 10 seconds
    private static final long POLL_TIMEOUT_MS = 5000; // wait up to 5s if lock held
    private static final long POLL_INTERVAL_MS = 200;
    private static final long REQCOUNT_EXPIRE_SEC = 24 * 3600; // 1 day
    private static final long SAVE_TO_MONGO_THRESHOLD = 3L;

    public RouteService(RedisLockService redisLockService,
                        CachedPathRepository cachedPathRepo,
                        RedisTemplate<String, Object> redisTemplate) {
        this.redisLockService = redisLockService;
        this.cachedPathRepo = cachedPathRepo;
        this.redisTemplate = redisTemplate;
    }

    public static String generateCacheKey(String source, String destination, String mode,
                                          Map<String, Double> weights) {

        String rawKey = source + "|" + destination + "|" + mode + "|" +
                weights.get("alpha") + "|" +
                weights.get("beta") + "|" +
                weights.get("gamma") + "|" +
                weights.get("delta");

        return DigestUtils.sha256Hex(rawKey); // Apache commons-codec
    }

    public RouteResponseDto handleRouteRequest(RouteRequestDTO request) {

        String routeKey = CacheKeyUtil.routeCacheKey(request);
        String lockKey = CacheKeyUtil.routeLockKey(request);
        String reqCountKey = CacheKeyUtil.routeReqCountKey(request);

        // ---- Step 2: Check Redis
        Object cached = redisLockService.get(routeKey);
        if (cached != null) {
            Map<String, Object> meta = Map.of("source", "redis");
            return new RouteResponseDto(true, cached, meta);
        }

        // ---- Step 3: Check Mongo (mid-tier)
        // to keep lookup simple we'll compute weightsKey string and search by fields
        Map<String, Double> weights = Map.of(
                "alpha", request.getAlpha(),
                "beta", request.getBeta(),
                "gamma", request.getGamma(),
                "delta", request.getDelta()
        );
        String hash = generateCacheKey(
                request.getSource(),
                request.getDestination(),
                request.getMode(),
                weights
        );
       //  try repo lookup by constructing an example or custom query (simplified approach)
        Optional<CachedPathDoc> maybe = cachedPathRepo.findByCacheKey(hash);

        if (maybe.isPresent()) {
            CachedPathDoc doc = maybe.get();
            // push into redis for fast subsequent hits
            redisLockService.putWithTTL(routeKey, doc.getPath(), REDIS_ROUTE_TTL_SEC);
            Map<String, Object> meta = Map.of("source", "mongo");
            return new RouteResponseDto(true, doc.getPath(), meta);
        }

        // ---- Step 4: Lock in Redis to avoid duplicated compute
        String token = redisLockService.tryLock(lockKey, Duration.ofSeconds(LOCK_TTL_SEC));
        if (token == null) {
            // someone else computing — poll for short time
            Object polled = redisLockService.pollForKey(routeKey, POLL_TIMEOUT_MS, POLL_INTERVAL_MS);
            if (polled != null) {
                Map<String, Object> meta = Map.of("source", "redis_after_wait");
                return new RouteResponseDto(true, polled, meta);
            } else {
                // still not available — return processing (202) or instruct caller to retry later
                Map<String, Object> meta = Map.of("status", "processing", "waitMs", POLL_TIMEOUT_MS);
                return new RouteResponseDto(false, Map.of("message", "Route is being computed. Please retry shortly."), meta);
            }
        }

        // ---- We are leader (we own the lock). Compute route now.
        try {
            Object optimalPath = restTemplate.postForObject(pathPlannerUrl, request, Object.class);

            // store in redis
            redisLockService.putWithTTL(routeKey, optimalPath, REDIS_ROUTE_TTL_SEC);

            // increment request counter and maybe persist to Mongo
            long cnt = redisLockService.incrWithExpireIfNeeded(reqCountKey, REQCOUNT_EXPIRE_SEC);

            if (cnt >= SAVE_TO_MONGO_THRESHOLD) {
                // persist in Mongo as materialized cached path
                CachedPathDoc doc = new CachedPathDoc(
                        request.getSource(),
                        request.getDestination(),
                        request.getMode(),
                        weights,
                        optimalPath, // cast may be required if path is List
                        computeCostFromPath(optimalPath), // implement cost calculation if needed
                        cnt,
                        Instant.now(),
                        hash
                );
                // upsert logic: simple save (could be find+update)
                cachedPathRepo.save(doc);
            }

            Map<String, Object> meta = Map.of("source", "computed", "reqCount", cnt);
            return new RouteResponseDto(false, optimalPath, meta);
        } finally {
            // always release lock (best effort)
            redisLockService.releaseLock(lockKey, token);
        }
    }

    // stub: compute numeric cost from returned path (optional); for now return 0.0
    private double computeCostFromPath(Object optimalPath) {
        return 0.0;
    }
}
