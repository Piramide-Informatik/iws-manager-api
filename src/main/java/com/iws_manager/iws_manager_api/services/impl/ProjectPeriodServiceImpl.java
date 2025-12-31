package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.ProjectPeriod;
import com.iws_manager.iws_manager_api.repositories.ProjectPeriodRepository;
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

    @Autowired
    public ProjectPeriodServiceImpl(ProjectPeriodRepository projectPeriodRepository) {
        this.projectPeriodRepository = projectPeriodRepository;
    }

    @Override
    public ProjectPeriod create(ProjectPeriod projectPeriod) {
        if (projectPeriod == null) {
            throw  new IllegalArgumentException("projectPeriod cannot be null");
        }
        //Default Dates
        if(projectPeriod.getStartDate() == null || projectPeriod.getEndDate() == null){
            int year = LocalDate.now().getYear();
            projectPeriod.setStartDate(LocalDate.of(year, 1, 1));
            projectPeriod.setEndDate(LocalDate.of(year, 12, 31));
        }
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
}
