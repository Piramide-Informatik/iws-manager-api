package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.AbsenceType;
import com.iws_manager.iws_manager_api.repositories.AbsenceTypeRepository;
import com.iws_manager.iws_manager_api.services.interfaces.AbsenceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AbsenceTypeServiceImpl implements AbsenceTypeService {
    private final AbsenceTypeRepository absenceTypeRepository;

    @Autowired
    public AbsenceTypeServiceImpl(AbsenceTypeRepository absenceTypeRepository) {
        this.absenceTypeRepository = absenceTypeRepository;
    }

    @Override
    public AbsenceType create(AbsenceType absenceType) {
        if(absenceType == null){
            throw new IllegalArgumentException("AbsenceType cannot be null");
        }
        return absenceTypeRepository.save(absenceType);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AbsenceType> findById(Long id) {
        if(id == null){
            throw new IllegalArgumentException("ID cannot be null");
        }
        return absenceTypeRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AbsenceType> findAll() {
        return absenceTypeRepository.findAllByOrderByNameAsc();
    }

    @Override
    public AbsenceType update(Long id, AbsenceType absenceTypeDetails) {
        if(id == null || absenceTypeDetails == null){
            throw new IllegalArgumentException("ID and Details cannot be null");
        }
        return absenceTypeRepository.findById(id)
                .map(existingAbsenseType -> {
                    existingAbsenseType.setName(absenceTypeDetails.getName());
                    existingAbsenseType.setHours(absenceTypeDetails.getHours());
                    existingAbsenseType.setLabel(absenceTypeDetails.getLabel());
                    existingAbsenseType.setIsHoliday(absenceTypeDetails.getIsHoliday());
                    existingAbsenseType.setShareOfDay(absenceTypeDetails.getShareOfDay());

                    return absenceTypeRepository.save(existingAbsenseType);
                }).orElseThrow(()-> new RuntimeException("AbsenseType not found with id: "+ id));
    }

    @Override
    public void delete(Long id) {
        if(id == null){
            throw new IllegalArgumentException("ID cannot be null");
        }
        absenceTypeRepository.deleteById(id);
    }
}
