//package com.travmate.repository;
//
//import com.travmate.model.Trip;
//import com.travmate.model.TripJoinRequest;
//import com.travmate.model.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface TripJoinRequestRepository extends JpaRepository<TripJoinRequest, Long> {
//    List<TripJoinRequest> findByReceiver(User receiver);
//
//    boolean existsByTripAndSenderAndReceiverAndStatus(Trip trip, User sender, User receiver, String status);
//}


//package com.travmate.repository;
//
//import com.travmate.model.TripJoinRequest;
//import com.travmate.model.Trip;
//import com.travmate.model.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import java.util.List;
//import java.util.Optional;
//
//public interface TripJoinRequestRepository extends JpaRepository<TripJoinRequest, Long> {
//    List<TripJoinRequest> findByReceiver(User receiver);
//    List<TripJoinRequest> findBySender(User sender);
//    List<TripJoinRequest> findByTrip(Trip trip);
//    Optional<TripJoinRequest> findByTripAndSender(Trip trip, User sender);
//    boolean existsByTripAndSenderAndReceiverAndStatus(
//            Trip trip,
//            User sender,
//            User receiver,
//            String status
//    );
//
//
//    //boolean existsByTripAndSenderAndReceiverAndStatus(Trip trip, User sender, User receiver, String pending);
//}


package com.travmate.repository;

import com.travmate.model.Trip;
import com.travmate.model.TripJoinRequest;
import com.travmate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TripJoinRequestRepository extends JpaRepository<TripJoinRequest, Long> {
    boolean existsByTripAndSenderAndReceiverAndStatus(Trip trip, User sender, User receiver, String status);
    List<TripJoinRequest> findByReceiver(User receiver);
    List<TripJoinRequest> findBySender(User sender);
    Optional<TripJoinRequest> findByTripAndSender(Trip trip, User sender);


}
