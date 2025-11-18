package com.iws_manager.iws_manager_api.services.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.dtos.contractordercommission.*;
import com.iws_manager.iws_manager_api.mappers.ContractOrderCommissionMapper;
import com.iws_manager.iws_manager_api.models.ContractOrderCommission;
import com.iws_manager.iws_manager_api.repositories.ContractOrderCommissionRepository;
import com.iws_manager.iws_manager_api.services.interfaces.ContractOrderCommissionServiceV2;

import jakarta.persistence.EntityNotFoundException;

/**
 * Implementation of the {@link ContractOrderCommissionService} interface for managing ContractOrderCommission entities.
 * Provides CRUD operations and business logic for ContractOrderCommission management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class ContractOrderCommissionServiceV2Impl implements ContractOrderCommissionServiceV2 {

    private final ContractOrderCommissionRepository contractOrderCommissionRepository;
    private final ContractOrderCommissionMapper contractOrderCommissionMapper;
    
    /**
     * Constructs a new ContractOrderCommissionService with the required repository dependency.
     * 
     * @param contractOrderCommissionRepository the repository for ContractOrderCommission entity operations
     * @param contractOrderCommissionMapper the mapper for converting between entities and DTOs
     */
    @Autowired
    public ContractOrderCommissionServiceV2Impl(
            ContractOrderCommissionRepository contractOrderCommissionRepository,
            ContractOrderCommissionMapper contractOrderCommissionMapper) {
        this.contractOrderCommissionRepository = contractOrderCommissionRepository;
        this.contractOrderCommissionMapper = contractOrderCommissionMapper;
    }

    /**
     * Creates and persists a new ContractOrderCommission entity from DTO.
     * 
     * @param dto the ContractOrderCommission DTO to be created
     * @return the persisted ContractOrderCommission as DTO
     * @throws IllegalArgumentException if the DTO parameter is null
     */
    @Override
    public ContractOrderCommissionDTO create(ContractOrderCommissionInputDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("ContractOrderCommission DTO cannot be null");
        }
        
        ContractOrderCommission entity = contractOrderCommissionMapper.toEntity(dto);
        ContractOrderCommission savedEntity = contractOrderCommissionRepository.save(entity);
        return contractOrderCommissionMapper.toDTO(savedEntity);
    }

    /**
     * Retrieves a ContractOrderCommission by its unique identifier.
     * 
     * @param id the ID of the ContractOrderCommission to retrieve
     * @return an Optional containing the found ContractOrderCommission as DetailDTO, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ContractOrderCommissionDetailDTO> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        
        return contractOrderCommissionRepository.findById(id)
                .map(contractOrderCommissionMapper::toDetailDTO);
    }

    /**
     * Retrieves all ContractOrderCommission entities from the database.
     * 
     * @return a List of all ContractOrderCommission entities as DTOs (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommissionDTO> findAll() {
        List<ContractOrderCommission> entities = contractOrderCommissionRepository.findAll();
        return contractOrderCommissionMapper.toDTOList(entities);
    }

    /**
     * Updates an existing ContractOrderCommission entity from DTO.
     * 
     * @param id the ID of the ContractOrderCommission to update
     * @param dto the ContractOrderCommission DTO containing updated fields
     * @return the updated ContractOrderCommission as DTO
     * @throws RuntimeException if no ContractOrderCommission exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public ContractOrderCommissionDTO update(Long id, ContractOrderCommissionInputDTO dto) {
        if (id == null || dto == null) {
            throw new IllegalArgumentException("ID and contract order commission DTO cannot be null");
        }
        
        return contractOrderCommissionRepository.findById(id)
                .map(existingCommission -> {
                    contractOrderCommissionMapper.updateEntityFromDTO(dto, existingCommission);
                    ContractOrderCommission updatedEntity = contractOrderCommissionRepository.save(existingCommission);
                    return contractOrderCommissionMapper.toDTO(updatedEntity);
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

        if (!contractOrderCommissionRepository.existsById(id)) {
            throw new EntityNotFoundException("ContractOrderCommission not found with id: " + id);
        }

        contractOrderCommissionRepository.deleteById(id);
    }

    // PROPERTIES - TODOS ACTUALIZADOS PARA DEVOLVER DTOs
    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommissionDTO> getByCommission(BigDecimal commission) {
        if (commission == null) {
            throw new IllegalArgumentException("Commission cannot be null");
        }
        List<ContractOrderCommission> entities = contractOrderCommissionRepository.findByCommission(commission);
        return contractOrderCommissionMapper.toDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommissionDTO> getByFromOrderValue(BigDecimal fromOrderValue) {
        if (fromOrderValue == null) {
            throw new IllegalArgumentException("FromOrderValue cannot be null");
        }
        List<ContractOrderCommission> entities = contractOrderCommissionRepository.findByFromOrderValue(fromOrderValue);
        return contractOrderCommissionMapper.toDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommissionDTO> getByMinCommission(BigDecimal minCommission) {
        if (minCommission == null) {
            throw new IllegalArgumentException("MinCommission cannot be null");
        }
        List<ContractOrderCommission> entities = contractOrderCommissionRepository.findByMinCommission(minCommission);
        return contractOrderCommissionMapper.toDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommissionDTO> getByBasicContractId(Long basicContractId) {
        if (basicContractId == null) {
            throw new IllegalArgumentException("BasicContractId cannot be null");
        }
        List<ContractOrderCommission> entities = contractOrderCommissionRepository.findByBasicContractId(basicContractId);
        return contractOrderCommissionMapper.toDTOList(entities);
    }

    // Commission - Greater than or equal/Less than or equal
    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommissionDTO> getByCommissionGreaterThanEqual(BigDecimal commission) {
        if (commission == null) {
            throw new IllegalArgumentException("Commission cannot be null");
        }
        List<ContractOrderCommission> entities = contractOrderCommissionRepository.findByCommissionGreaterThanEqual(commission);
        return contractOrderCommissionMapper.toDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommissionDTO> getByCommissionLessThanEqual(BigDecimal commission) {
        if (commission == null) {
            throw new IllegalArgumentException("Commission cannot be null");
        }
        List<ContractOrderCommission> entities = contractOrderCommissionRepository.findByCommissionLessThanEqual(commission);
        return contractOrderCommissionMapper.toDTOList(entities);
    }

    // FromOrderValue - Greater than or equal/Less than or equal
    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommissionDTO> getByFromOrderValueGreaterThanEqual(BigDecimal fromOrderValue) {
        if (fromOrderValue == null) {
            throw new IllegalArgumentException("FromOrderValue cannot be null");
        }
        List<ContractOrderCommission> entities = contractOrderCommissionRepository.findByFromOrderValueGreaterThanEqual(fromOrderValue);
        return contractOrderCommissionMapper.toDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommissionDTO> getByFromOrderValueLessThanEqual(BigDecimal fromOrderValue) {
        if (fromOrderValue == null) {
            throw new IllegalArgumentException("FromOrderValue cannot be null");
        }
        List<ContractOrderCommission> entities = contractOrderCommissionRepository.findByFromOrderValueLessThanEqual(fromOrderValue);
        return contractOrderCommissionMapper.toDTOList(entities);
    }

    // MinCommission - Greater than or equal/Less than or equal
    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommissionDTO> getByMinCommissionGreaterThanEqual(BigDecimal minCommission) {
        if (minCommission == null) {
            throw new IllegalArgumentException("MinCommission cannot be null");
        }
        List<ContractOrderCommission> entities = contractOrderCommissionRepository.findByMinCommissionGreaterThanEqual(minCommission);
        return contractOrderCommissionMapper.toDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommissionDTO> getByMinCommissionLessThanEqual(BigDecimal minCommission) {
        if (minCommission == null) {
            throw new IllegalArgumentException("MinCommission cannot be null");
        }
        List<ContractOrderCommission> entities = contractOrderCommissionRepository.findByMinCommissionLessThanEqual(minCommission);
        return contractOrderCommissionMapper.toDTOList(entities);
    }

    // Commission by basicContract - Greater than or equal/Less than or equal
    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommissionDTO> getByBasicContractIdAndCommissionGreaterThanEqual(
            Long basicContractId, BigDecimal commission) {
        if (basicContractId == null || commission == null) {
            throw new IllegalArgumentException("BasicContractId and Commission cannot be null");
        }
        List<ContractOrderCommission> entities = contractOrderCommissionRepository.findByBasicContractIdAndCommissionGreaterThanEqual(
                basicContractId, commission);
        return contractOrderCommissionMapper.toDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommissionDTO> getByBasicContractIdAndCommissionLessThanEqual(
            Long basicContractId, BigDecimal commission) {
        if (basicContractId == null || commission == null) {
            throw new IllegalArgumentException("BasicContractId and Commission cannot be null");
        }
        List<ContractOrderCommission> entities = contractOrderCommissionRepository.findByBasicContractIdAndCommissionLessThanEqual(
                basicContractId, commission);
        return contractOrderCommissionMapper.toDTOList(entities);
    }

    // FromOrderValue by basicContract - Greater than or equal/Less than or equal
    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommissionDTO> getByBasicContractIdAndFromOrderValueGreaterThanEqual(
            Long basicContractId, BigDecimal fromOrderValue) {
        if (basicContractId == null || fromOrderValue == null) {
            throw new IllegalArgumentException("BasicContractId and FromOrderValue cannot be null");
        }
        List<ContractOrderCommission> entities = contractOrderCommissionRepository.findByBasicContractIdAndFromOrderValueGreaterThanEqual(
                basicContractId, fromOrderValue);
        return contractOrderCommissionMapper.toDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommissionDTO> getByBasicContractIdAndFromOrderValueLessThanEqual(
            Long basicContractId, BigDecimal fromOrderValue) {
        if (basicContractId == null || fromOrderValue == null) {
            throw new IllegalArgumentException("BasicContractId and FromOrderValue cannot be null");
        }
        List<ContractOrderCommission> entities = contractOrderCommissionRepository.findByBasicContractIdAndFromOrderValueLessThanEqual(
                basicContractId, fromOrderValue);
        return contractOrderCommissionMapper.toDTOList(entities);
    }

    // MinCommission by basicContract - Greater than or equal/Less than or equal
    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommissionDTO> getByBasicContractIdAndMinCommissionGreaterThanEqual(
            Long basicContractId, BigDecimal minCommission) {
        if (basicContractId == null || minCommission == null) {
            throw new IllegalArgumentException("BasicContractId and MinCommission cannot be null");
        }
        List<ContractOrderCommission> entities = contractOrderCommissionRepository.findByBasicContractIdAndMinCommissionGreaterThanEqual(
                basicContractId, minCommission);
        return contractOrderCommissionMapper.toDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommissionDTO> getByBasicContractIdAndMinCommissionLessThanEqual(
            Long basicContractId, BigDecimal minCommission) {
        if (basicContractId == null || minCommission == null) {
            throw new IllegalArgumentException("BasicContractId and MinCommission cannot be null");
        }
        List<ContractOrderCommission> entities = contractOrderCommissionRepository.findByBasicContractIdAndMinCommissionLessThanEqual(
                basicContractId, minCommission);
        return contractOrderCommissionMapper.toDTOList(entities);
    }

    // Additional useful methods with ranges
    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommissionDTO> getByCommissionBetween(BigDecimal minCommission, BigDecimal maxCommission) {
        if (minCommission == null || maxCommission == null) {
            throw new IllegalArgumentException("MinCommission and MaxCommission cannot be null");
        }
        if (minCommission.compareTo(maxCommission) > 0) {
            throw new IllegalArgumentException("MinCommission must be less than or equal to MaxCommission");
        }
        List<ContractOrderCommission> entities = contractOrderCommissionRepository.findByCommissionBetween(minCommission, maxCommission);
        return contractOrderCommissionMapper.toDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommissionDTO> getByFromOrderValueBetween(BigDecimal minFromOrderValue, BigDecimal maxFromOrderValue) {
        if (minFromOrderValue == null || maxFromOrderValue == null) {
            throw new IllegalArgumentException("MinFromOrderValue and MaxFromOrderValue cannot be null");
        }
        if (minFromOrderValue.compareTo(maxFromOrderValue) > 0) {
            throw new IllegalArgumentException("MinFromOrderValue must be less than or equal to MaxFromOrderValue");
        }
        List<ContractOrderCommission> entities = contractOrderCommissionRepository.findByFromOrderValueBetween(minFromOrderValue, maxFromOrderValue);
        return contractOrderCommissionMapper.toDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommissionDTO> getByMinCommissionBetween(BigDecimal minMinCommission, BigDecimal maxMinCommission) {
        if (minMinCommission == null || maxMinCommission == null) {
            throw new IllegalArgumentException("MinMinCommission and MaxMinCommission cannot be null");
        }
        if (minMinCommission.compareTo(maxMinCommission) > 0) {
            throw new IllegalArgumentException("MinMinCommission must be less than or equal to MaxMinCommission");
        }
        List<ContractOrderCommission> entities = contractOrderCommissionRepository.findByMinCommissionBetween(minMinCommission, maxMinCommission);
        return contractOrderCommissionMapper.toDTOList(entities);
    }

    // Methods with basicContract and ranges
    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommissionDTO> getByBasicContractIdAndCommissionBetween(
            Long basicContractId, BigDecimal minCommission, BigDecimal maxCommission) {
        if (basicContractId == null || minCommission == null || maxCommission == null) {
            throw new IllegalArgumentException("BasicContractId, MinCommission and MaxCommission cannot be null");
        }
        if (minCommission.compareTo(maxCommission) > 0) {
            throw new IllegalArgumentException("MinCommission must be less than or equal to MaxCommission");
        }
        List<ContractOrderCommission> entities = contractOrderCommissionRepository.findByBasicContractIdAndCommissionBetween(
                basicContractId, minCommission, maxCommission);
        return contractOrderCommissionMapper.toDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommissionDTO> getByBasicContractIdAndFromOrderValueBetween(
            Long basicContractId, BigDecimal minFromOrderValue, BigDecimal maxFromOrderValue) {
        if (basicContractId == null || minFromOrderValue == null || maxFromOrderValue == null) {
            throw new IllegalArgumentException("BasicContractId, MinFromOrderValue and MaxFromOrderValue cannot be null");
        }
        if (minFromOrderValue.compareTo(maxFromOrderValue) > 0) {
            throw new IllegalArgumentException("MinFromOrderValue must be less than or equal to MaxFromOrderValue");
        }
        List<ContractOrderCommission> entities = contractOrderCommissionRepository.findByBasicContractIdAndFromOrderValueBetween(
                basicContractId, minFromOrderValue, maxFromOrderValue);
        return contractOrderCommissionMapper.toDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommissionDTO> getByBasicContractIdAndMinCommissionBetween(
            Long basicContractId, BigDecimal minMinCommission, BigDecimal maxMinCommission) {
        if (basicContractId == null || minMinCommission == null || maxMinCommission == null) {
            throw new IllegalArgumentException("BasicContractId, MinMinCommission and MaxMinCommission cannot be null");
        }
        if (minMinCommission.compareTo(maxMinCommission) > 0) {
            throw new IllegalArgumentException("MinMinCommission must be less than or equal to MaxMinCommission");
        }
        List<ContractOrderCommission> entities = contractOrderCommissionRepository.findByBasicContractIdAndMinCommissionBetween(
                basicContractId, minMinCommission, maxMinCommission);
        return contractOrderCommissionMapper.toDTOList(entities);
    }

    // SORTING
    @Override
    @Transactional(readOnly = true)
    public List<ContractOrderCommissionDTO> getByBasicContractIdOrderByFromOrderValueAsc(Long basicContractId) {
        if (basicContractId == null) {
            throw new IllegalArgumentException("BasicContractId cannot be null");
        }
        List<ContractOrderCommission> entities = contractOrderCommissionRepository.findByBasicContractIdOrderByFromOrderValueAsc(basicContractId);
        return contractOrderCommissionMapper.toDTOList(entities);
    }
}