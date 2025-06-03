package com.iws_manager.iws_manager_api.services.interfaces;

import com.iws_manager.iws_manager_api.models.ProjectStatus;

import java.util.List;
import java.util.Optional;

public interface ProjectStatusService {
    ProjectStatus create(ProjectStatus projectStatus);
    Optional<ProjectStatus> findById(Long id);
    List<ProjectStatus> findAll();
    ProjectStatus update(Long id, ProjectStatus projectStatusDetails);
    void delete(Long id);
}
