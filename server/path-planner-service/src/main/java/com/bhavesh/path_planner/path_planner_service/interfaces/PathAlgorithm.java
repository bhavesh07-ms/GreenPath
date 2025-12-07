package com.bhavesh.path_planner.path_planner_service.interfaces;

import com.bhavesh.commonutils.commonutils.dto.RouteRequestDTO;
import com.bhavesh.path_planner.path_planner_service.records.Edge;

import java.util.*;

public interface PathAlgorithm {
    /**
     * Compute route from start -> end using graph and request factors.
     *
     * @param graph graph adjacency list (node -> list of edges)
     * @param start starting node id
     * @param end   destination node id
     * @param req   route request (alpha, beta, gamma, delta, mode, etc)
     * @return ordered list of node ids representing path (start...end)
     */
    List<String> compute(Map<String, List<Edge>> graph, String start, String end, RouteRequestDTO req);
}