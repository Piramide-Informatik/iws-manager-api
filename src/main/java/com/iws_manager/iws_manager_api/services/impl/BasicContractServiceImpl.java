package com.iws_manager.iws_manager_api.services.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.BasicContract;
import com.iws_manager.iws_manager_api.repositories.BasicContractRepository;
import com.iws_manager.iws_manager_api.services.interfaces.BasicContractService;

/**
 * Implementation of the {@link BasicContractService} interface for managing Branch entities.
 * Provides CRUD operations and business logic for BasicContract management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class BasicContractServiceImpl implements BasicContractService {

    private final BasicContractRepository basicContractRepository;
    
    /**
     * Constructs a new BasicContractService with the required repository dependency.
     * 
     * @param basicContractRepository the repository for BasicContract entity operations
     */
    @Autowired
    public BasicContractServiceImpl(BasicContractRepository basicContractRepository) {
        this.basicContractRepository = basicContractRepository;
    }


    /**
     * Creates and persists a new BasicContract entity.
     * 
     * @param basicContract the BasicContract entity to be created
     * @return the persisted BasicContract entity with generated ID
     * @throws IllegalArgumentException if the BasicContract parameter is null
     */
    @Override
    public BasicContract create(BasicContract basicContract) {
        if (basicContract == null) {
            throw new IllegalArgumentException("BasicContract cannot be null");
        }
        return basicContractRepository.save(basicContract);
    }

    /**
     * Retrieves a BasicContract by its unique identifier.
     * 
     * @param id the ID of the BasicContract to retrieve
     * @return an Optional containing the found BasicContract, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BasicContract> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return basicContractRepository.findById(id);
    }

    /**
     * Retrieves all BasicContract entities from the database.
     * 
     * @return a List of all BasicContract entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<BasicContract> findAll() {
        return basicContractRepository.findAll();
    }

    /**
     * Updates an existing BasicContract entity.
     * 
     * @param id the ID of the BasicContract to update
     * @param branchDetails the BasicContract object containing updated fields
     * @return the updated BasicContract entity
     * @throws RuntimeException if no BasicContract exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public BasicContract update(Long id, BasicContract basicContractDetails) {
        if (id == null || basicContractDetails == null) {
            throw new IllegalArgumentException("ID and basicContract details cannot be null");
        }
        
        return  basicContractRepository.findById(id)
                .map(existingBasicContract -> {
                    existingBasicContract.setConfirmationDate(basicContractDetails.getConfirmationDate());
                    existingBasicContract.setContractLabel(basicContractDetails.getContractLabel());
                    existingBasicContract.setContractNo(basicContractDetails.getContractNo());
                    existingBasicContract.setContractStatus(basicContractDetails.getContractStatus());
                    existingBasicContract.setContractTitle(basicContractDetails.getContractTitle());
                    existingBasicContract.setCustomer(basicContractDetails.getCustomer());
                    existingBasicContract.setDate(basicContractDetails.getDate());
                    existingBasicContract.setFundingProgram(basicContractDetails.getFundingProgram());
                    existingBasicContract.setEmployeeIws(basicContractDetails.getEmployeeIws());

                    return basicContractRepository.save(existingBasicContract);
                })
                .orElseThrow(() -> new RuntimeException("BasicContract not found with id: " + id));
    }

    /**
     * Deletes a BasicContract entity by its ID.
     * 
     * @param id the ID of the BasicContract to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        basicContractRepository.deleteById(id);
    }

    // finders for each property
    @Override
    public List<BasicContract> getByConfirmationDate(LocalDate confirmationDate) {
        return basicContractRepository.findByConfirmationDate(confirmationDate);
    }

    @Override
    public List<BasicContract> getByContractLabel(String contractLabel) {
        return basicContractRepository.findByContractLabel(contractLabel);
    }

    @Override
    public List<BasicContract> getByContractNo(Integer contractNo) {
        return basicContractRepository.findByContractNo(contractNo);
    }

    @Override
    public List<BasicContract> getByContractStatusId(Long contractStatusId) {
        return basicContractRepository.findByContractStatus_ContractStatusId(contractStatusId);
    }

    @Override
    public List<BasicContract> getByContractTitle(String contractTitle) {
        return basicContractRepository.findByContractTitle(contractTitle);
    }

    @Override
    public List<BasicContract> getByCustomerId(Long customerId) {
        return basicContractRepository.findByCustomer_CustomerId(customerId);
    }

    @Override
    public List<BasicContract> getByDate(LocalDate date) {
        return basicContractRepository.findByDate(date);
    }

    @Override
    public List<BasicContract> getByFundingProgramId(Long fundingProgramId) {
        return basicContractRepository.findByFundingProgram_FundingProgramId(fundingProgramId);
    }

    @Override
    public List<BasicContract> getByEmployeeIwsId(Long employeeIwsId) {
        return basicContractRepository.findByEmployeeIws_EmployeeIwsId(employeeIwsId);
    }

    // customer finders

    
    @Override
    public List<BasicContract> getByCustomerIdOrderByContractNoAsc(Long customerId) {
        return basicContractRepository.findByCustomer_CustomerIdOrderByContractNoAsc(customerId);
    }

    
    @Override
    public List<BasicContract> getByCustomerIdOrderByContractLabelAsc(Long customerId) {
        return basicContractRepository.findByCustomer_CustomerIdOrderByContractLabelAsc(customerId);
    }

    // Date range

    @Override
    public List<BasicContract> getByDateBetween(LocalDate startDate, LocalDate endDate) {
        return basicContractRepository.findByDateBetween(startDate, endDate);
    }

    @Override
    public List<BasicContract> getByConfirmationDateBetween(LocalDate startDate, LocalDate endDate) {
        return basicContractRepository.findByConfirmationDateBetween(startDate, endDate);
    }
}