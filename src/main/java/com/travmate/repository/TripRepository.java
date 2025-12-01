


package com.travmate.repository;

import com.travmate.model.Trip;
import com.travmate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip, Long> {

    // ✅ Get all trips by owner
    List<Trip> findAllByOwner(User owner);

    // ✅ For DTO loading (fetch itineraries)
    @Query("SELECT DISTINCT t FROM Trip t LEFT JOIN FETCH t.itineraries WHERE t.owner = :owner")
    List<Trip> findAllByOwnerWithItineraries(@Param("owner") User owner);

    // ✅ For detailed trip view
    @Query("SELECT t FROM Trip t LEFT JOIN FETCH t.itineraries LEFT JOIN FETCH t.members WHERE t.id = :id")
    Optional<Trip> findDetailedById(@Param("id") Long id);

    // ✅ Discover public trips (excluding own trips)
    @Query("SELECT t FROM Trip t WHERE t.visibility = 'PUBLIC' AND t.owner <> :user")
    List<Trip> findDiscoverableTrips(@Param("user") User currentUser);

    // ✅ FIXED: Match trips by overlapping dates & same destination (excluding same owner)
    @Query("""
        SELECT t FROM Trip t
        WHERE t.visibility = 'PUBLIC'
          AND LOWER(t.destination) = LOWER(:destination)
          AND t.owner.id <> :ownerId
          AND (
              (t.startDate <= :endDate AND t.endDate >= :startDate)
          )
    """)
    List<Trip> findMatchingTrips(
            @Param("destination") String destination,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("ownerId") Long ownerId
    );
}
