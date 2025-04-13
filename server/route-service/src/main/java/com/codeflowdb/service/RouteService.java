package com.codeflowdb.service;

import com.codeflowdb.dto.LocationRequestDTO;
import com.codeflowdb.dto.LocationResponseDTO;
import com.codeflowdb.model.Location;

import java.util.List;

public interface RouteService {
    LocationResponseDTO saveLocation(LocationRequestDTO dto);
    List<LocationResponseDTO> getAllLocations();
    List<LocationResponseDTO> getRouteBetween(String from, String to);
}
