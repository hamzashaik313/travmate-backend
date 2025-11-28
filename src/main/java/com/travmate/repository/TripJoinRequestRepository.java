package com.travmate.repository;

import com.travmate.model.Trip;
import com.travmate.model.TripJoinRequest;
import com.travmate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripJoinRequestRepository extends JpaRepository<TripJoinRequest, Long> {
    List<TripJoinRequest> findByReceiver(User receiver);

    boolean existsByTripAndSenderAndReceiverAndStatus(Trip trip, User sender, User receiver, String status);
}
