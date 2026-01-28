package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.dtos.auth.LoginRequest;
import com.iws_manager.iws_manager_api.exception.GlobalExceptionHandler.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AuthController
 * Tests authentication error responses, edge cases, and validation
 */
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @InjectMocks
    private AuthController authController;

    private LoginRequest validLoginRequest;
    private UserDetails mockUserDetails;
    private Authentication mockAuthentication;

    @BeforeEach
    void setUp() {
        validLoginRequest = new LoginRequest();
        validLoginRequest.setUsername("admin");
        validLoginRequest.setPassword("admin123");

        // Create mock user details
        mockUserDetails = User.builder()
                .username("admin")
                .password("admin123")
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
                .build();

        // Create mock authentication
        mockAuthentication = new UsernamePasswordAuthenticationToken(
                mockUserDetails,
                "admin123",
                mockUserDetails.getAuthorities());

        // Setup default mock behaviors (lenient to avoid unnecessary stubbing warnings)
        lenient().when(request.getRemoteAddr()).thenReturn("127.0.0.1");
        lenient().when(request.getRequestURI()).thenReturn("/auth/login");
    }

    // ==================== EDGE CASE TESTS ====================

    @Test
    @DisplayName("Should return 400 when login request is null")
    void testLoginWithNullRequest() {
        ResponseEntity<?> response = authController.authenticateUser(null, request, this.response);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertNotNull(errorResponse);
        assertEquals(400, errorResponse.getStatus());
        assertEquals("Login request body is required", errorResponse.getMessage());
    }

    @Test
    @DisplayName("Should return 400 when username is null")
    void testLoginWithNullUsername() {
        LoginRequest request = new LoginRequest();
        request.setUsername(null);
        request.setPassword("password123");

        ResponseEntity<?> response = authController.authenticateUser(request, this.request, this.response);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Username is required", errorResponse.getMessage());
    }

    @Test
    @DisplayName("Should return 400 when username is empty")
    void testLoginWithEmptyUsername() {
        LoginRequest request = new LoginRequest();
        request.setUsername("");
        request.setPassword("password123");

        ResponseEntity<?> response = authController.authenticateUser(request, this.request, this.response);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Username is required", errorResponse.getMessage());
    }

    @Test
    @DisplayName("Should return 400 when username is only whitespace")
    void testLoginWithWhitespaceUsername() {
        LoginRequest request = new LoginRequest();
        request.setUsername("   ");
        request.setPassword("password123");

        ResponseEntity<?> response = authController.authenticateUser(request, this.request, this.response);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Username is required", errorResponse.getMessage());
    }

    @Test
    @DisplayName("Should return 400 when password is null")
    void testLoginWithNullPassword() {
        LoginRequest request = new LoginRequest();
        request.setUsername("testuser");
        request.setPassword(null);

        ResponseEntity<?> response = authController.authenticateUser(request, this.request, this.response);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Password is required", errorResponse.getMessage());
    }

    @Test
    @DisplayName("Should return 400 when password is empty")
    void testLoginWithEmptyPassword() {
        LoginRequest request = new LoginRequest();
        request.setUsername("testuser");
        request.setPassword("");

        ResponseEntity<?> response = authController.authenticateUser(request, this.request, this.response);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Password is required", errorResponse.getMessage());
    }

    // ==================== AUTHENTICATION ERROR TESTS ====================

    @Test
    @DisplayName("Should return 401 with standardized error for invalid credentials")
    void testLoginWithInvalidCredentials() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        ResponseEntity<?> response = authController.authenticateUser(validLoginRequest, request, this.response);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertNotNull(errorResponse);
        assertEquals(401, errorResponse.getStatus());
        assertEquals("Invalid username or password", errorResponse.getMessage());
        assertEquals("Unauthorized", errorResponse.getError());
    }

    @Test
    @DisplayName("Should return 403 when account is disabled")
    void testLoginWithDisabledAccount() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new DisabledException("User account is disabled"));

        ResponseEntity<?> response = authController.authenticateUser(validLoginRequest, request, this.response);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertNotNull(errorResponse);
        assertEquals(403, errorResponse.getStatus());
        assertEquals("Account is disabled", errorResponse.getMessage());
    }

    @Test
    @DisplayName("Should return 403 when account is locked")
    void testLoginWithLockedAccount() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new LockedException("User account is locked"));

        ResponseEntity<?> response = authController.authenticateUser(validLoginRequest, request, this.response);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertNotNull(errorResponse);
        assertEquals(403, errorResponse.getStatus());
        assertEquals("Account is locked", errorResponse.getMessage());
    }

    @Test
    @DisplayName("Should return 500 for unexpected exceptions")
    void testLoginWithUnexpectedException() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<?> response = authController.authenticateUser(validLoginRequest, request, this.response);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertNotNull(errorResponse);
        assertEquals(500, errorResponse.getStatus());
        assertTrue(errorResponse.getMessage().contains("unexpected error"));
    }

    // ==================== SUCCESSFUL AUTHENTICATION TESTS ====================

    @Test
    @DisplayName("Should successfully authenticate with valid credentials")
    void testSuccessfulLogin() {
        when(request.getSession(false)).thenReturn(session);
        when(request.getSession(true)).thenReturn(session);
        when(session.getId()).thenReturn("TEST-SESSION-ID");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuthentication);

        ResponseEntity<?> response = authController.authenticateUser(validLoginRequest, request, this.response);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    @DisplayName("Should trim whitespace from username before authentication")
    void testLoginWithWhitespaceAroundUsername() {
        LoginRequest request = new LoginRequest();
        request.setUsername("  admin  ");
        request.setPassword("admin123");

        when(this.request.getSession(false)).thenReturn(session);
        when(this.request.getSession(true)).thenReturn(session);
        when(session.getId()).thenReturn("TEST-SESSION-ID");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuthentication);

        ResponseEntity<?> response = authController.authenticateUser(request, this.request, this.response);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the username was trimmed
        verify(authenticationManager, times(1)).authenticate(
                argThat(auth -> auth.getPrincipal().equals("admin")));
    }

    // ==================== ERROR RESPONSE FORMAT TESTS ====================

    @Test
    @DisplayName("Should return standardized error response format")
    void testStandardizedErrorResponseFormat() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        ResponseEntity<?> response = authController.authenticateUser(validLoginRequest, request, this.response);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertNotNull(errorResponse);
        assertNotNull(errorResponse.getTimestamp());
        assertTrue(errorResponse.getStatus() > 0);
        assertNotNull(errorResponse.getError());
        assertNotNull(errorResponse.getMessage());
        assertNotNull(errorResponse.getPath());
    }

    @Test
    @DisplayName("Should include correct path in error response")
    void testErrorResponseIncludesCorrectPath() {
        LoginRequest request = new LoginRequest();
        request.setUsername("");
        request.setPassword("password");

        ResponseEntity<?> response = authController.authenticateUser(request, this.request, this.response);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertNotNull(errorResponse);
        assertEquals("/auth/login", errorResponse.getPath());
    }

    @Test
    @DisplayName("Should verify all required fields in error response")
    void testErrorResponseHasAllRequiredFields() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        ResponseEntity<?> response = authController.authenticateUser(validLoginRequest, request, this.response);

        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertNotNull(errorResponse);
        assertNotNull(errorResponse.getTimestamp(), "Timestamp should not be null");
        assertEquals(401, errorResponse.getStatus(), "Status should be 401");
        assertEquals("Unauthorized", errorResponse.getError(), "Error should be 'Unauthorized'");
        assertEquals("Invalid username or password", errorResponse.getMessage(), "Message should match");
        assertEquals("/auth/login", errorResponse.getPath(), "Path should be '/auth/login'");
    }

    // ==================== LOGOUT TESTS ====================

    @Test
    @DisplayName("Should logout successfully when session exists")
    void testLogoutWithSession() {
        when(request.getSession(false)).thenReturn(session);

        ResponseEntity<?> response = authController.logout(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(session, times(1)).invalidate();
    }

    @Test
    @DisplayName("Should logout successfully when no session exists")
    void testLogoutWithoutSession() {
        when(request.getSession(false)).thenReturn(null);

        ResponseEntity<?> response = authController.logout(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(session, never()).invalidate();
    }
}
