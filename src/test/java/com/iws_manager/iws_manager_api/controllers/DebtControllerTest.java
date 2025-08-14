package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.Debt;
import com.iws_manager.iws_manager_api.services.interfaces.DebtService;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DebtControllerTest {

    @Mock
    private DebtService debtService;

    @InjectMocks
    private DebtController debtController;

    private Debt testDebt;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testDebt = new Debt();
        testDebt.setId(1L);
        testDebt.setDebtTitle("Test Debt");
        testDebt.setGrossAmount(BigDecimal.valueOf(1000));
    }

    @Test
    void createDebtShouldReturnCreatedDebt() {
        when(debtService.create(any(Debt.class))).thenReturn(testDebt);

        ResponseEntity<?> response = debtController.create(testDebt);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testDebt, response.getBody());
        verify(debtService, times(1)).create(testDebt);
    }

    @Test
    void getByIdWhenDebtExistsShouldReturnDebt() {
        when(debtService.findById(1L)).thenReturn(Optional.of(testDebt));

        ResponseEntity<Debt> response = debtController.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testDebt, response.getBody());
    }

    @Test
    void getByIdWhenDebtNotExistsShouldReturnNotFound() {
        when(debtService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Debt> response = debtController.getById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAllShouldReturnAllDebts() {
        List<Debt> debts = Arrays.asList(testDebt, new Debt());
        when(debtService.findAll()).thenReturn(debts);

        ResponseEntity<List<Debt>> response = debtController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void updateWhenDebtExistsShouldReturnUpdatedDebt() {
        when(debtService.update(eq(1L), any(Debt.class))).thenReturn(testDebt);

        ResponseEntity<Debt> response = debtController.update(1L, testDebt);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testDebt, response.getBody());
    }

    @Test
    void updateWhenDebtNotExistsShouldReturnNotFound() {
        when(debtService.update(eq(1L), any(Debt.class))).thenThrow(new RuntimeException());

        ResponseEntity<Debt> response = debtController.update(1L, testDebt);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteWhenDebtExistsShouldReturnNoContent() {
        doNothing().when(debtService).delete(1L);

        ResponseEntity<Void> response = debtController.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(debtService, times(1)).delete(1L);
    }

    @Test
    void deleteWhenDebtNotExistsShouldReturnNotFound() {
        doThrow(new RuntimeException()).when(debtService).delete(1L);

        ResponseEntity<Void> response = debtController.delete(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getByCustomerIdShouldReturnDebts() {
        List<Debt> debts = Collections.singletonList(testDebt);
        when(debtService.getByCustomerId(1L)).thenReturn(debts);

        ResponseEntity<List<Debt>> response = debtController.getByCustomerId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getByDebtTitleContainingShouldReturnMatchingDebts() {
        List<Debt> debts = Collections.singletonList(testDebt);
        when(debtService.getByDebtTitleContainingIgnoreCase("test")).thenReturn(debts);

        ResponseEntity<List<Debt>> response = debtController.getByDebtTitleContaining("test");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getByBillingStartBetweenWithValidDatesShouldReturnDebts() {
        LocalDate start = LocalDate.now().minusDays(10);
        LocalDate end = LocalDate.now();
        List<Debt> debts = Collections.singletonList(testDebt);
        when(debtService.getByBillingStartBetween(start, end)).thenReturn(debts);

        ResponseEntity<List<Debt>> response = debtController.getDebtsByBillingStartRange(start, end);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getByBillingStartBetweenWithInvalidDatesShouldReturnBadRequest() {
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().minusDays(10);

        ResponseEntity<List<Debt>> response = debtController.getDebtsByBillingStartRange(start, end);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getByGrossAmountGreaterThanShouldReturnDebts() {
        List<Debt> debts = Collections.singletonList(testDebt);
        when(debtService.getByGrossAmountGreaterThan(BigDecimal.valueOf(500))).thenReturn(debts);

        ResponseEntity<List<Debt>> response = debtController.getDebtsWithGrossAmountGreaterThan(BigDecimal.valueOf(500));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getByGrossAmountGreaterThanWithNegativeAmountShouldReturnBadRequest() {
        ResponseEntity<List<Debt>> response = debtController.getDebtsWithGrossAmountGreaterThan(BigDecimal.valueOf(-100));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void calculateTotalDebtByCustomerShouldReturnTotal() {
        when(debtService.calculateTotalDebtByCustomer(1L)).thenReturn(BigDecimal.valueOf(1500));

        ResponseEntity<BigDecimal> response = debtController.getTotalDebtByCustomer(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(BigDecimal.valueOf(1500), response.getBody());
    }

    @Test
    void calculateTotalDebtByCustomerWhenCustomerNotFoundShouldReturnNotFound() {
        when(debtService.calculateTotalDebtByCustomer(1L)).thenThrow(new RuntimeException());

        ResponseEntity<BigDecimal> response = debtController.getTotalDebtByCustomer(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void findOverdueDebtsShouldReturnOverdueDebts() {
        List<Debt> debts = Collections.singletonList(testDebt);
        LocalDate currentDate = LocalDate.now();
        when(debtService.findOverdueDebts(currentDate)).thenReturn(debts);

        ResponseEntity<List<Debt>> response = debtController.getOverdueDebts(currentDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void findOverdueDebtsWithNoDateShouldUseCurrentDate() {
        List<Debt> debts = Collections.singletonList(testDebt);
        when(debtService.findOverdueDebts(LocalDate.now())).thenReturn(debts);

        ResponseEntity<List<Debt>> response = debtController.getOverdueDebts(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }
}