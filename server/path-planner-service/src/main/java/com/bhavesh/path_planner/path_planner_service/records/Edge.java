package com.bhavesh.path_planner.path_planner_service.records;
// package com.bhavesh.pathplanner.graph;

public record Edge(
        String node,           // target node id
        double distance,       // kilometers (or meters; keep unit consistent)
        double trafficPenalty, // 0..10 (dynamic)
        double roadCondition,  // 1=good ... 5=bad
        double elevationDiff,  // meters uphill (positive) or downhill (negative)
        // additional metadata similar to PostGIS that you may want later:
        double speedLimitKph,  // e.g. 50.0
        String allowedTurns,   // "straight,left,right" or "all"
        String geometryWkt     // LineString WKT (e.g. "LINESTRING(lon1 lat1, lon2 lat2)")
) {

}


