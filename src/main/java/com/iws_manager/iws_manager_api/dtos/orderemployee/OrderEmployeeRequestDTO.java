package com.iws_manager.iws_manager_api.dtos.orderemployee;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import com.iws_manager.iws_manager_api.dtos.shared.BasicReferenceDTO;

public record OrderEmployeeRequestDTO(
    
    @DecimalMin(value = "0.0", inclusive = false, message = "Hourly rate must be greater than 0")
    BigDecimal hourlyrate,
    
    @DecimalMin(value = "0.0", inclusive = false, message = "Planned hours must be greater than 0")
    BigDecimal plannedhours,

    String qualificationkmui,
    String title,
    Integer orderemployeeno,
    
    BasicReferenceDTO order,
    BasicReferenceDTO qualificationFZ,
    BasicReferenceDTO employee
) {}