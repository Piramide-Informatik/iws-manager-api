package com.iws_manager.iws_manager_api.services.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.SubcontractYear;

public interface SubcontractYearService {

    SubcontractYear create(SubcontractYear subcontractYear);
    Optional<SubcontractYear> findById(Long id);
    List<SubcontractYear> findAll();
    SubcontractYear update(Long id, SubcontractYear subcontractYear);
    void delete(Long id);

    List<SubcontractYear> findByMonths(Integer months);
    List<SubcontractYear> findBySubcontractId(Long subcontractId);
    List<SubcontractYear> findByYear(LocalDate year);
}
