package com.iws_manager.iws_manager_api.dtos.employee;

public record EmployeeNameDTO(
                Long id,
                Integer employeeno,
                String firstname,
                String lastname,
                String fullname) {
}