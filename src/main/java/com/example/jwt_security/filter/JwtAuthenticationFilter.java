package com.example.jwt_security.filter;

import com.example.jwt_security.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.example.jwt_security.service.CustomUserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	
	private final JwtService jwtService;
	private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(CustomUserDetailsService customUserDetailsService, JwtService jwtService) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtService = jwtService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request,response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            String username = jwtService.extractUsername(token);

            if (username != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                if (jwtService.isTokenValid(token,userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,null,userDetails.getAuthorities()
                            );

                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));

                            SecurityContext context = SecurityContextHolder.createEmptyContext();
                            context.setAuthentication(authenticationToken);
                            SecurityContextHolder.setContext(context);

                }
            }
        } catch (JwtException ex) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request,response);
    }
}
