package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.dtos.auth.LoginRequest;
import com.iws_manager.iws_manager_api.dtos.auth.LoginResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(
            @RequestBody LoginRequest loginRequest,
            HttpServletRequest request,
            HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        securityContextRepository.saveContext(context, request, response);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(new LoginResponse(userDetails.getUsername()));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Not authenticated"));
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(
                new LoginResponse(userDetails.getUsername()));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok(
                Map.of("message", "Logged out successfully"));
    }

}
