package com.iws_manager.iws_manager_api.dtos.employee;

public record EmployeeNameDTO(
        Long id,
        String firstname,
        String lastname,
        String fullname) {
}