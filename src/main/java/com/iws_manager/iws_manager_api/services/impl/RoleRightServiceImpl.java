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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jakarta.persistence.EntityNotFoundException;

import javax.swing.text.html.parser.Entity;

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
                .map(existingRoleRight -> {
                    existingRoleRight.setAccessRight(roleRightDetails.getAccessRight());

                    if (roleRightDetails.getRole() != null && roleRightDetails.getRole().getId() != null) {
                        Role role = roleRepository.findById(roleRightDetails.getRole().getId())
                                .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleRightDetails.getRole().getId()));
                        existingRoleRight.setRole(role);
                    }

                    if (roleRightDetails.getSystemFunction() != null && roleRightDetails.getSystemFunction().getId() != null) {
                        SystemFunction systemFunction = systemFunctionRepository.findById(roleRightDetails.getSystemFunction().getId())
                                .orElseThrow(() -> new RuntimeException("SystemFunction not found with id: " + roleRightDetails.getSystemFunction().getId()));
                        existingRoleRight.setSystemFunction(systemFunction);
                    }

                    return roleRightRepository.save(existingRoleRight);
                })
                .orElseThrow(()-> new RuntimeException("roleRight not found with id: "+id));
    }

    @Override
    public void delete(Long id) {
        if (id == null ) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        if (!roleRightRepository.existsById(id)) {
            throw new EntityNotFoundException("RoleRight not found with id: " + id);
        }

        roleRightRepository.deleteById(id);

    }

    @Override
    public List<RoleRight> getRightRolesByModuleId(Long moduleId, Long roleId) {
        return roleRightRepository.findByModuleIdAndRoleId(moduleId, roleId);
    }

    @Override
    @Transactional
    public List<RoleRight> saveAll(List<RoleRight> rights) {
        if (rights == null || rights.isEmpty()) {
            throw new IllegalArgumentException("Rights list cannot be null or empty");
        }

        List<RoleRight> savedRights = new ArrayList<>();

        for (RoleRight roleRight : rights) {
            if (roleRight == null) {
                throw new IllegalArgumentException("RoleRight in list cannot be null");
            }

            // Validate and get Role
            Long roleId = roleRight.getRole().getId();
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));

            // Validate and get System Function
            Long functionId = roleRight.getSystemFunction().getId();
            SystemFunction function = systemFunctionRepository.findById(functionId)
                    .orElseThrow(() -> new RuntimeException("SystemFunction not found with id: " + functionId));

            roleRight.setRole(role);
            roleRight.setSystemFunction(function);

            // Add to saved list
            savedRights.add(roleRight);
        }

        return roleRightRepository.saveAll(savedRights);
    }
}
