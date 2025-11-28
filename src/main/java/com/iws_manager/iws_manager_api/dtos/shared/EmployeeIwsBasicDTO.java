package com.iws_manager.iws_manager_api.dtos.shared;

import java.time.LocalDate;

public record EmployeeIwsBasicDTO(
        Long id,
        String employeeLabel,
        Integer employeeNo,
        String firstname,
        String lastname,
        String mail,
        LocalDate startDate,
        LocalDate endDate,
        Integer active) {
}