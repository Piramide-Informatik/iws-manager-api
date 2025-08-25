package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.OrderCommission;
import com.iws_manager.iws_manager_api.services.interfaces.OrderCommissionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderCommissionControllerTest {

    private static final String COMMISSION_15 = "15.00";
    private static final String COMMISSION_10 = "10.00";
    private static final String COMMISSION_20 = "20.00";
    private static final String COMMISSION_30 = "30.00";
    private static final String FROMORDERVALUE_1000 = "1000.00";
    private static final String FROMORDERVALUE_1500 = "1500.00";
    private static final String FROMORDERVALUE_2000 = "2000.00";
    private static final String MINCOMMISSION_25 = "25.00";


    @Mock
    private OrderCommissionService orderCommissionService;

    @InjectMocks
    private OrderCommissionController orderCommissionController;

    // CRUD Tests
    @Test
    void createWithValidOrderCommissionShouldReturnCreated() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setCommission(new BigDecimal(COMMISSION_10));
        
        when(orderCommissionService.create(any(OrderCommission.class))).thenReturn(commission);

        // Act
        ResponseEntity<?> response = orderCommissionController.create(commission);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertInstanceOf(OrderCommission.class, response.getBody());
        verify(orderCommissionService).create(commission);
    }

    @Test
    void getByIdWithExistingIdShouldReturnOrderCommission() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setId(1L);
        
        when(orderCommissionService.findById(1L)).thenReturn(Optional.of(commission));

        // Act
        ResponseEntity<OrderCommission> response = orderCommissionController.getById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(orderCommissionService).findById(1L);
    }

    @Test
    void getByIdWithNonExistingIdShouldReturnNotFound() {
        // Arrange
        when(orderCommissionService.findById(999L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<OrderCommission> response = orderCommissionController.getById(999L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(orderCommissionService).findById(999L);
    }

    @Test
    void getAllShouldReturnAllOrderCommissions() {
        // Arrange
        OrderCommission comm1 = new OrderCommission();
        OrderCommission comm2 = new OrderCommission();
        List<OrderCommission> expected = Arrays.asList(comm1, comm2);
        
        when(orderCommissionService.findAll()).thenReturn(expected);

        // Act
        ResponseEntity<List<OrderCommission>> response = orderCommissionController.getAll();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(orderCommissionService).findAll();
    }

    @Test
    void getAllWhenNoOrderCommissionsShouldReturnEmptyList() {
        // Arrange
        when(orderCommissionService.findAll()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<OrderCommission>> response = orderCommissionController.getAll();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
        verify(orderCommissionService).findAll();
    }

    @Test
    void updateWithValidDataShouldReturnUpdatedOrderCommission() {
        // Arrange
        OrderCommission existing = new OrderCommission();
        existing.setId(1L);
        
        OrderCommission details = new OrderCommission();
        details.setCommission(new BigDecimal(COMMISSION_15));
        
        when(orderCommissionService.update(1L, details)).thenReturn(existing);

        // Act
        ResponseEntity<OrderCommission> response = orderCommissionController.update(1L, details);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(orderCommissionService).update(1L, details);
    }

    @Test
    void updateWithNonExistingIdShouldReturnNotFound() {
        // Arrange
        OrderCommission details = new OrderCommission();
        when(orderCommissionService.update(999L, details)).thenThrow(new RuntimeException("Not found"));

        // Act
        ResponseEntity<OrderCommission> response = orderCommissionController.update(999L, details);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(orderCommissionService).update(999L, details);
    }

    @Test
    void deleteWithValidIdShouldReturnNoContent() {
        // Arrange
        doNothing().when(orderCommissionService).delete(1L);

        // Act
        ResponseEntity<Void> response = orderCommissionController.delete(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(orderCommissionService).delete(1L);
    }

    @Test
    void deleteWithNonExistingIdShouldReturnNotFound() {
        // Arrange
        doThrow(new RuntimeException("Not found")).when(orderCommissionService).delete(999L);

        // Act
        ResponseEntity<Void> response = orderCommissionController.delete(999L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(orderCommissionService).delete(999L);
    }

    // PROPERTIES Tests
    @Test
    void getByCommissionShouldReturnMatchingCommissions() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setCommission(new BigDecimal(COMMISSION_10));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionService.getByCommission(new BigDecimal(COMMISSION_10))).thenReturn(expected);

        // Act
        ResponseEntity<List<OrderCommission>> response = orderCommissionController.getByCommission(new BigDecimal(COMMISSION_10));

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(new BigDecimal(COMMISSION_10), response.getBody().get(0).getCommission());
        verify(orderCommissionService).getByCommission(new BigDecimal(COMMISSION_10));
    }

    @Test
    void getByFromOrderValueShouldReturnMatchingCommissions() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setFromOrderValue(new BigDecimal(FROMORDERVALUE_1000));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionService.getByFromOrderValue(new BigDecimal(FROMORDERVALUE_1000))).thenReturn(expected);

        // Act
        ResponseEntity<List<OrderCommission>> response = orderCommissionController.getByFromOrderValue(new BigDecimal(FROMORDERVALUE_1000));

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(new BigDecimal(FROMORDERVALUE_1000), response.getBody().get(0).getFromOrderValue());
        verify(orderCommissionService).getByFromOrderValue(new BigDecimal(FROMORDERVALUE_1000));
    }

    @Test
    void getByMinCommissionShouldReturnMatchingCommissions() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setMinCommission(new BigDecimal(MINCOMMISSION_25));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionService.getByMinCommission(new BigDecimal(MINCOMMISSION_25))).thenReturn(expected);

        // Act
        ResponseEntity<List<OrderCommission>> response = orderCommissionController.getByMinCommission(new BigDecimal(MINCOMMISSION_25));

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(new BigDecimal(MINCOMMISSION_25), response.getBody().get(0).getMinCommission());
        verify(orderCommissionService).getByMinCommission(new BigDecimal(MINCOMMISSION_25));
    }

    @Test
    void getByOrderIdShouldReturnMatchingCommissions() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setCommission(new BigDecimal("5.00"));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionService.getByOrderId(1L)).thenReturn(expected);

        // Act
        ResponseEntity<List<OrderCommission>> response = orderCommissionController.getByOrderId(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(new BigDecimal("5.00"), response.getBody().get(0).getCommission());
        verify(orderCommissionService).getByOrderId(1L);
    }

    @Test
    void getByOrderIdWhenNoResultsShouldReturnEmptyList() {
        // Arrange
        when(orderCommissionService.getByOrderId(999L)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<OrderCommission>> response = orderCommissionController.getByOrderId(999L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
        verify(orderCommissionService).getByOrderId(999L);
    }

    // HELPERS Tests - COMMISSION
    @Test
    void getByCommissionLessThanEqualShouldReturnFilteredResults() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setCommission(new BigDecimal("7.50"));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionService.getByCommissionLessThanEqual(new BigDecimal(COMMISSION_10))).thenReturn(expected);

        // Act
        ResponseEntity<List<OrderCommission>> response = orderCommissionController.getByCommissionLessThanEqual(new BigDecimal(COMMISSION_10));

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(new BigDecimal("7.50"), response.getBody().get(0).getCommission());
        verify(orderCommissionService).getByCommissionLessThanEqual(new BigDecimal(COMMISSION_10));
    }

    @Test
    void getByCommissionGreaterThanEqualShouldReturnFilteredResults() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setCommission(new BigDecimal(COMMISSION_15));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionService.getByCommissionGreaterThanEqual(new BigDecimal(COMMISSION_10))).thenReturn(expected);

        // Act
        ResponseEntity<List<OrderCommission>> response = orderCommissionController.getByCommissionGreaterThanEqual(new BigDecimal(COMMISSION_10));

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(new BigDecimal(COMMISSION_15), response.getBody().get(0).getCommission());
        verify(orderCommissionService).getByCommissionGreaterThanEqual(new BigDecimal(COMMISSION_10));
    }

    @Test
    void getByCommissionBetweenShouldReturnFilteredResults() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setCommission(new BigDecimal(COMMISSION_15));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionService.getByCommissionBetween(new BigDecimal(COMMISSION_10), new BigDecimal(COMMISSION_20))).thenReturn(expected);

        // Act
        ResponseEntity<List<OrderCommission>> response = orderCommissionController.getByCommissionBetween(new BigDecimal(COMMISSION_10), new BigDecimal(COMMISSION_20));

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(new BigDecimal(COMMISSION_15), response.getBody().get(0).getCommission());
        verify(orderCommissionService).getByCommissionBetween(new BigDecimal(COMMISSION_10), new BigDecimal(COMMISSION_20));
    }

    // HELPERS Tests - FROM ORDER VALUE
    @Test
    void getByFromOrderValueLessThanEqualShouldReturnFilteredResults() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setFromOrderValue(new BigDecimal("500.00"));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionService.getByFromOrderValueLessThanEqual(new BigDecimal(FROMORDERVALUE_1000))).thenReturn(expected);

        // Act
        ResponseEntity<List<OrderCommission>> response = orderCommissionController.getByFromOrderValueLessThanEqual(new BigDecimal(FROMORDERVALUE_1000));

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(new BigDecimal("500.00"), response.getBody().get(0).getFromOrderValue());
        verify(orderCommissionService).getByFromOrderValueLessThanEqual(new BigDecimal(FROMORDERVALUE_1000));
    }

    @Test
    void getByFromOrderValueGreaterThanEqualShouldReturnFilteredResults() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setFromOrderValue(new BigDecimal(FROMORDERVALUE_1500));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionService.getByFromOrderValueGreaterThanEqual(new BigDecimal(FROMORDERVALUE_1000))).thenReturn(expected);

        // Act
        ResponseEntity<List<OrderCommission>> response = orderCommissionController.getByFromOrderValueGreaterThanEqual(new BigDecimal(FROMORDERVALUE_1000));

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(new BigDecimal(FROMORDERVALUE_1500), response.getBody().get(0).getFromOrderValue());
        verify(orderCommissionService).getByFromOrderValueGreaterThanEqual(new BigDecimal(FROMORDERVALUE_1000));
    }

    @Test
    void getByFromOrderValueBetweenShouldReturnFilteredResults() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setFromOrderValue(new BigDecimal(FROMORDERVALUE_1500));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionService.getByFromOrderValueBetween(new BigDecimal(FROMORDERVALUE_1000), new BigDecimal(FROMORDERVALUE_2000))).thenReturn(expected);

        // Act
        ResponseEntity<List<OrderCommission>> response = orderCommissionController.getByFromOrderValueBetween(new BigDecimal(FROMORDERVALUE_1000), new BigDecimal(FROMORDERVALUE_2000));

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(new BigDecimal(FROMORDERVALUE_1500), response.getBody().get(0).getFromOrderValue());
        verify(orderCommissionService).getByFromOrderValueBetween(new BigDecimal(FROMORDERVALUE_1000), new BigDecimal(FROMORDERVALUE_2000));
    }

    // HELPERS Tests - MIN COMMISSION
    @Test
    void getByMinCommissionLessThanEqualShouldReturnFilteredResults() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setMinCommission(new BigDecimal(COMMISSION_20));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionService.getByMinCommissionLessThanEqual(new BigDecimal(COMMISSION_30))).thenReturn(expected);

        // Act
        ResponseEntity<List<OrderCommission>> response = orderCommissionController.getByMinCommissionLessThanEqual(new BigDecimal(COMMISSION_30));

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(new BigDecimal(COMMISSION_20), response.getBody().get(0).getMinCommission());
        verify(orderCommissionService).getByMinCommissionLessThanEqual(new BigDecimal(COMMISSION_30));
    }

    @Test
    void getByMinCommissionGreaterThanEqualShouldReturnFilteredResults() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setMinCommission(new BigDecimal("40.00"));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionService.getByMinCommissionGreaterThanEqual(new BigDecimal(COMMISSION_30))).thenReturn(expected);

        // Act
        ResponseEntity<List<OrderCommission>> response = orderCommissionController.getByMinCommissionGreaterThanEqual(new BigDecimal(COMMISSION_30));

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(new BigDecimal("40.00"), response.getBody().get(0).getMinCommission());
        verify(orderCommissionService).getByMinCommissionGreaterThanEqual(new BigDecimal(COMMISSION_30));
    }

    @Test
    void getByMinCommissionBetweenShouldReturnFilteredResults() {
        // Arrange
        OrderCommission commission = new OrderCommission();
        commission.setMinCommission(new BigDecimal(MINCOMMISSION_25));
        List<OrderCommission> expected = Arrays.asList(commission);
        
        when(orderCommissionService.getByMinCommissionBetween(new BigDecimal(COMMISSION_20), new BigDecimal(COMMISSION_30))).thenReturn(expected);

        // Act
        ResponseEntity<List<OrderCommission>> response = orderCommissionController.getByMinCommissionBetween(new BigDecimal(COMMISSION_20), new BigDecimal(COMMISSION_30));

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(new BigDecimal(MINCOMMISSION_25), response.getBody().get(0).getMinCommission());
        verify(orderCommissionService).getByMinCommissionBetween(new BigDecimal(COMMISSION_20), new BigDecimal(COMMISSION_30));
    }

    @Test
    void getAllHelperMethodsWhenNoResultsShouldReturnEmptyLists() {
        // Arrange
        when(orderCommissionService.getByCommission(any())).thenReturn(Collections.emptyList());
        when(orderCommissionService.getByCommissionLessThanEqual(any())).thenReturn(Collections.emptyList());
        when(orderCommissionService.getByCommissionGreaterThanEqual(any())).thenReturn(Collections.emptyList());
        when(orderCommissionService.getByCommissionBetween(any(), any())).thenReturn(Collections.emptyList());

        // Act & Assert
        assertTrue(orderCommissionController.getByCommission(new BigDecimal(COMMISSION_10)).getBody().isEmpty());
        assertTrue(orderCommissionController.getByCommissionLessThanEqual(new BigDecimal(COMMISSION_10)).getBody().isEmpty());
        assertTrue(orderCommissionController.getByCommissionGreaterThanEqual(new BigDecimal(COMMISSION_10)).getBody().isEmpty());
        assertTrue(orderCommissionController.getByCommissionBetween(new BigDecimal(COMMISSION_10), new BigDecimal(COMMISSION_20)).getBody().isEmpty());
    }
}