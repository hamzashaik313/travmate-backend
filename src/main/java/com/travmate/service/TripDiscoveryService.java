package com.travmate.service;

import com.travmate.model.Trip;
import com.travmate.model.User;
import com.travmate.repository.TripRepository;
import com.travmate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TripDiscoveryService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TripRepository tripRepository;

    public List<Trip> discoverTrips(String userEmail) {
        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch all trips owned by the current user
        List<Trip> myTrips = tripRepository.findAllByOwnerWithItineraries(currentUser);

        if (myTrips.isEmpty()) {
            return Collections.emptyList();
        }

        // Get all users who are not the current user
        List<User> otherUsers = userRepository.findAll().stream()
                .filter(u -> !u.getEmail().equals(currentUser.getEmail()))
                .filter(u -> u.isAllowTripRequests() && u.isPublicProfile())
                .collect(Collectors.toList());

        // Collect overlapping trips
        List<Trip> matchingTrips = new ArrayList<>();

        for (User other : otherUsers) {
            List<Trip> trips = tripRepository.findAllByOwnerWithItineraries(other);

            for (Trip myTrip : myTrips) {
                for (Trip theirTrip : trips) {
                    if (datesOverlap(myTrip.getStartDate(), myTrip.getEndDate(),
                            theirTrip.getStartDate(), theirTrip.getEndDate())) {
                        matchingTrips.add(theirTrip);
                    }
                }
            }
        }

        return matchingTrips;
    }

    private boolean datesOverlap(LocalDate start1, LocalDate end1,
                                 LocalDate start2, LocalDate end2) {
        return !(end1.isBefore(start2) || start1.isAfter(end2));
    }
}
