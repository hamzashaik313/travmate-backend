package com.travmate.controller;

import com.travmate.model.Trip;
import com.travmate.service.TripDiscoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips/discover/all")

public class TripDiscoveryController {

    @Autowired
    private TripDiscoveryService tripDiscoveryService;

    @GetMapping
    public ResponseEntity<List<Trip>> discoverTrips(Authentication authentication)
{
        String userEmail = authentication.getName();
        List<Trip> trips = tripDiscoveryService.discoverTrips(userEmail);
        return ResponseEntity.ok(trips);
    }
}
