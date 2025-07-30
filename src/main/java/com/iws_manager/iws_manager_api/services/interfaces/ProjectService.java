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

    List<Project> findByApprovalDate(LocalDate approvalDate);
    List<Project> findByAuthorizationDate(LocalDate authorizationDate);
    List<Project> findByEndApproval(LocalDate endApproval);
    List<Project> findByEndDate(LocalDate endDate);
    List<Project> findByStartApproval(LocalDate startApproval);
    List<Project> findByStartDate(LocalDate startDate);
    List<Project> findByChance(BigDecimal chance);
    List<Project> findByFundingRate(BigDecimal fundingRate);
    List<Project> findByHourlyRateMueu(BigDecimal hourlyRateMueu);
    List<Project> findByMaxHoursPerMonth(BigDecimal maxHoursPerMonth);
    List<Project> findByMaxHoursPerYear(BigDecimal maxHoursPerYear);
    List<Project> findByProductiveHoursPerYear(BigDecimal productiveHoursPerYear);
    List<Project> findByShareResearch(BigDecimal shareResearch);
    List<Project> findByStuffFlat(BigDecimal stuffFlat);
    List<Project> findByOrderIdFue(Integer orderIdFue);
    List<Project> findByOrderIdAdmin(Integer orderIdAdmin);
    List<Project> findByCommentContaining(String keyword);
    List<Project> findByFinanceAuthority(String authority);
    List<Project> findByFundingLabel(String label);
    List<Project> findByNoteContaining(String text);
    List<Project> findByProjectLabel(String projectLabel);
    List<Project> findByProjectName(String projectName);
    List<Project> findByTitle(String title);

    List<Project> findByCustomerId(Long customerId);
    // List<Project> findByEmpiws20Id(Long empiws20Id);  // Cuando se descomente
    // List<Project> findByFundingProgramId(Long fundingProgramId);  // Cuando se descomente
    // List<Project> findByStatusId(Long statusId);  // Cuando se descomente

    List<Project> findByApprovalDateBetween(LocalDate start, LocalDate end);
    List<Project> findByAuthorizationDateBefore(LocalDate date);
    List<Project> findByEndDateAfter(LocalDate date);
    List<Project> findByStartDateBetween(LocalDate start, LocalDate end);
    List<Project> findByChanceGreaterThan(BigDecimal chance);
    List<Project> findByFundingRateLessThan(BigDecimal fundingRate);
    List<Project> findByHourlyRateMueuBetween(BigDecimal min, BigDecimal max);
    List<Project> findByProjectNameContainingIgnoreCase(String name);
    List<Project> findByFundingLabelStartingWith(String prefix);
    List<Project> findByTitleEndingWith(String suffix);
    List<Project> findByCustomerIdOrderByStartDateDesc(Long customerId);
}
