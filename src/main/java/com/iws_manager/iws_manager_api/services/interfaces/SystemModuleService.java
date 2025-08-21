package com.iws_manager.iws_manager_api.services.interfaces;

import com.iws_manager.iws_manager_api.models.SystemModule;

import java.util.List;
import java.util.Optional;

public interface SystemModuleService {
    SystemModule create(SystemModule systemModule);
    Optional<SystemModule> findById(Long id);
    List<SystemModule> findAll();
    SystemModule update(Long id, SystemModule systemModuleDetails);
    void delete(Long id);
}
