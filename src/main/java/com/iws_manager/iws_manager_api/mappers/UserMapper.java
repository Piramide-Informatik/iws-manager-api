package com.iws_manager.iws_manager_api.mappers;

import com.iws_manager.iws_manager_api.dtos.user.CreateUserDTO;
import com.iws_manager.iws_manager_api.dtos.user.UpdateUserDTO;
import com.iws_manager.iws_manager_api.dtos.user.UserDTO;
import com.iws_manager.iws_manager_api.dtos.user.UserWithRolesDTO;
import com.iws_manager.iws_manager_api.dtos.role.RoleDTO;
import com.iws_manager.iws_manager_api.models.User;

import java.util.stream.Collectors;

public class UserMapper {
    private UserMapper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.isActive(),
                user.getEmail()
        );
    }

    public static UserWithRolesDTO toDTOWithRoles(User user) {
        return new UserWithRolesDTO(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.isActive(),
                user.getEmail(),
                user.getRoles().stream()
                        .map(role -> new RoleDTO(role.getId(), role.getName()))
                        .collect(Collectors.toList())
        );
    }

    public static User toEntity(CreateUserDTO dto) {
        User user = new User();
        user.setUsername(dto.username());
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setActive(dto.active() == null || dto.active());
        return user;
    }

    public static void updateEntity(User user, UpdateUserDTO dto) {
        if (dto.username() != null) user.setUsername(dto.username());
        if (dto.firstName() != null) user.setFirstName(dto.firstName());
        if (dto.lastName() != null) user.setLastName(dto.lastName());
        if (dto.email() != null) user.setEmail(dto.email());
        if (dto.active() != null) user.setActive(dto.active());
    }
}
