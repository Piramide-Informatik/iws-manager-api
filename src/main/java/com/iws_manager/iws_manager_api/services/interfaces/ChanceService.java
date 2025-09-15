package com.iws_manager.iws_manager_api.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.Chance;

public interface ChanceService {
    Chance create(Chance chance);
    Optional<Chance> findById(Long id);
    List<Chance> findAll();
    Chance update(Long id, Chance chanceDetails);
    void delete(Long id);
}