package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.RoleRight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRightRepository extends JpaRepository<RoleRight, Long> {
    List<RoleRight> findByRoleId(Long roleId);

    @Query("SELECT rr FROM RoleRight rr " +
            "JOIN rr.systemFunction sf " +
            "JOIN sf.module sm " +
            "WHERE sm.id = :moduleId AND rr.role.id = :roleId")
    List<RoleRight> findByModuleIdAndRoleId(@Param("moduleId") Long moduleId,
                                            @Param("roleId") Long roleId);
}
