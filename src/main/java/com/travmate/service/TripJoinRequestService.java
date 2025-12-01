//package com.travmate.service;
//
//import com.travmate.model.Trip;
//import com.travmate.model.TripJoinRequest;
//import com.travmate.model.User;
//import com.travmate.repository.TripJoinRequestRepository;
//import com.travmate.repository.TripRepository;
//import com.travmate.repository.UserRepository;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class TripJoinRequestService {
//
//    private final TripJoinRequestRepository requestRepo;
//    private final TripRepository tripRepo;
//    private final UserRepository userRepo;
//
//    public TripJoinRequestService(TripJoinRequestRepository requestRepo,
//                                  TripRepository tripRepo,
//                                  UserRepository userRepo) {
//        this.requestRepo = requestRepo;
//        this.tripRepo = tripRepo;
//        this.userRepo = userRepo;
//    }
//
//    @Transactional
//    public TripJoinRequest sendRequest(Long tripId, String senderEmail) {
//        Trip trip = tripRepo.findById(tripId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip not found"));
//
//        User sender = userRepo.findByEmail(senderEmail)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender not found"));
//
//        User receiver = trip.getOwner();
//        if (receiver == null)
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trip owner not found");
//
//        if (sender.getEmail().equals(receiver.getEmail()))
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot send a request to your own trip");
//
//        Optional<TripJoinRequest> existing = requestRepo
//                .findByTripAndSenderAndReceiverAndStatus(trip, sender, receiver, "PENDING")
//                .stream()
//                .findFirst();
//
//        if (existing.isPresent()) {
//            return existing.get(); // ✅ silently return same pending request
//        }
//
//        TripJoinRequest req = new TripJoinRequest();
//        req.setTrip(trip);
//        req.setSender(sender);
//        req.setReceiver(receiver);
//        req.setStatus("PENDING");
//
//        return requestRepo.save(req);
//    }
//
//    public List<TripJoinRequest> getIncomingRequests(String ownerEmail) {
//        User owner = userRepo.findByEmail(ownerEmail)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found"));
//        return requestRepo.findByReceiver(owner);
//    }
//
//    public List<TripJoinRequest> getSentRequests(String senderEmail) {
//        User sender = userRepo.findByEmail(senderEmail)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender not found"));
//        return requestRepo.findBySender(sender);
//    }
//
//    @Transactional
//    public TripJoinRequest acceptRequest(Long id, String ownerEmail) {
//        TripJoinRequest req = requestRepo.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found"));
//
//        if (!req.getReceiver().getEmail().equals(ownerEmail))
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
//
//        req.setStatus("ACCEPTED");
//
//        Trip trip = req.getTrip();
//        if (!trip.getMembers().contains(req.getSender())) {
//            trip.getMembers().add(req.getSender());
//            tripRepo.save(trip);
//        }
//
//        return requestRepo.save(req);
//    }
//
//    @Transactional
//    public TripJoinRequest rejectRequest(Long id, String ownerEmail) {
//        TripJoinRequest req = requestRepo.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found"));
//
//        if (!req.getReceiver().getEmail().equals(ownerEmail))
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip not found"));

        User sender = userRepo.findByEmail(senderEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender not found"));

        User receiver = trip.getOwner();
        if (receiver == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trip owner not found");

        if (sender.getEmail().equals(receiver.getEmail()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot send a request to your own trip");

        // ✅ Check if pending request already exists
        Optional<TripJoinRequest> existing = requestRepo
                .findByTripAndSenderAndReceiverAndStatus(trip, sender, receiver, "PENDING")
                .stream()
                .findFirst();

        if (existing.isPresent()) {
            return existing.get(); // return same pending request
        }

        TripJoinRequest req = new TripJoinRequest();
        req.setTrip(trip);
        req.setSender(sender);
        req.setReceiver(receiver);
        req.setStatus("PENDING");

        return requestRepo.save(req);
    }

    // ✅ Fetch only PENDING incoming requests (for dashboard)
    public List<TripJoinRequest> getIncomingRequests(String ownerEmail) {
        User owner = userRepo.findByEmail(ownerEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found"));
        return requestRepo.findByReceiverAndStatus(owner, "PENDING");
    }

    // ✅ Fetch all sent requests by current user
    public List<TripJoinRequest> getSentRequests(String senderEmail) {
        User sender = userRepo.findByEmail(senderEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender not found"));
        return requestRepo.findBySender(sender);
    }

    @Transactional
    public TripJoinRequest acceptRequest(Long id, String ownerEmail) {
        TripJoinRequest req = requestRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found"));

        if (!req.getReceiver().getEmail().equals(ownerEmail))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");

        req.setStatus("ACCEPTED");

        Trip trip = req.getTrip();
        if (!trip.getMembers().contains(req.getSender())) {
            trip.getMembers().add(req.getSender());
            tripRepo.save(trip);
        }

        return requestRepo.save(req);
    }

    @Transactional
    public TripJoinRequest rejectRequest(Long id, String ownerEmail) {
        TripJoinRequest req = requestRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found"));

        if (!req.getReceiver().getEmail().equals(ownerEmail))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");

        req.setStatus("REJECTED");
        return requestRepo.save(req);
    }
}
