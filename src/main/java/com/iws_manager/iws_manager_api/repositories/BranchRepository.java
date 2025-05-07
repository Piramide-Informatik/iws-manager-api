package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
}