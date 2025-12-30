package com.iws_manager.iws_manager_api.dtos.employee;

import com.iws_manager.iws_manager_api.dtos.shared.BasicReferenceDTO;
import java.time.LocalDate;

public record EmployeeInputDTO(
    String firstname,
    String lastname,
    String email,
    String label,
    String phone,
    LocalDate coentrepreneursince,
    LocalDate generalmanagersince,
    LocalDate shareholdersince,
    LocalDate soleproprietorsince,
    String qualificationkmui,
    
    // Referencias con version - ahora usa el shared
    BasicReferenceDTO customer,
    BasicReferenceDTO qualificationFZ,
    BasicReferenceDTO salutation,
    BasicReferenceDTO title,
    BasicReferenceDTO employeeCategory
) {}