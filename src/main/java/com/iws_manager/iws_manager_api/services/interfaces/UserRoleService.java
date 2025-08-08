package com.iws_manager.iws_manager_api.services.interfaces;

import com.iws_manager.iws_manager_api.models.Role;
import com.iws_manager.iws_manager_api.models.User;

import java.util.List;

public interface UserRoleService {
    List<User> getUsersByRole(Role role);
    List<Role> getRolesByUser(User user);
}
