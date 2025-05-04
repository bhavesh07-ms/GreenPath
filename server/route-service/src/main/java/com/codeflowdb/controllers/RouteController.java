package com.codeflowdb.controllers;

import com.codeflowdb.dto.EmissionResponseDTO;
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

    @GetMapping("/{id}")
    public ResponseEntity<LocationResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(routeService.getLocationById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationResponseDTO> update(@PathVariable Long id, @RequestBody LocationRequestDTO dto) {
        return ResponseEntity.ok(routeService.updateLocation(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        routeService.deleteLocation(id);
        return ResponseEntity.ok("Location deleted");
    }


    @GetMapping("/search")
    public ResponseEntity<List<LocationResponseDTO>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(routeService.searchByName(name));
    }

    @GetMapping("/optimal-path")
    public ResponseEntity<List<LocationResponseDTO>> getBestPath(@RequestParam String source, @RequestParam String destination) {
        List<LocationResponseDTO> path = routeService.getBestRoute(source, destination);
        return ResponseEntity.ok(path);
    }

    // Endpoint for best route (without emission)
    @GetMapping("/best")
    public List<LocationResponseDTO> getBestRoute(@RequestParam String source,
                                                  @RequestParam String destination) {
        return routeService.getBestRoute(source, destination);
    }

    // Endpoint for best route with emission
    @GetMapping("/best-with-emission")
    public EmissionResponseDTO getBestRouteWithEmission(@RequestParam String source,
                                                        @RequestParam String destination,
                                                        @RequestParam String mode) {
        return routeService.getBestRouteWithEmission(source, destination, mode);
    }
}
