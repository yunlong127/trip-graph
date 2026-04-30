package com.travel.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "route")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private double distance;
    @Column(nullable = false)
    private String transportation;
    private boolean visaRequired;
    @Column(nullable = false)
    private double duration;
    @Column(nullable = false)
    private double price;
    
    @ManyToOne
    @JoinColumn(name = "start_location_id", nullable = false)
    private Location startLocation;
    
    @ManyToOne
    @JoinColumn(name = "end_location_id", nullable = false)
    private Location endLocation;

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public double getDistance() {
        return distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }
    public String getTransportation() {
        return transportation;
    }
    public void setTransportation(String transportation) {
        this.transportation = transportation;
    }
    public boolean isVisaRequired() {
        return visaRequired;
    }
    public void setVisaRequired(boolean visaRequired) {
        this.visaRequired = visaRequired;
    }
    public double getDuration() {
        return duration;
    }
    public void setDuration(double duration) {
        this.duration = duration;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public Location getStartLocation() {
        return startLocation;
    }
    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }
    public Location getEndLocation() {
        return endLocation;
    }
    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }
}