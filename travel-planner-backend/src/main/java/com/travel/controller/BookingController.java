package com.travel.controller;

import com.travel.entity.Booking;
import com.travel.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    
    @Autowired
    private BookingService bookingService;

    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingService.createBooking(booking);
    }

    @PutMapping
    public Booking updateBooking(@RequestBody Booking booking) {
        return bookingService.updateBooking(booking);
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
    }

    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    @GetMapping("/trip/{tripId}")
    public List<Booking> getBookingsByTripId(@PathVariable Long tripId) {
        return bookingService.getBookingsByTripId(tripId);
    }
}