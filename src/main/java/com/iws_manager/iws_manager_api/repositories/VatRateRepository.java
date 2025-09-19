package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.VatRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VatRateRepository extends JpaRepository<VatRate, Long> {
    
    List<VatRate> findAllByOrderByFromdateAsc();
    
    List<VatRate> findByVatId(Long vatId);
    
    List<VatRate> findByVatIdOrderByFromdateAsc(Long vatId);
}