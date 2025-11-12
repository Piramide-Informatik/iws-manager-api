package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.Contractor;
import com.iws_manager.iws_manager_api.repositories.ContractorRepository;
import com.iws_manager.iws_manager_api.services.interfaces.ContractorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.exception.exceptions.DuplicateResourceException;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class ContractorServiceImpl implements ContractorService {
    private final ContractorRepository contractorRepository;
    @Autowired
    public ContractorServiceImpl(ContractorRepository contractorRepository) {
        this.contractorRepository = contractorRepository;
    }


    @Override
    public Contractor create(Contractor contractor) {
        if(contractor == null){
            throw new IllegalArgumentException("Contractor cannot be null");
        }

        validateUniqueLabelForCreation(contractor.getLabel(), contractor.getCustomer().getId());

        return contractorRepository.save(contractor);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Contractor> findById(Long id) {
        if(id == null){
            throw new IllegalArgumentException("ID cannot be null");
        }
        return  contractorRepository.findById(id);
    }

    @Override
    public List<Contractor> findAll() {
        return contractorRepository.findAllByOrderByNameAsc();
    }

    @Override
    public Contractor update(Long id, Contractor contractorDetails) {
        if (id==null || contractorDetails == null){
            throw new IllegalArgumentException("ID and contractor Details cannot be null");
        }
        return contractorRepository.findById(id)
                .map(existingContractor -> {
                    Long customerId = existingContractor.getCustomer().getId();
                    validateUniqueLabelForUpdate(existingContractor, contractorDetails, id, customerId);
                    
                    existingContractor.setName(contractorDetails.getName());
                    existingContractor.setCity(contractorDetails.getCity());
                    existingContractor.setLabel(contractorDetails.getLabel());
                    existingContractor.setNumber(contractorDetails.getNumber());
                    existingContractor.setCountry(contractorDetails.getCountry());
                    existingContractor.setCustomer(contractorDetails.getCustomer());
                    existingContractor.setStreet(contractorDetails.getStreet());
                    existingContractor.setTaxNumber(contractorDetails.getTaxNumber());
                    existingContractor.setZipCode(contractorDetails.getZipCode());


                    return contractorRepository.save(existingContractor);
                })
                .orElseThrow(()-> new EntityNotFoundException("Contractor not found with id: "+ id));
    }

    @Override
    public void delete(Long id) {
        if (id==null){
            throw new IllegalArgumentException("ID cannot be null");
        }

        if (!contractorRepository.existsById(id)) {  
            throw new EntityNotFoundException("Contractor not found with id: " + id);
        }
        contractorRepository.deleteById(id);
    }

   @Override
    public List<Contractor> getContractorsByCustomerId(Long customerId) {
        return contractorRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Contractor> getContractorsByCountryId(Long countryId) {
        return contractorRepository.findByCountryId(countryId);
    }

    @Override
    public List<Contractor> getByCustomerIdOrderByLabelAsc(Long customerId) {
        return contractorRepository.findByCustomerIdOrderByLabelAsc(customerId);
    }

    @Override
    public List<Contractor> getByCustomerIdOrderByNameAsc(Long customerId) {
        return contractorRepository.findByCustomerIdOrderByNameAsc(customerId);
    }

    /**
     * Validates that the label is unique for creation (case-insensitive) within the same customer.
     */
    private void validateUniqueLabelForCreation(String label, Long customerId) {
        if (label != null && customerId != null && 
            contractorRepository.existsByLabelIgnoreCaseAndCustomerId(label, customerId)) {
            throw new DuplicateResourceException(
                "Contractor duplication with attribute 'label' = '" + label + "' for this customer"
            );
        }
    }

    /**
     * Validates that the label is unique for update, considering only other records (case-insensitive) within the same customer.
     */
    private void validateUniqueLabelForUpdate(Contractor existingContractor, 
                                            Contractor newContractor, 
                                            Long id, 
                                            Long customerId) {
       
        boolean labelChanged = !existingContractor.getLabel().equals(newContractor.getLabel());
        
        if (labelChanged) {
            boolean labelExists = contractorRepository.existsByLabelIgnoreCaseAndCustomerIdAndIdNot(
                newContractor.getLabel(), customerId, id);
            
            if (labelExists) {
                throw new DuplicateResourceException(
                    "Contractor duplication with attribute 'label' = '" + newContractor.getLabel() + "' for this customer"
                );
            }
        }
    }
}
