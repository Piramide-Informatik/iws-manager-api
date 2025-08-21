package com.iws_manager.iws_manager_api.services.interfaces;

import com.iws_manager.iws_manager_api.models.RoleRight;

import java.util.List;
import java.util.Optional;

public interface RoleRightService {
    RoleRight create(RoleRight roleRight);
    Optional<RoleRight> findById(Long id);
    List<RoleRight> findAll();
    RoleRight update(Long id, RoleRight roleRightDetails);
    void delete(Long id);
}
