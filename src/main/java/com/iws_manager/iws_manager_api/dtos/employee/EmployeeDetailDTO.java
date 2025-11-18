package com.iws_manager.iws_manager_api.dtos.employee;

import java.time.LocalDate;

public record EmployeeDetailDTO(
    Long id,
    String firstname,
    String lastname,
    String email,
    Integer employeeno,
    String label,
    String phone,
    LocalDate coentrepreneursince,
    LocalDate generalmanagersince,
    LocalDate shareholdersince,
    LocalDate soleproprietorsince,
    String qualificationkmui,
    
    // Relaciones optimizadas
    CustomerInfoDTO customer,
    QualificationFZInfoDTO qualificationFZ,
    SalutationInfoDTO salutation,
    TitleInfoDTO title,
    EmployeeCategoryInfoDTO employeeCategory
) {}