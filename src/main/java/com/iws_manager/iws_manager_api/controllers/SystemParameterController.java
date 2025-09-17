package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.SystemParameter;
import com.iws_manager.iws_manager_api.services.interfaces.SystemParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/systems")
public class SystemParameterController {
    private final SystemParameterService systemParameterService;

    @Autowired
    public SystemParameterController(SystemParameterService systemParameterService) {
        this.systemParameterService = systemParameterService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody SystemParameter systemParameter) {
        SystemParameter createdSystemParameter = systemParameterService.create(systemParameter);
        return new ResponseEntity<>(createdSystemParameter, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemParameter> getById(@PathVariable Long id) {
        return systemParameterService.findById(id)
                .map(systemParameter -> new ResponseEntity<>(systemParameter, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<SystemParameter>> getAll() {
        List<SystemParameter> systemParameters = systemParameterService.findAll();
        return new ResponseEntity<>(systemParameters, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SystemParameter> update(@PathVariable Long id, @RequestBody SystemParameter systemParameterDetails) {
        try {
            SystemParameter updatedSystemParameter = systemParameterService.update(id, systemParameterDetails);
            return new ResponseEntity<>(updatedSystemParameter, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            systemParameterService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}