package com.travmate.controller;

import com.travmate.model.User;
import com.travmate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {"http://localhost:3000","https://travmate-frontend.vercel.app","https://travmate.vercel.app"})
public class UserSettingsController {

    @Autowired private UserRepository userRepository;

    @GetMapping("/settings")
    public ResponseEntity<User> getSettings(Principal principal) {
        Optional<User> optionalUser = userRepository.findByEmail(principal.getName());
        return optionalUser.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PutMapping("/settings")
    public ResponseEntity<User> updateSettings(@RequestBody User newSettings, Principal principal) {
        Optional<User> optionalUser = userRepository.findByEmail(principal.getName());
        if (optionalUser.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        User user = optionalUser.get();
        user.setEmailNotifications(newSettings.isEmailNotifications());
        user.setTripReminders(newSettings.isTripReminders());
        user.setTripJoinRequests(newSettings.isTripJoinRequests());
        user.setPublicProfile(newSettings.isPublicProfile());
        user.setAllowTripRequests(newSettings.isAllowTripRequests());

        userRepository.save(user);
        return ResponseEntity.ok(user);
    }
}
