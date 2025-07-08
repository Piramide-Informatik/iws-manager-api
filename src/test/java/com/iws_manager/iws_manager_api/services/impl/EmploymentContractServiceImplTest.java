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
    private final Long contractId = 1L;
    private final Long employeeId = 1L;
    private final Long customerId = 1L;

    @BeforeEach
    void setUp() {
        contract = new EmploymentContract();
        contract.setId(contractId);
    }

    @Test
    void create_ShouldSaveAndReturnContract() {
        when(employmentContractRepository.save(any(EmploymentContract.class))).thenReturn(contract);

        EmploymentContract result = employmentContractService.create(contract);

        assertNotNull(result);
        assertEquals(contractId, result.getId());
        verify(employmentContractRepository, times(1)).save(contract);
    }

    @Test
    void create_WithNullContract_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> employmentContractService.create(null));
    }

    @Test
    void findById_ShouldReturnContract() {
        when(employmentContractRepository.findById(contractId)).thenReturn(Optional.of(contract));

        Optional<EmploymentContract> result = employmentContractService.findById(contractId);

        assertTrue(result.isPresent());
        assertEquals(contractId, result.get().getId());
    }

    @Test
    void findById_WithNullId_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> employmentContractService.findById(null));
    }

    @Test
    void findById_NonExistentId_ShouldReturnEmptyOptional() {
        when(employmentContractRepository.findById(contractId)).thenReturn(Optional.empty());

        Optional<EmploymentContract> result = employmentContractService.findById(contractId);

        assertFalse(result.isPresent());
    }

    @Test
    void findAll_ShouldReturnAllContracts() {
        List<EmploymentContract> contracts = Arrays.asList(contract, new EmploymentContract());
        when(employmentContractRepository.findAll()).thenReturn(contracts);

        List<EmploymentContract> result = employmentContractService.findAll();

        assertEquals(2, result.size());
        verify(employmentContractRepository, times(1)).findAll();
    }

    @Test
    void update_ShouldUpdateAndReturnContract() {
        EmploymentContract updatedContract = new EmploymentContract();
        updatedContract.setHourlyRate(25.50);

        when(employmentContractRepository.findById(contractId)).thenReturn(Optional.of(contract));
        when(employmentContractRepository.save(any(EmploymentContract.class))).thenReturn(updatedContract);

        EmploymentContract result = employmentContractService.update(contractId, updatedContract);

        assertNotNull(result);
        assertEquals(25.50, result.getHourlyRate());
        verify(employmentContractRepository, times(1)).save(contract);
    }

    @Test
    void update_WithNullId_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, 
            () -> employmentContractService.update(null, new EmploymentContract()));
    }

    @Test
    void update_WithNullContract_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, 
            () -> employmentContractService.update(contractId, null));
    }

    @Test
    void update_NonExistentId_ShouldThrowRuntimeException() {
        when(employmentContractRepository.findById(contractId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, 
            () -> employmentContractService.update(contractId, new EmploymentContract()));
    }

    @Test
    void delete_ShouldDeleteContract() {
        when(employmentContractRepository.existsById(contractId)).thenReturn(true);

        employmentContractService.delete(contractId);

        verify(employmentContractRepository, times(1)).deleteById(contractId);
    }

    @Test
    void delete_WithNullId_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> employmentContractService.delete(null));
    }

    @Test
    void delete_NonExistentId_ShouldThrowEntityNotFoundException() {
        when(employmentContractRepository.existsById(contractId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, 
            () -> employmentContractService.delete(contractId));
    }

    @Test
    void findByEmployeeId_ShouldReturnContracts() {
        List<EmploymentContract> contracts = Arrays.asList(contract);
        when(employmentContractRepository.findByEmployeeId(employeeId)).thenReturn(contracts);

        List<EmploymentContract> result = employmentContractService.findByEmployeeId(employeeId);

        assertEquals(1, result.size());
        verify(employmentContractRepository, times(1)).findByEmployeeId(employeeId);
    }

    @Test
    void findByCustomerId_ShouldReturnContracts() {
        List<EmploymentContract> contracts = Arrays.asList(contract);
        when(employmentContractRepository.findByCustomerId(customerId)).thenReturn(contracts);

        List<EmploymentContract> result = employmentContractService.findByCustomerId(customerId);

        assertEquals(1, result.size());
        verify(employmentContractRepository, times(1)).findByCustomerId(customerId);
    }
}