package com.codeflowdb.dto;

import java.util.List;

public class EmissionResponseDTO {
    private List<String> route;
    private double totalDistance;
    private double carbonEmission;

    public EmissionResponseDTO(List<String> path, double totalDistance, double emission) {
        this.route = path;
        this.totalDistance = totalDistance;
        this.carbonEmission = emission;
    }
    // Getters, setters, constructors
}

