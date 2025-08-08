package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.Role;
import com.iws_manager.iws_manager_api.models.User;
import com.iws_manager.iws_manager_api.repositories.RoleRepository;
import com.iws_manager.iws_manager_api.services.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
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
        return roleRepository.findAll();
    }

    @Override
    public Role update(Long id, Role roleDetails) {
        if (id == null || roleDetails == null) {
            throw new IllegalArgumentException("ID and role details cannot be null");
        }
        return roleRepository.findById(id)
                .map(existingRole -> {
                    existingRole.setName(roleDetails.getName());
                    return roleRepository.save(existingRole);
                })
                .orElseThrow(()-> new RuntimeException("Role not found with id: "+id));
    }

    @Override
    public void delete(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        int userCount = role.getUsers().size();

        if (userCount > 0) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Role is assigned to " + userCount + " user(s) and cannot be deleted"
            );
        }
        roleRepository.deleteById(id);
    }

    @Override
    public List<User> getUsersByRole(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        return role.getUsers();
    }
}
