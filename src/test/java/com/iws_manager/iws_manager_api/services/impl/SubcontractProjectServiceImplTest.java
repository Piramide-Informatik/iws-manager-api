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

    private static final BigDecimal TEST_AMOUNT = new BigDecimal("1000.00");
    private static final BigDecimal TEST_SHARE = new BigDecimal("50.00");

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
        project1.setAmount(TEST_AMOUNT);
        project1.setShare(TEST_SHARE);

        project2 = new SubcontractProject();
        project2.setId(2L);
        project2.setAmount(new BigDecimal("2000.00"));
        project2.setShare(new BigDecimal("75.00"));
    }

    @Test
    void createShouldReturnSavedProject() {
        when(subcontractProjectRepository.save(project1)).thenReturn(project1);

        SubcontractProject result = subcontractProjectService.create(project1);

        assertNotNull(result);
        assertEquals(project1.getId(), result.getId());
        verify(subcontractProjectRepository).save(project1);
    }

    @Test
    void findByIdShouldReturnProjectWhenExists() {
        when(subcontractProjectRepository.findById(1L)).thenReturn(Optional.of(project1));

        Optional<SubcontractProject> result = subcontractProjectService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(project1.getId(), result.get().getId());
    }

    @Test
    void findByIdShouldReturnEmptyWhenNotExists() {
        when(subcontractProjectRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<SubcontractProject> result = subcontractProjectService.findById(99L);

        assertFalse(result.isPresent());
    }

    @Test
    void findAllShouldReturnAllProjects() {
        when(subcontractProjectRepository.findAll()).thenReturn(Arrays.asList(project1, project2));

        List<SubcontractProject> result = subcontractProjectService.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(project1));
        assertTrue(result.contains(project2));
    }

    @Test
    void updateShouldUpdateExistingProject() {
        SubcontractProject updatedDetails = new SubcontractProject();
        updatedDetails.setAmount(new BigDecimal("3000.00"));
        updatedDetails.setShare(new BigDecimal("100.00"));

        when(subcontractProjectRepository.findById(1L)).thenReturn(Optional.of(project1));
        when(subcontractProjectRepository.save(any(SubcontractProject.class))).thenReturn(project1);

        SubcontractProject result = subcontractProjectService.update(1L, updatedDetails);

        assertEquals(new BigDecimal("3000.00"), result.getAmount());
        assertEquals(new BigDecimal("100.00"), result.getShare());
    }

    @Test
    void updateShouldThrowWhenProjectNotFound() {
        when(subcontractProjectRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> 
            subcontractProjectService.update(99L, new SubcontractProject()));
    }

    @Test
    void deleteShouldCallRepositoryDelete() {
        when(subcontractProjectRepository.existsById(1L)).thenReturn(true);

        subcontractProjectService.delete(1L);

        verify(subcontractProjectRepository).deleteById(1L);
    }

    @Test
    void getByAmountShouldReturnMatchingProjects() {
        when(subcontractProjectRepository.findByAmount(TEST_AMOUNT)).thenReturn(Arrays.asList(project1));

        List<SubcontractProject> result = subcontractProjectService.getByAmount(TEST_AMOUNT);

        assertEquals(1, result.size());
        assertEquals(project1, result.get(0));
    }

    @Test
    void getByShareShouldReturnMatchingProjects() {
        when(subcontractProjectRepository.findByShare(TEST_SHARE)).thenReturn(Arrays.asList(project1));

        List<SubcontractProject> result = subcontractProjectService.getByShare(TEST_SHARE);

        assertEquals(1, result.size());
        assertEquals(project1, result.get(0));
    }

    @Test
    void getBySubcontractYearIdShouldReturnMatchingProjects() {
        when(subcontractProjectRepository.findBySubcontractYearId(1L)).thenReturn(Arrays.asList(project1));

        List<SubcontractProject> result = subcontractProjectService.getBySubcontractYearId(1L);

        assertEquals(1, result.size());
        assertEquals(project1, result.get(0));
    }

    @Test
    void getByProjectIdShouldReturnMatchingProjects() {
        when(subcontractProjectRepository.findByProjectId(1L)).thenReturn(Arrays.asList(project1));

        List<SubcontractProject> result = subcontractProjectService.getByProjectId(1L);

        assertEquals(1, result.size());
        assertEquals(project1, result.get(0));
    }

    @Test
    void getBySubcontractIdShouldReturnMatchingProjects() {
        when(subcontractProjectRepository.findBySubcontractId(1L)).thenReturn(Arrays.asList(project1));

        List<SubcontractProject> result = subcontractProjectService.getBySubcontractId(1L);

        assertEquals(1, result.size());
        assertEquals(project1, result.get(0));
    }

    @Test
    void getByShareBetweenShouldReturnMatchingProjects() {
        BigDecimal start = new BigDecimal("40.00");
        BigDecimal end = new BigDecimal("60.00");
        when(subcontractProjectRepository.findByShareBetween(start, end)).thenReturn(Arrays.asList(project1));

        List<SubcontractProject> result = subcontractProjectService.getByShareBetween(start, end);

        assertEquals(1, result.size());
        assertEquals(project1, result.get(0));
    }
}
