package com.iws_manager.iws_manager_api.repositories;

import java.time.LocalDate;
import java.util.List;

import com.iws_manager.iws_manager_api.models.SubcontractYear;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcontractYearRepository extends JpaRepository<SubcontractYear, Long> {
    List<SubcontractYear> findByMonths(Integer months);
    List<SubcontractYear> findBySubcontractId(Long subcontractId);
    List<SubcontractYear> findByYear(LocalDate year);
}