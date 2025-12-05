package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.ProjectEmployee;
import com.iws_manager.iws_manager_api.repositories.ProjectEmployeeRepository;
import com.iws_manager.iws_manager_api.services.interfaces.ProjectEmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link ProjectEmployeeService} interface for managing
 * ProjectEmployee entities.
 * Provides CRUD operations and business logic for ProjectEmployee management.
 * 
 * <p>
 * This service implementation is transactional by default, with read-only
 * operations optimized for database performance.
 * </p>
 */
@Service
@Transactional
public class ProjectEmployeeServiceImpl implements ProjectEmployeeService {

    private final ProjectEmployeeRepository projectEmployeeRepository;

    /**
     * Constructs a new ProjectEmployeeService with the required repository
     * dependency.
     * 
     * @param projectEmployeeRepository the repository for ProjectEmployee entity
     *                                  operations
     */
    @Autowired
    public ProjectEmployeeServiceImpl(ProjectEmployeeRepository projectEmployeeRepository) {
        this.projectEmployeeRepository = projectEmployeeRepository;
    }

    /**
     * Creates and persists a new ProjectEmployee entity.
     * 
     * @param projectEmployee the ProjectEmployee entity to be created
     * @return the persisted ProjectEmployee entity with generated ID
     * @throws IllegalArgumentException if the projectEmployee parameter is null
     */
    @Override
    public ProjectEmployee create(ProjectEmployee projectEmployee) {
        if (projectEmployee == null) {
            throw new IllegalArgumentException("ProjectEmployee cannot be null");
        }

        // TODO: Add validation logic if needed in the future
        // validateProjectEmployee(projectEmployee);

        return projectEmployeeRepository.save(projectEmployee);
    }

