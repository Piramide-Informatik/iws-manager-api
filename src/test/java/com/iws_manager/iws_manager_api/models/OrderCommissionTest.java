package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

class OrderCommissionTest {

    private static final BigDecimal COMMISSION = new BigDecimal("10.50");

    @Test
    void testOrderCommissionCreation() {
        // Arrange
        BigDecimal commission = new BigDecimal("5.75");
        BigDecimal fromOrderValue = new BigDecimal("1000.00");
        BigDecimal minCommission = new BigDecimal("25.00");
        
        // Act
        OrderCommission orderCommission = new OrderCommission();
        orderCommission.setCommission(commission);
        orderCommission.setFromOrderValue(fromOrderValue);
        orderCommission.setMinCommission(minCommission);
        
        // Assert
        assertEquals(commission, orderCommission.getCommission());
        assertEquals(fromOrderValue, orderCommission.getFromOrderValue());
        assertEquals(minCommission, orderCommission.getMinCommission());
    }

    @Test
    void testOrderCommissionWithAuditFields() {
        // Arrange
        OrderCommission orderCommission = new OrderCommission();
        LocalDateTime now = LocalDateTime.now();
        
        // Act
        orderCommission.setCreatedAt(now);
        orderCommission.setUpdatedAt(now);
        
        // Assert
        assertEquals(now, orderCommission.getCreatedAt());
        assertEquals(now, orderCommission.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        OrderCommission orderCommission1 = new OrderCommission();
        orderCommission1.setId(1L);
        orderCommission1.setCommission(COMMISSION);
        orderCommission1.setFromOrderValue(new BigDecimal("500.00"));
        
        OrderCommission orderCommission2 = new OrderCommission();
        orderCommission2.setId(1L);
        orderCommission2.setCommission(COMMISSION);
        orderCommission2.setFromOrderValue(new BigDecimal("500.00"));
        
        // Assert
        assertEquals(orderCommission1, orderCommission2);
        assertEquals(orderCommission1.hashCode(), orderCommission2.hashCode());
    }

    @Test
    void testEqualsAndHashCodeWithDifferentIds() {
        // Arrange
        OrderCommission orderCommission1 = new OrderCommission();
        orderCommission1.setId(1L);
        orderCommission1.setCommission(COMMISSION);
        
        OrderCommission orderCommission2 = new OrderCommission();
        orderCommission2.setId(2L);
        orderCommission2.setCommission(COMMISSION);
        
        // Assert
        assertNotEquals(orderCommission1, orderCommission2);
        assertNotEquals(orderCommission1.hashCode(), orderCommission2.hashCode());
    }

    @Test
    void testOrderCommissionWithOrderRelation() {
        // Arrange
        Order order = new Order();
        order.setId(100L);
        
        OrderCommission orderCommission = new OrderCommission();
        orderCommission.setCommission(new BigDecimal("15.00"));
        
        // Act
        orderCommission.setOrder(order);
        
        // Assert
        assertEquals(order, orderCommission.getOrder());
        assertEquals(100L, orderCommission.getOrder().getId());
    }

    @Test
    void testOrderCommissionWithNullValues() {
        // Arrange
        OrderCommission orderCommission = new OrderCommission();
        
        // Act
        orderCommission.setCommission(null);
        orderCommission.setFromOrderValue(null);
        orderCommission.setMinCommission(null);
        orderCommission.setOrder(null);
        
        // Assert
        assertNull(orderCommission.getCommission());
        assertNull(orderCommission.getFromOrderValue());
        assertNull(orderCommission.getMinCommission());
        assertNull(orderCommission.getOrder());
    }

    @Test
    void testOrderCommissionConstructor() {
        // Arrange
        BigDecimal commission = new BigDecimal("7.50");
        BigDecimal fromOrderValue = new BigDecimal("2000.00");
        BigDecimal minCommission = new BigDecimal("30.00");
        Order order = new Order();
        order.setId(200L);
        
        // Act - usando el constructor con todos los argumentos (gracias a @AllArgsConstructor de Lombok)
        OrderCommission orderCommission = new OrderCommission(commission, fromOrderValue, minCommission, order);
        
        // Assert
        assertEquals(commission, orderCommission.getCommission());
        assertEquals(fromOrderValue, orderCommission.getFromOrderValue());
        assertEquals(minCommission, orderCommission.getMinCommission());
        assertEquals(order, orderCommission.getOrder());
    }
}