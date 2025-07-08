package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.EmploymentContract;
import com.iws_manager.iws_manager_api.services.interfaces.EmploymentContractService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmploymentContractControllerTest {

    private static final Long CONTRACT_ID = 1L;
    private static final Long EMPLOYEE_ID = 1L;
    private static final Long CUSTOMER_ID = 1L;

    @Mock
    private EmploymentContractService employmentContractService;

    @InjectMocks
    private EmploymentContractController employmentContractController;

    private EmploymentContract contract;

    @BeforeEach
    void setUp() {
        contract = new EmploymentContract();
        contract.setId(CONTRACT_ID);
    }

    @Test
    void createShouldReturnCreatedContract() {
        when(employmentContractService.create(any(EmploymentContract.class))).thenReturn(contract);

        ResponseEntity<EmploymentContract> response = employmentContractController.create(contract);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(contract, response.getBody());
        verify(employmentContractService, times(1)).create(contract);
    }

    @Test
    void getByIdShouldReturnContractWhenExists() {
        when(employmentContractService.findById(CONTRACT_ID)).thenReturn(Optional.of(contract));

        ResponseEntity<EmploymentContract> response = employmentContractController.getById(CONTRACT_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(contract, response.getBody());
    }

    @Test
    void getByIdShouldReturnNotFoundWhenNotExists() {
        when(employmentContractService.findById(CONTRACT_ID)).thenReturn(Optional.empty());

        ResponseEntity<EmploymentContract> response = employmentContractController.getById(CONTRACT_ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAllShouldReturnAllContracts() {
        List<EmploymentContract> contracts = Arrays.asList(contract, new EmploymentContract());
        when(employmentContractService.findAll()).thenReturn(contracts);

        ResponseEntity<List<EmploymentContract>> response = employmentContractController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void updateShouldReturnUpdatedContract() {
        EmploymentContract updatedContract = new EmploymentContract();
        updatedContract.setHourlyRate(25.0);

        when(employmentContractService.update(anyLong(), any(EmploymentContract.class))).thenReturn(updatedContract);

        ResponseEntity<EmploymentContract> response = employmentContractController.update(CONTRACT_ID, updatedContract);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(25.0, response.getBody().getHourlyRate());
    }

    @Test
    void updateShouldReturnNotFoundWhenExceptionThrown() {
        when(employmentContractService.update(anyLong(), any(EmploymentContract.class)))
            .thenThrow(new RuntimeException());

        ResponseEntity<EmploymentContract> response = employmentContractController.update(CONTRACT_ID, contract);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteShouldReturnNoContent() {
        doNothing().when(employmentContractService).delete(CONTRACT_ID);

        ResponseEntity<Void> response = employmentContractController.delete(CONTRACT_ID);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(employmentContractService, times(1)).delete(CONTRACT_ID);
    }

    @Test
    void getByEmployeeIdShouldReturnContracts() {
        List<EmploymentContract> contracts = Arrays.asList(contract);
        when(employmentContractService.findByEmployeeId(EMPLOYEE_ID)).thenReturn(contracts);

        ResponseEntity<List<EmploymentContract>> response = employmentContractController.getByEmployeeId(EMPLOYEE_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getByCustomerIdShouldReturnContracts() {
        List<EmploymentContract> contracts = Arrays.asList(contract);
        when(employmentContractService.findByCustomerId(CUSTOMER_ID)).thenReturn(contracts);

        ResponseEntity<List<EmploymentContract>> response = employmentContractController.getByCustomerId(CUSTOMER_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }
}