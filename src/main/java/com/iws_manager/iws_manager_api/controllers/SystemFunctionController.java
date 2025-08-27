package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.SystemFunction;
import com.iws_manager.iws_manager_api.services.interfaces.SystemFunctionService;
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
@RequestMapping("/api/v1/systemfunctions")
public class SystemFunctionController {
    private final SystemFunctionService systemFunctionService;

    @Autowired
    public SystemFunctionController(SystemFunctionService systemFunctionService) {
        this.systemFunctionService = systemFunctionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createASystemFunction(@RequestBody SystemFunction systemFunction) {
        if (systemFunction.getFunctionName() == null || systemFunction.getFunctionName().trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error","FunctionName is required");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        SystemFunction createdSystemFunction = systemFunctionService.create(systemFunction);
        return new ResponseEntity<>(createdSystemFunction, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemFunction> getSystemFunctionById(@PathVariable Long id){
        return systemFunctionService.findById(id)
                .map(systemFunction -> new ResponseEntity<>(systemFunction,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<SystemFunction>> getAllSystemFunction() {
        List<SystemFunction> systemFunctions = systemFunctionService.findAll();
        return new ResponseEntity<>(systemFunctions,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SystemFunction> updateSystemFunction(
            @PathVariable Long id,
            @RequestBody SystemFunction systemFunctionDetails){
        try {
            SystemFunction updatedSystemFunctions = systemFunctionService.update(id, systemFunctionDetails);
            return new ResponseEntity<>(updatedSystemFunctions, HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteSystemFunction(@PathVariable Long id) {
        systemFunctionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/module/{moduleId}")
    public ResponseEntity<List<SystemFunction>> getFunctionsByModule(@PathVariable Long moduleId) {
        List<SystemFunction> functions = systemFunctionService.getFunctionsByModuleId(moduleId);
        if (functions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(functions, HttpStatus.OK);
    }
}
