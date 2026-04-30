package com.travel.controller;

import com.travel.entity.Route;
import com.travel.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RouteController {
    
    @Autowired
    private RouteService routeService;

    @GetMapping
    public List<Route> getAllRoutes() {
        return routeService.getAllRoutes();
    }

    @GetMapping("/{id}")
    public Route getRouteById(@PathVariable Long id) {
        return routeService.getRouteById(id);
    }

    @PostMapping
    public Route createRoute(@RequestBody Route route) {
        return routeService.createRoute(route);
    }

    @PutMapping
    public Route updateRoute(@RequestBody Route route) {
        return routeService.updateRoute(route);
    }

    @DeleteMapping("/{id}")
    public void deleteRoute(@PathVariable Long id) {
        routeService.deleteRoute(id);
    }
    
    // 路径规划接口
    @GetMapping("/shortest-time")
    public List<Route> findShortestTimePath(
            @RequestParam Long startLocationId,
            @RequestParam Long endLocationId,
            @RequestParam boolean includeVisaRequired) {
        return routeService.findShortestTimePath(startLocationId, endLocationId, includeVisaRequired);
    }
    
    @GetMapping("/least-cost")
    public List<Route> findLeastCostPath(
            @RequestParam Long startLocationId,
            @RequestParam Long endLocationId,
            @RequestParam boolean includeVisaRequired) {
        return routeService.findLeastCostPath(startLocationId, endLocationId, includeVisaRequired);
    }
    
    @GetMapping("/least-transfers")
    public List<Route> findLeastTransfersPath(
            @RequestParam Long startLocationId,
            @RequestParam Long endLocationId,
            @RequestParam boolean includeVisaRequired) {
        return routeService.findLeastTransfersPath(startLocationId, endLocationId, includeVisaRequired);
    }
}