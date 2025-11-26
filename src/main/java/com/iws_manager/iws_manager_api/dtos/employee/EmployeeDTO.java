package com.iws_manager.iws_manager_api.dtos.employee;

import java.time.LocalDate;

public record EmployeeDTO(
        Long id,
        String firstname,
        String lastname,
        String email,
        Integer employeeno,
        String label,

        // Additional fields (frontend overview table)
        LocalDate generalmanagersince,
        LocalDate shareholdersince,
        LocalDate soleproprietorsince,
        LocalDate coentrepreneursince,
        String qualificationkmui,

        // Relations
        QualificationFZInfoDTO qualificationFZ,
        EmployeeCategoryInfoDTO employeeCategory,

        // Additional fields
        Long customerId,
        String customerName) {
}