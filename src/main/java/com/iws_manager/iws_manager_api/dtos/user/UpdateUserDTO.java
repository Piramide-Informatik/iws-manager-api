package com.iws_manager.iws_manager_api.dtos.user;

public record UpdateUserDTO(
        String username,
        String firstName,
        String lastName,
        String email,
        String password,
        Boolean active
) {
}
