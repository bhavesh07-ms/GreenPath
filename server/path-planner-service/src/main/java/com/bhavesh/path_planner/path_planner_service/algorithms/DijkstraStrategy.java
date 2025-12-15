package com.bhavesh.path_planner.path_planner_service.algorithms;

import com.bhavesh.commonutils.commonutils.dto.RouteRequestDTO;
import com.bhavesh.path_planner.path_planner_service.interfaces.PathAlgorithm;
import com.bhavesh.path_planner.path_planner_service.records.Edge;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("dijkstra")
public class DijkstraStrategy implements PathAlgorithm {

    @Override
    public List<String> compute(Map<String, List<Edge>> graph, String start, String end, RouteRequestDTO req) {
        // distances and predecessor maps
        Map<String, Double> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();

        for (String node : graph.keySet()) dist.put(node, Double.MAX_VALUE);
        dist.put(start, 0.0);

        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingDouble(dist::get));
        pq.add(start);

        while (!pq.isEmpty()) {
            String u = pq.poll();
            if (u == null) break;
            if (u.equals(end)) break;

            double du = dist.getOrDefault(u, Double.MAX_VALUE);
            if (du == Double.MAX_VALUE) continue;

            for (Edge edge : graph.getOrDefault(u, List.of())) {
                String v = edge.node();
                double cost = computeCost(edge, req);
                double alt = du + cost;
                if (alt < dist.getOrDefault(v, Double.MAX_VALUE)) {
                    dist.put(v, alt);
                    prev.put(v, u);
                    pq.add(v);
                }
            }
        }

        // reconstruct
        return reconstructPath(prev, start, end);
    }

    private double computeCost(Edge e, RouteRequestDTO req) {
        return req.getAlpha() * e.distance()
                + req.getBeta()  * e.trafficPenalty()
                + req.getGamma() * e.roadCondition()
                + req.getDelta() * Math.max(0, e.elevationDiff());
    }

    private List<String> reconstructPath(Map<String, String> prev, String start, String end) {
        LinkedList<String> path = new LinkedList<>();
        String at = end;
        while (at != null) {
            path.addFirst(at);
            if (at.equals(start)) break;
            at = prev.get(at);
        }
        // if start not reached, return empty list
        if (path.isEmpty() || !path.getFirst().equals(start)) return Collections.emptyList();
        return path;
    }
}
