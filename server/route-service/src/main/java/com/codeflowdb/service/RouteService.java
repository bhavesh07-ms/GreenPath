package com.codeflowdb.service;

import com.codeflowdb.dto.LocationRequestDTO;
import com.codeflowdb.dto.LocationResponseDTO;
import com.codeflowdb.model.Location;

import java.util.List;

public interface RouteService {
    LocationResponseDTO saveLocation(LocationRequestDTO dto);

    LocationResponseDTO getLocationById(Long id);

    List<LocationResponseDTO> getAllLocations();

    LocationResponseDTO updateLocation(Long id, LocationRequestDTO dto);

    void deleteLocation(Long id);

    List<LocationResponseDTO> getRouteBetween(String from, String to);

    List<LocationResponseDTO> searchByName(String name);

    public List<String> getBestRoute(String source, String destination);
}
