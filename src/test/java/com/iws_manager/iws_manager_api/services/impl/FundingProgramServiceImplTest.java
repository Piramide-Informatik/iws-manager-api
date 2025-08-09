package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.FundingProgram;
import com.iws_manager.iws_manager_api.repositories.FundingProgramRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("FundingProgram Service Implementation Tests")
class FundingProgramServiceImplTest {

    @Mock
    private FundingProgramRepository fundingProgramRepository;

    @InjectMocks
    private FundingProgramServiceImpl fundingProgramService;

    private FundingProgram sampleFundingProgram;
    private static final String FUNDING_PROGRAM_NAME = "KMU-i";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleFundingProgram = new FundingProgram();
        sampleFundingProgram.setId(1L);
        sampleFundingProgram.setDefaultFundingRate(12.5);
        sampleFundingProgram.setDefaultHoursPerYear(1800.0);
        sampleFundingProgram.setDefaultResearchShare(30.0);
        sampleFundingProgram.setDefaultStuffFlat(15.0);
        sampleFundingProgram.setName("ZIM");
    }

    /**
     * Test creation of a FundingProgram entity.
     */
    @Test
    @DisplayName("Should create funding program")
    void testCreateFundingProgram() {
        // Arrange
        when(fundingProgramRepository.save(sampleFundingProgram)).thenReturn(sampleFundingProgram);
        // Act
        FundingProgram result = fundingProgramService.create(sampleFundingProgram);
        // Assert
        assertEquals(sampleFundingProgram, result);
        verify(fundingProgramRepository, times(1)).save(sampleFundingProgram);
    }

    /**
     * Test retrieval of a FundingProgram by ID.
     */
    @Test
    @DisplayName("Should find funding program by ID")
    void testFindById() {
        // Arrange
        when(fundingProgramRepository.findById(1L)).thenReturn(Optional.of(sampleFundingProgram));
        // Act
        Optional<FundingProgram> result = fundingProgramService.findById(1L);
        // Assert
        assertTrue(result.isPresent());
        assertEquals(sampleFundingProgram, result.get());
    }

    /**
     * Test retrieval of all FundingProgram entities.
     */
    @Test
    @DisplayName("Should find all funding programs")
    void testFindAll() {
        // Arrange
        FundingProgram fp2 = new FundingProgram();
        fp2.setId(2L);
        fp2.setName(FUNDING_PROGRAM_NAME);
        when(fundingProgramRepository.findAll()).thenReturn(Arrays.asList(sampleFundingProgram, fp2));
        // Act
        List<FundingProgram> result = fundingProgramService.findAll();
        // Assert
        assertEquals(2, result.size());
    }

    /**
     * Test update of an existing FundingProgram entity.
     */
    @Test
    @DisplayName("Should update funding program")
    void testUpdateFundingProgram() {
        // Arrange
        FundingProgram updated = new FundingProgram();
        updated.setDefaultFundingRate(20.0);
        updated.setDefaultHoursPerYear(2000.0);
        updated.setDefaultResearchShare(40.0);
        updated.setDefaultStuffFlat(10.0);
        updated.setName(FUNDING_PROGRAM_NAME);
        when(fundingProgramRepository.findById(1L)).thenReturn(Optional.of(sampleFundingProgram));
        when(fundingProgramRepository.save(any(FundingProgram.class))).thenReturn(sampleFundingProgram);
        // Act
        FundingProgram result = fundingProgramService.update(1L, updated);
        // Assert
        assertEquals(FUNDING_PROGRAM_NAME, result.getName());
        assertEquals(20.0, result.getDefaultFundingRate());
        assertEquals(2000.0, result.getDefaultHoursPerYear());
        assertEquals(40.0, result.getDefaultResearchShare());
        assertEquals(10.0, result.getDefaultStuffFlat());
    }

    /**
     * Test deletion of a FundingProgram entity.
     */
    @Test
    @DisplayName("Should delete funding program")
    void testDeleteFundingProgram() {
        // Arrange
        doNothing().when(fundingProgramRepository).deleteById(1L);
        // Act
        fundingProgramService.delete(1L);
        // Assert
        verify(fundingProgramRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw exception when creating null FundingProgram")
    void createShouldThrowExceptionWhenFundingProgramIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> fundingProgramService.create(null));
        verify(fundingProgramRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when updating with null ID")
    void updateShouldThrowExceptionWhenIdIsNull() {
        // Act & Assert
        FundingProgram details = new FundingProgram();
        assertThrows(IllegalArgumentException.class, () -> fundingProgramService.update(null, details));
    }

    @Test
    @DisplayName("Should throw exception when updating with null details")
    void updateShouldThrowExceptionWhenDetailsAreNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> fundingProgramService.update(1L, null));
    }

    @Test
    @DisplayName("Should throw exception when finding by null ID")
    void findByIdShouldThrowExceptionWhenIdIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> fundingProgramService.findById(null));
    }

    @Test
    @DisplayName("Should throw exception when deleting by null ID")
    void deleteShouldThrowExceptionWhenIdIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> fundingProgramService.delete(null));
    }
}
