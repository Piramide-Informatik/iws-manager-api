package com.iws_manager.iws_manager_api.models;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BillerTest {

    @Test
    void testBillerCreation() {
        // Arrange
        String billerName = "Finance Department";
        
        // Act
        Biller biller = new Biller();
        biller.setName(billerName);
        
        // Assert
        assertEquals(billerName, biller.getName());
    }

    @Test
    void testBillerWithAuditFields() {
        // Arrange
        Biller biller = new Biller();
        biller.setName("Accounting Division");
        LocalDateTime now = LocalDateTime.now();
        
        // Act
        biller.setCreatedAt(now);
        biller.setUpdatedAt(now);
        
        // Assert
        assertEquals(now, biller.getCreatedAt());
        assertEquals(now, biller.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Biller biller1 = new Biller();
        biller1.setId(1L);
        biller1.setName("Billing Department");
        
        Biller biller2 = new Biller();
        biller2.setId(1L);
        biller2.setName("Billing Department");
        
        Biller biller3 = new Biller();
        biller3.setId(2L);
        biller3.setName("Different Department");
        
        // Assert
        assertEquals(biller1, biller2);
        assertEquals(biller1.hashCode(), biller2.hashCode());
        assertNotEquals(biller1, biller3);
        assertNotEquals(biller1.hashCode(), biller3.hashCode());
    }
}