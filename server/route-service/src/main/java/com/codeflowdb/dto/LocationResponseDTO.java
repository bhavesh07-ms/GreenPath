package com.codeflowdb.dto;

import lombok.Data;

@Data
public class LocationResponseDTO {
    private Long id;
    private String name;
    private double latitude;
    private double longitude;
}
