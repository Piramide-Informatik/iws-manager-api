package com.iws_manager.iws_manager_api.services.interfaces;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.Project;

public interface ProjectService {
    Project create(Project project);
    Optional<Project> findById(Long id);
    List<Project> findAll();
    Project update(Long id, Project projectDetails);
    void delete(Long id);

    List<Project> getProjectsByApprovalDate(LocalDate approvalDate);
    List<Project> getProjectsByAuthorizationDate(LocalDate authorizationDate);
    List<Project> getProjectsByEndApproval(LocalDate endApproval);
    List<Project> getProjectsByEndDate(LocalDate endDate);
    List<Project> getProjectsByStartApproval(LocalDate startApproval);
    List<Project> getProjectsByStartDate(LocalDate startDate);
    List<Project> getProjectsByChance(BigDecimal chance);
    List<Project> getProjectsByFundingRate(BigDecimal fundingRate);
    List<Project> getProjectsByHourlyRateMueu(BigDecimal hourlyRateMueu);
    List<Project> getProjectsByMaxHoursPerMonth(BigDecimal maxHoursPerMonth);
    List<Project> getProjectsByMaxHoursPerYear(BigDecimal maxHoursPerYear);
    List<Project> getProjectsByProductiveHoursPerYear(BigDecimal productiveHoursPerYear);
    List<Project> getProjectsByShareResearch(BigDecimal shareResearch);
    List<Project> getProjectsByStuffFlat(BigDecimal stuffFlat);
    List<Project> getProjectsByOrderIdFue(Integer orderIdFue);
    List<Project> getProjectsByOrderIdAdmin(Integer orderIdAdmin);
    List<Project> getProjectsByCommentContaining(String keyword);
    List<Project> getProjectsByFinanceAuthority(String authority);
    List<Project> getProjectsByFundingLabel(String label);
    List<Project> getProjectsByNoteContaining(String text);
    List<Project> getProjectsByProjectLabel(String projectLabel);
    List<Project> getProjectsByProjectName(String projectName);
    List<Project> getProjectsByTitle(String title);

    List<Project> getProjectsByCustomerId(Long customerId);
    // List<Project> getProjectsByEmpiws20Id(Long empiws20Id);  // Cuando se descomente
    List<Project> getProjectsByFundingProgramId(Long fundingProgramId);
    List<Project> getProjectsByPromoterId(Long promoterId);
    // List<Project> getProjectsByStatusId(Long statusId);  // Cuando se descomente

    List<Project> getProjectsByApprovalDateBetween(LocalDate start, LocalDate end);
    List<Project> getProjectsByAuthorizationDateBefore(LocalDate date);
    List<Project> getProjectsByEndDateAfter(LocalDate date);
    List<Project> getProjectsByStartDateBetween(LocalDate start, LocalDate end);
    List<Project> getProjectsByChanceGreaterThan(BigDecimal chance);
    List<Project> getProjectsByFundingRateLessThan(BigDecimal fundingRate);
    List<Project> getProjectsByHourlyRateMueuBetween(BigDecimal min, BigDecimal max);
    List<Project> getProjectsByProjectNameContainingIgnoreCase(String name);
    List<Project> getProjectsByFundingLabelStartingWith(String prefix);
    List<Project> getProjectsByTitleEndingWith(String suffix);
    List<Project> getProjectsByCustomerIdOrderByStartDateDesc(Long customerId);
}
