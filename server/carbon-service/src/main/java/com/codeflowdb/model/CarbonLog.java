package com.codeflowdb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class CarbonLog {
    @Id
    @GeneratedValue
    private Long id;

    private String route;
    private double distanceInKm;
    private double carbonSaved; // kg CO2
    private LocalDateTime timestamp;
}
