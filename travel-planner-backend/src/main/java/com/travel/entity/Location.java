package com.travel.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String country;
    
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Facility> facilities;
    
    @OneToMany(mappedBy = "startLocation", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Route> outgoingRoutes;
    
    @OneToMany(mappedBy = "endLocation", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Route> incomingRoutes;

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public List<Facility> getFacilities() {
        return facilities;
    }
    public void setFacilities(List<Facility> facilities) {
        this.facilities = facilities;
    }
    public List<Route> getOutgoingRoutes() {
        return outgoingRoutes;
    }
    public void setOutgoingRoutes(List<Route> outgoingRoutes) {
        this.outgoingRoutes = outgoingRoutes;
    }
    public List<Route> getIncomingRoutes() {
        return incomingRoutes;
    }
    public void setIncomingRoutes(List<Route> incomingRoutes) {
        this.incomingRoutes = incomingRoutes;
    }
}