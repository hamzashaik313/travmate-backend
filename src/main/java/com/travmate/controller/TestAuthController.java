package com.travmate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestAuthController {

    @GetMapping("/api/test/auth")
    public ResponseEntity<String> testAuth(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).body("❌ Not authenticated");
        }
        return ResponseEntity.ok("✅ Authenticated as: " + authentication.getName());
    }
}
