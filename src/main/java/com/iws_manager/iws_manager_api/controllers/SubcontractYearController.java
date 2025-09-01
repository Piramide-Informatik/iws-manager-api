package com.iws_manager.iws_manager_api.controllers;

import java.time.LocalDate;

import com.iws_manager.iws_manager_api.models.SubcontractYear;
import com.iws_manager.iws_manager_api.services.interfaces.SubcontractYearService;

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
@RequestMapping("/api/v1/subcontractyear")
public class SubcontractYearController {
    private final SubcontractYearService subcontractYearService;

    @Autowired
    public SubcontractYearController(SubcontractYearService subcontractYearService) {
        this.subcontractYearService = subcontractYearService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody SubcontractYear subcontractYear){
        SubcontractYear createSubcontractYear = subcontractYearService.create(subcontractYear);
        return new ResponseEntity<>(createSubcontractYear, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubcontractYear> getById(@PathVariable Long id){
        return  subcontractYearService.findById(id)
                .map( subcontractYear -> new ResponseEntity<>(subcontractYear, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<SubcontractYear>> getAll(){
        List<SubcontractYear> subcontractYears = subcontractYearService.findAll();
        return new ResponseEntity<>(subcontractYears, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubcontractYear> update(@PathVariable Long id, @RequestBody SubcontractYear subcontractYearDetails){
        try {
            SubcontractYear updateSubcontractYear = subcontractYearService.update(id, subcontractYearDetails);
            return new ResponseEntity<>(updateSubcontractYear, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try {
            subcontractYearService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/months/{months}")
    public ResponseEntity<List<SubcontractYear>> findByMonths(@PathVariable Integer months) {
        List<SubcontractYear> subcontractYears = subcontractYearService.findByMonths(months);
        return new ResponseEntity<>(subcontractYears, HttpStatus.OK);
    }

    @GetMapping("/subcontract/{subcontractId}")
    public ResponseEntity<List<SubcontractYear>> findBySubcontractId(@PathVariable Long subcontractId) {
        List<SubcontractYear> subcontractYears = subcontractYearService.findBySubcontractId(subcontractId);
        return new ResponseEntity<>(subcontractYears, HttpStatus.OK);
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<List<SubcontractYear>> findByYear(@PathVariable LocalDate year) {
        List<SubcontractYear> subcontractYears = subcontractYearService.findByYear(year);
        return new ResponseEntity<>(subcontractYears, HttpStatus.OK);
    }

    @GetMapping("/subcontract/{subcontractId}/sort-by-year")
    public ResponseEntity<List<SubcontractYear>> getBySubcontractIdOrderByYearAsc(@PathVariable Long subcontractId) {
        List<SubcontractYear> subcontractYears = subcontractYearService.getBySubcontractIdOrderByYearAsc(subcontractId);
        return new ResponseEntity<>(subcontractYears, HttpStatus.OK);
    }
}
