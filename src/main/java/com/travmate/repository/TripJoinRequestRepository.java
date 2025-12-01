
//package com.travmate.repository;
//
//import com.travmate.model.Trip;
//import com.travmate.model.TripJoinRequest;
//import com.travmate.model.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface TripJoinRequestRepository extends JpaRepository<TripJoinRequest, Long> {
//
//    // ✅ All requests sent by a specific user
//    List<TripJoinRequest> findBySender(User sender);
//
//    // ✅ All requests received by a specific user
//    List<TripJoinRequest> findByReceiver(User receiver);
//
//    // ✅ Check if a pending request exists (boolean)
//    boolean existsByTripAndSenderAndReceiverAndStatus(
//            Trip trip,
//            User sender,
//            User receiver,
//            String status
//    );
//
//    // ✅ Optionally find a specific pending request (object)
//    Optional<TripJoinRequest> findByTripAndSenderAndReceiverAndStatus(
//            Trip trip,
//            User sender,
//            User receiver,
//            String status
//    );
//
//    // ✅ Optional helper: all requests by trip & sender
//    List<TripJoinRequest> findByTripAndSender(Trip trip, User sender);
//}


package com.travmate.repository;

import com.travmate.model.Trip;
import com.travmate.model.TripJoinRequest;
import com.travmate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripJoinRequestRepository extends JpaRepository<TripJoinRequest, Long> {

    // ✅ All requests sent by a specific user
    List<TripJoinRequest> findBySender(User sender);

    // ✅ All requests received by a specific user
    List<TripJoinRequest> findByReceiver(User receiver);

    // ✅ Only pending requests for dashboard
    List<TripJoinRequest> findByReceiverAndStatus(User receiver, String status);

    // ✅ Check if a pending request already exists
    boolean existsByTripAndSenderAndReceiverAndStatus(
            Trip trip,
            User sender,
            User receiver,
            String status
    );

    // ✅ Optionally find a specific pending request
    Optional<TripJoinRequest> findByTripAndSenderAndReceiverAndStatus(
            Trip trip,
            User sender,
            User receiver,
            String status
    );

    // ✅ Optional helper: all requests by trip & sender
    List<TripJoinRequest> findByTripAndSender(Trip trip, User sender);
}
