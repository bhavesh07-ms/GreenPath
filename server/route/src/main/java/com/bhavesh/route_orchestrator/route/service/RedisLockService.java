package com.bhavesh.route_orchestrator.route.service;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class RedisLockService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ValueOperations<String, Object> vals;

    public RedisLockService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.vals = redisTemplate.opsForValue();
    }

    /**
     * Try to acquire lock. Returns lock token if acquired, else null.
     * Uses setIfAbsent with TTL.
     */
    public String tryLock(String lockKey, Duration ttl) {
        String token = UUID.randomUUID().toString();
        Boolean ok = vals.setIfAbsent(lockKey, token, ttl);
        if (Boolean.TRUE.equals(ok)) return token;
        return null;
    }

    /**
     * Release lock only if token matches (safe unlock).
     * Implemented atomically using Lua script would be ideal; here we do compare-get-delete.
     */
    public boolean releaseLock(String lockKey, String token) {
        try {
            Object cur = vals.get(lockKey);
            if (cur == null) return false;
            if (Objects.equals(token, cur.toString())) {
                redisTemplate.delete(lockKey);
                return true;
            }
            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Poll for a key to appear in Redis for a short time.
     * Returns value if found within timeout, else null.
     */
    public Object pollForKey(String key, long timeoutMillis, long pollIntervalMillis) {
        long deadline = System.currentTimeMillis() + timeoutMillis;
        while (System.currentTimeMillis() < deadline) {
            Object val = vals.get(key);
            if (val != null) return val;
            try { TimeUnit.MILLISECONDS.sleep(pollIntervalMillis); } catch (InterruptedException ignored) {}
        }
        return null;
    }

    /** Increment request counter atomically, set TTL in seconds if newly created. Returns new value. */
    public long incrWithExpireIfNeeded(String counterKey, long expireSeconds) {
        Long v = (Long) vals.increment(counterKey, 1L);
        if (v != null && v == 1L) {
            redisTemplate.expire(counterKey, Duration.ofSeconds(expireSeconds));
        }
        return v == null ? 0L : v;
    }

    public void putWithTTL(String key, Object value, long ttlSeconds) {
        vals.set(key, value, Duration.ofSeconds(ttlSeconds));
    }

    public Object get(String key) {
        return vals.get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}

