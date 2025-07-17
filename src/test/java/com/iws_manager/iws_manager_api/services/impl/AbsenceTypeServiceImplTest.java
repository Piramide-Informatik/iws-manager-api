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


@ExtendWith(MockitoExtension.class)
@DisplayName("AbsenceType Service Implementation Tests")
public class AbsenceTypeServiceImplTest {

    @Mock
    private AbsenceTypeRepository absenceTypeRepository;
    @InjectMocks
    private AbsenceTypeServiceImpl absenceTypeService;
    private AbsenceType sampleAbsenceType;

    @BeforeEach
    void setUp(){
        sampleAbsenceType = new AbsenceType();
        sampleAbsenceType.setId(1L);
        sampleAbsenceType.setName("Vacation");
    }

    @Test
    @DisplayName("Should save absencetype successfully")
    void creatShouldReturnSavedAbsenceType(){
        when(absenceTypeRepository.save(any(AbsenceType.class))).thenReturn(sampleAbsenceType);

        AbsenceType result = absenceTypeService.create(sampleAbsenceType);

        assertNotNull(result);
        assertEquals("Vacation", result.getName());
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
        assertEquals("Vacation", result.get().getName());
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
        absenceType2.setName("Personal permission");

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
    public void updateShouldThrowExceptioWhenOptimisticLockingFails() {
        //Setup
        Long absenceTypeId = 1L;
        AbsenceType currentAbsenceType = new AbsenceType();
        currentAbsenceType.setId(absenceTypeId);
        currentAbsenceType.setName("Vacation");
        currentAbsenceType.setVersion(2L);

        AbsenceType outdatedAbsenceType = new AbsenceType();
        outdatedAbsenceType.setId(absenceTypeId);
        outdatedAbsenceType.setName("Personal permission");
        outdatedAbsenceType.setVersion(1L);

        when(absenceTypeRepository.findById(absenceTypeId)).thenReturn(Optional.of(currentAbsenceType));
        when(absenceTypeRepository.save(any(AbsenceType.class)))
                .thenThrow(new ObjectOptimisticLockingFailureException("Concurrent modification detected",
                        new ObjectOptimisticLockingFailureException(AbsenceType.class, absenceTypeId)));

        Exception exception = assertThrows(RuntimeException.class,
                () -> absenceTypeService.update(absenceTypeId, outdatedAbsenceType));

        assertNotNull(exception, "An exception should have been thrown");

        if(!(exception instanceof ObjectOptimisticLockingFailureException)){
            assertNotNull(exception.getCause(), "The exception should have a cause");
            assertTrue(exception.getCause() instanceof  ObjectOptimisticLockingFailureException,
                    "the cause should be ObjectOptimisticLockingFailureException");

            verify(absenceTypeRepository).findById(absenceTypeId);
            verify(absenceTypeRepository.save(any(AbsenceType.class)));
        }
    }
}
