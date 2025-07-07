package com.iws_manager.iws_manager_api.models;

import java.time.LocalDateTime;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AbsenceDayTest {

   @Test
    public void testAbsenceDayCreation() {
        LocalDate date = LocalDate.of(2024, 6, 30);

        AbsenceType absenceType = new AbsenceType();
        absenceType.setId(1L);
        absenceType.setName("Sick Leave");

        AbsenceDay absenceDay = new AbsenceDay();
        absenceDay.setId(10L);
        absenceDay.setAbsenceDate(date);
        absenceDay.setAbsenceType(absenceType);

        assertEquals(10L, absenceDay.getId());
        assertEquals(date, absenceDay.getAbsenceDate());
        assertNotNull(absenceDay.getAbsenceType());
        assertEquals("Sick Leave", absenceDay.getAbsenceType().getName());
    }

    @Test
    public void testEqualsAndHashCode() {
        AbsenceDay a1 = new AbsenceDay();
        a1.setId(1L);

        AbsenceDay a2 = new AbsenceDay();
        a2.setId(1L);

        assertEquals(a1, a2);
        assertEquals(a1.hashCode(), a2.hashCode());
    }

    @Test
    public void testAbsenceDayWithAuditFields() {
        AbsenceDay absenceDay = new AbsenceDay();
        absenceDay.setCreatedAt(LocalDateTime.now().minusDays(1));
        absenceDay.setUpdatedAt(LocalDateTime.now());

        assertNotNull(absenceDay.getCreatedAt());
        assertNotNull(absenceDay.getUpdatedAt());
        assertTrue(absenceDay.getCreatedAt().isBefore(absenceDay.getUpdatedAt()) ||
                   absenceDay.getCreatedAt().isEqual(absenceDay.getUpdatedAt()));
    }
}