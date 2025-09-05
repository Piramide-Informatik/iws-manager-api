package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class IwsCommissionTest {
    BigDecimal commission;
    private IwsCommission iwsCommission;

    @BeforeEach
    void setUp() {

        iwsCommission = new IwsCommission();
        commission = BigDecimal.valueOf(2);
    }
    @Test
    void testHolidayYearCreation() {
        iwsCommission.setCommission(commission);

        assertEquals(commission, iwsCommission.getCommission());
    }
    @Test
    void testHolidayYearWithAuditFields(){
        iwsCommission.setCommission(commission);
        LocalDateTime now = LocalDateTime.now();

        iwsCommission.setCreatedAt(now);
        iwsCommission.setUpdatedAt(now);

        assertEquals(now, iwsCommission.getCreatedAt());
        assertEquals(now, iwsCommission.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        iwsCommission.setCommission(commission);
        iwsCommission.setId(1L);

        IwsCommission iwsCommission2 = new IwsCommission();
        iwsCommission2.setCommission(commission);
        iwsCommission2.setId(1L);

        assertEquals(iwsCommission, iwsCommission2);
        assertEquals(iwsCommission.hashCode(), iwsCommission2.hashCode());
    }
}