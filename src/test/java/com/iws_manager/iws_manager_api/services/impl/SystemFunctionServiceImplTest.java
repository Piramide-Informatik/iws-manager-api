package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.ApprovalStatus;
import com.iws_manager.iws_manager_api.models.SystemFunction;
import com.iws_manager.iws_manager_api.repositories.ApprovalStatusRepository;
import com.iws_manager.iws_manager_api.repositories.SystemFunctionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("SystemFunction Service Implementation Tests")
class SystemFunctionServiceImplTest {

    private static final String FUNCTION_A = "function A";
    private static final String FUNCTION_B = "function B";

    @Mock
    private SystemFunctionRepository systemFunctionRepository;

    @InjectMocks
    private SystemFunctionServiceImpl systemFunctionService;
    private SystemFunction sampleSystemFunction;

    @BeforeEach
    void setUp() {
        sampleSystemFunction = new SystemFunction();
        sampleSystemFunction.setId(1L);
        sampleSystemFunction.setFunctionName(FUNCTION_A);
    }

    @Test
    @DisplayName("Should save SystemFunction successfully")
    void creatShouldReturnSavedSystemFunction(){
        when(systemFunctionRepository.save(any(SystemFunction.class))).thenReturn(sampleSystemFunction);

        SystemFunction result = systemFunctionService.create(sampleSystemFunction);

        assertNotNull(result);
        assertEquals(FUNCTION_A, result.getFunctionName());
        verify(systemFunctionRepository, times(1)).save(any(SystemFunction.class));
    }

    @Test
    @DisplayName("Should throw exception when creating null SystemFunction")
    void createShouldThrowExceptionWhenSystemFunctionIsNull() {
        assertThrows(IllegalArgumentException.class, () -> systemFunctionService.create(null));
        verify(systemFunctionRepository, never()).save(any());

    }

    @Test
    @DisplayName("Should find SystemFunction by ID")
    void findByIdShouldReturnSystemFunctionWhen() {
        when(systemFunctionRepository.findById(1L)).thenReturn(Optional.of(sampleSystemFunction));

        Optional<SystemFunction> result = systemFunctionService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(FUNCTION_A, result.get().getFunctionName());
        verify(systemFunctionRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return all SystemFunction")
    void findAllShouldReturnAllSystemFunction() {
        SystemFunction systemFunction2 = new SystemFunction();
        systemFunction2.setId(2L);
        systemFunction2.setFunctionName(FUNCTION_A);

        when(systemFunctionRepository.findAll()).thenReturn(Arrays.asList(sampleSystemFunction,systemFunction2));

        List<SystemFunction> result = systemFunctionService.findAll();

        assertEquals(2, result.size());
        verify(systemFunctionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should update SystemFunction successfully")
    void updateShouldReturnUpdatedSystemFunction() {
        SystemFunction updatedDetails = new SystemFunction();
        updatedDetails.setFunctionName(FUNCTION_A+" Updated");

        when(systemFunctionRepository.findById(1L)).thenReturn(Optional.of(sampleSystemFunction));
        when(systemFunctionRepository.save(any(SystemFunction.class))).thenAnswer(inv -> inv.getArgument(0));

        SystemFunction result = systemFunctionService.update(1L, updatedDetails);

        assertEquals("function A Updated", result.getFunctionName());
        verify(systemFunctionRepository, times(1)).findById(1L);
        verify(systemFunctionRepository, times(1)).save(any(SystemFunction.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent SystemFunction")
    void updateShouldThrowExceptionWhenSystemFunctionNotFound() {
        when(systemFunctionRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> systemFunctionService.update(99L, new SystemFunction()));
        verify(systemFunctionRepository, never()).save(any());
    }

    @Test
    public void updateShouldThrowExceptioWhenOptimisticLockingFails() {
        Long systemFunctionId = 1L;
        SystemFunction currentSystemFunction = new SystemFunction();
        currentSystemFunction.setId(systemFunctionId);
        currentSystemFunction.setFunctionName(FUNCTION_A);
        currentSystemFunction.setVersion(2L);

        SystemFunction outSystemFunction = new SystemFunction();
        outSystemFunction.setId(systemFunctionId);
        outSystemFunction.setFunctionName(FUNCTION_B);
        outSystemFunction.setVersion(1L);

        when(systemFunctionRepository.findById(systemFunctionId)).thenReturn(Optional.of(currentSystemFunction));
        when(systemFunctionRepository.save(any(SystemFunction.class)))
                .thenThrow(new ObjectOptimisticLockingFailureException("Concurrent modification detected",
                        new ObjectOptimisticLockingFailureException(ApprovalStatus.class, systemFunctionId)));

        Exception exception = assertThrows(RuntimeException.class, () -> systemFunctionService.update(systemFunctionId,outSystemFunction));

        assertNotNull(exception, "An exception should have been thrown");

        if (!(exception instanceof  ObjectOptimisticLockingFailureException)) {
            assertNotNull(exception.getCause(), "The exception should have a cause");
            assertTrue(exception.getCause() instanceof  ObjectOptimisticLockingFailureException,
                    "The cause should be ObjectOptimisticLockingFailureException");
        }

        verify(systemFunctionRepository).findById(systemFunctionId);
        verify(systemFunctionRepository).save(any(SystemFunction.class));
    }
}