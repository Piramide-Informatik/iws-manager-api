package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

class ChanceTest {

    @Test
    void testChanceCreation() {
        // Arrange
        BigDecimal probability = new BigDecimal("75.50");
        
        // Act
        Chance chance = new Chance();
        chance.setProbability(probability);
        
        // Assert
        assertEquals(probability, chance.getProbability());
    }

    @Test
    void testChanceWithAllFields() {
        // Arrange
        BigDecimal probability = new BigDecimal("90.25");
        
        // Act
        Chance chance = new Chance();
        chance.setId(1L);
        chance.setProbability(probability);
        
        // Assert
        assertAll("Chance properties",
            () -> assertEquals(1L, chance.getId()),
            () -> assertEquals(probability, chance.getProbability())
        );
    }

    @Test
    void testChanceWithAuditFields() {
        // Arrange
        Chance chance = new Chance();
        chance.setProbability(new BigDecimal("60.00"));
        LocalDateTime now = LocalDateTime.now();
        
        // Act
        chance.setCreatedAt(now);
        chance.setUpdatedAt(now);
        
        // Assert
        assertEquals(now, chance.getCreatedAt());
        assertEquals(now, chance.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Chance chance1 = new Chance();
        chance1.setProbability(new BigDecimal("80.00"));
        chance1.setId(1L);
        
        Chance chance2 = new Chance();
        chance2.setProbability(new BigDecimal("80.00"));
        chance2.setId(1L);
        
        // Assert
        assertEquals(chance1, chance2);
        assertEquals(chance1.hashCode(), chance2.hashCode());
    }

    @Test
    void testChanceWithNullProbability() {
        // Arrange & Act
        Chance chance = new Chance();
        chance.setId(1L);
        chance.setProbability(null);
        
        // Assert - El campo probability debe aceptar null según la estructura de la BD
        assertAll("Chance with null probability",
            () -> assertEquals(1L, chance.getId()),
            () -> assertNull(chance.getProbability())
        );
    }

    @Test
    void testChanceWithDifferentProbabilities() {
        // Arrange
        BigDecimal probability1 = new BigDecimal("25.75");
        BigDecimal probability2 = new BigDecimal("99.99");
        
        // Act
        Chance chance1 = new Chance();
        chance1.setId(1L);
        chance1.setProbability(probability1);
        
        Chance chance2 = new Chance();
        chance2.setId(2L);
        chance2.setProbability(probability2);
        
        // Assert
        assertEquals(probability1, chance1.getProbability());
        assertEquals(probability2, chance2.getProbability());
        assertNotEquals(chance1.getProbability(), chance2.getProbability());
    }
}