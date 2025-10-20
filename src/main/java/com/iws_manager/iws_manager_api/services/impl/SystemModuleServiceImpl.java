package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.SystemModule;
import com.iws_manager.iws_manager_api.repositories.SystemModuleRepository;
import com.iws_manager.iws_manager_api.services.interfaces.SystemModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SystemModuleServiceImpl implements SystemModuleService {
    private final SystemModuleRepository systemModuleRepository;

    @Autowired
    public SystemModuleServiceImpl(SystemModuleRepository systemModuleRepository) {
        this.systemModuleRepository = systemModuleRepository;
    }

    @Override
    public SystemModule create(SystemModule systemModule) {
        if (systemModule == null) {
            throw new IllegalArgumentException("SystemModule cannot be null");
        }
        return systemModuleRepository.save(systemModule);    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SystemModule> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return systemModuleRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SystemModule> findAll() {
        return systemModuleRepository.findAllByOrderByNameAsc();
    }

    @Override
    public SystemModule update(Long id, SystemModule systemModuleDetails) {
        if (id==null || systemModuleDetails ==null) {
            throw new IllegalArgumentException("Id and Details cannot be null");
        }
        return systemModuleRepository.findById(id)
                .map(existingSystemModule -> {
                    existingSystemModule.setName(systemModuleDetails.getName());
                    return  systemModuleRepository.save(existingSystemModule);
                }).orElseThrow(() -> new RuntimeException("SystemModule not found with id: "+id));
    }

    @Override
    public void delete(Long id) {
        if (id == null ) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        systemModuleRepository.deleteById(id);
    }
}
