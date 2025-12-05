package com.iws_manager.iws_manager_api.dtos.role;

import java.util.List;

public record RolesDTO(
        Long id,
        String name,
        List<RoleRightDTO> roleRights,
        Integer version
) {
}
