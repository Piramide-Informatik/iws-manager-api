package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.ProjectStatus;
import com.iws_manager.iws_manager_api.services.interfaces.ProjectStatusService;
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
@RequestMapping("/api/v1/projectstatus")
public class ProjectStatusController {
    private final ProjectStatusService projectStatusService;

    @Autowired
    public ProjectStatusController(ProjectStatusService projectStatusService) {
        this.projectStatusService = projectStatusService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createProjectStatus(@RequestBody ProjectStatus projectStatus){
        if( projectStatus.getName() == null || projectStatus.getName().trim().isEmpty()){
            Map<String, String> error = new HashMap<>();
            error.put("error", "Name is requierd");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        ProjectStatus createdProjectStatus = projectStatusService.create(projectStatus);
        return new ResponseEntity<>(createdProjectStatus,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectStatus> getProjectStatusById(@PathVariable Long id){
        return projectStatusService.findById(id)
                .map(projectStatus -> new ResponseEntity<>(projectStatus, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<ProjectStatus>> getAllProjectStatus(){
        List<ProjectStatus> projectStatus = projectStatusService.findAll();
        return new ResponseEntity<>(projectStatus, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectStatus> updateProjectStatus(
            @PathVariable Long id,
            @RequestBody ProjectStatus projectStatusDetails) {
        try {
            ProjectStatus updateProjectStatus = projectStatusService.update(id, projectStatusDetails);
            return new ResponseEntity<>(updateProjectStatus, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteProjectStatus(@PathVariable Long id) {
        projectStatusService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
