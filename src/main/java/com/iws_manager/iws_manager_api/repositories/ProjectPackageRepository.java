package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.ProjectPackage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
