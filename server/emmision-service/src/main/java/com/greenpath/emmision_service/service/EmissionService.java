package com.greenpath.emmision_service.service;

import com.greenpath.emmision_service.dto.EmissionRequestDTO;
import com.greenpath.emmision_service.model.EmissionRecord;
import com.greenpath.emmision_service.repository.EmissionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmissionService {

    private final EmissionRepository repo;

    public EmissionService(EmissionRepository repo) {
        this.repo = repo;
    }

    public EmissionRecord saveEmission(EmissionRequestDTO dto) {
        EmissionRecord record = EmissionRecord.builder()
                .source(dto.getSource())
                .destination(dto.getDestination())
                .mode(dto.getMode())
                .distanceKm(dto.getDistanceKm())
                .carbonEmission(dto.getCarbonEmission())
                .timestamp(LocalDateTime.now())
                .build();

        return repo.save(record);
    }

    public List<EmissionRecord> getAllEmissions() {
        return repo.findAll();
    }
}
