package com.iws_manager.iws_manager_api.services.interfaces;

import com.iws_manager.iws_manager_api.models.SystemFunction;

import java.util.List;
import java.util.Optional;

public interface SystemFunctionService {
    SystemFunction create(SystemFunction systemFunction);
    Optional<SystemFunction> findById(Long id);
    List<SystemFunction> findAll();
    SystemFunction update(Long id, SystemFunction systemFunctionDetails);
    void delete(Long id);
    List<SystemFunction> getFunctionsByModuleId(Long moduleId);
}
