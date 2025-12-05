package com.iws_manager.iws_manager_api.mappers;

import com.iws_manager.iws_manager_api.dtos.role.CreateRoleDTO;
import com.iws_manager.iws_manager_api.dtos.role.RoleRightDTO;
import com.iws_manager.iws_manager_api.dtos.role.RolesDTO;
import com.iws_manager.iws_manager_api.models.Role;
import com.iws_manager.iws_manager_api.models.RoleRight;

import java.util.List;
import java.util.stream.Collectors;

public class RoleMapper {
    private RoleMapper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    public static RolesDTO toDTO(Role role) {
        if (role == null) return null;

        List<RoleRightDTO> rights = null;

        if (role.getRoleRights() != null) {
            rights = role.getRoleRights()
                    .stream()
                    .map(RoleMapper::toRoleRightDTO)
                    .collect(Collectors.toList());
        }

        return new RolesDTO(
                role.getId(),
                role.getName(),
                rights,
                role.getVersion()
        );
    }

    public static Role toEntity(CreateRoleDTO dto) {
        Role role = new Role();
        role.setName(dto.name());
        return role;
    }

    public static void updateEntity(Role role, CreateRoleDTO dto) {
        if (dto.name() != null) role.setName(dto.name());
    }

    private static RoleRightDTO toRoleRightDTO(RoleRight rr) {
        if (rr == null) return null;

        return new RoleRightDTO(
                rr.getId(),
                rr.getAccessRight(),
                rr.getVersion()
        );
    }

}
