package com.bhavesh.route_orchestrator.route.controller;


import com.bhavesh.commonutils.commonutils.dto.RouteRequestDTO;
import com.bhavesh.route_orchestrator.route.entity.CachedPathDoc;
import com.bhavesh.route_orchestrator.route.repository.CachedPathRepository;
import com.bhavesh.route_orchestrator.route.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/route")
public class RouteOrchestratorController {

    @Autowired
    private RouteService routeService;


    @PostMapping("/plan")
    public ResponseEntity<?> planRoute(@RequestBody RouteRequestDTO request) {

      return ResponseEntity.ok(routeService.handleRouteRequest(request));

    }
}
