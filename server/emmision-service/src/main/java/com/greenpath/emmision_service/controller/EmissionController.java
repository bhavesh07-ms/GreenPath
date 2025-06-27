package com.greenpath.emmision_service.controller;

import com.greenpath.emmision_service.dto.EmissionRequestDTO;
import com.greenpath.emmision_service.dto.EmissionResponseDTO;
import com.greenpath.emmision_service.model.EmissionRecord;
import com.greenpath.emmision_service.service.EmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/emissions")
public class EmissionController {

    private final EmissionService service;

    public EmissionController(EmissionService service) {
        this.service = service;
    }


    @PostMapping("/calculate")
    public ResponseEntity<EmissionResponseDTO> calculateEmission(@RequestBody EmissionRequestDTO request) {
        EmissionResponseDTO response = service.calculateEmission(request);
        return ResponseEntity.ok(response);
    }

}

