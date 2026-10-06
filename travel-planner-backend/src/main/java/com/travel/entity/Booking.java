package com.travel.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String bookingNumber;
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date bookingDate;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private double price;
    
    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getBookingNumber() {
        return bookingNumber;
    }
    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }
    public Date getBookingDate() {
        return bookingDate;
    }
    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public Trip getTrip() {
        return trip;
    }
    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}