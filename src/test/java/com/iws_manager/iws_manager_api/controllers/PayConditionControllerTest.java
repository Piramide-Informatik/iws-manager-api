package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.PayCondition;
import com.iws_manager.iws_manager_api.services.interfaces.PayConditionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PayConditionControllerTest {

    private static final Long ID = 1L;
    private static final String NAME = "30 Days Net";
    private static final Integer DEADLINE = 30;

    @Mock
    private PayConditionService payConditionService;

    @InjectMocks
    private PayConditionController payConditionController;

    private PayCondition payCondition;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        payCondition = new PayCondition();
        payCondition.setId(ID);
        payCondition.setName(NAME);
        payCondition.setDeadline(DEADLINE);
    }

    @Test
    void createReturnsCreatedPayCondition() {
        when(payConditionService.create(payCondition)).thenReturn(payCondition);

        ResponseEntity<?> response = payConditionController.create(payCondition);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(payCondition, response.getBody());
        verify(payConditionService, times(1)).create(payCondition);
    }

    @Test
    void getByIdFound() {
        when(payConditionService.findById(ID)).thenReturn(Optional.of(payCondition));

        ResponseEntity<PayCondition> response = payConditionController.getById(ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(payCondition, response.getBody());
    }

    @Test
    void getByIdNotFound() {
        when(payConditionService.findById(ID)).thenReturn(Optional.empty());

        ResponseEntity<PayCondition> response = payConditionController.getById(ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAllReturnsList() {
        List<PayCondition> list = Arrays.asList(payCondition);
        when(payConditionService.findAll()).thenReturn(list);

        ResponseEntity<List<PayCondition>> response = payConditionController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getAllReturnsEmptyList() {
        when(payConditionService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<PayCondition>> response = payConditionController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    void updateFound() {
        when(payConditionService.update(ID, payCondition)).thenReturn(payCondition);

        ResponseEntity<PayCondition> response = payConditionController.update(ID, payCondition);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(payCondition, response.getBody());
    }

    @Test
    void updateNotFound() {
        when(payConditionService.update(ID, payCondition)).thenThrow(new RuntimeException());

        ResponseEntity<PayCondition> response = payConditionController.update(ID, payCondition);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteFound() {
        ResponseEntity<Void> response = payConditionController.delete(ID);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(payConditionService, times(1)).delete(ID);
    }

    @Test
    void deleteNotFound() {
        doThrow(new RuntimeException()).when(payConditionService).delete(ID);

        ResponseEntity<Void> response = payConditionController.delete(ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getByNameReturnsList() {
        List<PayCondition> list = Arrays.asList(payCondition);
        when(payConditionService.getByName(NAME)).thenReturn(list);

        ResponseEntity<List<PayCondition>> response = payConditionController.getByName(NAME);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByNameReturnsEmptyList() {
        when(payConditionService.getByName(NAME)).thenReturn(Collections.emptyList());

        ResponseEntity<List<PayCondition>> response = payConditionController.getByName(NAME);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    void getByDeadlineReturnsList() {
        List<PayCondition> list = Arrays.asList(payCondition);
        when(payConditionService.getByDeadline(DEADLINE)).thenReturn(list);

        ResponseEntity<List<PayCondition>> response = payConditionController.getByDeadline(DEADLINE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByDeadlineReturnsEmptyList() {
        when(payConditionService.getByDeadline(DEADLINE)).thenReturn(Collections.emptyList());

        ResponseEntity<List<PayCondition>> response = payConditionController.getByDeadline(DEADLINE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    void getByDeadlineWithDifferentValue() {
        Integer differentDeadline = 60;
        List<PayCondition> list = Arrays.asList(payCondition);
        when(payConditionService.getByDeadline(differentDeadline)).thenReturn(list);

        ResponseEntity<List<PayCondition>> response = payConditionController.getByDeadline(differentDeadline);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }
}