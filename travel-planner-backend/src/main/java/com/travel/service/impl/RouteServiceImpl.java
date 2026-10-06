package com.travel.service.impl;

import com.travel.entity.Route;
import com.travel.repository.RouteRepository;
import com.travel.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class RouteServiceImpl implements RouteService {
    
    @Autowired
    private RouteRepository routeRepository;

    @Override
    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    @Override
    public Route getRouteById(Long id) {
        return routeRepository.findById(id).orElse(null);
    }

    @Override
    public Route createRoute(Route route) {
        return routeRepository.save(route);
    }

    @Override
    public Route updateRoute(Route route) {
        return routeRepository.save(route);
    }

    @Override
    public void deleteRoute(Long id) {
        routeRepository.deleteById(id);
    }

    @Override
    public List<Route> findShortestTimePath(Long startLocationId, Long endLocationId, boolean includeVisaRequired) {
        // 构建图
        Map<Long, List<Route>> graph = new HashMap<>();
        List<Route> allRoutes = routeRepository.findAll();
        Set<Long> allLocationIds = new HashSet<>();
        
        for (Route route : allRoutes) {
            if (!includeVisaRequired && route.isVisaRequired()) {
                continue;
            }
            
            Long startId = route.getStartLocation().getId();
            Long endId = route.getEndLocation().getId();
            allLocationIds.add(startId);
            allLocationIds.add(endId);
            
            // 添加正向路线
            if (!graph.containsKey(startId)) {
                graph.put(startId, new ArrayList<>());
            }
            graph.get(startId).add(route);
            
            // 添加反向路线
            Route reverseRoute = new Route();
            reverseRoute.setDistance(route.getDistance());
            reverseRoute.setDuration(route.getDuration());
            reverseRoute.setPrice(route.getPrice());
            reverseRoute.setTransportation(route.getTransportation());
            reverseRoute.setVisaRequired(route.isVisaRequired());
            reverseRoute.setStartLocation(route.getEndLocation());
            reverseRoute.setEndLocation(route.getStartLocation());
            
            if (!graph.containsKey(endId)) {
                graph.put(endId, new ArrayList<>());
            }
            graph.get(endId).add(reverseRoute);
        }
        
        // Dijkstra算法 - 最短时间
        Map<Long, Double> times = new HashMap<>();
        Map<Long, Route> previousRoutes = new HashMap<>();
        PriorityQueue<Map.Entry<Long, Double>> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(Map.Entry::getValue));
        
        // 初始化时间
        for (Long locationId : allLocationIds) {
            times.put(locationId, Double.MAX_VALUE);
        }
        times.put(startLocationId, 0.0);
        priorityQueue.add(new AbstractMap.SimpleEntry<>(startLocationId, 0.0));
        
        while (!priorityQueue.isEmpty()) {
            Map.Entry<Long, Double> entry = priorityQueue.poll();
            Long currentLocationId = entry.getKey();
            double currentTime = entry.getValue();
            
            // 优化：如果当前取出的时间不是该点的最短时间，跳过
            if (currentTime > times.get(currentLocationId)) {
                continue;
            }
            
            if (currentLocationId.equals(endLocationId)) {
                break;
            }
            
            if (!graph.containsKey(currentLocationId)) {
                continue;
            }
            
            for (Route route : graph.get(currentLocationId)) {
                Long nextLocationId = route.getEndLocation().getId();
                double newTime = times.get(currentLocationId) + route.getDuration();
                
                if (newTime < times.get(nextLocationId)) {
                    times.put(nextLocationId, newTime);
                    previousRoutes.put(nextLocationId, route);
                    priorityQueue.add(new AbstractMap.SimpleEntry<>(nextLocationId, newTime));
                }
            }
        }
        
        // 构建路径
        List<Route> path = new ArrayList<>();
        Long current = endLocationId;
        while (previousRoutes.containsKey(current)) {
            Route route = previousRoutes.get(current);
            path.add(route);
            current = route.getStartLocation().getId();
        }
        Collections.reverse(path);
        return path;
    }
    
    @Override
    public List<Route> findLeastCostPath(Long startLocationId, Long endLocationId, boolean includeVisaRequired) {
        // 构建图
        Map<Long, List<Route>> graph = new HashMap<>();
        List<Route> allRoutes = routeRepository.findAll();
        Set<Long> allLocationIds = new HashSet<>();
        
        for (Route route : allRoutes) {
            if (!includeVisaRequired && route.isVisaRequired()) {
                continue;
            }
            
            Long startId = route.getStartLocation().getId();
            Long endId = route.getEndLocation().getId();
            allLocationIds.add(startId);
            allLocationIds.add(endId);
            
            // 添加正向路线
            if (!graph.containsKey(startId)) {
                graph.put(startId, new ArrayList<>());
            }
            graph.get(startId).add(route);
            
            // 添加反向路线
            Route reverseRoute = new Route();
            reverseRoute.setDistance(route.getDistance());
            reverseRoute.setDuration(route.getDuration());
            reverseRoute.setPrice(route.getPrice());
            reverseRoute.setTransportation(route.getTransportation());
            reverseRoute.setVisaRequired(route.isVisaRequired());
            reverseRoute.setStartLocation(route.getEndLocation());
            reverseRoute.setEndLocation(route.getStartLocation());
            
            if (!graph.containsKey(endId)) {
                graph.put(endId, new ArrayList<>());
            }
            graph.get(endId).add(reverseRoute);
        }
        
        // Dijkstra算法 - 最少花费
        Map<Long, Double> costs = new HashMap<>();
        Map<Long, Route> previousRoutes = new HashMap<>();
        PriorityQueue<Map.Entry<Long, Double>> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(Map.Entry::getValue));
        
        // 初始化花费
        for (Long locationId : allLocationIds) {
            costs.put(locationId, Double.MAX_VALUE);
        }
        costs.put(startLocationId, 0.0);
        priorityQueue.add(new AbstractMap.SimpleEntry<>(startLocationId, 0.0));
        
        while (!priorityQueue.isEmpty()) {
            Map.Entry<Long, Double> entry = priorityQueue.poll();
            Long currentLocationId = entry.getKey();
            double currentCost = entry.getValue();
            
            // 优化：如果当前取出的花费不是该点的最小花费，跳过
            if (currentCost > costs.get(currentLocationId)) {
                continue;
            }
            
            if (currentLocationId.equals(endLocationId)) {
                break;
            }
            
            if (!graph.containsKey(currentLocationId)) {
                continue;
            }
            
            for (Route route : graph.get(currentLocationId)) {
                Long nextLocationId = route.getEndLocation().getId();
                double newCost = costs.get(currentLocationId) + route.getPrice();
                
                if (newCost < costs.get(nextLocationId)) {
                    costs.put(nextLocationId, newCost);
                    previousRoutes.put(nextLocationId, route);
                    priorityQueue.add(new AbstractMap.SimpleEntry<>(nextLocationId, newCost));
                }
            }
        }
        
        // 构建路径
        List<Route> path = new ArrayList<>();
        Long current = endLocationId;
        while (previousRoutes.containsKey(current)) {
            Route route = previousRoutes.get(current);
            path.add(route);
            current = route.getStartLocation().getId();
        }
        Collections.reverse(path);
        return path;
    }

    @Override
    public List<Route> findLeastTransfersPath(Long startLocationId, Long endLocationId, boolean includeVisaRequired) {
        // 构建图
        Map<Long, List<Route>> graph = new HashMap<>();
        List<Route> allRoutes = routeRepository.findAll();
        
        for (Route route : allRoutes) {
            if (!includeVisaRequired && route.isVisaRequired()) {
                continue;
            }
            
            Long startId = route.getStartLocation().getId();
            Long endId = route.getEndLocation().getId();
            
            // 添加正向路线
            if (!graph.containsKey(startId)) {
                graph.put(startId, new ArrayList<>());
            }
            graph.get(startId).add(route);
            
            // 添加反向路线
            Route reverseRoute = new Route();
            reverseRoute.setDistance(route.getDistance());
            reverseRoute.setDuration(route.getDuration());
            reverseRoute.setPrice(route.getPrice());
            reverseRoute.setTransportation(route.getTransportation());
            reverseRoute.setVisaRequired(route.isVisaRequired());
            reverseRoute.setStartLocation(route.getEndLocation());
            reverseRoute.setEndLocation(route.getStartLocation());
            
            if (!graph.containsKey(endId)) {
                graph.put(endId, new ArrayList<>());
            }
            graph.get(endId).add(reverseRoute);
        }
        
        // 广度优先搜索
        Queue<Map.Entry<Long, List<Route>>> queue = new LinkedList<>();
        Set<Long> visited = new HashSet<>();
        
        queue.add(new AbstractMap.SimpleEntry<>(startLocationId, new ArrayList<>()));
        visited.add(startLocationId);
        
        while (!queue.isEmpty()) {
            Map.Entry<Long, List<Route>> entry = queue.poll();
            Long currentLocationId = entry.getKey();
            List<Route> currentPath = entry.getValue();
            
            if (currentLocationId.equals(endLocationId)) {
                return currentPath;
            }
            
            if (!graph.containsKey(currentLocationId)) {
                continue;
            }
            
            for (Route route : graph.get(currentLocationId)) {
                Long nextLocationId = route.getEndLocation().getId();
                if (!visited.contains(nextLocationId)) {
                    visited.add(nextLocationId);
                    List<Route> newPath = new ArrayList<>(currentPath);
                    newPath.add(route);
                    queue.add(new AbstractMap.SimpleEntry<>(nextLocationId, newPath));
                }
            }
        }
        
        return new ArrayList<>();
    }
}