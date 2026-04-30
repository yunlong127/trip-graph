package com.travel.service.impl;

import com.travel.entity.Trip;
import com.travel.repository.TripRepository;
import com.travel.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripServiceImpl implements TripService {
    
    @Autowired
    private TripRepository tripRepository;

    @Override
    public Trip createTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    @Override
    public Trip updateTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    @Override
    public void deleteTrip(Long id) {
        tripRepository.deleteById(id);
    }

    @Override
    public Trip getTripById(Long id) {
        return tripRepository.findById(id).orElse(null);
    }

    @Override
    public List<Trip> getTripsByUserId(Long userId) {
        return tripRepository.findByUserId(userId);
    }
    
    @Override
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }
}