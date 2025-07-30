package com.iws_manager.iws_manager_api.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iws_manager.iws_manager_api.models.Project;
import com.iws_manager.iws_manager_api.repositories.ProjectRepository;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    private Project testProject;

    @BeforeEach
    void setUp() {
        testProject = new Project();
        testProject.setId(1L);
        testProject.setProjectName("Test Project");
        testProject.setChance(BigDecimal.valueOf(80.0));
        testProject.setStartDate(LocalDate.now());
    }

    // CRUD Operations Tests
    @Test
    void create_ShouldSaveAndReturnProject() {
        when(projectRepository.save(any(Project.class))).thenReturn(testProject);

        Project result = projectService.create(testProject);

        assertNotNull(result);
        assertEquals(testProject.getId(), result.getId());
        verify(projectRepository).save(testProject);
    }

    @Test
    void create_ShouldThrowExceptionWhenProjectIsNull() {
        assertThrows(IllegalArgumentException.class, () -> projectService.create(null));
    }

    @Test
    void findById_ShouldReturnProjectWhenExists() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));

        Optional<Project> result = projectService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(testProject.getId(), result.get().getId());
    }

    @Test
    void findById_ShouldReturnEmptyWhenNotExists() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Project> result = projectService.findById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void findById_ShouldThrowExceptionWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> projectService.findById(null));
    }

    @Test
    void findAll_ShouldReturnAllProjectsOrderedByName() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectRepository.findAllByOrderByProjectNameAsc()).thenReturn(projects);

        List<Project> result = projectService.findAll();

        assertEquals(1, result.size());
        verify(projectRepository).findAllByOrderByProjectNameAsc();
    }

    @Test
    void update_ShouldUpdateExistingProject() {
        Project updatedDetails = new Project();
        updatedDetails.setProjectName("Updated Project");
        updatedDetails.setChance(BigDecimal.valueOf(90.0));

        when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));
        when(projectRepository.save(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Project result = projectService.update(1L, updatedDetails);

        assertEquals("Updated Project", result.getProjectName());
        assertEquals(BigDecimal.valueOf(90.0), result.getChance());
        verify(projectRepository).save(testProject);
    }

    @Test
    void update_ShouldThrowExceptionWhenProjectNotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> projectService.update(1L, new Project()));
    }

    @Test
    void update_ShouldThrowExceptionWhenIdOrDetailsIsNull() {
        assertThrows(IllegalArgumentException.class, () -> projectService.update(null, new Project()));
        assertThrows(IllegalArgumentException.class, () -> projectService.update(1L, null));
    }

    @Test
    void delete_ShouldDeleteProject() {
        projectService.delete(1L);
        
        verify(projectRepository).deleteById(1L);
    }

    @Test
    void delete_ShouldThrowExceptionWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> projectService.delete(null));
    }

    // Search Methods Tests
    @Test
    void findByApprovalDate_ShouldReturnProjects() {
        LocalDate date = LocalDate.now();
        when(projectRepository.findByApprovalDate(date)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByApprovalDate(date);

        assertEquals(1, result.size());
        verify(projectRepository).findByApprovalDate(date);
    }

    @Test
    void findByAuthorizationDate_ShouldReturnProjects() {
        LocalDate date = LocalDate.now();
        when(projectRepository.findByAuthorizationDate(date)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByAuthorizationDate(date);

        assertEquals(1, result.size());
        verify(projectRepository).findByAuthorizationDate(date);
    }

    @Test
    void findByEndApproval_ShouldReturnProjects() {
        LocalDate date = LocalDate.now();
        when(projectRepository.findByEndApproval(date)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByEndApproval(date);

        assertEquals(1, result.size());
        verify(projectRepository).findByEndApproval(date);
    }

    @Test
    void findByEndDate_ShouldReturnProjects() {
        LocalDate date = LocalDate.now();
        when(projectRepository.findByEndDate(date)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByEndDate(date);

        assertEquals(1, result.size());
        verify(projectRepository).findByEndDate(date);
    }

    @Test
    void findByStartApproval_ShouldReturnProjects() {
        LocalDate date = LocalDate.now();
        when(projectRepository.findByStartApproval(date)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByStartApproval(date);

        assertEquals(1, result.size());
        verify(projectRepository).findByStartApproval(date);
    }

    @Test
    void findByStartDate_ShouldReturnProjects() {
        LocalDate date = LocalDate.now();
        when(projectRepository.findByStartDate(date)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByStartDate(date);

        assertEquals(1, result.size());
        verify(projectRepository).findByStartDate(date);
    }

    @Test
    void findByChance_ShouldReturnProjects() {
        BigDecimal chance = BigDecimal.valueOf(80.0);
        when(projectRepository.findByChance(chance)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByChance(chance);

        assertEquals(1, result.size());
        verify(projectRepository).findByChance(chance);
    }

    @Test
    void findByFundingRate_ShouldReturnProjects() {
        BigDecimal rate = BigDecimal.valueOf(50.0);
        when(projectRepository.findByFundingRate(rate)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByFundingRate(rate);

        assertEquals(1, result.size());
        verify(projectRepository).findByFundingRate(rate);
    }

    @Test
    void findByHourlyRateMueu_ShouldReturnProjects() {
        BigDecimal rate = BigDecimal.valueOf(100.0);
        when(projectRepository.findByHourlyRateMueu(rate)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByHourlyRateMueu(rate);

        assertEquals(1, result.size());
        verify(projectRepository).findByHourlyRateMueu(rate);
    }

    @Test
    void findByMaxHoursPerMonth_ShouldReturnProjects() {
        BigDecimal hours = BigDecimal.valueOf(160.0);
        when(projectRepository.findByMaxHoursPerMonth(hours)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByMaxHoursPerMonth(hours);

        assertEquals(1, result.size());
        verify(projectRepository).findByMaxHoursPerMonth(hours);
    }

    @Test
    void findByMaxHoursPerYear_ShouldReturnProjects() {
        BigDecimal hours = BigDecimal.valueOf(1920.0);
        when(projectRepository.findByMaxHoursPerYear(hours)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByMaxHoursPerYear(hours);

        assertEquals(1, result.size());
        verify(projectRepository).findByMaxHoursPerYear(hours);
    }

    @Test
    void findByProductiveHoursPerYear_ShouldReturnProjects() {
        BigDecimal hours = BigDecimal.valueOf(1800.0);
        when(projectRepository.findByProductiveHoursPerYear(hours)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByProductiveHoursPerYear(hours);

        assertEquals(1, result.size());
        verify(projectRepository).findByProductiveHoursPerYear(hours);
    }

    @Test
    void findByShareResearch_ShouldReturnProjects() {
        BigDecimal share = BigDecimal.valueOf(30.0);
        when(projectRepository.findByShareResearch(share)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByShareResearch(share);

        assertEquals(1, result.size());
        verify(projectRepository).findByShareResearch(share);
    }

    @Test
    void findByStuffFlat_ShouldReturnProjects() {
        BigDecimal flat = BigDecimal.valueOf(500.0);
        when(projectRepository.findByStuffFlat(flat)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByStuffFlat(flat);

        assertEquals(1, result.size());
        verify(projectRepository).findByStuffFlat(flat);
    }

    @Test
    void findByOrderIdFue_ShouldReturnProjects() {
        Integer orderId = 123;
        when(projectRepository.findByOrderIdFue(orderId)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByOrderIdFue(orderId);

        assertEquals(1, result.size());
        verify(projectRepository).findByOrderIdFue(orderId);
    }

    @Test
    void findByOrderIdAdmin_ShouldReturnProjects() {
        Integer orderId = 456;
        when(projectRepository.findByOrderIdAdmin(orderId)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByOrderIdAdmin(orderId);

        assertEquals(1, result.size());
        verify(projectRepository).findByOrderIdAdmin(orderId);
    }

    @Test
    void findByCommentContaining_ShouldReturnProjects() {
        String keyword = "test";
        when(projectRepository.findByCommentContaining(keyword)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByCommentContaining(keyword);

        assertEquals(1, result.size());
        verify(projectRepository).findByCommentContaining(keyword);
    }

    @Test
    void findByFinanceAuthority_ShouldReturnProjects() {
        String authority = "Test Authority";
        when(projectRepository.findByFinanceAuthority(authority)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByFinanceAuthority(authority);

        assertEquals(1, result.size());
        verify(projectRepository).findByFinanceAuthority(authority);
    }

    @Test
    void findByFundingLabel_ShouldReturnProjects() {
        String label = "Test Label";
        when(projectRepository.findByFundingLabel(label)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByFundingLabel(label);

        assertEquals(1, result.size());
        verify(projectRepository).findByFundingLabel(label);
    }

    @Test
    void findByNoteContaining_ShouldReturnProjects() {
        String text = "test note";
        when(projectRepository.findByNoteContaining(text)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByNoteContaining(text);

        assertEquals(1, result.size());
        verify(projectRepository).findByNoteContaining(text);
    }

    @Test
    void findByProjectLabel_ShouldReturnProjects() {
        String label = "Test Label";
        when(projectRepository.findByProjectLabel(label)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByProjectLabel(label);

        assertEquals(1, result.size());
        verify(projectRepository).findByProjectLabel(label);
    }

    @Test
    void findByProjectName_ShouldReturnProjects() {
        String name = "Test Project";
        when(projectRepository.findByProjectName(name)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByProjectName(name);

        assertEquals(1, result.size());
        verify(projectRepository).findByProjectName(name);
    }

    @Test
    void findByTitle_ShouldReturnProjects() {
        String title = "Test Title";
        when(projectRepository.findByTitle(title)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByTitle(title);

        assertEquals(1, result.size());
        verify(projectRepository).findByTitle(title);
    }

    @Test
    void findByCustomerId_ShouldReturnProjects() {
        Long customerId = 1L;
        when(projectRepository.findByCustomerId(customerId)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByCustomerId(customerId);

        assertEquals(1, result.size());
        verify(projectRepository).findByCustomerId(customerId);
    }

    @Test
    void findByApprovalDateBetween_ShouldReturnProjects() {
        LocalDate start = LocalDate.now().minusDays(1);
        LocalDate end = LocalDate.now();
        when(projectRepository.findByApprovalDateBetween(start, end)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByApprovalDateBetween(start, end);

        assertEquals(1, result.size());
        verify(projectRepository).findByApprovalDateBetween(start, end);
    }

    @Test
    void findByAuthorizationDateBefore_ShouldReturnProjects() {
        LocalDate date = LocalDate.now();
        when(projectRepository.findByAuthorizationDateBefore(date)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByAuthorizationDateBefore(date);

        assertEquals(1, result.size());
        verify(projectRepository).findByAuthorizationDateBefore(date);
    }

    @Test
    void findByEndDateAfter_ShouldReturnProjects() {
        LocalDate date = LocalDate.now();
        when(projectRepository.findByEndDateAfter(date)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByEndDateAfter(date);

        assertEquals(1, result.size());
        verify(projectRepository).findByEndDateAfter(date);
    }

    @Test
    void findByStartDateBetween_ShouldReturnProjects() {
        LocalDate start = LocalDate.now().minusDays(1);
        LocalDate end = LocalDate.now();
        when(projectRepository.findByStartDateBetween(start, end)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByStartDateBetween(start, end);

        assertEquals(1, result.size());
        verify(projectRepository).findByStartDateBetween(start, end);
    }

    @Test
    void findByChanceGreaterThan_ShouldReturnProjects() {
        BigDecimal chance = BigDecimal.valueOf(50.0);
        when(projectRepository.findByChanceGreaterThan(chance)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByChanceGreaterThan(chance);

        assertEquals(1, result.size());
        verify(projectRepository).findByChanceGreaterThan(chance);
    }

    @Test
    void findByFundingRateLessThan_ShouldReturnProjects() {
        BigDecimal rate = BigDecimal.valueOf(100.0);
        when(projectRepository.findByFundingRateLessThan(rate)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByFundingRateLessThan(rate);

        assertEquals(1, result.size());
        verify(projectRepository).findByFundingRateLessThan(rate);
    }

    @Test
    void findByHourlyRateMueuBetween_ShouldReturnProjects() {
        BigDecimal min = BigDecimal.valueOf(50.0);
        BigDecimal max = BigDecimal.valueOf(150.0);
        when(projectRepository.findByHourlyRateMueuBetween(min, max)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByHourlyRateMueuBetween(min, max);

        assertEquals(1, result.size());
        verify(projectRepository).findByHourlyRateMueuBetween(min, max);
    }

    @Test
    void findByProjectNameContainingIgnoreCase_ShouldReturnProjects() {
        String name = "test";
        when(projectRepository.findByProjectNameContainingIgnoreCase(name)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByProjectNameContainingIgnoreCase(name);

        assertEquals(1, result.size());
        verify(projectRepository).findByProjectNameContainingIgnoreCase(name);
    }

    @Test
    void findByFundingLabelStartingWith_ShouldReturnProjects() {
        String prefix = "Test";
        when(projectRepository.findByFundingLabelStartingWith(prefix)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByFundingLabelStartingWith(prefix);

        assertEquals(1, result.size());
        verify(projectRepository).findByFundingLabelStartingWith(prefix);
    }

    @Test
    void findByTitleEndingWith_ShouldReturnProjects() {
        String suffix = "Project";
        when(projectRepository.findByTitleEndingWith(suffix)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByTitleEndingWith(suffix);

        assertEquals(1, result.size());
        verify(projectRepository).findByTitleEndingWith(suffix);
    }

    @Test
    void findByCustomerIdOrderByStartDateDesc_ShouldReturnProjects() {
        Long customerId = 1L;
        when(projectRepository.findByCustomerIdOrderByStartDateDesc(customerId)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.findByCustomerIdOrderByStartDateDesc(customerId);

        assertEquals(1, result.size());
        verify(projectRepository).findByCustomerIdOrderByStartDateDesc(customerId);
    }
}