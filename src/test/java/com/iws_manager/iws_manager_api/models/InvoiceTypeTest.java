package com.iws_manager.iws_manager_api.models;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InvoiceTypeTest {

    @Test
    void testInvoiceTypeCreation() {
        // Arrange
        String invoiceTypeName = "Direct Billing";
        
        // Act
        InvoiceType invoiceType = new InvoiceType();
        invoiceType.setName(invoiceTypeName);
        
        // Assert
        assertEquals(invoiceTypeName, invoiceType.getName());
    }

    @Test
    void testInvoiceTypeWithAuditFields() {
        // Arrange
        InvoiceType invoiceType = new InvoiceType();
        invoiceType.setName("Network Billing");
        LocalDateTime now = LocalDateTime.now();
        
        // Act
        invoiceType.setCreatedAt(now);
        invoiceType.setUpdatedAt(now);
        
        // Assert
        assertEquals(now, invoiceType.getCreatedAt());
        assertEquals(now, invoiceType.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        InvoiceType type1 = new InvoiceType();
        type1.setId(1L);
        type1.setName("Direct");
        
        InvoiceType type2 = new InvoiceType();
        type2.setId(1L);
        type2.setName("Direct");
        
        InvoiceType type3 = new InvoiceType();
        type3.setId(2L);
        type3.setName("Network");
        
        // Assert
        assertEquals(type1, type2);
        assertEquals(type1.hashCode(), type2.hashCode());
        assertNotEquals(type1, type3);
        assertNotEquals(type1.hashCode(), type3.hashCode());
    }
}