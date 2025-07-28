package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovalStatusRepository extends JpaRepository<ApprovalStatus, Long> {
    List<ApprovalStatus> findAllByOrderByNameAsc();
}
