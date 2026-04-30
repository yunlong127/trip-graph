package com.travel.service;

import com.travel.entity.Route;
import java.util.List;
import java.util.Map;

public interface RouteService {
    List<Route> getAllRoutes();
    Route getRouteById(Long id);
    Route createRoute(Route route);
    Route updateRoute(Route route);
    void deleteRoute(Long id);
    
    // 路径规划方法
    List<Route> findShortestTimePath(Long startLocationId, Long endLocationId, boolean includeVisaRequired);
    List<Route> findLeastCostPath(Long startLocationId, Long endLocationId, boolean includeVisaRequired);
    List<Route> findLeastTransfersPath(Long startLocationId, Long endLocationId, boolean includeVisaRequired);
}