package com.travel.controller;

import com.travel.entity.Trip;
import com.travel.entity.User;
import com.travel.service.TripService;
import com.travel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trips")
public class TripController {
    
    @Autowired
    private TripService tripService;
    
    @Autowired
    private UserService userService;

    @PostMapping
    public Trip createTrip(@RequestBody Map<String, Object> requestData) {
        System.out.println("接收到的请求数据: " + requestData);
        
        // 处理日期转换
        Object startDateObj=null;
        Object endDateObj=null;
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            startDateObj = requestData.get("startDate");
            endDateObj = requestData.get("endDate");
        } catch (Exception e) {
            throw new RuntimeException("日期格式错误", e);
        }

        // 处理用户ID
        Object userObj = requestData.get("user");
        Long userId = null;
        if (userObj instanceof Map) {
            Map<?, ?> userMap = (Map<?, ?>) userObj;
            Object idObj = userMap.get("id");
            if (idObj instanceof Number) {
                userId = ((Number) idObj).longValue();
            } else if (idObj instanceof String) {
                userId = Long.parseLong((String) idObj);
            }
        } else if (userObj instanceof Number) {
            userId = ((Number) userObj).longValue();
        }
        
        if (userId == null) {
            throw new RuntimeException("用户ID不能为空");
        }
        
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        Trip trip = TripFactory.createTrip((String) requestData.get("name"),(String) requestData.get("destination"), user, dateFormat.parse(startDateObj.toString()), dateFormat.parse(endDateObj.toString()), (String) requestData.get("status"), (String) requestData.get("description"), Boolean.TRUE.equals(requestData.get("visaRequired")), (String) requestData.get("transportation"));
           
        return tripService.createTrip(trip);
    }

    @PutMapping
    public Trip updateTrip(@RequestBody Trip trip) {
        return tripService.updateTrip(trip);
    }

    @DeleteMapping("/{id}")
    public void deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
    }

    @GetMapping("/{id}")
    public Trip getTripById(@PathVariable Long id) {
        return tripService.getTripById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Trip> getTripsByUserId(@PathVariable Long userId) {
        return tripService.getTripsByUserId(userId);
    }
    
    @GetMapping("/test/{userId}")
    public List<Trip> testGetTripsByUserId(@PathVariable Long userId) {
        return tripService.getTripsByUserId(userId);
    }
    
    @GetMapping("/all")
    public List<Trip> getAllTrips() {
        return tripService.getAllTrips();
    }
}