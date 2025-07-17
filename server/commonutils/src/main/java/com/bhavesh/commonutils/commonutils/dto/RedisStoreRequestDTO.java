package com.bhavesh.commonutils.commonutils.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedisStoreRequestDTO {
    private String sessionId;
    private Object route; // can be structured later
}
