package com.greenpath.emmision_service.dto;

import lombok.Data;

@Data
public class EmissionRequestDTO {
    private String source;
    private String destination;
    private String mode;
    private double distanceKm;

    // Getters & Setters
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }

    public double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(double distanceKm) { this.distanceKm = distanceKm; }
}

