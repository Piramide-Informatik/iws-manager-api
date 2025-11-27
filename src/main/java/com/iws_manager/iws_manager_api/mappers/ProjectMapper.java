package com.iws_manager.iws_manager_api.dtos.project;

import com.iws_manager.iws_manager_api.models.Project;
import com.iws_manager.iws_manager_api.dtos.shared.CustomerInfoDTO;
import com.iws_manager.iws_manager_api.dtos.shared.EmployeeIwsBasicDTO;
import com.iws_manager.iws_manager_api.dtos.shared.OrderBasicDTO;
import com.iws_manager.iws_manager_api.dtos.shared.NetworkBasicDTO;
import com.iws_manager.iws_manager_api.dtos.shared.FundingProgramBasicDTO;
import com.iws_manager.iws_manager_api.dtos.shared.PromoterBasicDTO;
import com.iws_manager.iws_manager_api.dtos.shared.ProjectStatusBasicDTO;

public class ProjectMapper {

    public static ProjectResponseDTO toResponseDTO(Project project) {
        if (project == null)
            return null;

        return new ProjectResponseDTO(
                project.getId(),
                project.getCreatedAt(),
                project.getUpdatedAt(),
                project.getVersion(),
                project.getApprovalDate(),
                project.getAuthorizationDate(),
                project.getChance(),
                project.getComment(),
                project.getDate1(),
                project.getDate2(),
                project.getDate3(),
                project.getDate4(),
                project.getDate5(),
                project.getDateLevel1(),
                project.getDateLevel2(),
                project.getDonation(),
                project.getEndApproval(),
                project.getEndDate(),
                project.getFinanceAuthority(),
                project.getFundingLabel(),
                project.getFundingRate(),
                project.getHourlyRateMueu(),
                project.getIncome1(),
                project.getIncome2(),
                project.getIncome3(),
                project.getIncome4(),
                project.getIncome5(),
                project.getMaxHoursPerMonth(),
                project.getMaxHoursPerYear(),
                project.getStuffFlat(),
                project.getProductiveHoursPerYear(),
                project.getProjectLabel(),
                project.getProjectName(),
                project.getNote(),
                project.getShareResearch(),
                project.getStartApproval(),
                project.getStartDate(),
                project.getTitle(),
                // Customer
                project.getCustomer() != null ? new CustomerInfoDTO(
                        project.getCustomer().getId(),
                        project.getCustomer().getCustomername1(),
                        project.getCustomer().getCustomername2(),
                        project.getCustomer().getCity()) : null,
                // empiws20
                project.getEmpiws20() != null ? new EmployeeIwsBasicDTO(
                        project.getEmpiws20().getId(),
                        project.getEmpiws20().getEmployeeLabel(),
                        project.getEmpiws20().getEmployeeNo(),
                        project.getEmpiws20().getFirstname(),
                        project.getEmpiws20().getLastname(),
                        project.getEmpiws20().getMail(),
                        project.getEmpiws20().getStartDate(),
                        project.getEmpiws20().getEndDate(),
                        project.getEmpiws20().getActive()) : null,
                // empiws30
                project.getEmpiws30() != null ? new EmployeeIwsBasicDTO(
                        project.getEmpiws30().getId(),
                        project.getEmpiws30().getEmployeeLabel(),
                        project.getEmpiws30().getEmployeeNo(),
                        project.getEmpiws30().getFirstname(),
                        project.getEmpiws30().getLastname(),
                        project.getEmpiws30().getMail(),
                        project.getEmpiws30().getStartDate(),
                        project.getEmpiws30().getEndDate(),
                        project.getEmpiws30().getActive()) : null,
                // empiws50
                project.getEmpiws50() != null ? new EmployeeIwsBasicDTO(
                        project.getEmpiws50().getId(),
                        project.getEmpiws50().getEmployeeLabel(),
                        project.getEmpiws50().getEmployeeNo(),
                        project.getEmpiws50().getFirstname(),
                        project.getEmpiws50().getLastname(),
                        project.getEmpiws50().getMail(),
                        project.getEmpiws50().getStartDate(),
                        project.getEmpiws50().getEndDate(),
                        project.getEmpiws50().getActive()) : null,
                // orderFue
                project.getOrderFue() != null ? new OrderBasicDTO(
                        project.getOrderFue().getId(),
                        project.getOrderFue().getAcronym(),
                        project.getOrderFue().getApprovalDate(),
                        project.getOrderFue().getOrderLabel(),
                        project.getOrderFue().getOrderNo(),
                        project.getOrderFue().getOrderTitle(),
                        project.getOrderFue().getOrderValue(),
                        project.getOrderFue().getIwsProvision(),
                        project.getOrderFue().getOrderDate(),
                        project.getOrderFue().getSignatureDate()) : null,
                // orderAdmin
                project.getOrderAdmin() != null ? new OrderBasicDTO(
                        project.getOrderAdmin().getId(),
                        project.getOrderAdmin().getAcronym(),
                        project.getOrderAdmin().getApprovalDate(),
                        project.getOrderAdmin().getOrderLabel(),
                        project.getOrderAdmin().getOrderNo(),
                        project.getOrderAdmin().getOrderTitle(),
                        project.getOrderAdmin().getOrderValue(),
                        project.getOrderAdmin().getIwsProvision(),
                        project.getOrderAdmin().getOrderDate(),
                        project.getOrderAdmin().getSignatureDate()) : null,
                // network
                project.getNetwork() != null ? new NetworkBasicDTO(
                        project.getNetwork().getId(),
                        project.getNetwork().getName()) : null,
                // fundingProgram
                project.getFundingProgram() != null ? new FundingProgramBasicDTO(
                        project.getFundingProgram().getId(),
                        project.getFundingProgram().getName()) : null,
                // promoter
                project.getPromoter() != null ? new PromoterBasicDTO(
                        project.getPromoter().getId(),
                        project.getPromoter().getPromoterName1(),
                        project.getPromoter().getPromoterName2(),
                        project.getPromoter().getPromoterNo(),
                        project.getPromoter().getCity(),
                        project.getPromoter().getStreet(),
                        project.getPromoter().getZipCode()) : null,
                // status
                project.getStatus() != null ? new ProjectStatusBasicDTO(
                        project.getStatus().getId(),
                        project.getStatus().getName()) : null);
    }
}