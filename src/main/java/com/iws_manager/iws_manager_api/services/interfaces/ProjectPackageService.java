package com.iws_manager.iws_manager_api.services.interfaces;

import com.iws_manager.iws_manager_api.models.ProjectPackage;

import java.util.List;
import java.util.Optional;

public interface ProjectPackageService {
    ProjectPackage create(ProjectPackage projectPackage);
    Optional<ProjectPackage> findById(Long id);
    List<ProjectPackage>  findAll();
    ProjectPackage update(Long id, ProjectPackage projectPackageDetails);
    void delete(Long id);

    List<ProjectPackage> findAllTitleAsc();
    List<ProjectPackage> findAllSerialAsc();
    List<ProjectPackage> findAllPackageNoAsc();
    List<ProjectPackage> findAllStartDateAsc();
    List<ProjectPackage> findAllEndDateAsc();
}
