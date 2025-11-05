package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.AbsenceType;
import com.iws_manager.iws_manager_api.repositories.AbsenceTypeRepository;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.iws_manager.iws_manager_api.exception.exceptions.DuplicateResourceException;


@ExtendWith(MockitoExtension.class)
@DisplayName("AbsenceType Service Implementation Tests")
public class AbsenceTypeServiceImplTest {
    private static final String VACATION_NAME = "Vacation";
    private static final String PERSONAL_PERMISSION="Personal permission";
    @Mock
    private AbsenceTypeRepository absenceTypeRepository;
    @InjectMocks
    private AbsenceTypeServiceImpl absenceTypeService;
    private AbsenceType sampleAbsenceType;

    @BeforeEach
    void setUp(){
        sampleAbsenceType = new AbsenceType();
        sampleAbsenceType.setId(1L);
        sampleAbsenceType.setName(VACATION_NAME);
    }

    @Test
    @DisplayName("Should save absencetype successfully")
    void creatShouldReturnSavedAbsenceType(){
        when(absenceTypeRepository.save(any(AbsenceType.class))).thenReturn(sampleAbsenceType);

        AbsenceType result = absenceTypeService.create(sampleAbsenceType);

        assertNotNull(result);
        assertEquals(VACATION_NAME, result.getName());
        verify(absenceTypeRepository, times(1)).save(any(AbsenceType.class));
    }

