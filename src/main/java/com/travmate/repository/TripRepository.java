//package com.travmate.repository;
//
//import com.travmate.model.Trip;
//import com.travmate.model.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import java.util.List;
//
//public interface    TripRepository extends JpaRepository<Trip, Long> {
//    List<Trip> findByCreatedBy(User user);
//    List<Trip> findAllByCreatedById(Long userId);
//}

//after deoloeyemt
package com.travmate.repository;

import com.travmate.model.Trip;
import com.travmate.model.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip, Long> {

    // ✅ Existing methods (keep them as is)
    @Query("""
           select distinct t
           from Trip t
           left join fetch t.itineraries i
           where t.owner = :owner
           """)
    List<Trip> findAllByOwnerWithItineraries(@Param("owner") User owner);

    @Query("""
           select t
           from Trip t
           left join fetch t.owner o
           left join fetch t.itineraries i
           where t.id = :id
           """)
    Optional<Trip> findDetailedById(@Param("id") Long id);

    // ✅ New method: discoverable trips (public, not owned by current user)
    @Query("""
           select t
           from Trip t
           join fetch t.owner o
           where t.visibility = 'PUBLIC'
           and t.owner <> :currentUser
           """)
    List<Trip> findDiscoverableTrips(@Param("currentUser") User currentUser);

    // ✅ New method: match trips based on overlapping dates + same destination
    @Query("""
           select t
           from Trip t
           join fetch t.owner o
           where t.visibility = 'PUBLIC'
           and lower(t.destination) like lower(concat('%', :destination, '%'))
           and t.startDate <= :endDate
           and t.endDate >= :startDate
           and t.owner <> :currentUser
           """)
    List<Trip> findMatchingTrips(@Param("destination") String destination,
                                 @Param("startDate") LocalDate startDate,
                                 @Param("endDate") LocalDate endDate,
                                 @Param("currentUser") User currentUser);
}
