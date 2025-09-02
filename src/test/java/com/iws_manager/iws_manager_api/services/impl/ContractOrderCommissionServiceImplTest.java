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

import com.iws_manager.iws_manager_api.models.ContractOrderCommission;
import com.iws_manager.iws_manager_api.models.BasicContract;
import com.iws_manager.iws_manager_api.repositories.ContractOrderCommissionRepository;

@ExtendWith(MockitoExtension.class)
class ContractOrderCommissionServiceImplTest {

    @Mock
    private ContractOrderCommissionRepository contractOrderCommissionRepository;

    @InjectMocks
    private ContractOrderCommissionServiceImpl contractOrderCommissionService;

    // Constantes para valores BigDecimal que se repiten
    private static final BigDecimal COMMISSION_10_50 = new BigDecimal("10.50");
    private static final BigDecimal COMMISSION_15_75 = new BigDecimal("15.75");
    private static final BigDecimal FROM_ORDER_VALUE_1000_00 = new BigDecimal("1000.00");
    private static final BigDecimal FROM_ORDER_VALUE_2000_00 = new BigDecimal("2000.00");
    private static final BigDecimal MIN_COMMISSION_50_00 = new BigDecimal("50.00");
    private static final BigDecimal MIN_COMMISSION_75_00 = new BigDecimal("75.00");
    
    private static final BigDecimal VALUE_10_00 = new BigDecimal("10.00");
    private static final BigDecimal VALUE_20_00 = new BigDecimal("20.00");
    private static final BigDecimal VALUE_5_00 = new BigDecimal("5.00");
    private static final BigDecimal VALUE_15_00 = new BigDecimal("15.00");
    private static final BigDecimal VALUE_500_00 = new BigDecimal("500.00");
    private static final BigDecimal VALUE_1500_00 = new BigDecimal("1500.00");
    private static final BigDecimal VALUE_25_00 = new BigDecimal("25.00");
    private static final BigDecimal VALUE_100_00 = new BigDecimal("100.00");
    private static final BigDecimal VALUE_75_00 = new BigDecimal("75.00");

    // Constantes para IDs
    private static final Long CONTRACT_ID_1 = 1L;
    private static final Long CONTRACT_ID_999 = 999L;

    private ContractOrderCommission commission;
    private BasicContract basicContract = new BasicContract();

    @BeforeEach
    void setUp() {
        basicContract.setId(CONTRACT_ID_1);

        commission = new ContractOrderCommission();
        commission.setId(CONTRACT_ID_1);
        commission.setCommission(COMMISSION_10_50);
        commission.setBasicContract(basicContract);
        commission.setFromOrderValue(FROM_ORDER_VALUE_1000_00);
        commission.setMinCommission(MIN_COMMISSION_50_00);
    }

    @Test
    void testCreate() {
        when(contractOrderCommissionRepository.save(any(ContractOrderCommission.class))).thenReturn(commission);

        ContractOrderCommission result = contractOrderCommissionService.create(commission);

        assertNotNull(result);
        assertEquals(commission.getId(), result.getId());
        verify(contractOrderCommissionRepository, times(1)).save(commission);
    }

