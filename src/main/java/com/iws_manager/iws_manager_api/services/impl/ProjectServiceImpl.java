package com.iws_manager.iws_manager_api.services.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.Project;
import com.iws_manager.iws_manager_api.repositories.ProjectRepository;
import com.iws_manager.iws_manager_api.services.interfaces.ProjectService;

/**
 * Implementation of the {@link ProjectService} interface for managing Branch entities.
 * Provides CRUD operations and business logic for Project management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    
    /**
     * Constructs a new ProjectService with the required repository dependency.
     * 
     * @param projectRepository the repository for Project entity operations
     */
    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    /**
     * Creates and persists a new Project entity.
     * 
     * @param project the Project entity to be created
     * @return the persisted Project entity with generated ID
     * @throws IllegalArgumentException if the Project parameter is null
     */
    @Override
    public Project create(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null");
        }
        return projectRepository.save(project);
    }

    /**
     * Retrieves a Project by its unique identifier.
     * 
     * @param id the ID of the Project to retrieve
     * @return an Optional containing the found Project, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Project> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return projectRepository.findById(id);
    }

    /**
     * Retrieves all Project entities from the database.
     * 
     * @return a List of all Project entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Project> findAll() {
        return projectRepository.findAllByOrderByProjectNameAsc();
    }

    /**
     * Updates an existing Project entity.
     * 
     * @param id the ID of the Project to update
     * @param branchDetails the Project object containing updated fields
     * @return the updated Project entity
     * @throws RuntimeException if no Project exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public Project update(Long id, Project projectDetails) {
        if (id == null || projectDetails == null) {
            throw new IllegalArgumentException("ID and project details cannot be null");
        }
        
        return  projectRepository.findById(id)
                .map(existingProject -> {
                    existingProject.setApprovalDate(projectDetails.getApprovalDate());
                    existingProject.setAuthorizationDate(projectDetails.getAuthorizationDate());
                    existingProject.setChance(projectDetails.getChance());
                    existingProject.setComment(projectDetails.getComment());
                    existingProject.setCustomer(projectDetails.getCustomer());
                    existingProject.setDate1(projectDetails.getDate1());
                    existingProject.setDate2(projectDetails.getDate2());
                    existingProject.setDate3(projectDetails.getDate3());
                    existingProject.setDate4(projectDetails.getDate4());
                    existingProject.setDate5(projectDetails.getDate5());
                    existingProject.setDateLevel1(projectDetails.getDateLevel1());
                    existingProject.setDateLevel2(projectDetails.getDateLevel2());
                    // existingProject.setEmpiws20(projectDetails.getEmpiws20());
                    // existingProject.setEmpiws30(projectDetails.getEmpiws30());
                    // existingProject.setEmpiws50(projectDetails.getEmpiws50());
                    existingProject.setEndApproval(projectDetails.getEndApproval());
                    existingProject.setEndDate(projectDetails.getEndDate());
                    existingProject.setFincanceAuthority(projectDetails.getFincanceAuthority());
                    existingProject.setFundingLabel(projectDetails.getFundingLabel());
                    // existingProject.setFundingProgram(projectDetails.getFundingProgram());
                    existingProject.setFundingRate(projectDetails.getFundingRate());
                    existingProject.setHourlyRateMueu(projectDetails.getHourlyRateMueu());
                    existingProject.setIncome1(projectDetails.getIncome1());
                    existingProject.setIncome2(projectDetails.getIncome2());
                    existingProject.setIncome3(projectDetails.getIncome3());
                    existingProject.setIncome4(projectDetails.getIncome4());
                    existingProject.setIncome5(projectDetails.getIncome5());
                    existingProject.setMaxHoursPerMonth(projectDetails.getMaxHoursPerMonth());
                    existingProject.setMaxHoursPerYear(projectDetails.getMaxHoursPerYear());
                    // existingProject.setNetwork(projectDetails.getNetwork());
                    existingProject.setOrderIdFue(projectDetails.getOrderIdFue());
                    existingProject.setOrderIdAdmin(projectDetails.getOrderIdAdmin());
                    existingProject.setStuffFlat(projectDetails.getStuffFlat());
                    existingProject.setProductiveHoursPerYear(projectDetails.getProductiveHoursPerYear());
                    existingProject.setProjectLabel(projectDetails.getProjectLabel());
                    existingProject.setProjectName(projectDetails.getProjectName());
                    // existingProject.setPromoter(projectDetails.getPromoter());
                    existingProject.setNote(projectDetails.getNote());
                    existingProject.setShareResearch(projectDetails.getShareResearch());
                    existingProject.setStartApproval(projectDetails.getStartApproval());
                    existingProject.setStartDate(projectDetails.getStartDate());
                    // existingProject.setStatus(projectDetails.getStatus());
                    existingProject.setTitle(projectDetails.getTitle());

                    return projectRepository.save(existingProject);
                })
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
    }

    /**
     * Deletes a Project entity by its ID.
     * 
     * @param id the ID of the Project to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        projectRepository.deleteById(id);
    }

    @Override
    public List<Project> findByApprovalDate(LocalDate approvalDate) {
        return projectRepository.findByApprovalDate(approvalDate);
    }

    @Override
    public List<Project> findByAuthorizationDate(LocalDate authorizationDate) {
        return projectRepository.findByAuthorizationDate(authorizationDate);
    }

    @Override
    public List<Project> findByEndApproval(LocalDate endApproval) {
        return projectRepository.findByEndApproval(endApproval);
    }

    @Override
    public List<Project> findByEndDate(LocalDate endDate) {
        return projectRepository.findByEndDate(endDate);
    }

    @Override
    public List<Project> findByStartApproval(LocalDate startApproval) {
        return projectRepository.findByStartApproval(startApproval);
    }

    @Override
    public List<Project> findByStartDate(LocalDate startDate) {
        return projectRepository.findByStartDate(startDate);
    }

    @Override
    public List<Project> findByChance(BigDecimal chance) {
        return projectRepository.findByChance(chance);
    }

    @Override
    public List<Project> findByFundingRate(BigDecimal fundingRate) {
        return projectRepository.findByFundingRate(fundingRate);
    }

    @Override
    public List<Project> findByHourlyRateMueu(BigDecimal hourlyRateMueu) {
        return projectRepository.findByHourlyRateMueu(hourlyRateMueu);
    }

    @Override
    public List<Project> findByMaxHoursPerMonth(BigDecimal maxHoursPerMonth) {
        return projectRepository.findByMaxHoursPerMonth(maxHoursPerMonth);
    }

    @Override
    public List<Project> findByMaxHoursPerYear(BigDecimal maxHoursPerYear) {
        return projectRepository.findByMaxHoursPerYear(maxHoursPerYear);
    }

    @Override
    public List<Project> findByProductiveHoursPerYear(BigDecimal productiveHoursPerYear) {
        return projectRepository.findByProductiveHoursPerYear(productiveHoursPerYear);
    }

    @Override
    public List<Project> findByShareResearch(BigDecimal shareResearch) {
        return projectRepository.findByShareResearch(shareResearch);
    }

    @Override
    public List<Project> findByStuffFlat(BigDecimal stuffFlat) {
        return projectRepository.findByStuffFlat(stuffFlat);
    }

    @Override
    public List<Project> findByOrderIdFue(Integer orderIdFue) {
        return projectRepository.findByOrderIdFue(orderIdFue);
    }

    @Override
    public List<Project> findByOrderIdAdmin(Integer orderIdAdmin) {
        return projectRepository.findByOrderIdAdmin(orderIdAdmin);
    }

    @Override
    public List<Project> findByCommentContaining(String keyword) {
        return projectRepository.findByCommentContaining(keyword);
    }

    @Override
    public List<Project> findByFinanceAuthority(String authority) {
        return projectRepository.findByFinanceAuthority(authority);
    }

    @Override
    public List<Project> findByFundingLabel(String label) {
        return projectRepository.findByFundingLabel(label);
    }

    @Override
    public List<Project> findByNoteContaining(String text) {
        return projectRepository.findByNoteContaining(text);
    }

    @Override
    public List<Project> findByProjectLabel(String projectLabel) {
        return projectRepository.findByProjectLabel(projectLabel);
    }

    @Override
    public List<Project> findByProjectName(String projectName) {
        return projectRepository.findByProjectName(projectName);
    }

    @Override
    public List<Project> findByTitle(String title) {
        return projectRepository.findByTitle(title);
    }

    @Override
    public List<Project> findByCustomerId(Long customerId) {
        return projectRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Project> findByApprovalDateBetween(LocalDate start, LocalDate end) {
        return projectRepository.findByApprovalDateBetween(start, end);
    }

    @Override
    public List<Project> findByAuthorizationDateBefore(LocalDate date) {
        return projectRepository.findByAuthorizationDateBefore(date);
    }

    @Override
    public List<Project> findByEndDateAfter(LocalDate date) {
        return projectRepository.findByEndDateAfter(date);
    }

    @Override
    public List<Project> findByStartDateBetween(LocalDate start, LocalDate end) {
        return projectRepository.findByStartDateBetween(start, end);
    }

    @Override
    public List<Project> findByChanceGreaterThan(BigDecimal chance) {
        return projectRepository.findByChanceGreaterThan(chance);
    }

    @Override
    public List<Project> findByFundingRateLessThan(BigDecimal fundingRate) {
        return projectRepository.findByFundingRateLessThan(fundingRate);
    }

    @Override
    public List<Project> findByHourlyRateMueuBetween(BigDecimal min, BigDecimal max) {
        return projectRepository.findByHourlyRateMueuBetween(min, max);
    }

    @Override
    public List<Project> findByProjectNameContainingIgnoreCase(String name) {
        return projectRepository.findByProjectNameContainingIgnoreCase(name);
    }

    @Override
    public List<Project> findByFundingLabelStartingWith(String prefix) {
        return projectRepository.findByFundingLabelStartingWith(prefix);
    }

    @Override
    public List<Project> findByTitleEndingWith(String suffix) {
        return projectRepository.findByTitleEndingWith(suffix);
    }

    @Override
    public List<Project> findByCustomerIdOrderByStartDateDesc(Long customerId) {
        return projectRepository.findByCustomerIdOrderByStartDateDesc(customerId);
    }
}