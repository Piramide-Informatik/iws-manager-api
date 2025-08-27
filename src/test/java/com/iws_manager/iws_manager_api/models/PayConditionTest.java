package com.iws_manager.iws_manager_api.models;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PayConditionTest {

    @Test
    void testPayConditionCreation() {
        // Arrange
        Integer deadline = 30;
        String payConditionName = "Net 30 Days";
        
        // Act
        PayCondition payCondition = new PayCondition();
        payCondition.setDeadline(deadline);
        payCondition.setName(payConditionName);
        
        // Assert
        assertEquals(deadline, payCondition.getDeadline());
        assertEquals(payConditionName, payCondition.getName());
    }

    @Test
    void testPayConditionWithAuditFields() {
        // Arrange
        PayCondition payCondition = new PayCondition();
        payCondition.setDeadline(15);
        payCondition.setName("Net 15 Days");
        LocalDateTime now = LocalDateTime.now();
        
        // Act
        payCondition.setCreatedAt(now);
        payCondition.setUpdatedAt(now);
        
        // Assert
        assertEquals(now, payCondition.getCreatedAt());
        assertEquals(now, payCondition.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        PayCondition condition1 = new PayCondition();
        condition1.setId(1L);
        condition1.setDeadline(30);
        condition1.setName("Net 30");
        
        PayCondition condition2 = new PayCondition();
        condition2.setId(1L);
        condition2.setDeadline(30);
        condition2.setName("Net 30");
        
        PayCondition condition3 = new PayCondition();
        condition3.setId(2L);
        condition3.setDeadline(15);
        condition3.setName("Net 15");
        
        // Assert
        assertEquals(condition1, condition2);
        assertEquals(condition1.hashCode(), condition2.hashCode());
        assertNotEquals(condition1, condition3);
        assertNotEquals(condition1.hashCode(), condition3.hashCode());
    }
}