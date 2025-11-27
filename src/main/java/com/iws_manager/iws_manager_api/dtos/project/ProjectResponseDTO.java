package com.iws_manager.iws_manager_api.dtos.project;

import com.iws_manager.iws_manager_api.dtos.shared.CustomerInfoDTO;
import com.iws_manager.iws_manager_api.dtos.shared.EmployeeIwsBasicDTO;
import com.iws_manager.iws_manager_api.dtos.shared.OrderBasicDTO;
import com.iws_manager.iws_manager_api.dtos.shared.NetworkBasicDTO;
import com.iws_manager.iws_manager_api.dtos.shared.FundingProgramBasicDTO;
import com.iws_manager.iws_manager_api.dtos.shared.PromoterBasicDTO;
import com.iws_manager.iws_manager_api.dtos.shared.ProjectStatusBasicDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ProjectResponseDTO(
        Long id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Integer version,
        LocalDate approvalDate,
        LocalDate authorizationDate,
        BigDecimal chance,
        String comment,
        LocalDate date1,
        LocalDate date2,
        LocalDate date3,
        LocalDate date4,
        LocalDate date5,
        LocalDate dateLevel1,
        LocalDate dateLevel2,
        BigDecimal donation,
        LocalDate endApproval,
        LocalDate endDate,
        String financeAuthority,
        String fundingLabel,
        BigDecimal fundingRate,
        BigDecimal hourlyRateMueu,
        BigDecimal income1,
        BigDecimal income2,
        BigDecimal income3,
        BigDecimal income4,
        BigDecimal income5,
        BigDecimal maxHoursPerMonth,
        BigDecimal maxHoursPerYear,
        BigDecimal stuffFlat,
        BigDecimal productiveHoursPerYear,
        String projectLabel,
        String projectName,
        String note,
        BigDecimal shareResearch,
        LocalDate startApproval,
        LocalDate startDate,
        String title,
        CustomerInfoDTO customer,
        EmployeeIwsBasicDTO empiws20,
        EmployeeIwsBasicDTO empiws30,
        EmployeeIwsBasicDTO empiws50,
        OrderBasicDTO orderFue,
        OrderBasicDTO orderAdmin,
        NetworkBasicDTO network,
        FundingProgramBasicDTO fundingProgram,
        PromoterBasicDTO promoter,
        ProjectStatusBasicDTO status) {
}