package com.iws_manager.iws_manager_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.ReminderLevel;
import com.iws_manager.iws_manager_api.repositories.ReminderLevelRepository;
import com.iws_manager.iws_manager_api.services.interfaces.ReminderLevelService;
import jakarta.persistence.EntityNotFoundException;

/**
 * Implementation of the {@link ReminderLevelService} interface for managing ReminderLevel entities.
 * Provides CRUD operations and business logic for ReminderLevel management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class ReminderLevelServiceImpl implements ReminderLevelService {

    private final ReminderLevelRepository reminderLevelRepository;
    
    /**
     * Constructs a new ReminderLevelService with the required repository dependency.
     * 
     * @param reminderLevelRepository the repository for ReminderLevel entity operations
     */
    @Autowired
    public ReminderLevelServiceImpl(ReminderLevelRepository reminderLevelRepository) {
        this.reminderLevelRepository = reminderLevelRepository;
    }

    /**
     * Creates and persists a new ReminderLevel entity.
     * 
     * @param reminderLevel the ReminderLevel entity to be created
     * @return the persisted ReminderLevel entity with generated ID
     * @throws IllegalArgumentException if the ReminderLevel parameter is null
     */
    @Override
    public ReminderLevel create(ReminderLevel reminderLevel) {
        if (reminderLevel == null) {
            throw new IllegalArgumentException("ReminderLevel cannot be null");
        }
        return reminderLevelRepository.save(reminderLevel);
    }

    /**
     * Retrieves a ReminderLevel by its unique identifier.
     * 
     * @param id the ID of the ReminderLevel to retrieve
     * @return an Optional containing the found ReminderLevel, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ReminderLevel> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return reminderLevelRepository.findById(id);
    }

    /**
     * Retrieves all ReminderLevel entities from the database, ordered by levelNo in ascending order.
     * 
     * @return a List of all ReminderLevel entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<ReminderLevel> findAll() {
        return reminderLevelRepository.findAllByOrderByLevelNoAsc();
    }

    /**
     * Updates an existing ReminderLevel entity.
     * 
     * @param id the ID of the ReminderLevel to update
     * @param reminderLevelDetails the ReminderLevel object containing updated fields
     * @return the updated ReminderLevel entity
     * @throws RuntimeException if no ReminderLevel exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public ReminderLevel update(Long id, ReminderLevel reminderLevelDetails) {
        if (id == null || reminderLevelDetails == null) {
            throw new IllegalArgumentException("ID and reminder level details cannot be null");
        }
        
        return reminderLevelRepository.findById(id)
                .map(existingReminderLevel -> {
                    existingReminderLevel.setFee(reminderLevelDetails.getFee());
                    existingReminderLevel.setInterestRate(reminderLevelDetails.getInterestRate());
                    existingReminderLevel.setLevelNo(reminderLevelDetails.getLevelNo());
                    existingReminderLevel.setPayPeriod(reminderLevelDetails.getPayPeriod());
                    existingReminderLevel.setReminderText(reminderLevelDetails.getReminderText());
                    existingReminderLevel.setReminderTitle(reminderLevelDetails.getReminderTitle());

                    return reminderLevelRepository.save(existingReminderLevel);
                })
                .orElseThrow(() -> new RuntimeException("ReminderLevel not found with id: " + id));
    }

    /**
     * Deletes a ReminderLevel entity by its ID.
     * 
     * @param id the ID of the ReminderLevel to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        if (!reminderLevelRepository.existsById(id)) {  
            throw new EntityNotFoundException("ReminderLevel not found with id: " + id);
        }
        reminderLevelRepository.deleteById(id);
    }
}