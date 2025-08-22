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
}
