package com.iws_manager.iws_manager_api.repositories;

import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.iws_manager.iws_manager_api.models.Project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByOrderByProjectNameAsc();   

    /* ==================== */
    /* PROPERTIES - BASICOS */
    /* ==================== */

    // --- Date Fields ---
    List<Project> findByApprovalDate(LocalDate approvalDate);
    List<Project> findByAuthorizationDate(LocalDate authorizationDate);
    List<Project> findByEndApproval(LocalDate endApproval);
    List<Project> findByEndDate(LocalDate endDate);
    List<Project> findByStartApproval(LocalDate startApproval);
    List<Project> findByStartDate(LocalDate startDate);

    // --- Number Fields ---
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

    // --- Text Fields ---
    List<Project> findByCommentContaining(String keyword);
    List<Project> findByFinanceAuthority(String authority);
    List<Project> findByFundingLabel(String label);
    List<Project> findByNoteContaining(String text);
    List<Project> findByProjectLabel(String projectLabel);
    List<Project> findByProjectName(String projectName);
    List<Project> findByTitle(String title);

    // --- Entities (Relationships) ---
    List<Project> findByCustomerId(Long customerId);
    // List<Project> findByEmpiws20Id(Long empiws20Id);  // Cuando se descomente
    // List<Project> findByFundingProgramId(Long fundingProgramId);  // Cuando se descomente
    // List<Project> findByStatusId(Long statusId);  // Cuando se descomente


    /* =================== */
    /* HELPERS - UTILITIES */
    /* =================== */

    // --- Date Helpers ---
    List<Project> findByApprovalDateBetween(LocalDate start, LocalDate end);
    List<Project> findByAuthorizationDateBefore(LocalDate date);
    List<Project> findByEndDateAfter(LocalDate date);
    List<Project> findByStartDateBetween(LocalDate start, LocalDate end);

    // --- Number Helpers ---
    List<Project> findByChanceGreaterThan(BigDecimal chance);
    List<Project> findByFundingRateLessThan(BigDecimal fundingRate);
    List<Project> findByHourlyRateMueuBetween(BigDecimal min, BigDecimal max);

    // --- Text Helpers ---
    List<Project> findByProjectNameContainingIgnoreCase(String name);
    List<Project> findByFundingLabelStartingWith(String prefix);
    List<Project> findByTitleEndingWith(String suffix);

    // --- Special Queries ---
    List<Project> findByCustomerIdOrderByStartDateDesc(Long customerId);


    
}