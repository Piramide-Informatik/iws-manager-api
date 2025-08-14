package com.iws_manager.iws_manager_api.models;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class OrderTest {
    private static final String ORDER_TITLE = "Proyecto de Investigación X";
    private static final Integer ORDER_NO = 1001;
    private static final LocalDate ORDER_DATE = LocalDate.of(2023, 6, 15);
    private static final BigDecimal ORDER_VALUE = new BigDecimal("50000.00");
    private static final Long ENTITY_ID = 1L;
    private static final Long ALTERNATIVE_ID = 2L;

    @Test
    void testOrderCreation() {
        // Arrange
        String acronym = "PRJ-X";
        LocalDate approvalDate = LocalDate.of(2023, 7, 1);
        
        // Act
        Order order = new Order();
        order.setAcronym(acronym);
        order.setOrderTitle(ORDER_TITLE);
        order.setOrderNo(ORDER_NO);
        order.setOrderDate(ORDER_DATE);
        order.setApprovalDate(approvalDate);
        order.setOrderValue(ORDER_VALUE);
        
        // Assert
        assertEquals(acronym, order.getAcronym());
        assertEquals(ORDER_TITLE, order.getOrderTitle());
        assertEquals(ORDER_NO, order.getOrderNo());
        assertEquals(ORDER_DATE, order.getOrderDate());
        assertEquals(approvalDate, order.getApprovalDate());
        assertEquals(0, ORDER_VALUE.compareTo(order.getOrderValue()));
    }

    @Test
    void testOrderWithAuditFields() {
        // Arrange
        Order order = new Order();
        order.setOrderTitle(ORDER_TITLE);
        LocalDateTime now = LocalDateTime.now();
        
        // Act
        order.setCreatedAt(now);
        order.setUpdatedAt(now);
        order.setVersion(1L);
        
        // Assert
        assertEquals(now, order.getCreatedAt());
        assertEquals(now, order.getUpdatedAt());
        assertEquals(1L, order.getVersion());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Order order1 = createBasicOrder(ENTITY_ID, ORDER_NO, ORDER_TITLE);
        Order order2 = createBasicOrder(ENTITY_ID, 1002, "Different Title");
        Order order3 = createBasicOrder(ALTERNATIVE_ID, ORDER_NO, ORDER_TITLE);
        
        // Assert
        assertEquals(order1, order2);  // Iguales por ID
        assertNotEquals(order1, order3); // Diferentes por ID
        assertEquals(order1.hashCode(), order2.hashCode());
        assertNotEquals(order1.hashCode(), order3.hashCode());
    }

    @Test
    void testDecimalFieldsPrecision() {
        // Arrange
        BigDecimal fixCommission = new BigDecimal("12.34");
        BigDecimal iwsProvision = new BigDecimal("1234.56");
        
        // Act
        Order order = new Order();
        order.setFixCommission(fixCommission);
        order.setIwsProvision(iwsProvision);
        
        // Assert
        assertEquals(2, order.getFixCommission().scale());
        assertEquals(2, order.getIwsProvision().scale());
        assertEquals(0, fixCommission.compareTo(order.getFixCommission()));
        assertEquals(0, iwsProvision.compareTo(order.getIwsProvision()));
    }

    @Test
    void testDateFields() {
        // Arrange
        LocalDate signatureDate = LocalDate.of(2023, 5, 10);
        LocalDate nextDeptDate = LocalDate.of(2023, 8, 20);
        
        // Act
        Order order = new Order();
        order.setSignatureDate(signatureDate);
        order.setNextDeptDate(nextDeptDate);
        
        // Assert
        assertEquals(signatureDate, order.getSignatureDate());
        assertEquals(nextDeptDate, order.getNextDeptDate());
    }

    private Order createBasicOrder(Long id, Integer orderNo, String title) {
        Order order = new Order();
        order.setId(id);
        order.setOrderNo(orderNo);
        order.setOrderTitle(title);
        return order;
    }

    @Test
    void testOrderWithCostTypeRelationship() {
        CostType type = new CostType();
        type.setId(1L);
        type.setType("Standard");
        
        Order order = new Order();
        order.setOrderNo(1001);
        order.setOrderType(type);
        
        assertAll(
            () -> assertEquals(1001, order.getOrderNo()),
            () -> assertEquals(1L, order.getOrderType().getId()),
            () -> assertEquals("Standard", order.getOrderType().getType())
        );
    }
}