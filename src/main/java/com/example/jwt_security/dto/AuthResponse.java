package com.example.jwt_security.dto;

public record AuthResponse(
		
		String token,
		String tokenType,
		String username,
        String role
		) {

}
