package com.iws_manager.iws_manager_api.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.Branch;

public interface BranchService {
    Branch create(Branch branch);
    Optional<Branch> findById(Long id);
    List<Branch> findAll();
    Branch update(Long id, Branch branchDetails);
    void delete(Long id);
}
