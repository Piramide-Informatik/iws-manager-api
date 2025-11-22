package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.ProjectPackage;
import com.iws_manager.iws_manager_api.repositories.ProjectPackageRepository;
import com.iws_manager.iws_manager_api.services.interfaces.ProjectPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class ProjectPackageServiceImpl implements ProjectPackageService {
    private final ProjectPackageRepository projectPackageRepository;

    @Autowired
    public ProjectPackageServiceImpl(ProjectPackageRepository projectPackageRepository) {
        this.projectPackageRepository = projectPackageRepository;
    }

    @Override
    public ProjectPackage create(ProjectPackage projectPackage) {
        if (projectPackage==null) {
            throw new IllegalArgumentException("projectPackage cannot be null");
        }
        return projectPackageRepository.save(projectPackage);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectPackage> findById(Long id) {
        if (id==null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return projectPackageRepository.findById(id);
    }

    @Override
    public List<ProjectPackage> findAll() {
        return projectPackageRepository.findAll();
    }

    @Override
    public ProjectPackage update(Long id, ProjectPackage projectPackageDetails) {
        if (id == null || projectPackageDetails==null) {
            throw new IllegalArgumentException("id and projectPackageDetails cannot be null");
        }
        return projectPackageRepository.findById(id)
                .map(existingProjectPackage -> {
                    existingProjectPackage.setPackageTitle(projectPackageDetails.getPackageTitle());
                    existingProjectPackage.setPackageSerial(projectPackageDetails.getPackageSerial());
                    existingProjectPackage.setPackageNo(projectPackageDetails.getPackageNo());
                    existingProjectPackage.setStartDate(projectPackageDetails.getStartDate());
                    existingProjectPackage.setEndDate(projectPackageDetails.getEndDate());
                    existingProjectPackage.setProject(projectPackageDetails.getProject());

                    return projectPackageRepository.save(existingProjectPackage);
                })
                .orElseThrow(() -> new RuntimeException("ProjectPackage not found with id " + id));
    }

    @Override
    public void delete(Long id) {
        if (id==null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        projectPackageRepository.deleteById(id);
    }

    @Override
    public List<ProjectPackage> findAllTitleAsc() {
        return projectPackageRepository.findAllByOrderByPackageTitleAsc();
    }

    @Override
    public List<ProjectPackage> findAllSerialAsc() {
        return projectPackageRepository.findAllByOrderByPackageSerialAsc();
    }

    @Override
    public List<ProjectPackage> findAllPackageNoAsc() {
        return projectPackageRepository.findAllByOrderByPackageNoAsc();
    }

    @Override
    public List<ProjectPackage> findAllStartDateAsc() {
        return projectPackageRepository.findAllByOrderByStartDate();
    }

    @Override
    public List<ProjectPackage> findAllEndDateAsc() {
        return projectPackageRepository.findAllByOrderByEndDate();
    }
}
