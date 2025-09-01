package com.iws_manager.iws_manager_api.repositories;

import java.util.List;

import com.iws_manager.iws_manager_api.models.FundingProgram;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundingProgramRepository extends JpaRepository<FundingProgram, Long> {
    List<FundingProgram> findAllByOrderByNameAsc();
}

