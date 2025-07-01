package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.Employee;
import com.iws_manager.iws_manager_api.services.interfaces.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        Employee created = employeeService.create(employee);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findById(@PathVariable Long id) {
        return employeeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Employee>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.update(id, employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ==== FILTERS ====

    @GetMapping("/lastname/{lastname}")
    public ResponseEntity<List<Employee>> findByLastname(@PathVariable String lastname) {
        return ResponseEntity.ok(employeeService.findByLastname(lastname));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Employee> findByEmail(@PathVariable String email) {
        return employeeService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/title/{titleId}")
    public ResponseEntity<List<Employee>> findByTitleId(@PathVariable Long titleId) {
        return ResponseEntity.ok(employeeService.findByTitleId(titleId));
    }

    @GetMapping("/salutation/{salutationId}")
    public ResponseEntity<List<Employee>> findBySalutationId(@PathVariable Long salutationId) {
        return ResponseEntity.ok(employeeService.findBySalutationId(salutationId));
    }

    @GetMapping("/qualification/{qualificationFZId}")
    public ResponseEntity<List<Employee>> findByQualificationFZId(@PathVariable Long qualificationFZId) {
        return ResponseEntity.ok(employeeService.findByQualificationFZId(qualificationFZId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Employee>> findByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(employeeService.findByCustomerId(customerId));
    }
}
