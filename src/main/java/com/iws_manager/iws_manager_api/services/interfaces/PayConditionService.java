package com.iws_manager.iws_manager_api.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.PayCondition;

public interface PayConditionService {
    PayCondition create(PayCondition payCondition);
    Optional<PayCondition> findById(Long id);
    List<PayCondition> findAll();
    PayCondition update(Long id, PayCondition payConditionDetails);
    void delete(Long id);
    
    List<PayCondition> getByName(String name);
    List<PayCondition> getByDeadline(Integer deadline);
}
