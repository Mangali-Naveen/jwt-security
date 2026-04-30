package com.example.jwt_security.controller;

import com.example.jwt_security.dto.AuthResponse;
import com.example.jwt_security.dto.LoginRequest;
import com.example.jwt_security.dto.RegisterRequest;
import com.example.jwt_security.service.AuthService;
import jakarta.validation.Valid;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        String message = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", message));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

}
