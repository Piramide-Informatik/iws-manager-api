package com.iws_manager.iws_manager_api.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.iws_manager.iws_manager_api.models.SubcontractProject;
import com.iws_manager.iws_manager_api.services.interfaces.SubcontractProjectService;
import org.springframework.web.bind.annotation.RequestParam;

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
@RequestMapping("/api/v1/subcontractproject")
public class SubcontractProjectController {
    private final SubcontractProjectService subcontractProjectService;

    @Autowired
    public SubcontractProjectController(SubcontractProjectService subcontractProjectService) {
        this.subcontractProjectService = subcontractProjectService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody SubcontractProject subcontractProject){
        SubcontractProject createSubcontractProject = subcontractProjectService.create(subcontractProject);
        return new ResponseEntity<>(createSubcontractProject, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubcontractProject> getById(@PathVariable Long id){
        return  subcontractProjectService.findById(id)
                .map( subcontractProject -> new ResponseEntity<>(subcontractProject, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<SubcontractProject>> getAll(){
        List<SubcontractProject> subcontractProjects = subcontractProjectService.findAll();
        return new ResponseEntity<>(subcontractProjects, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubcontractProject> update(@PathVariable Long id, @RequestBody SubcontractProject subcontractProjectDetails){
        try {
            SubcontractProject updateSubcontractProject = subcontractProjectService.update(id, subcontractProjectDetails);
            return new ResponseEntity<>(updateSubcontractProject, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try {
            subcontractProjectService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/months/{months}")
    public ResponseEntity<List<SubcontractProject>> getByMonths(@PathVariable Integer months) {
        List<SubcontractProject> subcontractProjects = subcontractProjectService.getByMonths(months);
        return new ResponseEntity<>(subcontractProjects, HttpStatus.OK);
    }

    @GetMapping("/amount/{amount}")
    public ResponseEntity<List<SubcontractProject>> getByAmount(@PathVariable BigDecimal amount) {
        List<SubcontractProject> subcontractProjects = subcontractProjectService.getByAmount(amount);
        return new ResponseEntity<>(subcontractProjects, HttpStatus.OK);
    }

    @GetMapping("/share/{share}")
    public ResponseEntity<List<SubcontractProject>> getByShare(@PathVariable BigDecimal share) {
        List<SubcontractProject> subcontractProjects = subcontractProjectService.getByShare(share);
        return new ResponseEntity<>(subcontractProjects, HttpStatus.OK);
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<List<SubcontractProject>> getByYear(@PathVariable LocalDate year) {
        List<SubcontractProject> subcontractProjects = subcontractProjectService.getByYear(year);
        return new ResponseEntity<>(subcontractProjects, HttpStatus.OK);
    }

    @GetMapping("/subcontractyear/{subcontractYearId}")
    public ResponseEntity<List<SubcontractProject>> getBySubcontractYearId(@PathVariable Long subcontractYearId) {
        List<SubcontractProject> subcontractProjects = subcontractProjectService.getBySubcontractYearId(subcontractYearId);
        return new ResponseEntity<>(subcontractProjects, HttpStatus.OK);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<SubcontractProject>> getByProjectId(@PathVariable Long projectId) {
        List<SubcontractProject> subcontractProjects = subcontractProjectService.getByProjectId(projectId);
        return new ResponseEntity<>(subcontractProjects, HttpStatus.OK);
    }

    @GetMapping("/subcontract/{subcontractId}")
    public ResponseEntity<List<SubcontractProject>> getBySubcontractId(@PathVariable Long subcontractId) {
        List<SubcontractProject> subcontractProjects = subcontractProjectService.getBySubcontractId(subcontractId);
        return new ResponseEntity<>(subcontractProjects, HttpStatus.OK);
    }

    @GetMapping("/share-between")
    public ResponseEntity<List<SubcontractProject>> getByShareBetween(@RequestParam BigDecimal start, @RequestParam BigDecimal end) {
        List<SubcontractProject> subcontractProjects = subcontractProjectService.getByShareBetween(start, end);
        return new ResponseEntity<>(subcontractProjects, HttpStatus.OK);
    }

    @GetMapping("/months-greater-than/{months}")
    public ResponseEntity<List<SubcontractProject>> getByMonthsGreaterThan(@PathVariable Integer months) {
        List<SubcontractProject> subcontractProjects = subcontractProjectService.getByMonthsGreaterThan(months);
        return new ResponseEntity<>(subcontractProjects, HttpStatus.OK);
    }

    @GetMapping("/months-less-than/{months}")
    public ResponseEntity<List<SubcontractProject>> getByMonthsLessThan(@PathVariable Integer months) {
        List<SubcontractProject> subcontractProjects = subcontractProjectService.getByMonthsLessThan(months);
        return new ResponseEntity<>(subcontractProjects, HttpStatus.OK);
    }

    @GetMapping("/year-after/{date}")
    public ResponseEntity<List<SubcontractProject>> getByYearAfter(@PathVariable LocalDate date) {
        List<SubcontractProject> subcontractProjects = subcontractProjectService.getByYearAfter(date);
        return new ResponseEntity<>(subcontractProjects, HttpStatus.OK);
    }

    @GetMapping("/year-before/{date}")
    public ResponseEntity<List<SubcontractProject>> getByYearBefore(@PathVariable LocalDate date) {
        List<SubcontractProject> subcontractProjects = subcontractProjectService.getByYearBefore(date);
        return new ResponseEntity<>(subcontractProjects, HttpStatus.OK);
    }
}
