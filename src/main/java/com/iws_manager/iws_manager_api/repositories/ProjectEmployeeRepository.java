package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.ProjectEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProjectEmployeeRepository extends JpaRepository<ProjectEmployee, Long> {

    // Basic queries with EntityGraph to load relationships
    @EntityGraph(attributePaths = { "employee", "project" })
    List<ProjectEmployee> findAll();

    @EntityGraph(attributePaths = { "employee", "project" })
    List<ProjectEmployee> findAllByOrderByIdAsc();

    // Queries by employee
    @EntityGraph(attributePaths = { "employee", "project" })
    List<ProjectEmployee> findByEmployeeId(Long employeeId);

    @EntityGraph(attributePaths = { "employee", "project" })
    List<ProjectEmployee> findByEmployeeIdOrderByIdAsc(Long employeeId);

    // Queries by project
    @EntityGraph(attributePaths = { "employee", "project" })
    List<ProjectEmployee> findByProjectId(Long projectId);

    @EntityGraph(attributePaths = { "employee", "project" })
    List<ProjectEmployee> findByProjectIdOrderByIdAsc(Long projectId);

    // Queries by qualificationkmui
    @EntityGraph(attributePaths = { "employee", "project" })
    List<ProjectEmployee> findByQualificationkmui(String qualificationkmui);

    @EntityGraph(attributePaths = { "employee", "project" })
    List<ProjectEmployee> findByQualificationkmuiContainingIgnoreCase(String qualificationkmui);

    @EntityGraph(attributePaths = { "employee", "project" })
    List<ProjectEmployee> findByQualificationkmuiOrderByIdAsc(String qualificationkmui);

    // Combined queries (employee + project)
    @EntityGraph(attributePaths = { "employee", "project" })
    List<ProjectEmployee> findByEmployeeIdAndProjectId(Long employeeId, Long projectId);

    // Combined queries (project + qualificationkmui)
    @EntityGraph(attributePaths = { "employee", "project" })
    List<ProjectEmployee> findByProjectIdAndQualificationkmui(Long projectId, String qualificationkmui);

    // Combined queries (employee + qualificationkmui)
    @EntityGraph(attributePaths = { "employee", "project" })
    List<ProjectEmployee> findByEmployeeIdAndQualificationkmui(Long employeeId, String qualificationkmui);

    // Queries by hourlyrate ranges
    @EntityGraph(attributePaths = { "employee", "project" })
    List<ProjectEmployee> findByHourlyrateGreaterThan(BigDecimal hourlyrate);

    @EntityGraph(attributePaths = { "employee", "project" })
    List<ProjectEmployee> findByHourlyrateLessThan(BigDecimal hourlyrate);

    @EntityGraph(attributePaths = { "employee", "project" })
    List<ProjectEmployee> findByHourlyrateBetween(BigDecimal minHourlyrate, BigDecimal maxHourlyrate);

    // Queries by plannedhours ranges
    @EntityGraph(attributePaths = { "employee", "project" })
    List<ProjectEmployee> findByPlannedhoursGreaterThan(BigDecimal plannedhours);

    @EntityGraph(attributePaths = { "employee", "project" })
    List<ProjectEmployee> findByPlannedhoursLessThan(BigDecimal plannedhours);

    @EntityGraph(attributePaths = { "employee", "project" })
    List<ProjectEmployee> findByPlannedhoursBetween(BigDecimal minPlannedhours, BigDecimal maxPlannedhours);

    // Sorting queries by specific fields
    @EntityGraph(attributePaths = { "employee", "project" })
    List<ProjectEmployee> findAllByOrderByHourlyrateAsc();

    @EntityGraph(attributePaths = { "employee", "project" })
    List<ProjectEmployee> findAllByOrderByHourlyrateDesc();

    @EntityGraph(attributePaths = { "employee", "project" })
    List<ProjectEmployee> findAllByOrderByPlannedhoursAsc();

    @EntityGraph(attributePaths = { "employee", "project" })
    List<ProjectEmployee> findAllByOrderByPlannedhoursDesc();

    @EntityGraph(attributePaths = { "employee", "project" })
    List<ProjectEmployee> findAllByOrderByQualificationkmuiAsc();

    // Custom JPQL queries
    @EntityGraph(attributePaths = { "employee", "project" })
    @Query("SELECT pe FROM ProjectEmployee pe WHERE pe.hourlyrate >= :minRate AND pe.plannedhours >= :minHours")
    List<ProjectEmployee> findWithMinimumRateAndHours(@Param("minRate") BigDecimal minRate,
            @Param("minHours") BigDecimal minHours);

    @EntityGraph(attributePaths = { "employee", "project" })
    @Query("SELECT pe FROM ProjectEmployee pe WHERE pe.employee.id = :employeeId AND pe.project.id = :projectId AND pe.qualificationkmui = :qualificationkmui")
    List<ProjectEmployee> findByEmployeeProjectAndQualification(@Param("employeeId") Long employeeId,
            @Param("projectId") Long projectId,
            @Param("qualificationkmui") String qualificationkmui);

    @EntityGraph(attributePaths = { "employee", "project" })
    @Query("SELECT pe FROM ProjectEmployee pe WHERE LOWER(pe.qualificationkmui) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<ProjectEmployee> findByQualificationContaining(@Param("keyword") String keyword);

    // Query to calculate total cost (hourlyrate * plannedhours)
    @Query("SELECT SUM(pe.hourlyrate * pe.plannedhours) FROM ProjectEmployee pe WHERE pe.project.id = :projectId")
    BigDecimal calculateTotalCostByProject(@Param("projectId") Long projectId);

    @Query("SELECT SUM(pe.hourlyrate * pe.plannedhours) FROM ProjectEmployee pe WHERE pe.employee.id = :employeeId")
    BigDecimal calculateTotalCostByEmployee(@Param("employeeId") Long employeeId);

    // Query to calculate total planned hours
    @Query("SELECT SUM(pe.plannedhours) FROM ProjectEmployee pe WHERE pe.project.id = :projectId")
    BigDecimal calculateTotalPlannedHoursByProject(@Param("projectId") Long projectId);

    @Query("SELECT SUM(pe.plannedhours) FROM ProjectEmployee pe WHERE pe.employee.id = :employeeId")
    BigDecimal calculateTotalPlannedHoursByEmployee(@Param("employeeId") Long employeeId);

    // Count queries
    @Query("SELECT COUNT(pe) FROM ProjectEmployee pe WHERE pe.project.id = :projectId")
    Long countByProject(@Param("projectId") Long projectId);

    @Query("SELECT COUNT(pe) FROM ProjectEmployee pe WHERE pe.employee.id = :employeeId")
    Long countByEmployee(@Param("employeeId") Long employeeId);

    @Query("SELECT COUNT(pe) FROM ProjectEmployee pe WHERE pe.project.id = :projectId AND pe.employee.id = :employeeId")
    Long countByProjectAndEmployee(@Param("projectId") Long projectId, @Param("employeeId") Long employeeId);

    // Projection queries (if you need specific data)
    @Query("SELECT pe.hourlyrate, pe.plannedhours, pe.qualificationkmui FROM ProjectEmployee pe WHERE pe.project.id = :projectId")
    List<Object[]> findRatesHoursAndQualificationsByProject(@Param("projectId") Long projectId);

    @Query("SELECT pe.hourlyrate, pe.plannedhours FROM ProjectEmployee pe WHERE pe.employee.id = :employeeId")
    List<Object[]> findRatesAndHoursByEmployee(@Param("employeeId") Long employeeId);

    // Queries to get unique employees by project
    @Query("SELECT DISTINCT pe.employee FROM ProjectEmployee pe WHERE pe.project.id = :projectId")
    List<com.iws_manager.iws_manager_api.models.Employee> findDistinctEmployeesByProject(
            @Param("projectId") Long projectId);

    // Queries to get unique projects by employee
    @Query("SELECT DISTINCT pe.project FROM ProjectEmployee pe WHERE pe.employee.id = :employeeId")
    List<com.iws_manager.iws_manager_api.models.Project> findDistinctProjectsByEmployee(
            @Param("employeeId") Long employeeId);

    // Queries to get average hourlyrate by project
    @Query("SELECT AVG(pe.hourlyrate) FROM ProjectEmployee pe WHERE pe.project.id = :projectId")
    BigDecimal findAverageHourlyRateByProject(@Param("projectId") Long projectId);

    // Queries to get average hourlyrate by employee
    @Query("SELECT AVG(pe.hourlyrate) FROM ProjectEmployee pe WHERE pe.employee.id = :employeeId")
    BigDecimal findAverageHourlyRateByEmployee(@Param("employeeId") Long employeeId);

    // Queries to filter by estimated cost ranges
    @EntityGraph(attributePaths = { "employee", "project" })
    @Query("SELECT pe FROM ProjectEmployee pe WHERE (pe.hourlyrate * pe.plannedhours) >= :minCost")
    List<ProjectEmployee> findByEstimatedCostGreaterThan(@Param("minCost") BigDecimal minCost);

    @EntityGraph(attributePaths = { "employee", "project" })
    @Query("SELECT pe FROM ProjectEmployee pe WHERE (pe.hourlyrate * pe.plannedhours) <= :maxCost")
    List<ProjectEmployee> findByEstimatedCostLessThan(@Param("maxCost") BigDecimal maxCost);

    @EntityGraph(attributePaths = { "employee", "project" })
    @Query("SELECT pe FROM ProjectEmployee pe WHERE (pe.hourlyrate * pe.plannedhours) BETWEEN :minCost AND :maxCost")
    List<ProjectEmployee> findByEstimatedCostBetween(@Param("minCost") BigDecimal minCost,
            @Param("maxCost") BigDecimal maxCost);

    // Queries to search by multiple qualifications
    @EntityGraph(attributePaths = { "employee", "project" })
    @Query("SELECT pe FROM ProjectEmployee pe WHERE pe.qualificationkmui IN :qualifications")
    List<ProjectEmployee> findByQualificationsIn(@Param("qualifications") List<String> qualifications);

    // Queries to sort by estimated cost
    @EntityGraph(attributePaths = { "employee", "project" })
    @Query("SELECT pe FROM ProjectEmployee pe ORDER BY (pe.hourlyrate * pe.plannedhours) ASC")
    List<ProjectEmployee> findAllByOrderByEstimatedCostAsc();

    @EntityGraph(attributePaths = { "employee", "project" })
    @Query("SELECT pe FROM ProjectEmployee pe ORDER BY (pe.hourlyrate * pe.plannedhours) DESC")
    List<ProjectEmployee> findAllByOrderByEstimatedCostDesc();

    // Queries to get statistics by project
    @Query("SELECT COUNT(pe), SUM(pe.plannedhours), AVG(pe.hourlyrate), SUM(pe.hourlyrate * pe.plannedhours) " +
            "FROM ProjectEmployee pe WHERE pe.project.id = :projectId")
    Object[] getProjectStatistics(@Param("projectId") Long projectId);

    // Queries to get statistics by employee
    @Query("SELECT COUNT(pe), SUM(pe.plannedhours), AVG(pe.hourlyrate), SUM(pe.hourlyrate * pe.plannedhours) " +
            "FROM ProjectEmployee pe WHERE pe.employee.id = :employeeId")
    Object[] getEmployeeStatistics(@Param("employeeId") Long employeeId);
}