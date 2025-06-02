package com.iws_manager.iws_manager_api.models;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectStatusTest {

    String name = "Antrag";
    ProjectStatus projectStatus = new ProjectStatus();

    @Test 
    void testProjectStatusCreation(){
        //Act
        projectStatus.setName(name);

        //Assert
        assertEquals(name, projectStatus.getName());
    }

    @Test
    void testProjectStatusWithAuditFields(){
        // Arrange
        projectStatus.setName(name);
        LocalDateTime now = LocalDateTime.now();

        // Act
        projectStatus.setCreatedAt(now);
        projectStatus.setUpdatedAt(now);

        // Assert
        assertEquals(now, projectStatus.getCreatedAt());
        assertEquals(now, projectStatus.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode(){
        // Arrange
        projectStatus.setName(name);
        projectStatus.setId(1L);

        ProjectStatus projectStatus2 = new ProjectStatus();
        projectStatus2.setName(name);
        projectStatus2.setId(1L);

        // Assert
        assertEquals(projectStatus, projectStatus2);
        assertEquals(projectStatus.hashCode(), projectStatus2.hashCode());
    }
}
