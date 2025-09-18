package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.SystemParameter;
import com.iws_manager.iws_manager_api.repositories.SystemParameterRepository;
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
class SystemParameterServiceImplTest {

    private static final String PARAMETER_NAME = "TestParameter";
    private static final String CHAR_VALUE = "TestValue";
    private static final BigDecimal NUM_VALUE = new BigDecimal("100.50");

    @Mock
    private SystemParameterRepository systemParameterRepository;

    @InjectMocks
    private SystemParameterServiceImpl systemParameterService;

    private SystemParameter systemParameter;

    @BeforeEach
    void setUp() {
        systemParameter = new SystemParameter();
        systemParameter.setName(PARAMETER_NAME);
        systemParameter.setValueChar(CHAR_VALUE);
        systemParameter.setValueNum(NUM_VALUE);
    }

    @Test
    void createShouldSaveSystemParameter() {
        when(systemParameterRepository.save(systemParameter)).thenReturn(systemParameter);

        SystemParameter result = systemParameterService.create(systemParameter);

        assertEquals(systemParameter, result);
        verify(systemParameterRepository).save(systemParameter);
    }

    @Test
    void createShouldThrowWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> systemParameterService.create(null));
    }

    @Test
    void findByIdShouldReturnSystemParameter() {
        when(systemParameterRepository.findById(1L)).thenReturn(Optional.of(systemParameter));

        Optional<SystemParameter> result = systemParameterService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(systemParameter, result.get());
    }

    @Test
    void findByIdShouldThrowWhenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> systemParameterService.findById(null));
    }

    @Test
    void findAllShouldReturnList() {
        when(systemParameterRepository.findAllByOrderByNameAsc()).thenReturn(List.of(systemParameter));

        List<SystemParameter> result = systemParameterService.findAll();

        assertEquals(1, result.size());
        assertEquals(PARAMETER_NAME, result.get(0).getName());
    }

    @Test
    void findAllShouldReturnEmptyList() {
        when(systemParameterRepository.findAllByOrderByNameAsc()).thenReturn(Collections.emptyList());

        List<SystemParameter> result = systemParameterService.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void updateShouldModifyAndSaveSystemParameter() {
        SystemParameter updatedDetails = new SystemParameter();
        updatedDetails.setName("UpdatedParameter");
        updatedDetails.setValueChar("UpdatedValue");
        updatedDetails.setValueNum(new BigDecimal("200.75"));

        when(systemParameterRepository.findById(1L)).thenReturn(Optional.of(systemParameter));
        when(systemParameterRepository.save(any(SystemParameter.class))).thenReturn(updatedDetails);

        SystemParameter result = systemParameterService.update(1L, updatedDetails);

        assertEquals("UpdatedParameter", result.getName());
        assertEquals("UpdatedValue", result.getValueChar());
        assertEquals(new BigDecimal("200.75"), result.getValueNum());
        verify(systemParameterRepository).save(any(SystemParameter.class));
    }

    @Test
    void updateShouldThrowWhenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> systemParameterService.update(null, systemParameter));
    }

    @Test
    void updateShouldThrowWhenDetailsNull() {
        assertThrows(IllegalArgumentException.class, () -> systemParameterService.update(1L, null));
    }

    @Test
    void updateShouldThrowWhenNotFound() {
        SystemParameter updatedDetails = new SystemParameter();
        when(systemParameterRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> systemParameterService.update(99L, updatedDetails));
    }

    @Test
    void deleteShouldCallRepository() {
        when(systemParameterRepository.existsById(1L)).thenReturn(true);

        systemParameterService.delete(1L);

        verify(systemParameterRepository).existsById(1L);
        verify(systemParameterRepository).deleteById(1L);
    }

    @Test
    void deleteShouldThrowWhenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> systemParameterService.delete(null));
    }

    @Test
    void deleteShouldThrowWhenNotFound() {
        when(systemParameterRepository.existsById(99L)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(
            EntityNotFoundException.class, 
            () -> systemParameterService.delete(99L)
        );

        assertEquals("Customer not found with id: 99", exception.getMessage());
        verify(systemParameterRepository).existsById(99L);
        verify(systemParameterRepository, never()).deleteById(any());
    }

    @Test
    void updateShouldOnlyModifyAllowedFields() {
        SystemParameter updatedDetails = new SystemParameter();
        updatedDetails.setName("UpdatedName");
        updatedDetails.setValueChar("UpdatedChar");
        updatedDetails.setValueNum(new BigDecimal("999.99"));

        when(systemParameterRepository.findById(1L)).thenReturn(Optional.of(systemParameter));
        when(systemParameterRepository.save(any(SystemParameter.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SystemParameter result = systemParameterService.update(1L, updatedDetails);

        assertEquals("UpdatedName", result.getName());
        assertEquals("UpdatedChar", result.getValueChar());
        assertEquals(new BigDecimal("999.99"), result.getValueNum());
    }

    @Test
    void createShouldPersistAllFields() {
        SystemParameter newParameter = new SystemParameter();
        newParameter.setName("NewParameter");
        newParameter.setValueChar("NewValue");
        newParameter.setValueNum(new BigDecimal("50.25"));

        when(systemParameterRepository.save(newParameter)).thenReturn(newParameter);

        SystemParameter result = systemParameterService.create(newParameter);

        assertEquals("NewParameter", result.getName());
        assertEquals("NewValue", result.getValueChar());
        assertEquals(new BigDecimal("50.25"), result.getValueNum());
    }
}