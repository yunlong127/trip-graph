package com.travel.service;

import com.travel.entity.Itinerary;

import java.util.List;

public interface ItineraryService {
    Itinerary createItinerary(Itinerary itinerary);
    Itinerary updateItinerary(Itinerary itinerary);
    void deleteItinerary(Long id);
    Itinerary getItineraryById(Long id);
    List<Itinerary> getItinerariesByTripId(Long tripId);
}