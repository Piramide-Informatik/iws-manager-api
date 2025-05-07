package com.iws_manager.iws_manager_api.models;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BranchTest {

    @Test
    void testBranchCreation() {
        // Arrange
        String branchName = "Economics Department";
        
        // Act
        Branch branch = new Branch();
        branch.setName(branchName);
        
        // Assert
        assertEquals(branchName, branch.getName());
    }

    @Test
    void testBranchWithAuditFields() {
        // Arrange
        Branch branch = new Branch();
        branch.setName("Financial Services");
        LocalDateTime now = LocalDateTime.now();
        
        // Act
        branch.setCreatedAt(now);
        branch.setUpdatedAt(now);
        
        // Assert
        assertEquals(now, branch.getCreatedAt());
        assertEquals(now, branch.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Branch branch1 = new Branch();
        branch1.setName("Talent Management");
        branch1.setId(1L); 
        
        Branch branch2 = new Branch();
        branch2.setName("Talent Management");
        branch2.setId(1L);
        
        // Assert
        assertEquals(branch1, branch2);
        assertEquals(branch1.hashCode(), branch2.hashCode());
    }

    @Test
    void testBranchWithInvalidName() {
        Branch branch = new Branch();
        branch.setName(null); 
        assertNotNull(branch.getName(), "Name cannot be null");
    }
}