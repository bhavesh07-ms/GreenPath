package com.bhavesh.path_planner.path_planner_service.service;

import com.bhavesh.commonutils.commonutils.dto.RouteRequestDTO;
import com.bhavesh.path_planner.path_planner_service.algorithms.StrategyFactory;
import com.bhavesh.path_planner.path_planner_service.interfaces.PathAlgorithm;
import com.bhavesh.path_planner.path_planner_service.records.Edge;
import com.bhavesh.path_planner.path_planner_service.reposiory.GraphStore;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Service
public class PathPlannerService {

    private final GraphStore graphStore;
    private final StrategyFactory strategyFactory;

    public PathPlannerService(GraphStore graphStore, StrategyFactory strategyFactory) {
        this.graphStore = graphStore;
        this.strategyFactory = strategyFactory;
    }

    public PathResult compute(RouteRequestDTO req) {
        Map<String, List<Edge>> graph = graphStore.getGraph();
        PathAlgorithm algo = strategyFactory.decideAlgorithm(req, graph);
        String algoName = algo.getClass().getSimpleName();
        long start = System.nanoTime();
        List<String> path = algo.compute(graph, req.getSource(), req.getDestination(), req);
        long end = System.nanoTime();
        String ms = formatDuration(start, end);

        System.out.println("Selected Algorithm: " +
                algo.getClass().getSimpleName() +
                " — time=" + ms +
                " — pathLength=" + path.size()
        );
        return new PathResult(path, ms, algoName);
    }

    private String formatDuration(long startNs, long endNs) {
        double seconds = (endNs - startNs) / 1_000_000_000.0;
        return String.format("%.3f seconds", seconds);
    }


    public static class PathResult {
        private final List<String> path;
        private final String timeMs;
        private final String algorithm;

        public PathResult(List<String> path, String timeMs, String algorithm) {
            this.path = path;
            this.timeMs = timeMs;
            this.algorithm = algorithm;
        }

        public List<String> getPath() { return path; }
        public String getTimeMs() { return timeMs; }
        public String getAlgorithm() { return algorithm; }
    }
}


