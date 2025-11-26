    package com.iws_manager.iws_manager_api.dtos.projectpackage;

    import java.time.LocalDate;
    import java.util.List;

    public record ProjectPackageWithProjectDTO(
            Long id,
            String packageTitle,
            String packageSerial,
            String packageNo,
            LocalDate startDate,
            LocalDate endDate,
            ProjectDTO project
    ) {
    }
