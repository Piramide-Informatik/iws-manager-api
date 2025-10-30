package com.iws_manager.iws_manager_api.repositories;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.iws_manager.iws_manager_api.models.SubcontractProject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcontractProjectRepository extends JpaRepository<SubcontractProject, Long> {
    
    // Find by amount (as string)
    @EntityGraph(attributePaths = {"subcontractYear", "project", "subcontract"})
    List<SubcontractProject> findByAmount(BigDecimal amount);
    
    // Find by share percentage
    @EntityGraph(attributePaths = {"subcontractYear", "project", "subcontract"})
    List<SubcontractProject> findByShare(BigDecimal share);
    
    // Find by subcontract year ID
    @EntityGraph(attributePaths = {"subcontractYear", "project", "subcontract"})
    List<SubcontractProject> findBySubcontractYearId(Long subcontractYearId);
    
    // Find by project ID
    @EntityGraph(attributePaths = {"subcontractYear", "project", "subcontract"})
    List<SubcontractProject> findByProjectId(Long projectId);
    
    // Find by subcontract ID
    @Query("SELECT sp FROM SubcontractProject sp WHERE sp.subcontract.id = :subcontractId ORDER BY sp.project.projectLabel ASC, sp.amount ASC")    
    List<SubcontractProject> findBySubcontractIdOrdered(@Param("subcontractId") Long subcontractId);
    // @EntityGraph(attributePaths = {"subcontractYear", "project", "subcontract"})
    // List<SubcontractProject> findBySubcontractId(Long subcontractId);
    
    // Find by share percentage range (inclusive)
    @EntityGraph(attributePaths = {"subcontractYear", "project", "subcontract"})
    List<SubcontractProject> findByShareBetween(BigDecimal start, BigDecimal end);
    
}