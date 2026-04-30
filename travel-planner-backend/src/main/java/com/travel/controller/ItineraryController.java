package com.travel.controller;

import com.travel.entity.Itinerary;
import com.travel.service.ItineraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itineraries")
public class ItineraryController {
    
    @Autowired
    private ItineraryService itineraryService;

    @PostMapping
    public Itinerary createItinerary(@RequestBody Itinerary itinerary) {
        return itineraryService.createItinerary(itinerary);
    }

    @PutMapping
    public Itinerary updateItinerary(@RequestBody Itinerary itinerary) {
        return itineraryService.updateItinerary(itinerary);
    }

    @DeleteMapping("/{id}")
    public void deleteItinerary(@PathVariable Long id) {
        itineraryService.deleteItinerary(id);
    }

    @GetMapping("/{id}")
    public Itinerary getItineraryById(@PathVariable Long id) {
        return itineraryService.getItineraryById(id);
    }

    @GetMapping("/trip/{tripId}")
    public List<Itinerary> getItinerariesByTripId(@PathVariable Long tripId) {
        return itineraryService.getItinerariesByTripId(tripId);
    }
}