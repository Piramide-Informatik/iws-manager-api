package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, Long> {
    List<ProjectStatus> findAllByOrderByNameAsc();
}