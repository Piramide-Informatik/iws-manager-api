package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.exception.exceptions.DuplicateResourceException;
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

@ExtendWith(MockitoExtension.class)
@DisplayName("AbsenceType Service Implementation Tests")
public class AbsenceTypeServiceImplTest {
    private static final String VACATION_NAME = "Vacation";
    private static final String PERSONAL_PERMISSION = "Personal permission";
    private static final Long ABSENCE_TYPE_ID = 1L;
    private static final String EXISTING_LABEL = "VAC";
    private static final String NEW_LABEL = "NEW_LABEL";
    private static final String DUPLICATE_LABEL = "PERM";

    @Mock
    private AbsenceTypeRepository absenceTypeRepository;

    @InjectMocks
    private AbsenceTypeServiceImpl absenceTypeService;

    private AbsenceType sampleAbsenceType;
    private AbsenceType existingAbsenceType;

    @BeforeEach
    void setUp() {
        sampleAbsenceType = createAbsenceType(ABSENCE_TYPE_ID, VACATION_NAME, EXISTING_LABEL);
        existingAbsenceType = createAbsenceType(ABSENCE_TYPE_ID, VACATION_NAME, EXISTING_LABEL);
    }

    // Helper methods to eliminate duplication
    private AbsenceType createAbsenceType(Long id, String name, String label) {
        AbsenceType absenceType = new AbsenceType();
        absenceType.setId(id);
        absenceType.setName(name);
        absenceType.setLabel(label);
        return absenceType;
    }

    private AbsenceType createAbsenceType(String name, String label) {
        return createAbsenceType(null, name, label);
    }

    private void setupFindByIdMock() {
        when(absenceTypeRepository.findById(ABSENCE_TYPE_ID)).thenReturn(Optional.of(existingAbsenceType));
    }

    // Tests
    @Test
    @DisplayName("Should save absencetype successfully")
    void createShouldReturnSavedAbsenceType() {
        when(absenceTypeRepository.existsByName(VACATION_NAME)).thenReturn(false);
        when(absenceTypeRepository.existsByLabel(EXISTING_LABEL)).thenReturn(false);
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
        when(absenceTypeRepository.findById(ABSENCE_TYPE_ID)).thenReturn(Optional.of(sampleAbsenceType));

        Optional<AbsenceType> result = absenceTypeService.findById(ABSENCE_TYPE_ID);

        assertTrue(result.isPresent());
        assertEquals(VACATION_NAME, result.get().getName());
        verify(absenceTypeRepository, times(1)).findById(ABSENCE_TYPE_ID);
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
        AbsenceType absenceType2 = createAbsenceType(2L, PERSONAL_PERMISSION, "PERM");
        when(absenceTypeRepository.findAllByOrderByNameAsc())
                .thenReturn(Arrays.asList(sampleAbsenceType, absenceType2));

        List<AbsenceType> result = absenceTypeService.findAll();

        assertEquals(2, result.size());
        verify(absenceTypeRepository, times(1)).findAllByOrderByNameAsc();
    }

