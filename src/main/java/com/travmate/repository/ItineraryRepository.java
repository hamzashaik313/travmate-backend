package com.travmate.repository;

import com.travmate.model.Itinerary;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Itinerary i WHERE i.trip.id = :tripId")
    void deleteAllByTripId(Long tripId);

    List<Itinerary> findByTripId(Long tripId);
}
