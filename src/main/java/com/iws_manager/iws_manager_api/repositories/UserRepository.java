package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.Role;
import com.iws_manager.iws_manager_api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    @Query("SELECT COUNT(u) FROM User u WHERE u.active = true")
    long countActiveUsers();

    @EntityGraph(attributePaths = {"roles"})
    List<User> findByRolesId(Long roleId);

    @EntityGraph(attributePaths = {"roles"})
    List<User> findAllByOrderByUsernameAsc();
}
