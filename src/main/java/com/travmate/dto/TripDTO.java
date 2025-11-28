package com.travmate.dto;

import java.time.LocalDate;
import java.util.List;

public class TripDTO {
    public Long id;
    public String title;
    public String destination;
    public LocalDate startDate;
    public LocalDate endDate;
    public Double budget;
    public String ownerName;
    public List<ItineraryDTO> itineraries;

    public TripDTO(Long id, String title, String destination,
                   LocalDate startDate, LocalDate endDate,
                   Double budget, String ownerName,
                   List<ItineraryDTO> itineraries) {
        this.id = id;
        this.title = title;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.ownerName = ownerName;
        this.itineraries = itineraries;
    }
}
