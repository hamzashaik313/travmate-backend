package com.travmate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    // 🌍 Root endpoint for Render or browser checks
    @GetMapping("/")
    public Map<String, String> rootCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("app", "TravMate Backend");
        response.put("status", "running ✅");
        response.put("message", "Welcome to TravMate API!");
        return response;
    }

    // ❤️ Health check endpoint
    @GetMapping("/api/auth/health")
    public Map<String, String> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "ok");
        response.put("message", "TravMate backend is healthy 🚀");
        return response;
    }
}
