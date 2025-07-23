package com.iws_manager.iws_manager_api.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.ProjectCostCenter;

public interface ProjectCostCenterService {
    ProjectCostCenter create(ProjectCostCenter projectCostCenter);
    Optional<ProjectCostCenter> findById(Long id);
    List<ProjectCostCenter> findAll();
    ProjectCostCenter update(Long id, ProjectCostCenter projectCostCenterDetails);
    void delete(Long id);
}
