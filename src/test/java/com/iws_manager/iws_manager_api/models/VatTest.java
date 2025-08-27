package com.iws_manager.iws_manager_api.models;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VatTest {

    @Test
    void testVatCreation() {
        // Arrange
        String vatLabel = "Vollbesteuert (19%)";
        
        // Act
        Vat vat = new Vat();
        vat.setLabel(vatLabel);
        
        // Assert
        assertEquals(vatLabel, vat.getLabel());
    }

    @Test
    void testVatWithAuditFields() {
        // Arrange
        Vat vat = new Vat();
        vat.setLabel("Reduziert (7%)");
        LocalDateTime now = LocalDateTime.now();
        
        // Act
        vat.setCreatedAt(now);
        vat.setUpdatedAt(now);
        
        // Assert
        assertEquals(now, vat.getCreatedAt());
        assertEquals(now, vat.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Vat vat1 = new Vat();
        vat1.setId(1L);
        vat1.setLabel("Freibetrag");
        
        Vat vat2 = new Vat();
        vat2.setId(1L);
        vat2.setLabel("Freibetrag");
        
        Vat vat3 = new Vat();
        vat3.setId(2L);
        vat3.setLabel("Vollbesteuert");
        
        // Assert
        assertEquals(vat1, vat2);
        assertEquals(vat1.hashCode(), vat2.hashCode());
        assertNotEquals(vat1, vat3);
        assertNotEquals(vat1.hashCode(), vat3.hashCode());
    }
}