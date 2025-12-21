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
import com.iws_manager.iws_manager_api.exception.exceptions.DuplicateResourceException;
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
            
        validateUniqueConstraintsForCreation(dto.packageNo(), dto.packageTitle());
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
                    validateUniqueConstraintsForUpdate(existing, dto, id);
                    
                    if (dto.projectId() != null) {
                        Project project = projectRepository.findById(dto.projectId())
                                .orElseThrow(() -> new RuntimeException("Project not found"));
                        existing.setProject(project);
                    } else {
                        // If null comes -> delete relationship
                        existing.setProject(null);
                    }
                    ProjectPackageMapper.updateEntity(existing, dto);
                    return projectPackageRepository.save(existing);
                }).orElseThrow(()-> new RuntimeException(PROJECTPACKAGE_NOT_FOUND));
    }

    @Override
    public void delete(Long id) {
        validateIdNotNull(id);
        if (!projectPackageRepository.existsById(id)) {
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

    /**
     * Validates that packageNo and packageTitle are unique for creation (case-insensitive).
     */
    private void validateUniqueConstraintsForCreation(String packageNo, String packageTitle) {
        boolean packageNoExists = packageNo != null && projectPackageRepository.existsByPackageNoIgnoreCase(packageNo);
        boolean packageTitleExists = packageTitle != null && projectPackageRepository.existsByPackageTitleIgnoreCase(packageTitle);
        
        if (packageNoExists || packageTitleExists) {
            buildAndThrowDuplicateException(
                packageNoExists, 
                packageTitleExists, 
                packageNo, 
                packageTitle
            );
        }
    }

    /**
     * Validates that packageNo and packageTitle are unique for update, 
     * considering only other records (case-insensitive).
     * Only validates if the fields have changed.
     */
    private void validateUniqueConstraintsForUpdate(
        ProjectPackage existingPackage, 
        ProjectPackageDTO newPackageDTO, 
        Long id
    ) {
        boolean packageNoChanged = newPackageDTO.packageNo() != null && 
                                !existingPackage.getPackageNo().equals(newPackageDTO.packageNo());
        boolean packageTitleChanged = newPackageDTO.packageTitle() != null && 
                                    !existingPackage.getPackageTitle().equals(newPackageDTO.packageTitle());
        
        if (packageNoChanged || packageTitleChanged) {
            boolean packageNoExists = packageNoChanged && 
                projectPackageRepository.existsByPackageNoIgnoreCaseAndIdNot(newPackageDTO.packageNo(), id);
            boolean packageTitleExists = packageTitleChanged && 
                projectPackageRepository.existsByPackageTitleIgnoreCaseAndIdNot(newPackageDTO.packageTitle(), id);
            
            if (packageNoExists || packageTitleExists) {
                buildAndThrowDuplicateException(
                    packageNoExists, 
                    packageTitleExists, 
                    newPackageDTO.packageNo(), 
                    newPackageDTO.packageTitle()
                );
            }
        }
    }

    /**
     * Builds and throws the appropriate duplicate exception based on which fields are duplicated.
     */
    private void buildAndThrowDuplicateException(
        boolean packageNoExists, 
        boolean packageTitleExists, 
        String packageNo, 
        String packageTitle
    ) {
        if (packageNoExists && packageTitleExists) {
            throw new DuplicateResourceException(
                "ProjectPackage duplication with attributes 'packageNo' = '" + packageNo + 
                "' and 'packageTitle' = '" + packageTitle + "'"
            );
        } else if (packageNoExists) {
            throw new DuplicateResourceException(
                "ProjectPackage duplication with attribute 'packageNo' = '" + packageNo + "'"
            );
        } else if (packageTitleExists) {
            throw new DuplicateResourceException(
                "ProjectPackage duplication with attribute 'packageTitle' = '" + packageTitle + "'"
            );
        }
    }
}
