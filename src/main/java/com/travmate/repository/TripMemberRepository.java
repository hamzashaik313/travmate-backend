package com.travmate.repository;

import com.travmate.model.Trip;
import com.travmate.model.TripMember;
import com.travmate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TripMemberRepository extends JpaRepository<TripMember, Long> {
    List<TripMember> findByTrip(Trip trip);
    Optional<TripMember> findByTripAndUser(Trip trip, User user);
}
