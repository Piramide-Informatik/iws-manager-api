package com.iws_manager.iws_manager_api.services.interfaces;

import com.iws_manager.iws_manager_api.models.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Role create(Role role);
    Optional<Role> findById(Long id);
    List<Role> findAll();
    Role update(Long id, Role roleDetails);
    void delete(Long id);
}
