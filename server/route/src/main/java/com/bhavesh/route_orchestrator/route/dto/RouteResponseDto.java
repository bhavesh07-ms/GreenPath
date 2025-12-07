package com.bhavesh.route_orchestrator.route.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
public class RouteResponseDto {
    private boolean fromCache;
    private Object payload; // could be path list or object
    private Map<String, Object> meta;

    public RouteResponseDto() {}



    // getters & setters
}
