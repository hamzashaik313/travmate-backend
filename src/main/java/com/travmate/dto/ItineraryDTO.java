package com.travmate.dto;

public class ItineraryDTO {
    public Integer dayNumber;
    public String activity;

    public ItineraryDTO(Integer dayNumber, String activity) {
        this.dayNumber = dayNumber;
        this.activity = activity;
    }
}
