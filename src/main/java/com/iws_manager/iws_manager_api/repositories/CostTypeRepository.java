package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.CostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostTypeRepository extends JpaRepository<CostType, Long> {
}