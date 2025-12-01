//
//package com.travmate.controller;
//
//import com.travmate.model.Trip;
//import com.travmate.service.TripService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/trips")
//public class TripController {
//
//    @Autowired
//    private TripService tripService;
//
//    @PostMapping
//    public ResponseEntity<Trip> createTrip(@RequestBody Trip trip, Authentication authentication) {
//        return ResponseEntity.ok(tripService.createTrip(trip, authentication.getName()));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Trip> getTripById(@PathVariable Long id) {
//        // NOTE: For full security, this method should ideally also take the Authentication object
//        // and verify that the logged-in user owns the trip before returning it.
//        return tripService.getTripById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Trip>> getAllTrips(Authentication authentication) {
//        // Now calls the new, secure method
//        return ResponseEntity.ok(tripService.getAllTripsByCreator(authentication.getName()));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Trip> updateTrip(@PathVariable Long id,
//                                           @RequestBody Trip trip,
//                                           Authentication authentication) {
//        // Already secure: Passes user email to service layer for ownership check
//        return ResponseEntity.ok(tripService.updateTrip(id, trip, authentication.getName()));
//    }
//
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteTrip(@PathVariable Long id, Authentication authentication) {
//        // Already secure: Passes user email to service layer for ownership check
//        tripService.deleteTrip(id, authentication.getName());
//        return ResponseEntity.noContent().build();
//    }
//}

//
//package com.travmate.controller;
//
//import com.travmate.dto.TripDTO;
//import com.travmate.model.Trip;
//import com.travmate.model.User;
//import com.travmate.repository.TripRepository;
//import com.travmate.service.TripService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Set;
//
//@RestController
//@RequestMapping("/api/trips")
//public class TripController {
//
//    @Autowired private TripService tripService;
//
//    @PostMapping
//    public ResponseEntity<Trip> createTrip(@RequestBody Trip trip, Authentication authentication) {
//        return ResponseEntity.ok(tripService.createTrip(trip, authentication.getName()));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<TripDTO> getTripById(@PathVariable Long id) {
//        return ResponseEntity.ok(tripService.getTripByIdDTO(id));
//    }
//
//    @GetMapping
//    public ResponseEntity<List<TripDTO>> getAllTrips(Authentication authentication) {
//        return ResponseEntity.ok(tripService.getAllTripsByCreator(authentication.getName()));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Trip> updateTrip(@PathVariable Long id,
//                                           @RequestBody Trip trip,
//                                           Authentication authentication) {
//        return ResponseEntity.ok(tripService.updateTrip(id, trip, authentication.getName()));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteTrip(@PathVariable Long id, Authentication authentication) {
//        tripService.deleteTrip(id, authentication.getName());
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping("/discover")
//    public ResponseEntity<?> discoverTrips(Authentication authentication) {
//        try {
//            if (authentication == null || authentication.getName() == null) {
//                return ResponseEntity.status(401).body("Unauthorized: Missing authentication info");
//            }
//
//            System.out.println("🧭 Discover request by: " + authentication.getName());
//
//            return ResponseEntity.ok(tripService.discoverTrips(authentication.getName()));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @Autowired
//    private TripRepository tripRepo;
//
//    @GetMapping("/{tripId}/members")
//    public ResponseEntity<Set<User>> getTripMembers(@PathVariable Long tripId) {
//        Trip trip = tripRepo.findById(tripId)
//                .orElseThrow(() -> new RuntimeException("Trip not found"));
//        return ResponseEntity.ok(trip.getMembers());
//    }
//
//}



//package com.travmate.controller;
//
//import com.travmate.dto.TripDTO;
//import com.travmate.model.Trip;
//import com.travmate.model.User;
//import com.travmate.repository.TripRepository;
//import com.travmate.service.TripService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Set;
//
//@RestController
//@RequestMapping("/api/trips")
//@CrossOrigin(origins = {
//        "http://localhost:3000",
//        "https://travmate-frontend.vercel.app",
//        "https://travmate.vercel.app"
//})
//public class TripController {
//
//    @Autowired
//    private TripService tripService;
//
//    @Autowired
//    private TripRepository tripRepo;
//
//    // ✅ 1️⃣ CREATE TRIP
//    @PostMapping
//    public ResponseEntity<Trip> createTrip(@RequestBody Trip trip, Authentication authentication) {
//        return ResponseEntity.ok(tripService.createTrip(trip, authentication.getName()));
//    }
//
//    // ✅ 2️⃣ GET TRIP BY ID (for detailed view)
//    @GetMapping("/{id}")
//    public ResponseEntity<TripDTO> getTripById(@PathVariable Long id) {
//        return ResponseEntity.ok(tripService.getTripByIdDTO(id));
//    }
//
//    // ✅ 3️⃣ GET ALL TRIPS BY CURRENT USER
//    @GetMapping
//    public ResponseEntity<List<TripDTO>> getAllTrips(Authentication authentication) {
//        return ResponseEntity.ok(tripService.getAllTripsByCreator(authentication.getName()));
//    }
//
//    // ✅ 4️⃣ UPDATE TRIP
//    @PutMapping("/{id}")
//    public ResponseEntity<Trip> updateTrip(@PathVariable Long id,
//                                           @RequestBody Trip trip,
//                                           Authentication authentication) {
//        return ResponseEntity.ok(tripService.updateTrip(id, trip, authentication.getName()));
//    }
//
//    // ✅ 5️⃣ DELETE TRIP
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteTrip(@PathVariable Long id, Authentication authentication) {
//        tripService.deleteTrip(id, authentication.getName());
//        return ResponseEntity.noContent().build();
//    }
//
//    // ✅ 6️⃣ DISCOVER TRIPS (Public Trips not owned by current user)
//    @GetMapping("/discover")
//    public ResponseEntity<?> discoverTrips(Authentication authentication) {
//        try {
//            if (authentication == null || authentication.getName() == null) {
//                return ResponseEntity.status(401).body("Unauthorized: Missing authentication info");
//            }
//
//            System.out.println("🧭 Discover request by: " + authentication.getName());
//            return ResponseEntity.ok(tripService.discoverTrips(authentication.getName()));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    // ✅ 7️⃣ MATCHING TRIPS (Smart Overlap Feature)
//    @GetMapping("/matching")
//    public ResponseEntity<?> getMatchingTrips(
//            @RequestParam String destination,
//            @RequestParam String startDate,
//            @RequestParam String endDate,
//            Authentication authentication) {
//        try {
//            if (authentication == null || authentication.getName() == null) {
//                return ResponseEntity.status(401).body("Unauthorized: Missing authentication info");
//            }
//
//            System.out.println("🔍 Matching trips request by: " + authentication.getName());
//
//            List<TripDTO> matches = tripService.findMatchingTrips(
//                    authentication.getName(),
//                    destination,
//                    startDate,
//                    endDate
//            );
//
//            if (matches.isEmpty()) {
//                return ResponseEntity.ok(List.of()); // return empty list if no matches
//            }
//
//            return ResponseEntity.ok(matches);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    // ✅ 8️⃣ GET TRIP MEMBERS
//    @GetMapping("/{tripId}/members")
//    public ResponseEntity<Set<User>> getTripMembers(@PathVariable Long tripId) {
//        Trip trip = tripRepo.findById(tripId)
//                .orElseThrow(() -> new RuntimeException("Trip not found"));
//        return ResponseEntity.ok(trip.getMembers());
//    }
//}

//package com.travmate.controller;
//
//import com.travmate.dto.TripDTO;
//import com.travmate.model.Trip;
//import com.travmate.model.User;
//import com.travmate.repository.TripRepository;
//import com.travmate.service.TripService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Set;
//
//@RestController
//@RequestMapping("/api/trips")
//public class TripController {
//
//    @Autowired private TripService tripService;
//    @Autowired private TripRepository tripRepo;
//
//    @PostMapping
//    public ResponseEntity<Trip> createTrip(@RequestBody Trip trip, Authentication authentication) {
//        return ResponseEntity.ok(tripService.createTrip(trip, authentication.getName()));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<TripDTO> getTripById(@PathVariable Long id) {
//        return ResponseEntity.ok(tripService.getTripByIdDTO(id));
//    }
//
//    @GetMapping
//    public ResponseEntity<List<TripDTO>> getAllTrips(Authentication authentication) {
//        return ResponseEntity.ok(tripService.getAllTripsByCreator(authentication.getName()));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Trip> updateTrip(@PathVariable Long id,
//                                           @RequestBody Trip trip,
//                                           Authentication authentication) {
//        return ResponseEntity.ok(tripService.updateTrip(id, trip, authentication.getName()));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteTrip(@PathVariable Long id, Authentication authentication) {
//        tripService.deleteTrip(id, authentication.getName());
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping("/discover")
//    public ResponseEntity<?> discoverTrips(Authentication authentication) {
//        return ResponseEntity.ok(tripService.discoverTrips(authentication.getName()));
//    }
//
//    // ✅ members of a trip
//    @GetMapping("/{tripId}/members")
//    public ResponseEntity<Set<User>> getTripMembers(@PathVariable Long tripId) {
//        Trip trip = tripRepo.findById(tripId)
//                .orElseThrow(() -> new RuntimeException("Trip not found"));
//        return ResponseEntity.ok(trip.getMembers());
//    }
//
//    // ✅ overlapping matches endpoint
//    @GetMapping("/match")
//    public ResponseEntity<List<Trip>> findMatchingTrips(
//            @RequestParam String destination,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
//            Authentication auth
//    ) {
//        return ResponseEntity.ok(
//                tripService.findMatchingTrips(auth.getName(), destination, startDate, endDate)
//        );
//    }
//
//    @GetMapping("/matches/auto")
//    public ResponseEntity<?> getAutoMatchingTrips(Authentication authentication) {
//        try {
//            String email = authentication.getName();
//            return ResponseEntity.ok(tripService.getAutoMatchingTrips(email));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.internalServerError().body(e.getMessage());
//        }
//    }
//
//}

//package com.travmate.controller;
//
//import com.travmate.dto.TripDTO;
//import com.travmate.model.Trip;
//import com.travmate.model.User;
//import com.travmate.repository.TripRepository;
//import com.travmate.service.TripService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Set;
//
//@RestController
//@RequestMapping("/api/trips")
//public class TripController {
//
//    @Autowired
//    private TripService tripService;
//
//    @Autowired
//    private TripRepository tripRepo;
//
//    // ✅ Create a trip
//    @PostMapping
//    public ResponseEntity<Trip> createTrip(@RequestBody Trip trip, Authentication authentication) {
//        return ResponseEntity.ok(tripService.createTrip(trip, authentication.getName()));
//    }
//
//    // ✅ Get trip details by ID (DTO)
//    @GetMapping("/{id}")
//    public ResponseEntity<TripDTO> getTripById(@PathVariable Long id) {
//        return ResponseEntity.ok(tripService.getTripByIdDTO(id));
//    }
//
//    // ✅ Get all trips created by the current user
//    @GetMapping
//    public ResponseEntity<List<TripDTO>> getAllTrips(Authentication authentication) {
//        return ResponseEntity.ok(tripService.getAllTripsByCreator(authentication.getName()));
//    }
//
//    // ✅ Update a trip
//    @PutMapping("/{id}")
//    public ResponseEntity<Trip> updateTrip(@PathVariable Long id,
//                                           @RequestBody Trip trip,
//                                           Authentication authentication) {
//        return ResponseEntity.ok(tripService.updateTrip(id, trip, authentication.getName()));
//    }
//
//    // ✅ Delete a trip
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteTrip(@PathVariable Long id, Authentication authentication) {
//        tripService.deleteTrip(id, authentication.getName());
//        return ResponseEntity.noContent().build();
//    }
//
//    // ✅ Discover other users' public trips
//    @GetMapping("/discover")
//    public ResponseEntity<?> discoverTrips(Authentication authentication) {
//        return ResponseEntity.ok(tripService.discoverTrips(authentication.getName()));
//    }
//
//    // ✅ View all members of a trip
//    @GetMapping("/{tripId}/members")
//    public ResponseEntity<Set<User>> getTripMembers(@PathVariable Long tripId) {
//        Trip trip = tripRepo.findById(tripId)
//                .orElseThrow(() -> new RuntimeException("Trip not found"));
//        return ResponseEntity.ok(trip.getMembers());
//    }
//
//    // ✅ Matching trips (overlapping date & same destination)
//    @GetMapping("/match")
//    public ResponseEntity<List<Trip>> findMatchingTrips(
//            @RequestParam String destination,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
//            Authentication auth
//    ) {
//        return ResponseEntity.ok(
//                tripService.findMatchingTrips(auth.getName(), destination, startDate, endDate)
//        );
//    }
//
//
//    // ✅ Auto matching trips (optional - future feature)
//    @GetMapping("/matches/auto")
//    public ResponseEntity<?> getAutoMatchingTrips(Authentication authentication) {
//        try {
//            String email = authentication.getName();
//            return ResponseEntity.ok(tripService.getAutoMatchingTrips(email));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.internalServerError().body(e.getMessage());
//        }
//    }
//}
//



package com.travmate.controller;

import com.travmate.dto.TripDTO;
import com.travmate.model.Trip;
import com.travmate.model.User;
import com.travmate.repository.TripRepository;
import com.travmate.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/trips")
@CrossOrigin(origins = "*") // 👈 Optional: allows your frontend (Vercel) to access backend
public class TripController {

    @Autowired
    private TripService tripService;

    @Autowired
    private TripRepository tripRepo;

    // ✅ Create a new trip
    @PostMapping
    public ResponseEntity<Trip> createTrip(@RequestBody Trip trip, Authentication authentication) {
        String email = authentication.getName();
        Trip created = tripService.createTrip(trip, email);
        return ResponseEntity.ok(created);
    }

    // ✅ Get a trip by ID (DTO for frontend)
    @GetMapping("/{id}")
    public ResponseEntity<TripDTO> getTripById(@PathVariable Long id) {
        TripDTO tripDTO = tripService.getTripByIdDTO(id);
        return ResponseEntity.ok(tripDTO);
    }

    // ✅ Get all trips created by the logged-in user
    @GetMapping
    public ResponseEntity<List<TripDTO>> getAllTrips(Authentication authentication) {
        String email = authentication.getName();
        List<TripDTO> trips = tripService.getAllTripsByCreator(email);
        return ResponseEntity.ok(trips);
    }

    // ✅ Update a specific trip
    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(
            @PathVariable Long id,
            @RequestBody Trip tripDetails,
            Authentication authentication
    ) {
        String email = authentication.getName();
        Trip updated = tripService.updateTrip(id, tripDetails, email);
        return ResponseEntity.ok(updated);
    }

    // ✅ Delete a specific trip
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id, Authentication authentication) {
        String email = authentication.getName();
        tripService.deleteTrip(id, email);
        return ResponseEntity.noContent().build();
    }

    // ✅ Get all public discoverable trips (excluding user’s own)
    @GetMapping("/discover")
    public ResponseEntity<List<Trip>> discoverTrips(Authentication authentication) {
        String email = authentication.getName();
        List<Trip> discoverableTrips = tripService.discoverTrips(email);
        return ResponseEntity.ok(discoverableTrips);
    }

    // ✅ Get members of a trip
    @GetMapping("/{tripId}/members")
    public ResponseEntity<Set<User>> getTripMembers(@PathVariable Long tripId) {
        Trip trip = tripRepo.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        return ResponseEntity.ok(trip.getMembers());
    }

    // ✅ Find overlapping trips manually (destination + overlapping date range)
    @GetMapping("/match")
    public ResponseEntity<List<Trip>> findMatchingTrips(
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Authentication auth
    ) {
        String email = auth.getName();
        List<Trip> matchingTrips = tripService.findMatchingTrips(email, destination, startDate, endDate);
        return ResponseEntity.ok(matchingTrips);
    }

    // ✅ Auto match (Option 2 — recommended for production)
    @GetMapping("/matches/auto")
    public ResponseEntity<?> getAutoMatchingTrips(Authentication authentication) {
        try {
            String email = authentication.getName();
            return ResponseEntity.ok(tripService.getAutoMatchingTrips(email));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Failed to fetch auto matches: " + e.getMessage());
        }
    }
}
