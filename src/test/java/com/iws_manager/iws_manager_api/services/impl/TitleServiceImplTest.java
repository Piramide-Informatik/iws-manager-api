package com.iws_manager.iws_manager_api.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.iws_manager.iws_manager_api.models.Title;
import com.iws_manager.iws_manager_api.repositories.TitleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@DisplayName("Title Service Implementation Tests")
class TitleServiceImplTest {

    @Mock
    private TitleRepository titleRepository;

    @InjectMocks
    private TitleServiceImpl titleService;

    private Title sampleTitle;

    @BeforeEach
    void setUp() {
        sampleTitle = new Title();
        sampleTitle.setId(1L);
        sampleTitle.setName("Dr.");
    }

    @Test
    @DisplayName("Should save title successfully")
    void createShouldReturnSavedTitle() {
        // Arrange
        when(titleRepository.save(any(Title.class))).thenReturn(sampleTitle);

        // Act
        Title result = titleService.create(sampleTitle);

        // Assert
        assertNotNull(result);
        assertEquals("Dr.", result.getName());
        verify(titleRepository, times(1)).save(any(Title.class));
    }

    @Test
    @DisplayName("Should throw exception when creating null title")
    void createShouldThrowExceptionWhenTitleIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> titleService.create(null));
        verify(titleRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should find title by ID")
    void findByIdShouldReturnTitleWhenExists() {
        // Arrange
        when(titleRepository.findById(1L)).thenReturn(Optional.of(sampleTitle));

        // Act
        Optional<Title> result = titleService.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Dr.", result.get().getName());
        verify(titleRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return empty when title not found")
    void findByIdShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(titleRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<Title> result = titleService.findById(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(titleRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Should throw exception when finding with null ID")
    void findByIdShouldThrowExceptionWhenIdIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> titleService.findById(null));
        verify(titleRepository, never()).findById(any());
    }

    @Test
    @DisplayName("Should return all titles")
    void findAllShouldReturnAllTitles() {
        // Arrange
        Title title2 = new Title();
        title2.setId(2L);
        title2.setName("Mgtr.");

        when(titleRepository.findAllByOrderByNameAsc()).thenReturn(Arrays.asList(sampleTitle, title2));

        // Act
        List<Title> result = titleService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(titleRepository, times(1)).findAllByOrderByNameAsc();
    }

    @Test
    @DisplayName("Should return all titles ordered by name")
    void findAllShouldReturnAllTitlesOrderedByName() {
        // Arrange
        Title title1 = new Title();
        title1.setId(1L);
        title1.setName("Dr.");

        Title title2 = new Title();
        title2.setId(2L);
        title2.setName("Prof.");

        // Mockea el método que realmente usa el servicio
        when(titleRepository.findAllByOrderByNameAsc())
                .thenReturn(List.of(title1, title2)); // Ordenados alfabéticamente

        // Act
        List<Title> result = titleService.findAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Dr.", result.get(0).getName()); // Verifica el orden
        assertEquals("Prof.", result.get(1).getName());
        verify(titleRepository, times(1)).findAllByOrderByNameAsc(); // Verifica el método correcto
    }

    @Test
    @DisplayName("Should update title successfully")
    void updateShouldReturnUpdatedTitle() {
        // Arrange
        Title updatedDetails = new Title();
        updatedDetails.setName("Dr. Updated");

        when(titleRepository.findById(1L)).thenReturn(Optional.of(sampleTitle));
        when(titleRepository.save(any(Title.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Title result = titleService.update(1L, updatedDetails);

        // Assert
        assertEquals("Dr. Updated", result.getName());
        verify(titleRepository, times(1)).findById(1L);
        verify(titleRepository, times(1)).save(any(Title.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent title")
    void updateShouldThrowExceptionWhenTitleNotFound() {
        // Arrange
        when(titleRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> titleService.update(99L, new Title()));
        verify(titleRepository, never()).save(any());
    }

    @Test
    public void update_ShouldThrowException_WhenOptimisticLockingFails() {
        // Setup
        Long titleId = 1L;
        Title currentTitle = new Title();
        currentTitle.setId(titleId);
        currentTitle.setName("Dr.");
        currentTitle.setVersion(2); // Current version in DB

        Title outdatedTitle = new Title();
        outdatedTitle.setId(titleId);
        outdatedTitle.setName("Doctor");
        outdatedTitle.setVersion(1); // Outdated version

        when(titleRepository.findById(titleId)).thenReturn(Optional.of(currentTitle));
        when(titleRepository.save(any(Title.class)))
                .thenThrow(new ObjectOptimisticLockingFailureException("Concurrent modification detected",
                        new ObjectOptimisticLockingFailureException(Title.class, titleId)));

        // Execution and verification
        Exception exception = assertThrows(RuntimeException.class, () -> {
            titleService.update(titleId, outdatedTitle);
        });

        assertNotNull(exception, "An exception should have been thrown");

        // Verify if it's the direct exception or wrapped
        if (!(exception instanceof ObjectOptimisticLockingFailureException)) {
            assertNotNull(exception.getCause(), "The exception should have a cause");
            assertTrue(exception.getCause() instanceof ObjectOptimisticLockingFailureException,
                    "The cause should be ObjectOptimisticLockingFailureException");
        }

        // Verify repository interactions
        verify(titleRepository).findById(titleId);
        verify(titleRepository).save(any(Title.class));
    }
}
