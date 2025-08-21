package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.ApprovalStatus;
import com.iws_manager.iws_manager_api.models.RoleRight;
import com.iws_manager.iws_manager_api.repositories.RoleRightRepository;
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
@DisplayName("RoleRigth Service Implementation Tests")
public class RoleRigthServiceImplTest {
    private static final Integer NUM_ACCESSA = 1;
    private static final Integer NUM_ACCESSB = 3;

    @Mock
    private RoleRightRepository roleRightRepository;

    @InjectMocks
    private RoleRightServiceImpl roleRightService;
    private RoleRight sampleRoleRight;

    @BeforeEach
    void setUp() {
        sampleRoleRight = new RoleRight();
        sampleRoleRight.setId(1L);
        sampleRoleRight.setAccessRight(NUM_ACCESSA);
    }

    @Test
    @DisplayName("Should save approvalstatus successfully")
    void creatShouldReturnSavedApprovalStatus(){
        when(roleRightRepository.save(any(RoleRight.class))).thenReturn(sampleRoleRight);

        RoleRight result = roleRightService.create(sampleRoleRight);

        assertNotNull(result);
        assertEquals(NUM_ACCESSA, result.getAccessRight());
        verify(roleRightRepository, times(1)).save(any(RoleRight.class));
    }

    @Test
    @DisplayName("Should throw exception when creating null roleRight")
    void createShouldThrowExceptionWhenRoleRightIsNull() {
        assertThrows(IllegalArgumentException.class, () -> roleRightService.create(null));
        verify(roleRightRepository, never()).save(any());

    }


    @Test
    @DisplayName("Should find roleRight by ID")
    void findByIdShouldReturnRoleRightWhen() {
        when(roleRightRepository.findById(1L)).thenReturn(Optional.of(sampleRoleRight));

        Optional<RoleRight> result = roleRightService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(NUM_ACCESSA, result.get().getAccessRight());
        verify(roleRightRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return all roleRight")
    void findAllShouldReturnAllRoleRight() {
        RoleRight roleRight2 = new RoleRight();
        roleRight2.setId(2L);
        roleRight2.setAccessRight(NUM_ACCESSB);

        when(roleRightRepository.findAll()).thenReturn(Arrays.asList(sampleRoleRight,roleRight2));

        List<RoleRight> result = roleRightService.findAll();

        assertEquals(2, result.size());
        verify(roleRightRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should update roleRight successfully")
    void updateShouldReturnUpdatedRoleRight() {
        RoleRight updatedDetails = new RoleRight();
        updatedDetails.setAccessRight(6);

        when(roleRightRepository.findById(1L)).thenReturn(Optional.of(sampleRoleRight));
        when(roleRightRepository.save(any(RoleRight.class))).thenAnswer(inv -> inv.getArgument(0));

        RoleRight result = roleRightService.update(1L, updatedDetails);

        assertEquals(6, result.getAccessRight());
        verify(roleRightRepository, times(1)).findById(1L);
        verify(roleRightRepository, times(1)).save(any(RoleRight.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent roleRight")
    void updateShouldThrowExceptionWhenRoleRightNotFound() {
        when(roleRightRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> roleRightService.update(99L, new RoleRight()));
        verify(roleRightRepository, never()).save(any());
    }

    @Test
    public void updateShouldThrowExceptioWhenOptimisticLockingFails() {
        Long roleRightId = 1L;
        RoleRight currentRoleRight = new RoleRight();
        currentRoleRight.setId(roleRightId);
        currentRoleRight.setAccessRight(NUM_ACCESSA);
        currentRoleRight.setVersion(2L);

        RoleRight outRoleRight = new RoleRight();
        outRoleRight.setId(roleRightId);
        outRoleRight.setAccessRight(NUM_ACCESSB);
        outRoleRight.setVersion(1L);

        when(roleRightRepository.findById(roleRightId)).thenReturn(Optional.of(currentRoleRight));
        when(roleRightRepository.save(any(RoleRight.class)))
                .thenThrow(new ObjectOptimisticLockingFailureException("Concurrent modification detected",
                        new ObjectOptimisticLockingFailureException(ApprovalStatus.class, roleRightId)));

        Exception exception = assertThrows(RuntimeException.class, () -> roleRightService.update(roleRightId,outRoleRight));

        assertNotNull(exception, "An exception should have been thrown");

        if (!(exception instanceof  ObjectOptimisticLockingFailureException)) {
            assertNotNull(exception.getCause(), "The exception should have a cause");
            assertTrue(exception.getCause() instanceof  ObjectOptimisticLockingFailureException,
                    "The cause should be ObjectOptimisticLockingFailureException");
        }

        verify(roleRightRepository).findById(roleRightId);
        verify(roleRightRepository).save(any(RoleRight.class));
    }
}
