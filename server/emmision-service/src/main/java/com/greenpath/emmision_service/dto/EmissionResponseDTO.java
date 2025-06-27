package com.greenpath.emmision_service.dto;

import lombok.Data;

@Data
public class EmissionResponseDTO {
    private String id;
    private String mode;
    private double carbonEmission;
    private String category;

    public EmissionResponseDTO(String id, String mode, double carbonEmission, String category) {
        this.id = id;
        this.mode = mode;
        this.carbonEmission = carbonEmission;
        this.category = category;
    }

    // Getters
    public String getId() { return id; }
    public String getMode() { return mode; }
    public double getCarbonEmission() { return carbonEmission; }
    public String getCategory() { return category; }
}
