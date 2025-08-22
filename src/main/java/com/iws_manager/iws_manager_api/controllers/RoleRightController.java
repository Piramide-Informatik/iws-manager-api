package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.RoleRight;
import com.iws_manager.iws_manager_api.services.interfaces.RoleRightService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/rolerights")
public class RoleRightController {
    private final RoleRightService roleRightService;

    @Autowired
    public RoleRightController(RoleRightService roleRightService) {
        this.roleRightService = roleRightService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createRoleRight(@RequestBody RoleRight roleRight) {
        if (roleRight.getAccessRight() == null || roleRight.getAccessRight().toString().trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error","AccessRight is required");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        RoleRight createdRoleRight = roleRightService.create(roleRight);
        return new ResponseEntity<>(createdRoleRight, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleRight> getRoleRightById(@PathVariable Long id){
        return roleRightService.findById(id)
                .map(roleRight -> new ResponseEntity<>(roleRight,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<RoleRight>> getAllRoleRight() {
        List<RoleRight> roleRights = roleRightService.findAll();
        return new ResponseEntity<>(roleRights,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleRight> updateApprovalStatus(
            @PathVariable Long id,
            @RequestBody RoleRight roleRightDetails){
        try {
            RoleRight updatedRoleRight = roleRightService.update(id, roleRightDetails);
            return new ResponseEntity<>(updatedRoleRight, HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteRoleRight(@PathVariable Long id) {
        roleRightService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
