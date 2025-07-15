package com.iws_manager.iws_manager_api.services.interfaces;

import com.iws_manager.iws_manager_api.models.AbsenceType;

import java.util.List;
import java.util.Optional;

public interface AbsenceTypeService {
    AbsenceType create(AbsenceType absenceType);
    Optional<AbsenceType> findById(Long id);
    List<AbsenceType> findAll();
    AbsenceType update(Long id, AbsenceType absenceTypeDetails);
    void delete(Long id);
}
