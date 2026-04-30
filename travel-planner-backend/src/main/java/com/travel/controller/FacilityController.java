package com.travel.controller;

import com.travel.entity.Facility;
import com.travel.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/facilities")
public class FacilityController {
    
    @Autowired
    private FacilityService facilityService;
    
    @GetMapping
    public List<Facility> getAllFacilities() {
        return facilityService.getAllFacilities();
    }
    
    @GetMapping("/{id}")
    public Facility getFacilityById(@PathVariable Long id) {
        return facilityService.getFacilityById(id);
    }
    
    @PostMapping
    public Facility createFacility(@RequestBody Facility facility) {
        return facilityService.createFacility(facility);
    }
    
    @PutMapping("/{id}")
    public Facility updateFacility(@PathVariable Long id, @RequestBody Facility facility) {
        facility.setId(id);
        return facilityService.updateFacility(facility);
    }
    
    @DeleteMapping("/{id}")
    public void deleteFacility(@PathVariable Long id) {
        facilityService.deleteFacility(id);
    }
    
    @GetMapping("/location/{locationId}")
    public List<Facility> getFacilitiesByLocationId(@PathVariable Long locationId) {
        return facilityService.getFacilitiesByLocationId(locationId);
    }
}
