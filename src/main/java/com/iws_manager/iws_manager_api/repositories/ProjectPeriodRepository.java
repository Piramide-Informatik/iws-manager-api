package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.ProjectPeriod;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectPeriodRepository extends JpaRepository<ProjectPeriod, Long> {
    @EntityGraph(attributePaths = {"project", "project.customer", "project.customer.branch", "project.customer.companytype", "project.customer.country", "project.customer.state"})
    List<ProjectPeriod> findAllByOrderByPeriodNoAsc();

    @EntityGraph(attributePaths = {"project", "project.customer", "project.customer.branch", "project.customer.companytype", "project.customer.country", "project.customer.state"})
    List<ProjectPeriod> findAllByOrderByStartDateAsc();

    @EntityGraph(attributePaths = {"project", "project.customer", "project.customer.branch", "project.customer.companytype", "project.customer.country", "project.customer.state"})
    List<ProjectPeriod> findAllByOrderByEndDateAsc();

    @EntityGraph(attributePaths = {"project", "project.customer", "project.customer.branch", "project.customer.companytype", "project.customer.country", "project.customer.state"})
    List<ProjectPeriod> findAll();

    @Query("SELECT DISTINCT p FROM ProjectPeriod p LEFT JOIN FETCH p.project pr WHERE pr.id = :projectId ORDER BY p.periodNo ASC")
    List<ProjectPeriod> findAllByProjectIdFetchProject(Long projectId);

    @Query("SELECT MAX(pp.periodNo) FROM ProjectPeriod pp WHERE pp.project.id = :projectId")
    Short findMaxPeriodNoByProject(@Param("projectId") Long projectId);
}
