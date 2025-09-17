package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SystemParameterTest {

    private SystemParameter systemParam = new SystemParameter();
    private String name = "tax_rate";
    private String valueChar = "standard";
    private BigDecimal valueNum = new BigDecimal("19.00");
    private LocalDateTime now = LocalDateTime.now();

    @Test
    void testSystemParameterCreation() {
        // Act
        systemParam.setName(name);
        systemParam.setValueChar(valueChar);
        systemParam.setValueNum(valueNum);

        // Assert
        assertEquals(name, systemParam.getName());
        assertEquals(valueChar, systemParam.getValueChar());
        assertEquals(valueNum, systemParam.getValueNum());
    }

    @Test
    void testSystemParameterWithAuditFields() {
        // Arrange
        systemParam.setName(name);

        // Act
        systemParam.setCreatedAt(now);
        systemParam.setUpdatedAt(now);

        // Assert
        assertEquals(now, systemParam.getCreatedAt());
        assertEquals(now, systemParam.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        systemParam.setName(name);
        systemParam.setValueChar(valueChar);
        systemParam.setId(1L);

        SystemParameter systemParam2 = new SystemParameter();
        systemParam2.setName(name);
        systemParam2.setValueChar(valueChar);
        systemParam2.setId(1L);

        SystemParameter systemParam3 = new SystemParameter();
        systemParam3.setName("discount_rate");
        systemParam3.setValueNum(new BigDecimal("5.00"));
        systemParam3.setId(2L);

        // Assert - Same ID should be equal
        assertEquals(systemParam, systemParam2);
        assertEquals(systemParam.hashCode(), systemParam2.hashCode());

        // Assert - Different ID should not be equal
        assertNotEquals(systemParam, systemParam3);
        assertNotEquals(systemParam.hashCode(), systemParam3.hashCode());
    }

    @Test
    void testNullValues() {
        // Act
        systemParam.setName("null_values");
        systemParam.setValueChar(null);
        systemParam.setValueNum(null);

        // Assert
        assertEquals("null_values", systemParam.getName());
        assertNull(systemParam.getValueChar());
        assertNull(systemParam.getValueNum());
    }

    @Test
    void testNumericValuePrecision() {
        // Arrange
        BigDecimal preciseValue = new BigDecimal("12345678.99");

        // Act
        systemParam.setValueNum(preciseValue);

        // Assert
        assertEquals(preciseValue, systemParam.getValueNum());
        assertEquals(10, systemParam.getValueNum().precision());
        assertEquals(2, systemParam.getValueNum().scale());
    }

    @Test
    void testCharacterValueMaxLength() {
        // Arrange
        String longValue = "This is a very long character value that should be properly stored " +
                          "in the VARCHAR(255) column. The maximum length is 255 characters.";

        // Act
        systemParam.setValueChar(longValue);

        // Assert
        assertEquals(longValue, systemParam.getValueChar());
        assertTrue(systemParam.getValueChar().length() <= 255);
    }

    @Test
    void testOnlyCharacterValue() {
        // Act
        systemParam.setName("char_only");
        systemParam.setValueChar("character_value");
        systemParam.setValueNum(null);

        // Assert
        assertEquals("char_only", systemParam.getName());
        assertEquals("character_value", systemParam.getValueChar());
        assertNull(systemParam.getValueNum());
    }

    @Test
    void testOnlyNumericValue() {
        // Act
        systemParam.setName("num_only");
        systemParam.setValueChar(null);
        systemParam.setValueNum(new BigDecimal("99.50"));

        // Assert
        assertEquals("num_only", systemParam.getName());
        assertNull(systemParam.getValueChar());
        assertEquals(new BigDecimal("99.50"), systemParam.getValueNum());
    }
}