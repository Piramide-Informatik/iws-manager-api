package com.iws_manager.iws_manager_api.services.impl;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.ProjectCost;
import com.iws_manager.iws_manager_api.repositories.ProjectCostRepository;
import com.iws_manager.iws_manager_api.services.interfaces.ProjectCostService;

/**
 * Implementation of the {@link ProjectCostService} interface for managing
 * ProjectCost entities.
 * Provides CRUD operations and business logic for ProjectCost management.
 * 
 * <p>
 * This service implementation is transactional by default, with read-only
 * operations
 * optimized for database performance.
 * </p>
 */
@Service
@Transactional
public class ProjectCostServiceImpl implements ProjectCostService {

    private final ProjectCostRepository projectCostRepository;

    /**
     * Comparador que maneja valores nulos para orden descendente de costs.
     * Los valores nulos se tratan como mayores que los no nulos.
     */
    private final Comparator<ProjectCost> costsDescComparator = Comparator
            .nullsLast(Comparator.comparing(ProjectCost::getCosts,
                    Comparator.nullsLast(BigDecimal::compareTo)))
            .reversed();

    /**
     * Comparador que maneja valores nulos para orden ascendente de costs.
     * Los valores nulos se tratan como mayores que los no nulos.
     */
    private final Comparator<ProjectCost> costsAscComparator = Comparator
            .nullsLast(Comparator.comparing(ProjectCost::getCosts,
                    Comparator.nullsLast(BigDecimal::compareTo)));

    /**
     * Constructs a new ProjectCostService with the required repository dependency.
     * 
     * @param projectCostRepository the repository for ProjectCost entity operations
     */
    @Autowired
    public ProjectCostServiceImpl(ProjectCostRepository projectCostRepository) {
        this.projectCostRepository = projectCostRepository;
    }

    /**
     * Creates and persists a new ProjectCost entity.
     * 
     * @param projectCost the ProjectCost entity to be created
     * @return the persisted ProjectCost entity with generated ID
     * @throws IllegalArgumentException if the ProjectCost parameter is null
     */
    @Override
    public ProjectCost create(ProjectCost projectCost) {
        if (projectCost == null) {
            throw new IllegalArgumentException("ProjectCost cannot be null");
        }

        // Validate business rules before creation
        validateProjectCost(projectCost);

        return projectCostRepository.save(projectCost);
    }

