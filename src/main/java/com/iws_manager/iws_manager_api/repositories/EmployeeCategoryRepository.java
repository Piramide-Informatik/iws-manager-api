package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.EmployeeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeCategoryRepository extends JpaRepository<EmployeeCategory, Long> {
    List<EmployeeCategory> findAllByOrderByTitleAsc();
}
