package com.greenpath.emmision_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("emission_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmissionRecord {
    @Id
    private String id;
    private String source;
    private String destination;
    private String mode;
    private double distanceKm;
    private double carbonEmission;
    private LocalDateTime timestamp;
}
