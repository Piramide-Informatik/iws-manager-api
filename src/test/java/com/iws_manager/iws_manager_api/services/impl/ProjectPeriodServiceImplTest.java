package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.ProjectPackage;
import com.iws_manager.iws_manager_api.models.ProjectPeriod;
import com.iws_manager.iws_manager_api.models.Project;
import com.iws_manager.iws_manager_api.repositories.ProjectPackageRepository;
import com.iws_manager.iws_manager_api.repositories.ProjectPeriodRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class ProjectPeriodServiceImplTest {

    @Mock
    private ProjectPeriodRepository periodRepository;
    @InjectMocks
    private ProjectPeriodServiceImpl projectPeriodService;
    private ProjectPeriod testProjectPeriod;

    @BeforeEach
    void setUp() {
        testProjectPeriod = new ProjectPeriod();
        testProjectPeriod.setId(1L);
        testProjectPeriod.setPeriodNo((short) 1);
        testProjectPeriod.setStartDate(LocalDate.now());
    }
    @Test
    void createShouldSaveAndReturnProjectPeriod() {
        when(periodRepository.save(any(ProjectPeriod.class))).thenReturn(testProjectPeriod);

        ProjectPeriod result = projectPeriodService.create(testProjectPeriod);

        assertNotNull(result);
        assertEquals(testProjectPeriod.getId(), result.getId());
        verify(periodRepository).save(testProjectPeriod);
    }

    @Test
    void createShouldThrowExceptionWhenProjectPeriodIsNull() {
        assertThrows(IllegalArgumentException.class, () -> projectPeriodService.create(null));
    }

    @Test
    void findByIdShouldReturnProjectPeriodWhenExists() {
        when(periodRepository.findById(1L)).thenReturn(Optional.of(testProjectPeriod));

        Optional<ProjectPeriod> result = projectPeriodService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(testProjectPeriod.getId(), result.get().getId());
    }

    @Test
    void findByIdShouldReturnEmptyWhenNotExists() {
        when(periodRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<ProjectPeriod> result = projectPeriodService.findById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void findByIdShouldThrowExceptionWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> projectPeriodService.findById(null));
    }

    @Test
    void findAllShouldReturnAllProjectsPeriodOrderedByNoAsc() {
        List<ProjectPeriod> projects = Arrays.asList(testProjectPeriod);
        when(periodRepository.findAllByOrderByPeriodNoAsc()).thenReturn(projects);

        List<ProjectPeriod> result = projectPeriodService.getAllProjectPeriodsByPeriodNoAsc();

        assertEquals(1, result.size());
        verify(periodRepository).findAllByOrderByPeriodNoAsc();
    }

    @Test
    void findAllShouldReturnAllProjectsPeriodOrderedByStartDateAsc() {
        List<ProjectPeriod> projects = Arrays.asList(testProjectPeriod);
        when(periodRepository.findAllByOrderByStartDateAsc()).thenReturn(projects);

        List<ProjectPeriod> result = projectPeriodService.getAllProjectPeriodsByStartDateAsc();

        assertEquals(1, result.size());
        verify(periodRepository).findAllByOrderByStartDateAsc();
    }

    @Test
    void findAllShouldReturnAllProjectsPeriodOrderedByEndDateAsc() {
        List<ProjectPeriod> projects = Arrays.asList(testProjectPeriod);
        when(periodRepository.findAllByOrderByEndDateAsc()).thenReturn(projects);

        List<ProjectPeriod> result = projectPeriodService.getAllProjectPeriodsByEndDateAsc();

        assertEquals(1, result.size());
        verify(periodRepository).findAllByOrderByEndDateAsc();
    }

    @Test
    void updateShouldUpdateExistingProjectPeriod() {
        ProjectPeriod updatedDetails = new ProjectPeriod();
        updatedDetails.setPeriodNo((short) 1);
        updatedDetails.setStartDate(LocalDate.now());
        updatedDetails.setEndDate(LocalDate.now().plusDays(1)); // ← Agregar endDate
        
        // Configurar project (necesario para validaciones)
        Project project = new Project();
        project.setId(1L);
        updatedDetails.setProject(project);
        
        // Configurar testProjectPeriod
        testProjectPeriod.setProject(project);
        testProjectPeriod.setPeriodNo((short) 1);
        testProjectPeriod.setStartDate(LocalDate.now().minusDays(10));
        testProjectPeriod.setEndDate(LocalDate.now().minusDays(5));

        when(periodRepository.findById(1L)).thenReturn(Optional.of(testProjectPeriod));
        when(periodRepository.save(any(ProjectPeriod.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        // Mockear validación de solapamiento
        when(periodRepository.existsOverlappingPeriod(anyLong(), any(LocalDate.class), 
            any(LocalDate.class), anyLong())).thenReturn(false);

        ProjectPeriod result = projectPeriodService.update(1L, updatedDetails);

        assertEquals((short) 1, result.getPeriodNo());
        assertEquals(LocalDate.now(), result.getStartDate());
        assertEquals(LocalDate.now().plusDays(1), result.getEndDate());
        verify(periodRepository).save(testProjectPeriod);
    }

    @Test
    void updateShouldThrowExceptionWhenProjectPeriodNotFound() {
        when(projectPeriodService.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> projectPeriodService.update(1L, new ProjectPeriod()));
    }

    @Test
    void updateShouldThrowExceptionWhenIdOrDetailsIsNull() {
        assertThrows(IllegalArgumentException.class, () -> projectPeriodService.update(null, new ProjectPeriod()));
        assertThrows(IllegalArgumentException.class, () -> projectPeriodService.update(1L, null));
    }

    @Test
    void deleteShouldDeleteProjectPackage() {
        when(periodRepository.existsById(1L)).thenReturn(true);
        
        projectPeriodService.delete(1L);
        
        verify(periodRepository).existsById(1L);
        verify(periodRepository).deleteById(1L);
    }

    @Test
    void deleteShouldThrowEntityNotFoundExceptionWhenIdDoesNotExist() {
        when(periodRepository.existsById(99L)).thenReturn(false);
        
        EntityNotFoundException exception = assertThrows(
            EntityNotFoundException.class,
            () -> projectPeriodService.delete(99L)
        );
    
        assertEquals("ProjectPeriod not found with id: 99", exception.getMessage());
        verify(periodRepository).existsById(99L);
        verify(periodRepository, never()).deleteById(anyLong());
    }

    @Test
    void deleteShouldThrowExceptionWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> projectPeriodService.delete(null));
    }
}