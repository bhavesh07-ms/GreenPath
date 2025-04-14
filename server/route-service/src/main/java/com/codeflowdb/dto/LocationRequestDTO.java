package com.codeflowdb.dto;

import lombok.Data;

import java.util.List;

@Data
public class LocationRequestDTO {
    private String name;
    private double latitude;
    private double longitude;
}
