package com.iws_manager.iws_manager_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.FundingProgram;
import com.iws_manager.iws_manager_api.repositories.FundingProgramRepository;
import com.iws_manager.iws_manager_api.services.interfaces.FundingProgramService;
import jakarta.persistence.EntityNotFoundException;

/**
 * Implementation of the {@link FundingProgramService} interface for managing FundingProgram entities.
 * Provides CRUD operations and business logic for FundingProgram management.
 *
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class FundingProgramServiceImpl implements FundingProgramService {

    private final FundingProgramRepository fundingProgramRepository;

    /**
     * Constructs a new FundingProgramService with the required repository dependency.
     *
     * @param fundingProgramRepository the repository for FundingProgram entity operations
     */
    @Autowired
    public FundingProgramServiceImpl(FundingProgramRepository fundingProgramRepository) {
        this.fundingProgramRepository = fundingProgramRepository;
    }

    /**
     * Creates and persists a new FundingProgram entity.
     *
     * @param fundingProgram the FundingProgram entity to be created
     * @return the persisted FundingProgram entity with generated ID
     * @throws IllegalArgumentException if the fundingProgram parameter is null
     */
    @Override
    public FundingProgram create(FundingProgram fundingProgram) {
        if (fundingProgram == null) {
            throw new IllegalArgumentException("FundingProgram cannot be null");
        }
        return fundingProgramRepository.save(fundingProgram);
    }

    /**
     * Retrieves a FundingProgram by its unique identifier.
     *
     * @param id the ID of the FundingProgram to retrieve
     * @return an Optional containing the found FundingProgram, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FundingProgram> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return fundingProgramRepository.findById(id);
    }

    /**
     * Retrieves all FundingProgram entities from the database.
     *
     * @return a List of all FundingProgram entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<FundingProgram> findAll() {
        return fundingProgramRepository.findAllByOrderByNameAsc();
    }

    /**
     * Updates an existing FundingProgram entity.
     *
     * @param id the ID of the FundingProgram to update
     * @param fundingProgramDetails the FundingProgram object containing updated fields
     * @return the updated FundingProgram entity
     * @throws RuntimeException if no FundingProgram exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public FundingProgram update(Long id, FundingProgram fundingProgramDetails) {
        if (id == null || fundingProgramDetails == null) {
            throw new IllegalArgumentException("ID and funding program details cannot be null");
        }
        return fundingProgramRepository.findById(id)
                .map(existing -> {
                    existing.setDefaultFundingRate(fundingProgramDetails.getDefaultFundingRate());
                    existing.setDefaultHoursPerYear(fundingProgramDetails.getDefaultHoursPerYear());
                    existing.setDefaultResearchShare(fundingProgramDetails.getDefaultResearchShare());
                    existing.setDefaultStuffFlat(fundingProgramDetails.getDefaultStuffFlat());
                    existing.setName(fundingProgramDetails.getName());
                    return fundingProgramRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("FundingProgram not found with id: " + id));
    }

    /**
     * Deletes a FundingProgram entity by its ID.
     *
     * @param id the ID of the FundingProgram to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        if (!fundingProgramRepository.existsById(id)) {
            throw new EntityNotFoundException("FundingProgram not found with id: " + id);
        }
        
        fundingProgramRepository.deleteById(id);
    }
}
