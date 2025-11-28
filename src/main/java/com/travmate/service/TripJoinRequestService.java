//package com.travmate.service;
//
//import com.travmate.model.*;
//import com.travmate.repository.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class TripJoinRequestService {
//
//    @Autowired private TripRepository tripRepository;
//    @Autowired private TripJoinRequestRepository joinRequestRepository;
//    @Autowired private UserRepository userRepository;
//    @Autowired private TripMemberRepository tripMemberRepository;
//
//    // Send request
//    public TripJoinRequest sendJoinRequest(Long tripId, String senderEmail) {
//        Trip trip = tripRepository.findById(tripId)
//                .orElseThrow(() -> new RuntimeException("Trip not found"));
//
//        User sender = userRepository.findByEmail(senderEmail)
//                .orElseThrow(() -> new RuntimeException("Sender not found"));
//
//        User owner = trip.getOwner();
//        if (owner == null) throw new RuntimeException("Trip owner not found");
//
//        if (!owner.isAllowTripRequests()) {
//            throw new RuntimeException("Trip owner is not accepting join requests.");
//        }
//
//        if (joinRequestRepository.findByTripAndSender(trip, sender).isPresent()) {
//            throw new RuntimeException("You already sent a request for this trip.");
//        }
//
//        TripJoinRequest request = new TripJoinRequest();
//        request.setTrip(trip);
//        request.setSender(sender);
//        request.setReceiver(owner);
//        request.setStatus("PENDING");
//        return joinRequestRepository.save(request);
//    }
//
//    // Owner: view incoming requests
//    public List<TripJoinRequest> getRequestsForOwner(String ownerEmail) {
//        User owner = userRepository.findByEmail(ownerEmail)
//                .orElseThrow(() -> new RuntimeException("Owner not found"));
//        return joinRequestRepository.findByReceiver(owner);
//    }
//
//    // Sender: view sent requests
//    public List<TripJoinRequest> getRequestsForSender(String senderEmail) {
//        User sender = userRepository.findByEmail(senderEmail)
//                .orElseThrow(() -> new RuntimeException("Sender not found"));
//        return joinRequestRepository.findBySender(sender);
//    }
//
//    // Accept/Reject
//    public TripJoinRequest handleRequest(Long requestId, String ownerEmail, String action) {
//        TripJoinRequest request = joinRequestRepository.findById(requestId)
//                .orElseThrow(() -> new RuntimeException("Request not found"));
//
//        if (!request.getReceiver().getEmail().equals(ownerEmail)) {
//            throw new RuntimeException("You are not authorized to handle this request.");
//        }
//
//        if (action.equalsIgnoreCase("accept")) {
//            request.setStatus("ACCEPTED");
//
//            TripMember member = new TripMember(request.getTrip(), request.getSender(), "COLLABORATOR");
//            tripMemberRepository.save(member);
//
//        } else if (action.equalsIgnoreCase("reject")) {
//            request.setStatus("REJECTED");
//        } else {
//            throw new RuntimeException("Invalid action");
//        }
//
//        return joinRequestRepository.save(request);
//    }
//}

