package com.iws_manager.iws_manager_api.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iws_manager.iws_manager_api.models.ContractStatus;
import com.iws_manager.iws_manager_api.repositories.ContractStatusRepository;

@ExtendWith(MockitoExtension.class)
class ContractStatusServiceImplTest {

    @Mock
    private ContractStatusRepository contractStatusRepository;

    @InjectMocks
    private ContractStatusServiceImpl contractStatusService;

    private ContractStatus contractStatus1;
    private ContractStatus contractStatus2;
    private ContractStatus contractStatus3;

    @BeforeEach
    void setUp() {
        contractStatus1 = new ContractStatus();
        contractStatus1.setId(1L);
        contractStatus1.setStatus("Active");
        contractStatus1.setChance(new BigDecimal("75.50"));

        contractStatus2 = new ContractStatus();
        contractStatus2.setId(2L);
        contractStatus2.setStatus("Pending");
        contractStatus2.setChance(new BigDecimal("25.00"));

        contractStatus3 = new ContractStatus();
        contractStatus3.setId(3L);
        contractStatus3.setStatus("Closed");
        contractStatus3.setChance(new BigDecimal("100.00"));
    }

    // CREATE TESTS
    @Test
    void create_ShouldSaveAndReturnContractStatus() {
        when(contractStatusRepository.save(any(ContractStatus.class))).thenReturn(contractStatus1);

        ContractStatus result = contractStatusService.create(contractStatus1);

        assertNotNull(result);
        assertEquals(contractStatus1, result);
        verify(contractStatusRepository).save(contractStatus1);
    }

