package com.travel.service;

import com.travel.entity.Location;
import java.util.List;

public interface LocationService {
    List<Location> getAllLocations();
    Location getLocationById(Long id);
    Location createLocation(Location location);
    Location updateLocation(Location location);
    void deleteLocation(Long id);
}