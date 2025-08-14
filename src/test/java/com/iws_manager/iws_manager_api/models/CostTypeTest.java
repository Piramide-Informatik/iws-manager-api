package com.iws_manager.iws_manager_api.models;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CostTypeTest {

    @Test
    void testCostTypeCreation() {
        CostType costType = new CostType();
        costType.setType("Standard");
        costType.setSequenceNo(1);
        
        assertAll(
            () -> assertEquals("Standard", costType.getType()),
            () -> assertEquals(1, costType.getSequenceNo())
        );
    }

    @Test
    void testWithAuditFields() {
        // Arrange
        CostType ct = new CostType();
        ct.setType("cost type 1");
        LocalDateTime now = LocalDateTime.now();
        
        // Act
        ct.setCreatedAt(now);
        ct.setUpdatedAt(now);
        
        // Assert
        assertEquals(now, ct.getCreatedAt());
        assertEquals(now, ct.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        CostType type1 = new CostType();
        type1.setId(1L);
        type1.setType("Type A");
        
        CostType type2 = new CostType();
        type2.setId(1L);
        type2.setType("Type A");
        
        CostType type3 = new CostType();
        type3.setId(2L);
        type3.setType("Type B");
        
        assertAll(
            () -> assertEquals(type1, type2),
            () -> assertEquals(type1.hashCode(), type2.hashCode()),
            () -> assertNotEquals(type1, type3)
        );
    }
}