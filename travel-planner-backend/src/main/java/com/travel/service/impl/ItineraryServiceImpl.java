package com.travel.service.impl;

import com.travel.entity.Itinerary;
import com.travel.repository.ItineraryRepository;
import com.travel.service.ItineraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItineraryServiceImpl implements ItineraryService {
    
    @Autowired
    private ItineraryRepository itineraryRepository;

    @Override
    public Itinerary createItinerary(Itinerary itinerary) {
        return itineraryRepository.save(itinerary);
    }

    @Override
    public Itinerary updateItinerary(Itinerary itinerary) {
        return itineraryRepository.save(itinerary);
    }

    @Override
    public void deleteItinerary(Long id) {
        itineraryRepository.deleteById(id);
    }

    @Override
    public Itinerary getItineraryById(Long id) {
        return itineraryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Itinerary> getItinerariesByTripId(Long tripId) {
        return itineraryRepository.findByTripId(tripId);
    }
}