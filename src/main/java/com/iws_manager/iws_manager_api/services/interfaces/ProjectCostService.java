package com.iws_manager.iws_manager_api.services.interfaces;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.ProjectCost;

public interface ProjectCostService {

    // Basic CRUD operations
    ProjectCost create(ProjectCost projectCost);

    Optional<ProjectCost> getById(Long id);

    List<ProjectCost> getAll();

    ProjectCost update(Long id, ProjectCost projectCostDetails);

    void delete(Long id);

    // Get operations by project
    List<ProjectCost> getByProjectId(Long projectId);

    List<ProjectCost> getByProjectIdOrderByCostsAsc(Long projectId);

    List<ProjectCost> getByProjectIdOrderByCostsDesc(Long projectId);

    List<ProjectCost> getByProjectIdOrderByProjectPeriodIdAsc(Long projectId);

    List<ProjectCost> getByProjectIdOrderByApproveOrPlanAsc(Long projectId);

    // Get operations by project period
    List<ProjectCost> getByProjectPeriodId(Long projectPeriodId);

    List<ProjectCost> getByProjectPeriodIdOrderByCostsAsc(Long projectPeriodId);

    List<ProjectCost> getByProjectPeriodIdOrderByCostsDesc(Long projectPeriodId);

    // Get operations by type (approved=1 or planned=2)
    List<ProjectCost> getByApproveOrPlan(Byte approveOrPlan);

    List<ProjectCost> getApprovedCosts();

    List<ProjectCost> getPlannedCosts();

    // Get operations by project and period
    List<ProjectCost> getByProjectIdAndProjectPeriodId(Long projectId, Long projectPeriodId);

    List<ProjectCost> getApprovedCostsByProjectAndPeriod(Long projectId, Long projectPeriodId);

    List<ProjectCost> getPlannedCostsByProjectAndPeriod(Long projectId, Long projectPeriodId);

    // Get operations by project and type
    List<ProjectCost> getByProjectIdAndApproveOrPlan(Long projectId, Byte approveOrPlan);

    List<ProjectCost> getApprovedCostsByProject(Long projectId);

    List<ProjectCost> getPlannedCostsByProject(Long projectId);

    // Get operations by project, period and type
    List<ProjectCost> getByProjectIdAndProjectPeriodIdAndApproveOrPlan(
            Long projectId, Long projectPeriodId, Byte approveOrPlan);

    // Special method for project totals (projectperiodid = 0)
    List<ProjectCost> getProjectTotals(Long projectId);

    List<ProjectCost> getApprovedProjectTotals(Long projectId);

    List<ProjectCost> getPlannedProjectTotals(Long projectId);

    // Sum operations
    BigDecimal getTotalCostsByProject(Long projectId);

    BigDecimal getTotalApprovedCostsByProject(Long projectId);

    BigDecimal getTotalPlannedCostsByProject(Long projectId);

    BigDecimal getTotalCostsByProjectAndPeriod(Long projectId, Long projectPeriodId);

    BigDecimal getTotalApprovedCostsByProjectAndPeriod(Long projectId, Long projectPeriodId);

    BigDecimal getTotalPlannedCostsByProjectAndPeriod(Long projectId, Long projectPeriodId);

    // Check existence operations
    boolean existsByProjectAndPeriod(Long projectId, Long projectPeriodId);

    boolean existsApprovedCostsByProjectAndPeriod(Long projectId, Long projectPeriodId);

    boolean existsPlannedCostsByProjectAndPeriod(Long projectId, Long projectPeriodId);

    // Ordering operations
    List<ProjectCost> getAllOrderByCostsAsc();

    List<ProjectCost> getAllOrderByCostsDesc();

    List<ProjectCost> getAllOrderByProjectIdAsc();

    List<ProjectCost> getAllOrderByProjectPeriodIdAsc();

    // Range operations
    List<ProjectCost> getByCostsGreaterThan(BigDecimal amount);

    List<ProjectCost> getByCostsLessThan(BigDecimal amount);

    List<ProjectCost> getByCostsBetween(BigDecimal minAmount, BigDecimal maxAmount);

    // Validation and business logic
    boolean validateProjectCost(ProjectCost projectCost);

    boolean canCreateProjectCost(Long projectId, Long projectPeriodId, Byte approveOrPlan);

    // Bulk operations
    List<ProjectCost> createAll(List<ProjectCost> projectCosts);

    void deleteByProjectId(Long projectId);

    void deleteByProjectPeriodId(Long projectPeriodId);

    // Utility methods
    Long getMaxId();

    List<ProjectCost> getByProjectIdWithNullCosts(Long projectId);
}