package com.iws_manager.iws_manager_api.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.iws_manager.iws_manager_api.models.State;
import com.iws_manager.iws_manager_api.repositories.StateRepository;
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
@DisplayName("State Service Implementation Tests")
class StateServiceImplTest {

    @Mock
    private StateRepository stateRepository;

    @InjectMocks
    private StateServiceImpl stateService;

    private State sampleState;

    @BeforeEach
    void setUp() {
        sampleState = new State();
        sampleState.setId(1L);
        sampleState.setName("Mr.");
    }

    @Test
    @DisplayName("Should save State successfully")
    void createShouldReturnSavedState() {
        // Arrange
        when(stateRepository.save(any(State.class))).thenReturn(sampleState);

        // Act
        State result = stateService.create(sampleState);

        // Assert
        assertNotNull(result);
        assertEquals("Mr.", result.getName());
        verify(stateRepository, times(1)).save(any(State.class));
    }

    @Test
    @DisplayName("Should throw exception when creating null state")
    void createShouldThrowExceptionWhenStateIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> stateService.create(null));
        verify(stateRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should find state by ID")
    void findByIdShouldReturnStateWhenExists() {
        // Arrange
        when(stateRepository.findById(1L)).thenReturn(Optional.of(sampleState));

        // Act
        Optional<State> result = stateService.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Mr.", result.get().getName());
        verify(stateRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return empty when state not found")
    void findByIdShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(stateRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<State> result = stateService.findById(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(stateRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Should throw exception when finding with null ID")
    void findByIdShouldThrowExceptionWhenIdIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> stateService.findById(null));
        verify(stateRepository, never()).findById(any());
    }

    @Test
    @DisplayName("Should return all states")
    void findAllShouldReturnAllStates() {
        // Arrange
        State state2 = new State();
        state2.setId(2L);
        state2.setName("Mrs.");
        
        when(stateRepository.findAll()).thenReturn(Arrays.asList(sampleState, state2));

        // Act
        List<State> result = stateService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(stateRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should update state successfully")
    void updateShouldReturnUpdatedState() {
        // Arrange
        State updatedDetails = new State();
        updatedDetails.setName("Mr. Updated");

        when(stateRepository.findById(1L)).thenReturn(Optional.of(sampleState));
        when(stateRepository.save(any(State.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        State result = stateService.update(1L, updatedDetails);

        // Assert
        assertEquals("Mr. Updated", result.getName());
        verify(stateRepository, times(1)).findById(1L);
        verify(stateRepository, times(1)).save(any(State.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent state")
    void updateShouldThrowExceptionWhenStateNotFound() {
        // Arrange
        when(stateRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, 
            () -> stateService.update(99L, new State()));
        verify(stateRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should delete state successfully")
    void deleteShouldExecuteDelete() {
        // Arrange
        doNothing().when(stateRepository).deleteById(1L);

        // Act
        stateService.delete(1L);

        // Assert
        verify(stateRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw exception when deleting with null ID")
    void deleteShouldThrowExceptionWhenIdIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> stateService.delete(null));
        verify(stateRepository, never()).deleteById(any());
    }
}
