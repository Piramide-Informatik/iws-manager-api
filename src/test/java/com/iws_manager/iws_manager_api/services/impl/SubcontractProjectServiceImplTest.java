package com.iws_manager.iws_manager_api.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.SubcontractProject;
import com.iws_manager.iws_manager_api.repositories.SubcontractProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SubcontractProjectServiceImplTest {

    @Mock
    private SubcontractProjectRepository subcontractProjectRepository;

    @InjectMocks
    private SubcontractProjectServiceImpl subcontractProjectService;

    private SubcontractProject project1;
    private SubcontractProject project2;

    @BeforeEach
    void setUp() {
        project1 = new SubcontractProject();
        project1.setId(1L);
        project1.setMonths(6);
        project1.setAmount("1000.00");
        project1.setShare(new BigDecimal("50.00"));
        project1.setYear(LocalDate.of(2023, 1, 1));

        project2 = new SubcontractProject();
        project2.setId(2L);
        project2.setMonths(12);
        project2.setAmount("2000.00");
        project2.setShare(new BigDecimal("75.00"));
        project2.setYear(LocalDate.of(2024, 1, 1));
    }

    @Test
    void create_ShouldReturnSavedProject() {
        when(subcontractProjectRepository.save(project1)).thenReturn(project1);

        SubcontractProject result = subcontractProjectService.create(project1);

        assertNotNull(result);
        assertEquals(project1.getId(), result.getId());
        verify(subcontractProjectRepository).save(project1);
    }

    @Test
    void findById_ShouldReturnProjectWhenExists() {
        when(subcontractProjectRepository.findById(1L)).thenReturn(Optional.of(project1));

        Optional<SubcontractProject> result = subcontractProjectService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(project1.getId(), result.get().getId());
    }

    @Test
    void findById_ShouldReturnEmptyWhenNotExists() {
        when(subcontractProjectRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<SubcontractProject> result = subcontractProjectService.findById(99L);

        assertFalse(result.isPresent());
    }

    @Test
    void findAll_ShouldReturnAllProjects() {
        when(subcontractProjectRepository.findAll()).thenReturn(Arrays.asList(project1, project2));

        List<SubcontractProject> result = subcontractProjectService.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(project1));
        assertTrue(result.contains(project2));
    }

    @Test
    void update_ShouldUpdateExistingProject() {
        SubcontractProject updatedDetails = new SubcontractProject();
        updatedDetails.setMonths(24);
        updatedDetails.setAmount("3000.00");
        updatedDetails.setShare(new BigDecimal("100.00"));

        when(subcontractProjectRepository.findById(1L)).thenReturn(Optional.of(project1));
        when(subcontractProjectRepository.save(any(SubcontractProject.class))).thenReturn(project1);

        SubcontractProject result = subcontractProjectService.update(1L, updatedDetails);

        assertEquals(24, result.getMonths());
        assertEquals("3000.00", result.getAmount());
        assertEquals(new BigDecimal("100.00"), result.getShare());
    }

    @Test
    void update_ShouldThrowWhenProjectNotFound() {
        when(subcontractProjectRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> 
            subcontractProjectService.update(99L, new SubcontractProject()));
    }

    @Test
    void delete_ShouldCallRepositoryDelete() {
        doNothing().when(subcontractProjectRepository).deleteById(1L);

        subcontractProjectService.delete(1L);

        verify(subcontractProjectRepository).deleteById(1L);
    }

    // Query methods tests
    @Test
    void getByMonths_ShouldReturnMatchingProjects() {
        when(subcontractProjectRepository.findByMonths(6)).thenReturn(Arrays.asList(project1));

        List<SubcontractProject> result = subcontractProjectService.getByMonths(6);

        assertEquals(1, result.size());
        assertEquals(project1, result.get(0));
    }

    @Test
    void getByAmount_ShouldReturnMatchingProjects() {
        when(subcontractProjectRepository.findByAmount("1000.00")).thenReturn(Arrays.asList(project1));

        List<SubcontractProject> result = subcontractProjectService.getByAmount("1000.00");

        assertEquals(1, result.size());
        assertEquals(project1, result.get(0));
    }

    @Test
    void getByShare_ShouldReturnMatchingProjects() {
        when(subcontractProjectRepository.findByShare(new BigDecimal("50.00"))).thenReturn(Arrays.asList(project1));

        List<SubcontractProject> result = subcontractProjectService.getByShare(new BigDecimal("50.00"));

        assertEquals(1, result.size());
        assertEquals(project1, result.get(0));
    }

    @Test
    void getByYear_ShouldReturnMatchingProjects() {
        LocalDate year = LocalDate.of(2023, 1, 1);
        when(subcontractProjectRepository.findByYear(year)).thenReturn(Arrays.asList(project1));

        List<SubcontractProject> result = subcontractProjectService.getByYear(year);

        assertEquals(1, result.size());
        assertEquals(project1, result.get(0));
    }

    @Test
    void getBySubcontractYearId_ShouldReturnMatchingProjects() {
        when(subcontractProjectRepository.findBySubcontractYearId(1L)).thenReturn(Arrays.asList(project1));

        List<SubcontractProject> result = subcontractProjectService.getBySubcontractYearId(1L);

        assertEquals(1, result.size());
        assertEquals(project1, result.get(0));
    }

    @Test
    void getByProjectId_ShouldReturnMatchingProjects() {
        when(subcontractProjectRepository.findByProjectId(1L)).thenReturn(Arrays.asList(project1));

        List<SubcontractProject> result = subcontractProjectService.getByProjectId(1L);

        assertEquals(1, result.size());
        assertEquals(project1, result.get(0));
    }

    @Test
    void getBySubcontractId_ShouldReturnMatchingProjects() {
        when(subcontractProjectRepository.findBySubcontractId(1L)).thenReturn(Arrays.asList(project1));

        List<SubcontractProject> result = subcontractProjectService.getBySubcontractId(1L);

        assertEquals(1, result.size());
        assertEquals(project1, result.get(0));
    }

    @Test
    void getByShareBetween_ShouldReturnMatchingProjects() {
        BigDecimal start = new BigDecimal("40.00");
        BigDecimal end = new BigDecimal("60.00");
        when(subcontractProjectRepository.findByShareBetween(start, end)).thenReturn(Arrays.asList(project1));

        List<SubcontractProject> result = subcontractProjectService.getByShareBetween(start, end);

        assertEquals(1, result.size());
        assertEquals(project1, result.get(0));
    }

    @Test
    void getByMonthsGreaterThan_ShouldReturnMatchingProjects() {
        when(subcontractProjectRepository.findByMonthsGreaterThan(6)).thenReturn(Arrays.asList(project2));

        List<SubcontractProject> result = subcontractProjectService.getByMonthsGreaterThan(6);

        assertEquals(1, result.size());
        assertEquals(project2, result.get(0));
    }

    @Test
    void getByMonthsLessThan_ShouldReturnMatchingProjects() {
        when(subcontractProjectRepository.findByMonthsLessThan(12)).thenReturn(Arrays.asList(project1));

        List<SubcontractProject> result = subcontractProjectService.getByMonthsLessThan(12);

        assertEquals(1, result.size());
        assertEquals(project1, result.get(0));
    }

    @Test
    void getByYearAfter_ShouldReturnMatchingProjects() {
        LocalDate date = LocalDate.of(2022, 12, 31);
        when(subcontractProjectRepository.findByYearAfter(date)).thenReturn(Arrays.asList(project1, project2));

        List<SubcontractProject> result = subcontractProjectService.getByYearAfter(date);

        assertEquals(2, result.size());
        assertTrue(result.contains(project1));
        assertTrue(result.contains(project2));
    }

    @Test
    void getByYearBefore_ShouldReturnMatchingProjects() {
        LocalDate date = LocalDate.of(2024, 12, 31);
        when(subcontractProjectRepository.findByYearBefore(date)).thenReturn(Arrays.asList(project1, project2));

        List<SubcontractProject> result = subcontractProjectService.getByYearBefore(date);

        assertEquals(2, result.size());
        assertTrue(result.contains(project1));
        assertTrue(result.contains(project2));
    }
}
