package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.dtos.projectpackage.CreateProjectPackageDTO;
import com.iws_manager.iws_manager_api.dtos.projectpackage.ProjectPackageDTO;
import com.iws_manager.iws_manager_api.dtos.projectpackage.ProjectPackageWithProjectDTO;
import com.iws_manager.iws_manager_api.mappers.ProjectPackageMapper;
import com.iws_manager.iws_manager_api.models.Project;
import com.iws_manager.iws_manager_api.models.ProjectPackage;
import com.iws_manager.iws_manager_api.repositories.ProjectPackageRepository;
import com.iws_manager.iws_manager_api.repositories.ProjectRepository;
import com.iws_manager.iws_manager_api.services.interfaces.ProjectPackageServiceV2;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProjectPackageServiceImplV2 implements ProjectPackageServiceV2 {
    private static final String PROJECTPACKAGE_NOT_FOUND = "ProjectPackage not found";
    private static final String ID_CANNOT_BE_NULL = "ID cannot be null";
    private final ProjectPackageRepository projectPackageRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectPackageServiceImplV2(ProjectPackageRepository projectPackageRepository, ProjectRepository projectRepository) {
        this.projectPackageRepository = projectPackageRepository;
        this.projectRepository = projectRepository;
    }
    @Override
    public ProjectPackageWithProjectDTO create(CreateProjectPackageDTO dto) {
        Project project = projectRepository.findById(dto.projectId())
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + dto.projectId()));
        ProjectPackage projectPackage = ProjectPackageMapper.toEntityWithProject(dto, project);
        ProjectPackage savedProjectPackage = projectPackageRepository.save(projectPackage);
        return ProjectPackageMapper.toDTOWithProject(savedProjectPackage);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectPackage> findById(Long id) {
        return projectPackageRepository.findByIdFetchProject(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectPackage> findAll() {
        return projectPackageRepository.findAllFetchProject();
    }

    @Override
    public ProjectPackage update(Long id, ProjectPackageDTO dto) {
        return projectPackageRepository.findById(id)
                .map(existing -> {
                    if (dto.projectId() != null) {
                        Project project = projectRepository.findById(dto.projectId())
                                .orElseThrow(() -> new RuntimeException("Project not found"));
                        existing.setProject(project);
                    }
                    ProjectPackageMapper.updateEntity(existing, dto);
                    return projectPackageRepository.save(existing);
                }).orElseThrow(()-> new RuntimeException(PROJECTPACKAGE_NOT_FOUND));
    }

    @Override
    public void delete(Long id) {
        validateIdNotNull(id);
        if (projectPackageRepository.existsById(id)) {
            throw new EntityNotFoundException("Project Package not found with id: " + id);
        }
        projectPackageRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectPackage> findAllTitleAsc() {
        return projectPackageRepository.findAllFetchProjectByOrderByTitleAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectPackage> findAllSerialAsc() {
        return projectPackageRepository.findAllFetchProjectByOrderBySerialAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectPackage> findAllPackageNoAsc() {
        return projectPackageRepository.findAllFetchProjectByOrderByNoAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectPackage> findAllStartDateAsc() {
        return projectPackageRepository.findAllFetchProjectByOrderByStartDateAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectPackage> findAllEndDateAsc() {
        return projectPackageRepository.findAllFetchProjectByOrderByEndDateAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectPackage> findAllByProjectId(Long id) {
        return projectPackageRepository.findAllByProjectIdFetchProject(id);
    }

    private void validateIdNotNull(Long id) {
        if (id == null) {
            throw new IllegalArgumentException(ID_CANNOT_BE_NULL);
        }
    }
}
