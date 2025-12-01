package com.iws_manager.iws_manager_api.dtos.user;


public record UserDTO(
        Long id,
        String username,
        String firstName,
        String lastName,
        String password,
        boolean active,
        String email,
        Integer version
) {
}
