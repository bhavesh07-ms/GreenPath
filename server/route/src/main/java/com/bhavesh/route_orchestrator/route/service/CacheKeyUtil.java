package com.bhavesh.route_orchestrator.route.service;

import com.bhavesh.commonutils.commonutils.dto.RouteRequestDTO;

import java.util.Locale;
import java.util.StringJoiner;

public final class CacheKeyUtil {

    private CacheKeyUtil(){}

    public static String routeCacheKey(RouteRequestDTO req) {
        // route:A->M:eco:0.5_0.2_0.2_0.1
        String weights = String.format(Locale.ROOT, "%.3f_%.3f_%.3f_%.3f",
                req.getAlpha(), req.getBeta(), req.getGamma(), req.getDelta());
        return String.format("route:%s->%s:%s:%s",
                req.getSource(), req.getDestination(), req.getMode(), weights);
    }

    public static String routeLockKey(RouteRequestDTO req) {
        return "lock:" + routeCacheKey(req);
    }

    public static String routeReqCountKey(RouteRequestDTO req) {
        return "reqcount:" + req.getSource() + "->" + req.getDestination() + ":" + req.getMode();
    }
}
