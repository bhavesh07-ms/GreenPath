package com.bhavesh.route_orchestrator.route.aop;

import com.bhavesh.commonutils.commonutils.dto.RouteEvent;
import com.bhavesh.route_orchestrator.route.dto.RouteResponseDto;
import com.bhavesh.route_orchestrator.route.service.RouteEventPublisher;
import org.apache.commons.codec.digest.DigestUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Aspect
public class RouteActivationAspect {

    private final RouteEventPublisher routeEventPublisher;
    private final RouteActivationASpectHelper routeActivationASpectHelper;

    public RouteActivationAspect(RouteEventPublisher routeEventPublisher,RouteActivationASpectHelper routeActivationASpectHelper) {
        this.routeEventPublisher = routeEventPublisher;
        this.routeActivationASpectHelper = routeActivationASpectHelper;
    }

    // Pointcut for methods annotated with @EmitRouteActivated
    @Pointcut("@annotation(com.bhavesh.route_orchestrator.route.aop.EmitRouteActivated)")
    public void emitRouteActivatedPointcut() {}

    // AfterReturning advice for methods matching the pointcut
    @AfterReturning(
            pointcut = "emitRouteActivatedPointcut()",
            returning = "response"
    )
    public void afterRouteComputed(RouteResponseDto response) {
        if (response == null || response.getMeta() == null) {
            return;
        }

        Object source = response.getMeta().get("source");



        // Build and publish the RouteEvent
        RouteEvent event = buildEvent(response);
        routeEventPublisher.publish(event);
    }

    @SuppressWarnings("unchecked")
    private RouteEvent buildEvent(RouteResponseDto response) {

        Map<String, Object> payload = (Map<String, Object>) response.getPayload();
        if (payload == null) return null;

        Map<String, Object> data = (Map<String, Object>) payload.get("data");
        if (data == null) return null;

        // ---- Extract path
        List<String> path = (List<String>) data.get("path");
        if (path == null || path.isEmpty()) return null;

        // ---- Extract baseline time
        int baselineTimeMs = path.size()*120;

        // ---- Build routeId (deterministic)
        String routeId = DigestUtils.sha256Hex(String.join("->", path));

        return RouteEvent.builder()
                .eventType("ROUTE_ACTIVATED")
                .routeId(routeId)
                .userId("anonymous-user")   // dummy for now
                .path(path)
                .baselineTime(baselineTimeMs)
                .threshold(20)              // default 20% degradation
                .build();
    }

}