    @Test
    @DisplayName("Should throw exception when creating null absencetype")
    void createShouldThrowExceptionWhenAbsenceTypeIsNull() {
        assertThrows(IllegalArgumentException.class, () -> absenceTypeService.create(null));
        verify(absenceTypeRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should find absencetype by ID")
    void findByIdShouldReturnAbsenceTypeWhenExists() {
        when(absenceTypeRepository.findById(1L)).thenReturn(Optional.of(sampleAbsenceType));

        Optional<AbsenceType> result = absenceTypeService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(VACATION_NAME, result.get().getName());
        verify(absenceTypeRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when finding with null ID")
    void findByIdShouldThrowExceptionWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> absenceTypeService.findById(null));
        verify(absenceTypeRepository, never()).findById(any());
    }

    @Test
    @DisplayName("Should return all absencetype")
    void findAllShouldReturnAllAbsenceType() {
        AbsenceType absenceType2 = new AbsenceType();
        absenceType2.setId(2L);
        absenceType2.setName(PERSONAL_PERMISSION);

        when(absenceTypeRepository.findAllByOrderByNameAsc()).thenReturn(Arrays.asList(sampleAbsenceType,absenceType2));

        List<AbsenceType> result = absenceTypeService.findAll();

        assertEquals(2, result.size());
        verify(absenceTypeRepository,times(1)).findAllByOrderByNameAsc();
    }

    @Test
    @DisplayName("Should return all absencetype ordered by name")
    void findAllShouldReturnAllAbsenceTypeOrderedByName() {
        AbsenceType absenceType1 = new AbsenceType();
        absenceType1.setId(1L);
        absenceType1.setName("A");

        AbsenceType absenceType2 = new AbsenceType();
        absenceType2.setId(2L);
        absenceType2.setName("C");

        when(absenceTypeRepository.findAllByOrderByNameAsc())
                .thenReturn(List.of(absenceType1,absenceType2));

        List<AbsenceType> result = absenceTypeService.findAll();

        assertEquals(2, result.size());
        assertEquals("A", result.get(0).getName());
        assertEquals("C",result.get(1).getName());
        verify(absenceTypeRepository, times(1)).findAllByOrderByNameAsc();
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent absencetype")
    void updateShouldThrowExceptionWhenAbsenceTypeNotFound() {
        when(absenceTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> absenceTypeService.update(99L, new AbsenceType()));
        verify(absenceTypeRepository, never()).save(any());
    }

    @Test
    public void updateShouldThrowExceptionWhenOptimisticLockingFails() {
        // Setup
        Long absenceTypeId = 1L;
        
        AbsenceType currentAbsenceType = new AbsenceType();
        currentAbsenceType.setId(absenceTypeId);
        currentAbsenceType.setName(VACATION_NAME);
        currentAbsenceType.setLabel("VAC");
        currentAbsenceType.setVersion(2L);

        // Outdated version trying to update (version 1)
        AbsenceType outdatedAbsenceType = new AbsenceType();
        outdatedAbsenceType.setId(absenceTypeId);
        outdatedAbsenceType.setName(PERSONAL_PERMISSION);
        outdatedAbsenceType.setLabel("PERMX");
        outdatedAbsenceType.setVersion(1L);

        when(absenceTypeRepository.findById(absenceTypeId)).thenReturn(Optional.of(currentAbsenceType));
        
        when(absenceTypeRepository.existsByNameAndIdNot(anyString(), anyLong()))
            .thenReturn(false);
        
        when(absenceTypeRepository.save(any(AbsenceType.class)))
                .thenThrow(new ObjectOptimisticLockingFailureException(AbsenceType.class, absenceTypeId));

        assertThrows(ObjectOptimisticLockingFailureException.class,
                () -> absenceTypeService.update(absenceTypeId, outdatedAbsenceType));

        verify(absenceTypeRepository).findById(absenceTypeId);
        verify(absenceTypeRepository).existsByNameAndIdNot(PERSONAL_PERMISSION, absenceTypeId);
        verify(absenceTypeRepository).save(any(AbsenceType.class));
    }

    @Test
    public void updateShouldThrowExceptionWhenOnlyNameChangedAndAlreadyExists() {
        // Setup
        Long absenceTypeId = 1L;
        
        AbsenceType existingAbsenceType = new AbsenceType();
        existingAbsenceType.setId(absenceTypeId);
        existingAbsenceType.setName(VACATION_NAME);
        existingAbsenceType.setLabel("VAC");

        AbsenceType updatedAbsenceType = new AbsenceType();
        updatedAbsenceType.setName(PERSONAL_PERMISSION); // Nombre que ya existe
        updatedAbsenceType.setLabel("VAC"); // Mismo label

        when(absenceTypeRepository.findById(absenceTypeId)).thenReturn(Optional.of(existingAbsenceType));
        when(absenceTypeRepository.existsByNameAndIdNot(PERSONAL_PERMISSION, absenceTypeId))
            .thenReturn(true);

        // Execute & Verify
        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class,
                () -> absenceTypeService.update(absenceTypeId, updatedAbsenceType));

        assertEquals("Absence type duplication with attribute 'name' = '" + PERSONAL_PERMISSION + "'", 
                    exception.getMessage());

        verify(absenceTypeRepository).findById(absenceTypeId);
        verify(absenceTypeRepository).existsByNameAndIdNot(PERSONAL_PERMISSION, absenceTypeId);
        verify(absenceTypeRepository, never()).existsByLabelAndIdNot(anyString(), anyLong());
        verify(absenceTypeRepository, never()).save(any(AbsenceType.class));
    }
    
    @Test
    public void updateShouldThrowExceptionWhenOnlyLabelChangedAndAlreadyExists() {
        // Setup
        Long absenceTypeId = 1L;
        
        AbsenceType existingAbsenceType = new AbsenceType();
        existingAbsenceType.setId(absenceTypeId);
        existingAbsenceType.setName(VACATION_NAME);
        existingAbsenceType.setLabel("VAC");

        AbsenceType updatedAbsenceType = new AbsenceType();
        updatedAbsenceType.setName(VACATION_NAME); // Mismo nombre
        updatedAbsenceType.setLabel("PERM"); // Label que ya existe

        when(absenceTypeRepository.findById(absenceTypeId)).thenReturn(Optional.of(existingAbsenceType));
        when(absenceTypeRepository.existsByLabelAndIdNot("PERM", absenceTypeId))
            .thenReturn(true);

        // Execute & Verify
        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class,
                () -> absenceTypeService.update(absenceTypeId, updatedAbsenceType));

        assertEquals("Absence type duplication with attribute 'label' = 'PERM'", 
                    exception.getMessage());

        verify(absenceTypeRepository).findById(absenceTypeId);
        verify(absenceTypeRepository, never()).existsByNameAndIdNot(anyString(), anyLong());
        verify(absenceTypeRepository).existsByLabelAndIdNot("PERM", absenceTypeId);
        verify(absenceTypeRepository, never()).save(any(AbsenceType.class));
    }

    @Test
    public void updateShouldThrowExceptionWhenBothNameAndLabelAlreadyExist() {
        // Setup
        Long absenceTypeId = 1L;
        
        AbsenceType existingAbsenceType = new AbsenceType();
        existingAbsenceType.setId(absenceTypeId);
        existingAbsenceType.setName(VACATION_NAME);
        existingAbsenceType.setLabel("VAC");

        AbsenceType updatedAbsenceType = new AbsenceType();
        updatedAbsenceType.setName(PERSONAL_PERMISSION); 
        updatedAbsenceType.setLabel("PERM"); 

        when(absenceTypeRepository.findById(absenceTypeId)).thenReturn(Optional.of(existingAbsenceType));
        when(absenceTypeRepository.existsByNameAndIdNot(PERSONAL_PERMISSION, absenceTypeId))
            .thenReturn(true);
        when(absenceTypeRepository.existsByLabelAndIdNot("PERM", absenceTypeId))
            .thenReturn(true);

        // Execute & Verify
        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class,
                () -> absenceTypeService.update(absenceTypeId, updatedAbsenceType));

        assertEquals("Absence type duplication with attributes 'name' = '" + PERSONAL_PERMISSION + "' and 'label' = 'PERM'", 
                    exception.getMessage());

        verify(absenceTypeRepository).findById(absenceTypeId);
        verify(absenceTypeRepository).existsByNameAndIdNot(PERSONAL_PERMISSION, absenceTypeId);
        verify(absenceTypeRepository).existsByLabelAndIdNot("PERM", absenceTypeId);
        verify(absenceTypeRepository, never()).save(any(AbsenceType.class));
    }

