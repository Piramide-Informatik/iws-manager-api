package com.iws_manager.iws_manager_api.services.interfaces;

import com.iws_manager.iws_manager_api.dtos.projectpackage.CreateProjectPackageDTO;
import com.iws_manager.iws_manager_api.dtos.projectpackage.ProjectPackageDTO;
import com.iws_manager.iws_manager_api.dtos.projectpackage.ProjectPackageWithProjectDTO;
import com.iws_manager.iws_manager_api.models.ProjectPackage;

import java.util.List;
import java.util.Optional;

public interface ProjectPackageServiceV2 {
    ProjectPackageWithProjectDTO create(CreateProjectPackageDTO dto);
    Optional<ProjectPackage> findById(Long id);
    List<ProjectPackage> findAll();
    ProjectPackage update(Long id, ProjectPackageDTO dto);
    void delete(Long id);

    List<ProjectPackage> findAllTitleAsc();
    List<ProjectPackage> findAllSerialAsc();
    List<ProjectPackage> findAllPackageNoAsc();
    List<ProjectPackage> findAllStartDateAsc();
    List<ProjectPackage> findAllEndDateAsc();

    List<ProjectPackage> findAllByProjectId(Long id);
}
