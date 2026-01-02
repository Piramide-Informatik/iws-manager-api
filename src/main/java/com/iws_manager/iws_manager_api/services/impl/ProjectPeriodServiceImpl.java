package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.Project;
import com.iws_manager.iws_manager_api.models.ProjectPeriod;
import com.iws_manager.iws_manager_api.repositories.ProjectPeriodRepository;
import com.iws_manager.iws_manager_api.repositories.ProjectRepository;
import com.iws_manager.iws_manager_api.services.interfaces.ProjectPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProjectPeriodServiceImpl implements ProjectPeriodService {
    private final ProjectPeriodRepository projectPeriodRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectPeriodServiceImpl(ProjectPeriodRepository projectPeriodRepository, ProjectRepository projectRepository) {
        this.projectPeriodRepository = projectPeriodRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectPeriod create(ProjectPeriod projectPeriod) {
        if (projectPeriod == null) {
            throw  new IllegalArgumentException("projectPeriod cannot be null");
        }
        //Default Dates
        setDefaultDates(projectPeriod);
        return projectPeriodRepository.save(projectPeriod);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectPeriod> findById(Long id) {
        if (id == null) {
            throw  new IllegalArgumentException("id cannot be null");
        }
        return projectPeriodRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectPeriod> findAll() {
        return projectPeriodRepository.findAll();
    }

    @Override
    public ProjectPeriod update(Long id, ProjectPeriod projectPeriodDetails) {
        if (id == null || projectPeriodDetails == null) {
            throw  new IllegalArgumentException("id and projectPeriodDetails cannot be null");
        }
        return projectPeriodRepository.findById(id)
                .map( existingProjectPeriod -> {
                    existingProjectPeriod.setPeriodNo(projectPeriodDetails.getPeriodNo());
                    existingProjectPeriod.setStartDate(projectPeriodDetails.getStartDate());
                    existingProjectPeriod.setEndDate(projectPeriodDetails.getEndDate());
                    existingProjectPeriod.setProject(projectPeriodDetails.getProject());
                    return projectPeriodRepository.save(existingProjectPeriod);
                })
                .orElseThrow(()-> new RuntimeException("ProjectPeriod not found with id " + id));
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw  new IllegalArgumentException("id cannot be null");
        }

        if (!projectPeriodRepository.existsById(id)) {  
            throw new EntityNotFoundException("ProjectPeriod not found with id: " + id);
        }

        projectPeriodRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectPeriod> getAllProjectPeriodsByPeriodNoAsc() {
        return projectPeriodRepository.findAllByOrderByPeriodNoAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectPeriod> getAllProjectPeriodsByStartDateAsc() {
        return projectPeriodRepository.findAllByOrderByStartDateAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectPeriod> getAllProjectPeriodsByEndDateAsc() {
        return projectPeriodRepository.findAllByOrderByEndDateAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectPeriod> findAllByProjectId(Long id) {
        return projectPeriodRepository.findAllByProjectIdFetchProject(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Short getNextYear(Long projectId) {
        Short maxYear = projectPeriodRepository.findMaxPeriodNoByProject(projectId);
        return (maxYear == null) ? 1 : (short) (maxYear + 1);
    }

    @Override
    public ProjectPeriod createWithNextYear(ProjectPeriod projectPeriod, Long projectId) {
        if (projectPeriod == null) {
            throw new IllegalArgumentException("projectPeriod cannot be null");
        }
        Project project = projectRepository.getReferenceById(projectId);

        Short newYear = getNextYear(projectId);

        projectPeriod.setPeriodNo(newYear);
        projectPeriod.setProject(project);
        //Default Dates
        setDefaultDates(projectPeriod);

        return projectPeriodRepository.save(projectPeriod);
    }

    private void setDefaultDates(ProjectPeriod projectPeriod) {
        int year = LocalDate.now().getYear();
        if (projectPeriod.getStartDate() == null) {
            projectPeriod.setStartDate(LocalDate.of(year, 1, 1));
        }
        if (projectPeriod.getEndDate() == null) {
            projectPeriod.setEndDate(LocalDate.of(year, 12, 31));
        }
    }
}
