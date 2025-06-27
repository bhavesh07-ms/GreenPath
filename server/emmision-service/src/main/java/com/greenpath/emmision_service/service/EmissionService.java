package com.greenpath.emmision_service.service;

import com.greenpath.emmision_service.dto.EmissionRequestDTO;
import com.greenpath.emmision_service.dto.EmissionResponseDTO;
import com.greenpath.emmision_service.model.EmissionRecord;
import com.greenpath.emmision_service.repository.EmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EmissionService {

    private final EmissionRepository emissionRepository;


    // Emission factors (kg CO2 per km)
    private static final Map<String, Double> EMISSION_FACTORS = new HashMap<>();
    static {
        EMISSION_FACTORS.put("car", 0.192);     // average car
        EMISSION_FACTORS.put("bike", 0.021);    // petrol bike
        EMISSION_FACTORS.put("bus", 0.105);     // public transport
        EMISSION_FACTORS.put("train", 0.041);   // rail travel
        EMISSION_FACTORS.put("flight", 0.255);  // domestic flight
        EMISSION_FACTORS.put("walking", 0.0);   // no emission
        EMISSION_FACTORS.put("cycling", 0.0);   // no emission
    }



    public EmissionResponseDTO calculateEmission(EmissionRequestDTO request) {
        String mode = request.getMode().toLowerCase();

        if (!EMISSION_FACTORS.containsKey(mode)) {
            throw new IllegalArgumentException("Unsupported transportation mode: " + request.getMode());
        }

        double factor = EMISSION_FACTORS.get(mode);
        double distance = request.getDistanceKm();

        if (distance < 0) {
            throw new IllegalArgumentException("Distance must be positive.");
        }

        double rawEmission = factor * distance;
        double roundedEmission = BigDecimal.valueOf(rawEmission)
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();


        EmissionRecord emissionRecord = new EmissionRecord();
        emissionRecord.setId(UUID.randomUUID().toString());
        emissionRecord.setSource(request.getSource());
        emissionRecord.setDestination(request.getDestination());
        emissionRecord.setMode(mode);
        emissionRecord.setDistanceKm(distance);
        emissionRecord.setCarbonEmission(roundedEmission);
        Instant instant = Instant.now();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        emissionRecord.setTimestamp(localDate.atStartOfDay());

        emissionRepository.save(emissionRecord);

        return new EmissionResponseDTO(
                emissionRecord.getId(),
                mode,
                roundedEmission,
                categorizeEmission(roundedEmission)
        );
    }

    private String categorizeEmission(double emission) {
        if (emission == 0.0) return "Zero";
        else if (emission <= 1.0) return "Low";
        else if (emission <= 5.0) return "Moderate";
        else return "High";
    }
}
