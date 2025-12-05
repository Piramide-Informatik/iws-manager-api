package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.dtos.role.CreateRoleDTO;
import com.iws_manager.iws_manager_api.mappers.RoleMapper;
import com.iws_manager.iws_manager_api.models.Role;
import com.iws_manager.iws_manager_api.models.User;
import com.iws_manager.iws_manager_api.repositories.RoleRepository;
import com.iws_manager.iws_manager_api.repositories.UserRepository;
import com.iws_manager.iws_manager_api.services.interfaces.RoleServiceV2;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleServiceImplV2 implements RoleServiceV2 {
    private static final String ROL_NOT_FOUND = "Role not found";
    private static final String ID_CANNOT_BE_NULL = "ID cannot be null";

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public RoleServiceImplV2(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Role createRole(CreateRoleDTO dto) {
        Role role = RoleMapper.toEntity(dto);
        if (roleRepository.existsByName(role.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Role with name " + role.getName() + " already exists");
        }
        return roleRepository.save(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return roleRepository.findAllFetchRoleRight();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findById(Long id) {
        return roleRepository.findByIdFetchRoleRight(id);
    }

    @Override
    @Transactional
    public Role update(Long id, CreateRoleDTO dto) {
        return roleRepository.findById(id)
                .map( existing -> {
                    RoleMapper.updateEntity(existing, dto);
                    return roleRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException(ROL_NOT_FOUND));
    }

    @Override
    public void delete(Long id) {
        validateIdNotNull(id);
        roleRepository.deleteById(id);
    }

    @Override
    public List<User> getUsersByRoleId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Role ID cannot be null");
        }
        return userRepository.findByRolesId(id);
    }

    // helper methods
    private void validateIdNotNull(Long id) {
        if (id == null) {
            throw new IllegalArgumentException(ID_CANNOT_BE_NULL);
        }
        if (!roleRepository.existsById(id)) {
            throw new EntityNotFoundException("Role not found with id: " + id);
        }
    }
}
