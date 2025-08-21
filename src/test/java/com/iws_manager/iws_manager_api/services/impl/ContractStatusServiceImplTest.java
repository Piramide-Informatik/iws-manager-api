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

    private static final String CONTRACT_STATUS_CANNOT_BE_NULL = "ContractStatus cannot be null";
    private static final String ID_CANNOT_BE_NULL = "ID cannot be null";
    private static final String ID_AND_DETAILS_CANNOT_BE_NULL = "ID and contractStatus details cannot be null";
    private static final String CHANCE_CANNOT_BE_NULL = "Chance cannot be null";
    private static final String MIN_MAX_CHANCE_CANNOT_BE_NULL = "Min and max chance cannot be null";
    private static final String MIN_CHANCE_GREATER_THAN_MAX = "Min chance cannot be greater than max chance";
    private static final String CONTRACT_STATUS_NOT_FOUND = "ContractStatus not found with id: ";

    private static final Long EXISTING_ID = 1L;
    private static final Long NON_EXISTING_ID = 999L;
    private static final Long NULL_ID = null;

    private static final BigDecimal CHANCE_25 = new BigDecimal("25.00");
    private static final BigDecimal CHANCE_50 = new BigDecimal("50.00");
    private static final BigDecimal CHANCE_75_50 = new BigDecimal("75.50");
    private static final BigDecimal CHANCE_80 = new BigDecimal("80.00");
    private static final BigDecimal CHANCE_100 = new BigDecimal("100.00");
    private static final BigDecimal CHANCE_30 = new BigDecimal("30.00");
    private static final BigDecimal CHANCE_10 = new BigDecimal("10.00");
    private static final BigDecimal CHANCE_20 = new BigDecimal("20.00");
    private static final BigDecimal NULL_CHANCE = null;

    private static final String STATUS_ACTIVE = "Active";
    private static final String STATUS_PENDING = "Pending";
    private static final String STATUS_CLOSED = "Closed";
    private static final String STATUS_UPDATED = "Updated Status";

    @Mock
    private ContractStatusRepository contractStatusRepository;

    @InjectMocks
    private ContractStatusServiceImpl contractStatusService;

    private ContractStatus contractStatus1;
    private ContractStatus contractStatus2;
    private ContractStatus contractStatus3;
    private ContractStatus updatedContractStatus;

    @BeforeEach
    void setUp() {
        contractStatus1 = new ContractStatus();
        contractStatus1.setId(EXISTING_ID);
        contractStatus1.setStatus(STATUS_ACTIVE);
        contractStatus1.setChance(CHANCE_75_50);

        contractStatus2 = new ContractStatus();
        contractStatus2.setId(2L);
        contractStatus2.setStatus(STATUS_PENDING);
        contractStatus2.setChance(CHANCE_25);

        contractStatus3 = new ContractStatus();
        contractStatus3.setId(3L);
        contractStatus3.setStatus(STATUS_CLOSED);
        contractStatus3.setChance(CHANCE_100);

        updatedContractStatus = new ContractStatus();
        updatedContractStatus.setStatus(STATUS_UPDATED);
        updatedContractStatus.setChance(CHANCE_80);
    }

    // CREATE TESTS
    @Test
    void createShouldSaveAndReturnContractStatus() {
        when(contractStatusRepository.save(any(ContractStatus.class))).thenReturn(contractStatus1);

        ContractStatus result = contractStatusService.create(contractStatus1);

        assertNotNull(result);
        assertEquals(contractStatus1, result);
        verify(contractStatusRepository).save(contractStatus1);
    }

    @Test
    void createWithNullContractStatusShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> contractStatusService.create(null));

        assertEquals(CONTRACT_STATUS_CANNOT_BE_NULL, exception.getMessage());
        verify(contractStatusRepository, never()).save(any());
    }

    // FIND BY ID TESTS
    @Test
    void findByIdWithExistingIdShouldReturnContractStatus() {
        when(contractStatusRepository.findById(EXISTING_ID)).thenReturn(Optional.of(contractStatus1));

        Optional<ContractStatus> result = contractStatusService.findById(EXISTING_ID);

        assertTrue(result.isPresent());
        assertEquals(contractStatus1, result.get());
        verify(contractStatusRepository).findById(EXISTING_ID);
    }

    @Test
    void findByIdWithNonExistingIdShouldReturnEmptyOptional() {
        when(contractStatusRepository.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

        Optional<ContractStatus> result = contractStatusService.findById(NON_EXISTING_ID);

        assertFalse(result.isPresent());
        verify(contractStatusRepository).findById(NON_EXISTING_ID);
    }

    @Test
    void findByIdWithNullIdShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> contractStatusService.findById(NULL_ID));

        assertEquals(ID_CANNOT_BE_NULL, exception.getMessage());
        verify(contractStatusRepository, never()).findById(any());
    }

    // FIND ALL TESTS
    @Test
    void findAllShouldReturnOrderedList() {
        List<ContractStatus> expectedList = Arrays.asList(contractStatus1, contractStatus3, contractStatus2);
        when(contractStatusRepository.findAllByOrderByStatusAsc()).thenReturn(expectedList);

        List<ContractStatus> result = contractStatusService.findAll();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(expectedList, result);
        verify(contractStatusRepository).findAllByOrderByStatusAsc();
    }

    @Test
    void findAllWithEmptyDatabaseShouldReturnEmptyList() {
        when(contractStatusRepository.findAllByOrderByStatusAsc()).thenReturn(List.of());

        List<ContractStatus> result = contractStatusService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(contractStatusRepository).findAllByOrderByStatusAsc();
    }

    // UPDATE TESTS
    @Test
    void updateWithExistingIdShouldUpdateAndReturnContractStatus() {
        when(contractStatusRepository.findById(EXISTING_ID)).thenReturn(Optional.of(contractStatus1));
        when(contractStatusRepository.save(any(ContractStatus.class))).thenReturn(contractStatus1);

        ContractStatus result = contractStatusService.update(EXISTING_ID, updatedContractStatus);

        assertNotNull(result);
        assertEquals(STATUS_UPDATED, contractStatus1.getStatus());
        assertEquals(CHANCE_80, contractStatus1.getChance());
        verify(contractStatusRepository).findById(EXISTING_ID);
        verify(contractStatusRepository).save(contractStatus1);
    }

    @Test
    void updateWithNonExistingIdShouldThrowRuntimeException() {
        when(contractStatusRepository.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> contractStatusService.update(NON_EXISTING_ID, contractStatus1));

        assertEquals(CONTRACT_STATUS_NOT_FOUND + NON_EXISTING_ID, exception.getMessage());
        verify(contractStatusRepository).findById(NON_EXISTING_ID);
        verify(contractStatusRepository, never()).save(any());
    }

    @Test
    void updateWithNullIdShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> contractStatusService.update(NULL_ID, contractStatus1));

        assertEquals(ID_AND_DETAILS_CANNOT_BE_NULL, exception.getMessage());
        verify(contractStatusRepository, never()).findById(any());
        verify(contractStatusRepository, never()).save(any());
    }

    @Test
    void updateWithNullContractStatusDetailsShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> contractStatusService.update(EXISTING_ID, null));

        assertEquals(ID_AND_DETAILS_CANNOT_BE_NULL, exception.getMessage());
        verify(contractStatusRepository, never()).findById(any());
        verify(contractStatusRepository, never()).save(any());
    }

    // DELETE TESTS
    @Test
    void deleteWithExistingIdShouldDeleteContractStatus() {
        doNothing().when(contractStatusRepository).deleteById(EXISTING_ID);

        contractStatusService.delete(EXISTING_ID);

        verify(contractStatusRepository).deleteById(EXISTING_ID);
    }

    @Test
    void deleteWithNullIdShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> contractStatusService.delete(NULL_ID));

        assertEquals(ID_CANNOT_BE_NULL, exception.getMessage());
        verify(contractStatusRepository, never()).deleteById(any());
    }

    // GET BY CHANCE GREATER THAN EQUAL TESTS
    @Test
    void getByChanceGreaterThanEqualShouldReturnFilteredList() {
        List<ContractStatus> expectedList = Arrays.asList(contractStatus1, contractStatus3);
        when(contractStatusRepository.findByChanceGreaterThanEqual(CHANCE_50))
            .thenReturn(expectedList);

        List<ContractStatus> result = contractStatusService.getByChanceGreaterThanEqual(CHANCE_50);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedList, result);
        verify(contractStatusRepository).findByChanceGreaterThanEqual(CHANCE_50);
    }

    @Test
    void getByChanceGreaterThanEqualWithNullChanceShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> contractStatusService.getByChanceGreaterThanEqual(NULL_CHANCE));

        assertEquals(CHANCE_CANNOT_BE_NULL, exception.getMessage());
        verify(contractStatusRepository, never()).findByChanceGreaterThanEqual(any());
    }

    // GET BY CHANCE LESS THAN EQUAL TESTS
    @Test
    void getByChanceLessThanEqualShouldReturnFilteredList() {
        List<ContractStatus> expectedList = Arrays.asList(contractStatus2);
        when(contractStatusRepository.findByChanceLessThanEqual(CHANCE_30))
            .thenReturn(expectedList);

        List<ContractStatus> result = contractStatusService.getByChanceLessThanEqual(CHANCE_30);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedList, result);
        verify(contractStatusRepository).findByChanceLessThanEqual(CHANCE_30);
    }

    @Test
    void getByChanceLessThanEqualWithNullChanceShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> contractStatusService.getByChanceLessThanEqual(NULL_CHANCE));

        assertEquals(CHANCE_CANNOT_BE_NULL, exception.getMessage());
        verify(contractStatusRepository, never()).findByChanceLessThanEqual(any());
    }

    // GET BY CHANCE BETWEEN TESTS
    @Test
    void getByChanceBetweenShouldReturnFilteredList() {
        List<ContractStatus> expectedList = Arrays.asList(contractStatus1);
        when(contractStatusRepository.findByChanceBetween(CHANCE_50, CHANCE_80))
            .thenReturn(expectedList);

        List<ContractStatus> result = contractStatusService.getByChanceBetween(CHANCE_50, CHANCE_80);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedList, result);
        verify(contractStatusRepository).findByChanceBetween(CHANCE_50, CHANCE_80);
    }

    @Test
    void getByChanceBetweenWithNullMinChanceShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> contractStatusService.getByChanceBetween(NULL_CHANCE, CHANCE_80));

        assertEquals(MIN_MAX_CHANCE_CANNOT_BE_NULL, exception.getMessage());
        verify(contractStatusRepository, never()).findByChanceBetween(any(), any());
    }

    @Test
    void getByChanceBetweenWithNullMaxChanceShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> contractStatusService.getByChanceBetween(CHANCE_50, NULL_CHANCE));

        assertEquals(MIN_MAX_CHANCE_CANNOT_BE_NULL, exception.getMessage());
        verify(contractStatusRepository, never()).findByChanceBetween(any(), any());
    }

    @Test
    void getByChanceBetweenWithMinGreaterThanMaxShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> contractStatusService.getByChanceBetween(CHANCE_80, CHANCE_50));

        assertEquals(MIN_CHANCE_GREATER_THAN_MAX, exception.getMessage());
        verify(contractStatusRepository, never()).findByChanceBetween(any(), any());
    }

    @Test
    void getByChanceBetweenWithEqualMinAndMaxShouldReturnResults() {
        List<ContractStatus> expectedList = Arrays.asList(contractStatus1);
        when(contractStatusRepository.findByChanceBetween(CHANCE_75_50, CHANCE_75_50))
            .thenReturn(expectedList);

        List<ContractStatus> result = contractStatusService.getByChanceBetween(CHANCE_75_50, CHANCE_75_50);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(contractStatusRepository).findByChanceBetween(CHANCE_75_50, CHANCE_75_50);
    }

    @Test
    void getByChanceBetweenWithNoResultsShouldReturnEmptyList() {
        when(contractStatusRepository.findByChanceBetween(CHANCE_10, CHANCE_20))
            .thenReturn(List.of());

        List<ContractStatus> result = contractStatusService.getByChanceBetween(CHANCE_10, CHANCE_20);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(contractStatusRepository).findByChanceBetween(CHANCE_10, CHANCE_20);
    }
}