    @Test
    void testCreateNullCommissionThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> 
            contractOrderCommissionService.create(null));
    }

    @Test
    void testFindById() {
        when(contractOrderCommissionRepository.findById(CONTRACT_ID_1)).thenReturn(Optional.of(commission));

        Optional<ContractOrderCommission> result = contractOrderCommissionService.findById(CONTRACT_ID_1);

        assertTrue(result.isPresent());
        assertEquals(commission.getId(), result.get().getId());
    }

    @Test
    void testFindByIdNullIdThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> 
            contractOrderCommissionService.findById(null));
    }

    @Test
    void testFindByIdNotFound() {
        when(contractOrderCommissionRepository.findById(CONTRACT_ID_999)).thenReturn(Optional.empty());

        Optional<ContractOrderCommission> result = contractOrderCommissionService.findById(CONTRACT_ID_999);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindAll() {
        List<ContractOrderCommission> commissions = Arrays.asList(commission);
        when(contractOrderCommissionRepository.findAll()).thenReturn(commissions);

        List<ContractOrderCommission> result = contractOrderCommissionService.findAll();

        assertEquals(1, result.size());
        assertEquals(commission.getId(), result.get(0).getId());
    }

    @Test
    void testUpdate() {
        ContractOrderCommission updatedCommission = new ContractOrderCommission();
        updatedCommission.setCommission(COMMISSION_15_75);
        updatedCommission.setFromOrderValue(FROM_ORDER_VALUE_2000_00);
        updatedCommission.setMinCommission(MIN_COMMISSION_75_00);

        when(contractOrderCommissionRepository.findById(CONTRACT_ID_1)).thenReturn(Optional.of(commission));
        when(contractOrderCommissionRepository.save(any(ContractOrderCommission.class))).thenReturn(commission);

        ContractOrderCommission result = contractOrderCommissionService.update(CONTRACT_ID_1, updatedCommission);

        assertNotNull(result);
        assertEquals(COMMISSION_15_75, result.getCommission());
        verify(contractOrderCommissionRepository, times(1)).save(commission);
    }

    @Test
    void testUpdateNullIdThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> 
            contractOrderCommissionService.update(null, commission));
    }

    @Test
    void testUpdateNullDetailsThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> 
            contractOrderCommissionService.update(CONTRACT_ID_1, null));
    }

    @Test
    void testUpdateNotFound() {
        when(contractOrderCommissionRepository.findById(CONTRACT_ID_999)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> 
            contractOrderCommissionService.update(CONTRACT_ID_999, commission));
    }

    @Test
    void testDelete() {
        doNothing().when(contractOrderCommissionRepository).deleteById(CONTRACT_ID_1);

        contractOrderCommissionService.delete(CONTRACT_ID_1);

        verify(contractOrderCommissionRepository, times(1)).deleteById(CONTRACT_ID_1);
    }

    @Test
    void testDeleteNullIdThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> 
            contractOrderCommissionService.delete(null));
    }

    // Property-based tests
    @Test
    void testGetByCommission() {
        List<ContractOrderCommission> commissions = Arrays.asList(commission);
        when(contractOrderCommissionRepository.findByCommission(COMMISSION_10_50)).thenReturn(commissions);

        List<ContractOrderCommission> result = contractOrderCommissionService.getByCommission(COMMISSION_10_50);

        assertEquals(1, result.size());
        assertEquals(COMMISSION_10_50, result.get(0).getCommission());
    }

    @Test
    void testGetByCommissionNullValueThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> 
            contractOrderCommissionService.getByCommission(null));
    }

    @Test
    void testGetByFromOrderValue() {
        List<ContractOrderCommission> commissions = Arrays.asList(commission);
        when(contractOrderCommissionRepository.findByFromOrderValue(FROM_ORDER_VALUE_1000_00)).thenReturn(commissions);

        List<ContractOrderCommission> result = contractOrderCommissionService.getByFromOrderValue(FROM_ORDER_VALUE_1000_00);

        assertEquals(1, result.size());
        assertEquals(FROM_ORDER_VALUE_1000_00, result.get(0).getFromOrderValue());
    }

    @Test
    void testGetByFromOrderValueNullValueThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> 
            contractOrderCommissionService.getByFromOrderValue(null));
    }

    @Test
    void testGetByMinCommission() {
        List<ContractOrderCommission> commissions = Arrays.asList(commission);
        when(contractOrderCommissionRepository.findByMinCommission(MIN_COMMISSION_50_00)).thenReturn(commissions);

        List<ContractOrderCommission> result = contractOrderCommissionService.getByMinCommission(MIN_COMMISSION_50_00);

        assertEquals(1, result.size());
        assertEquals(MIN_COMMISSION_50_00, result.get(0).getMinCommission());
    }

    @Test
    void testGetByMinCommissionNullValueThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> 
            contractOrderCommissionService.getByMinCommission(null));
    }

    @Test
    void testGetByBasicContractId() {
        List<ContractOrderCommission> commissions = Arrays.asList(commission);
        when(contractOrderCommissionRepository.findByBasicContractId(CONTRACT_ID_1)).thenReturn(commissions);

        List<ContractOrderCommission> result = contractOrderCommissionService.getByBasicContractId(CONTRACT_ID_1);

        assertEquals(1, result.size());
        assertEquals(CONTRACT_ID_1, result.get(0).getBasicContract().getId());
    }

    @Test
    void testGetByBasicContractIdNullValueThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> 
            contractOrderCommissionService.getByBasicContractId(null));
    }

    // Greater than or equal tests
    @Test
    void testGetByCommissionGreaterThanEqual() {
        List<ContractOrderCommission> commissions = Arrays.asList(commission);
        when(contractOrderCommissionRepository.findByCommissionGreaterThanEqual(VALUE_10_00)).thenReturn(commissions);

        List<ContractOrderCommission> result = contractOrderCommissionService.getByCommissionGreaterThanEqual(VALUE_10_00);

        assertEquals(1, result.size());
    }

    @Test
    void testGetByCommissionLessThanEqual() {
        List<ContractOrderCommission> commissions = Arrays.asList(commission);
        when(contractOrderCommissionRepository.findByCommissionLessThanEqual(VALUE_20_00)).thenReturn(commissions);

        List<ContractOrderCommission> result = contractOrderCommissionService.getByCommissionLessThanEqual(VALUE_20_00);

        assertEquals(1, result.size());
    }

    @Test
    void testGetByFromOrderValueGreaterThanEqual() {
        List<ContractOrderCommission> commissions = Arrays.asList(commission);
        when(contractOrderCommissionRepository.findByFromOrderValueGreaterThanEqual(VALUE_500_00)).thenReturn(commissions);

        List<ContractOrderCommission> result = contractOrderCommissionService.getByFromOrderValueGreaterThanEqual(VALUE_500_00);

        assertEquals(1, result.size());
    }

    @Test
    void testGetByFromOrderValueLessThanEqual() {
        List<ContractOrderCommission> commissions = Arrays.asList(commission);
        when(contractOrderCommissionRepository.findByFromOrderValueLessThanEqual(VALUE_1500_00)).thenReturn(commissions);

        List<ContractOrderCommission> result = contractOrderCommissionService.getByFromOrderValueLessThanEqual(VALUE_1500_00);

        assertEquals(1, result.size());
    }

    @Test
    void testGetByMinCommissionGreaterThanEqual() {
        List<ContractOrderCommission> commissions = Arrays.asList(commission);
        when(contractOrderCommissionRepository.findByMinCommissionGreaterThanEqual(VALUE_25_00)).thenReturn(commissions);

        List<ContractOrderCommission> result = contractOrderCommissionService.getByMinCommissionGreaterThanEqual(VALUE_25_00);

        assertEquals(1, result.size());
    }

    @Test
    void testGetByMinCommissionLessThanEqual() {
        List<ContractOrderCommission> commissions = Arrays.asList(commission);
        when(contractOrderCommissionRepository.findByMinCommissionLessThanEqual(VALUE_100_00)).thenReturn(commissions);

        List<ContractOrderCommission> result = contractOrderCommissionService.getByMinCommissionLessThanEqual(VALUE_100_00);

        assertEquals(1, result.size());
    }

    // BasicContract with conditions tests
    @Test
    void testGetByBasicContractIdAndCommissionGreaterThanEqual() {
        List<ContractOrderCommission> commissions = Arrays.asList(commission);
        when(contractOrderCommissionRepository.findByBasicContractIdAndCommissionGreaterThanEqual(CONTRACT_ID_1, VALUE_10_00))
            .thenReturn(commissions);

        List<ContractOrderCommission> result = contractOrderCommissionService
            .getByBasicContractIdAndCommissionGreaterThanEqual(CONTRACT_ID_1, VALUE_10_00);

        assertEquals(1, result.size());
    }

    @Test
    void testGetByBasicContractIdAndCommissionGreaterThanEqualNullValuesThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> 
            contractOrderCommissionService.getByBasicContractIdAndCommissionGreaterThanEqual(null, VALUE_10_00));

        assertThrows(IllegalArgumentException.class, () -> 
            contractOrderCommissionService.getByBasicContractIdAndCommissionGreaterThanEqual(CONTRACT_ID_1, null));
    }

    @Test
    void testGetByBasicContractIdAndCommissionLessThanEqual() {
        List<ContractOrderCommission> commissions = Arrays.asList(commission);
        when(contractOrderCommissionRepository.findByBasicContractIdAndCommissionLessThanEqual(CONTRACT_ID_1, VALUE_20_00))
            .thenReturn(commissions);

        List<ContractOrderCommission> result = contractOrderCommissionService
            .getByBasicContractIdAndCommissionLessThanEqual(CONTRACT_ID_1, VALUE_20_00);

        assertEquals(1, result.size());
    }

    @Test
    void testGetByBasicContractIdAndFromOrderValueGreaterThanEqual() {
        List<ContractOrderCommission> commissions = Arrays.asList(commission);
        when(contractOrderCommissionRepository.findByBasicContractIdAndFromOrderValueGreaterThanEqual(CONTRACT_ID_1, VALUE_500_00))
            .thenReturn(commissions);

        List<ContractOrderCommission> result = contractOrderCommissionService
            .getByBasicContractIdAndFromOrderValueGreaterThanEqual(CONTRACT_ID_1, VALUE_500_00);

        assertEquals(1, result.size());
    }

    @Test
    void testGetByBasicContractIdAndFromOrderValueLessThanEqual() {
        List<ContractOrderCommission> commissions = Arrays.asList(commission);
        when(contractOrderCommissionRepository.findByBasicContractIdAndFromOrderValueLessThanEqual(CONTRACT_ID_1, VALUE_1500_00))
            .thenReturn(commissions);

        List<ContractOrderCommission> result = contractOrderCommissionService
            .getByBasicContractIdAndFromOrderValueLessThanEqual(CONTRACT_ID_1, VALUE_1500_00);

        assertEquals(1, result.size());
    }

    @Test
    void testGetByBasicContractIdAndMinCommissionGreaterThanEqual() {
        List<ContractOrderCommission> commissions = Arrays.asList(commission);
        when(contractOrderCommissionRepository.findByBasicContractIdAndMinCommissionGreaterThanEqual(CONTRACT_ID_1, VALUE_25_00))
            .thenReturn(commissions);

        List<ContractOrderCommission> result = contractOrderCommissionService
            .getByBasicContractIdAndMinCommissionGreaterThanEqual(CONTRACT_ID_1, VALUE_25_00);

        assertEquals(1, result.size());
    }

    @Test
    void testGetByBasicContractIdAndMinCommissionLessThanEqual() {
        List<ContractOrderCommission> commissions = Arrays.asList(commission);
        when(contractOrderCommissionRepository.findByBasicContractIdAndMinCommissionLessThanEqual(CONTRACT_ID_1, VALUE_100_00))
            .thenReturn(commissions);

        List<ContractOrderCommission> result = contractOrderCommissionService
            .getByBasicContractIdAndMinCommissionLessThanEqual(CONTRACT_ID_1, VALUE_100_00);

        assertEquals(1, result.size());
    }

    // Range tests
    @Test
    void testGetByCommissionBetween() {
        List<ContractOrderCommission> commissions = Arrays.asList(commission);
        when(contractOrderCommissionRepository.findByCommissionBetween(VALUE_5_00, VALUE_15_00))
            .thenReturn(commissions);

        List<ContractOrderCommission> result = contractOrderCommissionService
            .getByCommissionBetween(VALUE_5_00, VALUE_15_00);

        assertEquals(1, result.size());
    }

    @Test
    void testGetByCommissionBetweenInvalidRangeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> 
            contractOrderCommissionService.getByCommissionBetween(VALUE_15_00, VALUE_5_00));
    }

    @Test
    void testGetByFromOrderValueBetween() {
        List<ContractOrderCommission> commissions = Arrays.asList(commission);
        when(contractOrderCommissionRepository.findByFromOrderValueBetween(VALUE_500_00, VALUE_1500_00))
            .thenReturn(commissions);

        List<ContractOrderCommission> result = contractOrderCommissionService
            .getByFromOrderValueBetween(VALUE_500_00, VALUE_1500_00);

        assertEquals(1, result.size());
    }

    @Test
    void testGetByMinCommissionBetween() {
        List<ContractOrderCommission> commissions = Arrays.asList(commission);
        when(contractOrderCommissionRepository.findByMinCommissionBetween(VALUE_25_00, VALUE_75_00))
            .thenReturn(commissions);

        List<ContractOrderCommission> result = contractOrderCommissionService
            .getByMinCommissionBetween(VALUE_25_00, VALUE_75_00);

        assertEquals(1, result.size());
    }

    // BasicContract with range tests
    @Test
    void testGetByBasicContractIdAndCommissionBetween() {
        List<ContractOrderCommission> commissions = Arrays.asList(commission);
        when(contractOrderCommissionRepository.findByBasicContractIdAndCommissionBetween(CONTRACT_ID_1, VALUE_5_00, VALUE_15_00))
            .thenReturn(commissions);

        List<ContractOrderCommission> result = contractOrderCommissionService
            .getByBasicContractIdAndCommissionBetween(CONTRACT_ID_1, VALUE_5_00, VALUE_15_00);

        assertEquals(1, result.size());
    }

    @Test
    void testGetByBasicContractIdAndFromOrderValueBetween() {
        List<ContractOrderCommission> commissions = Arrays.asList(commission);
        when(contractOrderCommissionRepository.findByBasicContractIdAndFromOrderValueBetween(CONTRACT_ID_1, VALUE_500_00, VALUE_1500_00))
            .thenReturn(commissions);

        List<ContractOrderCommission> result = contractOrderCommissionService
            .getByBasicContractIdAndFromOrderValueBetween(CONTRACT_ID_1, VALUE_500_00, VALUE_1500_00);

        assertEquals(1, result.size());
    }

    @Test
    void testGetByBasicContractIdAndMinCommissionBetween() {
        List<ContractOrderCommission> commissions = Arrays.asList(commission);
        when(contractOrderCommissionRepository.findByBasicContractIdAndMinCommissionBetween(CONTRACT_ID_1, VALUE_25_00, VALUE_75_00))
            .thenReturn(commissions);

        List<ContractOrderCommission> result = contractOrderCommissionService
            .getByBasicContractIdAndMinCommissionBetween(CONTRACT_ID_1, VALUE_25_00, VALUE_75_00);

        assertEquals(1, result.size());
    }

    @Test
    void testGetByBasicContractIdAndCommissionBetweenNullValuesThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> 
            contractOrderCommissionService.getByBasicContractIdAndCommissionBetween(null, VALUE_5_00, VALUE_15_00));
    }

    @Test
    void testGetByBasicContractIdAndCommissionBetweenInvalidRangeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> 
            contractOrderCommissionService.getByBasicContractIdAndCommissionBetween(CONTRACT_ID_1, VALUE_15_00, VALUE_5_00));
    }
}