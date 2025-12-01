//package com.travmate.controller;
//
//import com.travmate.model.TripJoinRequest;
//import com.travmate.service.TripJoinRequestService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.*;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/join-requests")
//public class TripJoinRequestController {
//
//    @Autowired private TripJoinRequestService tripJoinRequestService;
//
//    // Send join request
//    @PostMapping("/send/{tripId}")
//    public ResponseEntity<?> sendJoinRequest(@PathVariable Long tripId, Authentication auth) {
//        try {
//            TripJoinRequest request = tripJoinRequestService.sendJoinRequest(tripId, auth.getName());
//            return ResponseEntity.ok(request);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }
//
//    // View incoming requests (for owner)
//    @GetMapping("/received")
//    public ResponseEntity<List<TripJoinRequest>> getReceivedRequests(Authentication auth) {
//        return ResponseEntity.ok(tripJoinRequestService.getRequestsForOwner(auth.getName()));
//    }
//
//    // View sent requests
//    @GetMapping("/sent")
//    public ResponseEntity<List<TripJoinRequest>> getSentRequests(Authentication auth) {
//        return ResponseEntity.ok(tripJoinRequestService.getRequestsForSender(auth.getName()));
//    }
//
//    // Accept or Reject
//    @PutMapping("/{requestId}/{action}")
//    public ResponseEntity<?> handleRequest(
//            @PathVariable Long requestId,
//            @PathVariable String action,
//            Authentication auth
//    ) {
//        try {
//            TripJoinRequest updated = tripJoinRequestService.handleRequest(requestId, auth.getName(), action);
//            return ResponseEntity.ok(updated);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }
//}


//package com.travmate.controller;
//
//import com.travmate.model.TripJoinRequest;
//import com.travmate.service.TripJoinRequestService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/trips/requests")
//public class TripJoinRequestController {
//
//    @Autowired
//    private TripJoinRequestService tripJoinRequestService;
//
//    @PostMapping("/{tripId}")
//    public ResponseEntity<TripJoinRequest> sendRequest(
//            @PathVariable Long tripId,
//            Authentication authentication) {
//        TripJoinRequest req = tripJoinRequestService.sendRequest(tripId, authentication.getName());
//        return ResponseEntity.ok(req);
//    }
//
//    @GetMapping("/incoming")
//    public ResponseEntity<List<TripJoinRequest>> getIncomingRequests(Authentication auth) {
//        String ownerEmail = auth.getName();
//        List<TripJoinRequest> requests = tripJoinRequestService.getIncomingRequests(ownerEmail);
//        return ResponseEntity.ok(requests);
//    }
//
//    @GetMapping("/sent")
//    public ResponseEntity<List<TripJoinRequest>> getSentRequests(Authentication auth) {
//        String senderEmail = auth.getName();
//        List<TripJoinRequest> requests = tripJoinRequestService.getSentRequests(senderEmail);
//        return ResponseEntity.ok(requests);
//    }
//
//    @PostMapping("/{id}/accept")
//    public ResponseEntity<?> acceptRequest(@PathVariable Long id, Authentication auth) {
//        try {
//            String ownerEmail = auth.getName();
//            tripJoinRequestService.updateRequestStatus(id, ownerEmail, "ACCEPTED");
//            return ResponseEntity.ok(
//                    Map.of("message", "Request accepted"));
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
//        }
//    }
//
//    @PostMapping("/{id}/reject")
//    public ResponseEntity<?> rejectRequest(@PathVariable Long id, Authentication auth) {
//        try {
//            String ownerEmail = auth.getName();
//            tripJoinRequestService.updateRequestStatus(id, ownerEmail, "REJECTED");
//            return ResponseEntity.ok(Map.of("message", "Request rejected"));
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
//        }
//    }
//
//}


//package com.travmate.controller;
//
//import com.travmate.model.TripJoinRequest;
//import com.travmate.service.TripJoinRequestService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.security.Principal;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/trips/requests")
//public class TripJoinRequestController {
//
//    private final TripJoinRequestService service;
//
//    public TripJoinRequestController(TripJoinRequestService service) {
//        this.service = service;
//    }
//
//    // SEND REQUEST
//    @PostMapping("/{tripId}")
//    public ResponseEntity<TripJoinRequest> sendRequest(@PathVariable Long tripId, Principal principal) {
//        TripJoinRequest req = service.sendRequest(tripId, principal.getName());
//        return ResponseEntity.ok(req);
//    }
//
//    // OWNER: FETCH INCOMING REQUESTS
//    @GetMapping("/incoming")
//    public ResponseEntity<List<TripJoinRequest>> incoming(Principal principal) {
//        return ResponseEntity.ok(service.getIncomingRequests(principal.getName()));
//    }
//
//    // TRAVELER: FETCH SENT REQUESTS
//    @GetMapping("/sent")
//    public ResponseEntity<List<TripJoinRequest>> sent(Principal principal) {
//        return ResponseEntity.ok(service.getSentRequests(principal.getName()));
//    }
//
//    // ACCEPT
//    @PutMapping("/{id}/accept")
//    public ResponseEntity<TripJoinRequest> accept(@PathVariable Long id, Principal principal) {
//        return ResponseEntity.ok(service.acceptRequest(id, principal.getName()));
//    }
//
//    // REJECT
//    @PutMapping("/{id}/reject")
//    public ResponseEntity<TripJoinRequest> reject(@PathVariable Long id, Principal principal) {
//        return ResponseEntity.ok(service.rejectRequest(id, principal.getName()));
//    }
//}

package com.travmate.controller;

import com.travmate.model.TripJoinRequest;
import com.travmate.service.TripJoinRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/trips/requests")
public class TripJoinRequestController {

    private final TripJoinRequestService service;

    public TripJoinRequestController(TripJoinRequestService service) {
        this.service = service;
    }

    @PostMapping("/{tripId}")
    public ResponseEntity<TripJoinRequest> sendRequest(@PathVariable Long tripId, Principal principal) {
        return ResponseEntity.ok(service.sendRequest(tripId, principal.getName()));
    }

    @GetMapping("/incoming")
    public ResponseEntity<List<TripJoinRequest>> incoming(Principal principal) {
        return ResponseEntity.ok(service.getIncomingRequests(principal.getName()));
    }

    @GetMapping("/sent")
    public ResponseEntity<List<TripJoinRequest>> sent(Principal principal) {
        return ResponseEntity.ok(service.getSentRequests(principal.getName()));
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<TripJoinRequest> accept(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(service.acceptRequest(id, principal.getName()));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<TripJoinRequest> reject(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(service.rejectRequest(id, principal.getName()));
    }
}

