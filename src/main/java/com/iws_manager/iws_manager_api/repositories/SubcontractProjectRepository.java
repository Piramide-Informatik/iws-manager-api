package com.iws_manager.iws_manager_api.repositories;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.iws_manager.iws_manager_api.models.SubcontractProject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcontractProjectRepository extends JpaRepository<SubcontractProject, Long> {
    
    // Find by amount (as string)
    List<SubcontractProject> findByAmount(BigDecimal amount);
    
    // Find by share percentage
    List<SubcontractProject> findByShare(BigDecimal share);
    
    // Find by subcontract year ID
    List<SubcontractProject> findBySubcontractYearId(Long subcontractYearId);
    
    // Find by project ID
    List<SubcontractProject> findByProjectId(Long projectId);
    
    // Find by subcontract ID
    List<SubcontractProject> findBySubcontractId(Long subcontractId);
    
    // Find by share percentage range (inclusive)
    List<SubcontractProject> findByShareBetween(BigDecimal start, BigDecimal end);
    
}