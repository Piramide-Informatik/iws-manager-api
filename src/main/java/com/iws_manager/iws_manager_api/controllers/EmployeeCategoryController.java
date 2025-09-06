package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.EmployeeCategory;
import com.iws_manager.iws_manager_api.services.interfaces.EmployeeCategoryService;
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
@RequestMapping("/api/v1/employee-category")
public class EmployeeCategoryController {
    private final EmployeeCategoryService employeeCategoryService;

    @Autowired
    public EmployeeCategoryController(EmployeeCategoryService employeeCategoryService) {
        this.employeeCategoryService = employeeCategoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createEmployeeCategory(@RequestBody EmployeeCategory category) {

        if (category.getTitle() == null || category.getTitle().trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Title is required");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        EmployeeCategory createdEmployeeCategory = employeeCategoryService.create(category);
        return new ResponseEntity<>(createdEmployeeCategory, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeCategory> getEmployeeCategoryById(@PathVariable Long id) {
        return employeeCategoryService.findById(id)
                .map(category -> new ResponseEntity<>(category, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeCategory>> getAllEmployeeCategories() {
        List<EmployeeCategory> categories = employeeCategoryService.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeCategory> updateEmployeeCategory(
            @PathVariable Long id,
            @RequestBody EmployeeCategory employeeCategory) {
        try {
            EmployeeCategory updatedEmployeeCategory = employeeCategoryService.update(id, employeeCategory);
            return new ResponseEntity<>(updatedEmployeeCategory, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteEmployeeCategory(@PathVariable Long id) {
        employeeCategoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
