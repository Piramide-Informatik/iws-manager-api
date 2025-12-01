package com.iws_manager.iws_manager_api.dtos.user;

import com.iws_manager.iws_manager_api.dtos.role.RoleDTO;

import java.util.List;

public record UserWithRolesDTO(
        Long id,
        String username,
        String firstName,
        String lastName,
        String password,
        boolean active,
        String email,
        List<RoleDTO> roles,
        Integer version
) {
}
