package com.bhavesh.path_planner.path_planner_service.records;

public record Node(
        String id,
        double lat,
        double lon,
        double elevation // meters
) {}
