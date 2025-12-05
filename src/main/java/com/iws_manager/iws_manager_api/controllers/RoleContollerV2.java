package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.dtos.role.CreateRoleDTO;
import com.iws_manager.iws_manager_api.dtos.role.RolesDTO;
import com.iws_manager.iws_manager_api.mappers.RoleMapper;
import com.iws_manager.iws_manager_api.models.Role;
import com.iws_manager.iws_manager_api.models.User;
import com.iws_manager.iws_manager_api.services.interfaces.RoleServiceV2;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2/roles")
public class RoleContollerV2 {
    private final RoleServiceV2 roleService;

    @Autowired
    public RoleContollerV2(RoleServiceV2 roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RolesDTO> createRole(@RequestBody CreateRoleDTO dto){
        Role role = roleService.createRole(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(RoleMapper.toDTO(role));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolesDTO> getRoleById(@PathVariable Long id) {
        return roleService.findById(id)
                .map(RoleMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<RolesDTO>> getAllRoles(){
        List<RolesDTO> roles = roleService.findAll()
                .stream()
                .map(RoleMapper::toDTO)
                .toList();
        return ResponseEntity.ok(roles);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolesDTO> updateRole(
            @PathVariable Long id,
            @RequestBody CreateRoleDTO roleDetails) {
        Role updatedRole = roleService.update(id, roleDetails);
        return ResponseEntity.ok(RoleMapper.toDTO(updatedRole));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getUsersByRoleId(id));
    }
}
