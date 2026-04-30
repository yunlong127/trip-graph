package com.travel.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String destination;
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date endDate;
    @Column(nullable = false)
    private String status;
    private String description;
    
    // 签证信息
    private boolean visaRequired;
    private String visaType;
    
    // 交通信息
    private String transportation;
    private String flightNumber;
    private String trainNumber;
    
    // 住宿信息
    private String accommodation;
    private String hotelName;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
    
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Itinerary> itineraries;
    
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Booking> bookings;

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
    public String getDestination() {
        return destination;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isVisaRequired() {
        return visaRequired;
    }
    public void setVisaRequired(boolean visaRequired) {
        this.visaRequired = visaRequired;
    }
    public String getVisaType() {
        return visaType;
    }
    public void setVisaType(String visaType) {
        this.visaType = visaType;
    }
    public String getTransportation() {
        return transportation;
    }
    public void setTransportation(String transportation) {
        this.transportation = transportation;
    }
    public String getFlightNumber() {
        return flightNumber;
    }
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
    public String getTrainNumber() {
        return trainNumber;
    }
    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }
    public String getAccommodation() {
        return accommodation;
    }
    public void setAccommodation(String accommodation) {
        this.accommodation = accommodation;
    }
    public String getHotelName() {
        return hotelName;
    }
    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public List<Itinerary> getItineraries() {
        return itineraries;
    }
    public void setItineraries(List<Itinerary> itineraries) {
        this.itineraries = itineraries;
    }
    public List<Booking> getBookings() {
        return bookings;
    }
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}