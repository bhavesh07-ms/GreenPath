package com.codeflowdb.route;

import java.util.*;

public class DjkstraAlgorithm {

    public static class RouteEdge {
        private String destination;
        private double cost;

        public RouteEdge(String destination, double cost) {
            this.destination = destination;
            this.cost = cost;
        }

        public String getDestination() {
            return destination;
        }

        public double getWeight() {
            return cost;
        }
    }

    public static List<String> findShortestPath(String start, String end, Map<String, List<RouteEdge>> graph) {
        Map<String, Double> distance = new HashMap<>();
        Map<String, String> prev = new HashMap<>();
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingDouble(distance::get));

        for (String node : graph.keySet()) {
            distance.put(node, Double.MAX_VALUE);
            prev.put(node, null);
        }

        distance.put(start, 0.0);
        pq.add(start);

        while (!pq.isEmpty()) {
            String current = pq.poll();
            for (RouteEdge edge : graph.getOrDefault(current, new ArrayList<>())) {
                double newDist = distance.get(current) + edge.cost;
                if (newDist < distance.get(edge.destination)) {
                    distance.put(edge.destination, newDist);
                    prev.put(edge.destination, current);
                    pq.add(edge.destination);
                }
            }
        }

        // Build path
        List<String> path = new LinkedList<>();
        String current = end;
        while (current != null) {
            path.add(0, current);
            current = prev.get(current);
        }

        return path;
    }
}
