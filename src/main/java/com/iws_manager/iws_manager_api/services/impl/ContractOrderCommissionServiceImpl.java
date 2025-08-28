package com.iws_manager.iws_manager_api.services.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.ContractOrderCommission;
import com.iws_manager.iws_manager_api.repositories.ContractOrderCommissionRepository;
import com.iws_manager.iws_manager_api.services.interfaces.ContractOrderCommissionService;

/**
 * Implementation of the {@link ContractOrderCommissionService} interface for managing ContractOrderCommission entities.
 * Provides CRUD operations and business logic for ContractOrderCommission management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class ContractOrderCommissionServiceImpl implements ContractOrderCommissionService {

    private final ContractOrderCommissionRepository contractOrderCommissionRepository;
    
    /**
     * Constructs a new ContractOrderCommissionService with the required repository dependency.
     * 
     * @param contractOrderCommissionRepository the repository for ContractOrderCommission entity operations
     */
    @Autowired
    public ContractOrderCommissionServiceImpl(ContractOrderCommissionRepository contractOrderCommissionRepository) {
        this.contractOrderCommissionRepository = contractOrderCommissionRepository;
    }

    /**
     * Creates and persists a new ContractOrderCommission entity.
     * 
     * @param contractOrderCommission the ContractOrderCommission entity to be created
     * @return the persisted ContractOrderCommission entity with generated ID
     * @throws IllegalArgumentException if the ContractOrderCommission parameter is null
     */
    @Override
    public ContractOrderCommission create(ContractOrderCommission contractOrderCommission) {
        if (contractOrderCommission == null) {
            throw new IllegalArgumentException("ContractOrderCommission cannot be null");
        }
        return contractOrderCommissionRepository.save(contractOrderCommission);
    }

    /**
     * Retrieves a ContractOrderCommission by its unique identifier.
     * 
     * @param id the ID of the ContractOrderCommission to retrieve
     * @return an Optional containing the found ContractOrderCommission, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ContractOrderCommission> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return contractOrderCommissionRepository.findById(id);
    }

    /**
     * Retrieves all ContractOrderCommission entities from the database.
     * 
     * @return a List of all ContractOrderCommission entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommission> findAll() {
        return contractOrderCommissionRepository.findAll();
    }

    /**
     * Updates an existing ContractOrderCommission entity.
     * 
     * @param id the ID of the ContractOrderCommission to update
     * @param contractOrderCommissionDetails the ContractOrderCommission object containing updated fields
     * @return the updated ContractOrderCommission entity
     * @throws RuntimeException if no ContractOrderCommission exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public ContractOrderCommission update(Long id, ContractOrderCommission contractOrderCommissionDetails) {
        if (id == null || contractOrderCommissionDetails == null) {
            throw new IllegalArgumentException("ID and contract order commission details cannot be null");
        }
        
        return contractOrderCommissionRepository.findById(id)
                .map(existingCommission -> {
                    existingCommission.setCommission(contractOrderCommissionDetails.getCommission());
                    existingCommission.setEmploymentContract(contractOrderCommissionDetails.getEmploymentContract());
                    existingCommission.setFromOrderValue(contractOrderCommissionDetails.getFromOrderValue());
                    existingCommission.setMinCommission(contractOrderCommissionDetails.getMinCommission());
                    
                    return contractOrderCommissionRepository.save(existingCommission);
                })
                .orElseThrow(() -> new RuntimeException("ContractOrderCommission not found with id: " + id));
    }

    /**
     * Deletes a ContractOrderCommission entity by its ID.
     * 
     * @param id the ID of the ContractOrderCommission to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        contractOrderCommissionRepository.deleteById(id);
    }

    // PROPERTIES
    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommission> getByCommission(BigDecimal commission) {
        if (commission == null) {
            throw new IllegalArgumentException("Commission cannot be null");
        }
        return contractOrderCommissionRepository.findByCommission(commission);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommission> getByFromOrderValue(BigDecimal fromOrderValue) {
        if (fromOrderValue == null) {
            throw new IllegalArgumentException("FromOrderValue cannot be null");
        }
        return contractOrderCommissionRepository.findByFromOrderValue(fromOrderValue);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommission> getByMinCommission(BigDecimal minCommission) {
        if (minCommission == null) {
            throw new IllegalArgumentException("MinCommission cannot be null");
        }
        return contractOrderCommissionRepository.findByMinCommission(minCommission);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommission> getByEmploymentContractId(Long employmentContractId) {
        if (employmentContractId == null) {
            throw new IllegalArgumentException("EmploymentContractId cannot be null");
        }
        return contractOrderCommissionRepository.findByEmploymentContractId(employmentContractId);
    }

    // Commission - Greater than or equal/Less than or equal
    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommission> getByCommissionGreaterThanEqual(BigDecimal commission) {
        if (commission == null) {
            throw new IllegalArgumentException("Commission cannot be null");
        }
        return contractOrderCommissionRepository.findByCommissionGreaterThanEqual(commission);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommission> getByCommissionLessThanEqual(BigDecimal commission) {
        if (commission == null) {
            throw new IllegalArgumentException("Commission cannot be null");
        }
        return contractOrderCommissionRepository.findByCommissionLessThanEqual(commission);
    }

    // FromOrderValue - Greater than or equal/Less than or equal
    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommission> getByFromOrderValueGreaterThanEqual(BigDecimal fromOrderValue) {
        if (fromOrderValue == null) {
            throw new IllegalArgumentException("FromOrderValue cannot be null");
        }
        return contractOrderCommissionRepository.findByFromOrderValueGreaterThanEqual(fromOrderValue);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommission> getByFromOrderValueLessThanEqual(BigDecimal fromOrderValue) {
        if (fromOrderValue == null) {
            throw new IllegalArgumentException("FromOrderValue cannot be null");
        }
        return contractOrderCommissionRepository.findByFromOrderValueLessThanEqual(fromOrderValue);
    }

    // MinCommission - Greater than or equal/Less than or equal
    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommission> getByMinCommissionGreaterThanEqual(BigDecimal minCommission) {
        if (minCommission == null) {
            throw new IllegalArgumentException("MinCommission cannot be null");
        }
        return contractOrderCommissionRepository.findByMinCommissionGreaterThanEqual(minCommission);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommission> getByMinCommissionLessThanEqual(BigDecimal minCommission) {
        if (minCommission == null) {
            throw new IllegalArgumentException("MinCommission cannot be null");
        }
        return contractOrderCommissionRepository.findByMinCommissionLessThanEqual(minCommission);
    }

    // Commission by employmentContract - Greater than or equal/Less than or equal
    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommission> getByEmploymentContractIdAndCommissionGreaterThanEqual(
            Long employmentContractId, BigDecimal commission) {
        if (employmentContractId == null || commission == null) {
            throw new IllegalArgumentException("EmploymentContractId and Commission cannot be null");
        }
        return contractOrderCommissionRepository.findByEmploymentContractIdAndCommissionGreaterThanEqual(
                employmentContractId, commission);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommission> getByEmploymentContractIdAndCommissionLessThanEqual(
            Long employmentContractId, BigDecimal commission) {
        if (employmentContractId == null || commission == null) {
            throw new IllegalArgumentException("EmploymentContractId and Commission cannot be null");
        }
        return contractOrderCommissionRepository.findByEmploymentContractIdAndCommissionLessThanEqual(
                employmentContractId, commission);
    }

    // FromOrderValue by employmentContract - Greater than or equal/Less than or equal
    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommission> getByEmploymentContractIdAndFromOrderValueGreaterThanEqual(
            Long employmentContractId, BigDecimal fromOrderValue) {
        if (employmentContractId == null || fromOrderValue == null) {
            throw new IllegalArgumentException("EmploymentContractId and FromOrderValue cannot be null");
        }
        return contractOrderCommissionRepository.findByEmploymentContractIdAndFromOrderValueGreaterThanEqual(
                employmentContractId, fromOrderValue);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommission> getByEmploymentContractIdAndFromOrderValueLessThanEqual(
            Long employmentContractId, BigDecimal fromOrderValue) {
        if (employmentContractId == null || fromOrderValue == null) {
            throw new IllegalArgumentException("EmploymentContractId and FromOrderValue cannot be null");
        }
        return contractOrderCommissionRepository.findByEmploymentContractIdAndFromOrderValueLessThanEqual(
                employmentContractId, fromOrderValue);
    }

    // MinCommission by employmentContract - Greater than or equal/Less than or equal
    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommission> getByEmploymentContractIdAndMinCommissionGreaterThanEqual(
            Long employmentContractId, BigDecimal minCommission) {
        if (employmentContractId == null || minCommission == null) {
            throw new IllegalArgumentException("EmploymentContractId and MinCommission cannot be null");
        }
        return contractOrderCommissionRepository.findByEmploymentContractIdAndMinCommissionGreaterThanEqual(
                employmentContractId, minCommission);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommission> getByEmploymentContractIdAndMinCommissionLessThanEqual(
            Long employmentContractId, BigDecimal minCommission) {
        if (employmentContractId == null || minCommission == null) {
            throw new IllegalArgumentException("EmploymentContractId and MinCommission cannot be null");
        }
        return contractOrderCommissionRepository.findByEmploymentContractIdAndMinCommissionLessThanEqual(
                employmentContractId, minCommission);
    }

    // Additional useful methods with ranges
    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommission> getByCommissionBetween(BigDecimal minCommission, BigDecimal maxCommission) {
        if (minCommission == null || maxCommission == null) {
            throw new IllegalArgumentException("MinCommission and MaxCommission cannot be null");
        }
        if (minCommission.compareTo(maxCommission) > 0) {
            throw new IllegalArgumentException("MinCommission must be less than or equal to MaxCommission");
        }
        return contractOrderCommissionRepository.findByCommissionBetween(minCommission, maxCommission);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommission> getByFromOrderValueBetween(BigDecimal minFromOrderValue, BigDecimal maxFromOrderValue) {
        if (minFromOrderValue == null || maxFromOrderValue == null) {
            throw new IllegalArgumentException("MinFromOrderValue and MaxFromOrderValue cannot be null");
        }
        if (minFromOrderValue.compareTo(maxFromOrderValue) > 0) {
            throw new IllegalArgumentException("MinFromOrderValue must be less than or equal to MaxFromOrderValue");
        }
        return contractOrderCommissionRepository.findByFromOrderValueBetween(minFromOrderValue, maxFromOrderValue);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommission> getByMinCommissionBetween(BigDecimal minMinCommission, BigDecimal maxMinCommission) {
        if (minMinCommission == null || maxMinCommission == null) {
            throw new IllegalArgumentException("MinMinCommission and MaxMinCommission cannot be null");
        }
        if (minMinCommission.compareTo(maxMinCommission) > 0) {
            throw new IllegalArgumentException("MinMinCommission must be less than or equal to MaxMinCommission");
        }
        return contractOrderCommissionRepository.findByMinCommissionBetween(minMinCommission, maxMinCommission);
    }

    // Methods with employmentContract and ranges
    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommission> getByEmploymentContractIdAndCommissionBetween(
            Long employmentContractId, BigDecimal minCommission, BigDecimal maxCommission) {
        if (employmentContractId == null || minCommission == null || maxCommission == null) {
            throw new IllegalArgumentException("EmploymentContractId, MinCommission and MaxCommission cannot be null");
        }
        if (minCommission.compareTo(maxCommission) > 0) {
            throw new IllegalArgumentException("MinCommission must be less than or equal to MaxCommission");
        }
        return contractOrderCommissionRepository.findByEmploymentContractIdAndCommissionBetween(
                employmentContractId, minCommission, maxCommission);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommission> getByEmploymentContractIdAndFromOrderValueBetween(
            Long employmentContractId, BigDecimal minFromOrderValue, BigDecimal maxFromOrderValue) {
        if (employmentContractId == null || minFromOrderValue == null || maxFromOrderValue == null) {
            throw new IllegalArgumentException("EmploymentContractId, MinFromOrderValue and MaxFromOrderValue cannot be null");
        }
        if (minFromOrderValue.compareTo(maxFromOrderValue) > 0) {
            throw new IllegalArgumentException("MinFromOrderValue must be less than or equal to MaxFromOrderValue");
        }
        return contractOrderCommissionRepository.findByEmploymentContractIdAndFromOrderValueBetween(
                employmentContractId, minFromOrderValue, maxFromOrderValue);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommission> getByEmploymentContractIdAndMinCommissionBetween(
            Long employmentContractId, BigDecimal minMinCommission, BigDecimal maxMinCommission) {
        if (employmentContractId == null || minMinCommission == null || maxMinCommission == null) {
            throw new IllegalArgumentException("EmploymentContractId, MinMinCommission and MaxMinCommission cannot be null");
        }
        if (minMinCommission.compareTo(maxMinCommission) > 0) {
            throw new IllegalArgumentException("MinMinCommission must be less than or equal to MaxMinCommission");
        }
        return contractOrderCommissionRepository.findByEmploymentContractIdAndMinCommissionBetween(
                employmentContractId, minMinCommission, maxMinCommission);
    }
}