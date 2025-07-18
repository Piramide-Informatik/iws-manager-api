package com.iws_manager.iws_manager_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.Employee;
import com.iws_manager.iws_manager_api.repositories.EmployeeRepository;
import com.iws_manager.iws_manager_api.services.interfaces.EmployeeService;

/**
 * Implementation of the {@link EmployeeService} interface for managing Branch entities.
 * Provides CRUD operations and business logic for Employee management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    
    /**
     * Constructs a new EmployeeService with the required repository dependency.
     * 
     * @param employeeRepository the repository for Branch entity operations
     */
    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    /**
     * Creates and persists a new Branch entity.
     * 
     * @param branch the Branch entity to be created
     * @return the persisted Branch entity with generated ID
     * @throws IllegalArgumentException if the branch parameter is null
     */
    @Override
    public Employee create(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        return employeeRepository.save(employee);
    }

    /**
     * Retrieves a Branch by its unique identifier.
     * 
     * @param id the ID of the Branch to retrieve
     * @return an Optional containing the found Branch, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Employee> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return employeeRepository.findById(id);
    }

    /**
     * Retrieves all Branch entities from the database.
     * 
     * @return a List of all Branch entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Employee> findAll() {
        return employeeRepository.findAllByOrderByEmployeenoAsc();
    }

    /**
     * Updates an existing Branch entity.
     * 
     * @param id the ID of the Branch to update
     * @param branchDetails the Branch object containing updated fields
     * @return the updated Branch entity
     * @throws RuntimeException if no Branch exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public Employee update(Long id, Employee employeeDetails) {
        if (id == null || employeeDetails == null) {
            throw new IllegalArgumentException("ID and employee details cannot be null");
        }
        
        return  employeeRepository.findById(id)
                .map(existingEmployee -> {
                    existingEmployee.setFirstname(employeeDetails.getFirstname());
                    existingEmployee.setLastname(employeeDetails.getLastname());
                    existingEmployee.setEmail(employeeDetails.getEmail());
                    existingEmployee.setPhone(employeeDetails.getPhone());
                    existingEmployee.setEmployeeno(employeeDetails.getEmployeeno());
                    existingEmployee.setLabel(employeeDetails.getLabel());
                    existingEmployee.setCustomer(employeeDetails.getCustomer());
                    existingEmployee.setTitle(employeeDetails.getTitle());
                    existingEmployee.setSalutation(employeeDetails.getSalutation());
                    existingEmployee.setQualificationFZ(employeeDetails.getQualificationFZ());
                    existingEmployee.setCoentrepreneursince(employeeDetails.getCoentrepreneursince());
                    existingEmployee.setGeneralmanagersince(employeeDetails.getGeneralmanagersince());
                    existingEmployee.setShareholdersince(employeeDetails.getShareholdersince());
                    existingEmployee.setSoleproprietorsince(employeeDetails.getSoleproprietorsince());
                    existingEmployee.setQualificationkmui(employeeDetails.getQualificationkmui());

                    return employeeRepository.save(existingEmployee);
                })
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    /**
     * Deletes a Branch entity by its ID.
     * 
     * @param id the ID of the Branch to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public List<Employee> findByLastname(String lastname) {
        return employeeRepository.findByLastname(lastname);
    }

    @Override
    public Optional<Employee> findByEmail(String email) {
        return Optional.ofNullable(employeeRepository.findByEmail(email));
    }

    @Override
    public List<Employee> findByTitleId(Long titleId) {
        return employeeRepository.findByTitleId(titleId);
    }

    @Override
    public List<Employee> findBySalutationId(Long salutationId) {
        return employeeRepository.findBySalutationId(salutationId);
    }

     @Override
    public List<Employee> findByQualificationFZId(Long qualificationFZId) {
        return employeeRepository.findByQualificationFZId(qualificationFZId);
    }

    @Override
    public List<Employee> findByCustomerId(Long customerId) {
        return employeeRepository.findByCustomerId(customerId);
    }
}