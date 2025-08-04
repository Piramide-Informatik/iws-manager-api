package com.iws_manager.iws_manager_api.services.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.SubcontractProject;
import com.iws_manager.iws_manager_api.repositories.SubcontractProjectRepository;
import com.iws_manager.iws_manager_api.services.interfaces.SubcontractProjectService;

/**
 * Implementation of the {@link SubcontractProjectService} interface for managing Branch entities.
 * Provides CRUD operations and business logic for SubcontractProject management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class SubcontractProjectServiceImpl implements SubcontractProjectService {

    private final SubcontractProjectRepository subcontractProjectRepository;
    
    /**
     * Constructs a new SubcontractProjectService with the required repository dependency.
     * 
     * @param subcontractProjectRepository the repository for SubcontractProject entity operations
     */
    @Autowired
    public SubcontractProjectServiceImpl(SubcontractProjectRepository subcontractProjectRepository) {
        this.subcontractProjectRepository = subcontractProjectRepository;
    }


    /**
     * Creates and persists a new SubcontractProject entity.
     * 
     * @param subcontractProject the SubcontractProject entity to be created
     * @return the persisted SubcontractProject entity with generated ID
     * @throws IllegalArgumentException if the SubcontractProject parameter is null
     */
    @Override
    public SubcontractProject create(SubcontractProject subcontractProject) {
        if (subcontractProject == null) {
            throw new IllegalArgumentException("SubcontractProject cannot be null");
        }
        return subcontractProjectRepository.save(subcontractProject);
    }

    /**
     * Retrieves a SubcontractProject by its unique identifier.
     * 
     * @param id the ID of the SubcontractProject to retrieve
     * @return an Optional containing the found SubcontractProject, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SubcontractProject> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return subcontractProjectRepository.findById(id);
    }

    /**
     * Retrieves all SubcontractProject entities from the database.
     * 
     * @return a List of all SubcontractProject entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<SubcontractProject> findAll() {
        return subcontractProjectRepository.findAll();
    }

    /**
     * Updates an existing SubcontractProject entity.
     * 
     * @param id the ID of the SubcontractProject to update
     * @param branchDetails the SubcontractProject object containing updated fields
     * @return the updated SubcontractProject entity
     * @throws RuntimeException if no SubcontractProject exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public SubcontractProject update(Long id, SubcontractProject subcontractProjectDetails) {
        if (id == null || subcontractProjectDetails == null) {
            throw new IllegalArgumentException("ID and subcontractProject details cannot be null");
        }
        
        return  subcontractProjectRepository.findById(id)
                .map(existingSubcontractProject -> {
                    existingSubcontractProject.setSubcontractYear(subcontractProjectDetails.getSubcontractYear());
                    existingSubcontractProject.setAmount(subcontractProjectDetails.getAmount());
                    existingSubcontractProject.setMonths(subcontractProjectDetails.getMonths());
                    existingSubcontractProject.setProject(subcontractProjectDetails.getProject());
                    existingSubcontractProject.setSubcontract(subcontractProjectDetails.getSubcontract());
                    existingSubcontractProject.setShare(subcontractProjectDetails.getShare());
                    existingSubcontractProject.setYear(subcontractProjectDetails.getYear());
        
                    return subcontractProjectRepository.save(existingSubcontractProject);
                })
                .orElseThrow(() -> new RuntimeException("SubcontractProject not found with id: " + id));
    }

    /**
     * Deletes a SubcontractProject entity by its ID.
     * 
     * @param id the ID of the SubcontractProject to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        subcontractProjectRepository.deleteById(id);
    }

    @Override
    public List<SubcontractProject> getByMonths(Integer months) {
        return subcontractProjectRepository.findByMonths(months);
    }

    @Override
    public List<SubcontractProject> getByAmount(BigDecimal amount){
        return subcontractProjectRepository.findByAmount(amount);
    }

    @Override
    public List<SubcontractProject> getByShare(BigDecimal share){
        return subcontractProjectRepository.findByShare(share);
    }

    @Override
    public List<SubcontractProject> getByYear(LocalDate year){
        return subcontractProjectRepository.findByYear(year);
    }

    @Override
    public List<SubcontractProject> getBySubcontractYearId(Long subcontractYearId) {
        return subcontractProjectRepository.findBySubcontractYearId(subcontractYearId);
    }

    @Override
    public List<SubcontractProject> getByProjectId(Long projectId) {
        return subcontractProjectRepository.findByProjectId(projectId);
    }

    @Override
    public List<SubcontractProject> getBySubcontractId(Long subcontractId) {
        return subcontractProjectRepository.findBySubcontractId(subcontractId);
    }

    @Override
    public List<SubcontractProject> getByShareBetween(BigDecimal start, BigDecimal end) {
        return subcontractProjectRepository.findByShareBetween(start, end);
    }    

    @Override
    public List<SubcontractProject> getByMonthsGreaterThan(Integer months) {
        return subcontractProjectRepository.findByMonthsGreaterThan(months);
    }

    @Override
    public List<SubcontractProject> getByMonthsLessThan(Integer months) {
        return subcontractProjectRepository.findByMonthsLessThan(months);
    }

    @Override
    public List<SubcontractProject> getByYearAfter(LocalDate date) {
        return subcontractProjectRepository.findByYearAfter(date);
    }

    @Override
    public List<SubcontractProject> getByYearBefore(LocalDate date) {
        return subcontractProjectRepository.findByYearBefore(date);
    }
}