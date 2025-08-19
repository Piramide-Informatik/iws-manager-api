package com.iws_manager.iws_manager_api.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.CostType;

public interface CostTypeService {
    CostType create(CostType costType);
    Optional<CostType> findById(Long id);
    List<CostType> findAll();
    CostType update(Long id, CostType costTypeDetails);
    void delete(Long id);
}
