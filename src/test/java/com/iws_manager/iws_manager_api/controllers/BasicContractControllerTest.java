package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.BasicContract;
import com.iws_manager.iws_manager_api.services.interfaces.BasicContractService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BasicContractControllerTest {

    private static final Long ID = 1L;
    private static final Integer CONTRACT_NO = 1001;
    private static final String CONTRACT_LABEL = "CON-2023-001";
    private static final String CONTRACT_TITLE = "Annual Maintenance";
    private static final LocalDate DATE = LocalDate.now();
    private static final Long CUSTOMER_ID = 2L;
    private static final Long CONTRACT_STATUS_ID = 3L;
    private static final Long FUNDING_PROGRAM_ID = 4L;
    private static final Long EMPLOYEE_IWS_ID = 5L;
    private static final LocalDate START_DATE = LocalDate.now().minusDays(10);
    private static final LocalDate END_DATE = LocalDate.now().plusDays(10);

    @Mock
    private BasicContractService basicContractService;

    @InjectMocks
    private BasicContractController basicContractController;

    private BasicContract basicContract;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        basicContract = new BasicContract();
        basicContract.setId(ID);
        basicContract.setContractNo(CONTRACT_NO);
        basicContract.setContractLabel(CONTRACT_LABEL);
        basicContract.setContractTitle(CONTRACT_TITLE);
        basicContract.setDate(DATE);
    }

    // CRUD Operations Tests
    @Test
    void createReturnsCreatedBasicContract() {
        when(basicContractService.create(basicContract)).thenReturn(basicContract);

        ResponseEntity<?> response = basicContractController.create(basicContract);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(basicContract, response.getBody());
        verify(basicContractService, times(1)).create(basicContract);
    }

    @Test
    void getByIdFound() {
        when(basicContractService.findById(ID)).thenReturn(Optional.of(basicContract));

        ResponseEntity<BasicContract> response = basicContractController.getById(ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(basicContract, response.getBody());
    }

    @Test
    void getByIdNotFound() {
        when(basicContractService.findById(ID)).thenReturn(Optional.empty());

        ResponseEntity<BasicContract> response = basicContractController.getById(ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAllReturnsList() {
        List<BasicContract> list = Arrays.asList(basicContract);
        when(basicContractService.findAll()).thenReturn(list);

        ResponseEntity<List<BasicContract>> response = basicContractController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void updateFound() {
        when(basicContractService.update(ID, basicContract)).thenReturn(basicContract);

        ResponseEntity<BasicContract> response = basicContractController.update(ID, basicContract);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(basicContract, response.getBody());
    }

    @Test
    void updateNotFound() {
        when(basicContractService.update(ID, basicContract)).thenThrow(new RuntimeException());

        ResponseEntity<BasicContract> response = basicContractController.update(ID, basicContract);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteFound() {
        ResponseEntity<Void> response = basicContractController.delete(ID);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(basicContractService, times(1)).delete(ID);
    }

    @Test
    void deleteNotFound() {
        doThrow(new RuntimeException()).when(basicContractService).delete(ID);

        ResponseEntity<Void> response = basicContractController.delete(ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Property-based Tests
    @Test
    void getByConfirmationDateReturnsList() {
        List<BasicContract> list = Arrays.asList(basicContract);
        when(basicContractService.getByConfirmationDate(DATE)).thenReturn(list);

        ResponseEntity<List<BasicContract>> response = basicContractController.getByConfirmationDate(DATE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByContractLabelReturnsList() {
        List<BasicContract> list = Arrays.asList(basicContract);
        when(basicContractService.getByContractLabel(CONTRACT_LABEL)).thenReturn(list);

        ResponseEntity<List<BasicContract>> response = basicContractController.getByContractLabel(CONTRACT_LABEL);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByContractNoReturnsList() {
        List<BasicContract> list = Arrays.asList(basicContract);
        when(basicContractService.getByContractNo(CONTRACT_NO)).thenReturn(list);

        ResponseEntity<List<BasicContract>> response = basicContractController.getByContractNo(CONTRACT_NO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByContractStatusIdReturnsList() {
        List<BasicContract> list = Arrays.asList(basicContract);
        when(basicContractService.getByContractStatusId(CONTRACT_STATUS_ID)).thenReturn(list);

        ResponseEntity<List<BasicContract>> response = basicContractController.getByContractStatusId(CONTRACT_STATUS_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByContractTitleReturnsList() {
        List<BasicContract> list = Arrays.asList(basicContract);
        when(basicContractService.getByContractTitle(CONTRACT_TITLE)).thenReturn(list);

        ResponseEntity<List<BasicContract>> response = basicContractController.getByContractTitle(CONTRACT_TITLE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByCustomerIdReturnsList() {
        List<BasicContract> list = Arrays.asList(basicContract);
        when(basicContractService.getByCustomerId(CUSTOMER_ID)).thenReturn(list);

        ResponseEntity<List<BasicContract>> response = basicContractController.getByCustomerId(CUSTOMER_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByDateReturnsList() {
        List<BasicContract> list = Arrays.asList(basicContract);
        when(basicContractService.getByDate(DATE)).thenReturn(list);

        ResponseEntity<List<BasicContract>> response = basicContractController.getByDate(DATE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByFundingProgramIdReturnsList() {
        List<BasicContract> list = Arrays.asList(basicContract);
        when(basicContractService.getByFundingProgramId(FUNDING_PROGRAM_ID)).thenReturn(list);

        ResponseEntity<List<BasicContract>> response = basicContractController.getByFundingProgramId(FUNDING_PROGRAM_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByEmployeeIwsIdReturnsList() {
        List<BasicContract> list = Arrays.asList(basicContract);
        when(basicContractService.getByEmployeeIwsId(EMPLOYEE_IWS_ID)).thenReturn(list);

        ResponseEntity<List<BasicContract>> response = basicContractController.getByEmployeeIwsId(EMPLOYEE_IWS_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    // Ordered Finders Tests
    @Test
    void getByCustomerIdOrderByContractNoAscReturnsList() {
        List<BasicContract> list = Arrays.asList(basicContract);
        when(basicContractService.getByCustomerIdOrderByContractNoAsc(CUSTOMER_ID)).thenReturn(list);

        ResponseEntity<List<BasicContract>> response = 
            basicContractController.getByCustomerIdOrderByContractNoAsc(CUSTOMER_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByCustomerIdOrderByContractLabelAscReturnsList() {
        List<BasicContract> list = Arrays.asList(basicContract);
        when(basicContractService.getByCustomerIdOrderByContractLabelAsc(CUSTOMER_ID)).thenReturn(list);

        ResponseEntity<List<BasicContract>> response = 
            basicContractController.getByCustomerIdOrderByContractLabelAsc(CUSTOMER_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    // Date Range Tests
    @Test
    void getByDateRangeReturnsList() {
        List<BasicContract> list = Arrays.asList(basicContract);
        when(basicContractService.getByDateBetween(START_DATE, END_DATE)).thenReturn(list);

        ResponseEntity<List<BasicContract>> response = 
            basicContractController.getByDateRange(START_DATE, END_DATE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByConfirmationDateRangeReturnsList() {
        List<BasicContract> list = Arrays.asList(basicContract);
        when(basicContractService.getByConfirmationDateBetween(START_DATE, END_DATE)).thenReturn(list);

        ResponseEntity<List<BasicContract>> response = 
            basicContractController.getByConfirmationDateRange(START_DATE, END_DATE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }
}