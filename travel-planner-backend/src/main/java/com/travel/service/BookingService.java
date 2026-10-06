package com.travel.service;

import com.travel.entity.Booking;

import java.util.List;

public interface BookingService {
    Booking createBooking(Booking booking);
    Booking updateBooking(Booking booking);
    void deleteBooking(Long id);
    Booking getBookingById(Long id);
    List<Booking> getBookingsByTripId(Long tripId);
}