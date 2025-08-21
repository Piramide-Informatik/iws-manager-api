package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.SystemFunction;
import com.iws_manager.iws_manager_api.repositories.SystemFunctionRepository;
import com.iws_manager.iws_manager_api.services.interfaces.SystemFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SystemFunctionServiceImpl implements SystemFunctionService {
    private final SystemFunctionRepository systemFunctionRepository;

    @Autowired
    public SystemFunctionServiceImpl(SystemFunctionRepository systemFunctionRepository) {
        this.systemFunctionRepository = systemFunctionRepository;
    }

    @Override
    public SystemFunction create(SystemFunction systemFunction) {
        if (systemFunction == null) {
            throw new IllegalArgumentException("SystemFunction cannot be null");
        }
        return systemFunctionRepository.save(systemFunction);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SystemFunction> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return systemFunctionRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SystemFunction> findAll() {
        return systemFunctionRepository.findAll();
    }

    @Override
    public SystemFunction update(Long id, SystemFunction systemFunctionDetails) {
        if (id==null || systemFunctionDetails ==null) {
            throw new IllegalArgumentException("Id and Details cannot be null");
        }
        return systemFunctionRepository.findById(id)
                .map(existingSystemFunction -> {
                    existingSystemFunction.setFunctionName(systemFunctionDetails.getFunctionName());
                    return  systemFunctionRepository.save(existingSystemFunction);
                }).orElseThrow(() -> new RuntimeException("SystemFunction not found with id: "+id));    }

    @Override
    public void delete(Long id) {
        if (id == null ) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        systemFunctionRepository.deleteById(id);
    }
}
