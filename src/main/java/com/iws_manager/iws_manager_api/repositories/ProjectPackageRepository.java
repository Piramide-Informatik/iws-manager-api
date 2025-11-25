package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.ProjectPackage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;

@Repository
public interface ProjectPackageRepository extends JpaRepository<ProjectPackage, Long> {
    @EntityGraph(attributePaths = {"project"})
    List<ProjectPackage> findAllByOrderByPackageTitleAsc();

    @EntityGraph(attributePaths = {"project"})
    List<ProjectPackage> findAllByOrderByPackageSerialAsc();

    @EntityGraph(attributePaths = {"project"})
    List<ProjectPackage> findAllByOrderByPackageNoAsc();

    @EntityGraph(attributePaths = {"project"})
    List<ProjectPackage> findAllByOrderByStartDate();

    @EntityGraph(attributePaths = {"project"})
    List<ProjectPackage> findAllByOrderByEndDate();
}
