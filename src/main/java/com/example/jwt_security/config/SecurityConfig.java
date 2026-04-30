package com.example.jwt_security.config;

import com.example.jwt_security.filter.JwtAuthenticationFilter;
import com.example.jwt_security.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                          CustomUserDetailsService customUserDetailsService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AuthenticationProvider authenticationProvider) throws Exception {

        http
                // ❌ disable csrf (for APIs)
                .csrf(csrf -> csrf.disable())

                // ❌ no session (JWT)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 🔐 authorization rules
                .authorizeHttpRequests(auth -> auth

                        // 🌍 PUBLIC (UI + static)
                        .requestMatchers(
                                "/**/*.html",
                                "/",     // ✅ all HTML pages
                                "/css/**",
                                "/js/**"
                        ).permitAll()

                        // 🌍 AUTH APIs
                        .requestMatchers("/auth/**", "/hello").permitAll()

                        // 🔐 ADMIN
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // 🔐 CUSTOMER
                        .requestMatchers("/customer/**").hasAnyRole("USER", "ADMIN")

                        // 🔒 everything else
                        .anyRequest().authenticated()
                )

                // 🔐 authentication provider
                .authenticationProvider(authenticationProvider)

                // 🔐 JWT filter
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)

                // ❌ disable default login UI
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

        return http.build();
    }

    // 🔐 Authentication Provider
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // 🔐 Authentication Manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    // 🔐 Password Encoder (BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}