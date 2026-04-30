package com.travel.service;

import com.travel.entity.Trip;

import java.util.List;

public interface TripService {
    Trip createTrip(Trip trip);
    Trip updateTrip(Trip trip);
    void deleteTrip(Long id);
    Trip getTripById(Long id);
    List<Trip> getTripsByUserId(Long userId);
    List<Trip> getAllTrips();
}