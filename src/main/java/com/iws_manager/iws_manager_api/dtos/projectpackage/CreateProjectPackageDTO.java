package com.iws_manager.iws_manager_api.dtos.projectpackage;

import java.time.LocalDate;

public record CreateProjectPackageDTO(
        String packageTitle,
        String packageSerial,
        String packageNo,
        LocalDate startDate,
        LocalDate endDate,
        Long projectId
) {
}
