package com.iws_manager.iws_manager_api.dtos.employee;

public record EmployeeDTO(
    Long id,
    String firstname,
    String lastname,
    String email,
    Integer employeeno,
    String label,
    Long customerId,
    String customerName
) {}