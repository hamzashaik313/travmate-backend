//
//package com.travmate.service.impl;
//
//import com.travmate.model.Trip;
//import com.travmate.model.User;
//import com.travmate.repository.TripRepository;
//import com.travmate.repository.UserRepository;
//import com.travmate.service.TripService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class TripServiceImpl implements TripService {
//
//    @Autowired
//    private TripRepository tripRepository; // Assumed to have findByCreatedBy(User user)
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public Trip createTrip(Trip trip, String userEmail) {
//
//        User user = userRepository.findByEmail(userEmail)
//                .orElseThrow(() -> new RuntimeException("User not found!"));
//        trip.setCreatedBy(user);
//        return tripRepository.save(trip);
//    }
//
//    @Override
//    public Optional<Trip> getTripById(Long id) {
//        // NOTE: For full security, this should also check ownership.
//        return tripRepository.findById(id);
//    }
//
//    // CRITICAL SECURITY FIX: Fetches trips ONLY created by the authenticated user
//    @Override
//    public List<Trip> getAllTripsByCreator(String userEmail) {
//        User creator = userRepository.findByEmail(userEmail)
//                .orElseThrow(() -> new RuntimeException("Authenticated user not found."));
//
//        // This relies on the TripRepository having: List<Trip> findByCreatedBy(User user);
//        return tripRepository.findByCreatedBy(creator);
//    }
//@Override
//public Trip updateTrip(Long id, Trip tripDetails, String userEmail) {
//    Trip trip = tripRepository.findById(id)
//            .orElseThrow(() -> new RuntimeException("Trip not found"));
//
//    if (!trip.getCreatedBy().getEmail().equals(userEmail)) {
//        throw new RuntimeException("You are not allowed to update this trip!");
//    }
//    trip.setTitle(tripDetails.getTitle());
//    trip.setDestination(tripDetails.getDestination());
//    trip.setStartDate(tripDetails.getStartDate());
//    trip.setEndDate(tripDetails.getEndDate());
//
//    // Save updated trip
//    return tripRepository.save(trip);
//}
//
//
//    @Override
//    public void deleteTrip(Long id, String userEmail) {
//        Trip trip = tripRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Trip not found"));
//
//        if (!trip.getCreatedBy().getEmail().equals(userEmail)) {
//            throw new RuntimeException("You are not allowed to delete this trip!");
//        }
//
//        tripRepository.delete(trip);
//    }
//}

//after deployement
//package com.travmate.service.impl;
//
//import com.travmate.dto.ItineraryDTO;
//import com.travmate.dto.TripDTO;
//import com.travmate.model.Trip;
//import com.travmate.model.User;
//import com.travmate.repository.TripRepository;
//import com.travmate.repository.UserRepository;
//import com.travmate.service.TripService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class TripServiceImpl implements TripService {
//
//    @Autowired
//    private TripRepository tripRepo;
//    @Autowired private TripRepository tripRepository;
//    @Autowired private UserRepository userRepository;
//
////    @Override
////    public Trip createTrip(Trip trip, String userEmail) {
////        User user = userRepository.findByEmail(userEmail)
////                .orElseThrow(() -> new RuntimeException("User not found"));
////        trip.setOwner(user);
////        if (trip.getCreatedBy() == null) trip.setCreatedBy(user);
////        return tripRepository.save(trip);
////    }
//public Trip createTrip(Trip trip, String ownerEmail) {
//    User owner = userRepository.findByEmail(ownerEmail)
//            .orElseThrow(() -> new RuntimeException("Owner not found"));
//
//    // ✅ Set the owner for the trip
//    trip.setOwner(owner);
//
//    // ✅ Add owner as the first member
//    trip.getMembers().add(owner);
//
//    // ✅ Save trip
//    Trip savedTrip = tripRepo.save(trip);
//
//    return savedTrip;
//}
//
//
//    @Override
//    public Optional<Trip> getTripEntityById(Long id) {
//        return tripRepository.findById(id);
//    }
//
//    @Override
//    public TripDTO getTripByIdDTO(Long id) {
//        Trip t = tripRepository.findDetailedById(id)
//                .orElseThrow(() -> new RuntimeException("Trip not found"));
//        return toDTO(t);
//    }
//
//    @Override
//    public List<TripDTO> getAllTripsByCreator(String userEmail) {
//        User owner = userRepository.findByEmail(userEmail)
//                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
//        List<Trip> trips = tripRepository.findAllByOwnerWithItineraries(owner);
//        return trips.stream().map(this::toDTO).toList();
//    }
//
//    @Override
//    public Trip updateTrip(Long id, Trip tripDetails, String userEmail) {
//        Trip trip = tripRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Trip not found"));
//
//        if (trip.getOwner() == null || !trip.getOwner().getEmail().equals(userEmail)) {
//            throw new RuntimeException("You are not allowed to update this trip!");
//        }
//
//        trip.setTitle(tripDetails.getTitle());
//        trip.setDestination(tripDetails.getDestination());
//        trip.setStartDate(tripDetails.getStartDate());
//        trip.setEndDate(tripDetails.getEndDate());
//        trip.setBudget(tripDetails.getBudget());
//        trip.setHeroImageUrl(tripDetails.getHeroImageUrl());
//
//        return tripRepository.save(trip);
//    }
//
//    @Override
//    public void deleteTrip(Long id, String userEmail) {
//        Trip trip = tripRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Trip not found"));
//
//        if (trip.getOwner() == null || !trip.getOwner().getEmail().equals(userEmail)) {
//            throw new RuntimeException("You are not allowed to delete this trip!");
//        }
//        tripRepository.delete(trip);
//    }
//
//    private TripDTO toDTO(Trip t) {
//        return new TripDTO(
//                t.getId(),
//                t.getTitle(),
//                t.getDestination(),
//                t.getStartDate(),
//                t.getEndDate(),
//                t.getBudget(),
//                t.getOwner() != null ? t.getOwner().getName() : null,
//                (t.getItineraries() == null) ? List.of() :
//                        t.getItineraries().stream()
//                                .map(i -> new ItineraryDTO(i.getDayNumber(), i.getActivity()))
//                                .toList()
//        );
//    }
//    @Override
//    public List<Trip> discoverTrips(String userEmail) {
//        User currentUser = userRepository.findByEmail(userEmail)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        return tripRepository.findDiscoverableTrips(currentUser);
//    }
//}


//package com.travmate.service.impl;
//
//import com.travmate.dto.ItineraryDTO;
//import com.travmate.dto.TripDTO;
//import com.travmate.model.Trip;
//import com.travmate.model.User;
//import com.travmate.repository.TripRepository;
//import com.travmate.repository.UserRepository;
//import com.travmate.service.TripService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class TripServiceImpl implements TripService {
//
//    @Autowired
//    private TripRepository tripRepo;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    // ✅ CREATE TRIP
//    @Override
//    public Trip createTrip(Trip trip, String ownerEmail) {
//        User owner = userRepository.findByEmail(ownerEmail)
//                .orElseThrow(() -> new RuntimeException("Owner not found"));
//
//        trip.setOwner(owner);
//        trip.getMembers().add(owner); // add owner as first member
//
//        return tripRepo.save(trip);
//    }
//
//    // ✅ FETCH TRIP ENTITY (for internal use)
//    @Override
//    public Optional<Trip> getTripEntityById(Long id) {
//        return tripRepo.findById(id);
//    }
//
//    // ✅ FETCH TRIP DTO (for frontend)
//    @Override
//    public TripDTO getTripByIdDTO(Long id) {
//        Trip trip = tripRepo.findDetailedById(id)
//                .orElseThrow(() -> new RuntimeException("Trip not found"));
//
//        TripDTO dto = new TripDTO();
//        dto.setId(trip.getId());
//        dto.setTitle(trip.getTitle());
//        dto.setDestination(trip.getDestination());
//        dto.setStartDate(trip.getStartDate());
//        dto.setEndDate(trip.getEndDate());
//        dto.setBudget(trip.getBudget());
//        dto.setHeroImageUrl(trip.getHeroImageUrl());
//
//        // ✅ Include owner info for frontend
//        if (trip.getOwner() != null) {
//            dto.setOwnerName(trip.getOwner().getName());
//            dto.setOwnerEmail(trip.getOwner().getEmail());
//        }
//
//        // ✅ Include itineraries safely
//        if (trip.getItineraries() != null) {
//            dto.setItineraries(
//                    trip.getItineraries().stream()
//                            .map(i -> new ItineraryDTO(i.getDayNumber(), i.getActivity()))
//                            .toList()
//            );
//        }
//
//        return dto;
//    }
//
//    // ✅ FETCH ALL TRIPS BY OWNER
//    @Override
//    public List<TripDTO> getAllTripsByCreator(String userEmail) {
//        User owner = userRepository.findByEmail(userEmail)
//                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
//
//        return tripRepo.findAllByOwnerWithItineraries(owner)
//                .stream()
//                .map(this::toDTO)
//                .toList();
//    }
//
//    // ✅ UPDATE TRIP
//    @Override
//    public Trip updateTrip(Long id, Trip tripDetails, String userEmail) {
//        Trip trip = tripRepo.findById(id)
//                .orElseThrow(() -> new RuntimeException("Trip not found"));
//
//        if (trip.getOwner() == null || !trip.getOwner().getEmail().equals(userEmail)) {
//            throw new RuntimeException("You are not allowed to update this trip!");
//        }
//
//        trip.setTitle(tripDetails.getTitle());
//        trip.setDestination(tripDetails.getDestination());
//        trip.setStartDate(tripDetails.getStartDate());
//        trip.setEndDate(tripDetails.getEndDate());
//        trip.setBudget(tripDetails.getBudget());
//        trip.setHeroImageUrl(tripDetails.getHeroImageUrl());
//
//        return tripRepo.save(trip);
//    }
//
//    // ✅ DELETE TRIP
//    @Override
//    public void deleteTrip(Long id, String userEmail) {
//        Trip trip = tripRepo.findById(id)
//                .orElseThrow(() -> new RuntimeException("Trip not found"));
//
//        if (trip.getOwner() == null || !trip.getOwner().getEmail().equals(userEmail)) {
//            throw new RuntimeException("You are not allowed to delete this trip!");
//        }
//
//        tripRepo.delete(trip);
//    }
//
//    // ✅ DISCOVER TRIPS (public trips)
//    @Override
//    public List<Trip> discoverTrips(String userEmail) {
//        User currentUser = userRepository.findByEmail(userEmail)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        return tripRepo.findDiscoverableTrips(currentUser);
//    }
//
//    // ✅ HELPER: convert Trip → TripDTO
//    private TripDTO toDTO(Trip t) {
//        TripDTO dto = new TripDTO();
//        dto.setId(t.getId());
//        dto.setTitle(t.getTitle());
//        dto.setDestination(t.getDestination());
//        dto.setStartDate(t.getStartDate());
//        dto.setEndDate(t.getEndDate());
//        dto.setBudget(t.getBudget());
//        dto.setHeroImageUrl(t.getHeroImageUrl());
//
//        if (t.getOwner() != null) {
//            dto.setOwnerName(t.getOwner().getName());
//            dto.setOwnerEmail(t.getOwner().getEmail());
//        }
//
//        if (t.getItineraries() != null) {
//            dto.setItineraries(
//                    t.getItineraries().stream()
//                            .map(i -> new ItineraryDTO(i.getDayNumber(), i.getActivity()))
//                            .toList()
//            );
//        }
//
//        return dto;
//    }
//    @Override
//    public List<TripDTO> findMatchingTrips(String userEmail, String destination, String startDate, String endDate) {
//        // 🔐 1️⃣ Validate and fetch current user
//        User currentUser = userRepository.findByEmail(userEmail)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        // 🗓️ 2️⃣ Parse date strings to LocalDate
//        LocalDate start;
//        LocalDate end;
//        try {
//            start = LocalDate.parse(startDate);
//            end = LocalDate.parse(endDate);
//        } catch (Exception e) {
//            throw new RuntimeException("Invalid date format! Expected yyyy-MM-dd");
//        }
//
//        // 🔍 3️⃣ Find trips that overlap (same destination + overlapping dates)
//        List<Trip> matches = tripRepo.findMatchingTrips(destination, start, end, currentUser);
//
//        // 🧭 4️⃣ Convert to DTO list for frontend
//        return matches.stream()
//                .map(this::toDTO)
//                .toList();
//    }
//
//}

//package com.travmate.service.impl;
//
//import com.travmate.dto.ItineraryDTO;
//import com.travmate.dto.TripDTO;
//import com.travmate.model.Trip;
//import com.travmate.model.User;
//import com.travmate.repository.TripRepository;
//import com.travmate.repository.UserRepository;
//import com.travmate.service.TripService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class TripServiceImpl implements TripService {
//
//    @Autowired private TripRepository tripRepo;
//    @Autowired private TripRepository tripRepository;
//    @Autowired private UserRepository userRepository;
//
//    @Override
//    public Trip createTrip(Trip trip, String ownerEmail) {
//        User owner = userRepository.findByEmail(ownerEmail)
//                .orElseThrow(() -> new RuntimeException("Owner not found"));
//        trip.setOwner(owner);
//        // ensure owner is a member
//        trip.getMembers().add(owner);
//        return tripRepo.save(trip);
//    }
//
//    @Override public Optional<Trip> getTripEntityById(Long id) { return tripRepository.findById(id); }
//
//    @Override
//    public TripDTO getTripByIdDTO(Long id) {
//        Trip t = tripRepository.findDetailedById(id)
//                .orElseThrow(() -> new RuntimeException("Trip not found"));
//        return toDTO(t);
//    }
//
//    @Override
//    public List<TripDTO> getAllTripsByCreator(String userEmail) {
//        User owner = userRepository.findByEmail(userEmail)
//                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
//        return tripRepository.findAllByOwnerWithItineraries(owner).stream().map(this::toDTO).toList();
//    }
//
//    @Override
//    public Trip updateTrip(Long id, Trip tripDetails, String userEmail) {
//        Trip trip = tripRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Trip not found"));
//
//        if (trip.getOwner() == null || !trip.getOwner().getEmail().equals(userEmail)) {
//            throw new RuntimeException("You are not allowed to update this trip!");
//        }
//
//        trip.setTitle(tripDetails.getTitle());
//        trip.setDestination(tripDetails.getDestination());
//        trip.setStartDate(tripDetails.getStartDate());
//        trip.setEndDate(tripDetails.getEndDate());
//        trip.setBudget(tripDetails.getBudget());
//        trip.setHeroImageUrl(tripDetails.getHeroImageUrl());
//        trip.setVisibility(tripDetails.getVisibility());
//
//        return tripRepository.save(trip);
//    }
//
//    @Override
//    public void deleteTrip(Long id, String userEmail) {
//        Trip trip = tripRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Trip not found"));
//        if (trip.getOwner() == null || !trip.getOwner().getEmail().equals(userEmail)) {
//            throw new RuntimeException("You are not allowed to delete this trip!");
//        }
//        tripRepository.delete(trip);
//    }
//
//    private TripDTO toDTO(Trip t) {
//        return new TripDTO(
//                t.getId(),
//                t.getTitle(),
//                t.getDestination(),
//                t.getStartDate(),
//                t.getEndDate(),
//                t.getBudget(),
//                t.getOwner() != null ? t.getOwner().getName() : null,
//                (t.getItineraries() == null) ? List.of() :
//                        t.getItineraries().stream()
//                                .map(i -> new ItineraryDTO(i.getDayNumber(), i.getActivity()))
//                                .toList()
//        );
//    }
//
//    @Override
//    public List<Trip> discoverTrips(String userEmail) {
//        User currentUser = userRepository.findByEmail(userEmail)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        return tripRepository.findDiscoverableTrips(currentUser);
//    }
//
//    @Override
//    public List<Trip> findMatchingTrips(String userEmail, String destination, LocalDate start, LocalDate end) {
//        User currentUser = userRepository.findByEmail(userEmail)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        return tripRepository.findMatchingTrips(destination, start, end, currentUser);
//    }
//
//    @Override
//    public List<Trip> getAutoMatchingTrips(String userEmail) {
//        User currentUser = userRepository.findByEmail(userEmail)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        List<Trip> myTrips = tripRepo.findAllByOwnerWithItineraries(currentUser);
//
//        List<Trip> matchedTrips = new ArrayList<>();
//
//        for (Trip myTrip : myTrips) {
//            List<Trip> matches = tripRepo.findMatchingTrips(
//                    myTrip.getDestination(),
//                    myTrip.getStartDate(),
//                    myTrip.getEndDate(),
//                    currentUser
//            );
//            matchedTrips.addAll(matches);
//        }
//
//        return matchedTrips;
//    }
//
//}


//package com.travmate.service.impl;
//
//import com.travmate.dto.ItineraryDTO;
//import com.travmate.dto.TripDTO;
//import com.travmate.model.Trip;
//import com.travmate.model.User;
//import com.travmate.repository.TripRepository;
//import com.travmate.repository.UserRepository;
//import com.travmate.service.TripService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class TripServiceImpl implements TripService {
//
//    @Autowired
//    private TripRepository tripRepo;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public Trip createTrip(Trip trip, String ownerEmail) {
//        User owner = userRepository.findByEmail(ownerEmail)
//                .orElseThrow(() -> new RuntimeException("Owner not found"));
//        trip.setOwner(owner);
//        trip.getMembers().add(owner);
//        trip.setVisibility("PUBLIC");
//        return tripRepo.save(trip);
//    }
//
//    @Override
//    public Optional<Trip> getTripEntityById(Long id) {
//        return tripRepo.findById(id);
//    }
//
//    @Override
//    public TripDTO getTripByIdDTO(Long id) {
//        Trip trip = tripRepo.findDetailedById(id)
//                .orElseThrow(() -> new RuntimeException("Trip not found"));
//
//        TripDTO dto = new TripDTO();
//        dto.setId(trip.getId());
//        dto.setTitle(trip.getTitle());
//        dto.setDestination(trip.getDestination());
//        dto.setStartDate(trip.getStartDate());
//        dto.setEndDate(trip.getEndDate());
//        dto.setBudget(trip.getBudget());
//        dto.setHeroImageUrl(trip.getHeroImageUrl());
//
//        // ✅ Owner info (now includes ID + photo)
//        if (trip.getOwner() != null) {
//            dto.setOwnerId(trip.getOwner().getId());
//            dto.setOwnerName(trip.getOwner().getName());
//            dto.setOwnerEmail(trip.getOwner().getEmail());
//            dto.setOwnerPhotoUrl(trip.getOwner().getPhotoUrl());
//        }
//
//        if (trip.getItineraries() != null) {
//            dto.setItineraries(
//                    trip.getItineraries().stream()
//                            .map(i -> new ItineraryDTO(i.getDayNumber(), i.getActivity()))
//                            .toList()
//            );
//        }
//
//        return dto;
//    }
//
//    @Override
//    public List<TripDTO> getAllTripsByCreator(String userEmail) {
//        User owner = userRepository.findByEmail(userEmail)
//                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
//        return tripRepo.findAllByOwnerWithItineraries(owner)
//                .stream()
//                .map(this::toDTO)
//                .toList();
//    }
//
//    @Override
//    public Trip updateTrip(Long id, Trip tripDetails, String userEmail) {
//        Trip trip = tripRepo.findById(id)
//                .orElseThrow(() -> new RuntimeException("Trip not found"));
//
//        if (trip.getOwner() == null || !trip.getOwner().getEmail().equals(userEmail)) {
//            throw new RuntimeException("You are not allowed to update this trip!");
//        }
//
//        trip.setTitle(tripDetails.getTitle());
//        trip.setDestination(tripDetails.getDestination());
//        trip.setStartDate(tripDetails.getStartDate());
//        trip.setEndDate(tripDetails.getEndDate());
//        trip.setBudget(tripDetails.getBudget());
//        trip.setHeroImageUrl(tripDetails.getHeroImageUrl());
//        return tripRepo.save(trip);
//    }
//
//    @Override
//    public void deleteTrip(Long id, String userEmail) {
//        Trip trip = tripRepo.findById(id)
//                .orElseThrow(() -> new RuntimeException("Trip not found"));
//
//        if (trip.getOwner() == null || !trip.getOwner().getEmail().equals(userEmail)) {
//            throw new RuntimeException("You are not allowed to delete this trip!");
//        }
//
//        tripRepo.delete(trip);
//    }
//
//    @Override
//    public List<Trip> discoverTrips(String userEmail) {
//        User currentUser = userRepository.findByEmail(userEmail)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        return tripRepo.findDiscoverableTrips(currentUser);
//    }
//
//    @Override
//    public List<Trip> findMatchingTrips(String userEmail, String destination, LocalDate startDate, LocalDate endDate) {
//        User currentUser = userRepository.findByEmail(userEmail)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        System.out.println("🔍 Checking for matches at " + destination + " for " + userEmail);
//        System.out.println("Start: " + startDate + " | End: " + endDate);
//
//        // ✅ pass currentUser.getId(), not full object, to your repository query
//        List<Trip> matches = tripRepo.findMatchingTrips(destination, startDate, endDate, currentUser.getId());
//        System.out.println("Found " + matches.size() + " matching trips.");
//
//        return matches;
//    }
//
//    @Override
//    public List<Trip> getAutoMatchingTrips(String userEmail) {
//        User currentUser = userRepository.findByEmail(userEmail)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        List<Trip> userTrips = tripRepo.findAllByOwnerWithItineraries(currentUser);
//        List<Trip> allMatches = new ArrayList<>();
//
//        for (Trip trip : userTrips) {
//            List<Trip> matches = tripRepo.findMatchingTrips(
//                    trip.getDestination(),
//                    trip.getStartDate(),
//                    trip.getEndDate(),
//                    currentUser.getId()
//            );
//            allMatches.addAll(matches);
//        }
//
//        System.out.println("🧩 Total auto matches for user " + userEmail + ": " + allMatches.size());
//
//        // ✅ avoid duplicates (distinct by trip ID)
//        return allMatches.stream()
//                .collect(
//                        Collectors.collectingAndThen(
//                        Collectors.toMap(Trip::getId, t -> t, (a, b) -> a),
//                        m -> new ArrayList<>(m.values())
//                ));
//    }
//
//
//    // ✅ Helper
////    private TripDTO toDTO(Trip t) {
////        TripDTO dto = new TripDTO();
////        dto.setId(t.getId());
////        dto.setTitle(t.getTitle());
////        dto.setDestination(t.getDestination());
////        dto.setStartDate(t.getStartDate());
////        dto.setEndDate(t.getEndDate());
////        dto.setBudget(t.getBudget());
////        dto.setHeroImageUrl(t.getHeroImageUrl());
////
////        if (t.getOwner() != null) {
////            dto.setOwnerId(t.getOwner().getId());
////            dto.setOwnerName(t.getOwner().getName());
////            dto.setOwnerEmail(t.getOwner().getEmail());
////            dto.setOwnerPhotoUrl(t.getOwner().getPhotoUrl());
////        }
////
////        if (t.getItineraries() != null) {
////            dto.setItineraries(
////                    t.getItineraries().stream()
////                            .map(i -> new ItineraryDTO(i.getDayNumber(), i.getActivity()))
////                            .toList()
////            );
////        }
////
////        return dto;
//
//    private TripDTO toDTO(Trip t) {
//        TripDTO dto = new TripDTO();
//        dto.setId(t.getId());
//        dto.setTitle(t.getTitle());
//        dto.setDestination(t.getDestination());
//        dto.setStartDate(t.getStartDate());
//        dto.setEndDate(t.getEndDate());
//        dto.setBudget(t.getBudget());
//        dto.setHeroImageUrl(t.getHeroImageUrl());
//
//        // ✅ Owner info fallback fixes
//        if (t.getOwner() != null) {
//            dto.setOwnerId(t.getOwner().getId());
//            dto.setOwnerName(
//                    (t.getOwner().getName() != null && !t.getOwner().getName().isBlank())
//                            ? t.getOwner().getName()
//                            : t.getOwner().getEmail().split("@")[0]
//            );
//            dto.setOwnerEmail(t.getOwner().getEmail());
//            dto.setOwnerPhotoUrl(t.getOwner().getPhotoUrl());
//        } else {
//            dto.setOwnerName("Unknown");
//            dto.setOwnerEmail("unknown@example.com");
//        }
//
//        if (t.getItineraries() != null) {
//            dto.setItineraries(
//                    t.getItineraries().stream()
//                            .map(i -> new ItineraryDTO(i.getDayNumber(), i.getActivity()))
//                            .toList()
//            );
//        }
//
//        return dto;
//    }
//
//}


package com.travmate.service.impl;

import com.travmate.dto.ItineraryDTO;
import com.travmate.dto.TripDTO;
import com.travmate.model.Trip;
import com.travmate.model.User;
import com.travmate.repository.TripRepository;
import com.travmate.repository.UserRepository;
import com.travmate.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TripServiceImpl implements TripService {

    @Autowired
    private TripRepository tripRepo;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Trip createTrip(Trip trip, String ownerEmail) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        trip.setOwner(owner);
        trip.getMembers().add(owner);
        trip.setVisibility("PUBLIC");
        return tripRepo.save(trip);
    }

    @Override
    public Optional<Trip> getTripEntityById(Long id) {
        return tripRepo.findById(id);
    }

    @Override
    public TripDTO getTripByIdDTO(Long id) {
        Trip trip = tripRepo.findDetailedById(id)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        TripDTO dto = new TripDTO();
        dto.setId(trip.getId());
        dto.setTitle(trip.getTitle());
        dto.setDestination(trip.getDestination());
        dto.setStartDate(trip.getStartDate());
        dto.setEndDate(trip.getEndDate());
        dto.setBudget(trip.getBudget());
        dto.setHeroImageUrl(trip.getHeroImageUrl());
        dto.setVisibility(trip.getVisibility()); // 🔧 add this for completeness

        // ✅ Ensure ownerId and details are always included
        if (trip.getOwner() != null) {
            dto.setOwnerId(trip.getOwner().getId());
            dto.setOwnerName(trip.getOwner().getName());
            dto.setOwnerEmail(trip.getOwner().getEmail());
            dto.setOwnerPhotoUrl(trip.getOwner().getPhotoUrl());
        }

        if (trip.getItineraries() != null) {
            dto.setItineraries(
                    trip.getItineraries().stream()
                            .map(i -> new ItineraryDTO(i.getDayNumber(), i.getActivity()))
                            .toList()
            );
        }

        return dto;
    }

    @Override
    public List<TripDTO> getAllTripsByCreator(String userEmail) {
        User owner = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
        return tripRepo.findAllByOwnerWithItineraries(owner)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public Trip updateTrip(Long id, Trip tripDetails, String userEmail) {
        Trip trip = tripRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        if (trip.getOwner() == null || !trip.getOwner().getEmail().equals(userEmail)) {
            throw new RuntimeException("You are not allowed to update this trip!");
        }

        trip.setTitle(tripDetails.getTitle());
        trip.setDestination(tripDetails.getDestination());
        trip.setStartDate(tripDetails.getStartDate());
        trip.setEndDate(tripDetails.getEndDate());
        trip.setBudget(tripDetails.getBudget());
        trip.setHeroImageUrl(tripDetails.getHeroImageUrl());
        trip.setVisibility(tripDetails.getVisibility());
        return tripRepo.save(trip);
    }

    @Override
    public void deleteTrip(Long id, String userEmail) {
        Trip trip = tripRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        if (trip.getOwner() == null || !trip.getOwner().getEmail().equals(userEmail)) {
            throw new RuntimeException("You are not allowed to delete this trip!");
        }

        tripRepo.delete(trip);
    }

    @Override
    public List<Trip> discoverTrips(String userEmail) {
        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return tripRepo.findDiscoverableTrips(currentUser);
    }

    @Override
    public List<Trip> findMatchingTrips(String userEmail, String destination, LocalDate startDate, LocalDate endDate) {
        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println("🔍 Checking for matches at " + destination + " for " + userEmail);
        System.out.println("Start: " + startDate + " | End: " + endDate);

        // ✅ Make sure your TripRepository query expects currentUser.getId()
        List<Trip> matches = tripRepo.findMatchingTrips(destination, startDate, endDate, currentUser.getId());
        System.out.println("Found " + matches.size() + " matching trips.");

        return matches;
    }

    @Override
    public List<Trip> getAutoMatchingTrips(String userEmail) {
        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Trip> userTrips = tripRepo.findAllByOwnerWithItineraries(currentUser);
        List<Trip> allMatches = new ArrayList<>();

        for (Trip trip : userTrips) {
            List<Trip> matches = tripRepo.findMatchingTrips(
                    trip.getDestination(),
                    trip.getStartDate(),
                    trip.getEndDate(),
                    currentUser.getId()
            );
            allMatches.addAll(matches);
        }

        System.out.println("🧩 Total auto matches for user " + userEmail + ": " + allMatches.size());

        // ✅ remove duplicates (distinct by ID)
        return allMatches.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(Trip::getId, t -> t, (a, b) -> a),
                        m -> new ArrayList<>(m.values())
                ));
    }

    // ✅ Reusable DTO mapper
    private TripDTO toDTO(Trip t) {
        TripDTO dto = new TripDTO();
        dto.setId(t.getId());
        dto.setTitle(t.getTitle());
        dto.setDestination(t.getDestination());
        dto.setStartDate(t.getStartDate());
        dto.setEndDate(t.getEndDate());
        dto.setBudget(t.getBudget());
        dto.setHeroImageUrl(t.getHeroImageUrl());
        dto.setVisibility(t.getVisibility());

        // ✅ Safe fallback for owner info
        if (t.getOwner() != null) {
            dto.setOwnerId(t.getOwner().getId());
            dto.setOwnerName(
                    (t.getOwner().getName() != null && !t.getOwner().getName().isBlank())
                            ? t.getOwner().getName()
                            : t.getOwner().getEmail().split("@")[0]
            );
            dto.setOwnerEmail(t.getOwner().getEmail());
            dto.setOwnerPhotoUrl(t.getOwner().getPhotoUrl());
        } else {
            dto.setOwnerName("Unknown");
            dto.setOwnerEmail("unknown@example.com");
        }

        if (t.getItineraries() != null) {
            dto.setItineraries(
                    t.getItineraries().stream()
                            .map(i -> new ItineraryDTO(i.getDayNumber(), i.getActivity()))
                            .toList()
            );
        }

        return dto;
    }
}
