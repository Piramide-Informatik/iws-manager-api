package com.iws_manager.iws_manager_api.services.interfaces;

import com.iws_manager.iws_manager_api.models.IwsCommission;

import java.util.List;
import java.util.Optional;

public interface IwsCommissionService {
    IwsCommission create(IwsCommission iwsCommission);
    Optional<IwsCommission> findById(Long id);
    List<IwsCommission> findAll();
    IwsCommission update(Long id, IwsCommission iwsCommissionDetails);
    void delete(Long id);
}
