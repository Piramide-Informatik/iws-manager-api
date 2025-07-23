package com.iws_manager.iws_manager_api.models;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProjectCostCenterTest {

    // Arrange
    String costCenter = "CC-1001";
    String kmuino = "0837";
    int sequenceNo = 1;
        
    @Test
    void testProjectCostCenterCreation() {
        
        // Act
        ProjectCostCenter projectCostCenter = new ProjectCostCenter();
        projectCostCenter.setCostCenter(costCenter);
        projectCostCenter.setKmuino(kmuino);
        projectCostCenter.setSequenceno(sequenceNo);
        
        // Assert
        assertEquals(costCenter, projectCostCenter.getCostCenter());
        assertEquals(kmuino, projectCostCenter.getKmuino());
        assertEquals(sequenceNo, projectCostCenter.getSequenceno());
    }

    @Test
    void testProjectCostCenterWithAuditFields() {
        // Arrange
        ProjectCostCenter projectCostCenter = new ProjectCostCenter();
        LocalDateTime now = LocalDateTime.now();
        long version = 2L;
        
        // Act
        projectCostCenter.setCreatedAt(now.minusDays(1));
        projectCostCenter.setUpdatedAt(now);
        projectCostCenter.setVersion(version);
        
        // Assert
        assertEquals(now.minusDays(1), projectCostCenter.getCreatedAt());
        assertEquals(now, projectCostCenter.getUpdatedAt());
        assertEquals(version, projectCostCenter.getVersion());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        ProjectCostCenter costCenter1 = new ProjectCostCenter();
        costCenter1.setId(1L);
        costCenter1.setCostCenter(costCenter);
        costCenter1.setKmuino(kmuino);
        costCenter1.setSequenceno(sequenceNo);
        
        ProjectCostCenter costCenter2 = new ProjectCostCenter();
        costCenter2.setId(1L); 
        costCenter2.setCostCenter(costCenter);
        costCenter2.setKmuino(kmuino);
        costCenter2.setSequenceno(sequenceNo);

        // Assert
        assertEquals(costCenter1.hashCode(), costCenter2.hashCode());
        
        // Special cases
        assertNotEquals(costCenter1, null);
        assertNotEquals(costCenter1, new Object());
    }
}