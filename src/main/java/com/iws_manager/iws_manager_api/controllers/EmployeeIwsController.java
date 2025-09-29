package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.EmployeeIws;
import com.iws_manager.iws_manager_api.services.interfaces.EmployeeIwsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employeesiws")
public class EmployeeIwsController {

    private final EmployeeIwsService employeeIwsService;

    @Autowired
    public EmployeeIwsController(EmployeeIwsService employeeIwsService) {
        this.employeeIwsService = employeeIwsService;
    }

    @PostMapping
    public ResponseEntity<EmployeeIws> create(@RequestBody EmployeeIws employeeIws) {
        EmployeeIws created = employeeIwsService.create(employeeIws);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeIws> findById(@PathVariable Long id) {
        return employeeIwsService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EmployeeIws>> findAll() {
        return ResponseEntity.ok(employeeIwsService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeIws> update(@PathVariable Long id, @RequestBody EmployeeIws employeeIwsDetails){
        try {
            EmployeeIws updateEmployeeIws = employeeIwsService.update(id, employeeIwsDetails);
            return new ResponseEntity<>(updateEmployeeIws, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeIwsService.delete(id);
        return ResponseEntity.noContent().build();
    }

     // FIND ALL - ORDER endpoints (MEJORADOS)
    @GetMapping("/by-lastname/ordered-asc")
    public ResponseEntity<List<EmployeeIws>> getAllByOrderByLastnameAsc() {
        List<EmployeeIws> employees = employeeIwsService.getAllByOrderByLastnameAsc();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/by-firstname/ordered-asc")
    public ResponseEntity<List<EmployeeIws>> getAllByOrderByFirstnameAsc() {
        List<EmployeeIws> employees = employeeIwsService.getAllByOrderByFirstnameAsc();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/by-id/ordered-desc")
    public ResponseEntity<List<EmployeeIws>> getAllByOrderByIdDesc() {
        List<EmployeeIws> employees = employeeIwsService.getAllByOrderByIdDesc();
        return ResponseEntity.ok(employees);
    }

    // PROPERTIES endpoints (MEJORADOS)
    @GetMapping("/by-active/{active}")
    public ResponseEntity<List<EmployeeIws>> getByActive(@PathVariable Integer active) {
        List<EmployeeIws> employees = employeeIwsService.getByActive(active);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/by-label/{employeeLabel}")
    public ResponseEntity<List<EmployeeIws>> getByEmployeeLabel(@PathVariable String employeeLabel) {
        List<EmployeeIws> employees = employeeIwsService.getByEmployeeLabel(employeeLabel);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/by-number/{employeeNo}")
    public ResponseEntity<List<EmployeeIws>> getByEmployeeNo(@PathVariable Integer employeeNo) {
        List<EmployeeIws> employees = employeeIwsService.getByEmployeeNo(employeeNo);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/by-enddate/{endDate}")
    public ResponseEntity<List<EmployeeIws>> getByEndDate(@PathVariable LocalDate endDate) {
        List<EmployeeIws> employees = employeeIwsService.getByEndDate(endDate);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/by-firstname/{firstname}")
    public ResponseEntity<List<EmployeeIws>> getByFirstname(@PathVariable String firstname) {
        List<EmployeeIws> employees = employeeIwsService.getByFirstname(firstname);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/by-lastname/{lastname}")
    public ResponseEntity<List<EmployeeIws>> getByLastname(@PathVariable String lastname) {
        List<EmployeeIws> employees = employeeIwsService.getByLastname(lastname);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/by-mail/{mail}")
    public ResponseEntity<List<EmployeeIws>> getByMail(@PathVariable String mail) {
        List<EmployeeIws> employees = employeeIwsService.getByMail(mail);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/by-startdate/{startDate}")
    public ResponseEntity<List<EmployeeIws>> getByStartDate(@PathVariable LocalDate startDate) {
        List<EmployeeIws> employees = employeeIwsService.getByStartDate(startDate);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/by-team/{teamIwsId}")
    public ResponseEntity<List<EmployeeIws>> getByTeamIwsId(@PathVariable Long teamIwsId) {
        List<EmployeeIws> employees = employeeIwsService.getByTeamIwsId(teamIwsId);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<EmployeeIws>> getByUserId(@PathVariable Long userId) {
        List<EmployeeIws> employees = employeeIwsService.getByUserId(userId);
        return ResponseEntity.ok(employees);
    }

    // HELPERS endpoints (MEJORADOS)
    @GetMapping("/by-startdate/after/{date}")
    public ResponseEntity<List<EmployeeIws>> getByStartDateAfter(@PathVariable LocalDate date) {
        List<EmployeeIws> employees = employeeIwsService.getByStartDateAfter(date);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/by-startdate/before/{date}")
    public ResponseEntity<List<EmployeeIws>> getByStartDateBefore(@PathVariable LocalDate date) {
        List<EmployeeIws> employees = employeeIwsService.getByStartDateBefore(date);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/by-startdate/between/{start}/{end}")
    public ResponseEntity<List<EmployeeIws>> getByStartDateBetween(
            @PathVariable LocalDate start, 
            @PathVariable LocalDate end) {
        List<EmployeeIws> employees = employeeIwsService.getByStartDateBetween(start, end);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/by-enddate/after/{date}")
    public ResponseEntity<List<EmployeeIws>> getByEndDateAfter(@PathVariable LocalDate date) {
        List<EmployeeIws> employees = employeeIwsService.getByEndDateAfter(date);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/by-enddate/before/{date}")
    public ResponseEntity<List<EmployeeIws>> getByEndDateBefore(@PathVariable LocalDate date) {
        List<EmployeeIws> employees = employeeIwsService.getByEndDateBefore(date);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/by-enddate/between/{start}/{end}")
    public ResponseEntity<List<EmployeeIws>> getByEndDateBetween(
            @PathVariable LocalDate start, 
            @PathVariable LocalDate end) {
        List<EmployeeIws> employees = employeeIwsService.getByEndDateBetween(start, end);
        return ResponseEntity.ok(employees);
    }

    // ACTIVE - ORDER endpoints
    @GetMapping("/active/{active}/by-firstname/ordered-asc")
    public ResponseEntity<List<EmployeeIws>> getByActiveOrderByFirstnameAsc(@PathVariable Integer active) {
        List<EmployeeIws> employees = employeeIwsService.getByActiveOrderByFirstnameAsc(active);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/active/{active}/by-lastname/ordered-asc")
    public ResponseEntity<List<EmployeeIws>> getByActiveOrderByLastnameAsc(@PathVariable Integer active) {
        List<EmployeeIws> employees = employeeIwsService.getByActiveOrderByLastnameAsc(active);
        return ResponseEntity.ok(employees);
    }
}
