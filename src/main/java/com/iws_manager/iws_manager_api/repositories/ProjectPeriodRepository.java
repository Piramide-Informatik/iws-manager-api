package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.ProjectPeriod;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Repository
public interface ProjectPeriodRepository extends JpaRepository<ProjectPeriod, Long> {
    @EntityGraph(attributePaths = { "project", "project.customer", "project.customer.branch",
            "project.customer.companytype", "project.customer.country", "project.customer.state" })
    List<ProjectPeriod> findAllByOrderByPeriodNoAsc();

    @EntityGraph(attributePaths = { "project", "project.customer", "project.customer.branch",
            "project.customer.companytype", "project.customer.country", "project.customer.state" })
    List<ProjectPeriod> findAllByOrderByStartDateAsc();

    @EntityGraph(attributePaths = { "project", "project.customer", "project.customer.branch",
            "project.customer.companytype", "project.customer.country", "project.customer.state" })
    List<ProjectPeriod> findAllByOrderByEndDateAsc();

    @EntityGraph(attributePaths = { "project", "project.customer", "project.customer.branch",
            "project.customer.companytype", "project.customer.country", "project.customer.state" })
    List<ProjectPeriod> findAll();

    @Query("SELECT DISTINCT p FROM ProjectPeriod p LEFT JOIN FETCH p.project pr WHERE pr.id = :projectId ORDER BY p.periodNo ASC")
    List<ProjectPeriod> findAllByProjectIdFetchProject(Long projectId);

    @Query("SELECT MAX(pp.periodNo) FROM ProjectPeriod pp WHERE pp.project.id = :projectId")
    Short findMaxPeriodNoByProject(@Param("projectId") Long projectId);

    @Query("SELECT pp FROM ProjectPeriod pp WHERE pp.project.id = :projectId AND pp.periodNo = :periodNo")
    Optional<ProjectPeriod> findByProjectIdAndPeriodNo(@Param("projectId") Long projectId,
            @Param("periodNo") Short periodNo);

    @Query("""
        SELECT COUNT(pp) > 0 
        FROM ProjectPeriod pp 
        WHERE pp.project.id = :projectId 
        AND (:excludeId IS NULL OR pp.id != :excludeId)
        AND (
            (pp.startDate <= :endDate AND pp.endDate >= :startDate)
        )
    """)
    boolean existsOverlappingPeriod(
        @Param("projectId") Long projectId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("excludeId") Long excludeId
    );
    
    /**
     * Método para obtener los periodos solapados (para mensajes de error más detallados)
     */
    @Query("""
        SELECT pp 
        FROM ProjectPeriod pp 
        WHERE pp.project.id = :projectId 
        AND (:excludeId IS NULL OR pp.id != :excludeId)
        AND (
            (pp.startDate <= :endDate AND pp.endDate >= :startDate)
        )
        ORDER BY pp.startDate
    """)
    List<ProjectPeriod> findOverlappingPeriods(
        @Param("projectId") Long projectId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("excludeId") Long excludeId
    );
}
