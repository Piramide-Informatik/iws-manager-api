package com.iws_manager.iws_manager_api.repositories;

import java.util.List;

import com.iws_manager.iws_manager_api.models.PayCondition;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayConditionRepository extends JpaRepository<PayCondition, Long> {
    
    List<PayCondition> findAllByOrderByNameAsc();
    
    List<PayCondition> findByName(String name);
    List<PayCondition> findByDeadline(Integer deadline);
}