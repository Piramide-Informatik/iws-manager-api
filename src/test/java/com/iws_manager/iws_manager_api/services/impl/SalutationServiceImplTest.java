package com.iws_manager.iws_manager_api.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.iws_manager.iws_manager_api.models.Salutation;
import com.iws_manager.iws_manager_api.repositories.SalutationRepository;
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
@DisplayName("Salutation Service Implementation Tests")
class SalutationServiceImplTest {

    @Mock
    private SalutationRepository salutationRepository;

    @InjectMocks
    private SalutationServiceImpl salutationService;

    private Salutation sampleSalutation;

    @BeforeEach
    void setUp() {
        sampleSalutation = new Salutation();
        sampleSalutation.setId(1L);
        sampleSalutation.setName("Mr.");
    }

    @Test
    @DisplayName("Should save salutation successfully")
    void createShouldReturnSavedSalutation() {
        // Arrange
        when(salutationRepository.save(any(Salutation.class))).thenReturn(sampleSalutation);

        // Act
        Salutation result = salutationService.create(sampleSalutation);

        // Assert
        assertNotNull(result);
        assertEquals("Mr.", result.getName());
        verify(salutationRepository, times(1)).save(any(Salutation.class));
    }

    @Test
    @DisplayName("Should throw exception when creating null salutation")
    void createShouldThrowExceptionWhenSalutationIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> salutationService.create(null));
        verify(salutationRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should find salutation by ID")
    void findByIdShouldReturnSalutationWhenExists() {
        // Arrange
        when(salutationRepository.findById(1L)).thenReturn(Optional.of(sampleSalutation));

        // Act
        Optional<Salutation> result = salutationService.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Mr.", result.get().getName());
        verify(salutationRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return empty when salutation not found")
    void findByIdShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(salutationRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<Salutation> result = salutationService.findById(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(salutationRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Should throw exception when finding with null ID")
    void findByIdShouldThrowExceptionWhenIdIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> salutationService.findById(null));
        verify(salutationRepository, never()).findById(any());
    }

    @Test
    @DisplayName("Should return all salutations")
    void findAllShouldReturnAllSalutations() {
        // Arrange
        Salutation salutation2 = new Salutation();
        salutation2.setId(2L);
        salutation2.setName("Mrs.");
        
        when(salutationRepository.findAllByOrderByNameAsc()).thenReturn(Arrays.asList(sampleSalutation, salutation2));

        // Act
        List<Salutation> result = salutationService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(salutationRepository, times(1)).findAllByOrderByNameAsc();
    }

    @Test
    @DisplayName("Should get all salutations ordered by name")
    void shouldGetAllSalutationsOrdered() {
        // Arrange
        Salutation mr = new Salutation();
        mr.setName("Mr.");
        
        Salutation mrs = new Salutation();
        mrs.setName("Mrs.");
        
        Salutation dr = new Salutation();
        dr.setName("Dr.");
        
        when(salutationRepository.findAllByOrderByNameAsc())
            .thenReturn(List.of(dr, mr, mrs));

        // Act
        List<Salutation> result = salutationService.findAll();

        // Assert
        assertEquals(3, result.size());
        assertEquals("Dr.", result.get(0).getName());
        assertEquals("Mr.", result.get(1).getName());
        assertEquals("Mrs.", result.get(2).getName());
        verify(salutationRepository, times(1)).findAllByOrderByNameAsc();
    }

    @Test
    @DisplayName("Should update salutation successfully")
    void updateShouldReturnUpdatedSalutation() {
        // Arrange
        Salutation updatedDetails = new Salutation();
        updatedDetails.setName("Mr. Updated");

        when(salutationRepository.findById(1L)).thenReturn(Optional.of(sampleSalutation));
        when(salutationRepository.save(any(Salutation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Salutation result = salutationService.update(1L, updatedDetails);

        // Assert
        assertEquals("Mr. Updated", result.getName());
        verify(salutationRepository, times(1)).findById(1L);
        verify(salutationRepository, times(1)).save(any(Salutation.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent salutation")
    void updateShouldThrowExceptionWhenSalutationNotFound() {
        // Arrange
        when(salutationRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, 
            () -> salutationService.update(99L, new Salutation()));
        verify(salutationRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should delete salutation successfully")
    void deleteShouldExecuteDelete() {
        // Arrange
        doNothing().when(salutationRepository).deleteById(1L);

        // Act
        salutationService.delete(1L);

        // Assert
        verify(salutationRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw exception when deleting with null ID")
    void deleteShouldThrowExceptionWhenIdIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> salutationService.delete(null));
        verify(salutationRepository, never()).deleteById(any());
    }
}
