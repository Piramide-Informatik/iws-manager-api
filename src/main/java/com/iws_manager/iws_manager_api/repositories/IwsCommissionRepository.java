package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.IwsCommission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface IwsCommissionRepository extends JpaRepository<IwsCommission, Long> {
    List<IwsCommission> findAllByOrderByFromOrderValueAsc();

    // New function: greater than or equal to fromOrderValue
    List<IwsCommission> findByFromOrderValueGreaterThanEqualOrderByFromOrderValueAsc(BigDecimal value);

    // New function: less than or equal to fromOrderValue
    List<IwsCommission> findByFromOrderValueLessThanEqualOrderByFromOrderValueAsc(BigDecimal value);
}
