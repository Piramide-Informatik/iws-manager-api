package com.iws_manager.iws_manager_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.EmploymentContract;
import com.iws_manager.iws_manager_api.repositories.EmploymentContractRepository;
import com.iws_manager.iws_manager_api.services.interfaces.EmploymentContractService;

import jakarta.persistence.EntityNotFoundException;

/**
 * Implementation of the {@link EmploymentContractService} interface for managing ContactPerson entities.
 * Provides CRUD operations and business logic for ContactPerson management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class EmploymentContractServiceImpl implements EmploymentContractService {

    private final EmploymentContractRepository employmentContractRepository;
    /**
     * Constructs a new EmploymentContractService with the required repository dependency.
     * 
     * @param employmentContractRepository the repository for ContactPerson entity operations
     */
    @Autowired
    public EmploymentContractServiceImpl(EmploymentContractRepository employmentContractRepository) {
        this.employmentContractRepository = employmentContractRepository;
    }

    /**
     * Creates and persists a new ContactPerson entity.
     * 
     * @param ContactPerson the ContactPerson entity to be created
     * @return the persisted ContactPerson entity with generated ID
     * @throws IllegalArgumentException if the ContactPerson parameter is null
     */
    @Override
    public EmploymentContract create(EmploymentContract employmentContract) {
        if (employmentContract == null) {
            throw new IllegalArgumentException("employmentContract cannot be null");
        }
        return employmentContractRepository.save(employmentContract);
    }

    /**
     * Retrieves a employmentContract by its unique identifier.
     * 
     * @param id the ID of the ContactPerson to retrieve
     * @return an Optional containing the found employmentContract, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EmploymentContract> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return employmentContractRepository.findById(id);
    }

    /**
     * Retrieves all employmentContracts entities from the database.
     * 
     * @return a List of all employmentContracts entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmploymentContract> findAll() {
        return employmentContractRepository.findAll();
    }

    /**
     * Updates an existing EmploymentContract entity.
     * 
     * @param id the ID of the EmploymentContract  to update
     * @param EmploymentContract Details the EmploymentContract object containing updated fields
     * @return the updated EmploymentContract entity
     * @throws RuntimeException if no EmploymentContract exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public EmploymentContract update(Long id, EmploymentContract employmentContractDetails) {
        if (id == null || employmentContractDetails == null) {
            throw new IllegalArgumentException("ID and employmentContract details cannot be null");
        }
        
        return employmentContractRepository.findById(id)
                .map(existingContract -> {
                    existingContract.setCustomer(employmentContractDetails.getCustomer());
                    existingContract.setEmployee(employmentContractDetails.getEmployee());
                    existingContract.setHourlyRate(employmentContractDetails.getHourlyRate());
                    existingContract.setHourlyRealRate(employmentContractDetails.getHourlyRealRate());
                    existingContract.setHoursPerWeek(employmentContractDetails.getHoursPerWeek());
                    existingContract.setMaxHoursPerDay(employmentContractDetails.getMaxHoursPerDay());
                    existingContract.setMaxHoursPerMonth(employmentContractDetails.getMaxHoursPerMonth());
                    existingContract.setSalaryPerMonth(employmentContractDetails.getSalaryPerMonth());
                    existingContract.setSpecialPayment(employmentContractDetails.getSpecialPayment());
                    existingContract.setStartDate(employmentContractDetails.getStartDate());
                    existingContract.setWorkShortTime(employmentContractDetails.getWorkShortTime());
                    return employmentContractRepository.save(existingContract);
                })
                .orElseThrow(() -> new RuntimeException("EmploymentContract not found with id: " + id));
    }

    /**
     * Deletes a EmploymentContract entity by its ID.
     * 
     * @param id the ID of the EmploymentContract to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (!employmentContractRepository.existsById(id)) {  
            throw new EntityNotFoundException("EmploymentContract not found with id: " + id);
        }
        
        employmentContractRepository.deleteById(id);
    }

     @Override
    public List<EmploymentContract> findByEmployeeId(Long employeeId) {
        return employmentContractRepository.findByEmployeeId(employeeId);
    }

    @Override
    public List<EmploymentContract> getByEmployeeIdOrderByStartDateAsc(Long employeeId) {
        return employmentContractRepository.findByEmployeeIdOrderByStartDateAsc(employeeId);
    }

    @Override
    public List<EmploymentContract> findByCustomerId(Long customerId) {
        return employmentContractRepository.findByCustomerId(customerId);
    }

    @Override
    public List<EmploymentContract> getByCustomerIdOrderByEmployeenoAsc(Long customerId) {
        return employmentContractRepository.findByCustomerIdOrderByEmployeenoAsc(customerId);
    }
}