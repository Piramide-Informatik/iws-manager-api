package com.iws_manager.iws_manager_api.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.iws_manager.iws_manager_api.models.Branch;
import com.iws_manager.iws_manager_api.repositories.BranchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@DisplayName("Branch Service Implementation Tests")
class BranchServiceImplTest {

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private BranchServiceImpl branchService;

    private Branch sampleBranch;

    @BeforeEach
    void setUp() {
        sampleBranch = new Branch();
        sampleBranch.setId(1L);
        sampleBranch.setName("Dr.");
    }

    @Test
    @DisplayName("Should save branch successfully")
    void createShouldReturnSavedBranch() {
        // Arrange
        when(branchRepository.save(any(Branch.class))).thenReturn(sampleBranch);

        // Act
        Branch result = branchService.create(sampleBranch);

        // Assert
        assertNotNull(result);
        assertEquals("Dr.", result.getName());
        verify(branchRepository, times(1)).save(any(Branch.class));
    }

    @Test
    @DisplayName("Should throw exception when creating null Branch")
    void createShouldThrowExceptionWhenBranchIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> branchService.create(null));
        verify(branchRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should find branch by ID")
    void findByIdShouldReturnBranchWhenExists() {
        // Arrange
        when(branchRepository.findById(1L)).thenReturn(Optional.of(sampleBranch));

        // Act
        Optional<Branch> result = branchService.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Dr.", result.get().getName());
        verify(branchRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return empty when branch not found")
    void findByIdShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(branchRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<Branch> result = branchService.findById(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(branchRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Should throw exception when finding with null ID")
    void findByIdShouldThrowExceptionWhenIdIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> branchService.findById(null));
        verify(branchRepository, never()).findById(any());
    }

    @Test
    @DisplayName("Should return all branches")
    void findAllShouldReturnAllBranches() {
        // Arrange
        Branch branch2 = new Branch();
        branch2.setId(2L);
        branch2.setName("Prof.");
        
        when(branchRepository.findAll()).thenReturn(Arrays.asList(sampleBranch, branch2));

        // Act
        List<Branch> result = branchService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(branchRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should update branch successfully")
    void updateShouldReturnUpdatedBranch() {
        // Arrange
        Branch updatedDetails = new Branch();
        updatedDetails.setName("Dr. Updated");

        when(branchRepository.findById(1L)).thenReturn(Optional.of(sampleBranch));
        when(branchRepository.save(any(Branch.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Branch result = branchService.update(1L, updatedDetails);

        // Assert
        assertEquals("Dr. Updated", result.getName());
        verify(branchRepository, times(1)).findById(1L);
        verify(branchRepository, times(1)).save(any(Branch.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent branch")
    void updateShouldThrowExceptionWhenBranchNotFound() {
        // Arrange
        when(branchRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, 
            () -> branchService.update(99L, new Branch()));
        verify(branchRepository, never()).save(any());
    }

    @Test
    public void updateShouldThrowExceptionWhenOptimisticLockingFails() {
        // Setup
        Long branchId = 1L;
        Branch currentBranch = new Branch();
        currentBranch.setId(branchId);
        currentBranch.setName("Dr.");
        currentBranch.setVersion(2L); // Current version in DB
        
        Branch outdatedBranch = new Branch();
        outdatedBranch.setId(branchId);
        outdatedBranch.setName("Doctor");
        outdatedBranch.setVersion(1L); // Outdated version

        when(branchRepository.findById(branchId)).thenReturn(Optional.of(currentBranch));
        when(branchRepository.save(any(Branch.class)))
            .thenThrow(new ObjectOptimisticLockingFailureException("Concurrent modification detected", 
                    new ObjectOptimisticLockingFailureException(Branch.class, branchId)));

        // Execution and verification
        Exception exception = assertThrows(RuntimeException.class, () -> {
            branchService.update(branchId, outdatedBranch);
        });

        assertNotNull(exception, "An exception should have been thrown");
        
        // Verify if it's the direct exception or wrapped
        if (!(exception instanceof ObjectOptimisticLockingFailureException)) {
            assertNotNull(exception.getCause(), "The exception should have a cause");
            assertTrue(exception.getCause() instanceof ObjectOptimisticLockingFailureException, 
                    "The cause should be ObjectOptimisticLockingFailureException");
        }
        
        // Verify repository interactions
        verify(branchRepository).findById(branchId);
        verify(branchRepository).save(any(Branch.class));
    }
}
