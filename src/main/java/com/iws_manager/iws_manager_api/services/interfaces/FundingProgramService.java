package com.iws_manager.iws_manager_api.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.FundingProgram;

public interface FundingProgramService {
    FundingProgram create(FundingProgram fundingProgram);
    Optional<FundingProgram> findById(Long id);
    List<FundingProgram> findAll();
    FundingProgram update(Long id, FundingProgram fundingProgramDetails);
    void delete(Long id);
}

