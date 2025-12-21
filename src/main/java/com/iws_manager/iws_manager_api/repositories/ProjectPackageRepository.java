package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.ProjectPackage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;

@Repository
public interface ProjectPackageRepository extends JpaRepository<ProjectPackage, Long> {
    @EntityGraph(attributePaths = {"project", "project.customer", "project.customer.branch", "project.customer.companytype", "project.customer.country", "project.customer.state"})
    List<ProjectPackage> findAllByOrderByPackageTitleAsc();

    @EntityGraph(attributePaths = {"project", "project.customer", "project.customer.branch", "project.customer.companytype", "project.customer.country", "project.customer.state"})
    List<ProjectPackage> findAllByOrderByPackageSerialAsc();

    @EntityGraph(attributePaths = {"project", "project.customer", "project.customer.branch", "project.customer.companytype", "project.customer.country", "project.customer.state"})
    List<ProjectPackage> findAllByOrderByPackageNoAsc();

    @EntityGraph(attributePaths = {"project", "project.customer", "project.customer.branch", "project.customer.companytype", "project.customer.country", "project.customer.state"})
    List<ProjectPackage> findAllByOrderByStartDate();

    @EntityGraph(attributePaths = {"project", "project.customer", "project.customer.branch", "project.customer.companytype", "project.customer.country", "project.customer.state"})
    List<ProjectPackage> findAllByOrderByEndDate();

    @Query("SELECT DISTINCT p FROM ProjectPackage p LEFT JOIN FETCH p.project ORDER BY p.packageTitle ASC")
    List<ProjectPackage> findAllFetchProject();

    @Query("SELECT DISTINCT p FROM ProjectPackage p LEFT JOIN FETCH p.project ORDER BY p.packageTitle ASC")
    List<ProjectPackage> findAllFetchProjectByOrderByTitleAsc();

    @Query("SELECT DISTINCT p FROM ProjectPackage p LEFT JOIN FETCH p.project ORDER BY p.packageSerial ASC")
    List<ProjectPackage> findAllFetchProjectByOrderBySerialAsc();

    @Query("SELECT DISTINCT p FROM ProjectPackage p LEFT JOIN FETCH p.project ORDER BY p.packageNo ASC")
    List<ProjectPackage> findAllFetchProjectByOrderByNoAsc();

    @Query("SELECT DISTINCT p FROM ProjectPackage p LEFT JOIN FETCH p.project ORDER BY p.startDate ASC")
    List<ProjectPackage> findAllFetchProjectByOrderByStartDateAsc();

    @Query("SELECT DISTINCT p FROM ProjectPackage p LEFT JOIN FETCH p.project ORDER BY p.endDate ASC")
    List<ProjectPackage> findAllFetchProjectByOrderByEndDateAsc();

    @Query("SELECT p FROM ProjectPackage p LEFT JOIN FETCH p.project WHERE p.id = :id")
    Optional<ProjectPackage> findByIdFetchProject(Long id);

    @Query("SELECT DISTINCT p FROM ProjectPackage p LEFT JOIN FETCH p.project pr WHERE pr.id = :projectId ORDER BY p.packageTitle ASC")
    List<ProjectPackage> findAllByProjectIdFetchProject(Long projectId);

    /* ============================ */
    /* VALIDATIONS                  */
    /* ============================ */

    // CREATION - verify if packageNo and packageTitle exist (case-insensitive)
    @Query("SELECT COUNT(p) > 0 FROM ProjectPackage p WHERE UPPER(p.packageNo) = UPPER(:packageNo)")
    boolean existsByPackageNoIgnoreCase(@Param("packageNo") String packageNo);

    @Query("SELECT COUNT(p) > 0 FROM ProjectPackage p WHERE UPPER(p.packageTitle) = UPPER(:packageTitle)")
    boolean existsByPackageTitleIgnoreCase(@Param("packageTitle") String packageTitle);

    // UPDATING - verify if packageNo and packageTitle exist with exception of the same item (case-insensitive)
    @Query("SELECT COUNT(p) > 0 FROM ProjectPackage p WHERE UPPER(p.packageNo) = UPPER(:packageNo) AND p.id != :excludeId")
    boolean existsByPackageNoIgnoreCaseAndIdNot(@Param("packageNo") String packageNo, @Param("excludeId") Long excludeId);

    @Query("SELECT COUNT(p) > 0 FROM ProjectPackage p WHERE UPPER(p.packageTitle) = UPPER(:packageTitle) AND p.id != :excludeId")
    boolean existsByPackageTitleIgnoreCaseAndIdNot(@Param("packageTitle") String packageTitle, @Param("excludeId") Long excludeId);
}
