package com.example.jwt_security.service;

import com.example.jwt_security.entity.User;
import com.example.jwt_security.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepo;

    public CustomUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }


    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                        .password(user.getPassword())
                        .authorities("ROlE_" + user.getRole())
                        .build();
    }
}
