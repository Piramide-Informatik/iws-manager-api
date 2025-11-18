package com.iws_manager.iws_manager_api.dtos.user;

import java.util.List;

public record UserWithRolesDTO(
        Long id,
        String username,
        String firstName,
        String lastName,
        boolean active,
        String email,
        List<String> roles
) {
}
