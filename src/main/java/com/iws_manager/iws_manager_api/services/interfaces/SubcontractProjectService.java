package com.iws_manager.iws_manager_api.services.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

import com.iws_manager.iws_manager_api.models.SubcontractProject;

public interface SubcontractProjectService {

    SubcontractProject create(SubcontractProject subcontractProject);
    Optional<SubcontractProject> findById(Long id);
    List<SubcontractProject> findAll();
    SubcontractProject update(Long id, SubcontractProject subcontractProject);
    void delete(Long id);

    List<SubcontractProject> getByAmount(BigDecimal amount);
    List<SubcontractProject> getByShare(BigDecimal share);
    List<SubcontractProject> getBySubcontractYearId(Long subcontractYearId);
    List<SubcontractProject> getByProjectId(Long projectId);
    List<SubcontractProject> getBySubcontractId(Long subcontractId);
    List<SubcontractProject> getByShareBetween(BigDecimal start, BigDecimal end);
}
