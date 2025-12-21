package com.bhavesh.route_orchestrator.route.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;


import java.util.List;
import java.util.Map;

@Document("cached_paths")
@Getter
@Setter
@NoArgsConstructor
public class CachedPathDoc {

    @Id
    private String id;
    private String source;
    private String destination;
    private String mode;
    private Map<String, Double> weights; // alpha,beta,gamma,delta
    private Object path;
    private double cost;
    private long requestCount;
    private Instant lastRequestedAt;
    private Instant createdAt;
    private String cacheKey;
    private String status;


    public CachedPathDoc(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public CachedPathDoc(String source, String destination, String mode, Map<String, Double> weights,
                         Object  path, double cost, long requestCount, Instant lastRequestedAt, String cacheKey) {
        this.source = source;
        this.destination = destination;
        this.mode = mode;
        this.weights = weights;
        this.path = path;
        this.cost = cost;
        this.requestCount = requestCount;
        this.lastRequestedAt = lastRequestedAt;
        this.cacheKey = cacheKey;
        this.createdAt = Instant.now();
    }

    // getters and setters omitted for brevity — add as needed
    // ... (generate via IDE)
}
