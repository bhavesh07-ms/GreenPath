package com.bhavesh.postgis_data_service.postgis_data_service.controller;

import com.bhavesh.commonutils.commonutils.dto.RouteRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/graph")
public class GraphController {

    @PostMapping("/fetch")
    public ResponseEntity<Object> fetchGraph(@RequestBody RouteRequestDTO request) {
        // For now, simulate graph from PostGIS or hardcoded

        return ResponseEntity.ok(null);
    }
}
