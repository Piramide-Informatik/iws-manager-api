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
        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findAllByOrderByProjectNameAsc();

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findAllByOrderByProjectLabelAsc();

        /* ==================== */
        /* PROPERTIES - BASICOS */
        /* ==================== */

        // --- Date Fields ---
        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByApprovalDate(LocalDate approvalDate);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByAuthorizationDate(LocalDate authorizationDate);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByEndApproval(LocalDate endApproval);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByEndDate(LocalDate endDate);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByStartApproval(LocalDate startApproval);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByStartDate(LocalDate startDate);

        // --- Number Fields ---
        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByChance(BigDecimal chance);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByFundingRate(BigDecimal fundingRate);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByHourlyRateMueu(BigDecimal hourlyRateMueu);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByMaxHoursPerMonth(BigDecimal maxHoursPerMonth);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByMaxHoursPerYear(BigDecimal maxHoursPerYear);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByProductiveHoursPerYear(BigDecimal productiveHoursPerYear);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByShareResearch(BigDecimal shareResearch);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByStuffFlat(BigDecimal stuffFlat);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByOrderFueId(Long orderFueId);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByOrderAdminId(Long orderAdminId);

        // --- Text Fields ---
        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByCommentContaining(String keyword);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByFinanceAuthority(String authority);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByFundingLabel(String label);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByNoteContaining(String text);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByProjectLabel(String projectLabel);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByProjectName(String projectName);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByTitle(String title);

        // --- Entities (Relationships) ---
        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByCustomerId(Long customerId);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByCustomerIdOrderByProjectLabelAsc(Long customerId);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByEmpiws20Id(Long empiws20Id);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByFundingProgramId(Long fundingProgramId);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByPromoterId(Long promoterId);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByStatusId(Long statusId);

        /* =================== */
        /* HELPERS - UTILITIES */
        /* =================== */

        // --- Date Helpers ---
        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByApprovalDateBetween(LocalDate start, LocalDate end);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByAuthorizationDateBefore(LocalDate date);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByEndDateAfter(LocalDate date);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByStartDateBetween(LocalDate start, LocalDate end);

        // --- Number Helpers ---
        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByChanceGreaterThan(BigDecimal chance);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByFundingRateLessThan(BigDecimal fundingRate);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByHourlyRateMueuBetween(BigDecimal min, BigDecimal max);

        // --- Text Helpers ---
        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByProjectNameContainingIgnoreCase(String name);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByFundingLabelStartingWith(String prefix);

        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByTitleEndingWith(String suffix);

        // --- Special Queries ---
        @EntityGraph(attributePaths = { "customer", "customer.branch", "customer.companytype", "customer.country",
                        "customer.state", "fundingProgram", "promoter", "promoter.country", "orderFue", "orderAdmin",
                        "empiws20", "empiws30", "empiws50", "network", "status" })
        List<Project> findByCustomerIdOrderByStartDateDesc(Long customerId);
}