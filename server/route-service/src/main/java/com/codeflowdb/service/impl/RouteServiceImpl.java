package com.codeflowdb.service.impl;

import com.codeflowdb.dto.LocationRequestDTO;
import com.codeflowdb.dto.LocationResponseDTO;
import com.codeflowdb.model.Location;
import com.codeflowdb.repository.LocationRepo;
import com.codeflowdb.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
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
}
