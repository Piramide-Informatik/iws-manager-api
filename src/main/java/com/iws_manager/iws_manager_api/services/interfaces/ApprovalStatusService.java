package com.iws_manager.iws_manager_api.services.interfaces;

import com.iws_manager.iws_manager_api.models.ApprovalStatus;

import java.util.List;
import java.util.Optional;

public interface ApprovalStatusService {
    ApprovalStatus create(ApprovalStatus approvalStatus);
    Optional<ApprovalStatus> findById(Long id);
    List<ApprovalStatus> findAll();
    ApprovalStatus update(Long id, ApprovalStatus approvalStatusDetails);
    void delete(Long id);
}
