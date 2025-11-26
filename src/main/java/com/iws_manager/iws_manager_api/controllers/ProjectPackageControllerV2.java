package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.dtos.projectpackage.CreateProjectPackageDTO;
import com.iws_manager.iws_manager_api.dtos.projectpackage.ProjectPackageDTO;
import com.iws_manager.iws_manager_api.dtos.projectpackage.ProjectPackageWithProjectDTO;
import com.iws_manager.iws_manager_api.mappers.ProjectPackageMapper;
import com.iws_manager.iws_manager_api.models.ProjectPackage;
import com.iws_manager.iws_manager_api.services.interfaces.ProjectPackageServiceV2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2/project-packages")
public class ProjectPackageControllerV2 {
    private final ProjectPackageServiceV2 projectPackageServiceV2;

    public ProjectPackageControllerV2(ProjectPackageServiceV2 projectPackageServiceV2) {
        this.projectPackageServiceV2 = projectPackageServiceV2;
    }

    @PostMapping
    public ResponseEntity<ProjectPackageWithProjectDTO> createProjectPackage(
            @RequestBody CreateProjectPackageDTO projectPackageDTO) {

        ProjectPackageWithProjectDTO createdPackage =
                projectPackageServiceV2.create(projectPackageDTO);

        return new ResponseEntity<>(createdPackage, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProjectPackageDTO>> getAll() {
        List<ProjectPackageDTO> list = projectPackageServiceV2.findAll()
                .stream()
                .map(ProjectPackageMapper::toDTO)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectPackageWithProjectDTO> getById(@PathVariable Long id) {
        return projectPackageServiceV2.findById(id)
                .map(ProjectPackageMapper::toDTOWithProject)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectPackageDTO> update(@PathVariable Long id, @RequestBody ProjectPackageDTO dto) {
        ProjectPackage updated = projectPackageServiceV2.update(id, dto);
        return ResponseEntity.ok(ProjectPackageMapper.toDTO(updated)    );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        projectPackageServiceV2.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/order-by-title")
    public ResponseEntity<List<ProjectPackageDTO>> getAllByOrderByTitleAsc(){
        List<ProjectPackageDTO> list = projectPackageServiceV2.findAllTitleAsc()
                .stream()
                .map(ProjectPackageMapper::toDTO)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/order-by-serial")
    public ResponseEntity<List<ProjectPackageDTO>> getAllByOrderBySerialAsc(){
        List<ProjectPackageDTO> list = projectPackageServiceV2.findAllSerialAsc()
                .stream()
                .map(ProjectPackageMapper::toDTO)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/order-by-packageno")
    public ResponseEntity<List<ProjectPackageDTO>> getAllByOrderByPackageNoAsc(){
        List<ProjectPackageDTO> list = projectPackageServiceV2.findAllPackageNoAsc()
                .stream()
                .map(ProjectPackageMapper::toDTO)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/order-by-startdate")
    public ResponseEntity<List<ProjectPackageDTO>> getAllByOrderByStartDateAsc(){
        List<ProjectPackageDTO> list = projectPackageServiceV2.findAllStartDateAsc()
                .stream()
                .map(ProjectPackageMapper::toDTO)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/order-by-enddate")
    public ResponseEntity<List<ProjectPackageDTO>> getAllByOrderByEndtDateAsc(){
        List<ProjectPackageDTO> list = projectPackageServiceV2.findAllEndDateAsc()
                .stream()
                .map(ProjectPackageMapper::toDTO)
                .toList();
        return ResponseEntity.ok(list);
    }
}