    /**
     * Retrieves a ProjectCost by its unique identifier.
     * 
     * @param id the ID of the ProjectCost to retrieve
     * @return an Optional containing the found ProjectCost, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectCost> getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return projectCostRepository.findById(id);
    }

    /**
     * Retrieves all ProjectCost entities from the database.
     * 
     * @return a List of all ProjectCost entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProjectCost> getAll() {
        return projectCostRepository.findAll();
    }

    /**
     * Updates an existing ProjectCost entity.
     * 
     * @param id                 the ID of the ProjectCost to update
     * @param projectCostDetails the ProjectCost object containing updated fields
     * @return the updated ProjectCost entity
     * @throws RuntimeException         if no ProjectCost exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public ProjectCost update(Long id, ProjectCost projectCostDetails) {
        if (id == null || projectCostDetails == null) {
            throw new IllegalArgumentException("ID and project cost details cannot be null");
        }

        return projectCostRepository.findById(id)
                .map(existingProjectCost -> {
                    // Validate business rules before update
                    validateProjectCost(projectCostDetails);

                    // Update fields
                    existingProjectCost.setApproveOrPlan(projectCostDetails.getApproveOrPlan());
                    existingProjectCost.setCosts(projectCostDetails.getCosts());
                    existingProjectCost.setProject(projectCostDetails.getProject());
                    existingProjectCost.setProjectPeriod(projectCostDetails.getProjectPeriod());

                    return projectCostRepository.save(existingProjectCost);
                })
                .orElseThrow(() -> new EntityNotFoundException("ProjectCost not found with id: " + id));
    }

    /**
     * Deletes a ProjectCost entity by its ID.
     * 
     * @param id the ID of the ProjectCost to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (!projectCostRepository.existsById(id)) {
            throw new EntityNotFoundException("ProjectCost not found with id: " + id);
        }
        projectCostRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectCost> getByProjectId(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        return projectCostRepository.findByProjectId(projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectCost> getByProjectIdOrderByCostsAsc(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        return projectCostRepository.findByProjectIdOrderByProjectPeriodIdAsc(projectId).stream()
                .sorted(costsAscComparator)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectCost> getByProjectIdOrderByCostsDesc(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        return getByProjectIdOrderByCostsAsc(projectId).stream()
                .sorted(costsDescComparator)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectCost> getByProjectIdOrderByProjectPeriodIdAsc(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        return projectCostRepository.findByProjectIdOrderByProjectPeriodIdAsc(projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectCost> getByProjectIdOrderByApproveOrPlanAsc(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        return projectCostRepository.findByProjectIdOrderByApproveOrPlanAsc(projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectCost> getByProjectPeriodId(Long projectPeriodId) {
        if (projectPeriodId == null) {
            throw new IllegalArgumentException("Project Period ID cannot be null");
        }
        return projectCostRepository.findByProjectPeriodId(projectPeriodId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectCost> getByProjectPeriodIdOrderByCostsAsc(Long projectPeriodId) {
        if (projectPeriodId == null) {
            throw new IllegalArgumentException("Project Period ID cannot be null");
        }
        return projectCostRepository.findByProjectPeriodId(projectPeriodId).stream()
                .sorted(costsAscComparator)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectCost> getByProjectPeriodIdOrderByCostsDesc(Long projectPeriodId) {
        if (projectPeriodId == null) {
            throw new IllegalArgumentException("Project Period ID cannot be null");
        }
        return getByProjectPeriodIdOrderByCostsAsc(projectPeriodId).stream()
                .sorted(costsDescComparator)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectCost> getByApproveOrPlan(Byte approveOrPlan) {
        if (approveOrPlan == null) {
            throw new IllegalArgumentException("ApproveOrPlan cannot be null");
        }
        return projectCostRepository.findByApproveOrPlan(approveOrPlan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectCost> getApprovedCosts() {
        return projectCostRepository.findByApproveOrPlan((byte) 1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectCost> getPlannedCosts() {
        return projectCostRepository.findByApproveOrPlan((byte) 2);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectCost> getByProjectIdAndProjectPeriodId(Long projectId, Long projectPeriodId) {
        if (projectId == null || projectPeriodId == null) {
            throw new IllegalArgumentException("Project ID and Project Period ID cannot be null");
        }
        return projectCostRepository.findByProjectIdAndProjectPeriodId(projectId, projectPeriodId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectCost> getApprovedCostsByProjectAndPeriod(Long projectId, Long projectPeriodId) {
        if (projectId == null || projectPeriodId == null) {
            throw new IllegalArgumentException("Project ID and Project Period ID cannot be null");
        }
        return projectCostRepository.findApprovedCostsByProjectAndPeriod(projectId, projectPeriodId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectCost> getPlannedCostsByProjectAndPeriod(Long projectId, Long projectPeriodId) {
        if (projectId == null || projectPeriodId == null) {
            throw new IllegalArgumentException("Project ID and Project Period ID cannot be null");
        }
        return projectCostRepository.findPlannedCostsByProjectAndPeriod(projectId, projectPeriodId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectCost> getByProjectIdAndApproveOrPlan(Long projectId, Byte approveOrPlan) {
        if (projectId == null || approveOrPlan == null) {
            throw new IllegalArgumentException("Project ID and ApproveOrPlan cannot be null");
        }
        return projectCostRepository.findByProjectIdAndApproveOrPlan(projectId, approveOrPlan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectCost> getApprovedCostsByProject(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        return projectCostRepository.findByProjectIdAndApproveOrPlan(projectId, (byte) 1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectCost> getPlannedCostsByProject(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        return projectCostRepository.findByProjectIdAndApproveOrPlan(projectId, (byte) 2);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectCost> getByProjectIdAndProjectPeriodIdAndApproveOrPlan(
            Long projectId, Long projectPeriodId, Byte approveOrPlan) {
        if (projectId == null || projectPeriodId == null || approveOrPlan == null) {
            throw new IllegalArgumentException("Project ID, Project Period ID and ApproveOrPlan cannot be null");
        }
        return projectCostRepository.findByProjectIdAndProjectPeriodIdAndApproveOrPlan(projectId, projectPeriodId,
                approveOrPlan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectCost> getProjectTotals(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        return projectCostRepository.findProjectTotals(projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectCost> getApprovedProjectTotals(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        return projectCostRepository.findApprovedProjectTotals(projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectCost> getPlannedProjectTotals(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        return projectCostRepository.findPlannedProjectTotals(projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getTotalCostsByProject(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        return projectCostRepository.sumCostsByProject(projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getTotalApprovedCostsByProject(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        return projectCostRepository.sumApprovedCostsByProject(projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getTotalPlannedCostsByProject(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        return projectCostRepository.sumPlannedCostsByProject(projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getTotalCostsByProjectAndPeriod(Long projectId, Long projectPeriodId) {
        if (projectId == null || projectPeriodId == null) {
            throw new IllegalArgumentException("Project ID and Project Period ID cannot be null");
        }
        return projectCostRepository.sumCostsByProjectAndPeriod(projectId, projectPeriodId);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getTotalApprovedCostsByProjectAndPeriod(Long projectId, Long projectPeriodId) {
        if (projectId == null || projectPeriodId == null) {
            throw new IllegalArgumentException("Project ID and Project Period ID cannot be null");
        }
        return projectCostRepository.sumApprovedCostsByProjectAndPeriod(projectId, projectPeriodId);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getTotalPlannedCostsByProjectAndPeriod(Long projectId, Long projectPeriodId) {
        if (projectId == null || projectPeriodId == null) {
            throw new IllegalArgumentException("Project ID and Project Period ID cannot be null");
        }
        return projectCostRepository.sumPlannedCostsByProjectAndPeriod(projectId, projectPeriodId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByProjectAndPeriod(Long projectId, Long projectPeriodId) {
        if (projectId == null || projectPeriodId == null) {
            throw new IllegalArgumentException("Project ID and Project Period ID cannot be null");
        }
        return projectCostRepository.existsByProjectAndPeriod(projectId, projectPeriodId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsApprovedCostsByProjectAndPeriod(Long projectId, Long projectPeriodId) {
        if (projectId == null || projectPeriodId == null) {
            throw new IllegalArgumentException("Project ID and Project Period ID cannot be null");
        }
        return projectCostRepository.existsApprovedCostsByProjectAndPeriod(projectId, projectPeriodId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsPlannedCostsByProjectAndPeriod(Long projectId, Long projectPeriodId) {
        if (projectId == null || projectPeriodId == null) {
            throw new IllegalArgumentException("Project ID and Project Period ID cannot be null");
        }
        return projectCostRepository.existsPlannedCostsByProjectAndPeriod(projectId, projectPeriodId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectCost> getAllOrderByCostsAsc() {
        return projectCostRepository.findAllByOrderByCostsAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectCost> getAllOrderByCostsDesc() {
        return getAllOrderByCostsAsc().stream()
                .sorted(costsDescComparator)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectCost> getAllOrderByProjectIdAsc() {
        return projectCostRepository.findAllByOrderByProjectIdAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectCost> getAllOrderByProjectPeriodIdAsc() {
        return projectCostRepository.findAllByOrderByProjectPeriodIdAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectCost> getByCostsGreaterThan(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        // El repositorio no tiene este método, lo implementamos manualmente
        return getAll().stream()
                .filter(pc -> pc.getCosts() != null && pc.getCosts().compareTo(amount) > 0)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectCost> getByCostsLessThan(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        // El repositorio no tiene este método, lo implementamos manualmente
        return getAll().stream()
                .filter(pc -> pc.getCosts() != null && pc.getCosts().compareTo(amount) < 0)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectCost> getByCostsBetween(BigDecimal minAmount, BigDecimal maxAmount) {
        if (minAmount == null || maxAmount == null) {
            throw new IllegalArgumentException("Min amount and max amount cannot be null");
        }
        if (minAmount.compareTo(maxAmount) > 0) {
            throw new IllegalArgumentException("Min amount cannot be greater than max amount");
        }
        // El repositorio no tiene este método, lo implementamos manualmente
        return getAll().stream()
                .filter(pc -> pc.getCosts() != null
                        && pc.getCosts().compareTo(minAmount) >= 0
                        && pc.getCosts().compareTo(maxAmount) <= 0)
                .collect(Collectors.toList());
    }

    @Override
    public boolean validateProjectCost(ProjectCost projectCost) {
        if (projectCost == null) {
            throw new IllegalArgumentException("ProjectCost cannot be null");
        }

        // Basic validation
        if (projectCost.getApproveOrPlan() == null) {
            throw new IllegalArgumentException("ApproveOrPlan is required");
        }

        if (projectCost.getApproveOrPlan() != 1 && projectCost.getApproveOrPlan() != 2) {
            throw new IllegalArgumentException("ApproveOrPlan must be 1 (approved) or 2 (planned)");
        }

        if (projectCost.getProject() == null) {
            throw new IllegalArgumentException("Project is required");
        }

        if (projectCost.getProjectPeriod() == null) {
            throw new IllegalArgumentException("ProjectPeriod is required");
        }

        return true;
    }

    @Override
    public boolean canCreateProjectCost(Long projectId, Long projectPeriodId, Byte approveOrPlan) {
        if (projectId == null || projectPeriodId == null || approveOrPlan == null) {
            return false;
        }

        // Check if a cost already exists for this combination
        return !projectCostRepository.findByProjectIdAndProjectPeriodIdAndApproveOrPlan(
                projectId, projectPeriodId, approveOrPlan).isEmpty();
    }

    @Override
    public List<ProjectCost> createAll(List<ProjectCost> projectCosts) {
        if (projectCosts == null || projectCosts.isEmpty()) {
            throw new IllegalArgumentException("ProjectCosts list cannot be null or empty");
        }

        // Validate all project costs before saving
        projectCosts.forEach(this::validateProjectCost);

        return projectCostRepository.saveAll(projectCosts);
    }

    @Override
    public void deleteByProjectId(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        List<ProjectCost> projectCosts = projectCostRepository.findByProjectId(projectId);
        projectCostRepository.deleteAll(projectCosts);
    }

    @Override
    public void deleteByProjectPeriodId(Long projectPeriodId) {
        if (projectPeriodId == null) {
            throw new IllegalArgumentException("Project Period ID cannot be null");
        }
        List<ProjectCost> projectCosts = projectCostRepository.findByProjectPeriodId(projectPeriodId);
        projectCostRepository.deleteAll(projectCosts);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getMaxId() {
        // El repositorio no tiene este método, lo implementamos manualmente
        return getAll().stream()
                .map(ProjectCost::getId)
                .max(Long::compareTo)
                .orElse(0L);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectCost> getByProjectIdWithNullCosts(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        return getByProjectId(projectId).stream()
                .filter(pc -> pc.getCosts() == null)
                .collect(Collectors.toList());
    }
}