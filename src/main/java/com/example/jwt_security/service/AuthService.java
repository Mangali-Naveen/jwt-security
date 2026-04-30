package com.example.jwt_security.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jwt_security.dto.AuthResponse;
import com.example.jwt_security.dto.LoginRequest;
import com.example.jwt_security.dto.RegisterRequest;
import com.example.jwt_security.entity.User;
import com.example.jwt_security.repository.UserRepository;

@Service
public class AuthService {
	
	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	
	
	public AuthService(UserRepository userRepo, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager,
			JwtService jwtService) {
		super();
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}
	
	
	public String register(RegisterRequest request) {
		
		if (userRepo.existsByUsername(request.username())) {
			throw new RuntimeException("Username already exists");
		}
		
		User user = new User(
				
				request.name(),
				request.username(),
				passwordEncoder.encode(request.password()),
				"USER");
		
		userRepo.save(user);
		return "User registered sucessfully";
	}
	
	public AuthResponse login(LoginRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken (
						request.username(),
						request.password()
						)
				);
		
		

        User user = userRepo.findByUsername(request.username())
                .orElseThrow(() -> new RuntimeException("User not found"));


		
        String token = jwtService.generateToken(
                org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUsername())
                                .password(user.getPassword())
                        .authorities("ROLE_" + user.getRole())
                        .build()
        );

        return new AuthResponse(token, "Bearer", user.getUsername(),user.getRole());
	}
	
	
	
	
	
	
	
	
	
	
}
