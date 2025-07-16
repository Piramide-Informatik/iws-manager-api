package com.iws_manager.iws_manager_api.services.interfaces;



import com.iws_manager.iws_manager_api.models.Contractor;

import java.util.List;
import java.util.Optional;

public interface ContractorService {
    Contractor create(Contractor contractor);
    Optional<Contractor> findById(Long id);
    List<Contractor> findAll();
    Contractor update(Long id, Contractor contractorDetails);
    void delete(Long id);

    List<Contractor> getContractorsByCustomerId(Long customerId);
    List<Contractor> getContractorsByCountryId(Long countryId);
}
