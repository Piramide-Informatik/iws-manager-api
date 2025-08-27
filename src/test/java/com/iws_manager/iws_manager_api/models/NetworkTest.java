package com.iws_manager.iws_manager_api.models;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NetworkTest {

    @Test
    void testNetworkCreation() {
        // Arrange
        String networkName = "Global Network";
        
        // Act
        Network network = new Network();
        network.setNetworkName(networkName);
        
        // Assert
        assertEquals(networkName, network.getNetworkName());
    }

    @Test
    void testNetworkWithAuditFields() {
        // Arrange
        Network network = new Network();
        network.setNetworkName("Regional Network");
        LocalDateTime now = LocalDateTime.now();
        
        // Act
        network.setCreatedAt(now);
        network.setUpdatedAt(now);
        
        // Assert
        assertEquals(now, network.getCreatedAt());
        assertEquals(now, network.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Network network1 = new Network();
        network1.setId(1L);
        network1.setNetworkName("Main Network");
        
        Network network2 = new Network();
        network2.setId(1L);
        network2.setNetworkName("Main Network");
        
        Network network3 = new Network();
        network3.setId(2L);
        network3.setNetworkName("Backup Network");
        
        // Assert
        assertEquals(network1, network2);
        assertEquals(network1.hashCode(), network2.hashCode());
        assertNotEquals(network1, network3);
        assertNotEquals(network1.hashCode(), network3.hashCode());
    }
}