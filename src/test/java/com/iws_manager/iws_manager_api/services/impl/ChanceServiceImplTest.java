package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.Chance;
import com.iws_manager.iws_manager_api.repositories.ChanceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChanceServiceImplTest {

    @Mock
    private ChanceRepository chanceRepository;

    @InjectMocks
    private ChanceServiceImpl chanceService;

    private Chance chance;

    @BeforeEach
    void setUp() {
        chance = new Chance();
        chance.setProbability(new BigDecimal("75.50"));
    }

    @Test
    void createShouldSaveChance() {
        when(chanceRepository.save(chance)).thenReturn(chance);

        Chance result = chanceService.create(chance);

        assertEquals(chance, result);
        verify(chanceRepository).save(chance);
    }

    @Test
    void createShouldThrowWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> chanceService.create(null));
    }

    @Test
    void findByIdShouldReturnChance() {
        when(chanceRepository.findById(1L)).thenReturn(Optional.of(chance));

        Optional<Chance> result = chanceService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(chance, result.get());
    }

    @Test
    void findByIdShouldThrowWhenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> chanceService.findById(null));
    }

    @Test
    void findAllShouldReturnOrderedList() {
        when(chanceRepository.findAllByOrderByProbabilityAsc()).thenReturn(List.of(chance));

        List<Chance> result = chanceService.findAll();

        assertEquals(1, result.size());
        verify(chanceRepository).findAllByOrderByProbabilityAsc();
    }

    @Test
    void updateShouldModifyAndSaveChance() {
        Chance updated = new Chance();
        updated.setProbability(new BigDecimal("90.25"));

        when(chanceRepository.findById(1L)).thenReturn(Optional.of(chance));
        when(chanceRepository.save(any(Chance.class))).thenReturn(updated);

        Chance result = chanceService.update(1L, updated);

        assertEquals(new BigDecimal("90.25"), result.getProbability());
        verify(chanceRepository).save(any(Chance.class));
    }

    @Test
    void updateShouldThrowWhenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> chanceService.update(null, chance));
    }

    @Test
    void updateShouldThrowWhenDetailsNull() {
        assertThrows(IllegalArgumentException.class, () -> chanceService.update(1L, null));
    }

    @Test
    void updateShouldThrowWhenNotFound() {
        when(chanceRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> chanceService.update(99L, chance));
    }

    @Test
    void deleteShouldCallRepository() {
        when(chanceRepository.existsById(1L)).thenReturn(true);
        
        chanceService.delete(1L);
        
        verify(chanceRepository).existsById(1L);
        verify(chanceRepository).deleteById(1L);
    }

    @Test
    void deleteShouldThrowWhenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> chanceService.delete(null));
    }

    @Test
    void deleteShouldThrowWhenNotFound() {
        when(chanceRepository.existsById(99L)).thenReturn(false);
        
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, 
            () -> chanceService.delete(99L));
        
        assertEquals("Chance not found with id: 99", exception.getMessage());
        verify(chanceRepository).existsById(99L);
        verify(chanceRepository, never()).deleteById(any());
    }

    @Test
    void findAllShouldReturnEmptyListWhenNoData() {
        when(chanceRepository.findAllByOrderByProbabilityAsc()).thenReturn(Collections.emptyList());

        List<Chance> result = chanceService.findAll();

        assertTrue(result.isEmpty());
        verify(chanceRepository).findAllByOrderByProbabilityAsc();
    }

    @Test
    void updateShouldUpdateProbabilityField() {
        Chance updatedDetails = new Chance();
        updatedDetails.setProbability(new BigDecimal("99.99"));

        when(chanceRepository.findById(1L)).thenReturn(Optional.of(chance));
        when(chanceRepository.save(any(Chance.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Chance result = chanceService.update(1L, updatedDetails);

        assertEquals(new BigDecimal("99.99"), result.getProbability());
    }

    @Test
    void createShouldPersistChanceWithNullProbability() {
        Chance chanceWithNullProbability = new Chance();
        chanceWithNullProbability.setProbability(null);

        when(chanceRepository.save(chanceWithNullProbability)).thenReturn(chanceWithNullProbability);

        Chance result = chanceService.create(chanceWithNullProbability);

        assertNull(result.getProbability());
        verify(chanceRepository).save(chanceWithNullProbability);
    }
}