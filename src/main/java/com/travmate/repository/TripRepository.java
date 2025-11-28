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

import java.util.List;
import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip, Long> {

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
}

