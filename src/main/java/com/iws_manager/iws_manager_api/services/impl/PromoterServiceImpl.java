package com.iws_manager.iws_manager_api.services.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(() -> new RuntimeException("Promoter not found with id: " + id));
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
    public List<Promoter> getByPromoterNo(Integer promoterNo) {
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
}