package com.greenpath.emmision_service.dto;

import lombok.Data;

@Data
public class EmissionRequestDTO {
    private String source;
    private String destination;
    private String mode;
    private double distanceKm;
    private double carbonEmission;
}
