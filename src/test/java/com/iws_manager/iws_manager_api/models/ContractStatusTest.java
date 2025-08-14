package com.iws_manager.iws_manager_api.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ContractStatusTest {

    @Test
    void testContractStatusCreation() {
        // Arrange
        String status = "Status1";
        BigDecimal chance = new BigDecimal("123.45");
        
        // Act
        ContractStatus contractStatus = new ContractStatus();
        contractStatus.setStatus(status);
        contractStatus.setChance(chance);
        
        // Assert
        assertEquals(status, contractStatus.getStatus());
        assertEquals(chance, contractStatus.getChance());
    }

    @Test
    void testContractStatusWithAuditFields() {
        // Arrange
        ContractStatus contractStatus = new ContractStatus();
        contractStatus.setStatus("Opened");
        LocalDateTime now = LocalDateTime.now();
        
        // Act
        contractStatus.setCreatedAt(now);
        contractStatus.setUpdatedAt(now);
        
        // Assert
        assertEquals(now, contractStatus.getCreatedAt());
        assertEquals(now, contractStatus.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        ContractStatus contractStatus1 = new ContractStatus();
        contractStatus1.setStatus("Closed");
        contractStatus1.setId(1L); 
        
        ContractStatus contractStatus2 = new ContractStatus();
        contractStatus2.setStatus("Closed");
        contractStatus2.setId(1L);
        
        // Assert
        assertEquals(contractStatus1, contractStatus2);
        assertEquals(contractStatus1.hashCode(), contractStatus2.hashCode());
    }
}