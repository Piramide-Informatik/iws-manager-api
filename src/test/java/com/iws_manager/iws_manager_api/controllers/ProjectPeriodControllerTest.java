package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.ProjectPeriod;
import com.iws_manager.iws_manager_api.services.interfaces.ProjectPeriodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
class ProjectPeriodControllerTest {
    @Mock
    private ProjectPeriodService periodService;

    @InjectMocks
    private ProjectPeriodController periodController;

    private ProjectPeriod testPeriod;
    private static final Long TEST_ID = 1L;

    @BeforeEach
    void setUp() {
        testPeriod = new ProjectPeriod();
        testPeriod.setId(TEST_ID);
        testPeriod.setPeriodNo((short) 1);
    }

    @Test
    void updateShouldReturnUpdatedProjectPeriod() {
        when(periodService.update(eq(TEST_ID), any(ProjectPeriod.class))).thenReturn(testPeriod);

        ResponseEntity<ProjectPeriod> response = periodController.update(TEST_ID, testPeriod);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testPeriod, response.getBody());
    }

    @Test
    void updateShouldReturnNotFoundWhenProjectPeriodNotExists() {
        when(periodService.update(eq(TEST_ID), any(ProjectPeriod.class)))
                .thenThrow(new RuntimeException("Not found"));

        ResponseEntity<ProjectPeriod> response = periodController.update(TEST_ID, testPeriod);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createShouldReturnCreatedProjectPeriod() {
        when(periodService.create(any(ProjectPeriod.class))).thenReturn(testPeriod);

        ResponseEntity<?> response = periodController.create(testPeriod);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testPeriod, response.getBody());
        verify(periodService).create(testPeriod);
    }

    @Test
    void getByIdShouldReturnProjectPeriodWhenExists() {
        when(periodService.findById(TEST_ID)).thenReturn(Optional.of(testPeriod));

        ResponseEntity<ProjectPeriod> response = periodController.getById(TEST_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testPeriod, response.getBody());
    }

    @Test
    void getByIdShouldReturnNotFoundWhenNotExists() {
        when(periodService.findById(TEST_ID)).thenReturn(Optional.empty());

        ResponseEntity<ProjectPeriod> response = periodController.getById(TEST_ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAllShouldReturnAllProjectPeriod() {
        List<ProjectPeriod> projects = Arrays.asList(testPeriod);
        when(periodService.findAll()).thenReturn(projects);

        ResponseEntity<List<ProjectPeriod>> response = periodController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void deleteShouldReturnNoContentWhenSuccess() {
        doNothing().when(periodService).delete(TEST_ID);

        ResponseEntity<Void> response = periodController.delete(TEST_ID);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(periodService).delete(TEST_ID);
    }

    @Test
    void deleteShouldReturnNotFoundWhenProjectPeriodNotExists() {
        doThrow(new RuntimeException("Not found")).when(periodService).delete(TEST_ID);

        ResponseEntity<Void> response = periodController.delete(TEST_ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAllShouldReturnAllProjectPeriodsByOrderByNoAsc() {
        List<ProjectPeriod> projects = Arrays.asList(testPeriod);
        when(periodService.getAllProjectPeriodsByPeriodNoAsc()).thenReturn(projects);

        ResponseEntity<List<ProjectPeriod>> response = periodController.getAllByOrderByNoAsc();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getAllShouldReturnAllProjectPeriodsByOrderByStartDateAsc() {
        List<ProjectPeriod> projects = Arrays.asList(testPeriod);
        when(periodService.getAllProjectPeriodsByStartDateAsc()).thenReturn(projects);

        ResponseEntity<List<ProjectPeriod>> response = periodController.getAllByOrderByStartDateAsc();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getAllShouldReturnAllProjectPeriodsByOrderByEndDateAsc() {
        List<ProjectPeriod> projects = Arrays.asList(testPeriod);
        when(periodService.getAllProjectPeriodsByEndDateAsc()).thenReturn(projects);

        ResponseEntity<List<ProjectPeriod>> response = periodController.getAllByOrderByEndDateAsc();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }
}