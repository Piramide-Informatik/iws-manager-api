package com.iws_manager.iws_manager_api.mappers;

import com.iws_manager.iws_manager_api.dtos.projectpackage.CreateProjectPackageDTO;
import com.iws_manager.iws_manager_api.dtos.projectpackage.ProjectDTO;
import com.iws_manager.iws_manager_api.dtos.projectpackage.ProjectPackageDTO;
import com.iws_manager.iws_manager_api.dtos.projectpackage.ProjectPackageWithProjectDTO;
import com.iws_manager.iws_manager_api.models.Project;
import com.iws_manager.iws_manager_api.models.ProjectPackage;

public class ProjectPackageMapper {
    private ProjectPackageMapper() {
        throw new IllegalStateException("Utility class and  cannot be instantiated");
    }

    public static ProjectPackageDTO toDTO(ProjectPackage projectPackage) {
        Long projectId = projectPackage.getProject() != null
                ? projectPackage.getProject().getId()
                : null;
        return  new ProjectPackageDTO(
                projectPackage.getId(),
                projectPackage.getPackageTitle(),
                projectPackage.getPackageSerial(),
                projectPackage.getPackageNo(),
                projectPackage.getStartDate(),
                projectPackage.getEndDate(),
                projectId,
                projectPackage.getVersion()
        );
    }

    public static ProjectPackageWithProjectDTO toDTOWithProject(ProjectPackage projectPackage) {
        ProjectDTO projectDTO = null;

        if (projectPackage.getProject() != null) {
            projectDTO = new ProjectDTO(
                    projectPackage.getProject().getId(),
                    projectPackage.getProject().getProjectName()
            );
        }
        return  new ProjectPackageWithProjectDTO(
                projectPackage.getId(),
                projectPackage.getPackageTitle(),
                projectPackage.getPackageSerial(),
                projectPackage.getPackageNo(),
                projectPackage.getStartDate(),
                projectPackage.getEndDate(),
                projectDTO,
                projectPackage.getVersion()
        );
    }

    public static ProjectPackage toEntity(ProjectPackageDTO dto) {
        ProjectPackage projectPackage = new ProjectPackage();
        projectPackage.setPackageTitle(dto.packageTitle());
        projectPackage.setPackageSerial(dto.packageSerial());
        projectPackage.setPackageNo(dto.packageNo());
        projectPackage.setStartDate(dto.startDate());
        projectPackage.setEndDate(dto.endDate());
        return projectPackage;
    }

    public static void updateEntity(ProjectPackage projectPackage, ProjectPackageDTO dto){
        if(dto.packageTitle() != null && !dto.packageTitle().isEmpty()){
            projectPackage.setPackageTitle(dto.packageTitle());
        }
        if(dto.packageSerial() != null && !dto.packageSerial().isEmpty()){
            projectPackage.setPackageSerial(dto.packageSerial());
        }
        if(dto.packageNo() != null && !dto.packageNo().isEmpty()){
            projectPackage.setPackageNo(dto.packageNo());
        }
        // Update startDate even if it is null
        projectPackage.setStartDate(dto.startDate());

        // // Update endDate even if it is null
        projectPackage.setEndDate(dto.endDate());
    }

    public static ProjectPackage toEntityWithProject(CreateProjectPackageDTO dto, Project project) {
        if (dto == null || project == null) {
            return null;
        }

        ProjectPackage projectPackage = new ProjectPackage();
        projectPackage.setPackageTitle(dto.packageTitle());
        projectPackage.setPackageSerial(dto.packageSerial());
        projectPackage.setPackageNo(dto.packageNo());
        projectPackage.setStartDate(dto.startDate());
        projectPackage.setEndDate(dto.endDate());
        projectPackage.setProject(project);
        return projectPackage;
    }
}
