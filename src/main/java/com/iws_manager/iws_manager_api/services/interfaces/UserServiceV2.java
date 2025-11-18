package com.iws_manager.iws_manager_api.services.interfaces;

import com.iws_manager.iws_manager_api.dtos.user.CreateUserDTO;
import com.iws_manager.iws_manager_api.dtos.user.UpdateUserDTO;
import com.iws_manager.iws_manager_api.models.Role;
import com.iws_manager.iws_manager_api.models.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceV2 {
    User create(CreateUserDTO dto);
    List<User> findAll();
    Optional<User> findById(Long id);
    User update(Long id, UpdateUserDTO dto);
    void delete(Long id);
    User assignRole(Long userId, List<Long> roleIds);
    List<Role> getRolesByUser(Long userId);
    List<String> getRoleNamesByUser(Long userId);
}
