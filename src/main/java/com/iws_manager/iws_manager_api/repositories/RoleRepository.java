package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    @Query(value = """
    SELECT r.* 
    FROM role r
    JOIN userrole ur ON ur.roleid = r.roleid
    WHERE ur.userid = :userId
""", nativeQuery = true)
    List<Role> findByUserId(@Param("userId") Long userId);

    @EntityGraph(attributePaths = {"roleRights", "roleRights.systemFunction", "roleRights.systemFunction.module"})
    List<Role> findAllByOrderByNameAsc();

    @Query("SELECT DISTINCT r FROM Role r LEFT JOIN FETCH r.roleRights ORDER BY r.name ASC")
    List<Role> findAllFetchRoleRight();

    @Query("SELECT r FROM Role r LEFT JOIN FETCH r.roleRights WHERE r.id = :id")
    Optional<Role> findByIdFetchRoleRight(Long id);

    boolean existsByName(String name);
}
