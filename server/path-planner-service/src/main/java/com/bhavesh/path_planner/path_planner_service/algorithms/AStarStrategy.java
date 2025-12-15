package com.bhavesh.path_planner.path_planner_service.algorithms;
import com.bhavesh.commonutils.commonutils.dto.RouteRequestDTO;
import com.bhavesh.path_planner.path_planner_service.interfaces.PathAlgorithm;
import com.bhavesh.path_planner.path_planner_service.records.Edge;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("astar")
public class AStarStrategy implements PathAlgorithm {

    @Override
    public List<String> compute(Map<String, List<Edge>> graph, String start, String end, RouteRequestDTO req) {

        // gScore = cost from start to current node
        Map<String, Double> gScore = new HashMap<>();
        Map<String, Double> fScore = new HashMap<>();
        Map<String, String> cameFrom = new HashMap<>();

        for (String node : graph.keySet()) {
            gScore.put(node, Double.MAX_VALUE);
            fScore.put(node, Double.MAX_VALUE);
        }

        gScore.put(start, 0.0);
        fScore.put(start, heuristic(start, end, graph)); // initial heuristic

        PriorityQueue<String> open = new PriorityQueue<>(Comparator.comparingDouble(fScore::get));
        open.add(start);

        while (!open.isEmpty()) {
            String current = open.poll();
            if (current.equals(end)) break;

            double gCurrent = gScore.getOrDefault(current, Double.MAX_VALUE);
            for (Edge edge : graph.getOrDefault(current, List.of())) {
                String neighbor = edge.node();
                double tentativeG = gCurrent + computeCost(edge, req);

                if (tentativeG < gScore.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeG);
                    double f = tentativeG + heuristic(neighbor, end, graph);
                    fScore.put(neighbor, f);
                    // ensure open contains neighbor (priority queue does not update keys; re-insert)
                    open.remove(neighbor);
                    open.add(neighbor);
                }
            }
        }

        // reconstruct
        return reconstructPath(cameFrom, start, end);
    }

    private double computeCost(Edge e, RouteRequestDTO req) {
        return req.getAlpha() * e.distance()
                + req.getBeta()  * e.trafficPenalty()
                + req.getGamma() * e.roadCondition()
                + req.getDelta() * Math.max(0, e.elevationDiff());
    }

    /**
     * Simple heuristic: optimistic straight-line estimate.
     * For mock graph without coordinates we use the minimum outgoing distance as lower bound.
     * Replace with Haversine when coordinates are available.
     */
    private double heuristic(String node, String goal, Map<String, List<Edge>> graph) {
        return graph.getOrDefault(node, List.of())
                .stream()
                .mapToDouble(Edge::distance)
                .min()
                .orElse(1.0);
    }

    private List<String> reconstructPath(Map<String, String> cameFrom, String start, String end) {
        LinkedList<String> path = new LinkedList<>();
        String at = end;
        while (at != null) {
            path.addFirst(at);
            if (at.equals(start)) break;
            at = cameFrom.get(at);
        }
        if (path.isEmpty() || !path.getFirst().equals(start)) return Collections.emptyList();
        return path;
    }
}
