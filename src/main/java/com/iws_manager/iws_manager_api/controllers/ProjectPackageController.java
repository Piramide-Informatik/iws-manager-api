package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.ProjectPackage;
import com.iws_manager.iws_manager_api.services.interfaces.ProjectPackageService;
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

import java.util.List;

@RestController
@RequestMapping("/api/v1/project-packages")
public class ProjectPackageController {
    private final ProjectPackageService projectPackageService;

    public ProjectPackageController(ProjectPackageService projectPackageService) {
        this.projectPackageService = projectPackageService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody ProjectPackage projectPackage){
        ProjectPackage createProjectPackage = projectPackageService.create(projectPackage);
        return new ResponseEntity<>(createProjectPackage, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectPackage> getById(@PathVariable Long id){
        return  projectPackageService.findById(id)
                .map( projectPackage -> new ResponseEntity<>(projectPackage, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<ProjectPackage>> getAll(){
        List<ProjectPackage> projectPackages = projectPackageService.findAll();
        return new ResponseEntity<>(projectPackages, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectPackage> update(@PathVariable Long id, @RequestBody ProjectPackage projectPackageDetails){
        try {
            ProjectPackage updateProjectPackage = projectPackageService.update(id, projectPackageDetails);
            return new ResponseEntity<>(updateProjectPackage, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try {
            projectPackageService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/order-by-title")
    public ResponseEntity<List<ProjectPackage>> getAllByOrderByTitleAsc(){
        List<ProjectPackage> projectPackages = projectPackageService.findAllTitleAsc();
        return new ResponseEntity<>(projectPackages, HttpStatus.OK);
    }

    @GetMapping("/order-by-serial")
    public ResponseEntity<List<ProjectPackage>> getAllByOrderBySerialAsc(){
        List<ProjectPackage> projectPackages = projectPackageService.findAllSerialAsc();
        return new ResponseEntity<>(projectPackages, HttpStatus.OK);
    }

    @GetMapping("/order-by-packageno")
    public ResponseEntity<List<ProjectPackage>> getAllByOrderByPackageNoAsc(){
        List<ProjectPackage> projectPackages = projectPackageService.findAllPackageNoAsc();
        return new ResponseEntity<>(projectPackages, HttpStatus.OK);
    }

    @GetMapping("/order-by-startdate")
    public ResponseEntity<List<ProjectPackage>> getAllByOrderByStartDateAsc(){
        List<ProjectPackage> projectPackages = projectPackageService.findAllStartDateAsc();
        return new ResponseEntity<>(projectPackages, HttpStatus.OK);
    }

    @GetMapping("/order-by-enddate")
    public ResponseEntity<List<ProjectPackage>> getAllByOrderByEndtDateAsc(){
        List<ProjectPackage> projectPackages = projectPackageService.findAllEndDateAsc();
        return new ResponseEntity<>(projectPackages, HttpStatus.OK);
    }
}
