package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.dtos.auth.LoginRequest;
import com.iws_manager.iws_manager_api.dtos.auth.LoginResponse;
import com.iws_manager.iws_manager_api.exception.GlobalExceptionHandler.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.csrf.CsrfToken;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

        private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

        private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

        @Autowired
        private AuthenticationManager authenticationManager;

        @PostMapping("/login")
        public ResponseEntity<?> authenticateUser(
                        @RequestBody LoginRequest loginRequest,
                        HttpServletRequest request,
                        HttpServletResponse response) {

                // Validate input - edge cases
                if (loginRequest == null) {
                        logger.warn("Login attempt with null request body from IP: {}", request.getRemoteAddr());
                        return ResponseEntity
                                        .status(HttpStatus.BAD_REQUEST)
                                        .body(createErrorResponse(
                                                        HttpStatus.BAD_REQUEST,
                                                        "Login request body is required",
                                                        request.getRequestURI()));
                }

                if (loginRequest.getUsername() == null || loginRequest.getUsername().trim().isEmpty()) {
                        logger.warn("Login attempt with empty username from IP: {}", request.getRemoteAddr());
                        return ResponseEntity
                                        .status(HttpStatus.BAD_REQUEST)
                                        .body(createErrorResponse(
                                                        HttpStatus.BAD_REQUEST,
                                                        "Username is required",
                                                        request.getRequestURI()));
                }

                if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
                        logger.warn("Login attempt with empty password for username: {} from IP: {}",
                                        loginRequest.getUsername(), request.getRemoteAddr());
                        return ResponseEntity
                                        .status(HttpStatus.BAD_REQUEST)
                                        .body(createErrorResponse(
                                                        HttpStatus.BAD_REQUEST,
                                                        "Password is required",
                                                        request.getRequestURI()));
                }

                try {
                        // Attempt authentication
                        Authentication authentication = authenticationManager.authenticate(
                                        new UsernamePasswordAuthenticationToken(
                                                        loginRequest.getUsername().trim(),
                                                        loginRequest.getPassword()));

                        // Create security context
                        SecurityContext context = SecurityContextHolder.createEmptyContext();
                        context.setAuthentication(authentication);

                        // Save context to session
                        securityContextRepository.saveContext(context, request, response);

                        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

                        // Log successful authentication
                        logger.info("User '{}' successfully authenticated from IP: {}. Session ID: {}",
                                        userDetails.getUsername(),
                                        request.getRemoteAddr(),
                                        request.getSession(false) != null ? request.getSession(false).getId() : "N/A");

                        return ResponseEntity.ok(new LoginResponse(userDetails.getUsername()));

                } catch (BadCredentialsException e) {
                        // Log authentication failure - invalid credentials
                        logger.warn("Authentication failed for username: '{}' from IP: {} - Invalid credentials",
                                        loginRequest.getUsername(), request.getRemoteAddr());
                        return ResponseEntity
                                        .status(HttpStatus.UNAUTHORIZED)
                                        .body(createErrorResponse(
                                                        HttpStatus.UNAUTHORIZED,
                                                        "Invalid username or password",
                                                        request.getRequestURI()));

                } catch (DisabledException e) {
                        // Log authentication failure - disabled account
                        logger.warn("Authentication failed for username: '{}' from IP: {} - Account disabled",
                                        loginRequest.getUsername(), request.getRemoteAddr());
                        return ResponseEntity
                                        .status(HttpStatus.FORBIDDEN)
                                        .body(createErrorResponse(
                                                        HttpStatus.FORBIDDEN,
                                                        "Account is disabled",
                                                        request.getRequestURI()));

                } catch (LockedException e) {
                        // Log authentication failure - locked account
                        logger.warn("Authentication failed for username: '{}' from IP: {} - Account locked",
                                        loginRequest.getUsername(), request.getRemoteAddr());
                        return ResponseEntity
                                        .status(HttpStatus.FORBIDDEN)
                                        .body(createErrorResponse(
                                                        HttpStatus.FORBIDDEN,
                                                        "Account is locked",
                                                        request.getRequestURI()));

                } catch (AuthenticationException e) {
                        // Log generic authentication failure
                        logger.error("Authentication failed for username: '{}' from IP: {} - {}",
                                        loginRequest.getUsername(), request.getRemoteAddr(), e.getMessage());
                        return ResponseEntity
                                        .status(HttpStatus.UNAUTHORIZED)
                                        .body(createErrorResponse(
                                                        HttpStatus.UNAUTHORIZED,
                                                        "Authentication failed: " + e.getMessage(),
                                                        request.getRequestURI()));

                } catch (Exception e) {
                        // Log unexpected errors
                        logger.error("Unexpected error during authentication for username: '{}' from IP: {}",
                                        loginRequest.getUsername(), request.getRemoteAddr(), e);
                        return ResponseEntity
                                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(createErrorResponse(
                                                        HttpStatus.INTERNAL_SERVER_ERROR,
                                                        "An unexpected error occurred during authentication",
                                                        request.getRequestURI()));
                }
        }

        @GetMapping("/me")
        public ResponseEntity<?> getCurrentUser(Authentication authentication) {

                if (authentication == null || !authentication.isAuthenticated()) {
                        logger.debug("Unauthenticated request to /auth/me endpoint");
                        return ResponseEntity
                                        .status(HttpStatus.UNAUTHORIZED)
                                        .body(createErrorResponse(
                                                        HttpStatus.UNAUTHORIZED,
                                                        "Not authenticated",
                                                        "/auth/me"));
                }

                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                logger.debug("User '{}' accessed /auth/me endpoint", userDetails.getUsername());

                return ResponseEntity.ok(
                                new LoginResponse(userDetails.getUsername()));
        }

        @PostMapping("/logout")
        public ResponseEntity<?> logout(HttpServletRequest request) {

                String username = "unknown";
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null && authentication.isAuthenticated()) {
                        Object principal = authentication.getPrincipal();
                        if (principal instanceof UserDetails) {
                                username = ((UserDetails) principal).getUsername();
                        }
                }

                HttpSession session = request.getSession(false);
                String sessionId = session != null ? session.getId() : "N/A";

                if (session != null) {
                        session.invalidate();
                }
                SecurityContextHolder.clearContext();

                logger.info("User '{}' logged out successfully. Session ID: {} invalidated", username, sessionId);

                return ResponseEntity.ok(
                                Map.of("message", "Logged out successfully"));
        }

        @GetMapping("/csrf")
        public CsrfToken csrf(CsrfToken token) {
                return token;
        }

        /**
         * Helper method to create standardized error responses
         */
        private ErrorResponse createErrorResponse(HttpStatus status, String message, String path) {
                return new ErrorResponse(
                                LocalDateTime.now(),
                                status.value(),
                                status.getReasonPhrase(),
                                message,
                                path);
        }

}
