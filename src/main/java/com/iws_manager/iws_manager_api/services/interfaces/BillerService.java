package com.iws_manager.iws_manager_api.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.Biller;

public interface BillerService {
    Biller create(Biller biller);
    Optional<Biller> findById(Long id);
    List<Biller> findAll();
    Biller update(Long id, Biller billerDetails);
    void delete(Long id);
}
