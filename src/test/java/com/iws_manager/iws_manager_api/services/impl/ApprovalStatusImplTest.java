package com.iws_manager.iws_manager_api.services.impl;


import com.iws_manager.iws_manager_api.models.ApprovalStatus;
import com.iws_manager.iws_manager_api.repositories.ApprovalStatusRepository;
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
@DisplayName("ApprovalStatus Service Implementation Tests")
public class ApprovalStatusImplTest {

    private static final String NAME_APPROVED = "Approved";
    private static final String NAME_REJECTED = "Rejected";

    @Mock
    private ApprovalStatusRepository approvalStatusRepository;

    @InjectMocks
    private ApprovalStatusServiceImpl approvalStatusService;
    private ApprovalStatus sampleApprovalStatus;

    @BeforeEach
    void setUp() {
        sampleApprovalStatus = new ApprovalStatus();
        sampleApprovalStatus.setId(1L);
        sampleApprovalStatus.setStatus(NAME_APPROVED);
    }

    @Test
    @DisplayName("Should save approvalstatus successfully")
    void creatShouldReturnSavedApprovalStatus(){
        when(approvalStatusRepository.save(any(ApprovalStatus.class))).thenReturn(sampleApprovalStatus);

        ApprovalStatus result = approvalStatusService.create(sampleApprovalStatus);

        assertNotNull(result);
        assertEquals(NAME_APPROVED, result.getStatus());
        verify(approvalStatusRepository, times(1)).save(any(ApprovalStatus.class));
    }

    @Test
    @DisplayName("Should throw exception when creating null approvalstatus")
    void createShouldThrowExceptionWhenApprovalStatusIsNull() {
        assertThrows(IllegalArgumentException.class, () -> approvalStatusService.create(null));
        verify(approvalStatusRepository, never()).save(any());

    }
    @Test
    @DisplayName("Should find approvalStatus by ID")
    void findByIdShouldReturnApprovalStatusWhen() {
        when(approvalStatusRepository.findById(1L)).thenReturn(Optional.of(sampleApprovalStatus));

        Optional<ApprovalStatus> result = approvalStatusService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(NAME_APPROVED, result.get().getStatus());
        verify(approvalStatusRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return all approvalstatus")
    void findAllShouldReturnAllApprovalStatus() {
        ApprovalStatus approvalStatus2 = new ApprovalStatus();
        approvalStatus2.setId(2L);
        approvalStatus2.setStatus(NAME_REJECTED);

        when(approvalStatusRepository.findAll()).thenReturn(Arrays.asList(sampleApprovalStatus,approvalStatus2));

        List<ApprovalStatus> result = approvalStatusService.findAll();

        assertEquals(2, result.size());
        verify(approvalStatusRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should update approvalstatus successfully")
    void updateShouldReturnUpdatedApprovalStatus() {
        ApprovalStatus updatedDetails = new ApprovalStatus();
        updatedDetails.setStatus(NAME_APPROVED+" Updated");

        when(approvalStatusRepository.findById(1L)).thenReturn(Optional.of(sampleApprovalStatus));
        when(approvalStatusRepository.save(any(ApprovalStatus.class))).thenAnswer(inv -> inv.getArgument(0));

        ApprovalStatus result = approvalStatusService.update(1L, updatedDetails);

        assertEquals("Approved Updated", result.getStatus());
        verify(approvalStatusRepository, times(1)).findById(1L);
        verify(approvalStatusRepository, times(1)).save(any(ApprovalStatus.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent approvalstatus")
    void updateShouldThrowExceptionWhenApprovalStatusNotFound() {
        when(approvalStatusRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> approvalStatusService.update(99L, new ApprovalStatus()));
        verify(approvalStatusRepository, never()).save(any());
    }

    @Test
    public void updateShouldThrowExceptioWhenOptimisticLockingFails() {
        Long approvalStatusId = 1L;
        ApprovalStatus currentApprovalStatus = new ApprovalStatus();
        currentApprovalStatus.setId(approvalStatusId);
        currentApprovalStatus.setStatus(NAME_APPROVED);
        currentApprovalStatus.setVersion(2L);

        ApprovalStatus outApprovalStatus = new ApprovalStatus();
        outApprovalStatus.setId(approvalStatusId);
        outApprovalStatus.setStatus(NAME_REJECTED);
        outApprovalStatus.setVersion(1L);

        when(approvalStatusRepository.findById(approvalStatusId)).thenReturn(Optional.of(currentApprovalStatus));
        when(approvalStatusRepository.save(any(ApprovalStatus.class)))
                .thenThrow(new ObjectOptimisticLockingFailureException("Concurrent modification detected",
                        new ObjectOptimisticLockingFailureException(ApprovalStatus.class, approvalStatusId)));

        Exception exception = assertThrows(RuntimeException.class, () -> approvalStatusService.update(approvalStatusId,outApprovalStatus));

        assertNotNull(exception, "An exception should have been thrown");

        if (!(exception instanceof  ObjectOptimisticLockingFailureException)) {
            assertNotNull(exception.getCause(), "The exception should have a cause");
            assertTrue(exception.getCause() instanceof  ObjectOptimisticLockingFailureException,
                    "The cause should be ObjectOptimisticLockingFailureException");
        }

        verify(approvalStatusRepository).findById(approvalStatusId);
        verify(approvalStatusRepository).save(any(ApprovalStatus.class));
    }
}
