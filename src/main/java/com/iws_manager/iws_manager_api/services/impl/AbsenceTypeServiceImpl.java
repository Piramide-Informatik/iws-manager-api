package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.AbsenceType;
import com.iws_manager.iws_manager_api.repositories.AbsenceTypeRepository;
import com.iws_manager.iws_manager_api.services.interfaces.AbsenceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;
import com.iws_manager.iws_manager_api.exception.exceptions.DuplicateResourceException;

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

        validateUniqueConstraintsForCreation(absenceType);

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
                    validateUniqueConstraintsForUpdate(existingAbsenseType, absenceTypeDetails, id);

                    existingAbsenseType.setName(absenceTypeDetails.getName());
                    existingAbsenseType.setHours(absenceTypeDetails.getHours());
                    existingAbsenseType.setLabel(absenceTypeDetails.getLabel());
                    existingAbsenseType.setIsHoliday(absenceTypeDetails.getIsHoliday());
                    existingAbsenseType.setShareOfDay(absenceTypeDetails.getShareOfDay());

                    return absenceTypeRepository.save(existingAbsenseType);
                }).orElseThrow(()-> new EntityNotFoundException("AbsenseType not found with id: "+ id));
    }

    @Override
    public void delete(Long id) {
        if(id == null){
            throw new IllegalArgumentException("ID cannot be null");
        }

        if(!absenceTypeRepository.existsById(id)){
            throw new EntityNotFoundException("AbsenseType not found with id: "+ id);
        }
        absenceTypeRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AbsenceType> getAllByOrderByLabelAsc() {
        return absenceTypeRepository.findAllByOrderByLabelAsc();
    }

    private void validateUniqueConstraintsForCreation(AbsenceType absenceType) {
        boolean nameExists = absenceTypeRepository.existsByName(absenceType.getName());
        boolean labelExists = absenceTypeRepository.existsByLabel(absenceType.getLabel());
        
        if (nameExists || labelExists) {
            buildAndThrowDuplicateException(nameExists, labelExists, absenceType.getName(), absenceType.getLabel());
        }
    }

    private void validateUniqueConstraintsForUpdate(
        AbsenceType existingAbsenceType, 
        AbsenceType newAbsenceType, 
        Long id
    ) {
        boolean nameChanged = !existingAbsenceType.getName().equals(newAbsenceType.getName());
        boolean labelChanged = !existingAbsenceType.getLabel().equals(newAbsenceType.getLabel());
        
        if (nameChanged || labelChanged) {
            boolean nameExists = nameChanged && absenceTypeRepository.existsByNameAndIdNot(newAbsenceType.getName(), id);
            boolean labelExists = labelChanged && absenceTypeRepository.existsByLabelAndIdNot(newAbsenceType.getLabel(), id);
            
            if (nameExists || labelExists) {
                buildAndThrowDuplicateException(nameExists, labelExists, newAbsenceType.getName(), newAbsenceType.getLabel());
            }
        }
    }

    private void buildAndThrowDuplicateException(boolean nameExists, boolean labelExists, String name, String label) {
        if (nameExists && labelExists) {
            throw new DuplicateResourceException(
                "Absence type duplication with attributes 'name' = '" + name + "' and 'label' = '" + label + "'"
            );
        } else if (nameExists) {
            throw new DuplicateResourceException(
                "Absence type duplication with attribute 'name' = '" + name + "'"
            );
        } else if (labelExists) {
            throw new DuplicateResourceException(
                "Absence type duplication with attribute 'label' = '" + label + "'"
            );
        }
    }
}
