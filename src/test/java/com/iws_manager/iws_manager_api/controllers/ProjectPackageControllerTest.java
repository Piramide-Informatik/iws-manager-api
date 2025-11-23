package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.ProjectPackage;
import com.iws_manager.iws_manager_api.services.interfaces.ProjectPackageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectPackageControllerTest {
    @Mock
    private ProjectPackageService projectPackageService;

    @InjectMocks
    private ProjectPackageController projectPackageController;

    private ProjectPackage testProject;
    private static final Long TEST_ID = 1L;
    private static final String TEST_TITLE = "test";
    private static final LocalDate TEST_DATE = LocalDate.now();

    @BeforeEach
    void setUp() {
        testProject = new ProjectPackage();
        testProject.setId(TEST_ID);
        testProject.setPackageTitle("Test ProjectPackage");
    }

    @Test
    void updateShouldReturnUpdatedProjectPackage() {
        when(projectPackageService.update(eq(TEST_ID), any(ProjectPackage.class))).thenReturn(testProject);

        ResponseEntity<ProjectPackage> response = projectPackageController.update(TEST_ID, testProject);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testProject, response.getBody());
    }

    @Test
    void updateShouldReturnNotFoundWhenProjectPackageNotExists() {
        when(projectPackageService.update(eq(TEST_ID), any(ProjectPackage.class)))
                .thenThrow(new RuntimeException("Not found"));

        ResponseEntity<ProjectPackage> response = projectPackageController.update(TEST_ID, testProject);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createShouldReturnCreatedProjectPackage() {
        when(projectPackageService.create(any(ProjectPackage.class))).thenReturn(testProject);

        ResponseEntity<?> response = projectPackageController.create(testProject);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testProject, response.getBody());
        verify(projectPackageService).create(testProject);
    }

    @Test
    void getByIdShouldReturnProjectPackageWhenExists() {
        when(projectPackageService.findById(TEST_ID)).thenReturn(Optional.of(testProject));

        ResponseEntity<ProjectPackage> response = projectPackageController.getById(TEST_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testProject, response.getBody());
    }

    @Test
    void getByIdShouldReturnNotFoundWhenNotExists() {
        when(projectPackageService.findById(TEST_ID)).thenReturn(Optional.empty());

        ResponseEntity<ProjectPackage> response = projectPackageController.getById(TEST_ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAllShouldReturnAllProjectPackages() {
        List<ProjectPackage> projects = Arrays.asList(testProject);
        when(projectPackageService.findAll()).thenReturn(projects);

        ResponseEntity<List<ProjectPackage>> response = projectPackageController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void deleteShouldReturnNoContentWhenSuccess() {
        doNothing().when(projectPackageService).delete(TEST_ID);

        ResponseEntity<Void> response = projectPackageController.delete(TEST_ID);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(projectPackageService).delete(TEST_ID);
    }

    @Test
    void deleteShouldReturnNotFoundWhenProjectPackagesNotExists() {
        doThrow(new RuntimeException("Not found")).when(projectPackageService).delete(TEST_ID);

        ResponseEntity<Void> response = projectPackageController.delete(TEST_ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAllShouldReturnAllProjectPackagesByOrderByTitleAsc() {
        List<ProjectPackage> projects = Arrays.asList(testProject);
        when(projectPackageService.findAllTitleAsc()).thenReturn(projects);

        ResponseEntity<List<ProjectPackage>> response = projectPackageController.getAllByOrderByTitleAsc();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getAllShouldReturnAllProjectPackagesByOrderBySerialAsc() {
        List<ProjectPackage> projects = Arrays.asList(testProject);
        when(projectPackageService.findAllSerialAsc()).thenReturn(projects);

        ResponseEntity<List<ProjectPackage>> response = projectPackageController.getAllByOrderBySerialAsc();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getAllShouldReturnAllProjectPackagesByOrderByPackageNoAsc() {
        List<ProjectPackage> projects = Arrays.asList(testProject);
        when(projectPackageService.findAllPackageNoAsc()).thenReturn(projects);

        ResponseEntity<List<ProjectPackage>> response = projectPackageController.getAllByOrderByPackageNoAsc();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getAllShouldReturnAllProjectPackagesByOrderByStartDataAsc() {
        List<ProjectPackage> projects = Arrays.asList(testProject);
        when(projectPackageService.findAllStartDateAsc()).thenReturn(projects);

        ResponseEntity<List<ProjectPackage>> response = projectPackageController.getAllByOrderByStartDateAsc();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getAllShouldReturnAllProjectPackagesByOrderByEndDateAsc() {
        List<ProjectPackage> projects = Arrays.asList(testProject);
        when(projectPackageService.findAllEndDateAsc()).thenReturn(projects);

        ResponseEntity<List<ProjectPackage>> response = projectPackageController.getAllByOrderByEndtDateAsc();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }
}