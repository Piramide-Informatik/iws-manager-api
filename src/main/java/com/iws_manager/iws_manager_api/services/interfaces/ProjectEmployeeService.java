package com.iws_manager.iws_manager_api.services.interfaces;

import com.iws_manager.iws_manager_api.models.ProjectEmployee;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProjectEmployeeService {

    // Basic CRUD operations
    ProjectEmployee create(ProjectEmployee projectEmployee);

    Optional<ProjectEmployee> getById(Long id);

    List<ProjectEmployee> getAll();

    ProjectEmployee update(Long id, ProjectEmployee projectEmployee);

    void delete(Long id);

    // Get operations by employee
    List<ProjectEmployee> getByEmployeeId(Long employeeId);

    List<ProjectEmployee> getByEmployeeIdOrderByIdAsc(Long employeeId);

    // Get operations by project
    List<ProjectEmployee> getByProjectId(Long projectId);

    List<ProjectEmployee> getByProjectIdOrderByIdAsc(Long projectId);

    // Get operations by qualificationkmui
    List<ProjectEmployee> getByQualificationkmui(String qualificationkmui);

    List<ProjectEmployee> getByQualificationkmuiContainingIgnoreCase(String qualificationkmui);

    List<ProjectEmployee> getByQualificationkmuiOrderByIdAsc(String qualificationkmui);

    // Get operations with combined criteria
    List<ProjectEmployee> getByEmployeeIdAndProjectId(Long employeeId, Long projectId);

    List<ProjectEmployee> getByProjectIdAndQualificationkmui(Long projectId, String qualificationkmui);

    List<ProjectEmployee> getByEmployeeIdAndQualificationkmui(Long employeeId, String qualificationkmui);

    List<ProjectEmployee> getByEmployeeProjectAndQualification(Long employeeId, Long projectId,
            String qualificationkmui);

    // Get operations by hourlyrate range
    List<ProjectEmployee> getByHourlyrateGreaterThan(BigDecimal hourlyrate);

    List<ProjectEmployee> getByHourlyrateLessThan(BigDecimal hourlyrate);

    List<ProjectEmployee> getByHourlyrateBetween(BigDecimal minHourlyrate, BigDecimal maxHourlyrate);

    // Get operations by plannedhours range
    List<ProjectEmployee> getByPlannedhoursGreaterThan(BigDecimal plannedhours);

    List<ProjectEmployee> getByPlannedhoursLessThan(BigDecimal plannedhours);

    List<ProjectEmployee> getByPlannedhoursBetween(BigDecimal minPlannedhours, BigDecimal maxPlannedhours);

    // Get operations by estimated cost range
    List<ProjectEmployee> getByEstimatedCostGreaterThan(BigDecimal minCost);

    List<ProjectEmployee> getByEstimatedCostLessThan(BigDecimal maxCost);

    List<ProjectEmployee> getByEstimatedCostBetween(BigDecimal minCost, BigDecimal maxCost);

    // Get operations with minimum rate and hours
    List<ProjectEmployee> getWithMinimumRateAndHours(BigDecimal minRate, BigDecimal minHours);

    // Get operations by qualification containing keyword
    List<ProjectEmployee> getByQualificationContaining(String keyword);

    // Get operations by multiple qualifications
    List<ProjectEmployee> getByQualificationsIn(List<String> qualifications);

    // Ordering operations
    List<ProjectEmployee> getAllOrderByHourlyrateAsc();

    List<ProjectEmployee> getAllOrderByHourlyrateDesc();

    List<ProjectEmployee> getAllOrderByPlannedhoursAsc();

    List<ProjectEmployee> getAllOrderByPlannedhoursDesc();

    List<ProjectEmployee> getAllOrderByQualificationkmuiAsc();

    List<ProjectEmployee> getAllOrderByEstimatedCostAsc();

    List<ProjectEmployee> getAllOrderByEstimatedCostDesc();

    // Calculation operations
    BigDecimal calculateTotalCostByProject(Long projectId);

    BigDecimal calculateTotalCostByEmployee(Long employeeId);

    BigDecimal calculateTotalPlannedHoursByProject(Long projectId);

    BigDecimal calculateTotalPlannedHoursByEmployee(Long employeeId);

    // Count operations
    Long countByProject(Long projectId);

    Long countByEmployee(Long employeeId);

    Long countByProjectAndEmployee(Long projectId, Long employeeId);

    // Get distinct operations
    List<com.iws_manager.iws_manager_api.models.Employee> getDistinctEmployeesByProject(Long projectId);

    List<com.iws_manager.iws_manager_api.models.Project> getDistinctProjectsByEmployee(Long employeeId);

    // Get average hourlyrate operations
    BigDecimal getAverageHourlyRateByProject(Long projectId);

    BigDecimal getAverageHourlyRateByEmployee(Long employeeId);

    // Get projection data operations
    List<Object[]> getRatesHoursAndQualificationsByProject(Long projectId);

    List<Object[]> getRatesAndHoursByEmployee(Long employeeId);

    // Get statistics operations
    Object[] getProjectStatistics(Long projectId);

    Object[] getEmployeeStatistics(Long employeeId);

    // Validation and business logic
    boolean validateProjectEmployee(ProjectEmployee projectEmployee);
}