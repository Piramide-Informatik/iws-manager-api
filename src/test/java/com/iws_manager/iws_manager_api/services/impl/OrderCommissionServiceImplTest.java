package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.OrderCommission;
import com.iws_manager.iws_manager_api.repositories.OrderCommissionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderCommissionServiceImplTest {

    private static final String COMISSION_10 = "10.00";
    private static final String COMISSION_15 = "15.00";
    private static final String COMISSION_20 = "20.00";
    private static final String ORDERVALUE_1000 = "1000.00";
    private static final String ORDERVALUE_1500 = "1500.00";
    private static final String ORDERVALUE_2000 = "2000.00";
    private static final String MINCOMM_25 = "25.00";
    private static final String MINCOMM_30 = "30.00";

    @Mock
    private OrderCommissionRepository orderCommissionRepository;

    @InjectMocks
    private OrderCommissionServiceImpl orderCommissionService;

    // CRUD Tests
    @Test
    void createWithValidOrderCommissionShouldSaveAndReturn() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setCommission(new BigDecimal(COMISSION_10));
        
        when(orderCommissionRepository.save(any(OrderCommission.class))).thenReturn(commission);

        // Act
        OrderCommission result = orderCommissionService.create(commission);

        // Assert
        assertNotNull(result);
        assertEquals(new BigDecimal(COMISSION_10), result.getCommission());
        verify(orderCommissionRepository).save(commission);
    }

    @Test
    void createWithNullOrderCommissionShouldThrowIllegalArgumentException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            orderCommissionService.create(null));
        assertEquals("OrderCommission cannot be null", exception.getMessage());
        verify(orderCommissionRepository, never()).save(any());
    }

    @Test
    void findByIdWithValidIdShouldReturnOrderCommission() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setId(1L);
        
        when(orderCommissionRepository.findById(1L)).thenReturn(Optional.of(commission));

        // Act
        Optional<OrderCommission> result = orderCommissionService.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(orderCommissionRepository).findById(1L);
    }

    @Test
    void findByIdWithNonExistingIdShouldReturnEmptyOptional() {
        // Arrange
        when(orderCommissionRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<OrderCommission> result = orderCommissionService.findById(999L);

        // Assert
        assertFalse(result.isPresent());
        verify(orderCommissionRepository).findById(999L);
    }

    @Test
    void findByIdWithNullIdShouldThrowIllegalArgumentException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> orderCommissionService.findById(null));
        assertEquals("ID cannot be null", exception.getMessage());
        verify(orderCommissionRepository, never()).findById(any());
    }

    @Test
    void findAllShouldReturnAllOrderCommissions() {
        // Arrange
        OrderCommission comm1 = new OrderCommission();
        OrderCommission comm2 = new OrderCommission();
        List<OrderCommission> expected = Arrays.asList(comm1, comm2);
        
        when(orderCommissionRepository.findAll()).thenReturn(expected);

        // Act
        List<OrderCommission> result = orderCommissionService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(orderCommissionRepository).findAll();
    }

    @Test
    void findAllWhenNoOrderCommissionsShouldReturnEmptyList() {
        // Arrange
        when(orderCommissionRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<OrderCommission> result = orderCommissionService.findAll();

        // Assert
        assertTrue(result.isEmpty());
        verify(orderCommissionRepository).findAll();
    }

    @Test
    void updateWithValidDataShouldUpdateAndReturn() {
        // Arrange
        OrderCommission existing = new OrderCommission();
        existing.setId(1L);
        existing.setCommission(new BigDecimal("5.00"));
        existing.setFromOrderValue(new BigDecimal("100.00"));
        existing.setMinCommission(new BigDecimal(COMISSION_10));
        
        OrderCommission details = new OrderCommission();
        details.setCommission(new BigDecimal(COMISSION_15));
        details.setFromOrderValue(new BigDecimal("200.00"));
        details.setMinCommission(new BigDecimal(COMISSION_20));
        
        when(orderCommissionRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(orderCommissionRepository.save(any(OrderCommission.class))).thenReturn(existing);

        // Act
        OrderCommission result = orderCommissionService.update(1L, details);

        // Assert
        assertNotNull(result);
        assertEquals(new BigDecimal(COMISSION_15), result.getCommission());
        assertEquals(new BigDecimal("200.00"), result.getFromOrderValue());
        assertEquals(new BigDecimal(COMISSION_20), result.getMinCommission());
        verify(orderCommissionRepository).findById(1L);
        verify(orderCommissionRepository).save(existing);
    }

    @Test
    void updateWithNullIdShouldThrowIllegalArgumentException() {
        // Arrange
        OrderCommission details = new OrderCommission();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> orderCommissionService.update(null, details));
        assertEquals("ID and orderCommission details cannot be null", exception.getMessage());
        verify(orderCommissionRepository, never()).findById(any());
        verify(orderCommissionRepository, never()).save(any());
    }

    @Test
    void updateWithNullDetailsShouldThrowIllegalArgumentException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> orderCommissionService.update(1L, null));
        assertEquals("ID and orderCommission details cannot be null", exception.getMessage());
        verify(orderCommissionRepository, never()).findById(any());
        verify(orderCommissionRepository, never()).save(any());
    }

    @Test
    void updateWithNonExistingIdShouldThrowRuntimeException() {
        // Arrange
        OrderCommission details = new OrderCommission();
        when(orderCommissionRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            orderCommissionService.update(999L, details));
        assertEquals("OrderCommission not found with id: 999", exception.getMessage());
        verify(orderCommissionRepository).findById(999L);
        verify(orderCommissionRepository, never()).save(any());
    }

    @Test
    void deleteWithValidIdShouldCallRepository() {
        when(orderCommissionRepository.existsById(1L)).thenReturn(true);
        // Act
        orderCommissionService.delete(1L);

        // Assert
        verify(orderCommissionRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteWithNullIdShouldThrowIllegalArgumentException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            orderCommissionService.delete(null));
        assertEquals("ID cannot be null", exception.getMessage());
        verify(orderCommissionRepository, never()).deleteById(any());
    }

    // PROPERTIES Tests
    @Test
    void getByCommissionShouldReturnMatchingCommissions() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setCommission(new BigDecimal(COMISSION_10));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionRepository.findByCommission(new BigDecimal(COMISSION_10))).thenReturn(expected);

        // Act
        List<OrderCommission> result = orderCommissionService.getByCommission(new BigDecimal(COMISSION_10));

        // Assert
        assertEquals(1, result.size());
        assertEquals(new BigDecimal(COMISSION_10), result.get(0).getCommission());
        verify(orderCommissionRepository).findByCommission(new BigDecimal(COMISSION_10));
    }

    @Test
    void getByFromOrderValueShouldReturnMatchingCommissions() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setFromOrderValue(new BigDecimal(ORDERVALUE_1000));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionRepository.findByFromOrderValue(new BigDecimal(ORDERVALUE_1000))).thenReturn(expected);

        // Act
        List<OrderCommission> result = orderCommissionService.getByFromOrderValue(new BigDecimal(ORDERVALUE_1000));

        // Assert
        assertEquals(1, result.size());
        assertEquals(new BigDecimal(ORDERVALUE_1000), result.get(0).getFromOrderValue());
        verify(orderCommissionRepository).findByFromOrderValue(new BigDecimal(ORDERVALUE_1000));
    }

    @Test
    void getByMinCommissionShouldReturnMatchingCommissions() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setMinCommission(new BigDecimal(MINCOMM_25));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionRepository.findByMinCommission(new BigDecimal(MINCOMM_25))).thenReturn(expected);

        // Act
        List<OrderCommission> result = orderCommissionService.getByMinCommission(new BigDecimal(MINCOMM_25));

        // Assert
        assertEquals(1, result.size());
        assertEquals(new BigDecimal(MINCOMM_25), result.get(0).getMinCommission());
        verify(orderCommissionRepository).findByMinCommission(new BigDecimal(MINCOMM_25));
    }

    @Test
    void getByOrderIdShouldReturnMatchingCommissions() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setCommission(new BigDecimal("5.00"));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionRepository.findByOrderId(1L)).thenReturn(expected);

        // Act
        List<OrderCommission> result = orderCommissionService.getByOrderId(1L);

        // Assert
        assertEquals(1, result.size());
        assertEquals(new BigDecimal("5.00"), result.get(0).getCommission());
        verify(orderCommissionRepository).findByOrderId(1L);
    }

    // HELPERS Tests
    @Test
    void getByCommissionLessThanEqualShouldReturnFilteredResults() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setCommission(new BigDecimal("7.50"));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionRepository.findByCommissionLessThanEqual(new BigDecimal(COMISSION_10))).thenReturn(expected);

        // Act
        List<OrderCommission> result = orderCommissionService.getByCommissionLessThanEqual(new BigDecimal(COMISSION_10));

        // Assert
        assertEquals(1, result.size());
        assertEquals(new BigDecimal("7.50"), result.get(0).getCommission());
        verify(orderCommissionRepository).findByCommissionLessThanEqual(new BigDecimal(COMISSION_10));
    }

    @Test
    void getByCommissionGreaterThanEqualShouldReturnFilteredResults() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setCommission(new BigDecimal(COMISSION_15));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionRepository.findByCommissionGreaterThanEqual(new BigDecimal(COMISSION_10))).thenReturn(expected);

        // Act
        List<OrderCommission> result = orderCommissionService.getByCommissionGreaterThanEqual(new BigDecimal(COMISSION_10));

        // Assert
        assertEquals(1, result.size());
        assertEquals(new BigDecimal(COMISSION_15), result.get(0).getCommission());
        verify(orderCommissionRepository).findByCommissionGreaterThanEqual(new BigDecimal(COMISSION_10));
    }

    @Test
    void getByCommissionBetweenShouldReturnFilteredResults() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setCommission(new BigDecimal(COMISSION_15));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionRepository.findByCommissionBetween(new BigDecimal(COMISSION_10), new BigDecimal(COMISSION_20))).thenReturn(expected);

        // Act
        List<OrderCommission> result = orderCommissionService.getByCommissionBetween(new BigDecimal(COMISSION_10), new BigDecimal(COMISSION_20));

        // Assert
        assertEquals(1, result.size());
        assertEquals(new BigDecimal(COMISSION_15), result.get(0).getCommission());
        verify(orderCommissionRepository).findByCommissionBetween(new BigDecimal(COMISSION_10), new BigDecimal(COMISSION_20));
    }

    @Test
    void getByFromOrderValueLessThanEqualShouldReturnFilteredResults() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setFromOrderValue(new BigDecimal("500.00"));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionRepository.findByFromOrderValueLessThanEqual(new BigDecimal(ORDERVALUE_1000))).thenReturn(expected);

        // Act
        List<OrderCommission> result = orderCommissionService.getByFromOrderValueLessThanEqual(new BigDecimal(ORDERVALUE_1000));

        // Assert
        assertEquals(1, result.size());
        assertEquals(new BigDecimal("500.00"), result.get(0).getFromOrderValue());
        verify(orderCommissionRepository).findByFromOrderValueLessThanEqual(new BigDecimal(ORDERVALUE_1000));
    }

    @Test
    void getByFromOrderValueGreaterThanEqualShouldReturnFilteredResults() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setFromOrderValue(new BigDecimal(ORDERVALUE_1500));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionRepository.findByFromOrderValueGreaterThanEqual(new BigDecimal(ORDERVALUE_1000))).thenReturn(expected);

        // Act
        List<OrderCommission> result = orderCommissionService.getByFromOrderValueGreaterThanEqual(new BigDecimal(ORDERVALUE_1000));

        // Assert
        assertEquals(1, result.size());
        assertEquals(new BigDecimal(ORDERVALUE_1500), result.get(0).getFromOrderValue());
        verify(orderCommissionRepository).findByFromOrderValueGreaterThanEqual(new BigDecimal(ORDERVALUE_1000));
    }

    @Test
    void getByFromOrderValueBetweenShouldReturnFilteredResults() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setFromOrderValue(new BigDecimal(ORDERVALUE_1500));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionRepository.findByFromOrderValueBetween(new BigDecimal(ORDERVALUE_1000), new BigDecimal(ORDERVALUE_2000))).thenReturn(expected);

        // Act
        List<OrderCommission> result = orderCommissionService.getByFromOrderValueBetween(new BigDecimal(ORDERVALUE_1000), new BigDecimal(ORDERVALUE_2000));

        // Assert
        assertEquals(1, result.size());
        assertEquals(new BigDecimal(ORDERVALUE_1500), result.get(0).getFromOrderValue());
        verify(orderCommissionRepository).findByFromOrderValueBetween(new BigDecimal(ORDERVALUE_1000), new BigDecimal(ORDERVALUE_2000));
    }

    @Test
    void getByMinCommissionLessThanEqualShouldReturnFilteredResults() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setMinCommission(new BigDecimal(COMISSION_20));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionRepository.findByMinCommissionLessThanEqual(new BigDecimal(MINCOMM_30))).thenReturn(expected);

        // Act
        List<OrderCommission> result = orderCommissionService.getByMinCommissionLessThanEqual(new BigDecimal(MINCOMM_30));

        // Assert
        assertEquals(1, result.size());
        assertEquals(new BigDecimal(COMISSION_20), result.get(0).getMinCommission());
        verify(orderCommissionRepository).findByMinCommissionLessThanEqual(new BigDecimal(MINCOMM_30));
    }

    @Test
    void getByMinCommissionGreaterThanEqualShouldReturnFilteredResults() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setMinCommission(new BigDecimal("40.00"));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionRepository.findByMinCommissionGreaterThanEqual(new BigDecimal(MINCOMM_30))).thenReturn(expected);

        // Act
        List<OrderCommission> result = orderCommissionService.getByMinCommissionGreaterThanEqual(new BigDecimal(MINCOMM_30));

        // Assert
        assertEquals(1, result.size());
        assertEquals(new BigDecimal("40.00"), result.get(0).getMinCommission());
        verify(orderCommissionRepository).findByMinCommissionGreaterThanEqual(new BigDecimal(MINCOMM_30));
    }

    @Test
    void getByMinCommissionBetweenShouldReturnFilteredResults() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setMinCommission(new BigDecimal(MINCOMM_25));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionRepository.findByMinCommissionBetween(new BigDecimal(COMISSION_20), new BigDecimal(MINCOMM_30))).thenReturn(expected);

        // Act
        List<OrderCommission> result = orderCommissionService.getByMinCommissionBetween(new BigDecimal(COMISSION_20), new BigDecimal(MINCOMM_30));

        // Assert
        assertEquals(1, result.size());
        assertEquals(new BigDecimal(MINCOMM_25), result.get(0).getMinCommission());
        verify(orderCommissionRepository).findByMinCommissionBetween(new BigDecimal(COMISSION_20), new BigDecimal(MINCOMM_30));
    }

    @Test
    void getAllMethodsWhenNoResultsShouldReturnEmptyLists() {
        // Arrange
        when(orderCommissionRepository.findByCommission(any())).thenReturn(Collections.emptyList());
        when(orderCommissionRepository.findByFromOrderValue(any())).thenReturn(Collections.emptyList());
        when(orderCommissionRepository.findByMinCommission(any())).thenReturn(Collections.emptyList());
        when(orderCommissionRepository.findByOrderId(any())).thenReturn(Collections.emptyList());

        // Act & Assert
        assertTrue(orderCommissionService.getByCommission(new BigDecimal(COMISSION_10)).isEmpty());
        assertTrue(orderCommissionService.getByFromOrderValue(new BigDecimal(ORDERVALUE_1000)).isEmpty());
        assertTrue(orderCommissionService.getByMinCommission(new BigDecimal(MINCOMM_25)).isEmpty());
        assertTrue(orderCommissionService.getByOrderId(1L).isEmpty());
    }
}