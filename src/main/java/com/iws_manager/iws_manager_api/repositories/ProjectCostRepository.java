package com.iws_manager.iws_manager_api.repositories;

import java.math.BigDecimal;
import java.util.List;

import com.iws_manager.iws_manager_api.models.ProjectCost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectCostRepository extends JpaRepository<ProjectCost, Long> {

    // Find costs by project (all types and periods)
    @EntityGraph(attributePaths = { "project", "projectPeriod" })
    @Query("SELECT pc FROM ProjectCost pc WHERE pc.project.id = :projectId")
    List<ProjectCost> findByProjectId(@Param("projectId") Long projectId);

    // Find costs by project period
    @EntityGraph(attributePaths = { "project", "projectPeriod" })
    @Query("SELECT pc FROM ProjectCost pc WHERE pc.projectPeriod.id = :projectPeriodId")
    List<ProjectCost> findByProjectPeriodId(@Param("projectPeriodId") Long projectPeriodId);

    // Find costs by type (approved=1 or planned=2)
    @EntityGraph(attributePaths = { "project", "projectPeriod" })
    @Query("SELECT pc FROM ProjectCost pc WHERE pc.approveOrPlan = :type")
    List<ProjectCost> findByApproveOrPlan(@Param("type") Byte type);

    // Find costs by project and period
    @EntityGraph(attributePaths = { "project", "projectPeriod" })
    @Query("SELECT pc FROM ProjectCost pc WHERE pc.project.id = :projectId AND pc.projectPeriod.id = :projectPeriodId")
    List<ProjectCost> findByProjectIdAndProjectPeriodId(
            @Param("projectId") Long projectId,
            @Param("projectPeriodId") Long projectPeriodId);

    // Find costs by project and type
    @EntityGraph(attributePaths = { "project", "projectPeriod" })
    @Query("SELECT pc FROM ProjectCost pc WHERE pc.project.id = :projectId AND pc.approveOrPlan = :type")
    List<ProjectCost> findByProjectIdAndApproveOrPlan(
            @Param("projectId") Long projectId,
            @Param("type") Byte type);

    // Find costs by project, period and type
    @EntityGraph(attributePaths = { "project", "projectPeriod" })
    @Query("SELECT pc FROM ProjectCost pc WHERE pc.project.id = :projectId AND pc.projectPeriod.id = :projectPeriodId AND pc.approveOrPlan = :type")
    List<ProjectCost> findByProjectIdAndProjectPeriodIdAndApproveOrPlan(
            @Param("projectId") Long projectId,
            @Param("projectPeriodId") Long projectPeriodId,
            @Param("type") Byte type);

    // Find PLANNED costs (type=2) by project and period
    @EntityGraph(attributePaths = { "project", "projectPeriod" })
    default List<ProjectCost> findPlannedCostsByProjectAndPeriod(Long projectId, Long projectPeriodId) {
        return findByProjectIdAndProjectPeriodIdAndApproveOrPlan(projectId, projectPeriodId, (byte) 2);
    }

    // Find APPROVED costs (type=1) by project and period
    @EntityGraph(attributePaths = { "project", "projectPeriod" })
    default List<ProjectCost> findApprovedCostsByProjectAndPeriod(Long projectId, Long projectPeriodId) {
        return findByProjectIdAndProjectPeriodIdAndApproveOrPlan(projectId, projectPeriodId, (byte) 1);
    }

    // Método específico para TOTALES del proyecto (projectperiodid = 0)
    @EntityGraph(attributePaths = { "project", "projectPeriod" })
    @Query("SELECT pc FROM ProjectCost pc WHERE pc.project.id = :projectId AND pc.projectPeriod.id = 0")
    List<ProjectCost> findProjectTotals(@Param("projectId") Long projectId);

    // Find PLANNED project totals (type=2, period=0)
    @EntityGraph(attributePaths = { "project", "projectPeriod" })
    @Query("SELECT pc FROM ProjectCost pc WHERE pc.project.id = :projectId AND pc.projectPeriod.id = 0 AND pc.approveOrPlan = 2")
    List<ProjectCost> findPlannedProjectTotals(@Param("projectId") Long projectId);

    // Find APPROVED project totals (type=1, period=0)
    @EntityGraph(attributePaths = { "project", "projectPeriod" })
    @Query("SELECT pc FROM ProjectCost pc WHERE pc.project.id = :projectId AND pc.projectPeriod.id = 0 AND pc.approveOrPlan = 1")
    List<ProjectCost> findApprovedProjectTotals(@Param("projectId") Long projectId);

    // Sum of costs by project and period
    @Query("SELECT COALESCE(SUM(pc.costs), 0) FROM ProjectCost pc WHERE pc.project.id = :projectId AND pc.projectPeriod.id = :projectPeriodId")
    BigDecimal sumCostsByProjectAndPeriod(@Param("projectId") Long projectId,
            @Param("projectPeriodId") Long projectPeriodId);

    // Sum of PLANNED costs by project and period
    @Query("SELECT COALESCE(SUM(pc.costs), 0) FROM ProjectCost pc WHERE pc.project.id = :projectId AND pc.projectPeriod.id = :projectPeriodId AND pc.approveOrPlan = 2")
    BigDecimal sumPlannedCostsByProjectAndPeriod(@Param("projectId") Long projectId,
            @Param("projectPeriodId") Long projectPeriodId);

    // Sum of APPROVED costs by project and period
    @Query("SELECT COALESCE(SUM(pc.costs), 0) FROM ProjectCost pc WHERE pc.project.id = :projectId AND pc.projectPeriod.id = :projectPeriodId AND pc.approveOrPlan = 1")
    BigDecimal sumApprovedCostsByProjectAndPeriod(@Param("projectId") Long projectId,
            @Param("projectPeriodId") Long projectPeriodId);

    // Sum of ALL costs by project (all periods)
    @Query("SELECT COALESCE(SUM(pc.costs), 0) FROM ProjectCost pc WHERE pc.project.id = :projectId")
    BigDecimal sumCostsByProject(@Param("projectId") Long projectId);

    // Sum of PLANNED costs by project (all periods)
    @Query("SELECT COALESCE(SUM(pc.costs), 0) FROM ProjectCost pc WHERE pc.project.id = :projectId AND pc.approveOrPlan = 2")
    BigDecimal sumPlannedCostsByProject(@Param("projectId") Long projectId);

    // Sum of APPROVED costs by project (all periods)
    @Query("SELECT COALESCE(SUM(pc.costs), 0) FROM ProjectCost pc WHERE pc.project.id = :projectId AND pc.approveOrPlan = 1")
    BigDecimal sumApprovedCostsByProject(@Param("projectId") Long projectId);

    // Check if costs exist for project and period
    @Query("SELECT COUNT(pc) > 0 FROM ProjectCost pc WHERE pc.project.id = :projectId AND pc.projectPeriod.id = :projectPeriodId")
    boolean existsByProjectAndPeriod(@Param("projectId") Long projectId,
            @Param("projectPeriodId") Long projectPeriodId);

    // Check if PLANNED costs exist for project and period
    @Query("SELECT COUNT(pc) > 0 FROM ProjectCost pc WHERE pc.project.id = :projectId AND pc.projectPeriod.id = :projectPeriodId AND pc.approveOrPlan = 2")
    boolean existsPlannedCostsByProjectAndPeriod(@Param("projectId") Long projectId,
            @Param("projectPeriodId") Long projectPeriodId);

    // Check if APPROVED costs exist for project and period
    @Query("SELECT COUNT(pc) > 0 FROM ProjectCost pc WHERE pc.project.id = :projectId AND pc.projectPeriod.id = :projectPeriodId AND pc.approveOrPlan = 1")
    boolean existsApprovedCostsByProjectAndPeriod(@Param("projectId") Long projectId,
            @Param("projectPeriodId") Long projectPeriodId);

    // Find all ordered by project
    @EntityGraph(attributePaths = { "project", "projectPeriod" })
    @Query("SELECT pc FROM ProjectCost pc ORDER BY pc.project.id ASC")
    List<ProjectCost> findAllByOrderByProjectIdAsc();

    // Find all ordered by period
    @EntityGraph(attributePaths = { "project", "projectPeriod" })
    @Query("SELECT pc FROM ProjectCost pc ORDER BY pc.projectPeriod.id ASC")
    List<ProjectCost> findAllByOrderByProjectPeriodIdAsc();

    // Find all ordered by cost amount
    @EntityGraph(attributePaths = { "project", "projectPeriod" })
    @Query("SELECT pc FROM ProjectCost pc ORDER BY pc.costs ASC")
    List<ProjectCost> findAllByOrderByCostsAsc();

    // Find costs by project ordered by period
    @EntityGraph(attributePaths = { "project", "projectPeriod" })
    @Query("SELECT pc FROM ProjectCost pc WHERE pc.project.id = :projectId ORDER BY pc.projectPeriod.id ASC")
    List<ProjectCost> findByProjectIdOrderByProjectPeriodIdAsc(@Param("projectId") Long projectId);

    // Find costs by project ordered by type
    @EntityGraph(attributePaths = { "project", "projectPeriod" })
    @Query("SELECT pc FROM ProjectCost pc WHERE pc.project.id = :projectId ORDER BY pc.approveOrPlan ASC")
    List<ProjectCost> findByProjectIdOrderByApproveOrPlanAsc(@Param("projectId") Long projectId);
}