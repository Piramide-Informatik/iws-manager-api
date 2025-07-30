package com.iws_manager.iws_manager_api.models;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {
    @Test
    void testProjectCreation() {
        // Arrange
        String projectName = "AI Research Initiative";
        String fundingLabel = "EU-H2020-12345";
        
        // Act
        Project project = new Project();
        project.setProjectName(projectName);
        project.setFundingLabel(fundingLabel);
        
        // Assert
        assertEquals(projectName, project.getProjectName());
        assertEquals(fundingLabel, project.getFundingLabel());
    }

    @Test
    void testProjectWithAuditFields() {
        // Arrange
        Project project = new Project();
        project.setProjectName("Renewable Energy Project");
        LocalDateTime now = LocalDateTime.now();
        
        // Act
        project.setCreatedAt(now);
        project.setUpdatedAt(now);
        
        // Assert
        assertEquals(now, project.getCreatedAt());
        assertEquals(now, project.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Project project1 = new Project();
        project1.setId(1L);
        project1.setProjectName("Global Sustainability");
        
        Project project2 = new Project();
        project2.setId(1L); 
        project2.setProjectName("New Global Sustainability");
        
        
        // Assert
        assertEquals(project1, project2);  
        assertEquals(project1.hashCode(), project2.hashCode());
    }
}