    @Test
    @DisplayName("Should return all absencetype ordered by name")
    void findAllShouldReturnAllAbsenceTypeOrderedByName() {
        AbsenceType absenceType1 = createAbsenceType(1L, "A", "LABEL_A");
        AbsenceType absenceType2 = createAbsenceType(2L, "C", "LABEL_C");
        when(absenceTypeRepository.findAllByOrderByNameAsc()).thenReturn(List.of(absenceType1, absenceType2));

        List<AbsenceType> result = absenceTypeService.findAll();

        assertEquals(2, result.size());
        assertEquals("A", result.get(0).getName());
        assertEquals("C", result.get(1).getName());
        verify(absenceTypeRepository, times(1)).findAllByOrderByNameAsc();
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent absencetype")
    void updateShouldThrowExceptionWhenAbsenceTypeNotFound() {
        when(absenceTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> absenceTypeService.update(99L, new AbsenceType()));
        verify(absenceTypeRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when optimistic locking fails")
    public void updateShouldThrowExceptionWhenOptimisticLockingFails() {
        existingAbsenceType.setVersion(2);
        AbsenceType outdatedAbsenceType = createAbsenceType(ABSENCE_TYPE_ID, PERSONAL_PERMISSION, "PERMX");
        outdatedAbsenceType.setVersion(1);

        setupFindByIdMock();
        when(absenceTypeRepository.existsByNameAndIdNot(PERSONAL_PERMISSION, ABSENCE_TYPE_ID)).thenReturn(false);
        when(absenceTypeRepository.save(any(AbsenceType.class)))
                .thenThrow(new ObjectOptimisticLockingFailureException(AbsenceType.class, ABSENCE_TYPE_ID));

        assertThrows(ObjectOptimisticLockingFailureException.class,
                () -> absenceTypeService.update(ABSENCE_TYPE_ID, outdatedAbsenceType));

        verify(absenceTypeRepository).findById(ABSENCE_TYPE_ID);
        verify(absenceTypeRepository).existsByNameAndIdNot(PERSONAL_PERMISSION, ABSENCE_TYPE_ID);
        verify(absenceTypeRepository).save(any(AbsenceType.class));
    }

    @Test
    @DisplayName("Should throw exception when only name changed and already exists")
    public void updateShouldThrowExceptionWhenOnlyNameChangedAndAlreadyExists() {
        AbsenceType updatedAbsenceType = createAbsenceType(PERSONAL_PERMISSION, EXISTING_LABEL);

        setupFindByIdMock();
        when(absenceTypeRepository.existsByNameAndIdNot(PERSONAL_PERMISSION, ABSENCE_TYPE_ID)).thenReturn(true);

        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class,
                () -> absenceTypeService.update(ABSENCE_TYPE_ID, updatedAbsenceType));

        assertEquals("Absence type duplication with attribute 'name' = '" + PERSONAL_PERMISSION + "'",
                exception.getMessage());

        verify(absenceTypeRepository).findById(ABSENCE_TYPE_ID);
        verify(absenceTypeRepository).existsByNameAndIdNot(PERSONAL_PERMISSION, ABSENCE_TYPE_ID);
        verify(absenceTypeRepository, never()).existsByLabelAndIdNot(anyString(), anyLong());
        verify(absenceTypeRepository, never()).save(any(AbsenceType.class));
    }

    @Test
    @DisplayName("Should throw exception when only label changed and already exists")
    public void updateShouldThrowExceptionWhenOnlyLabelChangedAndAlreadyExists() {
        AbsenceType updatedAbsenceType = createAbsenceType(VACATION_NAME, DUPLICATE_LABEL);

        setupFindByIdMock();
        when(absenceTypeRepository.existsByLabelAndIdNot(DUPLICATE_LABEL, ABSENCE_TYPE_ID)).thenReturn(true);

        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class,
                () -> absenceTypeService.update(ABSENCE_TYPE_ID, updatedAbsenceType));

        assertEquals("Absence type duplication with attribute 'label' = '" + DUPLICATE_LABEL + "'",
                exception.getMessage());

        verify(absenceTypeRepository).findById(ABSENCE_TYPE_ID);
        verify(absenceTypeRepository, never()).existsByNameAndIdNot(anyString(), anyLong());
        verify(absenceTypeRepository).existsByLabelAndIdNot(DUPLICATE_LABEL, ABSENCE_TYPE_ID);
        verify(absenceTypeRepository, never()).save(any(AbsenceType.class));
    }

    @Test
    @DisplayName("Should throw exception when both name and label already exist")
    public void updateShouldThrowExceptionWhenBothNameAndLabelAlreadyExist() {
        AbsenceType updatedAbsenceType = createAbsenceType(PERSONAL_PERMISSION, DUPLICATE_LABEL);

        setupFindByIdMock();
        when(absenceTypeRepository.existsByNameAndIdNot(PERSONAL_PERMISSION, ABSENCE_TYPE_ID)).thenReturn(true);
        when(absenceTypeRepository.existsByLabelAndIdNot(DUPLICATE_LABEL, ABSENCE_TYPE_ID)).thenReturn(true);

        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class,
                () -> absenceTypeService.update(ABSENCE_TYPE_ID, updatedAbsenceType));

        assertEquals(
                "Absence type duplication with attributes 'name' = '" + PERSONAL_PERMISSION + "' and 'label' = '"
                        + DUPLICATE_LABEL + "'",
                exception.getMessage());

        verify(absenceTypeRepository).findById(ABSENCE_TYPE_ID);
        verify(absenceTypeRepository).existsByNameAndIdNot(PERSONAL_PERMISSION, ABSENCE_TYPE_ID);
        verify(absenceTypeRepository).existsByLabelAndIdNot(DUPLICATE_LABEL, ABSENCE_TYPE_ID);
        verify(absenceTypeRepository, never()).save(any(AbsenceType.class));
    }

