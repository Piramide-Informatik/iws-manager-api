package com.iws_manager.iws_manager_api.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.iws_manager.iws_manager_api.models.CompanyType;
import com.iws_manager.iws_manager_api.repositories.CompanyTypeRepository;
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

@ExtendWith(MockitoExtension.class)
@DisplayName("CompanyType Service Implementation Tests")
class CompanyTypeServiceImplTest {

    @Mock
    private CompanyTypeRepository companyTypeRepository;

    @InjectMocks
    private CompanyTypeServiceImpl companyTypeService;

    private CompanyType sampleCompanyType;
    private String name = "Public";

    @BeforeEach
    void setUp() {
        sampleCompanyType = new CompanyType();
        sampleCompanyType.setId(1L);
        sampleCompanyType.setName(name);
    }

    @Test
    @DisplayName("Should save companyType successfully")
    void createShouldReturnSavedCompanyType() {
        // Arrange
        when(companyTypeRepository.save(any(CompanyType.class))).thenReturn(sampleCompanyType);

        // Act
        CompanyType result = companyTypeService.create(sampleCompanyType);

        // Assert
        assertNotNull(result);
        assertEquals(name, result.getName());
        verify(companyTypeRepository, times(1)).save(any(CompanyType.class));
    }

    @Test
    @DisplayName("Should throw exception when creating null companyType")
    void createShouldThrowExceptionWhenCompanyTypeIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> companyTypeService.create(null));
        verify(companyTypeRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should find companyType by ID")
    void findByIdShouldReturnCompanyTypeWhenExists() {
        // Arrange
        when(companyTypeRepository.findById(1L)).thenReturn(Optional.of(sampleCompanyType));

        // Act
        Optional<CompanyType> result = companyTypeService.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(name, result.get().getName());
        verify(companyTypeRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return empty when companyType not found")
    void findByIdShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(companyTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<CompanyType> result = companyTypeService.findById(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(companyTypeRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Should throw exception when finding with null ID")
    void findByIdShouldThrowExceptionWhenIdIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> companyTypeService.findById(null));
        verify(companyTypeRepository, never()).findById(any());
    }

    @Test
    @DisplayName("Should return all companyType")
    void findAllShouldReturnAllCompanyTypes() {
        // Arrange
        CompanyType companyType2 = new CompanyType();
        companyType2.setId(2L);
        companyType2.setName("Private");
        
        when(companyTypeRepository.findAllByOrderByNameAsc()).thenReturn(Arrays.asList(sampleCompanyType, companyType2));

        // Act
        List<CompanyType> result = companyTypeService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(companyTypeRepository, times(1)).findAllByOrderByNameAsc();
    }

    @Test
    @DisplayName("Should return all company types ordered by name")
    void shouldGetAllCompanyTypesOrdered() {
        String name1 = "Public";
        String name2 = "Private";
        String name3 = "Non-Profit";
        // Arrange
        CompanyType publicType = new CompanyType();
        publicType.setName(name1);
        
        CompanyType privateType = new CompanyType();
        privateType.setName(name2);
        
        CompanyType nonprofit = new CompanyType();
        nonprofit.setName(name3);
        
        when(companyTypeRepository.findAllByOrderByNameAsc())
            .thenReturn(List.of(nonprofit, privateType, publicType));

        // Act
        List<CompanyType> result = companyTypeService.findAll();

        // Assert
        assertEquals(3, result.size());
        assertEquals(name3, result.get(0).getName());
        assertEquals(name2, result.get(1).getName());
        assertEquals(name1, result.get(2).getName());
        verify(companyTypeRepository, times(1)).findAllByOrderByNameAsc();
    }

    @Test
    @DisplayName("Should update companyType successfully")
    void updateShouldReturnUpdatedCompanyType() {
        // Arrange
        CompanyType updatedDetails = new CompanyType();
        updatedDetails.setName("Public Updated");

        when(companyTypeRepository.findById(1L)).thenReturn(Optional.of(sampleCompanyType));
        when(companyTypeRepository.save(any(CompanyType.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        CompanyType result = companyTypeService.update(1L, updatedDetails);

        // Assert
        assertEquals("Public Updated", result.getName());
        verify(companyTypeRepository, times(1)).findById(1L);
        verify(companyTypeRepository, times(1)).save(any(CompanyType.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent companyType")
    void updateShouldThrowExceptionWhenCompanyTypeNotFound() {
        // Arrange
        when(companyTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, 
            () -> companyTypeService.update(99L, new CompanyType()));
        verify(companyTypeRepository, never()).save(any());
    }
    @Test
    @DisplayName("Should delete companyType successfully")
    void deleteShouldExecuteDelete() {
        // Arrange
        when(companyTypeRepository.existsById(1L)).thenReturn(true);

        // Act
        companyTypeService.delete(1L);

        // Assert
        verify(companyTypeRepository, times(1)).deleteById(1L);
    }
    @Test
    @DisplayName("Should throw exception when deleting with null ID")
    void deleteShouldThrowExceptionWhenIdIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> companyTypeService.delete(null));
        verify(companyTypeRepository, never()).deleteById(any());
    }
}
