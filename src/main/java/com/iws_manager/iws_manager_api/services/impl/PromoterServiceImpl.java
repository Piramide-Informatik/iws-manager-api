package com.iws_manager.iws_manager_api.services.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.exception.exceptions.DuplicateResourceException;
import com.iws_manager.iws_manager_api.models.Promoter;
import com.iws_manager.iws_manager_api.repositories.PromoterRepository;
import com.iws_manager.iws_manager_api.services.interfaces.PromoterService;

/**
 * Implementation of the {@link PromoterService} interface for managing Branch entities.
 * Provides CRUD operations and business logic for Promoter management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class PromoterServiceImpl implements PromoterService {

    private final PromoterRepository promoterRepository;
    
    /**
     * Constructs a new PromoterService with the required repository dependency.
     * 
     * @param promoterRepository the repository for Promoter entity operations
     */
    @Autowired
    public PromoterServiceImpl(PromoterRepository promoterRepository) {
        this.promoterRepository = promoterRepository;
    }


    /**
     * Creates and persists a new Promoter entity.
     * 
     * @param promoter the Promoter entity to be created
     * @return the persisted Promoter entity with generated ID
     * @throws IllegalArgumentException if the Promoter parameter is null
     */
    @Override
    public Promoter create(Promoter promoter) {
        if (promoter == null) {
            throw new IllegalArgumentException("Promoter cannot be null");
        }

        validateUniqueConstraintsForCreation(promoter.getProjectPromoter(), promoter.getPromoterName1());

        return promoterRepository.save(promoter);
    }

    /**
     * Retrieves a Promoter by its unique identifier.
     * 
     * @param id the ID of the Promoter to retrieve
     * @return an Optional containing the found Promoter, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Promoter> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return promoterRepository.findById(id);
    }

    /**
     * Retrieves all Promoter entities from the database.
     * 
     * @return a List of all Promoter entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Promoter> findAll() {
        return promoterRepository.findAllByOrderByPromoterNoAsc();
    }

    /**
     * Updates an existing Promoter entity.
     * 
     * @param id the ID of the Promoter to update
     * @param branchDetails the Promoter object containing updated fields
     * @return the updated Promoter entity
     * @throws RuntimeException if no Promoter exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public Promoter update(Long id, Promoter promoterDetails) {
        if (id == null || promoterDetails == null) {
            throw new IllegalArgumentException("ID and promoter details cannot be null");
        }
        
        return  promoterRepository.findById(id)
                .map(existingPromoter -> {
                    validateUniqueConstraintsForUpdate(existingPromoter, promoterDetails, id);

                    existingPromoter.setCity(promoterDetails.getCity());
                    existingPromoter.setCountry(promoterDetails.getCountry());
                    existingPromoter.setProjectPromoter(promoterDetails.getProjectPromoter());
                    existingPromoter.setPromoterName1(promoterDetails.getPromoterName1());
                    existingPromoter.setPromoterName2(promoterDetails.getPromoterName2());
                    existingPromoter.setPromoterNo(promoterDetails.getPromoterNo());
                    existingPromoter.setStreet(promoterDetails.getStreet());
                    existingPromoter.setZipCode(promoterDetails.getZipCode());

                    return promoterRepository.save(existingPromoter);
                })
                .orElseThrow(() -> new EntityNotFoundException("Promoter not found with id: " + id));
    }

    /**
     * Deletes a Promoter entity by its ID.
     * 
     * @param id the ID of the Promoter to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (!promoterRepository.existsById(id)) {
            throw new EntityNotFoundException("promoter not found with id: " + id);
        }
        promoterRepository.deleteById(id);
    }

    @Override
    public List<Promoter> getByCity(String city) {
        return promoterRepository.findByCity(city);
    }

    @Override
    public List<Promoter> getByCountryId(Long countryId) {
        return promoterRepository.findByCountryId(countryId);
    }

    @Override
    public List<Promoter> getByProjectPromoter(String projectPromoter) {
        return promoterRepository.findByProjectPromoter(projectPromoter);
    }

    @Override
    public List<Promoter> getByPromoterName1(String promoterName1) {
        return promoterRepository.findByPromoterName1(promoterName1);
    }

    @Override
    public List<Promoter> getByPromoterName2(String promoterName2) {
        return promoterRepository.findByPromoterName2(promoterName2);
    }

    @Override
    public List<Promoter> getByPromoterNo(String promoterNo) {
        return promoterRepository.findByPromoterNo(promoterNo);
    }

    @Override
    public List<Promoter> getByStreet(String street) {
        return promoterRepository.findByStreet(street);
    }

    @Override
    public List<Promoter> getByZipCode(String zipCode) {
        return promoterRepository.findByZipCode(zipCode);
    }

    @Override
    public List<Promoter> getByPromoterName1OrPromoterName2(String name1, String name2) {
        return promoterRepository.findByPromoterName1OrPromoterName2(name1, name2);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Promoter> getAllByOrderByPromoterName1Asc() {
        return promoterRepository.findAllByOrderByPromoterName1Asc();
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getNextPromoterNo() {
        Integer maxPromoterNo = promoterRepository.findMaxPromoterNo();
        return (maxPromoterNo == null || maxPromoterNo == 0) ? 1 : maxPromoterNo + 1;
    }

    /**
     * Creates a new Promoter with automatically generated promoterNo.
     * The promoterNo is generated by incrementing the highest existing promoterNo by 1
     * and converted to String for storage.
     * 
     * @param promoter the Promoter entity to be created (without promoterNo)
     * @return the persisted Promoter entity with generated promoterNo
     * @throws IllegalArgumentException if the Promoter parameter is null
     */
    @Override
    public Promoter createWithAutoPromoterNo(Promoter promoter) {
        if (promoter == null) {
            throw new IllegalArgumentException("Promoter cannot be null");
        }
        validateUniqueConstraintsForCreation(promoter.getProjectPromoter(), promoter.getPromoterName1());

        // Obtener el siguiente promoterNo automáticamente como Integer
        Integer nextPromoterNo = getNextPromoterNo();
        
        // Convertir a String para almacenar en el campo promoterNo (que es String)
        promoter.setPromoterNo(String.valueOf(nextPromoterNo));

        return promoterRepository.save(promoter);
    }

    /**
     * Validates that projectPromoter and promoterName1 are unique for creation (case-insensitive).
     */
    private void validateUniqueConstraintsForCreation(String projectPromoter, String promoterName1) {
        if ((projectPromoter != null && promoterRepository.existsByProjectPromoterOrPromoterName1IgnoreCase(projectPromoter, promoterName1))) {
            checkWhichFieldsAreDuplicateForCreation(projectPromoter, promoterName1);
        }
    }

    /**
     * Validates that projectPromoter and promoterName1 are unique for update, considering only other records (case-insensitive).
     */
    private void validateUniqueConstraintsForUpdate(Promoter existingPromoter, 
                                                  Promoter newPromoter, 
                                                  Long id) {
        boolean projectPromoterChanged = !existingPromoter.getProjectPromoter().equals(newPromoter.getProjectPromoter());
        boolean promoterName1Changed = !existingPromoter.getPromoterName1().equals(newPromoter.getPromoterName1());
        
        if (projectPromoterChanged || promoterName1Changed) {
            if (promoterRepository.existsByProjectPromoterOrPromoterName1IgnoreCaseAndIdNot(
                newPromoter.getProjectPromoter(), newPromoter.getPromoterName1(), id)) {
                checkWhichFieldsAreDuplicateForUpdate(newPromoter.getProjectPromoter(), newPromoter.getPromoterName1(), id);
            }
        }
    }

    /**
     * Determines which specific fields are duplicated during creation.
     */
    private void checkWhichFieldsAreDuplicateForCreation(String projectPromoter, String promoterName1) {
        // Buscar todos los promoters para verificar duplicados case-insensitive
        List<Promoter> allPromoters = promoterRepository.findAll();
        
        boolean projectPromoterExists = allPromoters.stream()
            .anyMatch(p -> p.getProjectPromoter() != null && 
                          p.getProjectPromoter().equalsIgnoreCase(projectPromoter));
        
        boolean promoterName1Exists = allPromoters.stream()
            .anyMatch(p -> p.getPromoterName1() != null && 
                          p.getPromoterName1().equalsIgnoreCase(promoterName1));
        
        buildAndThrowDuplicateException(projectPromoterExists, promoterName1Exists, projectPromoter, promoterName1);
    }

    /**
     * Determines which specific fields are duplicated during update.
     */
    private void checkWhichFieldsAreDuplicateForUpdate(String projectPromoter, String promoterName1, Long excludeId) {
        // Buscar promoters existentes excluyendo el actual
        List<Promoter> allPromoters = promoterRepository.findAll();
        
        boolean projectPromoterExists = allPromoters.stream()
            .anyMatch(p -> !p.getId().equals(excludeId) && 
                          p.getProjectPromoter() != null && 
                          p.getProjectPromoter().equalsIgnoreCase(projectPromoter));
        
        boolean promoterName1Exists = allPromoters.stream()
            .anyMatch(p -> !p.getId().equals(excludeId) && 
                          p.getPromoterName1() != null && 
                          p.getPromoterName1().equalsIgnoreCase(promoterName1));
        
        buildAndThrowDuplicateException(projectPromoterExists, promoterName1Exists, projectPromoter, promoterName1);
    }

    /**
     * Builds and throws the appropriate duplicate exception based on which fields are duplicated.
     */
    private void buildAndThrowDuplicateException(boolean projectPromoterExists, boolean promoterName1Exists, 
                                               String projectPromoter, String promoterName1) {
        if (projectPromoterExists && promoterName1Exists) {
            throw new DuplicateResourceException(
                "Promoter duplication with attributes 'projectPromoter' = '" + projectPromoter + "' and 'promoterName1' = '" + promoterName1 + "'"
            );
        } else if (projectPromoterExists) {
            throw new DuplicateResourceException(
                "Promoter duplication with attribute 'projectPromoter' = '" + projectPromoter + "'"
            );
        } else if (promoterName1Exists) {
            throw new DuplicateResourceException(
                "Promoter duplication with attribute 'promoterName1' = '" + promoterName1 + "'"
            );
        }
    }
}