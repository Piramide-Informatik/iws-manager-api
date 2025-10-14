package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.EmployeeCategory;
import com.iws_manager.iws_manager_api.repositories.EmployeeCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("EmployeeCategory Service Implementation Tests")
class EmployeeCategoryServiceImplTest {
    @Mock
    private EmployeeCategoryRepository categoryRepository;

    @InjectMocks
    private EmployeeCategoryServiceImpl categoryService;

    private EmployeeCategory sampleEmployeeCategory;
    private String  categoryTitle = "testTitle";

    @BeforeEach
    void setUp() {
        sampleEmployeeCategory = new EmployeeCategory();
        sampleEmployeeCategory.setId(1L);
        sampleEmployeeCategory.setTitle(categoryTitle);
    }

    @Test
    @DisplayName("Should save EmployeeCategory successfully")
    void createShouldReturnSavedEmployeeCategory() {
        // Arrange
        when(categoryRepository.save(any(EmployeeCategory.class))).thenReturn(sampleEmployeeCategory);

        // Act
        EmployeeCategory result = categoryService.create(sampleEmployeeCategory);

        // Assert
        assertNotNull(result);
        assertEquals(categoryTitle, result.getTitle());
        verify(categoryRepository, times(1)).save(any(EmployeeCategory.class));
    }

    @Test
    @DisplayName("Should throw exception when creating null EmployeeCategory")
    void createShouldThrowExceptionWhenEmployeeCategoryIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> categoryService.create(null));
        verify(categoryRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should find state by ID")
    void findByIdShouldReturnStateWhenExists() {
        // Arrange
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(sampleEmployeeCategory));

        // Act
        Optional<EmployeeCategory> result = categoryService.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(categoryTitle, result.get().getTitle());
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return empty when state not found")
    void findByIdShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<EmployeeCategory> result = categoryService.findById(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(categoryRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Should throw exception when finding with null ID")
    void findByIdShouldThrowExceptionWhenIdIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> categoryService.findById(null));
        verify(categoryRepository, never()).findById(any());
    }

    @Test
    @DisplayName("Should return all EmployeeCategories")
    void findAllShouldReturnAllEmployeeCategories() {
        // Arrange
        EmployeeCategory category2 = new EmployeeCategory();
        category2.setId(2L);
        category2.setTitle("TestTitle2");

        when(categoryRepository.findAllByOrderByTitleAsc()).thenReturn(Arrays.asList(sampleEmployeeCategory, category2));

        // Act
        List<EmployeeCategory> result = categoryService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(categoryRepository, times(1)).findAllByOrderByTitleAsc();
    }

    @Test
    @DisplayName("Should return all EmployeeCategories ordered by title")
    void findAllShouldReturnEmployeeCategoriesOrderedByTitle() {
        // Arrange
        EmployeeCategory title1 = new EmployeeCategory();
        title1.setTitle("title A");

        EmployeeCategory title2 = new EmployeeCategory();
        title2.setTitle("title B");

        EmployeeCategory title3 = new EmployeeCategory();
        title3.setTitle("title C");

        // Mock ordenado alfabéticamente
        when(categoryRepository.findAllByOrderByTitleAsc())
                .thenReturn(List.of(title1, title2, title3));

        // Act
        List<EmployeeCategory> result = categoryService.findAll();

        // Assert
        assertEquals(3, result.size());
        assertEquals("title A", result.get(0).getTitle());
        assertEquals("title B", result.get(1).getTitle());
        assertEquals("title C", result.get(2).getTitle());
        verify(categoryRepository, times(1)).findAllByOrderByTitleAsc();
    }

    @Test
    @DisplayName("Should update EmployeeCategory successfully")
    void updateShouldReturnUpdatedEmployeeCategory() {
        // Arrange
        EmployeeCategory updatedDetails = new EmployeeCategory();
        updatedDetails.setTitle("title A Updated");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(sampleEmployeeCategory));
        when(categoryRepository.save(any(EmployeeCategory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        EmployeeCategory result = categoryService.update(1L, updatedDetails);

        // Assert
        assertEquals("title A Updated", result.getTitle());
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).save(any(EmployeeCategory.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent EmployeeCategory")
    void updateShouldThrowExceptionWhenEmployeeCategoryNotFound() {
        // Arrange
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> categoryService.update(99L, new EmployeeCategory()));
        verify(categoryRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should delete EmployeeCategory successfully")
    void deleteShouldExecuteDelete() {
        // Arrange
        when(categoryRepository.existsById(1L)).thenReturn(true);

        // Act
        categoryService.delete(1L);

        // Assert
        verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw exception when deleting with null ID")
    void deleteShouldThrowExceptionWhenIdIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> categoryService.delete(null));
        verify(categoryRepository, never()).deleteById(any());
    }

}