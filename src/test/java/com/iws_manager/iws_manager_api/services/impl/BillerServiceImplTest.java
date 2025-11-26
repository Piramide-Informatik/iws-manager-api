package com.iws_manager.iws_manager_api.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.iws_manager.iws_manager_api.models.Biller;
import com.iws_manager.iws_manager_api.repositories.BillerRepository;
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
@DisplayName("Biller Service Implementation Tests")
class BillerServiceImplTest {

    @Mock
    private BillerRepository billerRepository;

    @InjectMocks
    private BillerServiceImpl billerService;

    private Biller sampleBiller;
    private String billerName = "Biller 1";

    @BeforeEach
    void setUp() {
        sampleBiller = new Biller();
        sampleBiller.setId(1L);
        sampleBiller.setName(billerName);
    }

    @Test
    @DisplayName("Should save biller successfully")
    void createShouldReturnSavedBiller() {
        // Arrange
        when(billerRepository.save(any(Biller.class))).thenReturn(sampleBiller);

        // Act
        Biller result = billerService.create(sampleBiller);

        // Assert
        assertNotNull(result);
        assertEquals(billerName, result.getName());
        verify(billerRepository, times(1)).save(any(Biller.class));
    }

    @Test
    @DisplayName("Should throw exception when creating null Biller")
    void createShouldThrowExceptionWhenBillerIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> billerService.create(null));
        verify(billerRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should find biller by ID")
    void findByIdShouldReturnBillerWhenExists() {
        // Arrange
        when(billerRepository.findById(1L)).thenReturn(Optional.of(sampleBiller));

        // Act
        Optional<Biller> result = billerService.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(billerName, result.get().getName());
        verify(billerRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return empty when biller not found")
    void findByIdShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(billerRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<Biller> result = billerService.findById(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(billerRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Should throw exception when finding with null ID")
    void findByIdShouldThrowExceptionWhenIdIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> billerService.findById(null));
        verify(billerRepository, never()).findById(any());
    }

    @Test
    @DisplayName("Should return all billeres")
    void findAllShouldReturnAllBilleres() {
        // Arrange
        Biller biller2 = new Biller();
        biller2.setId(2L);
        biller2.setName("New Biller");

        when(billerRepository.findAllByOrderByNameAsc()).thenReturn(Arrays.asList(sampleBiller, biller2));

        // Act
        List<Biller> result = billerService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(billerRepository, times(1)).findAllByOrderByNameAsc();
    }

    @Test
    @DisplayName("Should update biller successfully")
    void updateShouldReturnUpdatedBiller() {
        // Arrange
        Biller updatedDetails = new Biller();
        updatedDetails.setName("Biller Updated");

        when(billerRepository.findById(1L)).thenReturn(Optional.of(sampleBiller));
        when(billerRepository.save(any(Biller.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Biller result = billerService.update(1L, updatedDetails);

        // Assert
        assertEquals("Biller Updated", result.getName());
        verify(billerRepository, times(1)).findById(1L);
        verify(billerRepository, times(1)).save(any(Biller.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent biller")
    void updateShouldThrowExceptionWhenBillerNotFound() {
        // Arrange
        when(billerRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> billerService.update(99L, new Biller()));
        verify(billerRepository, never()).save(any());
    }

    @Test
    public void updateShouldThrowExceptionWhenOptimisticLockingFails() {
        // Setup
        Long billerId = 1L;
        Biller currentBiller = new Biller();
        currentBiller.setId(billerId);
        currentBiller.setName(billerName);
        currentBiller.setVersion(2); // Current version in DB

        Biller outdatedBiller = new Biller();
        outdatedBiller.setId(billerId);
        outdatedBiller.setName("Doctor");
        outdatedBiller.setVersion(1); // Outdated version

        when(billerRepository.findById(billerId)).thenReturn(Optional.of(currentBiller));
        when(billerRepository.save(any(Biller.class)))
                .thenThrow(new ObjectOptimisticLockingFailureException("Concurrent modification detected",
                        new ObjectOptimisticLockingFailureException(Biller.class, billerId)));

        // Execution and verification
        Exception exception = assertThrows(RuntimeException.class,
                () -> billerService.update(billerId, outdatedBiller));

        assertNotNull(exception, "An exception should have been thrown");

        // Verify if it's the direct exception or wrapped
        if (!(exception instanceof ObjectOptimisticLockingFailureException)) {
            assertNotNull(exception.getCause(), "The exception should have a cause");
            assertTrue(exception.getCause() instanceof ObjectOptimisticLockingFailureException,
                    "The cause should be ObjectOptimisticLockingFailureException");
        }

        // Verify repository interactions
        verify(billerRepository).findById(billerId);
        verify(billerRepository).save(any(Biller.class));
    }
}
