package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProjectPackageTest {
    String packageTitle = "test title";
    ProjectPackage projectPackage = new ProjectPackage();
    @Test
    void testPublicHolidayCreation() {
        projectPackage.setPackageTitle(packageTitle);

        assertEquals(packageTitle, projectPackage.getPackageTitle());
    }

    @Test
    void testPublicHolidayWithAuditFields(){
        // Arrange
        projectPackage.setPackageTitle(packageTitle);
        LocalDateTime now = LocalDateTime.now();

        // Act
        projectPackage.setCreatedAt(now);
        projectPackage.setUpdatedAt(now);

        // Assert
        assertEquals(now, projectPackage.getCreatedAt());
        assertEquals(now, projectPackage.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode(){
        // Arrange
        projectPackage.setPackageTitle(packageTitle);
        projectPackage.setId(1L);

        ProjectPackage projectPackage2 = new ProjectPackage();
        projectPackage2.setPackageTitle(packageTitle);
        projectPackage2.setId(1L);

        // Assert
        assertEquals(projectPackage, projectPackage2);
        assertEquals(projectPackage.hashCode(), projectPackage2.hashCode());
    }

}