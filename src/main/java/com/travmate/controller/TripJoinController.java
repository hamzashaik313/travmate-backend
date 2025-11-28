package com.travmate.controller;

import com.travmate.model.Trip;
import com.travmate.model.TripJoinRequest;
import com.travmate.model.User;
import com.travmate.repository.TripJoinRequestRepository;
import com.travmate.repository.TripRepository;
import com.travmate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/trip-join")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://travmate-frontend.vercel.app",
        "https://travmate.vercel.app"
})
public class TripJoinController {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TripJoinRequestRepository joinRequestRepository;

    /**
     * ✅ Send a join request to a trip owner.
     */
    @PostMapping("/send/{tripId}")
    public ResponseEntity<?> sendJoinRequest(@PathVariable Long tripId, Principal principal) {
        User sender = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        User receiver = trip.getOwner();

        if (sender.getId().equals(receiver.getId())) {
            return ResponseEntity.badRequest().body("You cannot send a request to your own trip!");
        }

        // Avoid duplicate requests
        boolean exists = joinRequestRepository.existsByTripAndSenderAndReceiverAndStatus(
                trip, sender, receiver, "PENDING");
        if (exists) {
            return ResponseEntity.badRequest().body("Request already sent!");
        }

        TripJoinRequest request = new TripJoinRequest();
        request.setTrip(trip);
        request.setSender(sender);
        request.setReceiver(receiver);
        request.setStatus("PENDING");

        joinRequestRepository.save(request);
        return ResponseEntity.ok("Join request sent successfully!");
    }

    /**
     * ✅ Get all join requests received by the current user.
     */
    @GetMapping("/received")
    public ResponseEntity<List<TripJoinRequest>> getReceivedRequests(Principal principal) {
        User receiver = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<TripJoinRequest> requests = joinRequestRepository.findByReceiver(receiver);
        return ResponseEntity.ok(requests);
    }

    /**
     * ✅ Respond to a join request (accept or reject).
     */
    @PutMapping("/respond/{requestId}")
    public ResponseEntity<?> respondToRequest(
            @PathVariable Long requestId,
            @RequestParam String status,
            Principal principal) {

        TripJoinRequest request = joinRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (!request.getReceiver().getEmail().equals(principal.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You cannot respond to someone else's requests!");
        }

        if (!status.equalsIgnoreCase("ACCEPTED") && !status.equalsIgnoreCase("REJECTED")) {
            return ResponseEntity.badRequest().body("Invalid status!");
        }

        request.setStatus(status.toUpperCase());
        joinRequestRepository.save(request);

        return ResponseEntity.ok("Request " + status.toLowerCase() + " successfully!");
    }
}
