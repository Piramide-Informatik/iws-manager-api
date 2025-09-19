package com.iws_manager.iws_manager_api.services.interfaces;

import com.iws_manager.iws_manager_api.models.SystemParameter;

import java.util.List;
import java.util.Optional;

public interface SystemParameterService {
    SystemParameter create(SystemParameter systemParameter);
    Optional<SystemParameter> findById(Long id);
    List<SystemParameter> findAll();
    SystemParameter update(Long id, SystemParameter systemParameterDetails);
    void delete(Long id);
}
