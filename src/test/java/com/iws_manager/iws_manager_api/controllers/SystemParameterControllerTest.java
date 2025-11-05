package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.SystemParameter;
import com.iws_manager.iws_manager_api.services.interfaces.SystemParameterService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import jakarta.persistence.EntityNotFoundException;

class SystemParameterControllerTest {

    private static final Long ID = 1L;
    private static final String PARAMETER_NAME = "MAX_USERS";
    private static final String CHAR_VALUE = "100";
    private static final BigDecimal NUM_VALUE = new BigDecimal("99.99");

    @Mock
    private SystemParameterService systemParameterService;

    @InjectMocks
    private SystemParameterController systemParameterController;

    private SystemParameter systemParameter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        systemParameter = new SystemParameter();
        systemParameter.setId(ID);
        systemParameter.setName(PARAMETER_NAME);
        systemParameter.setValueChar(CHAR_VALUE);
        systemParameter.setValueNum(NUM_VALUE);
    }

    @Test
    void createReturnsCreatedSystemParameter() {
        when(systemParameterService.create(systemParameter)).thenReturn(systemParameter);

        ResponseEntity<?> response = systemParameterController.create(systemParameter);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(systemParameter, response.getBody());
        verify(systemParameterService, times(1)).create(systemParameter);
    }

    @Test
    void getByIdFound() {
        when(systemParameterService.findById(ID)).thenReturn(Optional.of(systemParameter));

        ResponseEntity<SystemParameter> response = systemParameterController.getById(ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(systemParameter, response.getBody());
    }

    @Test
    void getByIdNotFound() {
        when(systemParameterService.findById(ID)).thenReturn(Optional.empty());

        ResponseEntity<SystemParameter> response = systemParameterController.getById(ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAllReturnsList() {
        List<SystemParameter> list = Arrays.asList(systemParameter);
        when(systemParameterService.findAll()).thenReturn(list);

        ResponseEntity<List<SystemParameter>> response = systemParameterController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getAllReturnsEmptyList() {
        List<SystemParameter> emptyList = Arrays.asList();
        when(systemParameterService.findAll()).thenReturn(emptyList);

        ResponseEntity<List<SystemParameter>> response = systemParameterController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    void updateFound() {
        when(systemParameterService.update(ID, systemParameter)).thenReturn(systemParameter);

        ResponseEntity<SystemParameter> response = systemParameterController.update(ID, systemParameter);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(systemParameter, response.getBody());
    }

    @Test
    void updateNotFound() {
        when(systemParameterService.update(eq(ID), any(SystemParameter.class)))
            .thenThrow(new EntityNotFoundException("SystemParameter not found with id: " + ID));

        assertThrows(EntityNotFoundException.class, () -> systemParameterController.update(ID, systemParameter));

        verify(systemParameterService, times(1)).update(eq(ID), any(SystemParameter.class));
    }

    @Test
    void deleteSuccess() {
        doNothing().when(systemParameterService).delete(ID);

        ResponseEntity<Void> response = systemParameterController.delete(ID);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(systemParameterService, times(1)).delete(ID);
    }

    @Test
    void createValidatesAllFields() {
        SystemParameter newParameter = new SystemParameter();
        newParameter.setName("NEW_PARAM");
        newParameter.setValueChar("NEW_VALUE");
        newParameter.setValueNum(new BigDecimal("50.0"));

        when(systemParameterService.create(newParameter)).thenReturn(newParameter);

        ResponseEntity<?> response = systemParameterController.create(newParameter);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        SystemParameter result = (SystemParameter) response.getBody();
        assertEquals("NEW_PARAM", result.getName());
        assertEquals("NEW_VALUE", result.getValueChar());
        assertEquals(new BigDecimal("50.0"), result.getValueNum());
    }

    @Test
    void updateModifiesAllFields() {
        SystemParameter updatedDetails = new SystemParameter();
        updatedDetails.setName("UPDATED_NAME");
        updatedDetails.setValueChar("UPDATED_VALUE");
        updatedDetails.setValueNum(new BigDecimal("200.0"));

        when(systemParameterService.update(ID, updatedDetails)).thenReturn(updatedDetails);

        ResponseEntity<SystemParameter> response = systemParameterController.update(ID, updatedDetails);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        SystemParameter result = response.getBody();
        assertEquals("UPDATED_NAME", result.getName());
        assertEquals("UPDATED_VALUE", result.getValueChar());
        assertEquals(new BigDecimal("200.0"), result.getValueNum());
    }

    @Test
    void getByIdReturnsCorrectData() {
        when(systemParameterService.findById(ID)).thenReturn(Optional.of(systemParameter));

        ResponseEntity<SystemParameter> response = systemParameterController.getById(ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        SystemParameter result = response.getBody();
        assertEquals(PARAMETER_NAME, result.getName());
        assertEquals(CHAR_VALUE, result.getValueChar());
        assertEquals(NUM_VALUE, result.getValueNum());
    }
}