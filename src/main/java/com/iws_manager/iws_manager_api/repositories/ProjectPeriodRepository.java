package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.ProjectPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectPeriodRepository extends JpaRepository<ProjectPeriod, Long> {
    List<ProjectPeriod> findAllByOrderByPeriodNoAsc();
    List<ProjectPeriod> findAllByOrderByStartDateAsc();
    List<ProjectPeriod> findAllByOrderByEndDateAsc();
}
