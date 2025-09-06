package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.IwsCommission;
import com.iws_manager.iws_manager_api.repositories.IwsCommissionRepository;
import com.iws_manager.iws_manager_api.services.interfaces.IwsCommissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class IwsCommissionServiceImpl implements IwsCommissionService {
    private final IwsCommissionRepository iwsCommissionRepository;

    @Autowired
    public IwsCommissionServiceImpl(IwsCommissionRepository iwsCommissionRepository) {
        this.iwsCommissionRepository = iwsCommissionRepository;
    }

    @Override
    public IwsCommission create(IwsCommission iwsCommission) {
        if (iwsCommission == null) {
            throw new IllegalArgumentException("IwsCommission cannot be null");
        }
        return iwsCommissionRepository.save(iwsCommission);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IwsCommission> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return iwsCommissionRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IwsCommission> findAll() {
        return iwsCommissionRepository.findAll();
    }

    @Override
    public IwsCommission update(Long id, IwsCommission iwsCommissionDetails) {
        if (id == null || iwsCommissionDetails == null) {
            throw new IllegalArgumentException("ID and IwsCommission details cannot be null");
        }
        return iwsCommissionRepository.findById(id)
                .map(existingIwsCommission -> {
                    existingIwsCommission.setCommission(iwsCommissionDetails.getCommission());
                    existingIwsCommission.setFromOrderValue(iwsCommissionDetails.getFromOrderValue());
                    existingIwsCommission.setInvoiceText(iwsCommissionDetails.getInvoiceText());
                    existingIwsCommission.setMinCommission(iwsCommissionDetails.getMinCommission());
                    existingIwsCommission.setPayDeadline(iwsCommissionDetails.getPayDeadline());
                    return iwsCommissionRepository.save(existingIwsCommission);
                }).orElseThrow(() -> new RuntimeException("IwsCommission not found with id: " + id)) ;
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        iwsCommissionRepository.deleteById(id);
    }
}
