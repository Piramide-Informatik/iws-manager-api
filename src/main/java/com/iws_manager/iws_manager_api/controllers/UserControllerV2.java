package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.dtos.role.RoleDTO;
import com.iws_manager.iws_manager_api.dtos.user.CreateUserDTO;
import com.iws_manager.iws_manager_api.dtos.user.UpdateUserDTO;
import com.iws_manager.iws_manager_api.dtos.user.UserDTO;
import com.iws_manager.iws_manager_api.dtos.user.UserWithRolesDTO;
import com.iws_manager.iws_manager_api.mappers.UserMapper;
import com.iws_manager.iws_manager_api.models.User;
import com.iws_manager.iws_manager_api.services.interfaces.UserServiceV2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2/users")
public class UserControllerV2 {
    private final UserServiceV2 userService;

    @Autowired
    public UserControllerV2(UserServiceV2 userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody CreateUserDTO dto) {
        User user = userService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDTO(user, isAdmin()));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        boolean adminStatus = isAdmin();
        List<UserDTO> list = userService.findAll()
                .stream()
                .map(user -> UserMapper.toDTO(user, adminStatus))
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserWithRolesDTO> getById(@PathVariable Long id) {
        boolean adminStatus = isAdmin();
        return userService.findById(id)
                .map(user -> UserMapper.toDTOWithRoles(user, adminStatus))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UpdateUserDTO dto) {
        User updated = userService.update(id, dto);
        return ResponseEntity.ok(UserMapper.toDTO(updated, isAdmin()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/roles")
    public ResponseEntity<UserWithRolesDTO> assignRoles(
            @PathVariable Long id,
            @RequestBody List<Long> roleIds) {
        User user = userService.assignRole(id, roleIds);
        return ResponseEntity.ok(UserMapper.toDTOWithRoles(user, isAdmin()));
    }

    private boolean isAdmin() {
        var authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        System.out.println("DEBUG CONTROLLER: Autoridades de la sesión: " + authorities);
        return authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    @GetMapping("/{id}/roles")
    public ResponseEntity<List<RoleDTO>> getRoleNamesByUser(@PathVariable Long id) {
        List<RoleDTO> roles = userService.getRoleNamesByUser(id);
        return ResponseEntity.ok(roles);
    }
}
