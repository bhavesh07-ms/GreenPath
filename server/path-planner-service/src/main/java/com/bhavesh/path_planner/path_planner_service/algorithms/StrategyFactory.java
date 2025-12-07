package com.bhavesh.path_planner.path_planner_service.algorithms;
import com.bhavesh.commonutils.commonutils.dto.RouteRequestDTO;
import com.bhavesh.path_planner.path_planner_service.interfaces.PathAlgorithm;
import com.bhavesh.path_planner.path_planner_service.records.Edge;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class StrategyFactory {

    private final ApplicationContext ctx;

    public StrategyFactory(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    public PathAlgorithm decideAlgorithm(RouteRequestDTO req, Map<String, List<Edge>> graph) {

        int size = graph.size();
//        boolean ecoMode = "eco".equalsIgnoreCase(req.getMode());
//        boolean fastest = "fastest".equalsIgnoreCase(req.getMode());
        boolean avoidTraffic = req.getBeta() > 0.3; // optional logic

//        boolean coordinatesAvailable = false; // Later when nodes have lat/lon
//        boolean realTimeRequired = req.isRealTime(); // optional flag

        // ---- RULES ----

        // 1. If no heuristic available → use Dijkstra
//        if (!coordinatesAvailable && size <= 1000) {
//            return ctx.getBean("dijkstra", PathAlgorithm.class);
//        }

        // 2. Eco, fastest, optimized → use A*
        if (avoidTraffic) {
            return ctx.getBean("astar", PathAlgorithm.class);
        }

        // 3. If graph too large → use A*
        if (size > 1000) {
            return ctx.getBean("astar", PathAlgorithm.class);
        }

        // 4. Real-time requirement → A*
//        if (realTimeRequired) {
//            return ctx.getBean("astar", PathAlgorithm.class);
//        }

        // Fallback → Dijkstra
        return ctx.getBean("dijkstra", PathAlgorithm.class);
    }
}
