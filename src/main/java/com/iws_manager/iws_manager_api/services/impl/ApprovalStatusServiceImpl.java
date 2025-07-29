package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.ApprovalStatus;
import com.iws_manager.iws_manager_api.repositories.ApprovalStatusRepository;
import com.iws_manager.iws_manager_api.services.interfaces.ApprovalStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ApprovalStatusServiceImpl implements ApprovalStatusService {
    private final ApprovalStatusRepository approvalStatusRepository;

    @Autowired
    public ApprovalStatusServiceImpl(ApprovalStatusRepository approvalStatusRepository) {
        this.approvalStatusRepository = approvalStatusRepository;
    }

    @Override
    public ApprovalStatus create(ApprovalStatus approvalStatus) {
        if (approvalStatus == null) {
            throw new IllegalArgumentException("ApprovalStatus cannot be null");
        }
        return approvalStatusRepository.save(approvalStatus);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApprovalStatus> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return approvalStatusRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApprovalStatus> findAll() {
        return approvalStatusRepository.findAll();
    }

    @Override
    public ApprovalStatus update(Long id, ApprovalStatus approvalStatusDetails) {
        if (id==null || approvalStatusDetails ==null) {
            throw new IllegalArgumentException("Id and Details cannot be null");
        }
        return approvalStatusRepository.findById(id)
                .map(existingApprovalStatus -> {
                    existingApprovalStatus.setStatus(approvalStatusDetails.getStatus());
                    existingApprovalStatus.setForProjects(approvalStatusDetails.getForProjects());
                    existingApprovalStatus.setForNetworks(approvalStatusDetails.getForNetworks());
                    existingApprovalStatus.setSequenceNo(approvalStatusDetails.getSequenceNo());

                    return  approvalStatusRepository.save(existingApprovalStatus);
                }).orElseThrow(() -> new RuntimeException("ApprovalStatus not found with id: "+id));
    }

    @Override
    public void delete(Long id) {
        if (id == null ) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        approvalStatusRepository.deleteById(id);
    }
}
