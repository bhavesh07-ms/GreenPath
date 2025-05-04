package com.codeflowdb.service.impl;

import com.codeflowdb.dto.EmissionResponseDTO;
import com.codeflowdb.dto.LocationRequestDTO;
import com.codeflowdb.dto.LocationResponseDTO;
import com.codeflowdb.model.Location;
import com.codeflowdb.repository.LocationRepo;
import com.codeflowdb.route.DjkstraAlgorithm;
import com.codeflowdb.route.DjkstraAlgorithm.RouteEdge;
import com.codeflowdb.service.RouteService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class RouteServiceImpl implements RouteService {

    private final LocationRepo locationRepository;
    private final ModelMapper modelMapper;

    public RouteServiceImpl(LocationRepo locationRepository, ModelMapper modelMapper) {
        this.locationRepository = locationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public LocationResponseDTO saveLocation(LocationRequestDTO dto) {
        Location location = modelMapper.map(dto, Location.class);
        Location saved = locationRepository.save(location);
        return modelMapper.map(saved, LocationResponseDTO.class);
    }

    @Override
    public LocationResponseDTO getLocationById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Location not found"));
        return modelMapper.map(location, LocationResponseDTO.class);
    }

    @Override
    public List<LocationResponseDTO> getAllLocations() {
        return locationRepository.findAll().stream()
                .map(loc -> modelMapper.map(loc, LocationResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public LocationResponseDTO updateLocation(Long id, LocationRequestDTO dto) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Location not found"));

        location.setName(dto.getName());
        location.setLatitude(dto.getLatitude());
        location.setLongitude(dto.getLongitude());

        return modelMapper.map(locationRepository.save(location), LocationResponseDTO.class);
    }

    @Override
    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }

    @Override
    public List<LocationResponseDTO> getRouteBetween(String from, String to) {
        Optional<Location> start = locationRepository.findByName(from);
        Optional<Location> end = locationRepository.findByName(to);

        if (start.isPresent() && end.isPresent()) {
            return Arrays.asList(
                    modelMapper.map(start.get(), LocationResponseDTO.class),
                    modelMapper.map(end.get(), LocationResponseDTO.class)
            );
        } else {
            throw new RuntimeException("Route not found between " + from + " and " + to);
        }
    }

    @Override
    public List<LocationResponseDTO> searchByName(String name) {
        return locationRepository.findByNameContainingIgnoreCase(name).stream()
                .map(loc -> modelMapper.map(loc, LocationResponseDTO.class))
                .collect(Collectors.toList());
    }
    @Override
    public List<LocationResponseDTO> getBestRoute(String source, String destination) {
        // Simulated map
        Map<String, List<RouteEdge>> graph = new HashMap<>();
        graph.put("A", Arrays.asList(new RouteEdge("B", 2), new RouteEdge("C", 4)));
        graph.put("B", Arrays.asList(new RouteEdge("C", 1), new RouteEdge("D", 7)));
        graph.put("C", Arrays.asList(new RouteEdge("D", 3)));
        graph.put("D", new ArrayList<>());

        List<String> path = DjkstraAlgorithm.findShortestPath(source, destination, graph);
        if (path == null || path.isEmpty()) {
            throw new RuntimeException("No route found between " + source + " and " + destination);
        }

        List<LocationResponseDTO> routeDTOs = path.stream()
                .map(locationName -> {
                    Optional<Location> locationOpt = locationRepository.findByName(locationName);
                    return locationOpt.map(location -> modelMapper.map(location, LocationResponseDTO.class))
                            .orElseThrow(() -> new RuntimeException("Location not found: " + locationName));
                })
                .collect(Collectors.toList());

        return routeDTOs;
    }

    @Override
    public EmissionResponseDTO getBestRouteWithEmission(String source, String destination, String transportMode) {
        if (!isValidTransportMode(transportMode)) {
            throw new IllegalArgumentException("Invalid transport mode: " + transportMode);
        }

        List<Location> locations = locationRepository.findAll();
        Map<String, List<RouteEdge>> graph = new HashMap<>();

        // Build the graph with real distances
        for (Location from : locations) {
            List<RouteEdge> edges = new ArrayList<>();
            for (Location to : locations) {
                if (!from.equals(to)) {
                    double distance = calculateHaversineDistance(
                            from.getLatitude(), from.getLongitude(),
                            to.getLatitude(), to.getLongitude()
                    );
                    edges.add(new RouteEdge(to.getName(), distance));
                }
            }
            graph.put(from.getName(), edges);
        }

        // Find best path
        List<String> path = DjkstraAlgorithm.findShortestPath(source, destination, graph);
        if (path == null || path.size() <= 1) {
            throw new RuntimeException("No valid route found between " + source + " and " + destination);
        }

        // Calculate total distance and emissions
        double totalDistance = calculateDistanceFromPath(path, graph);
        double emission = calculateCarbonEmission(totalDistance, transportMode);

        return new EmissionResponseDTO(path, totalDistance, emission);
    }

    private double calculateHaversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Earth's radius in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    private boolean isValidTransportMode(String transportMode) {
        return Arrays.asList("car", "bus", "bike", "walk", "train").contains(transportMode.toLowerCase());
    }

    private double calculateDistanceFromPath(List<String> path, Map<String, List<RouteEdge>> graph) {
        double distance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            String current = path.get(i);
            String next = path.get(i + 1);
            distance += graph.get(current).stream()
                    .filter(edge -> edge.getDestination().equals(next))
                    .findFirst().orElseThrow().getWeight();
        }
        return distance;
    }

    private double calculateCarbonEmission(double distanceKm, String transportMode) {
        switch (transportMode.toLowerCase()) {
            case "car": return distanceKm * 0.21;
            case "bus": return distanceKm * 0.089;
            case "bike": return 0.0;
            case "walk": return 0.0;
            case "train": return distanceKm * 0.041;
            default: return 0.0;
        }
    }

}
