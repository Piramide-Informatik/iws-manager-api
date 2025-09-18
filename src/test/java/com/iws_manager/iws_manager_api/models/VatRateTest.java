package com.iws_manager.iws_manager_api.models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;

class VatRateTest {

    private static final String TEST_RATE = "19.00";
    private static final LocalDate TEST_DATE = LocalDate.of(2024, 1, 1);
    private static final Long TEST_ID = 1L;

    @Test
    void testVatRateCreation() {
        // Arrange
        Vat vat = new Vat();
        vat.setId(TEST_ID);
        
        // Act
        VatRate vatRate = new VatRate();
        vatRate.setFromdate(TEST_DATE);
        vatRate.setRate(new BigDecimal(TEST_RATE));
        vatRate.setVat(vat);

        // Assert
        assertNotNull(vatRate);
        assertEquals(TEST_DATE, vatRate.getFromdate());
        assertEquals(new BigDecimal(TEST_RATE), vatRate.getRate());
        assertEquals(vat, vatRate.getVat());
    }

    @Test
    void testVatRateWithNullValues() {
        // Act
        VatRate vatRate = new VatRate();
        vatRate.setFromdate(null);
        vatRate.setRate(null);
        vatRate.setVat(null);

        // Assert
        assertNotNull(vatRate);
        assertNull(vatRate.getFromdate());
        assertNull(vatRate.getRate());
        assertNull(vatRate.getVat());
    }

    @Test
    void testVatRateWithAuditFields() {
        // Arrange
        VatRate vatRate = new VatRate();
        LocalDate now = LocalDate.now();
        
        // Act
        vatRate.setCreatedAt(now.atStartOfDay());
        vatRate.setUpdatedAt(now.plusDays(1).atStartOfDay());

        // Assert
        assertNotNull(vatRate.getCreatedAt());
        assertNotNull(vatRate.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        VatRate vatRate1 = new VatRate();
        vatRate1.setId(TEST_ID);
        vatRate1.setRate(new BigDecimal(TEST_RATE));

        VatRate vatRate2 = new VatRate();
        vatRate2.setId(TEST_ID);
        vatRate2.setRate(new BigDecimal(TEST_RATE));

        // Act & Assert
        assertEquals(vatRate1, vatRate2);
        assertEquals(vatRate1.hashCode(), vatRate2.hashCode());

        // Test null and other class
        assertNotEquals(vatRate1, null);
        assertNotEquals(vatRate1, new Object());

        // Test same object
        assertEquals(vatRate1, vatRate1);
    }

    @Test
    void testToString() {
        // Arrange
        VatRate vatRate = new VatRate();
        vatRate.setId(TEST_ID);
        vatRate.setRate(new BigDecimal(TEST_RATE));
        vatRate.setFromdate(TEST_DATE);

        // Act
        String toStringResult = vatRate.toString();

        // Assert
        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains(TEST_RATE));
    }
}