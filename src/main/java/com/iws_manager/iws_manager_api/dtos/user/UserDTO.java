package com.iws_manager.iws_manager_api.dtos.user;


public record UserDTO(
        Long id,
        String username,
        String firstName,
        String lastName,
        boolean active,
        String email
) {
}