    @Test
    @DisplayName("Should throw exception when name already exists during creation")
    public void createShouldThrowExceptionWhenNameAlreadyExists() {
        AbsenceType newAbsenceType = createAbsenceType(PERSONAL_PERMISSION, NEW_LABEL);

        when(absenceTypeRepository.existsByName(PERSONAL_PERMISSION)).thenReturn(true);
        when(absenceTypeRepository.existsByLabel(NEW_LABEL)).thenReturn(false);

        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class,
                () -> absenceTypeService.create(newAbsenceType));

        assertEquals("Absence type duplication with attribute 'name' = '" + PERSONAL_PERMISSION + "'",
                exception.getMessage());

        verify(absenceTypeRepository).existsByName(PERSONAL_PERMISSION);
        verify(absenceTypeRepository).existsByLabel(NEW_LABEL);
        verify(absenceTypeRepository, never()).save(any(AbsenceType.class));
    }

    @Test
    @DisplayName("Should throw exception when label already exists during creation")
    public void createShouldThrowExceptionWhenLabelAlreadyExists() {
        String name = "NEW_NAME";
        AbsenceType newAbsenceType = createAbsenceType(name, EXISTING_LABEL);

        when(absenceTypeRepository.existsByName(name)).thenReturn(false);
        when(absenceTypeRepository.existsByLabel(EXISTING_LABEL)).thenReturn(true);

        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class,
                () -> absenceTypeService.create(newAbsenceType));

        assertEquals("Absence type duplication with attribute 'label' = '" + EXISTING_LABEL + "'",
                exception.getMessage());

        verify(absenceTypeRepository).existsByName(name);
        verify(absenceTypeRepository).existsByLabel(EXISTING_LABEL);
        verify(absenceTypeRepository, never()).save(any(AbsenceType.class));
    }

    @Test
    @DisplayName("Should throw exception when both name and label already exist during creation")
    public void createShouldThrowExceptionWhenBothNameAndLabelAlreadyExist() {
        AbsenceType newAbsenceType = createAbsenceType(PERSONAL_PERMISSION, EXISTING_LABEL);

        when(absenceTypeRepository.existsByName(PERSONAL_PERMISSION)).thenReturn(true);
        when(absenceTypeRepository.existsByLabel(EXISTING_LABEL)).thenReturn(true);

        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class,
                () -> absenceTypeService.create(newAbsenceType));

        assertEquals(
                "Absence type duplication with attributes 'name' = '" + PERSONAL_PERMISSION + "' and 'label' = '"
                        + EXISTING_LABEL + "'",
                exception.getMessage());

        verify(absenceTypeRepository).existsByName(PERSONAL_PERMISSION);
        verify(absenceTypeRepository).existsByLabel(EXISTING_LABEL);
        verify(absenceTypeRepository, never()).save(any(AbsenceType.class));
    }
}