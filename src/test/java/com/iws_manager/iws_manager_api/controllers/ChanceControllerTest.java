package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.Chance;
import com.iws_manager.iws_manager_api.services.interfaces.ChanceService;
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
import static org.mockito.Mockito.*;

class ChanceControllerTest {

    private static final Long ID = 1L;
    private static final BigDecimal PROBABILITY = new BigDecimal("75.50");

    @Mock
    private ChanceService chanceService;

    @InjectMocks
    private ChanceController chanceController;

    private Chance chance;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        chance = new Chance();
        chance.setId(ID);
        chance.setProbability(PROBABILITY);
    }

    @Test
    void createReturnsCreatedChance() {
        when(chanceService.create(chance)).thenReturn(chance);

        ResponseEntity<?> response = chanceController.create(chance);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(chance, response.getBody());
        verify(chanceService, times(1)).create(chance);
    }

    @Test
    void getByIdFound() {
        when(chanceService.findById(ID)).thenReturn(Optional.of(chance));

        ResponseEntity<Chance> response = chanceController.getById(ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(chance, response.getBody());
    }

    @Test
    void getByIdNotFound() {
        when(chanceService.findById(ID)).thenReturn(Optional.empty());

        ResponseEntity<Chance> response = chanceController.getById(ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAllReturnsList() {
        List<Chance> list = Arrays.asList(chance);
        when(chanceService.findAll()).thenReturn(list);

        ResponseEntity<List<Chance>> response = chanceController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void updateFound() {
        when(chanceService.update(ID, chance)).thenReturn(chance);

        ResponseEntity<Chance> response = chanceController.update(ID, chance);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(chance, response.getBody());
    }

    @Test
    void updateNotFound() {
        when(chanceService.update(ID, chance)).thenThrow(new RuntimeException());

        ResponseEntity<Chance> response = chanceController.update(ID, chance);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteFound() {
        ResponseEntity<Void> response = chanceController.delete(ID);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(chanceService, times(1)).delete(ID);
    }

    @Test
    void deleteNotFound() {
        doThrow(new RuntimeException()).when(chanceService).delete(ID);

        ResponseEntity<Void> response = chanceController.delete(ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAllReturnsEmptyList() {
        List<Chance> emptyList = List.of();
        when(chanceService.findAll()).thenReturn(emptyList);

        ResponseEntity<List<Chance>> response = chanceController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(emptyList, response.getBody());
        assertEquals(0, response.getBody().size());
    }

    @Test
    void createWithNullProbability() {
        Chance chanceWithNullProbability = new Chance();
        chanceWithNullProbability.setId(ID);
        chanceWithNullProbability.setProbability(null);

        when(chanceService.create(chanceWithNullProbability)).thenReturn(chanceWithNullProbability);

        ResponseEntity<?> response = chanceController.create(chanceWithNullProbability);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(chanceWithNullProbability, response.getBody());
        verify(chanceService, times(1)).create(chanceWithNullProbability);
    }

    @Test
    void updateWithDifferentProbability() {
        Chance updatedChance = new Chance();
        updatedChance.setId(ID);
        updatedChance.setProbability(new BigDecimal("90.25"));

        when(chanceService.update(ID, updatedChance)).thenReturn(updatedChance);

        ResponseEntity<Chance> response = chanceController.update(ID, updatedChance);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedChance, response.getBody());
        assertEquals(new BigDecimal("90.25"), response.getBody().getProbability());
    }
}