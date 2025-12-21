package com.bhavesh.route_orchestrator.route.aop;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class RouteActivationASpectHelper {

    private int extractBaselineTime(int size) {

        return size*120;
    }

    public int getExtractBaselineTime(int size) {
        return this.getExtractBaselineTime(size);
    }
}
