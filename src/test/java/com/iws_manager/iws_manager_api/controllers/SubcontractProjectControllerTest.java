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
    private final Long TEST_ID = 1L;
    private final Integer TEST_MONTHS = 6;
    private final BigDecimal TEST_AMOUNT = new BigDecimal("1000.50");
    private final BigDecimal TEST_SHARE = new BigDecimal("25.50");
    private final LocalDate TEST_DATE = LocalDate.of(2023, 1, 1);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testProject = new SubcontractProject();
        testProject.setId(TEST_ID);
    }

    @Test
    void create_ShouldReturnCreatedResponse() {
        when(subcontractProjectService.create(any(SubcontractProject.class))).thenReturn(testProject);

        ResponseEntity<?> response = subcontractProjectController.create(testProject);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testProject, response.getBody());
        verify(subcontractProjectService).create(testProject);
    }

    @Test
    void getById_WhenExists_ShouldReturnProject() {
        when(subcontractProjectService.findById(TEST_ID)).thenReturn(Optional.of(testProject));

        ResponseEntity<SubcontractProject> response = subcontractProjectController.getById(TEST_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testProject, response.getBody());
    }

    @Test
    void getById_WhenNotExists_ShouldReturnNotFound() {
        when(subcontractProjectService.findById(TEST_ID)).thenReturn(Optional.empty());

        ResponseEntity<SubcontractProject> response = subcontractProjectController.getById(TEST_ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAll_ShouldReturnAllProjects() {
        List<SubcontractProject> projects = Arrays.asList(testProject, new SubcontractProject());
        when(subcontractProjectService.findAll()).thenReturn(projects);

        ResponseEntity<List<SubcontractProject>> response = subcontractProjectController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void update_WhenExists_ShouldReturnUpdatedProject() {
        when(subcontractProjectService.update(eq(TEST_ID), any(SubcontractProject.class))).thenReturn(testProject);

        ResponseEntity<SubcontractProject> response = subcontractProjectController.update(TEST_ID, testProject);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testProject, response.getBody());
    }

    @Test
    void update_WhenNotExists_ShouldReturnNotFound() {
        when(subcontractProjectService.update(eq(TEST_ID), any(SubcontractProject.class)))
                .thenThrow(new RuntimeException());

        ResponseEntity<SubcontractProject> response = subcontractProjectController.update(TEST_ID, testProject);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void delete_WhenExists_ShouldReturnNoContent() {
        doNothing().when(subcontractProjectService).delete(TEST_ID);

        ResponseEntity<Void> response = subcontractProjectController.delete(TEST_ID);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(subcontractProjectService).delete(TEST_ID);
    }

    @Test
    void delete_WhenNotExists_ShouldReturnNotFound() {
        doThrow(new RuntimeException()).when(subcontractProjectService).delete(TEST_ID);

        ResponseEntity<Void> response = subcontractProjectController.delete(TEST_ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getByMonths_ShouldReturnProjects() {
        List<SubcontractProject> projects = Arrays.asList(testProject);
        when(subcontractProjectService.getByMonths(TEST_MONTHS)).thenReturn(projects);

        ResponseEntity<List<SubcontractProject>> response = subcontractProjectController.getByMonths(TEST_MONTHS);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getByAmount_ShouldReturnProjects() {
        List<SubcontractProject> projects = Arrays.asList(testProject);
        when(subcontractProjectService.getByAmount(TEST_AMOUNT)).thenReturn(projects);

        ResponseEntity<List<SubcontractProject>> response = subcontractProjectController.getByAmount(TEST_AMOUNT);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getByShare_ShouldReturnProjects() {
        List<SubcontractProject> projects = Arrays.asList(testProject);
        when(subcontractProjectService.getByShare(TEST_SHARE)).thenReturn(projects);

        ResponseEntity<List<SubcontractProject>> response = subcontractProjectController.getByShare(TEST_SHARE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getByYear_ShouldReturnProjects() {
        List<SubcontractProject> projects = Arrays.asList(testProject);
        when(subcontractProjectService.getByYear(TEST_DATE)).thenReturn(projects);

        ResponseEntity<List<SubcontractProject>> response = subcontractProjectController.getByYear(TEST_DATE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getBySubcontractYearId_ShouldReturnProjects() {
        List<SubcontractProject> projects = Arrays.asList(testProject);
        when(subcontractProjectService.getBySubcontractYearId(TEST_ID)).thenReturn(projects);

        ResponseEntity<List<SubcontractProject>> response = subcontractProjectController.getBySubcontractYearId(TEST_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getByProjectId_ShouldReturnProjects() {
        List<SubcontractProject> projects = Arrays.asList(testProject);
        when(subcontractProjectService.getByProjectId(TEST_ID)).thenReturn(projects);

        ResponseEntity<List<SubcontractProject>> response = subcontractProjectController.getByProjectId(TEST_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getBySubcontractId_ShouldReturnProjects() {
        List<SubcontractProject> projects = Arrays.asList(testProject);
        when(subcontractProjectService.getBySubcontractId(TEST_ID)).thenReturn(projects);

        ResponseEntity<List<SubcontractProject>> response = subcontractProjectController.getBySubcontractId(TEST_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getByShareBetween_ShouldReturnProjects() {
        List<SubcontractProject> projects = Arrays.asList(testProject);
        when(subcontractProjectService.getByShareBetween(TEST_SHARE, TEST_SHARE.add(BigDecimal.ONE))).thenReturn(projects);

        ResponseEntity<List<SubcontractProject>> response = subcontractProjectController.getByShareBetween(TEST_SHARE, TEST_SHARE.add(BigDecimal.ONE));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getByMonthsGreaterThan_ShouldReturnProjects() {
        List<SubcontractProject> projects = Arrays.asList(testProject);
        when(subcontractProjectService.getByMonthsGreaterThan(TEST_MONTHS)).thenReturn(projects);

        ResponseEntity<List<SubcontractProject>> response = subcontractProjectController.getByMonthsGreaterThan(TEST_MONTHS);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getByMonthsLessThan_ShouldReturnProjects() {
        List<SubcontractProject> projects = Arrays.asList(testProject);
        when(subcontractProjectService.getByMonthsLessThan(TEST_MONTHS)).thenReturn(projects);

        ResponseEntity<List<SubcontractProject>> response = subcontractProjectController.getByMonthsLessThan(TEST_MONTHS);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getByYearAfter_ShouldReturnProjects() {
        List<SubcontractProject> projects = Arrays.asList(testProject);
        when(subcontractProjectService.getByYearAfter(TEST_DATE)).thenReturn(projects);

        ResponseEntity<List<SubcontractProject>> response = subcontractProjectController.getByYearAfter(TEST_DATE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getByYearBefore_ShouldReturnProjects() {
        List<SubcontractProject> projects = Arrays.asList(testProject);
        when(subcontractProjectService.getByYearBefore(TEST_DATE)).thenReturn(projects);

        ResponseEntity<List<SubcontractProject>> response = subcontractProjectController.getByYearBefore(TEST_DATE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }
}