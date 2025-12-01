//package com.travmate.dto;
//
//import java.time.LocalDate;
//import java.util.List;
//
//public class TripDTO {
//    public Long id;
//    public String title;
//    public String destination;
//    public LocalDate startDate;
//    public LocalDate endDate;
//    public Double budget;
//    public String ownerName;
//    public List<ItineraryDTO> itineraries;
//
//    public TripDTO(Long id, String title, String destination,
//                   LocalDate startDate, LocalDate endDate,
//                   Double budget, String ownerName,
//                   List<ItineraryDTO> itineraries) {
//        this.id = id;
//        this.title = title;
//        this.destination = destination;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.budget = budget;
//        this.ownerName = ownerName;
//        this.itineraries = itineraries;
//    }
//}



//package com.travmate.dto;
//
//import java.time.LocalDate;
//import java.util.List;
//
//public class TripDTO {
//    private Long id;
//    private String title;
//    private String destination;
//    private LocalDate startDate;
//    private LocalDate endDate;
//    private Double budget;
//    private String ownerName;
//    private List<ItineraryDTO> itineraries;
//
//    public TripDTO() {
//    }
//
//    public TripDTO(Long id, String title, String destination,
//                   LocalDate startDate, LocalDate endDate,
//                   Double budget, String ownerName,
//                   List<ItineraryDTO> itineraries) {
//        this.id = id;
//        this.title = title;
//        this.destination = destination;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.budget = budget;
//        this.ownerName = ownerName;
//        this.itineraries = itineraries;
//    }
//
//    // ✅ Getters & Setters (optional but recommended for Jackson)
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//
//    public String getTitle() { return title; }
//    public void setTitle(String title) { this.title = title; }
//
//    public String getDestination() { return destination; }
//    public void setDestination(String destination) { this.destination = destination; }
//
//    public LocalDate getStartDate() { return startDate; }
//    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
//
//    public LocalDate getEndDate() { return endDate; }
//    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
//
//    public Double getBudget() { return budget; }
//    public void setBudget(Double budget) { this.budget = budget; }
//
//    public String getOwnerName() { return ownerName; }
//    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
//
//    public List<ItineraryDTO> getItineraries() { return itineraries; }
//    public void setItineraries(List<ItineraryDTO> itineraries) { this.itineraries = itineraries; }
//}
//


package com.travmate.dto;

import java.time.LocalDate;
import java.util.List;

public class TripDTO {

    private Long id;
    private String title;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double budget;
    private String heroImageUrl;
    private String ownerName;
    private String ownerEmail;
    private String ownerPhotoUrl;
    private List<ItineraryDTO> itineraries;
    private Long ownerId;
    private String visibility; // PUBLIC or PRIVATE




    // ✅ Empty constructor
    public TripDTO() {}


    // ✅ All-fields constructor
    public TripDTO(Long id, String title, String destination,
                   LocalDate startDate, LocalDate endDate,
                   Double budget, String heroImageUrl,
                   String ownerName, String ownerEmail,
                   List<ItineraryDTO> itineraries) {
        this.id = id;
        this.title = title;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.heroImageUrl = heroImageUrl;
        this.ownerName = ownerName;
        this.ownerEmail = ownerEmail;
        this.itineraries = itineraries;
    }

    // ✅ Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public String getHeroImageUrl() {
        return heroImageUrl;
    }

    public void setHeroImageUrl(String heroImageUrl) {
        this.heroImageUrl = heroImageUrl;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getOwnerPhotoUrl() {
        return ownerPhotoUrl;
    }

    public void setOwnerPhotoUrl(String ownerPhotoUrl) {
        this.ownerPhotoUrl = ownerPhotoUrl;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }


    public List<ItineraryDTO> getItineraries() {
        return itineraries;
    }

    public void setItineraries(List<ItineraryDTO> itineraries) {
        this.itineraries = itineraries;
    }
}

