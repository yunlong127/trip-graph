package com.travel.service;

import com.travel.entity.Facility;
import java.util.List;

public interface FacilityService {
    List<Facility> getAllFacilities();
    Facility getFacilityById(Long id);
    Facility createFacility(Facility facility);
    Facility updateFacility(Facility facility);
    void deleteFacility(Long id);
    List<Facility> getFacilitiesByLocationId(Long locationId);
}
