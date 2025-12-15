package com.bhavesh.path_planner.path_planner_service.reposiory;

import com.bhavesh.path_planner.path_planner_service.records.Edge;
import com.bhavesh.path_planner.path_planner_service.records.Node;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.*;


import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GraphStore {

    private final Map<String, List<Edge>> graph = new HashMap<>();
    private final Map<String, Node> nodes = new HashMap<>();

    @PostConstruct
    public void load() {
        // --- Nodes: id, lat, lon, elevation(m) ---
        nodes.put("N1", new Node("N1", 18.5204, 73.8567, 560)); // Pune center approx
        nodes.put("N2", new Node("N2", 18.5220, 73.8600, 562));
        nodes.put("N3", new Node("N3", 18.5180, 73.8590, 558));
        nodes.put("N4", new Node("N4", 18.5165, 73.8540, 555));
        nodes.put("N5", new Node("N5", 18.5195, 73.8525, 554));
        nodes.put("N6", new Node("N6", 18.5230, 73.8535, 565));
        nodes.put("N7", new Node("N7", 18.5250, 73.8570, 568));
        nodes.put("N8", new Node("N8", 18.5265, 73.8610, 570));
        nodes.put("N9", new Node("N9", 18.5275, 73.8480, 552));
        nodes.put("N10", new Node("N10", 18.5150, 73.8490, 549));
        nodes.put("N11", new Node("N11", 18.5285, 73.8550, 565));
        nodes.put("N12", new Node("N12", 18.5300, 73.8595, 567));
        nodes.put("N13", new Node("N13", 18.5120, 73.8580, 551));
        nodes.put("N14", new Node("N14", 18.5170, 73.8630, 559));
        nodes.put("N15", new Node("N15", 18.5310, 73.8470, 548));
        nodes.put("N16", new Node("N16", 18.5330, 73.8520, 553));
        nodes.put("N17", new Node("N17", 18.5360, 73.8590, 560));
        nodes.put("N18", new Node("N18", 18.5110, 73.8460, 545));
        nodes.put("N19", new Node("N19", 18.5290, 73.8440, 547));
        nodes.put("N20", new Node("N20", 18.5140, 73.8615, 556));

        // --- Edges: node, distance(km), trafficPenalty(0..10), roadCondition(1..5), elevationDiff(m),
        //             speedLimitKph, allowedTurns, geometryWkt ---
        graph.put("N1", List.of(
                new Edge("N2", 0.38, 3, 1, 2, 50, "all", "LINESTRING(73.8567 18.5204, 73.8600 18.5220)"),
                new Edge("N3", 0.28, 2, 1, -2, 40, "all", "LINESTRING(73.8567 18.5204, 73.8590 18.5180)"),
                new Edge("N7", 0.60, 5, 2, 8, 60, "all", "LINESTRING(73.8567 18.5204, 73.8570 18.5250)")
        ));

        graph.put("N2", List.of(
                new Edge("N1", 0.38, 3, 1, -2, 50, "all", "LINESTRING(73.8600 18.5220, 73.8567 18.5204)"),
                new Edge("N8", 0.45, 6, 2, 6, 60, "all", "LINESTRING(73.8600 18.5220, 73.8610 18.5265)"),
                new Edge("N6", 0.55, 4, 2, 3, 50, "all", "LINESTRING(73.8600 18.5220, 73.8535 18.5230)")
        ));

        graph.put("N3", List.of(
                new Edge("N1", 0.28, 2, 1, 2, 40, "all", "LINESTRING(73.8590 18.5180, 73.8567 18.5204)"),
                new Edge("N4", 0.70, 7, 3, -3, 50, "left_only", "LINESTRING(73.8590 18.5180, 73.8540 18.5165)")
        ));

        graph.put("N4", List.of(
                new Edge("N3", 0.70, 7, 3, 3, 50, "right_only", "LINESTRING(73.8540 18.5165, 73.8590 18.5180)"),
                new Edge("N5", 0.30, 2, 1, -1, 40, "all", "LINESTRING(73.8540 18.5165, 73.8525 18.5195)"),
                new Edge("N10", 0.70, 4, 2, -6, 50, "all", "LINESTRING(73.8540 18.5165, 73.8490 18.5150)")
        ));

        graph.put("N5", List.of(
                new Edge("N4", 0.30, 2, 1, 1, 40, "all", "LINESTRING(73.8525 18.5195, 73.8540 18.5165)"),
                new Edge("N6", 0.85, 4, 2, 6, 60, "all", "LINESTRING(73.8525 18.5195, 73.8535 18.5230)")
        ));

        graph.put("N6", List.of(
                new Edge("N2", 0.55, 4, 2, -3, 50, "all", "LINESTRING(73.8535 18.5230, 73.8600 18.5220)"),
                new Edge("N5", 0.85, 4, 2, -6, 60, "all", "LINESTRING(73.8535 18.5230, 73.8525 18.5195)"),
                new Edge("N11", 0.90, 5, 3, 10, 60, "all", "LINESTRING(73.8535 18.5230, 73.8550 18.5285)")
        ));

        graph.put("N7", List.of(
                new Edge("N1", 0.60, 5, 2, -8, 60, "all", "LINESTRING(73.8570 18.5250, 73.8567 18.5204)"),
                new Edge("N11", 0.50, 3, 2, 2, 50, "all", "LINESTRING(73.8570 18.5250, 73.8550 18.5285)")
        ));

        graph.put("N8", List.of(
                new Edge("N2", 0.45, 6, 2, -6, 60, "all", "LINESTRING(73.8610 18.5265, 73.8600 18.5220)"),
                new Edge("N12", 0.60, 3, 1, 2, 50, "all", "LINESTRING(73.8610 18.5265, 73.8595 18.5300)")
        ));

        graph.put("N9", List.of(
                new Edge("N10", 0.75, 2, 1, -3, 50, "all", "LINESTRING(73.8480 18.5275, 73.8490 18.5150)"),
                new Edge("N15", 0.90, 4, 3, 2, 60, "all", "LINESTRING(73.8480 18.5275, 73.8470 18.5310)")
        ));

        graph.put("N10", List.of(
                new Edge("N4", 0.70, 4, 2, 6, 50, "all", "LINESTRING(73.8490 18.5150, 73.8540 18.5165)"),
                new Edge("N9", 0.75, 2, 1, 3, 50, "all", "LINESTRING(73.8490 18.5150, 73.8480 18.5275)"),
                new Edge("N13", 0.80, 3, 2, -2, 40, "all", "LINESTRING(73.8490 18.5150, 73.8580 18.5120)")
        ));

        graph.put("N11", List.of(
                new Edge("N6", 0.90, 5, 3, -10, 60, "all", "LINESTRING(73.8550 18.5285, 73.8535 18.5230)"),
                new Edge("N7", 0.50, 3, 2, -2, 50, "all", "LINESTRING(73.8550 18.5285, 73.8570 18.5250)"),
                new Edge("N12", 0.65, 4, 3, 5, 50, "LINESTRING(73.8550 18.5285, 73.8595 18.5300)","LINESTRING(73.8550 18.5285, 73.8595 18.5300)")
        ));

        graph.put("N12", List.of(
                new Edge("N8", 0.60, 3, 1, -2, 50, "all", "LINESTRING(73.8595 18.5300, 73.8610 18.5265)"),
                new Edge("N11", 0.65, 4, 3, -5, 50, "all", "LINESTRING(73.8595 18.5300, 73.8550 18.5285)"),
                new Edge("N17", 1.20, 6, 3, 12, 80, "all", "LINESTRING(73.8595 18.5300, 73.8590 18.5360)")
        ));

        graph.put("N13", List.of(
                new Edge("N10", 0.80, 3, 2, 2, 40, "all", "LINESTRING(73.8580 18.5120, 73.8490 18.5150)"),
                new Edge("N18", 1.30, 2, 1, -6, 50, "all", "LINESTRING(73.8580 18.5120, 73.8460 18.5110)")
        ));

        graph.put("N14", List.of(
                new Edge("N20", 1.10, 3, 1, -3, 50, "all", "LINESTRING(73.8630 18.5170, 73.8615 18.5140)"),
                new Edge("N8", 1.00, 5, 2, 4, 60, "all", "LINESTRING(73.8630 18.5170, 73.8610 18.5265)")
        ));

        graph.put("N15", List.of(
                new Edge("N9", 0.90, 4, 3, -2, 60, "all", "LINESTRING(73.8470 18.5310, 73.8480 18.5275)"),
                new Edge("N16", 0.80, 3, 2, -1, 50, "all", "LINESTRING(73.8470 18.5310, 73.8520 18.5330)")
        ));

        graph.put("N16", List.of(
                new Edge("N15", 0.80, 3, 2, 1, 50, "all", "LINESTRING(73.8520 18.5330, 73.8470 18.5310)"),
                new Edge("N17", 0.95, 4, 2, 6, 60, "all", "LINESTRING(73.8520 18.5330, 73.8590 18.5360)")
        ));

        graph.put("N17", List.of(
                new Edge("N16", 0.95, 4, 2, -6, 60, "all", "LINESTRING(73.8590 18.5360, 73.8520 18.5330)"),
                new Edge("N12", 1.20, 6, 3, -12, 80, "all", "LINESTRING(73.8590 18.5360, 73.8595 18.5300)")
        ));

        graph.put("N18", List.of(
                new Edge("N13", 1.30, 2, 1, 6, 50, "all", "LINESTRING(73.8460 18.5110, 73.8580 18.5120)"),
                new Edge("N10", 1.40, 5, 3, 9, 50, "all", "LINESTRING(73.8460 18.5110, 73.8490 18.5150)")
        ));

        graph.put("N19", List.of(
                new Edge("N15", 1.10, 3, 2, -1, 50, "all", "LINESTRING(73.8440 18.5290, 73.8470 18.5310)"),
                new Edge("N20", 0.95, 4, 2, 2, 50, "all", "LINESTRING(73.8440 18.5290, 73.8615 18.5140)")
        ));

        graph.put("N20", List.of(
                new Edge("N14", 1.10, 3, 1, 3, 50, "all", "LINESTRING(73.8615 18.5140, 73.8630 18.5170)"),
                new Edge("N19", 0.95, 4, 2, -2, 50, "all", "LINESTRING(73.8615 18.5140, 73.8440 18.5290)")
        ));
    }

    public Map<String, List<Edge>> getGraph() {
        return graph;
    }

    public Map<String, Node> getNodes() { return nodes; }

//    private void addEdge(String a, String b, double dist, double traffic, double rc, double elev) {
//        graph.computeIfAbsent(a, k -> new ArrayList<>())
//                .add(new Edge(b, dist, traffic, rc, elev));
//        graph.computeIfAbsent(b, k -> new ArrayList<>())
//                .add(new Edge(a, dist, traffic, rc, elev));
//    }
}