    @Test
    public void createShouldThrowExceptionWhenNameAlreadyExists() {
        // Setup
        AbsenceType newAbsenceType = new AbsenceType();
        newAbsenceType.setName(PERSONAL_PERMISSION); 
        newAbsenceType.setLabel("NEW_LABEL"); 

        when(absenceTypeRepository.existsByName(PERSONAL_PERMISSION)).thenReturn(true);
        when(absenceTypeRepository.existsByLabel("NEW_LABEL")).thenReturn(false);

        // Execute & Verify
        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class,
                () -> absenceTypeService.create(newAbsenceType));

        assertEquals("Absence type duplication with attribute 'name' = '" + PERSONAL_PERMISSION + "'", 
                    exception.getMessage());

        verify(absenceTypeRepository).existsByName(PERSONAL_PERMISSION);
        verify(absenceTypeRepository).existsByLabel("NEW_LABEL");
        verify(absenceTypeRepository, never()).save(any(AbsenceType.class));
    }

    @Test
    public void createShouldThrowExceptionWhenLabelAlreadyExists() {
        // Setup
        AbsenceType newAbsenceType = new AbsenceType();
        newAbsenceType.setName("NEW_NAME"); 
        newAbsenceType.setLabel("VAC"); 

        when(absenceTypeRepository.existsByName("NEW_NAME")).thenReturn(false);
        when(absenceTypeRepository.existsByLabel("VAC")).thenReturn(true);

        // Execute & Verify
        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class,
                () -> absenceTypeService.create(newAbsenceType));

        assertEquals("Absence type duplication with attribute 'label' = 'VAC'", 
                    exception.getMessage());

        verify(absenceTypeRepository).existsByName("NEW_NAME");
        verify(absenceTypeRepository).existsByLabel("VAC");
        verify(absenceTypeRepository, never()).save(any(AbsenceType.class));
    }

    @Test
    public void createShouldThrowExceptionWhenBothNameAndLabelAlreadyExist() {
        // Setup
        AbsenceType newAbsenceType = new AbsenceType();
        newAbsenceType.setName(PERSONAL_PERMISSION); 
        newAbsenceType.setLabel("VAC"); 

        when(absenceTypeRepository.existsByName(PERSONAL_PERMISSION)).thenReturn(true);
        when(absenceTypeRepository.existsByLabel("VAC")).thenReturn(true);

        // Execute & Verify
        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class,
                () -> absenceTypeService.create(newAbsenceType));

        assertEquals("Absence type duplication with attributes 'name' = '" + PERSONAL_PERMISSION + "' and 'label' = 'VAC'", 
                    exception.getMessage());

        verify(absenceTypeRepository).existsByName(PERSONAL_PERMISSION);
        verify(absenceTypeRepository).existsByLabel("VAC");
        verify(absenceTypeRepository, never()).save(any(AbsenceType.class));
    }
    
}
