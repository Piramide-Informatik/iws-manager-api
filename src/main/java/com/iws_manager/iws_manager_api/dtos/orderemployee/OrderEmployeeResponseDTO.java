package com.iws_manager.iws_manager_api.dtos.orderemployee;

import com.iws_manager.iws_manager_api.dtos.shared.EmployeeBasicDTO;
import com.iws_manager.iws_manager_api.dtos.shared.OrderReferenceDTO;
import com.iws_manager.iws_manager_api.dtos.shared.QualificationFZReferenceDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderEmployeeResponseDTO(
    Long id,
    Integer orderemployeeno,
    BigDecimal hourlyrate,
    BigDecimal plannedhours,
    String qualificationkmui,
    String title,
    Integer version,
    
    EmployeeBasicDTO employee,
    OrderReferenceDTO order,
    QualificationFZReferenceDTO qualificationFZ
) {}