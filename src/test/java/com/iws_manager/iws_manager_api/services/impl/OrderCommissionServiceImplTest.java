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

    @Mock
    private OrderCommissionRepository orderCommissionRepository;

    @InjectMocks
    private OrderCommissionServiceImpl orderCommissionService;

    // CRUD Tests
    @Test
    void createWithValidOrderCommissionShouldSaveAndReturn() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setCommission(new BigDecimal("10.00"));
        
        when(orderCommissionRepository.save(any(OrderCommission.class))).thenReturn(commission);

        // Act
        OrderCommission result = orderCommissionService.create(commission);

        // Assert
        assertNotNull(result);
        assertEquals(new BigDecimal("10.00"), result.getCommission());
        verify(orderCommissionRepository).save(commission);
    }

    @Test
    void createWithNullOrderCommissionShouldThrowIllegalArgumentException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderCommissionService.create(null);
        });
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
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderCommissionService.findById(null);
        });
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
        existing.setMinCommission(new BigDecimal("10.00"));
        
        OrderCommission details = new OrderCommission();
        details.setCommission(new BigDecimal("15.00"));
        details.setFromOrderValue(new BigDecimal("200.00"));
        details.setMinCommission(new BigDecimal("20.00"));
        
        when(orderCommissionRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(orderCommissionRepository.save(any(OrderCommission.class))).thenReturn(existing);

        // Act
        OrderCommission result = orderCommissionService.update(1L, details);

        // Assert
        assertNotNull(result);
        assertEquals(new BigDecimal("15.00"), result.getCommission());
        assertEquals(new BigDecimal("200.00"), result.getFromOrderValue());
        assertEquals(new BigDecimal("20.00"), result.getMinCommission());
        verify(orderCommissionRepository).findById(1L);
        verify(orderCommissionRepository).save(existing);
    }

    @Test
    void updateWithNullIdShouldThrowIllegalArgumentException() {
        // Arrange
        OrderCommission details = new OrderCommission();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderCommissionService.update(null, details);
        });
        assertEquals("ID and orderCommission details cannot be null", exception.getMessage());
        verify(orderCommissionRepository, never()).findById(any());
        verify(orderCommissionRepository, never()).save(any());
    }

    @Test
    void updateWithNullDetailsShouldThrowIllegalArgumentException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderCommissionService.update(1L, null);
        });
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
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderCommissionService.update(999L, details);
        });
        assertEquals("OrderCommission not found with id: 999", exception.getMessage());
        verify(orderCommissionRepository).findById(999L);
        verify(orderCommissionRepository, never()).save(any());
    }

    @Test
    void deleteWithValidIdShouldCallRepository() {
        // Act
        orderCommissionService.delete(1L);

        // Assert
        verify(orderCommissionRepository).deleteById(1L);
    }

    @Test
    void deleteWithNullIdShouldThrowIllegalArgumentException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderCommissionService.delete(null);
        });
        assertEquals("ID cannot be null", exception.getMessage());
        verify(orderCommissionRepository, never()).deleteById(any());
    }

    // PROPERTIES Tests
    @Test
    void getByCommissionShouldReturnMatchingCommissions() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setCommission(new BigDecimal("10.00"));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionRepository.findByCommission(new BigDecimal("10.00"))).thenReturn(expected);

        // Act
        List<OrderCommission> result = orderCommissionService.getByCommission(new BigDecimal("10.00"));

        // Assert
        assertEquals(1, result.size());
        assertEquals(new BigDecimal("10.00"), result.get(0).getCommission());
        verify(orderCommissionRepository).findByCommission(new BigDecimal("10.00"));
    }

    @Test
    void getByFromOrderValueShouldReturnMatchingCommissions() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setFromOrderValue(new BigDecimal("1000.00"));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionRepository.findByFromOrderValue(new BigDecimal("1000.00"))).thenReturn(expected);

        // Act
        List<OrderCommission> result = orderCommissionService.getByFromOrderValue(new BigDecimal("1000.00"));

        // Assert
        assertEquals(1, result.size());
        assertEquals(new BigDecimal("1000.00"), result.get(0).getFromOrderValue());
        verify(orderCommissionRepository).findByFromOrderValue(new BigDecimal("1000.00"));
    }

    @Test
    void getByMinCommissionShouldReturnMatchingCommissions() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setMinCommission(new BigDecimal("25.00"));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionRepository.findByMinCommission(new BigDecimal("25.00"))).thenReturn(expected);

        // Act
        List<OrderCommission> result = orderCommissionService.getByMinCommission(new BigDecimal("25.00"));

        // Assert
        assertEquals(1, result.size());
        assertEquals(new BigDecimal("25.00"), result.get(0).getMinCommission());
        verify(orderCommissionRepository).findByMinCommission(new BigDecimal("25.00"));
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
        
        when(orderCommissionRepository.findByCommissionLessThanEqual(new BigDecimal("10.00"))).thenReturn(expected);

        // Act
        List<OrderCommission> result = orderCommissionService.getByCommissionLessThanEqual(new BigDecimal("10.00"));

        // Assert
        assertEquals(1, result.size());
        assertEquals(new BigDecimal("7.50"), result.get(0).getCommission());
        verify(orderCommissionRepository).findByCommissionLessThanEqual(new BigDecimal("10.00"));
    }

    @Test
    void getByCommissionGreaterThanEqualShouldReturnFilteredResults() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setCommission(new BigDecimal("15.00"));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionRepository.findByCommissionGreaterThanEqual(new BigDecimal("10.00"))).thenReturn(expected);

        // Act
        List<OrderCommission> result = orderCommissionService.getByCommissionGreaterThanEqual(new BigDecimal("10.00"));

        // Assert
        assertEquals(1, result.size());
        assertEquals(new BigDecimal("15.00"), result.get(0).getCommission());
        verify(orderCommissionRepository).findByCommissionGreaterThanEqual(new BigDecimal("10.00"));
    }

    @Test
    void getByCommissionBetweenShouldReturnFilteredResults() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setCommission(new BigDecimal("15.00"));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionRepository.findByCommissionBetween(new BigDecimal("10.00"), new BigDecimal("20.00"))).thenReturn(expected);

        // Act
        List<OrderCommission> result = orderCommissionService.getByCommissionBetween(new BigDecimal("10.00"), new BigDecimal("20.00"));

        // Assert
        assertEquals(1, result.size());
        assertEquals(new BigDecimal("15.00"), result.get(0).getCommission());
        verify(orderCommissionRepository).findByCommissionBetween(new BigDecimal("10.00"), new BigDecimal("20.00"));
    }

    @Test
    void getByFromOrderValueLessThanEqualShouldReturnFilteredResults() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setFromOrderValue(new BigDecimal("500.00"));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionRepository.findByFromOrderValueLessThanEqual(new BigDecimal("1000.00"))).thenReturn(expected);

        // Act
        List<OrderCommission> result = orderCommissionService.getByFromOrderValueLessThanEqual(new BigDecimal("1000.00"));

        // Assert
        assertEquals(1, result.size());
        assertEquals(new BigDecimal("500.00"), result.get(0).getFromOrderValue());
        verify(orderCommissionRepository).findByFromOrderValueLessThanEqual(new BigDecimal("1000.00"));
    }

    @Test
    void getByFromOrderValueGreaterThanEqualShouldReturnFilteredResults() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setFromOrderValue(new BigDecimal("1500.00"));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionRepository.findByFromOrderValueGreaterThanEqual(new BigDecimal("1000.00"))).thenReturn(expected);

        // Act
        List<OrderCommission> result = orderCommissionService.getByFromOrderValueGreaterThanEqual(new BigDecimal("1000.00"));

        // Assert
        assertEquals(1, result.size());
        assertEquals(new BigDecimal("1500.00"), result.get(0).getFromOrderValue());
        verify(orderCommissionRepository).findByFromOrderValueGreaterThanEqual(new BigDecimal("1000.00"));
    }

    @Test
    void getByFromOrderValueBetweenShouldReturnFilteredResults() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setFromOrderValue(new BigDecimal("1500.00"));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionRepository.findByFromOrderValueBetween(new BigDecimal("1000.00"), new BigDecimal("2000.00"))).thenReturn(expected);

        // Act
        List<OrderCommission> result = orderCommissionService.getByFromOrderValueBetween(new BigDecimal("1000.00"), new BigDecimal("2000.00"));

        // Assert
        assertEquals(1, result.size());
        assertEquals(new BigDecimal("1500.00"), result.get(0).getFromOrderValue());
        verify(orderCommissionRepository).findByFromOrderValueBetween(new BigDecimal("1000.00"), new BigDecimal("2000.00"));
    }

    @Test
    void getByMinCommissionLessThanEqualShouldReturnFilteredResults() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setMinCommission(new BigDecimal("20.00"));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionRepository.findByMinCommissionLessThanEqual(new BigDecimal("30.00"))).thenReturn(expected);

        // Act
        List<OrderCommission> result = orderCommissionService.getByMinCommissionLessThanEqual(new BigDecimal("30.00"));

        // Assert
        assertEquals(1, result.size());
        assertEquals(new BigDecimal("20.00"), result.get(0).getMinCommission());
        verify(orderCommissionRepository).findByMinCommissionLessThanEqual(new BigDecimal("30.00"));
    }

    @Test
    void getByMinCommissionGreaterThanEqualShouldReturnFilteredResults() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setMinCommission(new BigDecimal("40.00"));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionRepository.findByMinCommissionGreaterThanEqual(new BigDecimal("30.00"))).thenReturn(expected);

        // Act
        List<OrderCommission> result = orderCommissionService.getByMinCommissionGreaterThanEqual(new BigDecimal("30.00"));

        // Assert
        assertEquals(1, result.size());
        assertEquals(new BigDecimal("40.00"), result.get(0).getMinCommission());
        verify(orderCommissionRepository).findByMinCommissionGreaterThanEqual(new BigDecimal("30.00"));
    }

    @Test
    void getByMinCommissionBetweenShouldReturnFilteredResults() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setMinCommission(new BigDecimal("25.00"));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionRepository.findByMinCommissionBetween(new BigDecimal("20.00"), new BigDecimal("30.00"))).thenReturn(expected);

        // Act
        List<OrderCommission> result = orderCommissionService.getByMinCommissionBetween(new BigDecimal("20.00"), new BigDecimal("30.00"));

        // Assert
        assertEquals(1, result.size());
        assertEquals(new BigDecimal("25.00"), result.get(0).getMinCommission());
        verify(orderCommissionRepository).findByMinCommissionBetween(new BigDecimal("20.00"), new BigDecimal("30.00"));
    }

    @Test
    void getAllMethodsWhenNoResultsShouldReturnEmptyLists() {
        // Arrange
        when(orderCommissionRepository.findByCommission(any())).thenReturn(Collections.emptyList());
        when(orderCommissionRepository.findByFromOrderValue(any())).thenReturn(Collections.emptyList());
        when(orderCommissionRepository.findByMinCommission(any())).thenReturn(Collections.emptyList());
        when(orderCommissionRepository.findByOrderId(any())).thenReturn(Collections.emptyList());

        // Act & Assert
        assertTrue(orderCommissionService.getByCommission(new BigDecimal("10.00")).isEmpty());
        assertTrue(orderCommissionService.getByFromOrderValue(new BigDecimal("1000.00")).isEmpty());
        assertTrue(orderCommissionService.getByMinCommission(new BigDecimal("25.00")).isEmpty());
        assertTrue(orderCommissionService.getByOrderId(1L).isEmpty());
    }
}