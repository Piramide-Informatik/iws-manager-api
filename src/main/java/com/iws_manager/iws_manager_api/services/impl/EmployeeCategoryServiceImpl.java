package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.EmployeeCategory;
import com.iws_manager.iws_manager_api.repositories.EmployeeCategoryRepository;
import com.iws_manager.iws_manager_api.services.interfaces.EmployeeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeCategoryServiceImpl implements EmployeeCategoryService {
    private final EmployeeCategoryRepository categoryRepository;

    @Autowired
    public EmployeeCategoryServiceImpl(EmployeeCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public EmployeeCategory create(EmployeeCategory category) {
        if (category == null) {
            throw new IllegalArgumentException("EmployeeCategory cannot be null");
        }
        return categoryRepository.save(category);
    }

    @Override
    public Optional<EmployeeCategory> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return categoryRepository.findById(id);
    }

    @Override
    public List<EmployeeCategory> findAll() {
        return categoryRepository.findAllByOrderByTitleAsc();
    }

    @Override
    public EmployeeCategory update(Long id, EmployeeCategory categoryDetails) {
        if (id == null || categoryDetails == null) {
            throw new IllegalArgumentException("ID and EmployeeCategory details cannot be null");
        }
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setLabel(categoryDetails.getLabel());
                    existingCategory.setTitle(categoryDetails.getTitle());
                    return categoryRepository.save(existingCategory);
                })
                .orElseThrow(() -> new RuntimeException("EmployeeCategory not found with id: " + id));
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("EmployeeCategory not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
