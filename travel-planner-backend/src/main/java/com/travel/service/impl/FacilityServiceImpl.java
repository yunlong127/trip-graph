package com.travel.service.impl;

import com.travel.entity.Facility;
import com.travel.repository.FacilityRepository;
import com.travel.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FacilityServiceImpl implements FacilityService {
    
    @Autowired
    private FacilityRepository facilityRepository;
    
    @Override
    public List<Facility> getAllFacilities() {
        return facilityRepository.findAll();
    }
    
    @Override
    public Facility getFacilityById(Long id) {
        return facilityRepository.findById(id).orElse(null);
    }
    
    @Override
    public Facility createFacility(Facility facility) {
        return facilityRepository.save(facility);
    }
    
    @Override
    public Facility updateFacility(Facility facility) {
        return facilityRepository.save(facility);
    }
    
    @Override
    public void deleteFacility(Long id) {
        facilityRepository.deleteById(id);
    }
    
    @Override
    public List<Facility> getFacilitiesByLocationId(Long locationId) {
        return facilityRepository.findByLocationId(locationId);
    }
}
