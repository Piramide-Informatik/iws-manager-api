package com.iws_manager.iws_manager_api.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.iws_manager.iws_manager_api.models.ProjectStatus;
import com.iws_manager.iws_manager_api.repositories.ProjectStatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProjectStatus Service Implementation Tests")
class ProjectStatusServiceImplTest {

    @Mock
    private ProjectStatusRepository projectStatusRepository;

    @InjectMocks
    private ProjectStatusServiceImpl projectStatusService;

    private ProjectStatus sampleProjectStatus;
    private String projectStatusName = "Antrag";

    @BeforeEach
    void setUp() {
        sampleProjectStatus = new ProjectStatus();
        sampleProjectStatus.setId(1L);
        sampleProjectStatus.setName(projectStatusName);
    }

    @Test
    @DisplayName("Should save ProjectStatus successfully")
    void createShouldReturnSavedProjectStatus() {
        // Arrange
        when(projectStatusRepository.save(any(ProjectStatus.class))).thenReturn(sampleProjectStatus);

        // Act
        ProjectStatus result = projectStatusService.create(sampleProjectStatus);

        // Assert
        assertNotNull(result);
        assertEquals(projectStatusName, result.getName());
        verify(projectStatusRepository, times(1)).save(any(ProjectStatus.class));
    }

    @Test
    @DisplayName("Should throw exception when creating null projectStatus")
    void createShouldThrowExceptionWhenProjectStatusIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> projectStatusService.create(null));
        verify(projectStatusRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should find projectStatus by ID")
    void findByIdShouldReturnProjectStatusWhenExists() {
        // Arrange
        when(projectStatusRepository.findById(1L)).thenReturn(Optional.of(sampleProjectStatus));

        // Act
        Optional<ProjectStatus> result = projectStatusService.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(projectStatusName, result.get().getName());
        verify(projectStatusRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return empty when projectStatus not found")
    void findByIdShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(projectStatusRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<ProjectStatus> result = projectStatusService.findById(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(projectStatusRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Should throw exception when finding with null ID")
    void findByIdShouldThrowExceptionWhenIdIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> projectStatusService.findById(null));
        verify(projectStatusRepository, never()).findById(any());
    }

    @Test
    @DisplayName("Should return all projectStatuses")
    void findAllShouldReturnAllProjectStatuss() {
        // Arrange
        ProjectStatus projectStatus2 = new ProjectStatus();
        projectStatus2.setId(2L);
        projectStatus2.setName("Stufe");
        
        when(projectStatusRepository.findAll()).thenReturn(Arrays.asList(sampleProjectStatus, projectStatus2));

        // Act
        List<ProjectStatus> result = projectStatusService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(projectStatusRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should update projectStatus successfully")
    void updateShouldReturnUpdatedProjectStatus() {
        // Arrange
        ProjectStatus updatedDetails = new ProjectStatus();
        updatedDetails.setName("Antrag Updated");

        when(projectStatusRepository.findById(1L)).thenReturn(Optional.of(sampleProjectStatus));
        when(projectStatusRepository.save(any(ProjectStatus.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ProjectStatus result = projectStatusService.update(1L, updatedDetails);

        // Assert
        assertEquals("Antrag Updated", result.getName());
        verify(projectStatusRepository, times(1)).findById(1L);
        verify(projectStatusRepository, times(1)).save(any(ProjectStatus.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent projectStatus")
    void updateShouldThrowExceptionWhenProjectStatusNotFound() {
        // Arrange
        when(projectStatusRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, 
            () -> projectStatusService.update(99L, new ProjectStatus()));
        verify(projectStatusRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when deleting with null ID")
    void deleteShouldThrowExceptionWhenIdIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> projectStatusService.delete(null));
        verify(projectStatusRepository, never()).deleteById(any());
    }
}
