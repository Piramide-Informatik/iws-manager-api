package com.iws_manager.iws_manager_api.services.interfaces;

import com.iws_manager.iws_manager_api.models.Project;
import com.iws_manager.iws_manager_api.models.ProjectPeriod;

import java.util.List;
import java.util.Optional;

public interface ProjectPeriodService {
    ProjectPeriod create(ProjectPeriod projectPeriod);

    Optional<ProjectPeriod> findById(Long id);

    List<ProjectPeriod> findAll();

    ProjectPeriod update(Long id, ProjectPeriod projectPeriodDetails);

    void delete(Long id);

    List<ProjectPeriod> getAllProjectPeriodsByPeriodNoAsc();

    List<ProjectPeriod> getAllProjectPeriodsByStartDateAsc();

    List<ProjectPeriod> getAllProjectPeriodsByEndDateAsc();

    List<ProjectPeriod> findAllByProjectId(Long id);

    Short getNextYear(Long projectId);

    ProjectPeriod createWithNextYear(ProjectPeriod projectPeriod, Long projectId);

    ProjectPeriod createDefaultPeriodForProject(Project project);
}