    /**
     * Retrieves a ProjectEmployee by its unique identifier.
     * 
     * @param id the ID of the ProjectEmployee to retrieve
     * @return an Optional containing the found ProjectEmployee, or empty if not
     *         found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectEmployee> getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return projectEmployeeRepository.findById(id);
    }

    /**
     * Retrieves all ProjectEmployee entities from the database.
     * 
     * @return a List of all ProjectEmployee entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProjectEmployee> getAll() {
        return projectEmployeeRepository.findAllByOrderByIdAsc();
    }

    /**
     * Updates an existing ProjectEmployee entity.
     * 
     * @param id                     the ID of the ProjectEmployee to update
     * @param projectEmployeeDetails the ProjectEmployee object containing updated
     *                               fields
     * @return the updated ProjectEmployee entity
     * @throws RuntimeException         if no ProjectEmployee exists with the given
     *                                  ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public ProjectEmployee update(Long id, ProjectEmployee projectEmployeeDetails) {
        if (id == null || projectEmployeeDetails == null) {
            throw new IllegalArgumentException("ID and project employee details cannot be null");
        }

        return projectEmployeeRepository.findById(id)
                .map(existingProjectEmployee -> {
                    // TODO: Add validation logic if needed in the future
                    // validateProjectEmployee(projectEmployeeDetails);

                    // Update fields
                    existingProjectEmployee.setEmployee(projectEmployeeDetails.getEmployee());
                    existingProjectEmployee.setHourlyrate(projectEmployeeDetails.getHourlyrate());
                    existingProjectEmployee.setPlannedhours(projectEmployeeDetails.getPlannedhours());
                    existingProjectEmployee.setProject(projectEmployeeDetails.getProject());
                    existingProjectEmployee.setQualificationkmui(projectEmployeeDetails.getQualificationkmui());

                    return projectEmployeeRepository.save(existingProjectEmployee);
                })
                .orElseThrow(() -> new EntityNotFoundException("ProjectEmployee not found with id: " + id));
    }

    /**
     * Deletes a ProjectEmployee entity by its ID.
     * 
     * @param id the ID of the ProjectEmployee to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (!projectEmployeeRepository.existsById(id)) {
            throw new EntityNotFoundException("ProjectEmployee not found with id: " + id);
        }
        projectEmployeeRepository.deleteById(id);
    }

    // Get operations by employee
    @Override
    @Transactional(readOnly = true)
    public List<ProjectEmployee> getByEmployeeId(Long employeeId) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        return projectEmployeeRepository.findByEmployeeId(employeeId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectEmployee> getByEmployeeIdOrderByIdAsc(Long employeeId) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        return projectEmployeeRepository.findByEmployeeIdOrderByIdAsc(employeeId);
    }

    // Get operations by project
    @Override
    @Transactional(readOnly = true)
    public List<ProjectEmployee> getByProjectId(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        return projectEmployeeRepository.findByProjectId(projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectEmployee> getByProjectIdOrderByIdAsc(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        return projectEmployeeRepository.findByProjectIdOrderByIdAsc(projectId);
    }

    // Get operations by qualificationkmui
    @Override
    @Transactional(readOnly = true)
    public List<ProjectEmployee> getByQualificationkmui(String qualificationkmui) {
        if (qualificationkmui == null) {
            throw new IllegalArgumentException("Qualification K MUI cannot be null");
        }
        return projectEmployeeRepository.findByQualificationkmui(qualificationkmui);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectEmployee> getByQualificationkmuiContainingIgnoreCase(String qualificationkmui) {
        if (qualificationkmui == null) {
            throw new IllegalArgumentException("Qualification K MUI cannot be null");
        }
        return projectEmployeeRepository.findByQualificationkmuiContainingIgnoreCase(qualificationkmui);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectEmployee> getByQualificationkmuiOrderByIdAsc(String qualificationkmui) {
        if (qualificationkmui == null) {
            throw new IllegalArgumentException("Qualification K MUI cannot be null");
        }
        return projectEmployeeRepository.findByQualificationkmuiOrderByIdAsc(qualificationkmui);
    }

    // Get operations with combined criteria
    @Override
    @Transactional(readOnly = true)
    public List<ProjectEmployee> getByEmployeeIdAndProjectId(Long employeeId, Long projectId) {
        if (employeeId == null || projectId == null) {
            throw new IllegalArgumentException("Employee ID and Project ID cannot be null");
        }
        return projectEmployeeRepository.findByEmployeeIdAndProjectId(employeeId, projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectEmployee> getByProjectIdAndQualificationkmui(Long projectId, String qualificationkmui) {
        if (projectId == null || qualificationkmui == null) {
            throw new IllegalArgumentException("Project ID and Qualification K MUI cannot be null");
        }
        return projectEmployeeRepository.findByProjectIdAndQualificationkmui(projectId, qualificationkmui);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectEmployee> getByEmployeeIdAndQualificationkmui(Long employeeId, String qualificationkmui) {
        if (employeeId == null || qualificationkmui == null) {
            throw new IllegalArgumentException("Employee ID and Qualification K MUI cannot be null");
        }
        return projectEmployeeRepository.findByEmployeeIdAndQualificationkmui(employeeId, qualificationkmui);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectEmployee> getByEmployeeProjectAndQualification(Long employeeId, Long projectId,
            String qualificationkmui) {
        if (employeeId == null || projectId == null || qualificationkmui == null) {
            throw new IllegalArgumentException("Employee ID, Project ID and Qualification K MUI cannot be null");
        }
        return projectEmployeeRepository.findByEmployeeProjectAndQualification(employeeId, projectId,
                qualificationkmui);
    }

    // Get operations by hourlyrate range
    @Override
    @Transactional(readOnly = true)
    public List<ProjectEmployee> getByHourlyrateGreaterThan(BigDecimal hourlyrate) {
        if (hourlyrate == null) {
            throw new IllegalArgumentException("Hourly rate cannot be null");
        }
        return projectEmployeeRepository.findByHourlyrateGreaterThan(hourlyrate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectEmployee> getByHourlyrateLessThan(BigDecimal hourlyrate) {
        if (hourlyrate == null) {
            throw new IllegalArgumentException("Hourly rate cannot be null");
        }
        return projectEmployeeRepository.findByHourlyrateLessThan(hourlyrate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectEmployee> getByHourlyrateBetween(BigDecimal minHourlyrate, BigDecimal maxHourlyrate) {
        if (minHourlyrate == null || maxHourlyrate == null) {
            throw new IllegalArgumentException("Min hourly rate and max hourly rate cannot be null");
        }
        if (minHourlyrate.compareTo(maxHourlyrate) > 0) {
            throw new IllegalArgumentException("Min hourly rate cannot be greater than max hourly rate");
        }
        return projectEmployeeRepository.findByHourlyrateBetween(minHourlyrate, maxHourlyrate);
    }

    // Get operations by plannedhours range
    @Override
    @Transactional(readOnly = true)
    public List<ProjectEmployee> getByPlannedhoursGreaterThan(BigDecimal plannedhours) {
        if (plannedhours == null) {
            throw new IllegalArgumentException("Planned hours cannot be null");
        }
        return projectEmployeeRepository.findByPlannedhoursGreaterThan(plannedhours);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectEmployee> getByPlannedhoursLessThan(BigDecimal plannedhours) {
        if (plannedhours == null) {
            throw new IllegalArgumentException("Planned hours cannot be null");
        }
        return projectEmployeeRepository.findByPlannedhoursLessThan(plannedhours);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectEmployee> getByPlannedhoursBetween(BigDecimal minPlannedhours, BigDecimal maxPlannedhours) {
        if (minPlannedhours == null || maxPlannedhours == null) {
            throw new IllegalArgumentException("Min planned hours and max planned hours cannot be null");
        }
        if (minPlannedhours.compareTo(maxPlannedhours) > 0) {
            throw new IllegalArgumentException("Min planned hours cannot be greater than max planned hours");
        }
        return projectEmployeeRepository.findByPlannedhoursBetween(minPlannedhours, maxPlannedhours);
    }

    // Get operations by estimated cost range
    @Override
    @Transactional(readOnly = true)
    public List<ProjectEmployee> getByEstimatedCostGreaterThan(BigDecimal minCost) {
        if (minCost == null) {
            throw new IllegalArgumentException("Minimum cost cannot be null");
        }
        return projectEmployeeRepository.findByEstimatedCostGreaterThan(minCost);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectEmployee> getByEstimatedCostLessThan(BigDecimal maxCost) {
        if (maxCost == null) {
            throw new IllegalArgumentException("Maximum cost cannot be null");
        }
        return projectEmployeeRepository.findByEstimatedCostLessThan(maxCost);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectEmployee> getByEstimatedCostBetween(BigDecimal minCost, BigDecimal maxCost) {
        if (minCost == null || maxCost == null) {
            throw new IllegalArgumentException("Minimum cost and maximum cost cannot be null");
        }
        if (minCost.compareTo(maxCost) > 0) {
            throw new IllegalArgumentException("Minimum cost cannot be greater than maximum cost");
        }
        return projectEmployeeRepository.findByEstimatedCostBetween(minCost, maxCost);
    }

    // Get operations with minimum rate and hours
    @Override
    @Transactional(readOnly = true)
    public List<ProjectEmployee> getWithMinimumRateAndHours(BigDecimal minRate, BigDecimal minHours) {
        if (minRate == null || minHours == null) {
            throw new IllegalArgumentException("Min rate and min hours cannot be null");
        }
        return projectEmployeeRepository.findWithMinimumRateAndHours(minRate, minHours);
    }

    // Get operations by qualification containing keyword
    @Override
    @Transactional(readOnly = true)
    public List<ProjectEmployee> getByQualificationContaining(String keyword) {
        if (keyword == null) {
            throw new IllegalArgumentException("Keyword cannot be null");
        }
        return projectEmployeeRepository.findByQualificationContaining(keyword);
    }

    // Get operations by multiple qualifications
    @Override
    @Transactional(readOnly = true)
    public List<ProjectEmployee> getByQualificationsIn(List<String> qualifications) {
        if (qualifications == null || qualifications.isEmpty()) {
            throw new IllegalArgumentException("Qualifications list cannot be null or empty");
        }
        return projectEmployeeRepository.findByQualificationsIn(qualifications);
    }

    // Ordering operations
    @Override
    @Transactional(readOnly = true)
    public List<ProjectEmployee> getAllOrderByHourlyrateAsc() {
        return projectEmployeeRepository.findAllByOrderByHourlyrateAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectEmployee> getAllOrderByHourlyrateDesc() {
        return projectEmployeeRepository.findAllByOrderByHourlyrateDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectEmployee> getAllOrderByPlannedhoursAsc() {
        return projectEmployeeRepository.findAllByOrderByPlannedhoursAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectEmployee> getAllOrderByPlannedhoursDesc() {
        return projectEmployeeRepository.findAllByOrderByPlannedhoursDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectEmployee> getAllOrderByQualificationkmuiAsc() {
        return projectEmployeeRepository.findAllByOrderByQualificationkmuiAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectEmployee> getAllOrderByEstimatedCostAsc() {
        return projectEmployeeRepository.findAllByOrderByEstimatedCostAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectEmployee> getAllOrderByEstimatedCostDesc() {
        return projectEmployeeRepository.findAllByOrderByEstimatedCostDesc();
    }

    // Calculation operations
    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateTotalCostByProject(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        BigDecimal total = projectEmployeeRepository.calculateTotalCostByProject(projectId);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateTotalCostByEmployee(Long employeeId) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        BigDecimal total = projectEmployeeRepository.calculateTotalCostByEmployee(employeeId);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateTotalPlannedHoursByProject(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        BigDecimal total = projectEmployeeRepository.calculateTotalPlannedHoursByProject(projectId);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateTotalPlannedHoursByEmployee(Long employeeId) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        BigDecimal total = projectEmployeeRepository.calculateTotalPlannedHoursByEmployee(employeeId);
        return total != null ? total : BigDecimal.ZERO;
    }

    // Count operations
    @Override
    @Transactional(readOnly = true)
    public Long countByProject(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        return projectEmployeeRepository.countByProject(projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByEmployee(Long employeeId) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        return projectEmployeeRepository.countByEmployee(employeeId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByProjectAndEmployee(Long projectId, Long employeeId) {
        if (projectId == null || employeeId == null) {
            throw new IllegalArgumentException("Project ID and Employee ID cannot be null");
        }
        return projectEmployeeRepository.countByProjectAndEmployee(projectId, employeeId);
    }

    // Get distinct operations
    @Override
    @Transactional(readOnly = true)
    public List<com.iws_manager.iws_manager_api.models.Employee> getDistinctEmployeesByProject(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        return projectEmployeeRepository.findDistinctEmployeesByProject(projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<com.iws_manager.iws_manager_api.models.Project> getDistinctProjectsByEmployee(Long employeeId) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        return projectEmployeeRepository.findDistinctProjectsByEmployee(employeeId);
    }

    // Get average hourlyrate operations
    @Override
    @Transactional(readOnly = true)
    public BigDecimal getAverageHourlyRateByProject(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        BigDecimal average = projectEmployeeRepository.findAverageHourlyRateByProject(projectId);
        return average != null ? average : BigDecimal.ZERO;
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getAverageHourlyRateByEmployee(Long employeeId) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        BigDecimal average = projectEmployeeRepository.findAverageHourlyRateByEmployee(employeeId);
        return average != null ? average : BigDecimal.ZERO;
    }

    // Get projection data operations
    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getRatesHoursAndQualificationsByProject(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        return projectEmployeeRepository.findRatesHoursAndQualificationsByProject(projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getRatesAndHoursByEmployee(Long employeeId) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        return projectEmployeeRepository.findRatesAndHoursByEmployee(employeeId);
    }

    // Get statistics operations
    @Override
    @Transactional(readOnly = true)
    public Object[] getProjectStatistics(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        return projectEmployeeRepository.getProjectStatistics(projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public Object[] getEmployeeStatistics(Long employeeId) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        return projectEmployeeRepository.getEmployeeStatistics(employeeId);
    }

    // Validation and business logic
    @Override
    public boolean validateProjectEmployee(ProjectEmployee projectEmployee) {
        if (projectEmployee == null) {
            throw new IllegalArgumentException("ProjectEmployee cannot be null");
        }

        // TODO: Add validation logic if needed in the future
        // Example validations that could be added later:
        //
        // if (projectEmployee.getEmployee() == null) {
        // throw new IllegalArgumentException("Employee is required");
        // }
        //
        // if (projectEmployee.getProject() == null) {
        // throw new IllegalArgumentException("Project is required");
        // }
        //
        // if (projectEmployee.getHourlyrate() == null ||
        // projectEmployee.getHourlyrate().compareTo(BigDecimal.ZERO) <= 0) {
        // throw new IllegalArgumentException("Hourly rate must be greater than zero");
        // }
        //
        // if (projectEmployee.getPlannedhours() == null ||
        // projectEmployee.getPlannedhours().compareTo(BigDecimal.ZERO) <= 0) {
        // throw new IllegalArgumentException("Planned hours must be greater than
        // zero");
        // }

        return true;
    }
}