//package com.travmate.service;
//
//import com.travmate.model.Trip;
//import com.travmate.model.TripJoinRequest;
//import com.travmate.model.User;
//import com.travmate.repository.TripJoinRequestRepository;
//import com.travmate.repository.TripRepository;
//import com.travmate.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.util.List;
//
//@Service
//public class TripJoinRequestService {
//
//    @Autowired
//    private TripJoinRequestRepository requestRepo;
//
//    @Autowired
//    private TripRepository tripRepo;
//
//    @Autowired
//    private UserRepository userRepo;
//
//    public TripJoinRequest sendRequest(Long tripId, String senderEmail) {
//        Trip trip = tripRepo.findById(tripId)
//                .orElseThrow(() -> new RuntimeException("Trip not found"));
//
//        User sender = userRepo.findByEmail(senderEmail)
//                .orElseThrow(() -> new RuntimeException("Sender not found"));
//
//        User receiver = trip.getOwner();
//
//        if (sender.getEmail().equals(receiver.getEmail())) {
//            throw new RuntimeException("You cannot send a request to your own trip");
//        }
//
//        boolean exists = requestRepo.existsByTripAndSenderAndReceiverAndStatus(trip, sender, receiver, "PENDING");
//        if (exists) {
//            throw new RuntimeException("A pending request already exists for this trip.");
//        }
//
//        TripJoinRequest request = new TripJoinRequest();
//        request.setTrip(trip);
//        request.setSender(sender);
//        request.setReceiver(receiver);
//        request.setStatus("PENDING");
//
//        return requestRepo.save(request);
//    }
//
//    public List<TripJoinRequest> getIncomingRequests(String ownerEmail) {
//        User owner = userRepository.findByEmail(ownerEmail)
//                .orElseThrow(() -> new RuntimeException("Owner not found"));
//        return tripJoinRequestRepository.findByReceiver(owner);
//    }
//
//    public List<TripJoinRequest> getSentRequests(String senderEmail) {
//        User sender = userRepository.findByEmail(senderEmail)
//                .orElseThrow(() -> new RuntimeException("Sender not found"));
//        return tripJoinRequestRepository.findBySender(sender);
//    }
//
//    public void updateRequestStatus(Long requestId, String ownerEmail, String status) {
//        TripJoinRequest request = tripJoinRequestRepository.findById(requestId)
//                .orElseThrow(() -> new RuntimeException("Request not found"));
//
//        if (!request.getReceiver().getEmail().equals(ownerEmail)) {
//            throw new RuntimeException("You are not the owner of this trip");
//        }
//
//        request.setStatus(status);
//        tripJoinRequestRepository.save(request);
//    }
//
//
//    public TripJoinRequest acceptRequest(Long id, String ownerEmail) {
//        TripJoinRequest req = requestRepo.findById(id)
//                .orElseThrow(() -> new RuntimeException("Request not found"));
//
//        if (!req.getReceiver().getEmail().equals(ownerEmail)) {
//            throw new RuntimeException("Unauthorized");
//        }
//
//        req.setStatus("ACCEPTED");
//        return requestRepo.save(req);
//    }
//
//    public TripJoinRequest rejectRequest(Long id, String ownerEmail) {
//        TripJoinRequest req = requestRepo.findById(id)
//                .orElseThrow(() -> new RuntimeException("Request not found"));
//
//        if (!req.getReceiver().getEmail().equals(ownerEmail)) {
//            throw new RuntimeException("Unauthorized");
//        }
//
//        req.setStatus("REJECTED");
//        return requestRepo.save(req);
//    }
//}


package com.travmate.service;

import com.travmate.model.Trip;
import com.travmate.model.TripJoinRequest;
import com.travmate.model.User;
import com.travmate.repository.TripJoinRequestRepository;
import com.travmate.repository.TripRepository;
import com.travmate.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TripJoinRequestService {

    private final TripJoinRequestRepository requestRepo;
    private final TripRepository tripRepo;
    private final UserRepository userRepo;

    public TripJoinRequestService(TripJoinRequestRepository requestRepo,
                                  TripRepository tripRepo,
                                  UserRepository userRepo) {
        this.requestRepo = requestRepo;
        this.tripRepo = tripRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public TripJoinRequest sendRequest(Long tripId, String senderEmail) {
        Trip trip = tripRepo.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        User sender = userRepo.findByEmail(senderEmail)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        User receiver = trip.getOwner();
        if (receiver == null) throw new RuntimeException("Trip has no owner");

        if (sender.getEmail().equals(receiver.getEmail())) {
            throw new RuntimeException("You cannot send a request to your own trip");
        }

        boolean exists = requestRepo.existsByTripAndSenderAndReceiverAndStatus(
                trip, sender, receiver, "PENDING"
        );
        if (exists) throw new RuntimeException("A pending request already exists for this trip.");

        TripJoinRequest req = new TripJoinRequest();
        req.setTrip(trip);
        req.setSender(sender);
        req.setReceiver(receiver);
        req.setStatus("PENDING");
        return requestRepo.save(req);
    }

    @Transactional(readOnly = true)
    public List<TripJoinRequest> getIncomingRequests(String ownerEmail) {
        User owner = userRepo.findByEmail(ownerEmail)
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        return requestRepo.findByReceiver(owner);
    }

    @Transactional(readOnly = true)
    public List<TripJoinRequest> getSentRequests(String senderEmail) {
        User sender = userRepo.findByEmail(senderEmail)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        return requestRepo.findBySender(sender);
    }

    @Transactional
    public void updateRequestStatus(Long requestId, String ownerEmail, String status) {
        TripJoinRequest req = requestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (!req.getReceiver().getEmail().equals(ownerEmail)) {
            throw new RuntimeException("You are not the owner of this trip");
        }

        req.setStatus(status);
        requestRepo.save(req);
    }

    @Transactional
    public TripJoinRequest acceptRequest(Long id, String ownerEmail) {
        TripJoinRequest req = requestRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        if (!req.getReceiver().getEmail().equals(ownerEmail)) throw new RuntimeException("Unauthorized");
        req.setStatus("ACCEPTED");
        return requestRepo.save(req);
    }

    @Transactional
    public TripJoinRequest rejectRequest(Long id, String ownerEmail) {
        TripJoinRequest req = requestRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        if (!req.getReceiver().getEmail().equals(ownerEmail)) throw new RuntimeException("Unauthorized");
        req.setStatus("REJECTED");
        return requestRepo.save(req);
    }
}
