package com.bhavesh.commonutils.commonutils.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PathPlannerInputDTO {
    private RouteRequestDTO request;
    private Object graphSegment; // can be refined into a proper graph DTO later
}