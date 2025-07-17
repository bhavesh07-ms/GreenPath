package com.bhavesh.route_orchestrator.route.service;

import com.bhavesh.commonutils.commonutils.dto.PathPlannerInputDTO;
import com.bhavesh.commonutils.commonutils.dto.RedisStoreRequestDTO;
import com.bhavesh.commonutils.commonutils.dto.RouteRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class RouteService {

    @Autowired
    private RestTemplate restTemplate;

    public Map<String, Object> handleRouteRequest(RouteRequestDTO request) {
        Map<String, Object> result = new HashMap<>();

        // 1. Check Redis for existing session
        String redisCheckUrl = "http://redis-session-service/api/session/check";
        Boolean sessionExists = restTemplate.postForObject(redisCheckUrl, request, Boolean.class);

        if (Boolean.TRUE.equals(sessionExists)) {
            String redisGetUrl = "http://redis-session-service/api/session/get";
            Object savedPath = restTemplate.postForObject(redisGetUrl, request, Object.class);
            result.put("sessionExists", true);
            result.put("optimalPath", savedPath);
            return result;
        }

        // 2. Get graph segment from PostGIS
        String graphUrl = "http://postgis-data-service/api/graph/fetch";
        Object graph = restTemplate.postForObject(graphUrl, request, Object.class);

        // 3. Prepare input and call Path Planner
        String pathPlannerUrl = "http://path-planner-service/api/plan/compute";
        PathPlannerInputDTO plannerInput = new PathPlannerInputDTO(request, graph);
        Object optimalPath = restTemplate.postForObject(pathPlannerUrl, plannerInput, Object.class);

        // 4. Store in Redis for future resume
        String redisStoreUrl = "http://redis-session-service/api/session/store";
        RedisStoreRequestDTO storeDTO = new RedisStoreRequestDTO(request.getSessionId(), optimalPath);
        restTemplate.postForObject(redisStoreUrl, storeDTO, Void.class);

        result.put("sessionExists", false);
        result.put("graphSegment", graph);
        result.put("optimalPath", optimalPath);
        return result;
    }
}
