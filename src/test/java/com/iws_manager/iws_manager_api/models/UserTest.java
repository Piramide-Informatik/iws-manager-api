package com.iws_manager.iws_manager_api.models;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserCreation() {
        // Arrange
        String email = "test@company.com";
        String rawPassword = "securePassword123";
        String first_name = "testFirstName";
        String last_name = "testLastName";
        String username = "testUsername";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // Act
        User user = new User();
        user.setEmail(email);
        user.setPassword(encoder.encode(rawPassword));// Hash here
        user.setFirst_name(first_name);
        user.setLast_name(last_name);
        user.setUsername(username);
        
        // Assert
        assertEquals(email, user.getEmail());
        assertTrue(encoder.matches(rawPassword, user.getPassword()));// Verify the hash
        assertEquals(first_name, user.getFirst_name());
        assertEquals(last_name, user.getLast_name());
        assertEquals(username, user.getUsername());
    }

    @Test
    void testUserWithAuditFields() {
        // Arrange
        User user = new User();
        user.setEmail("admin@company.com");
        LocalDateTime now = LocalDateTime.now();
        
        // Act
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        
        // Assert
        assertEquals(now, user.getCreatedAt());
        assertEquals(now, user.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        User user1 = new User();
        user1.setEmail("user1@company.com");
        user1.setId(1L);
        
        User user2 = new User();
        user2.setEmail("user1@company.com");
        user2.setId(1L);
        
        // Assert
        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testDefaultActiveStatus() {
        // Arrange & Act
        User user = new User();
        
        // Assert
        assertTrue(user.isActive());
    }

    @Test
    void testUserDeactivation() {
        // Arrange
        User user = new User();
        user.setEmail("inactive@company.com");
        
        // Act
        user.setActive(false);
        
        // Assert
        assertFalse(user.isActive());
    }
}
