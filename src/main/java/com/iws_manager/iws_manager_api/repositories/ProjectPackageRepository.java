package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.ProjectPackage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
}
