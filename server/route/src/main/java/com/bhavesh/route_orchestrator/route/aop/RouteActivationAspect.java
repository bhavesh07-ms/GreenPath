package com.bhavesh.route_orchestrator.route.aop;

import com.bhavesh.commonutils.commonutils.dto.RouteEvent;
import com.bhavesh.route_orchestrator.route.dto.RouteResponseDto;
import com.bhavesh.route_orchestrator.route.service.RouteEventPublisher;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.RouteMatcher;

@Aspect
@Component
public class RouteActivationAspect {

    private final RouteEventPublisher routeEventPublisher;

    public RouteActivationAspect(RouteEventPublisher routeEventPublisher) {
        this.routeEventPublisher = routeEventPublisher;
    }

    @Pointcut("@annotation(com.bhavesh.route_orchestrator.aop.EmitRouteActivated)")
    public void emitRouteActivatedPointcut() {}

    @AfterReturning(
            pointcut = "emitRouteActivatedPointcut()",
            returning = "response"
    )
    public void afterRouteComputed(RouteResponseDto response) {

        if (response == null || response.getMeta() == null) {
            return;
        }

        Object source = response.getMeta().get("source");

        // 🚨 CRITICAL GUARD
        if (!"computed".equals(source)) {
            return;
        }

        // Extract what Lambda / traffic monitoring needs
        RouteEvent event = buildEvent(response);

        routeEventPublisher.publish(event);
    }

    private RouteEvent buildEvent(RouteResponseDto response) {
        return RouteEvent.builder().build();
    }
}
