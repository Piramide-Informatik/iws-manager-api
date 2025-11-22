package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.ProjectPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectPackageRepository extends JpaRepository<ProjectPackage, Long> {
    List<ProjectPackage> findAllByOrderByPackageTitleAsc();
    List<ProjectPackage> findAllByOrderByPackageSerialAsc();
    List<ProjectPackage> findAllByOrderByPackageNoAsc();
    List<ProjectPackage> findAllByOrderByStartDate();
    List<ProjectPackage> findAllByOrderByEndDate();
}
