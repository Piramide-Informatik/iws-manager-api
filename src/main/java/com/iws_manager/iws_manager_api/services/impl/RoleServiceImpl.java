package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.Role;
import com.iws_manager.iws_manager_api.models.RoleRight;
import com.iws_manager.iws_manager_api.models.User;
import com.iws_manager.iws_manager_api.repositories.RoleRepository;
import com.iws_manager.iws_manager_api.repositories.RoleRightRepository;
import com.iws_manager.iws_manager_api.repositories.UserRepository;
import com.iws_manager.iws_manager_api.services.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Role create(Role role) {
        if (role == null) {
            throw  new IllegalArgumentException("Role cannot be null");
        }
        return roleRepository.save(role);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        return roleRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return roleRepository.findAllByOrderByNameAsc();
    }

    @Override
    public Role update(Long id, Role roleDetails) {
        if (id == null || roleDetails == null) {
            throw new IllegalArgumentException("ID and role details cannot be null");
        }

        return roleRepository.findById(id)
                .map(existingRole -> {
                    existingRole.setName(roleDetails.getName());
                    existingRole.setUpdatedAt(LocalDateTime.now());
                    return roleRepository.save(existingRole);
                })
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (!roleRepository.existsById(id)) {  
            throw new EntityNotFoundException("Role not found with id: " + id);
        }

        roleRepository.deleteById(id);
    }


    @Override
    public List<User> getUsersByRole(Long roleId) {
        if (roleId == null) {
            throw new IllegalArgumentException("Role ID cannot be null");
        }
        return userRepository.findByRolesId(roleId);
    }
}
