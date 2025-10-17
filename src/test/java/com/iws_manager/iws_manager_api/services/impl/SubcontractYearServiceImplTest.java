package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.SubcontractYear;
import com.iws_manager.iws_manager_api.repositories.SubcontractYearRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubcontractYearServiceImplTest {

    @Mock
    private SubcontractYearRepository subcontractYearRepository;

    @InjectMocks
    private SubcontractYearServiceImpl subcontractYearService;

    private SubcontractYear testSubcontractYear;
    private static final Long TEST_ID = 1L;
    private static final Integer MONTHS = 6;
    private static final Long SUBCONTRACT_ID = 2L;
    private static final LocalDate testYear = LocalDate.of(2023, 1, 1);

    @BeforeEach
    public void setUp() {
        testSubcontractYear = new SubcontractYear();
        testSubcontractYear.setId(TEST_ID);
        testSubcontractYear.setMonths(MONTHS);
        testSubcontractYear.setYear(testYear);
    }

    @Test
    public void createShouldSaveAndReturnSubcontractYear() {
        when(subcontractYearRepository.save(any(SubcontractYear.class))).thenReturn(testSubcontractYear);

        SubcontractYear result = subcontractYearService.create(testSubcontractYear);

        assertNotNull(result);
        assertEquals(TEST_ID, result.getId());
        verify(subcontractYearRepository, times(1)).save(testSubcontractYear);
    }

    @Test
    public void createShouldThrowIllegalArgumentExceptionWhenSubcontractYearIsNull() {
        assertThrows(IllegalArgumentException.class, () -> subcontractYearService.create(null));
        verify(subcontractYearRepository, never()).save(any());
    }

    @Test
    public void findByIdShouldReturnSubcontractYearWhenExists() {
        when(subcontractYearRepository.findById(TEST_ID)).thenReturn(Optional.of(testSubcontractYear));

        Optional<SubcontractYear> result = subcontractYearService.findById(TEST_ID);

        assertTrue(result.isPresent());
        assertEquals(TEST_ID, result.get().getId());
        verify(subcontractYearRepository, times(1)).findById(TEST_ID);
    }

    @Test
    public void findByIdShouldReturnEmptyWhenNotExists() {
        when(subcontractYearRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        Optional<SubcontractYear> result = subcontractYearService.findById(TEST_ID);

        assertFalse(result.isPresent());
        verify(subcontractYearRepository, times(1)).findById(TEST_ID);
    }

    @Test
    public void findByIdShouldThrowIllegalArgumentExceptionWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> subcontractYearService.findById(null));
        verify(subcontractYearRepository, never()).findById(any());
    }

    @Test
    public void findAllShouldReturnListOfSubcontractYears() {
        SubcontractYear secondSubcontractYear = new SubcontractYear();
        secondSubcontractYear.setId(2L);
        secondSubcontractYear.setMonths(null);
        secondSubcontractYear.setYear(null);
        
        List<SubcontractYear> list = List.of(testSubcontractYear, secondSubcontractYear);
        
        when(subcontractYearRepository.findAll()).thenReturn(list);

        List<SubcontractYear> result = subcontractYearService.findAll();

        assertEquals(2, result.size());
        verify(subcontractYearRepository, times(1)).findAll();
    }

    @Test
    public void updateShouldSaveUpdatedSubcontractYearWhenExists() {
        SubcontractYear updatedDetails = new SubcontractYear();
        updatedDetails.setMonths(12);
        updatedDetails.setYear(LocalDate.of(2024, 1, 1));

        when(subcontractYearRepository.findById(TEST_ID)).thenReturn(Optional.of(testSubcontractYear));
        when(subcontractYearRepository.save(any(SubcontractYear.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SubcontractYear result = subcontractYearService.update(TEST_ID, updatedDetails);

        assertEquals(TEST_ID, result.getId());
        assertEquals(12, result.getMonths());
        assertEquals(LocalDate.of(2024, 1, 1), result.getYear());

        verify(subcontractYearRepository, times(1)).findById(TEST_ID);
        verify(subcontractYearRepository, times(1)).save(testSubcontractYear);
    }

    @Test
    public void updateShouldThrowRuntimeExceptionWhenNotExists() {
        when(subcontractYearRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> subcontractYearService.update(TEST_ID, testSubcontractYear));
        verify(subcontractYearRepository, times(1)).findById(TEST_ID);
        verify(subcontractYearRepository, never()).save(any());
    }

    @Test
    public void updateShouldThrowIllegalArgumentExceptionWhenParametersAreNull() {
        assertThrows(IllegalArgumentException.class, () -> subcontractYearService.update(null, testSubcontractYear));
        assertThrows(IllegalArgumentException.class, () -> subcontractYearService.update(TEST_ID, null));
        verify(subcontractYearRepository, never()).findById(any());
        verify(subcontractYearRepository, never()).save(any());
    }

    @Test
    public void deleteShouldCallRepository() {
        when(subcontractYearRepository.existsById(TEST_ID)).thenReturn(true);

        subcontractYearService.delete(TEST_ID);

        verify(subcontractYearRepository, times(1)).deleteById(TEST_ID);
    }

    @Test
    public void deleteShouldThrowIllegalArgumentExceptionWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> subcontractYearService.delete(null));
        verify(subcontractYearRepository, never()).deleteById(any());
    }

    @Test
    public void findByMonthsShouldReturnListOfSubcontractYears() {
        when(subcontractYearRepository.findByMonths(MONTHS)).thenReturn(List.of(testSubcontractYear));

        List<SubcontractYear> result = subcontractYearService.findByMonths(MONTHS);

        assertEquals(1, result.size());
        assertEquals(MONTHS, result.get(0).getMonths());
        verify(subcontractYearRepository, times(1)).findByMonths(MONTHS);
    }

    @Test
    public void findByMonthsShouldWorkWithNullParameter() {
        when(subcontractYearRepository.findByMonths(null)).thenReturn(Collections.emptyList());

        List<SubcontractYear> result = subcontractYearService.findByMonths(null);

        assertTrue(result.isEmpty());
        verify(subcontractYearRepository, times(1)).findByMonths(null);
    }

    @Test
    public void findBySubcontractIdShouldReturnListOfSubcontractYears() {
        when(subcontractYearRepository.findBySubcontractId(SUBCONTRACT_ID)).thenReturn(List.of(testSubcontractYear));

        List<SubcontractYear> result = subcontractYearService.findBySubcontractId(SUBCONTRACT_ID);

        assertEquals(1, result.size());
        verify(subcontractYearRepository, times(1)).findBySubcontractId(SUBCONTRACT_ID);
    }

    @Test
    public void findBySubcontractIdShouldWorkWithNullParameter() {
        when(subcontractYearRepository.findBySubcontractId(null)).thenReturn(Collections.emptyList());

        List<SubcontractYear> result = subcontractYearService.findBySubcontractId(null);

        assertTrue(result.isEmpty());
        verify(subcontractYearRepository, times(1)).findBySubcontractId(null);
    }

    @Test
    public void findByYearShouldReturnListOfSubcontractYears() {
        when(subcontractYearRepository.findByYear(testYear)).thenReturn(List.of(testSubcontractYear));

        List<SubcontractYear> result = subcontractYearService.findByYear(testYear);

        assertEquals(1, result.size());
        assertEquals(testYear, result.get(0).getYear());
        verify(subcontractYearRepository, times(1)).findByYear(testYear);
    }

    @Test
    public void getBySubcontractIdOrderByYearAscShouldReturnOrderedList() {
        SubcontractYear year2022 = new SubcontractYear();
        year2022.setId(10L);
        year2022.setYear(LocalDate.of(2022, 1, 1));

        SubcontractYear year2025 = new SubcontractYear();
        year2025.setId(11L);
        year2025.setYear(LocalDate.of(2025, 1, 1));

        List<SubcontractYear> orderedList = List.of(year2022, testSubcontractYear, year2025);

        when(subcontractYearRepository.findBySubcontractIdOrderByYearAsc(SUBCONTRACT_ID))
                .thenReturn(orderedList);

        List<SubcontractYear> result = subcontractYearService.getBySubcontractIdOrderByYearAsc(SUBCONTRACT_ID);

        assertEquals(3, result.size());
        assertEquals(LocalDate.of(2022, 1, 1), result.get(0).getYear());
        assertEquals(LocalDate.of(2023, 1, 1), result.get(1).getYear());
        assertEquals(LocalDate.of(2025, 1, 1), result.get(2).getYear());

        verify(subcontractYearRepository, times(1))
                .findBySubcontractIdOrderByYearAsc(SUBCONTRACT_ID);
    }

    @Test
    public void getBySubcontractIdOrderByYearAscShouldReturnEmptyListWhenNull() {
        when(subcontractYearRepository.findBySubcontractIdOrderByYearAsc(null))
                .thenReturn(Collections.emptyList());

        List<SubcontractYear> result = subcontractYearService.getBySubcontractIdOrderByYearAsc(null);

        assertTrue(result.isEmpty());
        verify(subcontractYearRepository, times(1))
                .findBySubcontractIdOrderByYearAsc(null);
    }

}