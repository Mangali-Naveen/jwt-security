package com.example.jwt_security.config;

import com.example.jwt_security.entity.User;
import com.example.jwt_security.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminSeeder {

    @Bean
    public CommandLineRunner seedAdmin(UserRepository userRepo,
                                       PasswordEncoder passwordEncoder) {

        return args -> {
            String adminUsername = "admin";

            if (!userRepo.existsByUsername(adminUsername)) {
                User admin = new User(
                        "Admin",
                        adminUsername,
                        passwordEncoder.encode("admin123"),
                        "ADMIN"
                );

                userRepo.save(admin);
                System.out.println("Admin user created: admin / admin123");

            }
        };
    }


}
