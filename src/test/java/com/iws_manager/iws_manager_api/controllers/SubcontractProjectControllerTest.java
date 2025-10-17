package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.SubcontractProject;
import com.iws_manager.iws_manager_api.services.interfaces.SubcontractProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubcontractProjectControllerTest {

    @Mock
    private SubcontractProjectService subcontractProjectService;

    @InjectMocks
    private SubcontractProjectController subcontractProjectController;

    private SubcontractProject testProject;
    private static final Long TEST_ID = 1L;
    private static final BigDecimal TEST_AMOUNT = new BigDecimal("1000.50");
    private static final BigDecimal TEST_SHARE = new BigDecimal("25.50");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testProject = new SubcontractProject();
        testProject.setId(TEST_ID);
    }

    @Test
    void createShouldReturnCreatedResponse() {
        when(subcontractProjectService.create(any(SubcontractProject.class))).thenReturn(testProject);

        ResponseEntity<?> response = subcontractProjectController.create(testProject);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testProject, response.getBody());
        verify(subcontractProjectService).create(testProject);
    }

    @Test
    void getByIdWhenExistsShouldReturnProject() {
        when(subcontractProjectService.findById(TEST_ID)).thenReturn(Optional.of(testProject));

        ResponseEntity<SubcontractProject> response = subcontractProjectController.getById(TEST_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testProject, response.getBody());
    }

    @Test
    void getByIdWhenNotExistsShouldReturnNotFound() {
        when(subcontractProjectService.findById(TEST_ID)).thenReturn(Optional.empty());

        ResponseEntity<SubcontractProject> response = subcontractProjectController.getById(TEST_ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAllShouldReturnAllProjects() {
        List<SubcontractProject> projects = Arrays.asList(testProject, new SubcontractProject());
        when(subcontractProjectService.findAll()).thenReturn(projects);

        ResponseEntity<List<SubcontractProject>> response = subcontractProjectController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void updateWhenExistsShouldReturnUpdatedProject() {
        when(subcontractProjectService.update(eq(TEST_ID), any(SubcontractProject.class))).thenReturn(testProject);

        ResponseEntity<SubcontractProject> response = subcontractProjectController.update(TEST_ID, testProject);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testProject, response.getBody());
    }

    @Test
    void updateWhenNotExistsShouldReturnNotFound() {
        when(subcontractProjectService.update(eq(TEST_ID), any(SubcontractProject.class)))
                .thenThrow(new RuntimeException());

        ResponseEntity<SubcontractProject> response = subcontractProjectController.update(TEST_ID, testProject);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteWhenExistsShouldReturnNoContent() {
        doNothing().when(subcontractProjectService).delete(TEST_ID);

        ResponseEntity<Void> response = subcontractProjectController.delete(TEST_ID);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(subcontractProjectService).delete(TEST_ID);
    }

    @Test
    void getByAmountShouldReturnProjects() {
        List<SubcontractProject> projects = Arrays.asList(testProject);
        when(subcontractProjectService.getByAmount(TEST_AMOUNT)).thenReturn(projects);

        ResponseEntity<List<SubcontractProject>> response = subcontractProjectController.getByAmount(TEST_AMOUNT);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getByShareShouldReturnProjects() {
        List<SubcontractProject> projects = Arrays.asList(testProject);
        when(subcontractProjectService.getByShare(TEST_SHARE)).thenReturn(projects);

        ResponseEntity<List<SubcontractProject>> response = subcontractProjectController.getByShare(TEST_SHARE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getBySubcontractYearIdShouldReturnProjects() {
        List<SubcontractProject> projects = Arrays.asList(testProject);
        when(subcontractProjectService.getBySubcontractYearId(TEST_ID)).thenReturn(projects);

        ResponseEntity<List<SubcontractProject>> response = subcontractProjectController.getBySubcontractYearId(TEST_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getByProjectIdShouldReturnProjects() {
        List<SubcontractProject> projects = Arrays.asList(testProject);
        when(subcontractProjectService.getByProjectId(TEST_ID)).thenReturn(projects);

        ResponseEntity<List<SubcontractProject>> response = subcontractProjectController.getByProjectId(TEST_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getBySubcontractIdShouldReturnProjects() {
        List<SubcontractProject> projects = Arrays.asList(testProject);
        when(subcontractProjectService.getBySubcontractId(TEST_ID)).thenReturn(projects);

        ResponseEntity<List<SubcontractProject>> response = subcontractProjectController.getBySubcontractId(TEST_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getByShareBetweenShouldReturnProjects() {
        List<SubcontractProject> projects = Arrays.asList(testProject);
        when(subcontractProjectService.getByShareBetween(TEST_SHARE, TEST_SHARE.add(BigDecimal.ONE))).thenReturn(projects);

        ResponseEntity<List<SubcontractProject>> response = subcontractProjectController.getByShareBetween(TEST_SHARE, TEST_SHARE.add(BigDecimal.ONE));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }
}