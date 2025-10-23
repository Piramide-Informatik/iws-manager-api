package com.iws_manager.iws_manager_api.repositories;

import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.iws_manager.iws_manager_api.models.Project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findAllByOrderByProjectNameAsc();   

    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findAllByOrderByProjectLabelAsc();   

    /* ==================== */
    /* PROPERTIES - BASICOS */
    /* ==================== */

    // --- Date Fields ---
    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByApprovalDate(LocalDate approvalDate);

    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByAuthorizationDate(LocalDate authorizationDate);

    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByEndApproval(LocalDate endApproval);

    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByEndDate(LocalDate endDate);

    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByStartApproval(LocalDate startApproval);

    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByStartDate(LocalDate startDate);

    // --- Number Fields ---
    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByChance(BigDecimal chance);

    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByFundingRate(BigDecimal fundingRate);

    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByHourlyRateMueu(BigDecimal hourlyRateMueu);

    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByMaxHoursPerMonth(BigDecimal maxHoursPerMonth);

    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByMaxHoursPerYear(BigDecimal maxHoursPerYear);

    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByProductiveHoursPerYear(BigDecimal productiveHoursPerYear);

    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByShareResearch(BigDecimal shareResearch);

    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByStuffFlat(BigDecimal stuffFlat);

    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByOrderIdFue(Integer orderIdFue);

    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByOrderIdAdmin(Integer orderIdAdmin);

    // --- Text Fields ---
    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByCommentContaining(String keyword);

    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByFinanceAuthority(String authority);

    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByFundingLabel(String label);

    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByNoteContaining(String text);

    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByProjectLabel(String projectLabel);

    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByProjectName(String projectName);

    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByTitle(String title);

    // --- Entities (Relationships) ---
    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByCustomerId(Long customerId);
    // List<Project> findByEmpiws20Id(Long empiws20Id);  // Cuando se descomente

    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByFundingProgramId(Long fundingProgramId);

    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByPromoterId(Long promoterId);
    // List<Project> findByStatusId(Long statusId);  // Cuando se descomente


    /* =================== */
    /* HELPERS - UTILITIES */
    /* =================== */

    // --- Date Helpers ---
    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByApprovalDateBetween(LocalDate start, LocalDate end);

    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByAuthorizationDateBefore(LocalDate date);

    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByEndDateAfter(LocalDate date);

    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByStartDateBetween(LocalDate start, LocalDate end);

    // --- Number Helpers ---
    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByChanceGreaterThan(BigDecimal chance);

    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByFundingRateLessThan(BigDecimal fundingRate);

    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByHourlyRateMueuBetween(BigDecimal min, BigDecimal max);

    // --- Text Helpers ---
    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByProjectNameContainingIgnoreCase(String name);

    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByFundingLabelStartingWith(String prefix);

    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByTitleEndingWith(String suffix);

    // --- Special Queries ---
    @EntityGraph(attributePaths = {"customer", "fundingProgram", "promoter"})
    List<Project> findByCustomerIdOrderByStartDateDesc(Long customerId);
}