package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.IwsCommission;
import com.iws_manager.iws_manager_api.repositories.IwsCommissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("IwsCommission Service Implementation Tests")
class IwsCommissionServiceImplTest {

    @Mock
    private IwsCommissionRepository iwsCommissionRepository;

    @InjectMocks
    private IwsCommissionServiceImpl iwsCommissionService;

    private IwsCommission sampleIwsCommission;
    private BigDecimal commission;
    @BeforeEach
    void setUp() {
        commission = BigDecimal.valueOf(2);
        sampleIwsCommission = new IwsCommission();
        sampleIwsCommission.setId(1L);
        sampleIwsCommission.setCommission(commission);
    }

    @Test
    @DisplayName("Should save IwsCommission successfully")
    void createShouldReturnSavedIwsCommission() {
        // Arrange
        when(iwsCommissionRepository.save(any(IwsCommission.class))).thenReturn(sampleIwsCommission);

        // Act
        IwsCommission result = iwsCommissionService.create(sampleIwsCommission);

        // Assert
        assertNotNull(result);
        assertEquals(commission, result.getCommission());
        verify(iwsCommissionRepository, times(1)).save(any(IwsCommission.class));
    }

    @Test
    @DisplayName("Should throw exception when creating null IwsCommission")
    void createShouldThrowExceptionWhenIwsCommissionIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> iwsCommissionService.create(null));
        verify(iwsCommissionRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should find IwsCommission by ID")
    void findByIdShouldReturnIwsCommissionWhenExists() {
        // Arrange
        when(iwsCommissionService.findById(1L)).thenReturn(Optional.of(sampleIwsCommission));

        // Act
        Optional<IwsCommission> result = iwsCommissionService.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(commission, result.get().getCommission());
        verify(iwsCommissionRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return empty when IwsCommission not found")
    void findByIdShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(iwsCommissionRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<IwsCommission> result = iwsCommissionService.findById(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(iwsCommissionRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Should throw exception when finding with null ID")
    void findByIdShouldThrowExceptionWhenIdIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> iwsCommissionService.findById(null));
        verify(iwsCommissionRepository, never()).findById(any());
    }

    @Test
    @DisplayName("Should return all IwsCommissions")
    void findAllShouldReturnAllIwsCommissions() {
        // Arrange
        IwsCommission iwsCommission2 = new IwsCommission();
        iwsCommission2.setId(2L);
        iwsCommission2.setCommission(commission);

        when(iwsCommissionRepository.findAllByOrderByFromOrderValueAsc()).thenReturn(Arrays.asList(sampleIwsCommission, iwsCommission2));

        // Act
        List<IwsCommission> result = iwsCommissionService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(iwsCommissionRepository, times(1)).findAllByOrderByFromOrderValueAsc();
    }

    @Test
    @DisplayName("Should update IwsCommission successfully")
    void updateShouldReturnUpdatedIwsCommission() {
        // Arrange
        IwsCommission updatedDetails = new IwsCommission();
        updatedDetails.setCommission(BigDecimal.valueOf(3.5));

        when(iwsCommissionRepository.findById(1L)).thenReturn(Optional.of(sampleIwsCommission));
        when(iwsCommissionRepository.save(any(IwsCommission.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        IwsCommission result = iwsCommissionService.update(1L, updatedDetails);

        // Assert
        assertEquals(BigDecimal.valueOf(3.5), result.getCommission());
        verify(iwsCommissionRepository, times(1)).findById(1L);
        verify(iwsCommissionRepository, times(1)).save(any(IwsCommission.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent IwsCommission")
    void updateShouldThrowExceptionWhenIwsCommissionNotFound() {
        // Arrange
        when(iwsCommissionRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> iwsCommissionService.update(99L, new IwsCommission()));
        verify(iwsCommissionRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should delete state successfully")
    void deleteShouldExecuteDelete() {
        // Arrange
        when(iwsCommissionRepository.existsById(1L)).thenReturn(true);

        // Act
        iwsCommissionService.delete(1L);

        // Assert
        verify(iwsCommissionRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw exception when deleting with null ID")
    void deleteShouldThrowExceptionWhenIdIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> iwsCommissionService.delete(null));
        verify(iwsCommissionRepository, never()).deleteById(any());
    }

}