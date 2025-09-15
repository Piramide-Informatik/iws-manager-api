package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.ReminderLevel;
import com.iws_manager.iws_manager_api.services.interfaces.ReminderLevelService;
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

class ReminderLevelControllerTest {

    private static final Long ID = 1L;
    private static final BigDecimal FEE = new BigDecimal("15.50");
    private static final BigDecimal INTEREST_RATE = new BigDecimal("2.50");
    private static final Short LEVEL_NO = 1;
    private static final Short PAY_PERIOD = 14;
    private static final String REMINDER_TEXT = "First reminder text";
    private static final String REMINDER_TITLE = "1. Mahnung";

    @Mock
    private ReminderLevelService reminderLevelService;

    @InjectMocks
    private ReminderLevelController reminderLevelController;

    private ReminderLevel reminderLevel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reminderLevel = new ReminderLevel();
        reminderLevel.setId(ID);
        reminderLevel.setFee(FEE);
        reminderLevel.setInterestRate(INTEREST_RATE);
        reminderLevel.setLevelNo(LEVEL_NO);
        reminderLevel.setPayPeriod(PAY_PERIOD);
        reminderLevel.setReminderText(REMINDER_TEXT);
        reminderLevel.setReminderTitle(REMINDER_TITLE);
    }

    @Test
    void createReturnsCreatedReminderLevel() {
        when(reminderLevelService.create(reminderLevel)).thenReturn(reminderLevel);

        ResponseEntity<?> response = reminderLevelController.create(reminderLevel);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(reminderLevel, response.getBody());
        verify(reminderLevelService, times(1)).create(reminderLevel);
    }

    @Test
    void getByIdFound() {
        when(reminderLevelService.findById(ID)).thenReturn(Optional.of(reminderLevel));

        ResponseEntity<ReminderLevel> response = reminderLevelController.getById(ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reminderLevel, response.getBody());
    }

    @Test
    void getByIdNotFound() {
        when(reminderLevelService.findById(ID)).thenReturn(Optional.empty());

        ResponseEntity<ReminderLevel> response = reminderLevelController.getById(ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAllReturnsList() {
        List<ReminderLevel> list = Arrays.asList(reminderLevel);
        when(reminderLevelService.findAll()).thenReturn(list);

        ResponseEntity<List<ReminderLevel>> response = reminderLevelController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void updateFound() {
        when(reminderLevelService.update(ID, reminderLevel)).thenReturn(reminderLevel);

        ResponseEntity<ReminderLevel> response = reminderLevelController.update(ID, reminderLevel);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reminderLevel, response.getBody());
    }

    @Test
    void updateNotFound() {
        when(reminderLevelService.update(ID, reminderLevel)).thenThrow(new RuntimeException());

        ResponseEntity<ReminderLevel> response = reminderLevelController.update(ID, reminderLevel);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteFound() {
        ResponseEntity<Void> response = reminderLevelController.delete(ID);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(reminderLevelService, times(1)).delete(ID);
    }

    @Test
    void deleteNotFound() {
        doThrow(new RuntimeException()).when(reminderLevelService).delete(ID);

        ResponseEntity<Void> response = reminderLevelController.delete(ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAllReturnsEmptyList() {
        List<ReminderLevel> emptyList = List.of();
        when(reminderLevelService.findAll()).thenReturn(emptyList);

        ResponseEntity<List<ReminderLevel>> response = reminderLevelController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(emptyList, response.getBody());
        assertEquals(0, response.getBody().size());
    }
}