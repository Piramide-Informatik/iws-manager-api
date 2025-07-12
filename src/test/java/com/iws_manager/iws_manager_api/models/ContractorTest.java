package com.iws_manager.iws_manager_api.models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

class ContractorTest {

    @Test
    void testContractorCreation() {
        // Arrange
        Country country = new Country();
        country.setId(1L);
        
        Customer customer = new Customer();
        customer.setId(1L);

        // Act
        Contractor contractor = new Contractor();
        contractor.setCity("Berlin");
        contractor.setLabel("Main Contractor");
        contractor.setName("BauTech GmbH");
        contractor.setNumber("CT-001");
        contractor.setCountry(country);
        contractor.setCustomer(customer);
        contractor.setStreet("Main Street 123");
        contractor.setTaxNumber("DE123456789");
        contractor.setZipCode("10115");

        // Assert
        assertNotNull(contractor);
        assertEquals("Berlin", contractor.getCity());
        assertEquals("Main Contractor", contractor.getLabel());
        assertEquals("BauTech GmbH", contractor.getName());
        assertEquals("CT-001", contractor.getNumber());
        assertEquals(country, contractor.getCountry());
        assertEquals(customer, contractor.getCustomer());
        assertEquals("Main Street 123", contractor.getStreet());
        assertEquals("DE123456789", contractor.getTaxNumber());
        assertEquals("10115", contractor.getZipCode());
    }

    @Test
    void testContractorWithAuditFields() {
        // Arrange
        Contractor contractor = new Contractor();
        LocalDateTime now = LocalDateTime.now();
        
        // Act
        contractor.setCreatedAt(now);
        contractor.setUpdatedAt(now.plusHours(1));

        // Assert
        assertEquals(now, contractor.getCreatedAt());
        assertEquals(now.plusHours(1), contractor.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Contractor contractor1 = new Contractor();
        contractor1.setId(1L);
        contractor1.setName("Contractor A");

        Contractor contractor2 = new Contractor();
        contractor2.setId(1L);
        contractor2.setName("Contractor A");

        // Act & Assert
        assertEquals(contractor1, contractor2);

        // Test hash code consistency
        assertEquals(contractor1.hashCode(), contractor2.hashCode());

        // Test null and other class
        assertNotEquals(contractor1, null);
        assertNotEquals(contractor1, new Object());

        // Test same object
        assertEquals(contractor1, contractor1);
    }

    @Test
    void testToString() {
        // Arrange
        Contractor contractor = new Contractor();
        contractor.setId(1L);
        contractor.setName("Test Contractor");
        contractor.setCity("Munich");

        // Act
        String toStringResult = contractor.toString();

        // Assert
        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("Test Contractor"));
        assertTrue(toStringResult.contains("Munich"));
        assertFalse(toStringResult.contains("subcontracts")); 
    }
}