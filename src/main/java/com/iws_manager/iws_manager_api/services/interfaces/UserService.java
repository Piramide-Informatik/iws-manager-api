package com.iws_manager.iws_manager_api.services.interfaces;

import com.iws_manager.iws_manager_api.models.Role;
import com.iws_manager.iws_manager_api.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User create(User user);
    Optional<User> findById(Long id);
    List<User> findAll();
    User update(Long id, User user);
    void delete(Long id);
    User assignRole(Long userId, List<Long> roleIds);
    List<Role> getRolesByUser(Long userId);
}
