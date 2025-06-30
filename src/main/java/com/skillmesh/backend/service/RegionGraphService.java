package com.skillmesh.backend.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class RegionGraphService {

    private final Map<String, Map<String, Integer>> regionGraph = new HashMap<>();

    @PostConstruct
    public void loadRegionGraph() {
        regionGraph.put("India/Asia", Map.of("EU/West", 200, "SEA", 90));
        regionGraph.put("EU/West", Map.of("India/Asia", 200, "US/East", 150));
        regionGraph.put("SEA", Map.of("India/Asia", 90, "US/East", 300));
        regionGraph.put("US/East", Map.of("EU/West", 150, "SEA", 300));
        regionGraph.put("US/West", Map.of("EU/West", 250, "SEA", 200));
        regionGraph.put("EU/East", Map.of("EU/West", 100, "SEA", 400));
        regionGraph.put("EU/South", Map.of("EU/West", 150, "SEA", 350));
        regionGraph.put("EU/North", Map.of("EU/West", 120, "SEA", 380));
        regionGraph.put("Asia/South", Map.of("India/Asia", 80, "SEA", 100));
        regionGraph.put("Asia/North", Map.of("India/Asia", 150, "SEA", 200));
        regionGraph.put("Australia", Map.of("SEA", 50, "US/East", 400, "EU/West", 500));
        regionGraph.put("Africa/North", Map.of("EU/West", 300, "SEA", 350));
        regionGraph.put("Africa/South", Map.of("EU/West", 400, "SEA", 450));
        regionGraph.put("SouthAmerica", Map.of("US/East", 200, "SEA", 300, "EU/West", 400));
        regionGraph.put("MiddleEast", Map.of("India/Asia", 100, "EU/West", 250, "SEA", 200));
        regionGraph.put("Canada", Map.of("US/East", 100, "US/West", 150, "EU/West", 300));
        regionGraph.put("Russia", Map.of("EU/West", 200, "SEA", 300, "US/East", 400));
        regionGraph.put("Japan", Map.of("SEA", 50, "US/East", 300, "EU/West", 400));
        regionGraph.put("Korea", Map.of("SEA", 30, "US/East", 250, "EU/West", 350));
        regionGraph.put("China", Map.of("SEA", 70, "US/East", 350, "EU/West", 450));
        regionGraph.put("Singapore", Map.of("SEA", 20, "US/East", 200, "EU/West", 300));
    }

    public List<String> getNearbyRegions(String region, int maxLatency) {
        Map<String, Integer> neighbors = regionGraph.getOrDefault(region, Collections.emptyMap());
        return neighbors.entrySet().stream()
            .filter(e -> e.getValue() <= maxLatency)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }
}
