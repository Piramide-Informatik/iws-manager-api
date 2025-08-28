package com.iws_manager.iws_manager_api.models;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class ContractOrderCommissionTest {
    
    private static final BigDecimal COMMISSION = new BigDecimal("10.50");
    private static final BigDecimal FROM_ORDER_VALUE = new BigDecimal("1000.00");
    private static final BigDecimal MIN_COMMISSION = new BigDecimal("50.00");
    private static final Long ENTITY_ID = 1L;
    private static final Long ALTERNATIVE_ID = 2L;

    @Test
    void testContractOrderCommissionCreation() {
        // Arrange & Act
        ContractOrderCommission commission = new ContractOrderCommission();
        commission.setCommission(COMMISSION);
        commission.setFromOrderValue(FROM_ORDER_VALUE);
        commission.setMinCommission(MIN_COMMISSION);
        
        // Assert
        assertEquals(COMMISSION, commission.getCommission());
        assertEquals(FROM_ORDER_VALUE, commission.getFromOrderValue());
        assertEquals(MIN_COMMISSION, commission.getMinCommission());
    }

    @Test
    void testContractOrderCommissionWithAuditFields() {
        // Arrange
        ContractOrderCommission commission = new ContractOrderCommission();
        commission.setCommission(COMMISSION);
        LocalDateTime now = LocalDateTime.now();
        
        // Act
        commission.setCreatedAt(now);
        commission.setUpdatedAt(now);
        commission.setVersion(1L);
        
        // Assert
        assertEquals(now, commission.getCreatedAt());
        assertEquals(now, commission.getUpdatedAt());
        assertEquals(1L, commission.getVersion());
        assertEquals(COMMISSION, commission.getCommission());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        ContractOrderCommission commission1 = createBasicCommission(ENTITY_ID, COMMISSION);
        ContractOrderCommission commission2 = createBasicCommission(ENTITY_ID, new BigDecimal("20.00"));
        ContractOrderCommission commission3 = createBasicCommission(ALTERNATIVE_ID, COMMISSION);
        
        // Assert
        assertEquals(commission1, commission2);  // Iguales por ID
        assertNotEquals(commission1, commission3); // Diferentes por ID
        assertEquals(commission1.hashCode(), commission2.hashCode());
        assertNotEquals(commission1.hashCode(), commission3.hashCode());
    }

    @Test
    void testDecimalFieldsPrecision() {
        // Arrange
        BigDecimal commissionValue = new BigDecimal("12.34");
        BigDecimal fromOrderValue = new BigDecimal("1234.56");
        BigDecimal minCommissionValue = new BigDecimal("567.89");
        
        // Act
        ContractOrderCommission commission = new ContractOrderCommission();
        commission.setCommission(commissionValue);
        commission.setFromOrderValue(fromOrderValue);
        commission.setMinCommission(minCommissionValue);
        
        // Assert
        assertEquals(2, commission.getCommission().scale());
        assertEquals(2, commission.getFromOrderValue().scale());
        assertEquals(2, commission.getMinCommission().scale());
        assertEquals(0, commissionValue.compareTo(commission.getCommission()));
        assertEquals(0, fromOrderValue.compareTo(commission.getFromOrderValue()));
        assertEquals(0, minCommissionValue.compareTo(commission.getMinCommission()));
    }

    @Test
    void testNullValues() {
        // Arrange
        ContractOrderCommission commission = new ContractOrderCommission();
        
        // Act
        // No establecer ningún valor (todos null por defecto)
        
        // Assert
        assertNull(commission.getCommission());
        assertNull(commission.getFromOrderValue());
        assertNull(commission.getMinCommission());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange & Act
        ContractOrderCommission commission = new ContractOrderCommission(
            COMMISSION,
            null,  // employmentContract null
            FROM_ORDER_VALUE,
            MIN_COMMISSION
        );
        
        // Assert
        assertEquals(COMMISSION, commission.getCommission());
        assertNull(commission.getContract());
        assertEquals(FROM_ORDER_VALUE, commission.getFromOrderValue());
        assertEquals(MIN_COMMISSION, commission.getMinCommission());
    }

    private ContractOrderCommission createBasicCommission(Long id, BigDecimal commissionValue) {
        ContractOrderCommission commission = new ContractOrderCommission();
        commission.setId(id);
        commission.setCommission(commissionValue);
        return commission;
    }
}