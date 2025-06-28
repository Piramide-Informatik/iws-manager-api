package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QualificationFZTest {

    @Test
    void testQualificationFZCreation() {
        // Arrange
        String qualificationName = "Software Engineer";
        
        // Act
        QualificationFZ qualification = new QualificationFZ();
        qualification.setQualification(qualificationName);
        
        // Assert
        assertEquals(qualificationName, qualification.getQualification());
    }

    @Test
    void testQualificationFZWithId() {
        // Arrange
        QualificationFZ qualification = new QualificationFZ();
        qualification.setQualification("Cloud Architect");
        Long id = 1L;
        
        // Act
        qualification.setId(id);
        
        // Assert
        assertEquals(id, qualification.getId());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        QualificationFZ qualification1 = new QualificationFZ();
        qualification1.setQualification("QA");
        qualification1.setId(1L);
        
        QualificationFZ qualification2 = new QualificationFZ();
        qualification2.setQualification("QA");
        qualification2.setId(1L);
        
        // Assert
        assertEquals(qualification1, qualification2);
        assertEquals(qualification1.hashCode(), qualification2.hashCode());
    }

    @Test
    void testEqualsAndHashCodeQualificationFZ() {
        // Arrange
        QualificationFZ qfz1 = new QualificationFZ();
        qfz1.setQualification("Data Analyst");
        qfz1.setId(1L); 
        
        QualificationFZ qfz2 = new QualificationFZ();
        qfz2.setQualification("Data Analyst");
        qfz2.setId(1L);
        
        // Assert
        assertEquals(qfz1, qfz2);
        assertEquals(qfz1.hashCode(), qfz2.hashCode());
    }

    @Test
    void testToStringContainsQualification() {
        // Arrange
        String qualificationName = "Frontend Dev";
        QualificationFZ qualification = new QualificationFZ();
        qualification.setQualification(qualificationName);
        
        // Act
        String toStringResult = qualification.toString();
        
        // Assert
        assertTrue(toStringResult.contains(qualificationName));
    }
}