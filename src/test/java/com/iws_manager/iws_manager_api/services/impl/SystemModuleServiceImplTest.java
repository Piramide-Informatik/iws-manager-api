package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.ApprovalStatus;
import com.iws_manager.iws_manager_api.models.SystemModule;
import com.iws_manager.iws_manager_api.repositories.SystemModuleRepository;
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
@DisplayName("SystemModule Implementation Tests")
public
class SystemModuleServiceImplTest {
    private static final String MODULE_A = "module A";
    private static final String MODULE_B = "module B";

    @Mock
    private SystemModuleRepository systemModuleRepository;

    @InjectMocks
    private SystemModuleServiceImpl systemModuleService;
    private SystemModule sampleSystemModule;

    @BeforeEach
    void setUp() {
        sampleSystemModule = new SystemModule();
        sampleSystemModule.setId(1L);
        sampleSystemModule.setName(MODULE_A);
    }

    @Test
    @DisplayName("Should save SystemModule successfully")
    void creatShouldReturnSavedSystemModule(){
        when(systemModuleRepository.save(any(SystemModule.class))).thenReturn(sampleSystemModule);

        SystemModule result = systemModuleService.create(sampleSystemModule);

        assertNotNull(result);
        assertEquals(MODULE_A, result.getName());
        verify(systemModuleRepository, times(1)).save(any(SystemModule.class));
    }

    @Test
    @DisplayName("Should throw exception when creating null SystemModule")
    void createShouldThrowExceptionWhenSystemModuleIsNull() {
        assertThrows(IllegalArgumentException.class, () -> systemModuleService.create(null));
        verify(systemModuleRepository, never()).save(any());

    }

    @Test
    @DisplayName("Should find SystemModule by ID")
    void findByIdShouldReturnSystemModuleWhen() {
        when(systemModuleRepository.findById(1L)).thenReturn(Optional.of(sampleSystemModule));

        Optional<SystemModule> result = systemModuleService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(MODULE_A, result.get().getName());
        verify(systemModuleRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return all SystemModule")
    void findAllShouldReturnAllSystemModule() {
        SystemModule systemModule2 = new SystemModule();
        systemModule2.setId(2L);
        systemModule2.setName(MODULE_B);

        when(systemModuleRepository.findAllByOrderByNameAsc()).thenReturn(Arrays.asList(sampleSystemModule,systemModule2));

        List<SystemModule> result = systemModuleService.findAll();

        assertEquals(2, result.size());
        verify(systemModuleRepository, times(1)).findAllByOrderByNameAsc();
    }

    @Test
    @DisplayName("Should update SystemModule successfully")
    void updateShouldReturnUpdatedSystemModule() {
        SystemModule updatedDetails = new SystemModule();
        updatedDetails.setName(MODULE_A+" Updated");

        when(systemModuleRepository.findById(1L)).thenReturn(Optional.of(sampleSystemModule));
        when(systemModuleRepository.save(any(SystemModule.class))).thenAnswer(inv -> inv.getArgument(0));

        SystemModule result = systemModuleService.update(1L, updatedDetails);

        assertEquals("module A Updated", result.getName());
        verify(systemModuleRepository, times(1)).findById(1L);
        verify(systemModuleRepository, times(1)).save(any(SystemModule.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent SystemModule")
    void updateShouldThrowExceptionWhenSystemModuleNotFound() {
        when(systemModuleRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> systemModuleService.update(99L, new SystemModule()));
        verify(systemModuleRepository, never()).save(any());
    }

    @Test
    public void updateShouldThrowExceptioWhenOptimisticLockingFails() {
        Long systemModuleId = 1L;
        SystemModule currentSystemModule = new SystemModule();
        currentSystemModule.setId(systemModuleId);
        currentSystemModule.setName(MODULE_A);
        currentSystemModule.setVersion(2L);

        SystemModule outSystemModule = new SystemModule();
        outSystemModule.setId(systemModuleId);
        outSystemModule.setName(MODULE_B);
        outSystemModule.setVersion(1L);

        when(systemModuleRepository.findById(systemModuleId)).thenReturn(Optional.of(currentSystemModule));
        when(systemModuleRepository.save(any(SystemModule.class)))
                .thenThrow(new ObjectOptimisticLockingFailureException("Concurrent modification detected",
                        new ObjectOptimisticLockingFailureException(ApprovalStatus.class, systemModuleId)));

        Exception exception = assertThrows(RuntimeException.class, () -> systemModuleService.update(systemModuleId,outSystemModule));

        assertNotNull(exception, "An exception should have been thrown");

        if (!(exception instanceof  ObjectOptimisticLockingFailureException)) {
            assertNotNull(exception.getCause(), "The exception should have a cause");
            assertTrue(exception.getCause() instanceof  ObjectOptimisticLockingFailureException,
                    "The cause should be ObjectOptimisticLockingFailureException");
        }

        verify(systemModuleRepository).findById(systemModuleId);
        verify(systemModuleRepository).save(any(SystemModule.class));
    }
}