    @Test
    void create_WithNullContractStatus_ShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> contractStatusService.create(null));

        assertEquals("ContractStatus cannot be null", exception.getMessage());
        verify(contractStatusRepository, never()).save(any());
    }

    // FIND BY ID TESTS
    @Test
    void findById_WithExistingId_ShouldReturnContractStatus() {
        when(contractStatusRepository.findById(1L)).thenReturn(Optional.of(contractStatus1));

        Optional<ContractStatus> result = contractStatusService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(contractStatus1, result.get());
        verify(contractStatusRepository).findById(1L);
    }

    @Test
    void findById_WithNonExistingId_ShouldReturnEmptyOptional() {
        when(contractStatusRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<ContractStatus> result = contractStatusService.findById(999L);

        assertFalse(result.isPresent());
        verify(contractStatusRepository).findById(999L);
    }

    @Test
    void findById_WithNullId_ShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> contractStatusService.findById(null));

        assertEquals("ID cannot be null", exception.getMessage());
        verify(contractStatusRepository, never()).findById(any());
    }

    // FIND ALL TESTS
    @Test
    void findAll_ShouldReturnOrderedList() {
        List<ContractStatus> expectedList = Arrays.asList(contractStatus1, contractStatus3, contractStatus2);
        when(contractStatusRepository.findAllByOrderByStatusAsc()).thenReturn(expectedList);

        List<ContractStatus> result = contractStatusService.findAll();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(expectedList, result);
        verify(contractStatusRepository).findAllByOrderByStatusAsc();
    }

    @Test
    void findAll_WithEmptyDatabase_ShouldReturnEmptyList() {
        when(contractStatusRepository.findAllByOrderByStatusAsc()).thenReturn(List.of());

        List<ContractStatus> result = contractStatusService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(contractStatusRepository).findAllByOrderByStatusAsc();
    }

    // UPDATE TESTS
    @Test
    void update_WithExistingId_ShouldUpdateAndReturnContractStatus() {
        ContractStatus updatedDetails = new ContractStatus();
        updatedDetails.setStatus("Updated Status");
        updatedDetails.setChance(new BigDecimal("90.00"));

        when(contractStatusRepository.findById(1L)).thenReturn(Optional.of(contractStatus1));
        when(contractStatusRepository.save(any(ContractStatus.class))).thenReturn(contractStatus1);

        ContractStatus result = contractStatusService.update(1L, updatedDetails);

        assertNotNull(result);
        assertEquals("Updated Status", contractStatus1.getStatus());
        assertEquals(new BigDecimal("90.00"), contractStatus1.getChance());
        verify(contractStatusRepository).findById(1L);
        verify(contractStatusRepository).save(contractStatus1);
    }

    @Test
    void update_WithNonExistingId_ShouldThrowRuntimeException() {
        when(contractStatusRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> contractStatusService.update(999L, contractStatus1));

        assertEquals("ContractStatus not found with id: 999", exception.getMessage());
        verify(contractStatusRepository).findById(999L);
        verify(contractStatusRepository, never()).save(any());
    }

    @Test
    void update_WithNullId_ShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> contractStatusService.update(null, contractStatus1));

        assertEquals("ID and contractStatus details cannot be null", exception.getMessage());
        verify(contractStatusRepository, never()).findById(any());
        verify(contractStatusRepository, never()).save(any());
    }

    @Test
    void update_WithNullContractStatusDetails_ShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> contractStatusService.update(1L, null));

        assertEquals("ID and contractStatus details cannot be null", exception.getMessage());
        verify(contractStatusRepository, never()).findById(any());
        verify(contractStatusRepository, never()).save(any());
    }

    // DELETE TESTS
    @Test
    void delete_WithExistingId_ShouldDeleteContractStatus() {
        doNothing().when(contractStatusRepository).deleteById(1L);

        contractStatusService.delete(1L);

        verify(contractStatusRepository).deleteById(1L);
    }

    @Test
    void delete_WithNullId_ShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> contractStatusService.delete(null));

        assertEquals("ID cannot be null", exception.getMessage());
        verify(contractStatusRepository, never()).deleteById(any());
    }

    // GET BY CHANCE GREATER THAN EQUAL TESTS
    @Test
    void getByChanceGreaterThanEqual_ShouldReturnFilteredList() {
        List<ContractStatus> expectedList = Arrays.asList(contractStatus1, contractStatus3);
        when(contractStatusRepository.findByChanceGreaterThanEqual(new BigDecimal("50.00")))
            .thenReturn(expectedList);

        List<ContractStatus> result = contractStatusService.getByChanceGreaterThanEqual(new BigDecimal("50.00"));

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedList, result);
        verify(contractStatusRepository).findByChanceGreaterThanEqual(new BigDecimal("50.00"));
    }

    @Test
    void getByChanceGreaterThanEqual_WithNullChance_ShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> contractStatusService.getByChanceGreaterThanEqual(null));

        assertEquals("Chance cannot be null", exception.getMessage());
        verify(contractStatusRepository, never()).findByChanceGreaterThanEqual(any());
    }

    // GET BY CHANCE LESS THAN EQUAL TESTS
    @Test
    void getByChanceLessThanEqual_ShouldReturnFilteredList() {
        List<ContractStatus> expectedList = Arrays.asList(contractStatus2);
        when(contractStatusRepository.findByChanceLessThanEqual(new BigDecimal("30.00")))
            .thenReturn(expectedList);

        List<ContractStatus> result = contractStatusService.getByChanceLessThanEqual(new BigDecimal("30.00"));

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedList, result);
        verify(contractStatusRepository).findByChanceLessThanEqual(new BigDecimal("30.00"));
    }

    @Test
    void getByChanceLessThanEqual_WithNullChance_ShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> contractStatusService.getByChanceLessThanEqual(null));

        assertEquals("Chance cannot be null", exception.getMessage());
        verify(contractStatusRepository, never()).findByChanceLessThanEqual(any());
    }

    // GET BY CHANCE BETWEEN TESTS
    @Test
    void getByChanceBetween_ShouldReturnFilteredList() {
        List<ContractStatus> expectedList = Arrays.asList(contractStatus1);
        when(contractStatusRepository.findByChanceBetween(new BigDecimal("50.00"), new BigDecimal("80.00")))
            .thenReturn(expectedList);

        List<ContractStatus> result = contractStatusService.getByChanceBetween(
            new BigDecimal("50.00"), new BigDecimal("80.00"));

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedList, result);
        verify(contractStatusRepository).findByChanceBetween(new BigDecimal("50.00"), new BigDecimal("80.00"));
    }

    @Test
    void getByChanceBetween_WithNullMinChance_ShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> contractStatusService.getByChanceBetween(null, new BigDecimal("80.00")));

        assertEquals("Min and max chance cannot be null", exception.getMessage());
        verify(contractStatusRepository, never()).findByChanceBetween(any(), any());
    }

    @Test
    void getByChanceBetween_WithNullMaxChance_ShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> contractStatusService.getByChanceBetween(new BigDecimal("50.00"), null));

        assertEquals("Min and max chance cannot be null", exception.getMessage());
        verify(contractStatusRepository, never()).findByChanceBetween(any(), any());
    }

    @Test
    void getByChanceBetween_WithMinGreaterThanMax_ShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> contractStatusService.getByChanceBetween(new BigDecimal("80.00"), new BigDecimal("50.00")));

        assertEquals("Min chance cannot be greater than max chance", exception.getMessage());
        verify(contractStatusRepository, never()).findByChanceBetween(any(), any());
    }

    @Test
    void getByChanceBetween_WithEqualMinAndMax_ShouldReturnResults() {
        List<ContractStatus> expectedList = Arrays.asList(contractStatus1);
        when(contractStatusRepository.findByChanceBetween(new BigDecimal("75.50"), new BigDecimal("75.50")))
            .thenReturn(expectedList);

        List<ContractStatus> result = contractStatusService.getByChanceBetween(
            new BigDecimal("75.50"), new BigDecimal("75.50"));

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(contractStatusRepository).findByChanceBetween(new BigDecimal("75.50"), new BigDecimal("75.50"));
    }

    @Test
    void getByChanceBetween_WithNoResults_ShouldReturnEmptyList() {
        when(contractStatusRepository.findByChanceBetween(new BigDecimal("10.00"), new BigDecimal("20.00")))
            .thenReturn(List.of());

        List<ContractStatus> result = contractStatusService.getByChanceBetween(
            new BigDecimal("10.00"), new BigDecimal("20.00"));

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(contractStatusRepository).findByChanceBetween(new BigDecimal("10.00"), new BigDecimal("20.00"));
    }
}