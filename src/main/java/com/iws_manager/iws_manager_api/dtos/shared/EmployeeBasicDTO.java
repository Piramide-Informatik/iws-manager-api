package com.iws_manager.iws_manager_api.dtos.shared;

public record EmployeeBasicDTO(
    Long id,
    Integer employeeno,
    String firstname,
    String lastname,
    String label,
    Integer version
) {
}