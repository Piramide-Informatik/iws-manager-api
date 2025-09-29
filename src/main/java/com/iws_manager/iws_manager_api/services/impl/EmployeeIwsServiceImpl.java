package com.iws_manager.iws_manager_api.services.impl;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.EmployeeIws;
import com.iws_manager.iws_manager_api.repositories.EmployeeIwsRepository;
import com.iws_manager.iws_manager_api.services.interfaces.EmployeeIwsService;

/**
 * Implementation of the {@link EmployeeIwsService} interface for managing EmployeeIws entities.
 * Provides CRUD operations and business logic for EmployeeIws management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class EmployeeIwsServiceImpl implements EmployeeIwsService {

    private final EmployeeIwsRepository employeeIwsRepository;
    
    /**
     * Constructs a new EmployeeIwsService with the required repository dependency.
     * 
     * @param employeeIwsRepository the repository for employeeIws entity operations
     */
    @Autowired
    public EmployeeIwsServiceImpl(EmployeeIwsRepository employeeIwsRepository) {
        this.employeeIwsRepository = employeeIwsRepository;
    }


    /**
     * Creates and persists a new employeeIws entity.
     * 
     * @param employeeIws the employeeIws entity to be created
     * @return the persisted employeeIws entity with generated ID
     * @throws IllegalArgumentException if the employeeIws parameter is null
     */
    @Override
    public EmployeeIws create(EmployeeIws employeeIws) {
        if (employeeIws == null) {
            throw new IllegalArgumentException("EmployeeIws cannot be null");
        }
        if (employeeIws.getEmployeeNo() == null) {
            Integer maxSeq = employeeIwsRepository.findMaxEmployeeNo();
            employeeIws.setEmployeeNo(maxSeq + 1);
        }
        return employeeIwsRepository.save(employeeIws);
    }

    /**
     * Retrieves a employeeIws by its unique identifier.
     * 
     * @param id the ID of the employeeIws to retrieve
     * @return an Optional containing the found employeeIws, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeIws> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return employeeIwsRepository.findById(id);
    }

    /**
     * Retrieves all employeeIws entities from the database.
     * 
     * @return a List of all employeeIws entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmployeeIws> findAll() {
        return employeeIwsRepository.findAll();
    }

    /**
     * Updates an existing employeeIws entity.
     * 
     * @param id the ID of the employeeIws to update
     * @param employeeIwsDetails the employeeIws object containing updated fields
     * @return the updated employeeIws entity
     * @throws RuntimeException if no employeeIws exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public EmployeeIws update(Long id, EmployeeIws employeeIwsDetails) {
        if (id == null || employeeIwsDetails == null) {
            throw new IllegalArgumentException("ID and employeeIws details cannot be null");
        }
        
        return  employeeIwsRepository.findById(id)
                .map(existingEmployeeIws -> {
                    existingEmployeeIws.setActive(employeeIwsDetails.getActive());
                    existingEmployeeIws.setEmployeeLabel(employeeIwsDetails.getEmployeeLabel());
                    existingEmployeeIws.setEmployeeNo(employeeIwsDetails.getEmployeeNo());
                    existingEmployeeIws.setEndDate(employeeIwsDetails.getEndDate());
                    existingEmployeeIws.setFirstname(employeeIwsDetails.getFirstname());
                    existingEmployeeIws.setLastname(employeeIwsDetails.getLastname());
                    existingEmployeeIws.setMail(employeeIwsDetails.getMail());
                    existingEmployeeIws.setStartDate(employeeIwsDetails.getStartDate());
                    existingEmployeeIws.setTeamIws(employeeIwsDetails.getTeamIws());
                    existingEmployeeIws.setUser(employeeIwsDetails.getUser());

                    return employeeIwsRepository.save(existingEmployeeIws);
                })
                .orElseThrow(() -> new RuntimeException("EmployeeIws not found with id: " + id));
    }

    /**
     * Deletes a employeeIws entity by its ID.
     * 
     * @param id the ID of the employeeIws to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        employeeIwsRepository.deleteById(id);
    }


        // FIND ALL - ORDER METHODS
    @Override
    @Transactional(readOnly = true)
    public List<EmployeeIws> getAllByOrderByLastnameAsc() {
        return employeeIwsRepository.findAllByOrderByLastnameAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeIws> getAllByOrderByFirstnameAsc() {
        return employeeIwsRepository.findAllByOrderByFirstnameAsc();
    }

    @Override
    public List<EmployeeIws> getAllByOrderByIdDesc() {
        return employeeIwsRepository.findAllByOrderByIdDesc();
    }

    // PROPERTIES METHODS - ACTUALIZADOS con los nuevos nombres de métodos
    @Override
    @Transactional(readOnly = true)
    public List<EmployeeIws> getByActive(Integer active) {
        return employeeIwsRepository.findByActive(active);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeIws> getByEmployeeLabel(String employeelabel) {
        return employeeIwsRepository.findByEmployeeLabel(employeelabel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeIws> getByEmployeeNo(Integer employeeno) {
        return employeeIwsRepository.findByEmployeeNo(employeeno);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeIws> getByEndDate(LocalDate enddate) {
        return employeeIwsRepository.findByEndDate(enddate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeIws> getByFirstname(String firstname) {
        return employeeIwsRepository.findByFirstname(firstname);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeIws> getByLastname(String lastname) {
        return employeeIwsRepository.findByLastname(lastname);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeIws> getByMail(String mail) {
        return employeeIwsRepository.findByMail(mail);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeIws> getByStartDate(LocalDate startdate) {
        return employeeIwsRepository.findByStartDate(startdate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeIws> getByTeamIwsId(Long teamIwsId) {
        return employeeIwsRepository.findByTeamIwsId(teamIwsId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeIws> getByUserId(Long userId) {
        return employeeIwsRepository.findByUserId(userId);
    }

    // HELPERS METHODS - ACTUALIZADOS
    @Override
    @Transactional(readOnly = true)
    public List<EmployeeIws> getByStartDateAfter(LocalDate date) {
        return employeeIwsRepository.findByStartDateAfter(date);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeIws> getByStartDateBefore(LocalDate date) {
        return employeeIwsRepository.findByStartDateBefore(date);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeIws> getByStartDateBetween(LocalDate start, LocalDate end) {
        return employeeIwsRepository.findByStartDateBetween(start, end);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeIws> getByEndDateAfter(LocalDate date) {
        return employeeIwsRepository.findByEndDateAfter(date);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeIws> getByEndDateBefore(LocalDate date) {
        return employeeIwsRepository.findByEndDateBefore(date);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeIws> getByEndDateBetween(LocalDate start, LocalDate end) {
        return employeeIwsRepository.findByEndDateBetween(start, end);
    }

    // ACTIVE - ORDER METHODS - ACTUALIZADOS
    @Override
    @Transactional(readOnly = true)
    public List<EmployeeIws> getByActiveOrderByFirstnameAsc(Integer active) {
        return employeeIwsRepository.findByActiveOrderByFirstnameAsc(active);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeIws> getByActiveOrderByLastnameAsc(Integer active) {
        return employeeIwsRepository.findByActiveOrderByLastnameAsc(active);
    }
}