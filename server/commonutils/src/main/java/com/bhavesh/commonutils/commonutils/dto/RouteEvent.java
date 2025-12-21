package com.bhavesh.commonutils.commonutils.dto;

import lombok.Builder;

import java.util.List;

@Builder
public class RouteEvent {
    private String eventType; // ROUTE_ACTIVATED / ROUTE_DEACTIVATED
    private String routeId;
    private String userId;
    private List<String> path;
    private int baselineTime;
    private int threshold;
}