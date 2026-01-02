package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = { "roles" })
    Optional<User> findByUsername(String username);

    @Query("SELECT COUNT(u) FROM User u WHERE u.active = true")
    long countActiveUsers();

    @EntityGraph(attributePaths = { "roles" })
    List<User> findByRolesId(Long roleId);

    @EntityGraph(attributePaths = { "roles" })
    List<User> findAllByOrderByUsernameAsc();

    boolean existsByUsername(String username);

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles ORDER BY u.username ASC")
    List<User> findAllFetchRoles();

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.id = :id")
    Optional<User> findByIdFetchRoles(Long id);
}
