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

    private static final String DEFAULT_USERNAME = "admin";
    private static final String VALID_CREDENTIAL = "admin123";
    private static final String ANOTHER_CREDENTIAL = "password123";
    private static final String USERNAME_REQUIRED = "Username is required";
    private static final String PASS_REQ_MSG = "Password is required";
    private static final String INVALID_CREDENTIALS = "Invalid username or password";

    private LoginRequest validLoginRequest;
    private Authentication mockAuthentication;

    @BeforeEach
    void setUp() {
        validLoginRequest = new LoginRequest();
        validLoginRequest.setUsername(DEFAULT_USERNAME);
        validLoginRequest.setPassword(VALID_CREDENTIAL);

        // Create mock user details
        UserDetails mockUserDetails = User.builder()
                .username(DEFAULT_USERNAME)
                .password(VALID_CREDENTIAL)
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
                .build();

        // Create mock authentication
        mockAuthentication = new UsernamePasswordAuthenticationToken(
                mockUserDetails,
                VALID_CREDENTIAL,
                mockUserDetails.getAuthorities());

        // Setup default mock behaviors (lenient to avoid unnecessary stubbing warnings)
        lenient().when(request.getRemoteAddr()).thenReturn("127.0.0.1");
        lenient().when(request.getRequestURI()).thenReturn("/auth/login");
    }

    // ==================== EDGE CASE TESTS ====================

    @Test
    @DisplayName("Should return 400 when login request is null")
    void testLoginWithNullRequest() {
        assertErrorResponse(login(null), HttpStatus.BAD_REQUEST, "Login request body is required");
    }

    @Test
    @DisplayName("Should return 400 when username is null")
    void testLoginWithNullUsername() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(null);
        loginRequest.setPassword(ANOTHER_CREDENTIAL);

        assertErrorResponse(login(loginRequest), HttpStatus.BAD_REQUEST, USERNAME_REQUIRED);
    }

    @Test
    @DisplayName("Should return 400 when username is empty")
    void testLoginWithEmptyUsername() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("");
        loginRequest.setPassword(ANOTHER_CREDENTIAL);

        assertErrorResponse(login(loginRequest), HttpStatus.BAD_REQUEST, USERNAME_REQUIRED);
    }

    @Test
    @DisplayName("Should return 400 when username is only whitespace")
    void testLoginWithWhitespaceUsername() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("   ");
        loginRequest.setPassword(ANOTHER_CREDENTIAL);

        assertErrorResponse(login(loginRequest), HttpStatus.BAD_REQUEST, USERNAME_REQUIRED);
    }

    @Test
    @DisplayName("Should return 400 when password is null")
    void testLoginWithNullPassword() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword(null);

        assertErrorResponse(login(loginRequest), HttpStatus.BAD_REQUEST, PASS_REQ_MSG);
    }

    @Test
    @DisplayName("Should return 400 when password is empty")
    void testLoginWithEmptyPassword() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("");

        assertErrorResponse(login(loginRequest), HttpStatus.BAD_REQUEST, PASS_REQ_MSG);
    }

    // ==================== AUTHENTICATION ERROR TESTS ====================

    @Test
    @DisplayName("Should return 401 with standardized error for invalid credentials")
    void testLoginWithInvalidCredentials() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        assertErrorResponse(login(validLoginRequest), HttpStatus.UNAUTHORIZED, INVALID_CREDENTIALS);
    }

    @Test
    @DisplayName("Should return 403 when account is disabled")
    void testLoginWithDisabledAccount() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new DisabledException("User account is disabled"));

        assertErrorResponse(login(validLoginRequest), HttpStatus.FORBIDDEN, "Account is disabled");
    }

    @Test
    @DisplayName("Should return 403 when account is locked")
    void testLoginWithLockedAccount() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new LockedException("User account is locked"));

        assertErrorResponse(login(validLoginRequest), HttpStatus.FORBIDDEN, "Account is locked");
    }

    @Test
    @DisplayName("Should return 500 for unexpected exceptions")
    void testLoginWithUnexpectedException() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Unexpected error"));

        assertErrorResponse(login(validLoginRequest), HttpStatus.INTERNAL_SERVER_ERROR, "contains:unexpected error");
    }

    // ==================== SUCCESSFUL AUTHENTICATION TESTS ====================

    @Test
    @DisplayName("Should successfully authenticate with valid credentials")
    void testSuccessfulLogin() {
        setupSuccessfulAuthMocks();

        ResponseEntity<?> authResponse = login(validLoginRequest);

        assertEquals(HttpStatus.OK, authResponse.getStatusCode());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    @DisplayName("Should trim whitespace from username before authentication")
    void testLoginWithWhitespaceAroundUsername() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("  " + DEFAULT_USERNAME + "  ");
        loginRequest.setPassword(VALID_CREDENTIAL);

        setupSuccessfulAuthMocks();

        ResponseEntity<?> authResponse = login(loginRequest);

        assertEquals(HttpStatus.OK, authResponse.getStatusCode());

        // Verify that the username was trimmed
        verify(authenticationManager, times(1)).authenticate(
                argThat(auth -> auth.getPrincipal().equals(DEFAULT_USERNAME)));
    }

    // ==================== ERROR RESPONSE FORMAT TESTS ====================

    @Test
    @DisplayName("Should standardized error response format")
    void testStandardizedErrorResponseFormat() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        ResponseEntity<?> authResponse = login(validLoginRequest);
        assertErrorResponse(authResponse, HttpStatus.UNAUTHORIZED, INVALID_CREDENTIALS);

        ErrorResponse errorResponse = (ErrorResponse) authResponse.getBody();
        assertNotNull(errorResponse.getTimestamp());
        assertNotNull(errorResponse.getError());
        assertEquals("/auth/login", errorResponse.getPath());
    }

    @Test
    @DisplayName("Should include correct path in error response")
    void testErrorResponseIncludesCorrectPath() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("");
        loginRequest.setPassword("password");

        assertErrorResponse(login(loginRequest), HttpStatus.BAD_REQUEST, USERNAME_REQUIRED);
    }

    // ==================== LOGOUT TESTS ====================

    @Test
    @DisplayName("Should logout successfully when session exists")
    void testLogoutWithSession() {
        when(this.request.getSession(false)).thenReturn(session);

        ResponseEntity<?> authResponse = authController.logout(this.request);

        assertEquals(HttpStatus.OK, authResponse.getStatusCode());
        verify(session, times(1)).invalidate();
    }

    @Test
    @DisplayName("Should logout successfully when no session exists")
    void testLogoutWithoutSession() {
        when(this.request.getSession(false)).thenReturn(null);

        ResponseEntity<?> authResponse = authController.logout(this.request);

        assertEquals(HttpStatus.OK, authResponse.getStatusCode());
        verify(session, never()).invalidate();
    }

    // ==================== HELPER METHODS ====================

    private void assertErrorResponse(ResponseEntity<?> response, HttpStatus expectedStatus, String expectedMessage) {
        assertEquals(expectedStatus, response.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertNotNull(errorResponse);
        assertEquals(expectedStatus.value(), errorResponse.getStatus());
        if (expectedMessage != null) {
            if (expectedMessage.startsWith("contains:")) {
                assertTrue(
                        errorResponse.getMessage().toLowerCase().contains(expectedMessage.substring(9).toLowerCase()));
            } else {
                assertEquals(expectedMessage, errorResponse.getMessage());
            }
        }
    }

    private ResponseEntity<?> login(LoginRequest loginRequest) {
        return authController.authenticateUser(loginRequest, request, response);
    }

    private void setupSuccessfulAuthMocks() {
        when(request.getSession(false)).thenReturn(session);
        when(request.getSession(true)).thenReturn(session);
        when(session.getId()).thenReturn("TEST-SESSION-ID");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuthentication);
    }
}
