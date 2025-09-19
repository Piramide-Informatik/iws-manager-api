package com.iws_manager.iws_manager_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.Text;
import com.iws_manager.iws_manager_api.repositories.TextRepository;
import com.iws_manager.iws_manager_api.services.interfaces.TextService;

import jakarta.persistence.EntityNotFoundException;

/**
 * Implementation of the {@link TextService} interface for managing Text entities.
 * Provides CRUD operations and business logic for Text templates management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class TextServiceImpl implements TextService {

    private final TextRepository textRepository;

    /**
     * Constructs a new TextService with the required repository dependency.
     * 
     * @param textRepository the repository for Text entity operations
     */
    @Autowired
    public TextServiceImpl(TextRepository textRepository) {
        this.textRepository = textRepository;
    }

    /**
     * Creates and persists a new Text entity.
     * 
     * @param text the Text entity to be created
     * @return the persisted Text entity with generated ID
     * @throws IllegalArgumentException if the Text parameter is null
     */
    @Override
    public Text create(Text text) {
        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null");
        }
        return textRepository.save(text);
    }

    /**
     * Retrieves a Text by its unique identifier.
     * 
     * @param id the ID of the Text to retrieve
     * @return an Optional containing the found Text, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Text> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return textRepository.findById(id);
    }

    /**
     * Retrieves all Text entities from the database, ordered by label ascending.
     * 
     * @return a List of all Text entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Text> findAll() {
        return textRepository.findAllByOrderByLabelAsc();
    }

    /**
     * Updates an existing Text entity.
     * 
     * @param id the ID of the Text to update
     * @param textDetails the Text object containing updated fields
     * @return the updated Text entity
     * @throws RuntimeException if no Text exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public Text update(Long id, Text textDetails) {
        if (id == null || textDetails == null) {
            throw new IllegalArgumentException("ID and Text details cannot be null");
        }
        
        return textRepository.findById(id)
                .map(existingText -> {
                    existingText.setLabel(textDetails.getLabel());
                    existingText.setContent(textDetails.getContent());
                    return textRepository.save(existingText);
                })
                .orElseThrow(() -> new RuntimeException("Text not found with id: " + id));
    }

    /**
     * Deletes a Text entity by its ID.
     * 
     * @param id the ID of the Text to delete
     * @throws IllegalArgumentException if the id parameter is null
     * @throws EntityNotFoundException if no Text exists with the given ID
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        if (!textRepository.existsById(id)) {  
            throw new EntityNotFoundException("Text not found with id: " + id);
        }
        textRepository.deleteById(id);
    }
}