package com.travel.service.impl;

import com.travel.entity.Location;
import com.travel.repository.LocationRepository;
import com.travel.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {
    
    @Autowired
    private LocationRepository locationRepository;

    @Override
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    @Override
    public Location getLocationById(Long id) {
        return locationRepository.findById(id).orElse(null);
    }

    @Override
    public Location createLocation(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public Location updateLocation(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }
}