package com.iws_manager.iws_manager_api.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.iws_manager.iws_manager_api.models.CostType;
import com.iws_manager.iws_manager_api.repositories.CostTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@DisplayName("CostType Service Implementation Tests")
class CostTypeServiceImplTest {

    @Mock
    private CostTypeRepository costTypeRepository;

    @InjectMocks
    private CostTypeServiceImpl costTypeService;

    private CostType sampleCostType;
    private CostType sampleCostType2;

    private static final String TYPE_TEST = "Material";

    @BeforeEach
    void setUp() {
        sampleCostType = new CostType();
        sampleCostType.setId(1L);
        sampleCostType.setType(TYPE_TEST);
        sampleCostType.setSequenceNo(1);

        sampleCostType2 = new CostType();
        sampleCostType2.setId(2L);
        sampleCostType2.setType("Labor");
        sampleCostType2.setSequenceNo(2);
    }

    @Test
    @DisplayName("Should save cost type successfully")
    void createShouldReturnSavedCostType() {
        // Arrange
        when(costTypeRepository.save(any(CostType.class))).thenReturn(sampleCostType);

        // Act
        CostType result = costTypeService.create(sampleCostType);

        // Assert
        assertNotNull(result);
        assertEquals(TYPE_TEST, result.getType());
        assertEquals(1, result.getSequenceNo());
        verify(costTypeRepository, times(1)).save(any(CostType.class));
    }

    @Test
    @DisplayName("Should throw exception when creating null CostType")
    void createShouldThrowExceptionWhenCostTypeIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> costTypeService.create(null));
        verify(costTypeRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should find cost type by ID")
    void findByIdShouldReturnCostTypeWhenExists() {
        // Arrange
        when(costTypeRepository.findById(1L)).thenReturn(Optional.of(sampleCostType));

        // Act
        Optional<CostType> result = costTypeService.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(TYPE_TEST, result.get().getType());
        verify(costTypeRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return empty when cost type not found")
    void findByIdShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(costTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<CostType> result = costTypeService.findById(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(costTypeRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Should throw exception when finding with null ID")
    void findByIdShouldThrowExceptionWhenIdIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> costTypeService.findById(null));
        verify(costTypeRepository, never()).findById(any());
    }

    @Test
    @DisplayName("Should return all cost types")
    void findAllShouldReturnAllCostTypes() {
        // Arrange
        when(costTypeRepository.findAll()).thenReturn(Arrays.asList(sampleCostType, sampleCostType2));

        // Act
        List<CostType> result = costTypeService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(costTypeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no cost types exist")
    void findAllShouldReturnEmptyListWhenNoCostTypes() {
        // Arrange
        when(costTypeRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<CostType> result = costTypeService.findAll();

        // Assert
        assertTrue(result.isEmpty());
        verify(costTypeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should update cost type successfully")
    void updateShouldReturnUpdatedCostType() {
        // Arrange
        CostType updatedDetails = new CostType();
        updatedDetails.setType("Material Updated");
        updatedDetails.setSequenceNo(10);

        when(costTypeRepository.findById(1L)).thenReturn(Optional.of(sampleCostType));
        when(costTypeRepository.save(any(CostType.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        CostType result = costTypeService.update(1L, updatedDetails);

        // Assert
        assertEquals("Material Updated", result.getType());
        assertEquals(10, result.getSequenceNo());
        verify(costTypeRepository, times(1)).findById(1L);
        verify(costTypeRepository, times(1)).save(any(CostType.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent cost type")
    void updateShouldThrowExceptionWhenCostTypeNotFound() {
        // Arrange
        when(costTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, 
            () -> costTypeService.update(99L, new CostType()));
        verify(costTypeRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when updating with null ID")
    void updateShouldThrowExceptionWhenIdIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, 
            () -> costTypeService.update(null, new CostType()));
        verify(costTypeRepository, never()).findById(any());
        verify(costTypeRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when updating with null cost type details")
    void updateShouldThrowExceptionWhenCostTypeDetailsIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, 
            () -> costTypeService.update(1L, null));
        verify(costTypeRepository, never()).findById(any());
        verify(costTypeRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should delete cost type successfully")
    void deleteShouldRemoveCostType() {
        // Act
        costTypeService.delete(1L);

        // Assert
        verify(costTypeRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw exception when deleting with null ID")
    void deleteShouldThrowExceptionWhenIdIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> costTypeService.delete(null));
        verify(costTypeRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Should maintain original ID when updating cost type")
    void updateShouldMaintainOriginalId() {
        // Arrange
        CostType updatedDetails = new CostType();
        updatedDetails.setType("Updated Type");
        updatedDetails.setSequenceNo(5);

        when(costTypeRepository.findById(1L)).thenReturn(Optional.of(sampleCostType));
        when(costTypeRepository.save(any(CostType.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        CostType result = costTypeService.update(1L, updatedDetails);

        // Assert
        assertEquals(1L, result.getId()); // ID should remain the same
        assertEquals("Updated Type", result.getType());
        assertEquals(5, result.getSequenceNo());
        verify(costTypeRepository, times(1)).findById(1L);
        verify(costTypeRepository, times(1)).save(sampleCostType);
    }
}