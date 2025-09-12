package com.iws_manager.iws_manager_api.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iws_manager.iws_manager_api.models.InvoiceType;
import com.iws_manager.iws_manager_api.repositories.InvoiceTypeRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class InvoiceTypeServiceImplTest {

    private static final String TEST_NAME1 = "Direct Billing";
    private static final String TEST_NAME2 = "Network Billing";

    @Mock
    private InvoiceTypeRepository invoiceTypeRepository;

    @InjectMocks
    private InvoiceTypeServiceImpl invoiceTypeService;

    private InvoiceType invoiceType;
    private InvoiceType invoiceType2;

    @BeforeEach
    void setUp() {
        invoiceType = new InvoiceType();
        invoiceType.setId(1L);
        invoiceType.setName(TEST_NAME1);

        invoiceType2 = new InvoiceType();
        invoiceType2.setId(2L);
        invoiceType2.setName(TEST_NAME2);
    }

    @Test
    void testCreateSuccess() {
        // Arrange
        when(invoiceTypeRepository.save(any(InvoiceType.class))).thenReturn(invoiceType);

        // Act
        InvoiceType result = invoiceTypeService.create(invoiceType);

        // Assert
        assertNotNull(result);
        assertEquals(invoiceType.getId(), result.getId());
        assertEquals(invoiceType.getName(), result.getName());
        verify(invoiceTypeRepository, times(1)).save(invoiceType);
    }

    @Test
    void testCreateWithNullInvoiceTypeThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> invoiceTypeService.create(null));
        
        assertEquals("InvoiceType cannot be null", exception.getMessage());
        verify(invoiceTypeRepository, never()).save(any());
    }

    @Test
    void testFindByIdSuccess() {
        // Arrange
        when(invoiceTypeRepository.findById(1L)).thenReturn(Optional.of(invoiceType));

        // Act
        Optional<InvoiceType> result = invoiceTypeService.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(invoiceType.getId(), result.get().getId());
        assertEquals(invoiceType.getName(), result.get().getName());
        verify(invoiceTypeRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdWithNullIdThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> invoiceTypeService.findById(null));
        
        assertEquals("ID cannot be null", exception.getMessage());
        verify(invoiceTypeRepository, never()).findById(any());
    }

    @Test
    void testFindByIdNotFound() {
        // Arrange
        when(invoiceTypeRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<InvoiceType> result = invoiceTypeService.findById(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(invoiceTypeRepository, times(1)).findById(99L);
    }

    @Test
    void testFindAllSuccess() {
        // Arrange
        List<InvoiceType> invoiceTypeList = Arrays.asList(invoiceType, invoiceType2);
        when(invoiceTypeRepository.findAllByOrderByNameAsc()).thenReturn(invoiceTypeList);

        // Act
        List<InvoiceType> result = invoiceTypeService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(TEST_NAME1, result.get(0).getName());
        assertEquals(TEST_NAME2, result.get(1).getName());
        verify(invoiceTypeRepository, times(1)).findAllByOrderByNameAsc();
    }

    @Test
    void testFindAllEmpty() {
        // Arrange
        when(invoiceTypeRepository.findAllByOrderByNameAsc()).thenReturn(Arrays.asList());

        // Act
        List<InvoiceType> result = invoiceTypeService.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(invoiceTypeRepository, times(1)).findAllByOrderByNameAsc();
    }

    @Test
    void testUpdateSuccess() {
        // Arrange
        InvoiceType updatedInvoiceType = new InvoiceType();
        updatedInvoiceType.setName("Updated Billing Type");

        when(invoiceTypeRepository.findById(1L)).thenReturn(Optional.of(invoiceType));
        when(invoiceTypeRepository.save(any(InvoiceType.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        InvoiceType result = invoiceTypeService.update(1L, updatedInvoiceType);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Billing Type", result.getName());
        assertEquals(1L, result.getId()); // ID should remain the same
        verify(invoiceTypeRepository, times(1)).findById(1L);
        verify(invoiceTypeRepository, times(1)).save(invoiceType);
    }

    @Test
    void testUpdateWithNullIdThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> invoiceTypeService.update(null, invoiceType));
        
        assertEquals("ID and InvoiceType details cannot be null", exception.getMessage());
        verify(invoiceTypeRepository, never()).findById(any());
        verify(invoiceTypeRepository, never()).save(any());
    }

    @Test
    void testUpdateWithNullInvoiceTypeDetailsThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> invoiceTypeService.update(1L, null));
        
        assertEquals("ID and InvoiceType details cannot be null", exception.getMessage());
        verify(invoiceTypeRepository, never()).findById(any());
        verify(invoiceTypeRepository, never()).save(any());
    }

    @Test
    void testUpdateNotFoundThrowsException() {
        // Arrange
        when(invoiceTypeRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> invoiceTypeService.update(99L, invoiceType));
        
        assertEquals("InvoiceType not found with id: 99", exception.getMessage());
        verify(invoiceTypeRepository, times(1)).findById(99L);
        verify(invoiceTypeRepository, never()).save(any());
    }

    @Test
    void testDeleteSuccess() {
        // Arrange
        when(invoiceTypeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(invoiceTypeRepository).deleteById(1L);

        // Act
        invoiceTypeService.delete(1L);

        // Assert
        verify(invoiceTypeRepository, times(1)).existsById(1L);
        verify(invoiceTypeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteWithNullIdThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> invoiceTypeService.delete(null));
        
        assertEquals("ID cannot be null", exception.getMessage());
        verify(invoiceTypeRepository, never()).existsById(any());
        verify(invoiceTypeRepository, never()).deleteById(any());
    }

    @Test
    void testDeleteNotFoundThrowsException() {
        // Arrange
        when(invoiceTypeRepository.existsById(99L)).thenReturn(false);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, 
            () -> invoiceTypeService.delete(99L));
        
        assertEquals("InvoiceType not found with id: 99", exception.getMessage());
        verify(invoiceTypeRepository, times(1)).existsById(99L);
        verify(invoiceTypeRepository, never()).deleteById(any());
    }

    @Test
    void testConstructorInjection() {
        // Arrange
        InvoiceTypeRepository testRepository = mock(InvoiceTypeRepository.class);
        
        // Act
        InvoiceTypeServiceImpl testService = new InvoiceTypeServiceImpl(testRepository);
        
        // Assert
        assertNotNull(testService);
        when(testRepository.findAllByOrderByNameAsc()).thenReturn(Arrays.asList(invoiceType));
        List<InvoiceType> result = testService.findAll();
        
        assertEquals(1, result.size());
        verify(testRepository, times(1)).findAllByOrderByNameAsc();
    }

    @Test
    void testUpdateVerifyFieldUpdate() {
        // Arrange
        InvoiceType updatedInvoiceType = new InvoiceType();
        updatedInvoiceType.setName("Completely New Name");

        when(invoiceTypeRepository.findById(1L)).thenReturn(Optional.of(invoiceType));
        when(invoiceTypeRepository.save(any(InvoiceType.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        InvoiceType result = invoiceTypeService.update(1L, updatedInvoiceType);

        // Assert
        assertEquals("Completely New Name", result.getName());
        assertEquals(1L, result.getId());
        verify(invoiceTypeRepository, times(1)).save(invoiceType);
    }

    @Test
    void testFindAllOrderVerification() {
        // Arrange
        List<InvoiceType> invoiceTypeList = Arrays.asList(invoiceType2, invoiceType); // Orden inverso
        when(invoiceTypeRepository.findAllByOrderByNameAsc()).thenReturn(Arrays.asList(invoiceType, invoiceType2));

        // Act
        List<InvoiceType> result = invoiceTypeService.findAll();

        // Assert
        assertEquals(TEST_NAME1, result.get(0).getName()); 
        assertEquals(TEST_NAME2, result.get(1).getName()); 
        verify(invoiceTypeRepository, times(1)).findAllByOrderByNameAsc();
    }
}