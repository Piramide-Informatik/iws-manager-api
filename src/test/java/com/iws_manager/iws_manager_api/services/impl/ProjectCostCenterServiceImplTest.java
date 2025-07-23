package com.iws_manager.iws_manager_api.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.ProjectCostCenter;
import com.iws_manager.iws_manager_api.repositories.ProjectCostCenterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProjectCostCenterServiceImplTest {

    @Mock
    private ProjectCostCenterRepository projectCostCenterRepository;

    @InjectMocks
    private ProjectCostCenterServiceImpl projectCostCenterService;

    private ProjectCostCenter projectCostCenter;
    private static final Long ID = 1L;

    @BeforeEach
    void setUp() {
        projectCostCenter = new ProjectCostCenter();
        projectCostCenter.setId(ID);
        projectCostCenter.setCostCenter("CC-1001");
        projectCostCenter.setKmuino("0837");
        projectCostCenter.setSequenceno(1);
    }

    @Test
    void createShouldSaveAndReturnProjectCostCenter() {
        // Arrange
        when(projectCostCenterRepository.save(any(ProjectCostCenter.class))).thenReturn(projectCostCenter);

        // Act
        ProjectCostCenter result = projectCostCenterService.create(projectCostCenter);

        // Assert
        assertNotNull(result);
        assertEquals(projectCostCenter.getId(), result.getId());
        verify(projectCostCenterRepository, times(1)).save(projectCostCenter);
    }

    @Test
    void createShouldThrowIllegalArgumentExceptionWhenProjectCostCenterIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, 
            () -> projectCostCenterService.create(null),
            "ProjectCostCenter cannot be null");
    }

    @Test
    void findByIdShouldReturnProjectCostCenterWhenExists() {
        // Arrange
        when(projectCostCenterRepository.findById(ID)).thenReturn(Optional.of(projectCostCenter));

        // Act
        Optional<ProjectCostCenter> result = projectCostCenterService.findById(ID);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(projectCostCenter.getId(), result.get().getId());
        verify(projectCostCenterRepository, times(1)).findById(ID);
    }

    @Test
    void findByIdShouldReturnEmptyWhenNotExists() {
        // Arrange
        when(projectCostCenterRepository.findById(ID)).thenReturn(Optional.empty());

        // Act
        Optional<ProjectCostCenter> result = projectCostCenterService.findById(ID);

        // Assert
        assertFalse(result.isPresent());
        verify(projectCostCenterRepository, times(1)).findById(ID);
    }

    @Test
    void findByIdShouldThrowIllegalArgumentExceptionWhenIdIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, 
            () -> projectCostCenterService.findById(null),
            "ID cannot be null");
    }

    @Test
    void findAllShouldReturnAllProjectCostCenters() {
        // Arrange
        ProjectCostCenter anotherCostCenter = new ProjectCostCenter();
        anotherCostCenter.setId(2L);
        
        List<ProjectCostCenter> expectedList = Arrays.asList(projectCostCenter, anotherCostCenter);
        when(projectCostCenterRepository.findAll()).thenReturn(expectedList);

        // Act
        List<ProjectCostCenter> result = projectCostCenterService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(projectCostCenterRepository, times(1)).findAll();
    }

    @Test
    void updateShouldUpdateAndReturnProjectCostCenterWhenExists() {
        // Arrange
        ProjectCostCenter updatedDetails = new ProjectCostCenter();
        updatedDetails.setCostCenter("CC-1002");
        updatedDetails.setKmuino("9999");
        updatedDetails.setSequenceno(2);

        when(projectCostCenterRepository.findById(ID)).thenReturn(Optional.of(projectCostCenter));
        when(projectCostCenterRepository.save(any(ProjectCostCenter.class))).thenReturn(projectCostCenter);

        // Act
        ProjectCostCenter result = projectCostCenterService.update(ID, updatedDetails);

        // Assert
        assertNotNull(result);
        assertEquals("CC-1002", result.getCostCenter());
        assertEquals("9999", result.getKmuino());
        assertEquals(2, result.getSequenceno());
        verify(projectCostCenterRepository, times(1)).findById(ID);
        verify(projectCostCenterRepository, times(1)).save(projectCostCenter);
    }

    @Test
    void updateShouldThrowRuntimeExceptionWhenNotFound() {
        // Arrange
        when(projectCostCenterRepository.findById(ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, 
            () -> projectCostCenterService.update(ID, new ProjectCostCenter()),
            "ProjectCostCenter not found with id: " + ID);
    }

    @Test
    void updateShouldThrowIllegalArgumentExceptionWhenIdOrDetailsIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, 
            () -> projectCostCenterService.update(null, new ProjectCostCenter()),
            "ID and projectCostCenter details cannot be null");

        assertThrows(IllegalArgumentException.class, 
            () -> projectCostCenterService.update(ID, null),
            "ID and projectCostCenter details cannot be null");
    }

    @Test
    void deleteShouldCallRepositoryDeleteWhenIdExists() {
        // Arrange
        doNothing().when(projectCostCenterRepository).deleteById(ID);

        // Act
        projectCostCenterService.delete(ID);

        // Assert
        verify(projectCostCenterRepository, times(1)).deleteById(ID);
    }

    @Test
    void deleteShouldThrowIllegalArgumentExceptionWhenIdIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, 
            () -> projectCostCenterService.delete(null),
            "ID cannot be null");
    }
}