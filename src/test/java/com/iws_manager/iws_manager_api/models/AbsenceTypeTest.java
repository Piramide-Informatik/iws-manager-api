package com.iws_manager.iws_manager_api.models;

import java.time.LocalDateTime;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AbsenceTypeTest {

    private static final String NAME_PAID_LEAVE = "Paid Leave";
    private static final String LABEL_PAID_LEAVE = "PL";

    @Test
    public void testAbsenceTypeCreation() {
        AbsenceType absenceType = new AbsenceType();
        absenceType.setName(NAME_PAID_LEAVE);
        absenceType.setLabel(LABEL_PAID_LEAVE);
        absenceType.setHours((byte) 1);
        absenceType.setIsHoliday((byte) 1);
        absenceType.setShareOfDay(new BigDecimal("0.5"));

        assertEquals(NAME_PAID_LEAVE, absenceType.getName());
        assertEquals(LABEL_PAID_LEAVE, absenceType.getLabel());
        assertEquals((byte)1, absenceType.getHours());
        assertEquals((byte)1, absenceType.getIsHoliday());
        assertEquals(new BigDecimal("0.5"), absenceType.getShareOfDay());
    }

    @Test
    public void testEqualsAndHashCode() {
        AbsenceType a1 = new AbsenceType();
        a1.setId(1L);
        a1.setName(NAME_PAID_LEAVE);
        a1.setLabel(LABEL_PAID_LEAVE);

        AbsenceType a2 = new AbsenceType();
        a2.setId(1L);
        a2.setName(NAME_PAID_LEAVE);
        a2.setLabel(LABEL_PAID_LEAVE);

        assertEquals(a1, a2);
        assertEquals(a1.hashCode(), a2.hashCode());
    }

    @Test
    public void testToStringContainsNameAndLabel() {
        AbsenceType absenceType = new AbsenceType();
        absenceType.setName(NAME_PAID_LEAVE);
        absenceType.setLabel(LABEL_PAID_LEAVE);

        String toString = absenceType.toString();
        assertNotNull(toString);
        assertTrue(toString.contains(NAME_PAID_LEAVE));
        assertTrue(toString.contains(LABEL_PAID_LEAVE));
    }
}