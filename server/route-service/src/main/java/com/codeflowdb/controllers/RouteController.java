package com.codeflowdb.controllers;

import com.codeflowdb.dto.LocationRequestDTO;
import com.codeflowdb.dto.LocationResponseDTO;
import com.codeflowdb.model.Location;
import com.codeflowdb.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping("/save")
    public ResponseEntity<LocationResponseDTO> saveRoute(@RequestBody LocationRequestDTO dto) {
        LocationResponseDTO saved = routeService.saveLocation(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/all")
    public ResponseEntity<List<LocationResponseDTO>> getAllRoutes() {
        return ResponseEntity.ok(routeService.getAllLocations());
    }

    @GetMapping("/from/{start}/to/{end}")
    public ResponseEntity<List<LocationResponseDTO>> getRouteBetween(@PathVariable String start,
                                                                     @PathVariable String end) {
        return ResponseEntity.ok(routeService.getRouteBetween(start, end));
    }
}
