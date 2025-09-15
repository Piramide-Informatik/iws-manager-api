package com.iws_manager.iws_manager_api.repositories;

import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.Chance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChanceRepository extends JpaRepository<Chance, Long> {
    
    List<Chance> findAllByOrderByProbabilityAsc();
}