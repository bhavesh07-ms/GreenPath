package com.bhavesh.commonutils.commonutils.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteRequestDTO {
    private String source;
    private String destination;
    private String mode; // eco, fast, user-preferred

    private double alpha; // distance weight
    private double beta;  // road quality weight
    private double gamma; // traffic weight
    private double delta; // carbon emission weight

    private String sessionId; // optional, for resume
}
