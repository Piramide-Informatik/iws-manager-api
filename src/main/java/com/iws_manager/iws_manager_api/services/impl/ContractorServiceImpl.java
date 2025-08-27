package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.Contractor;
import com.iws_manager.iws_manager_api.repositories.ContractorRepository;
import com.iws_manager.iws_manager_api.services.interfaces.ContractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
                .orElseThrow(()-> new RuntimeException("Contractor not found with id: "+ id));
    }

    @Override
    public void delete(Long id) {
        if (id==null){
            throw new IllegalArgumentException("ID cannot be null");
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
}
