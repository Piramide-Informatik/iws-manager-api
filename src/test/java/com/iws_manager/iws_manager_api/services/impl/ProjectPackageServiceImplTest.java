package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.Project;
import com.iws_manager.iws_manager_api.models.ProjectPackage;
import com.iws_manager.iws_manager_api.repositories.ProjectPackageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectPackageServiceImplTest {

    @Mock
    private ProjectPackageRepository projectPackageRepository;
    @InjectMocks
    private ProjectPackageServiceImpl projectPackageService;
    private ProjectPackage testProjectPackage;

    @BeforeEach
    void setUp() {
        testProjectPackage = new ProjectPackage();
        testProjectPackage.setId(1L);
        testProjectPackage.setPackageTitle("test title");
        testProjectPackage.setStartDate(LocalDate.now());
    }

    @Test
    void createShouldSaveAndReturnProjectPackage() {
        when(projectPackageRepository.save(any(ProjectPackage.class))).thenReturn(testProjectPackage);

        ProjectPackage result = projectPackageService.create(testProjectPackage);

        assertNotNull(result);
        assertEquals(testProjectPackage.getId(), result.getId());
        verify(projectPackageRepository).save(testProjectPackage);
    }

    @Test
    void createShouldThrowExceptionWhenProjectPackageIsNull() {
        assertThrows(IllegalArgumentException.class, () -> projectPackageService.create(null));
    }

    @Test
    void findByIdShouldReturnProjectPackageWhenExists() {
        when(projectPackageRepository.findById(1L)).thenReturn(Optional.of(testProjectPackage));

        Optional<ProjectPackage> result = projectPackageService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(testProjectPackage.getId(), result.get().getId());
    }

    @Test
    void findByIdShouldReturnEmptyWhenNotExists() {
        when(projectPackageRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<ProjectPackage> result = projectPackageService.findById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void findByIdShouldThrowExceptionWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> projectPackageService.findById(null));
    }

    @Test
    void findAllShouldReturnAllProjectsPackageOrderedByTitleAsc() {
        List<ProjectPackage> projects = Arrays.asList(testProjectPackage);
        when(projectPackageRepository.findAllByOrderByPackageTitleAsc()).thenReturn(projects);

        List<ProjectPackage> result = projectPackageService.findAllTitleAsc();

        assertEquals(1, result.size());
        verify(projectPackageRepository).findAllByOrderByPackageTitleAsc();
    }

    @Test
    void findAllShouldReturnAllProjectsPackageOrderedBySerialAsc() {
        List<ProjectPackage> projects = Arrays.asList(testProjectPackage);
        when(projectPackageRepository.findAllByOrderByPackageSerialAsc()).thenReturn(projects);

        List<ProjectPackage> result = projectPackageService.findAllSerialAsc();

        assertEquals(1, result.size());
        verify(projectPackageRepository).findAllByOrderByPackageSerialAsc();
    }

    @Test
    void findAllShouldReturnAllProjectsPackageOrderedByPackageNoAsc() {
        List<ProjectPackage> projects = Arrays.asList(testProjectPackage);
        when(projectPackageRepository.findAllByOrderByPackageNoAsc()).thenReturn(projects);

        List<ProjectPackage> result = projectPackageService.findAllPackageNoAsc();

        assertEquals(1, result.size());
        verify(projectPackageRepository).findAllByOrderByPackageNoAsc();
    }

    @Test
    void findAllShouldReturnAllProjectsPackageOrderedByStartDateAsc() {
        List<ProjectPackage> projects = Arrays.asList(testProjectPackage);
        when(projectPackageRepository.findAllByOrderByStartDate()).thenReturn(projects);

        List<ProjectPackage> result = projectPackageService.findAllStartDateAsc();

        assertEquals(1, result.size());
        verify(projectPackageRepository).findAllByOrderByStartDate();
    }

    @Test
    void findAllShouldReturnAllProjectsPackageOrderedByEndDateAsc() {
        List<ProjectPackage> projects = Arrays.asList(testProjectPackage);
        when(projectPackageRepository.findAllByOrderByEndDate()).thenReturn(projects);

        List<ProjectPackage> result = projectPackageService.findAllEndDateAsc();

        assertEquals(1, result.size());
        verify(projectPackageRepository).findAllByOrderByEndDate();
    }


    @Test
    void updateShouldUpdateExistingProjectPackage() {
        ProjectPackage updatedDetails = new ProjectPackage();
        updatedDetails.setPackageTitle("Updated test title");
        updatedDetails.setStartDate(LocalDate.now());

        when(projectPackageRepository.findById(1L)).thenReturn(Optional.of(testProjectPackage));
        when(projectPackageRepository.save(any(ProjectPackage.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProjectPackage result = projectPackageService.update(1L, updatedDetails);

        assertEquals("Updated test title", result.getPackageTitle());
        assertEquals(LocalDate.now(), result.getStartDate());
        verify(projectPackageRepository).save(testProjectPackage);
    }

    @Test
    void updateShouldThrowExceptionWhenProjectPackageNotFound() {
        when(projectPackageRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> projectPackageService.update(1L, new ProjectPackage()));
    }

    @Test
    void updateShouldThrowExceptionWhenIdOrDetailsIsNull() {
        assertThrows(IllegalArgumentException.class, () -> projectPackageService.update(null, new ProjectPackage()));
        assertThrows(IllegalArgumentException.class, () -> projectPackageService.update(1L, null));
    }

    @Test
    void deleteShouldDeleteProjectPackage() {
        projectPackageService.delete(1L);

        verify(projectPackageRepository).deleteById(1L);
    }

    @Test
    void deleteShouldThrowExceptionWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> projectPackageService.delete(null));
    }

}