package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.PayCondition;
import com.iws_manager.iws_manager_api.repositories.PayConditionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PayConditionServiceImplTest {

    private static final String NAME_TEST = "Pay condition 1";
    private static final Integer DL_TEST = 1;

    @Mock
    private PayConditionRepository payConditionRepository;

    @InjectMocks
    private PayConditionServiceImpl payConditionService;

    private PayCondition payCondition;

    @BeforeEach
    void setUp() {
        payCondition = new PayCondition();
        payCondition.setName(NAME_TEST);
        payCondition.setDeadline(DL_TEST);
    }

    @Test
    void createShouldSavePayCondition() {
        when(payConditionRepository.save(payCondition)).thenReturn(payCondition);

        PayCondition result = payConditionService.create(payCondition);

        assertEquals(payCondition, result);
        verify(payConditionRepository).save(payCondition);
    }

    @Test
    void createShouldThrowWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> payConditionService.create(null));
    }

    @Test
    void findByIdShouldReturnPayCondition() {
        when(payConditionRepository.findById(1L)).thenReturn(Optional.of(payCondition));

        Optional<PayCondition> result = payConditionService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(payCondition, result.get());
    }

    @Test
    void findByIdShouldThrowWhenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> payConditionService.findById(null));
    }

    @Test
    void findAllShouldReturnList() {
        when(payConditionRepository.findAllByOrderByNameAsc()).thenReturn(List.of(payCondition));

        List<PayCondition> result = payConditionService.findAll();

        assertEquals(1, result.size());
    }

    @Test
    void updateShouldModifyAndSavePayCondition() {
        PayCondition updated = new PayCondition();
        updated.setName("Pay condition updated");

        when(payConditionRepository.findById(1L)).thenReturn(Optional.of(payCondition));
        when(payConditionRepository.save(any(PayCondition.class))).thenReturn(updated);

        PayCondition result = payConditionService.update(1L, updated);

        assertEquals("Pay condition updated", result.getName());
        verify(payConditionRepository).save(any(PayCondition.class));
    }

    @Test
    void updateShouldThrowWhenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> payConditionService.update(null, payCondition));
    }

    @Test
    void updateShouldThrowWhenDetailsNull() {
        assertThrows(IllegalArgumentException.class, () -> payConditionService.update(1L, null));
    }

    @Test
    void updateShouldThrowWhenNotFound() {
        when(payConditionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> payConditionService.update(99L, payCondition));
    }

    @Test
    void deleteShouldCallRepository() {
        payConditionService.delete(1L);
        verify(payConditionRepository).deleteById(1L);
    }

    @Test
    void deleteShouldThrowWhenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> payConditionService.delete(null));
    }

    @Test
    void getByNameShouldReturnList() {
        when(payConditionRepository.findByName(NAME_TEST)).thenReturn(List.of(payCondition));

        List<PayCondition> result = payConditionService.getByName(NAME_TEST);

        assertEquals(1, result.size());
    }

    @Test
    void getByDeadlineShouldReturnList() {
        when(payConditionRepository.findByDeadline(1)).thenReturn(List.of(payCondition));

        List<PayCondition> result = payConditionService.getByDeadline(1);

        assertEquals(1, result.size());
    }
}
