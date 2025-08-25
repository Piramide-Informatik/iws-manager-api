package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.Role;
import com.iws_manager.iws_manager_api.models.RoleRight;
import com.iws_manager.iws_manager_api.models.SystemFunction;
import com.iws_manager.iws_manager_api.repositories.RoleRepository;
import com.iws_manager.iws_manager_api.repositories.RoleRightRepository;
import com.iws_manager.iws_manager_api.repositories.SystemFunctionRepository;
import com.iws_manager.iws_manager_api.services.interfaces.RoleRightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleRightServiceImpl implements RoleRightService {
    private final RoleRightRepository roleRightRepository;
    private final RoleRepository roleRepository;
    private final SystemFunctionRepository systemFunctionRepository;

    @Autowired
    public RoleRightServiceImpl(RoleRightRepository roleRightRepository, RoleRepository roleRepository, SystemFunctionRepository systemFunctionRepository) {
        this.roleRightRepository = roleRightRepository;
        this.roleRepository = roleRepository;
        this.systemFunctionRepository = systemFunctionRepository;
    }


    @Override
    public RoleRight create(RoleRight roleRight) {
        if (roleRight == null) {
            throw new IllegalArgumentException("RoleRight cannot be null");
        }
        Long roleId = roleRight.getRole().getId();
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        Long functionId = roleRight.getSystemFunction().getId();
        SystemFunction function = systemFunctionRepository.findById(functionId)
                .orElseThrow(() -> new RuntimeException("SystemFunction not found"));

        roleRight.setRole(role);
        roleRight.setSystemFunction(function);

        return roleRightRepository.save(roleRight);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoleRight> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        return roleRightRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleRight> findAll() {
        return roleRightRepository.findAll();
    }

    @Override
    public RoleRight update(Long id, RoleRight roleRightDetails) {
        if (id == null || roleRightDetails == null) {
            throw new IllegalArgumentException("ID and roleRight details cannot be null");
        }
        return roleRightRepository.findById(id)
                .map(existingRole -> {
                    existingRole.setAccessRight(roleRightDetails.getAccessRight());
                    return roleRightRepository.save(existingRole);
                })
                .orElseThrow(()-> new RuntimeException("roleRight not found with id: "+id));
    }

    @Override
    public void delete(Long id) {
        if (id == null ) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        roleRightRepository.deleteById(id);

    }
}
