package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.VatRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;

@Repository
public interface VatRateRepository extends JpaRepository<VatRate, Long> {

    @EntityGraph(attributePaths = {"vat"})
    List<VatRate> findAllByOrderByFromdateAsc();

    @EntityGraph(attributePaths = {"vat"})
    List<VatRate> findByVatId(Long vatId);

    @EntityGraph(attributePaths = {"vat"})
    List<VatRate> findByVatIdOrderByFromdateAsc(Long vatId);
}