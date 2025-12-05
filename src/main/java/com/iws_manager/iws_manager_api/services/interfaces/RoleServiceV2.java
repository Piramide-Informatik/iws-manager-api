package com.iws_manager.iws_manager_api.services.interfaces;

import com.iws_manager.iws_manager_api.dtos.role.CreateRoleDTO;
import com.iws_manager.iws_manager_api.models.Role;
import com.iws_manager.iws_manager_api.models.User;

import java.util.List;
import java.util.Optional;

public interface RoleServiceV2 {
    Role createRole(CreateRoleDTO dto);
    List<Role> findAll();
    Optional<Role> findById(Long id);
    Role update(Long id, CreateRoleDTO dto);
    void delete(Long id);
    List<User> getUsersByRoleId(Long id);
}
