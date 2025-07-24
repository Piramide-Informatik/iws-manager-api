package com.iws_manager.iws_manager_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.Subcontract;
import com.iws_manager.iws_manager_api.repositories.SubcontractRepository;
import com.iws_manager.iws_manager_api.services.interfaces.SubcontractService;

/**
 * Implementation of the {@link SubcontractService} interface for managing Branch entities.
 * Provides CRUD operations and business logic for Subcontract management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class SubcontractServiceImpl implements SubcontractService {

    private final SubcontractRepository subcontractRepository;
    
    /**
     * Constructs a new SubcontractService with the required repository dependency.
     * 
     * @param subcontractRepository the repository for Subcontract entity operations
     */
    @Autowired
    public SubcontractServiceImpl(SubcontractRepository subcontractRepository) {
        this.subcontractRepository = subcontractRepository;
    }


    /**
     * Creates and persists a new Subcontract entity.
     * 
     * @param subcontract the Subcontract entity to be created
     * @return the persisted Subcontract entity with generated ID
     * @throws IllegalArgumentException if the Subcontract parameter is null
     */
    @Override
    public Subcontract create(Subcontract subcontract) {
        if (subcontract == null) {
            throw new IllegalArgumentException("Subcontract cannot be null");
        }
        return subcontractRepository.save(subcontract);
    }

    /**
     * Retrieves a Subcontract by its unique identifier.
     * 
     * @param id the ID of the Subcontract to retrieve
     * @return an Optional containing the found Subcontract, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Subcontract> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return subcontractRepository.findById(id);
    }

    /**
     * Retrieves all Subcontract entities from the database.
     * 
     * @return a List of all Subcontract entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Subcontract> findAll() {
        return subcontractRepository.findAllByOrderByContractTitleAsc();
    }

    /**
     * Updates an existing Subcontract entity.
     * 
     * @param id the ID of the Subcontract to update
     * @param branchDetails the Subcontract object containing updated fields
     * @return the updated Subcontract entity
     * @throws RuntimeException if no Subcontract exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public Subcontract update(Long id, Subcontract subcontractDetails) {
        if (id == null || subcontractDetails == null) {
            throw new IllegalArgumentException("ID and subcontract details cannot be null");
        }
        
        return  subcontractRepository.findById(id)
                .map(existingSubcontract -> {
                    existingSubcontract.setAfamonths(subcontractDetails.getAfamonths());
                    existingSubcontract.setContractor(subcontractDetails.getContractor());
                    existingSubcontract.setContractTitle(subcontractDetails.getContractTitle());
                    existingSubcontract.setCustomer(subcontractDetails.getCustomer());
                    existingSubcontract.setDate(subcontractDetails.getDate());
                    existingSubcontract.setDescription(subcontractDetails.getDescription());
                    existingSubcontract.setInvoiceAmount(subcontractDetails.getInvoiceAmount());
                    existingSubcontract.setInvoiceDate(subcontractDetails.getInvoiceDate());
                    existingSubcontract.setInvoiceGross(subcontractDetails.getInvoiceGross());
                    existingSubcontract.setInvoiceNet(subcontractDetails.getInvoiceNet());
                    existingSubcontract.setInvoiceNo(subcontractDetails.getInvoiceNo());
                    existingSubcontract.setIsAfa(subcontractDetails.getIsAfa());
                    existingSubcontract.setNetOrGross(subcontractDetails.getNetOrGross());
                    existingSubcontract.setNote(subcontractDetails.getNote());
                    existingSubcontract.setProjectCostCenter(subcontractDetails.getProjectCostCenter());

                    return subcontractRepository.save(existingSubcontract);
                })
                .orElseThrow(() -> new RuntimeException("Subcontract not found with id: " + id));
    }

    /**
     * Deletes a Subcontract entity by its ID.
     * 
     * @param id the ID of the Subcontract to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        subcontractRepository.deleteById(id);
    }

    @Override
    public List<Subcontract> findByContractorId(Long contractorId) {
        return subcontractRepository.findByContractorId(contractorId);
    }

    @Override
    public List<Subcontract> findByCustomerId(Long customerId) {
        return subcontractRepository.findByCustomerIdOrderByContractTitleAsc(customerId);
    }

    @Override
    public List<Subcontract> findByProjectCostCenterId(Long projectCostCenterId) {
        return subcontractRepository.findByProjectCostCenterId(projectCostCenterId);
    }
}