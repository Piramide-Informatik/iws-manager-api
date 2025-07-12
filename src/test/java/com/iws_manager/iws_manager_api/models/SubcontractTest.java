package com.iws_manager.iws_manager_api.models;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class SubcontractTest {

    @Test
    void testSubcontractCreation() {
        // Arrange
        Contractor contractor = new Contractor();
        contractor.setId(1L);
        
        Customer customer = new Customer();
        customer.setId(1L);
        
        LocalDate testDate = LocalDate.now();
        BigDecimal testAmount = new BigDecimal("1234.56");

        // Act
        Subcontract subcontract = new Subcontract();
        subcontract.setAfamonths(true);
        subcontract.setContractor(contractor);
        subcontract.setContractTitle("Construction Work");
        subcontract.setCustomer(customer);
        subcontract.setDate(testDate);
        subcontract.setDescription("Foundation construction");
        subcontract.setInvoiceAmount(testAmount);
        subcontract.setInvoiceDate(testDate.plusDays(30));
        subcontract.setInvoiceGross(testAmount);
        subcontract.setInvoiceNet(testAmount);
        subcontract.setInvoiceNo("INV-2023-001");
        subcontract.setIsAfa(true);
        subcontract.setNetOrGross(true);
        subcontract.setNote("Urgent project");

        // Assert
        assertNotNull(subcontract);
        assertTrue(subcontract.getAfamonths());
        assertEquals(contractor, subcontract.getContractor());
        assertEquals("Construction Work", subcontract.getContractTitle());
        assertEquals(customer, subcontract.getCustomer());
        assertEquals(testDate, subcontract.getDate());
        assertEquals("Foundation construction", subcontract.getDescription());
        assertEquals(testAmount, subcontract.getInvoiceAmount());
        assertEquals(testDate.plusDays(30), subcontract.getInvoiceDate());
        assertEquals(testAmount, subcontract.getInvoiceGross());
        assertEquals(testAmount, subcontract.getInvoiceNet());
        assertEquals("INV-2023-001", subcontract.getInvoiceNo());
        assertTrue(subcontract.getIsAfa());
        assertTrue(subcontract.getNetOrGross());
        assertEquals("Urgent project", subcontract.getNote());
    }

    @Test
    void testSubcontractWithAuditFields() {
        // Arrange
        Subcontract subcontract = new Subcontract();
        LocalDateTime now = LocalDateTime.now();
        
        // Act
        subcontract.setCreatedAt(now);
        subcontract.setUpdatedAt(now.plusHours(1));

        // Assert
        assertEquals(now, subcontract.getCreatedAt());
        assertEquals(now.plusHours(1), subcontract.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Subcontract subcontract1 = new Subcontract();
        subcontract1.setId(1L);
        subcontract1.setInvoiceNo("INV-001");

        Subcontract subcontract2 = new Subcontract();
        subcontract2.setId(1L);
        subcontract2.setInvoiceNo("INV-001"); 

        // Act & Assert
        // Test equality based on ID only
        assertEquals(subcontract1, subcontract2);

        // Test hash code consistency
        assertEquals(subcontract1.hashCode(), subcontract2.hashCode());

        // Test edge cases
        assertNotEquals(subcontract1, null);
        assertNotEquals(subcontract1, new Object());
        assertEquals(subcontract1, subcontract1); // Reflexivity
    }

    @Test
    void testToString() {
        // Arrange
        Subcontract subcontract = new Subcontract();
        subcontract.setId(1L);
        subcontract.setInvoiceNo("INV-2023-001");
        subcontract.setContractTitle("Test Contract");

        // Act
        String toStringResult = subcontract.toString();

        // Assert
        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("INV-2023-001"));
        assertTrue(toStringResult.contains("Test Contract"));
        assertFalse(toStringResult.contains("subcontractProjects")); // Because of @ToString.Exclude
    }
}