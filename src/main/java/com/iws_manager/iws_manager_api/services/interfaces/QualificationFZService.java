package com.iws_manager.iws_manager_api.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.QualificationFZ;

public interface QualificationFZService {
    QualificationFZ create(QualificationFZ qualificationFZ);
    Optional<QualificationFZ> findById(Long id);
    List<QualificationFZ> findAll();
    QualificationFZ update(Long id, QualificationFZ qualificationFZDetails);
    void delete(Long id);
}
