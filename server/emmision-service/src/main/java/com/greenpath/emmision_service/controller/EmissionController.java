package com.greenpath.emmision_service.controller;

import com.greenpath.emmision_service.dto.EmissionRequestDTO;
import com.greenpath.emmision_service.model.EmissionRecord;
import com.greenpath.emmision_service.service.EmissionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/emissions")
public class EmissionController {

    private final EmissionService service;

    public EmissionController(EmissionService service) {
        this.service = service;
    }

    @PostMapping
    public EmissionRecord logEmission(@RequestBody EmissionRequestDTO dto) {
        return service.saveEmission(dto);
    }

    @GetMapping
    public List<EmissionRecord> getAll() {
        return service.getAllEmissions();
    }
}

