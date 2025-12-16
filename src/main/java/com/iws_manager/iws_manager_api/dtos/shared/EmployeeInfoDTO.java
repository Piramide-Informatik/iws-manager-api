package com.iws_manager.iws_manager_api.dtos.shared;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public record EmployeeInfoDTO(
    Long id,
    String firstname,
    String lastname,
    String email,
    Integer employeeno,
    String label,
    String phone,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate coentrepreneursince,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate generalmanagersince,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate shareholdersince,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate soleproprietorsince,
    String qualificationkmui,
    Integer version,
    
    BasicReferenceDTO customer,
    BasicReferenceDTO qualificationFZ,
    BasicReferenceDTO salutation,
    BasicReferenceDTO title,
    BasicReferenceDTO employeeCategory
) {}