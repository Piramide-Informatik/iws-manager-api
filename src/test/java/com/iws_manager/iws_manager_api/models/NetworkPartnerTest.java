package com.iws_manager.iws_manager_api.models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

class NetworkPartnerTest {

    @Test
    void testNetworkPartnerCreation() {
        // Arrange
        ContactPerson contactPerson = new ContactPerson();
        contactPerson.setId(1L);
        
        Customer partner = new Customer();
        partner.setId(2L);
        
        Network network = new Network();
        network.setId(3L);

        // Act
        NetworkPartner networkPartner = new NetworkPartner();
        networkPartner.setComment("Test comment");
        networkPartner.setContact(contactPerson);
        networkPartner.setPartner(partner);
        networkPartner.setNetwork(network);
        networkPartner.setPartnerno(123);

        // Assert
        assertNotNull(networkPartner);
        assertEquals("Test comment", networkPartner.getComment());
        assertEquals(contactPerson, networkPartner.getContact());
        assertEquals(partner, networkPartner.getPartner());
        assertEquals(network, networkPartner.getNetwork());
        assertEquals(123, networkPartner.getPartnerno());
    }

    @Test
    void testNetworkPartnerWithNullValues() {
        // Act
        NetworkPartner networkPartner = new NetworkPartner();
        networkPartner.setComment(null);
        networkPartner.setContact(null);
        networkPartner.setPartner(null);
        networkPartner.setNetwork(null);
        networkPartner.setPartnerno(null);

        // Assert
        assertNotNull(networkPartner);
        assertNull(networkPartner.getComment());
        assertNull(networkPartner.getContact());
        assertNull(networkPartner.getPartner());
        assertNull(networkPartner.getNetwork());
        assertNull(networkPartner.getPartnerno());
    }

    @Test
    void testNetworkPartnerWithAuditFields() {
        // Arrange
        NetworkPartner networkPartner = new NetworkPartner();
        LocalDateTime now = LocalDateTime.now();
        
        // Act
        networkPartner.setCreatedAt(now);
        networkPartner.setUpdatedAt(now.plusHours(1));

        // Assert
        assertEquals(now, networkPartner.getCreatedAt());
        assertEquals(now.plusHours(1), networkPartner.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        NetworkPartner networkPartner1 = new NetworkPartner();
        networkPartner1.setId(1L);
        networkPartner1.setComment("Test");

        NetworkPartner networkPartner2 = new NetworkPartner();
        networkPartner2.setId(1L);
        networkPartner2.setComment("Test");

        // Act & Assert
        assertEquals(networkPartner1, networkPartner2);
        assertEquals(networkPartner1.hashCode(), networkPartner2.hashCode());

        // Test null and other class
        assertNotEquals(networkPartner1, null);
        assertNotEquals(networkPartner1, new Object());

        // Test same object
        assertEquals(networkPartner1, networkPartner1);
    }

    @Test
    void testNetworkPartnerWithEmptyComment() {
        // Act
        NetworkPartner networkPartner = new NetworkPartner();
        networkPartner.setComment("");

        // Assert
        assertEquals("", networkPartner.getComment());
    }
}