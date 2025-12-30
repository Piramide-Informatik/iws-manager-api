package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.ProjectPeriod;
import com.iws_manager.iws_manager_api.services.interfaces.ProjectPeriodService;
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

import java.util.List;

@RestController
@RequestMapping("/api/v1/project-periods")
public class ProjectPeriodController {
    private final ProjectPeriodService projectPeriodService;

    @Autowired
    public ProjectPeriodController(ProjectPeriodService projectPeriodService) {
        this.projectPeriodService = projectPeriodService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody ProjectPeriod projectPeriod){
        ProjectPeriod createProjectPeriod = projectPeriodService.create(projectPeriod);
        return new ResponseEntity<>(createProjectPeriod, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectPeriod> getById(@PathVariable Long id){
        return  projectPeriodService.findById(id)
                .map( projectPeriod -> new ResponseEntity<>(projectPeriod, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<ProjectPeriod>> getAll(){
        List<ProjectPeriod> projectPeriods = projectPeriodService.findAll();
        return new ResponseEntity<>(projectPeriods, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectPeriod> update(@PathVariable Long id, @RequestBody ProjectPeriod projectPeriodDetails){
        try {
            ProjectPeriod updateProjectPeriod = projectPeriodService.update(id, projectPeriodDetails);
            return new ResponseEntity<>(updateProjectPeriod, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
    
        projectPeriodService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/order-by-periodno")
    public ResponseEntity<List<ProjectPeriod>> getAllByOrderByNoAsc(){
        List<ProjectPeriod> projectPeriods = projectPeriodService.getAllProjectPeriodsByPeriodNoAsc();
        return new ResponseEntity<>(projectPeriods, HttpStatus.OK);
    }

    @GetMapping("/order-by-startdate")
    public ResponseEntity<List<ProjectPeriod>> getAllByOrderByStartDateAsc(){
        List<ProjectPeriod> projectPeriods = projectPeriodService.getAllProjectPeriodsByStartDateAsc();
        return new ResponseEntity<>(projectPeriods, HttpStatus.OK);
    }

    @GetMapping("/order-by-enddate")
    public ResponseEntity<List<ProjectPeriod>> getAllByOrderByEndDateAsc(){
        List<ProjectPeriod> projectPeriods = projectPeriodService.getAllProjectPeriodsByEndDateAsc();
        return new ResponseEntity<>(projectPeriods, HttpStatus.OK);
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<List<ProjectPeriod>> getAllByProjectId(@PathVariable Long id){
        List<ProjectPeriod> list = projectPeriodService.findAllByProjectId(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
