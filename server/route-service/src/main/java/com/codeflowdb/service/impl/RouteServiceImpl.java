package com.codeflowdb.service.impl;

import com.codeflowdb.dto.LocationRequestDTO;
import com.codeflowdb.dto.LocationResponseDTO;
import com.codeflowdb.model.Location;
import com.codeflowdb.repository.LocationRepo;
import com.codeflowdb.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final LocationRepo locationRepository;
    private final ModelMapper modelMapper;

    @Override
    public LocationResponseDTO saveLocation(LocationRequestDTO dto) {
        Location location = modelMapper.map(dto, Location.class);
        Location saved = locationRepository.save(location);
        return modelMapper.map(saved, LocationResponseDTO.class);
    }

    @Override
    public List<LocationResponseDTO> getAllLocations() {
        return locationRepository.findAll().stream()
                .map(loc -> modelMapper.map(loc, LocationResponseDTO.class))
                .collect(Collectors.toList());
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
}
