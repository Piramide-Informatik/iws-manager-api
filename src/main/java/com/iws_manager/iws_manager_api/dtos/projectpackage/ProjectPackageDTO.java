package com.iws_manager.iws_manager_api.dtos.projectpackage;

import java.time.LocalDate;

public record ProjectPackageDTO(
        Long id,
        String packageTitle,
        String packageSerial,
        String packageNo,
        LocalDate startDate,
        LocalDate endDate,
        Long projectId,
        Integer version
) {
}
