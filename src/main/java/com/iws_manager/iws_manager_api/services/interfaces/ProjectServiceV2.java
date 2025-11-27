package com.iws_manager.iws_manager_api.services.interfaces;

import com.iws_manager.iws_manager_api.dtos.project.ProjectResponseDTO;
import com.iws_manager.iws_manager_api.dtos.project.ProjectRequestDTO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProjectServiceV2 {

    // CRUD Operations
    ProjectResponseDTO create(ProjectRequestDTO projectRequest);

    Optional<ProjectResponseDTO> findById(Long id);

    List<ProjectResponseDTO> findAll();

    ProjectResponseDTO update(Long id, ProjectRequestDTO projectDetails);

    void delete(Long id);

    // Date Fields
    List<ProjectResponseDTO> getProjectsByApprovalDate(LocalDate approvalDate);

    List<ProjectResponseDTO> getProjectsByAuthorizationDate(LocalDate authorizationDate);

    List<ProjectResponseDTO> getProjectsByEndApproval(LocalDate endApproval);

    List<ProjectResponseDTO> getProjectsByEndDate(LocalDate endDate);

    List<ProjectResponseDTO> getProjectsByStartApproval(LocalDate startApproval);

    List<ProjectResponseDTO> getProjectsByStartDate(LocalDate startDate);

    // Number Fields
    List<ProjectResponseDTO> getProjectsByChance(BigDecimal chance);

    List<ProjectResponseDTO> getProjectsByFundingRate(BigDecimal fundingRate);

    List<ProjectResponseDTO> getProjectsByHourlyRateMueu(BigDecimal hourlyRateMueu);

    List<ProjectResponseDTO> getProjectsByMaxHoursPerMonth(BigDecimal maxHoursPerMonth);

    List<ProjectResponseDTO> getProjectsByMaxHoursPerYear(BigDecimal maxHoursPerYear);

    List<ProjectResponseDTO> getProjectsByProductiveHoursPerYear(BigDecimal productiveHoursPerYear);

    List<ProjectResponseDTO> getProjectsByShareResearch(BigDecimal shareResearch);

    List<ProjectResponseDTO> getProjectsByStuffFlat(BigDecimal stuffFlat);

    // Order Fields (ahora con IDs)
    List<ProjectResponseDTO> getProjectsByOrderIdFue(Long orderIdFue);

    List<ProjectResponseDTO> getProjectsByOrderIdAdmin(Long orderIdAdmin);

    // Text Fields
    List<ProjectResponseDTO> getProjectsByCommentContaining(String keyword);

    List<ProjectResponseDTO> getProjectsByFinanceAuthority(String authority);

    List<ProjectResponseDTO> getProjectsByFundingLabel(String label);

    List<ProjectResponseDTO> getProjectsByNoteContaining(String text);

    List<ProjectResponseDTO> getProjectsByProjectLabel(String projectLabel);

    List<ProjectResponseDTO> getProjectsByProjectName(String projectName);

    List<ProjectResponseDTO> getProjectsByTitle(String title);

    // Entity Relationships
    List<ProjectResponseDTO> getProjectsByCustomerId(Long customerId);

    List<ProjectResponseDTO> getProjectsByEmpiws20Id(Long empiws20Id);

    List<ProjectResponseDTO> getProjectsByFundingProgramId(Long fundingProgramId);

    List<ProjectResponseDTO> getProjectsByPromoterId(Long promoterId);

    List<ProjectResponseDTO> getProjectsByStatusId(Long statusId);

    // Date Helpers
    List<ProjectResponseDTO> getProjectsByApprovalDateBetween(LocalDate start, LocalDate end);

    List<ProjectResponseDTO> getProjectsByAuthorizationDateBefore(LocalDate date);

    List<ProjectResponseDTO> getProjectsByEndDateAfter(LocalDate date);

    List<ProjectResponseDTO> getProjectsByStartDateBetween(LocalDate start, LocalDate end);

    // Number Helpers
    List<ProjectResponseDTO> getProjectsByChanceGreaterThan(BigDecimal chance);

    List<ProjectResponseDTO> getProjectsByFundingRateLessThan(BigDecimal fundingRate);

    List<ProjectResponseDTO> getProjectsByHourlyRateMueuBetween(BigDecimal min, BigDecimal max);

    // Text Helpers
    List<ProjectResponseDTO> getProjectsByProjectNameContainingIgnoreCase(String name);

    List<ProjectResponseDTO> getProjectsByFundingLabelStartingWith(String prefix);

    List<ProjectResponseDTO> getProjectsByTitleEndingWith(String suffix);

    // Special Queries
    List<ProjectResponseDTO> getProjectsByCustomerIdOrderByStartDateDesc(Long customerId);
}