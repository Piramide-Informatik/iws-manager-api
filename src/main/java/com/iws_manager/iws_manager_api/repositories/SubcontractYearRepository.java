package com.iws_manager.iws_manager_api.repositories;

import java.util.List;
import com.iws_manager.iws_manager_api.models.SubcontractYear;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcontractYearRepository extends JpaRepository<SubcontractYear, Long> {
}