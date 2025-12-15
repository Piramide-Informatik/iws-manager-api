package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.AbsenceDay;
import com.iws_manager.iws_manager_api.services.interfaces.AbsenceDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/absence-days")
public class AbsenceDayController {

    private final AbsenceDayService absenceDayService;

    @Autowired
    public AbsenceDayController(AbsenceDayService absenceDayService) {
        this.absenceDayService = absenceDayService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AbsenceDay> create(@RequestBody AbsenceDay absenceDay) {
        AbsenceDay createdAbsenceDay = absenceDayService.create(absenceDay);
        return new ResponseEntity<>(createdAbsenceDay, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbsenceDay> getById(@PathVariable Long id) {
        return absenceDayService.findById(id)
                .map(absenceDay -> new ResponseEntity<>(absenceDay, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<AbsenceDay>> getAll() {
        List<AbsenceDay> absenceDays = absenceDayService.findAll();
        return new ResponseEntity<>(absenceDays, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AbsenceDay> update(
            @PathVariable Long id,
            @RequestBody AbsenceDay absenceDayDetails) {
        AbsenceDay updatedAbsenceDay = absenceDayService.update(id, absenceDayDetails);
        return new ResponseEntity<>(updatedAbsenceDay, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        absenceDayService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/by-employee/{employeeId}")
    public ResponseEntity<List<AbsenceDay>> getByEmployeeId(@PathVariable Long employeeId) {
        List<AbsenceDay> absenceDays = absenceDayService.getByEmployeeId(employeeId);
        return new ResponseEntity<>(absenceDays, HttpStatus.OK);
    }

    @GetMapping("/by-employee/{employeeId}/date-range")
    public ResponseEntity<List<AbsenceDay>> getByEmployeeIdAndDateRange(
            @PathVariable Long employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<AbsenceDay> absenceDays = absenceDayService.getByEmployeeIdAndDateRange(
                employeeId, startDate, endDate);
        return new ResponseEntity<>(absenceDays, HttpStatus.OK);
    }

    @GetMapping("/by-employee/{employeeId}/absence-type/{absenceTypeId}")
    public ResponseEntity<List<AbsenceDay>> getByEmployeeIdAndAbsenceTypeId(
            @PathVariable Long employeeId,
            @PathVariable Long absenceTypeId) {
        List<AbsenceDay> absenceDays = absenceDayService.getByEmployeeIdAndAbsenceTypeId(
                employeeId, absenceTypeId);
        return new ResponseEntity<>(absenceDays, HttpStatus.OK);
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsByEmployeeIdAndAbsenceDate(
            @RequestParam Long employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate absenceDate) {
        boolean exists = absenceDayService.existsByEmployeeIdAndAbsenceDate(employeeId, absenceDate);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/by-employee/{employeeId}/year/{year}")
    public ResponseEntity<List<AbsenceDay>> getByEmployeeIdAndYear(
            @PathVariable Long employeeId,
            @PathVariable int year) {
        
        List<AbsenceDay> absenceDays = absenceDayService.getByEmployeeIdAndYear(employeeId, year);
        return new ResponseEntity<>(absenceDays, HttpStatus.OK);
    }

    @GetMapping("/count/by-employee/{employeeId}/absence-type/{absenceTypeId}/year/{year}")
    public ResponseEntity<Long> countByEmployeeIdAndAbsenceTypeIdAndYear(
            @PathVariable Long employeeId,
            @PathVariable Long absenceTypeId,
            @PathVariable int year) {
        
        long count = absenceDayService.countByEmployeeIdAndAbsenceTypeIdAndYear(
            employeeId, absenceTypeId, year);
        return ResponseEntity.ok(count);
    }
}