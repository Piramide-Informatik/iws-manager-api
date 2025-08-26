package com.iws_manager.iws_manager_api.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InvoiceTest {

    @Test
    void testInvoiceCreation() {
        // Arrange
        String comment = "Test invoice comment";
        Integer invoiceNo = 1001;
        Short isCancellation = 0;
        String note = "Test note";
        BigDecimal amountGross = new BigDecimal("1000.00");
        BigDecimal amountNet = new BigDecimal("800.00");
        String invoiceTitle = "Test Invoice";
        BigDecimal taxRate = new BigDecimal("19.00");
        
        // Act
        Invoice invoice = new Invoice();
        invoice.setComment(comment);
        invoice.setInvoiceNo(invoiceNo);
        invoice.setIsCancellation(isCancellation);
        invoice.setNote(note);
        invoice.setAmountGross(amountGross);
        invoice.setAmountNet(amountNet);
        invoice.setInvoiceTitle(invoiceTitle);
        invoice.setTaxRate(taxRate);
        
        // Assert
        assertEquals(comment, invoice.getComment());
        assertEquals(invoiceNo, invoice.getInvoiceNo());
        assertEquals(isCancellation, invoice.getIsCancellation());
        assertEquals(note, invoice.getNote());
        assertEquals(amountGross, invoice.getAmountGross());
        assertEquals(amountNet, invoice.getAmountNet());
        assertEquals(invoiceTitle, invoice.getInvoiceTitle());
        assertEquals(taxRate, invoice.getTaxRate());
    }

    @Test
    void testInvoiceWithAuditFields() {
        // Arrange
        Invoice invoice = new Invoice();
        invoice.setInvoiceNo(2001);
        invoice.setInvoiceTitle("Audit Test Invoice");
        LocalDateTime now = LocalDateTime.now();
        LocalDate paymentDate = LocalDate.now();
        
        // Act
        invoice.setCreatedAt(now);
        invoice.setUpdatedAt(now);
        invoice.setPaymentDate(paymentDate);
        
        // Assert
        assertEquals(now, invoice.getCreatedAt());
        assertEquals(now, invoice.getUpdatedAt());
        assertEquals(paymentDate, invoice.getPaymentDate());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Invoice invoice1 = new Invoice();
        invoice1.setId(1L);
        invoice1.setInvoiceNo(3001);
        invoice1.setComment("Test comment");
        invoice1.setAmountGross(new BigDecimal("500.00"));
        invoice1.setInvoiceTitle("Invoice 3001");
        
        Invoice invoice2 = new Invoice();
        invoice2.setId(1L);
        invoice2.setInvoiceNo(3001);
        invoice2.setComment("Test comment");
        invoice2.setAmountGross(new BigDecimal("500.00"));
        invoice2.setInvoiceTitle("Invoice 3001");
        
        Invoice invoice3 = new Invoice();
        invoice3.setId(2L);
        invoice3.setInvoiceNo(3002);
        invoice3.setComment("Different comment");
        invoice3.setAmountGross(new BigDecimal("700.00"));
        invoice3.setInvoiceTitle("Different Invoice");
        
        // Assert
        assertEquals(invoice1, invoice2);
        assertEquals(invoice1.hashCode(), invoice2.hashCode());
        assertNotEquals(invoice1, invoice3);
        assertNotEquals(invoice1.hashCode(), invoice3.hashCode());
    }
}