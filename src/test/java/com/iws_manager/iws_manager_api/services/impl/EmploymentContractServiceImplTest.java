package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.EmploymentContract;
import com.iws_manager.iws_manager_api.repositories.EmploymentContractRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmploymentContractServiceImplTest {

    @Mock
    private EmploymentContractRepository employmentContractRepository;

    @InjectMocks
    private EmploymentContractServiceImpl employmentContractService;

    private EmploymentContract contract;
    private static final Long CONTRACT_ID = 1L;
    private static final Long EMPLOYEE_ID = 1L;
    private static final Long CUSTOMER_ID = 1L;

    @BeforeEach
    void setUp() {
        contract = new EmploymentContract();
        contract.setId(CONTRACT_ID);
    }

    @Test
    void createShouldSaveAndReturnContract() {
        when(employmentContractRepository.save(any(EmploymentContract.class))).thenReturn(contract);

        EmploymentContract result = employmentContractService.create(contract);

        assertNotNull(result);
        assertEquals(CONTRACT_ID, result.getId());
        verify(employmentContractRepository, times(1)).save(contract);
    }

    @Test
    void createWithNullContractShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> employmentContractService.create(null));
    }

    @Test
    void findByIdShouldReturnContract() {
        when(employmentContractRepository.findById(CONTRACT_ID)).thenReturn(Optional.of(contract));

        Optional<EmploymentContract> result = employmentContractService.findById(CONTRACT_ID);

        assertTrue(result.isPresent());
        assertEquals(CONTRACT_ID, result.get().getId());
    }

    @Test
    void findByIdWithNullIdShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> employmentContractService.findById(null));
    }

    @Test
    void findByIdNonExistentIdShouldReturnEmptyOptional() {
        when(employmentContractRepository.findById(CONTRACT_ID)).thenReturn(Optional.empty());

        Optional<EmploymentContract> result = employmentContractService.findById(CONTRACT_ID);

        assertFalse(result.isPresent());
    }

    @Test
    void findAllShouldReturnAllContracts() {
        List<EmploymentContract> contracts = Arrays.asList(contract, new EmploymentContract());
        when(employmentContractRepository.findAll()).thenReturn(contracts);

        List<EmploymentContract> result = employmentContractService.findAll();

        assertEquals(2, result.size());
        verify(employmentContractRepository, times(1)).findAll();
    }

    @Test
    void updateShouldUpdateAndReturnContract() {
        EmploymentContract updatedContract = new EmploymentContract();
        updatedContract.setHourlyRate(25.50);

        when(employmentContractRepository.findById(CONTRACT_ID)).thenReturn(Optional.of(contract));
        when(employmentContractRepository.save(any(EmploymentContract.class))).thenReturn(updatedContract);

        EmploymentContract result = employmentContractService.update(CONTRACT_ID, updatedContract);

        assertNotNull(result);
        assertEquals(25.50, result.getHourlyRate());
        verify(employmentContractRepository, times(1)).save(contract);
    }

    @Test
    void updateWithNullIdShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, 
            () -> employmentContractService.update(null, new EmploymentContract()));
    }

    @Test
    void updateWithNullContractShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, 
            () -> employmentContractService.update(CONTRACT_ID, null));
    }

    @Test
    void updateNonExistentIdShouldThrowRuntimeException() {
        when(employmentContractRepository.findById(CONTRACT_ID)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, 
            () -> employmentContractService.update(CONTRACT_ID, new EmploymentContract()));
    }

    @Test
    void deleteShouldDeleteContract() {
        when(employmentContractRepository.existsById(CONTRACT_ID)).thenReturn(true);

        employmentContractService.delete(CONTRACT_ID);

        verify(employmentContractRepository, times(1)).deleteById(CONTRACT_ID);
    }

    @Test
    void deleteWithNullIdShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> employmentContractService.delete(null));
    }

    @Test
    void deleteNonExistentIdShouldThrowEntityNotFoundException() {
        when(employmentContractRepository.existsById(CONTRACT_ID)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, 
            () -> employmentContractService.delete(CONTRACT_ID));
    }

    @Test
    void findByEmployeeIdShouldReturnContracts() {
        List<EmploymentContract> contracts = Arrays.asList(contract);
        when(employmentContractRepository.findByEmployeeId(EMPLOYEE_ID)).thenReturn(contracts);

        List<EmploymentContract> result = employmentContractService.findByEmployeeId(EMPLOYEE_ID);

        assertEquals(1, result.size());
        verify(employmentContractRepository, times(1)).findByEmployeeId(EMPLOYEE_ID);
    }

    @Test
    void findByCustomerIdShouldReturnContracts() {
        List<EmploymentContract> contracts = Arrays.asList(contract);
        when(employmentContractRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(contracts);

        List<EmploymentContract> result = employmentContractService.findByCustomerId(CUSTOMER_ID);

        assertEquals(1, result.size());
        verify(employmentContractRepository, times(1)).findByCustomerId(CUSTOMER_ID);
    }
}