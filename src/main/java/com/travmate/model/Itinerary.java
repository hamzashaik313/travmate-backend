package com.travmate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "itinerary")
public class Itinerary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer dayNumber;

    private String activity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    @JsonIgnore
    private Trip trip;

    public Itinerary() {}

    public Itinerary(Integer dayNumber, String activity, Trip trip) {
        this.dayNumber = dayNumber;
        this.activity = activity;
        this.trip = trip;
    }

    // getters/setters
    public Long getId() { return id; }
    public Integer getDayNumber() { return dayNumber; }
    public void setDayNumber(Integer dayNumber) { this.dayNumber = dayNumber; }
    public String getActivity() { return activity; }
    public void setActivity(String activity) { this.activity = activity; }
    public Trip getTrip() { return trip; }
    public void setTrip(Trip trip) { this.trip = trip; }
}

