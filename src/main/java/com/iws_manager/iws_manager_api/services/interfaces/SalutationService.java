package com.iws_manager.iws_manager_api.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.Salutation;

public interface SalutationService {
    Salutation create(Salutation salutation);
    Optional<Salutation> findById(Long id);
    List<Salutation> findAll();
    Salutation update (Long id, Salutation salutationDetails);
    void delete(Long id);    
}
