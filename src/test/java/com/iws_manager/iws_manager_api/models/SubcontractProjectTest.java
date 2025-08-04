package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SubcontractProjectTest {

    private static final SubcontractYear MOCK_SUBCONTRACT_YEAR = mock(SubcontractYear.class);
    private static final Project MOCK_PROJECT = mock(Project.class);
    private static final Subcontract MOCK_SUBCONTRACT = mock(Subcontract.class);
    private static final String AMOUNT_1 = "1000.50";
    private static final Integer MONTHS_1 = 6;
    private static final BigDecimal SHARE_1 = new BigDecimal("25.50");
    private static final LocalDate YEAR_1 = LocalDate.of(2023, 1, 1);
    
    private SubcontractProject createSubcontractProject1() {
        SubcontractProject project = new SubcontractProject();
        project.setSubcontractYear(MOCK_SUBCONTRACT_YEAR);
        project.setAmount(AMOUNT_1);
        project.setMonths(MONTHS_1);
        project.setProject(MOCK_PROJECT);
        project.setSubcontract(MOCK_SUBCONTRACT);
        project.setShare(SHARE_1);
        project.setYear(YEAR_1);
        return project;
    }

    @Test
    void testSubcontractProjectCreation() {
        SubcontractProject project = createSubcontractProject1();
        
        assertNotNull(project);
        assertEquals(AMOUNT_1, project.getAmount());
        assertEquals(MONTHS_1, project.getMonths());
        assertEquals(MOCK_PROJECT, project.getProject());
        assertEquals(MOCK_SUBCONTRACT, project.getSubcontract());
        assertEquals(SHARE_1, project.getShare());
        assertEquals(YEAR_1, project.getYear());
        assertEquals(MOCK_SUBCONTRACT_YEAR, project.getSubcontractYear());
    }

    @Test
    void testSubcontractProjectWithAuditFields() {
        SubcontractProject project = createSubcontractProject1();
        LocalDateTime now = LocalDateTime.now();
        
        project.setCreatedAt(now);
        project.setUpdatedAt(now);

        assertNotNull(project.getCreatedAt());
        assertNotNull(project.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        SubcontractProject project1 = createSubcontractProject1();
        SubcontractProject project2 = createSubcontractProject1();

        // Reflexivity
        assertEquals(project1, project1);
        assertEquals(project1.hashCode(), project1.hashCode());

        // Symmetry
        assertEquals(project1, project2);
        assertEquals(project2, project1);
        assertEquals(project1.hashCode(), project2.hashCode());

        // Transitivity
        SubcontractProject project3 = createSubcontractProject1();
        assertEquals(project1, project2);
        assertEquals(project2, project3);
        assertEquals(project1, project3);

        // Consistency
        assertEquals(project1, project2);
        assertEquals(project1, project2);

        // Comparison with null
        assertNotEquals(null, project1);

        // Comparison with different type
        assertNotEquals("string", project1);
    }

    @Test
    void testToString() {
        SubcontractProject project = createSubcontractProject1();
        String toStringResult = project.toString();
        
        assertTrue(toStringResult.contains("SubcontractProject"));
        assertTrue(toStringResult.contains("amount=" + AMOUNT_1));
        assertTrue(toStringResult.contains("months=" + MONTHS_1));
        assertTrue(toStringResult.contains("share=" + SHARE_1));
    }
}