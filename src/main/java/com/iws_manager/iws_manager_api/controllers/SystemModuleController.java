package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.ApprovalStatus;
import com.iws_manager.iws_manager_api.models.SystemModule;
import com.iws_manager.iws_manager_api.services.interfaces.SystemModuleService;
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
@RequestMapping("/api/v1/systemmodules")
public class SystemModuleController {
    private final SystemModuleService systemModuleService;

    @Autowired
    public SystemModuleController(SystemModuleService systemModuleService) {
        this.systemModuleService = systemModuleService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createSystemModule(@RequestBody SystemModule systemModule) {
        if (systemModule.getName() == null || systemModule.getName().trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error","name is required");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        SystemModule createdSystemModule = systemModuleService.create(systemModule);
        return new ResponseEntity<>(createdSystemModule, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemModule> getSystemModuleById(@PathVariable Long id){
        return systemModuleService.findById(id)
                .map(systemModule -> new ResponseEntity<>(systemModule,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<SystemModule>> getAllSystemModule() {
        List<SystemModule> systemModules = systemModuleService.findAll();
        return new ResponseEntity<>(systemModules,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SystemModule> updateSystemModule(
            @PathVariable Long id,
            @RequestBody SystemModule systemModuleDetails){
        try {
            SystemModule updatedSystemModule = systemModuleService.update(id, systemModuleDetails);
            return new ResponseEntity<>(updatedSystemModule, HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteSystemModule(@PathVariable Long id) {
        systemModuleService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
