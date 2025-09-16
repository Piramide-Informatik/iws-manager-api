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

import com.iws_manager.iws_manager_api.models.Vat;
import com.iws_manager.iws_manager_api.repositories.VatRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class VatServiceImplTest {

    @Mock
    private VatRepository vatRepository;

    @InjectMocks
    private VatServiceImpl vatService;

    private Vat vat;
    private Vat vat2;

    @BeforeEach
    void setUp() {
        vat = new Vat();
        vat.setId(1L);
        vat.setLabel("Standard VAT");

        vat2 = new Vat();
        vat2.setId(2L);
        vat2.setLabel("Reduced VAT");
    }

    @Test
    void testCreateSuccess() {
        // Arrange
        when(vatRepository.save(any(Vat.class))).thenReturn(vat);

        // Act
        Vat result = vatService.create(vat);

        // Assert
        assertNotNull(result);
        assertEquals(vat.getId(), result.getId());
        assertEquals(vat.getLabel(), result.getLabel());
        verify(vatRepository, times(1)).save(vat);
    }

    @Test
    void testCreateWithNullVatThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> vatService.create(null));
        
        assertEquals("Vat cannot be null", exception.getMessage());
        verify(vatRepository, never()).save(any());
    }

    @Test
    void testFindByIdSuccess() {
        // Arrange
        when(vatRepository.findById(1L)).thenReturn(Optional.of(vat));

        // Act
        Optional<Vat> result = vatService.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(vat.getId(), result.get().getId());
        assertEquals(vat.getLabel(), result.get().getLabel());
        verify(vatRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdWithNullIdThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> vatService.findById(null));
        
        assertEquals("ID cannot be null", exception.getMessage());
        verify(vatRepository, never()).findById(any());
    }

    @Test
    void testFindByIdNotFound() {
        // Arrange
        when(vatRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<Vat> result = vatService.findById(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(vatRepository, times(1)).findById(99L);
    }

    @Test
    void testFindAllSuccess() {
        // Arrange
        List<Vat> vatList = Arrays.asList(vat, vat2);
        when(vatRepository.findAllByOrderByLabelAsc()).thenReturn(vatList);

        // Act
        List<Vat> result = vatService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Standard VAT", result.get(0).getLabel());
        assertEquals("Reduced VAT", result.get(1).getLabel());
        verify(vatRepository, times(1)).findAllByOrderByLabelAsc();
    }

    @Test
    void testFindAllEmpty() {
        // Arrange
        when(vatRepository.findAllByOrderByLabelAsc()).thenReturn(Arrays.asList());

        // Act
        List<Vat> result = vatService.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(vatRepository, times(1)).findAllByOrderByLabelAsc();
    }

    @Test
    void testUpdateSuccess() {
        // Arrange
        Vat updatedVat = new Vat();
        updatedVat.setLabel("Updated VAT");

        when(vatRepository.findById(1L)).thenReturn(Optional.of(vat));
        when(vatRepository.save(any(Vat.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Vat result = vatService.update(1L, updatedVat);

        // Assert
        assertNotNull(result);
        assertEquals("Updated VAT", result.getLabel());
        assertEquals(1L, result.getId()); // ID should remain the same
        verify(vatRepository, times(1)).findById(1L);
        verify(vatRepository, times(1)).save(vat);
    }

    @Test
    void testUpdateWithNullIdThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> vatService.update(null, vat));
        
        assertEquals("ID and VAT details cannot be null", exception.getMessage());
        verify(vatRepository, never()).findById(any());
        verify(vatRepository, never()).save(any());
    }

    @Test
    void testUpdateWithNullVatDetailsThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> vatService.update(1L, null));
        
        assertEquals("ID and VAT details cannot be null", exception.getMessage());
        verify(vatRepository, never()).findById(any());
        verify(vatRepository, never()).save(any());
    }

    @Test
    void testUpdateNotFoundThrowsException() {
        // Arrange
        when(vatRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> vatService.update(99L, vat));
        
        assertEquals("VAT not found with id: 99", exception.getMessage());
        verify(vatRepository, times(1)).findById(99L);
        verify(vatRepository, never()).save(any());
    }

    @Test
    void testDeleteSuccess() {
        // Arrange
        when(vatRepository.existsById(1L)).thenReturn(true);
        doNothing().when(vatRepository).deleteById(1L);

        // Act
        vatService.delete(1L);

        // Assert
        verify(vatRepository, times(1)).existsById(1L);
        verify(vatRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteWithNullIdThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> vatService.delete(null));
        
        assertEquals("ID cannot be null", exception.getMessage());
        verify(vatRepository, never()).existsById(any());
        verify(vatRepository, never()).deleteById(any());
    }

    @Test
    void testDeleteNotFoundThrowsException() {
        // Arrange
        when(vatRepository.existsById(99L)).thenReturn(false);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, 
            () -> vatService.delete(99L));
        
        assertEquals("Vat not found with id: 99", exception.getMessage());
        verify(vatRepository, times(1)).existsById(99L);
        verify(vatRepository, never()).deleteById(any());
    }

    @Test
    void testConstructorInjection() {
        // Arrange
        VatRepository testRepository = mock(VatRepository.class);
        
        // Act
        VatServiceImpl testService = new VatServiceImpl(testRepository);
        
        // Assert
        assertNotNull(testService);
        when(testRepository.findAllByOrderByLabelAsc()).thenReturn(Arrays.asList(vat));
        List<Vat> result = testService.findAll();
        
        assertEquals(1, result.size());
        verify(testRepository, times(1)).findAllByOrderByLabelAsc();
    }

    @Test
    void testUpdateVerifyFieldUpdate() {
        // Arrange
        Vat updatedVat = new Vat();
        updatedVat.setLabel("Completely New Label");

        when(vatRepository.findById(1L)).thenReturn(Optional.of(vat));
        when(vatRepository.save(any(Vat.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Vat result = vatService.update(1L, updatedVat);

        // Assert
        assertEquals("Completely New Label", result.getLabel());
        // Verify that only the label was updated, other fields should remain unchanged
        assertEquals(1L, result.getId());
        verify(vatRepository, times(1)).save(vat);
    }
}