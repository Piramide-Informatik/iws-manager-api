package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeCategoryTest {
    String title = "testTitle";
    EmployeeCategory category = new EmployeeCategory();

    @Test
    void testEmployeeCategoryCreation(){
        //Act
        category.setTitle(title);

        //Assert
        assertEquals(title, category.getTitle());
    }

    @Test
    void testEmployeeCategoryWithAuditFields(){
        // Arrange
        category.setTitle(title);
        LocalDateTime now = LocalDateTime.now();

        // Act
        category.setCreatedAt(now);
        category.setUpdatedAt(now);

        // Assert
        assertEquals(now, category.getCreatedAt());
        assertEquals(now, category.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode(){
        // Arrange
        category.setTitle(title);
        category.setId(1L);

        EmployeeCategory category2 = new EmployeeCategory();
        category2.setTitle(title);
        category2.setId(1L);

        // Assert
        assertEquals(category, category2);
        assertEquals(category.hashCode(), category2.hashCode());
    }
}