package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.ReminderLevel;
import com.iws_manager.iws_manager_api.repositories.ReminderLevelRepository;
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
class ReminderLevelServiceImplTest {

    @Mock
    private ReminderLevelRepository reminderLevelRepository;

    @InjectMocks
    private ReminderLevelServiceImpl reminderLevelService;

    private ReminderLevel reminderLevel;

    @BeforeEach
    void setUp() {
        reminderLevel = new ReminderLevel();
        reminderLevel.setFee(new BigDecimal("15.50"));
        reminderLevel.setInterestRate(new BigDecimal("2.50"));
        reminderLevel.setLevelNo((short) 1);
        reminderLevel.setPayPeriod((short) 14);
        reminderLevel.setReminderText("First reminder text");
        reminderLevel.setReminderTitle("1. Mahnung");
    }

    @Test
    void createShouldSaveReminderLevel() {
        when(reminderLevelRepository.save(reminderLevel)).thenReturn(reminderLevel);

        ReminderLevel result = reminderLevelService.create(reminderLevel);

        assertEquals(reminderLevel, result);
        verify(reminderLevelRepository).save(reminderLevel);
    }

    @Test
    void createShouldThrowWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> reminderLevelService.create(null));
    }

    @Test
    void findByIdShouldReturnReminderLevel() {
        when(reminderLevelRepository.findById(1L)).thenReturn(Optional.of(reminderLevel));

        Optional<ReminderLevel> result = reminderLevelService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(reminderLevel, result.get());
    }

    @Test
    void findByIdShouldThrowWhenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> reminderLevelService.findById(null));
    }

    @Test
    void findAllShouldReturnOrderedList() {
        when(reminderLevelRepository.findAllByOrderByLevelNoAsc()).thenReturn(List.of(reminderLevel));

        List<ReminderLevel> result = reminderLevelService.findAll();

        assertEquals(1, result.size());
        verify(reminderLevelRepository).findAllByOrderByLevelNoAsc();
    }

    @Test
    void updateShouldModifyAndSaveReminderLevel() {
        ReminderLevel updated = new ReminderLevel();
        updated.setFee(new BigDecimal("25.75"));
        updated.setInterestRate(new BigDecimal("3.25"));
        updated.setLevelNo((short) 2);
        updated.setPayPeriod((short) 21);
        updated.setReminderText("Updated reminder text");
        updated.setReminderTitle("2. Mahnung");

        when(reminderLevelRepository.findById(1L)).thenReturn(Optional.of(reminderLevel));
        when(reminderLevelRepository.save(any(ReminderLevel.class))).thenReturn(updated);

        ReminderLevel result = reminderLevelService.update(1L, updated);

        assertEquals(new BigDecimal("25.75"), result.getFee());
        assertEquals(new BigDecimal("3.25"), result.getInterestRate());
        assertEquals((short) 2, result.getLevelNo());
        verify(reminderLevelRepository).save(any(ReminderLevel.class));
    }

    @Test
    void updateShouldThrowWhenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> reminderLevelService.update(null, reminderLevel));
    }

    @Test
    void updateShouldThrowWhenDetailsNull() {
        assertThrows(IllegalArgumentException.class, () -> reminderLevelService.update(1L, null));
    }

    @Test
    void updateShouldThrowWhenNotFound() {
        when(reminderLevelRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> reminderLevelService.update(99L, reminderLevel));
    }

    @Test
    void deleteShouldCallRepository() {
        when(reminderLevelRepository.existsById(1L)).thenReturn(true);
        
        reminderLevelService.delete(1L);
        
        verify(reminderLevelRepository).existsById(1L);
        verify(reminderLevelRepository).deleteById(1L);
    }

    @Test
    void deleteShouldThrowWhenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> reminderLevelService.delete(null));
    }

    @Test
    void deleteShouldThrowWhenNotFound() {
        when(reminderLevelRepository.existsById(99L)).thenReturn(false);
        
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, 
            () -> reminderLevelService.delete(99L));
        
        assertEquals("ReminderLevel not found with id: 99", exception.getMessage());
        verify(reminderLevelRepository).existsById(99L);
        verify(reminderLevelRepository, never()).deleteById(any());
    }

    @Test
    void findAllShouldReturnEmptyListWhenNoData() {
        when(reminderLevelRepository.findAllByOrderByLevelNoAsc()).thenReturn(Collections.emptyList());

        List<ReminderLevel> result = reminderLevelService.findAll();

        assertTrue(result.isEmpty());
        verify(reminderLevelRepository).findAllByOrderByLevelNoAsc();
    }

    @Test
    void updateShouldUpdateAllFields() {
        ReminderLevel updatedDetails = new ReminderLevel();
        updatedDetails.setFee(new BigDecimal("30.00"));
        updatedDetails.setInterestRate(new BigDecimal("4.00"));
        updatedDetails.setLevelNo((short) 3);
        updatedDetails.setPayPeriod((short) 30);
        updatedDetails.setReminderText("New reminder text");
        updatedDetails.setReminderTitle("3. Mahnung");

        when(reminderLevelRepository.findById(1L)).thenReturn(Optional.of(reminderLevel));
        when(reminderLevelRepository.save(any(ReminderLevel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ReminderLevel result = reminderLevelService.update(1L, updatedDetails);

        assertEquals(new BigDecimal("30.00"), result.getFee());
        assertEquals(new BigDecimal("4.00"), result.getInterestRate());
        assertEquals((short) 3, result.getLevelNo());
        assertEquals((short) 30, result.getPayPeriod());
        assertEquals("New reminder text", result.getReminderText());
        assertEquals("3. Mahnung", result.getReminderTitle());
    }
}