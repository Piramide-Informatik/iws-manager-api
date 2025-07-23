package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.ProjectCostCenter;
import com.iws_manager.iws_manager_api.services.interfaces.ProjectCostCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project-cost-centers")
public class ProjectCostCenterController {

    private final ProjectCostCenterService projectCostCenterService;

    @Autowired
    public ProjectCostCenterController(ProjectCostCenterService projectCostCenterService) {
        this.projectCostCenterService = projectCostCenterService;
    }

    @PostMapping
    public ResponseEntity<ProjectCostCenter> create(@RequestBody ProjectCostCenter projectCostCenter) {
        ProjectCostCenter created = projectCostCenterService.create(projectCostCenter);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectCostCenter> findById(@PathVariable Long id) {
        return projectCostCenterService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ProjectCostCenter>> findAll() {
        return ResponseEntity.ok(projectCostCenterService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectCostCenter> update(@PathVariable Long id, @RequestBody ProjectCostCenter projectCostCenter) {
        return ResponseEntity.ok(projectCostCenterService.update(id, projectCostCenter));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        projectCostCenterService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
