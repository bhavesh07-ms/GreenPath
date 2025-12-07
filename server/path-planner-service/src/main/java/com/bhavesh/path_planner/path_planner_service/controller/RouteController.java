package com.bhavesh.path_planner.path_planner_service.controller;


import com.bhavesh.commonutils.commonutils.dto.ResponseDTO;
import com.bhavesh.commonutils.commonutils.dto.RouteRequestDTO;
import com.bhavesh.path_planner.path_planner_service.service.PathPlannerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
@RestController
@RequestMapping("path-planner-service")
public class RouteController {

    private final PathPlannerService pathPlannerService;

    public RouteController(PathPlannerService pathPlannerService) {
        this.pathPlannerService = pathPlannerService;
    }

    @PostMapping("/plan/compute")
    public ResponseEntity<Object> computeRoute(@RequestBody RouteRequestDTO request) {
        try {
            PathPlannerService.PathResult result = pathPlannerService.compute(request);
            return ResponseEntity.ok(ResponseDTO.ok(result));
        }
        catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body(ResponseDTO.fail("Error computing route: " + e.getMessage()));
        }
    }
}
