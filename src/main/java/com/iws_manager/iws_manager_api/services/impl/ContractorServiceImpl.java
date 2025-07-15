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
    private final ContractorRepository contractRepository;
    @Autowired
    public ContractorServiceImpl(ContractorRepository contractRepository) {
        this.contractRepository = contractRepository;
    }


    @Override
    public Contractor create(Contractor contractor) {
        if(contractor == null){
            throw new IllegalArgumentException("Contractor cannot be null");
        }
        return contractRepository.save(contractor);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Contractor> findById(Long id) {
        if(id == null){
            throw new IllegalArgumentException("ID cannot be null");
        }
        return  contractRepository.findById(id);
    }

    @Override
    public List<Contractor> findAll() {
        return contractRepository.findAllByOrderByNameAsc();
    }

    @Override
    public Contractor update(Long id, Contractor contractorDetails) {
        if (id==null || contractorDetails == null){
            throw new IllegalArgumentException("ID and contractor Details cannot be null");
        }
        return contractRepository.findById(id)
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


                    return contractRepository.save(existingContractor);
                })
                .orElseThrow(()-> new RuntimeException("Contractor not found with id: "+ id));
    }

    @Override
    public void delete(Long id) {
        if (id==null){
            throw new IllegalArgumentException("ID cannot be null");
        }
        contractRepository.deleteById(id);
    }
}
