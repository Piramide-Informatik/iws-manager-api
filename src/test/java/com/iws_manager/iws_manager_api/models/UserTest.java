package com.iws_manager.iws_manager_api.models;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserCreation() {
        // Arrange
        String email = "test@company.com";
        String password = "securePassword123";
        
        // Act
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        
        // Assert
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
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
