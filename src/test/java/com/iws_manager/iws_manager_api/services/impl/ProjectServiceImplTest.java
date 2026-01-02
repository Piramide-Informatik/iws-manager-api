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
import com.iws_manager.iws_manager_api.models.ProjectPeriod;
import com.iws_manager.iws_manager_api.repositories.ProjectRepository;
import com.iws_manager.iws_manager_api.services.interfaces.ProjectPeriodService;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectPeriodService projectPeriodService;

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
    void createShouldSaveAndReturnProject() {
        when(projectRepository.save(any(Project.class))).thenReturn(testProject);
        when(projectPeriodService.createDefaultPeriodForProject(any(Project.class)))
                .thenReturn(new ProjectPeriod());

        Project result = projectService.create(testProject);

        assertNotNull(result);
        assertEquals(testProject.getId(), result.getId());
        verify(projectRepository).save(testProject);
        verify(projectPeriodService).createDefaultPeriodForProject(testProject);
    }

    @Test
    void createShouldThrowExceptionWhenProjectIsNull() {
        assertThrows(IllegalArgumentException.class, () -> projectService.create(null));
    }

    @Test
    void findByIdShouldReturnProjectWhenExists() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));

        Optional<Project> result = projectService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(testProject.getId(), result.get().getId());
    }

    @Test
    void findByIdShouldReturnEmptyWhenNotExists() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Project> result = projectService.findById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void findByIdShouldThrowExceptionWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> projectService.findById(null));
    }

    @Test
    void findAllShouldReturnAllProjectsOrderedByLabel() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectRepository.findAllByOrderByProjectLabelAsc()).thenReturn(projects);

        List<Project> result = projectService.findAll();

        assertEquals(1, result.size());
        verify(projectRepository).findAllByOrderByProjectLabelAsc();
    }

    @Test
    void updateShouldUpdateExistingProject() {
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
    void updateShouldThrowExceptionWhenProjectNotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> projectService.update(1L, new Project()));
    }

    @Test
    void updateShouldThrowExceptionWhenIdOrDetailsIsNull() {
        assertThrows(IllegalArgumentException.class, () -> projectService.update(null, new Project()));
        assertThrows(IllegalArgumentException.class, () -> projectService.update(1L, null));
    }

    @Test
    void deleteShouldDeleteProject() {
        projectService.delete(1L);

        verify(projectRepository).deleteById(1L);
    }

    @Test
    void deleteShouldThrowExceptionWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> projectService.delete(null));
    }

    // Search Methods Tests
    @Test
    void findByApprovalDateShouldReturnProjects() {
        LocalDate date = LocalDate.now();
        when(projectRepository.findByApprovalDate(date)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByApprovalDate(date);

        assertEquals(1, result.size());
        verify(projectRepository).findByApprovalDate(date);
    }

    @Test
    void findByAuthorizationDateShouldReturnProjects() {
        LocalDate date = LocalDate.now();
        when(projectRepository.findByAuthorizationDate(date)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByAuthorizationDate(date);

        assertEquals(1, result.size());
        verify(projectRepository).findByAuthorizationDate(date);
    }

    @Test
    void findByEndApprovalShouldReturnProjects() {
        LocalDate date = LocalDate.now();
        when(projectRepository.findByEndApproval(date)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByEndApproval(date);

        assertEquals(1, result.size());
        verify(projectRepository).findByEndApproval(date);
    }

    @Test
    void findByEndDateShouldReturnProjects() {
        LocalDate date = LocalDate.now();
        when(projectRepository.findByEndDate(date)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByEndDate(date);

        assertEquals(1, result.size());
        verify(projectRepository).findByEndDate(date);
    }

    @Test
    void findByStartApprovalShouldReturnProjects() {
        LocalDate date = LocalDate.now();
        when(projectRepository.findByStartApproval(date)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByStartApproval(date);

        assertEquals(1, result.size());
        verify(projectRepository).findByStartApproval(date);
    }

    @Test
    void findByStartDateShouldReturnProjects() {
        LocalDate date = LocalDate.now();
        when(projectRepository.findByStartDate(date)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByStartDate(date);

        assertEquals(1, result.size());
        verify(projectRepository).findByStartDate(date);
    }

    @Test
    void findByChanceShouldReturnProjects() {
        BigDecimal chance = BigDecimal.valueOf(80.0);
        when(projectRepository.findByChance(chance)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByChance(chance);

        assertEquals(1, result.size());
        verify(projectRepository).findByChance(chance);
    }

    @Test
    void findByFundingRateShouldReturnProjects() {
        BigDecimal rate = BigDecimal.valueOf(50.0);
        when(projectRepository.findByFundingRate(rate)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByFundingRate(rate);

        assertEquals(1, result.size());
        verify(projectRepository).findByFundingRate(rate);
    }

    @Test
    void findByHourlyRateMueuShouldReturnProjects() {
        BigDecimal rate = BigDecimal.valueOf(100.0);
        when(projectRepository.findByHourlyRateMueu(rate)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByHourlyRateMueu(rate);

        assertEquals(1, result.size());
        verify(projectRepository).findByHourlyRateMueu(rate);
    }

    @Test
    void findByMaxHoursPerMonthShouldReturnProjects() {
        BigDecimal hours = BigDecimal.valueOf(160.0);
        when(projectRepository.findByMaxHoursPerMonth(hours)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByMaxHoursPerMonth(hours);

        assertEquals(1, result.size());
        verify(projectRepository).findByMaxHoursPerMonth(hours);
    }

    @Test
    void findByMaxHoursPerYearShouldReturnProjects() {
        BigDecimal hours = BigDecimal.valueOf(1920.0);
        when(projectRepository.findByMaxHoursPerYear(hours)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByMaxHoursPerYear(hours);

        assertEquals(1, result.size());
        verify(projectRepository).findByMaxHoursPerYear(hours);
    }

    @Test
    void findByProductiveHoursPerYearShouldReturnProjects() {
        BigDecimal hours = BigDecimal.valueOf(1800.0);
        when(projectRepository.findByProductiveHoursPerYear(hours)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByProductiveHoursPerYear(hours);

        assertEquals(1, result.size());
        verify(projectRepository).findByProductiveHoursPerYear(hours);
    }

    @Test
    void findByShareResearchShouldReturnProjects() {
        BigDecimal share = BigDecimal.valueOf(30.0);
        when(projectRepository.findByShareResearch(share)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByShareResearch(share);

        assertEquals(1, result.size());
        verify(projectRepository).findByShareResearch(share);
    }

    @Test
    void findByStuffFlatShouldReturnProjects() {
        BigDecimal flat = BigDecimal.valueOf(500.0);
        when(projectRepository.findByStuffFlat(flat)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByStuffFlat(flat);

        assertEquals(1, result.size());
        verify(projectRepository).findByStuffFlat(flat);
    }

    @Test
    void findByOrderIdFueShouldReturnProjects() {
        Long orderId = 123L;
        when(projectRepository.findByOrderFueId(orderId)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByOrderIdFue(orderId);

        assertEquals(1, result.size());
        verify(projectRepository).findByOrderFueId(orderId);
    }

    @Test
    void findByOrderIdAdminShouldReturnProjects() {
        Long orderId = 456L;
        when(projectRepository.findByOrderAdminId(orderId)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByOrderIdAdmin(orderId);

        assertEquals(1, result.size());
        verify(projectRepository).findByOrderAdminId(orderId);
    }

    @Test
    void findByCommentContainingShouldReturnProjects() {
        String keyword = "test";
        when(projectRepository.findByCommentContaining(keyword)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByCommentContaining(keyword);

        assertEquals(1, result.size());
        verify(projectRepository).findByCommentContaining(keyword);
    }

    @Test
    void findByFinanceAuthorityShouldReturnProjects() {
        String authority = "Test Authority";
        when(projectRepository.findByFinanceAuthority(authority)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByFinanceAuthority(authority);

        assertEquals(1, result.size());
        verify(projectRepository).findByFinanceAuthority(authority);
    }

    @Test
    void findByFundingLabelShouldReturnProjects() {
        String label = "Test Label";
        when(projectRepository.findByFundingLabel(label)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByFundingLabel(label);

        assertEquals(1, result.size());
        verify(projectRepository).findByFundingLabel(label);
    }

    @Test
    void findByNoteContainingShouldReturnProjects() {
        String text = "test note";
        when(projectRepository.findByNoteContaining(text)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByNoteContaining(text);

        assertEquals(1, result.size());
        verify(projectRepository).findByNoteContaining(text);
    }

    @Test
    void findByProjectLabelShouldReturnProjects() {
        String label = "Test Label";
        when(projectRepository.findByProjectLabel(label)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByProjectLabel(label);

        assertEquals(1, result.size());
        verify(projectRepository).findByProjectLabel(label);
    }

    @Test
    void findByProjectNameShouldReturnProjects() {
        String name = "Test Project";
        when(projectRepository.findByProjectName(name)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByProjectName(name);

        assertEquals(1, result.size());
        verify(projectRepository).findByProjectName(name);
    }

    @Test
    void findByTitleShouldReturnProjects() {
        String title = "Test Title";
        when(projectRepository.findByTitle(title)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByTitle(title);

        assertEquals(1, result.size());
        verify(projectRepository).findByTitle(title);
    }

    @Test
    void findByCustomerIdShouldReturnProjects() {
        Long customerId = 1L;
        when(projectRepository.findByCustomerIdOrderByProjectLabelAsc(customerId))
                .thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByCustomerId(customerId);

        assertEquals(1, result.size());
        verify(projectRepository).findByCustomerIdOrderByProjectLabelAsc(customerId);
    }

    @Test
    void findByApprovalDateBetweenShouldReturnProjects() {
        LocalDate start = LocalDate.now().minusDays(1);
        LocalDate end = LocalDate.now();
        when(projectRepository.findByApprovalDateBetween(start, end)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByApprovalDateBetween(start, end);

        assertEquals(1, result.size());
        verify(projectRepository).findByApprovalDateBetween(start, end);
    }

    @Test
    void findByAuthorizationDateBeforeShouldReturnProjects() {
        LocalDate date = LocalDate.now();
        when(projectRepository.findByAuthorizationDateBefore(date)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByAuthorizationDateBefore(date);

        assertEquals(1, result.size());
        verify(projectRepository).findByAuthorizationDateBefore(date);
    }

    @Test
    void findByEndDateAfterShouldReturnProjects() {
        LocalDate date = LocalDate.now();
        when(projectRepository.findByEndDateAfter(date)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByEndDateAfter(date);

        assertEquals(1, result.size());
        verify(projectRepository).findByEndDateAfter(date);
    }

    @Test
    void findByStartDateBetweenShouldReturnProjects() {
        LocalDate start = LocalDate.now().minusDays(1);
        LocalDate end = LocalDate.now();
        when(projectRepository.findByStartDateBetween(start, end)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByStartDateBetween(start, end);

        assertEquals(1, result.size());
        verify(projectRepository).findByStartDateBetween(start, end);
    }

    @Test
    void findByChanceGreaterThanShouldReturnProjects() {
        BigDecimal chance = BigDecimal.valueOf(50.0);
        when(projectRepository.findByChanceGreaterThan(chance)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByChanceGreaterThan(chance);

        assertEquals(1, result.size());
        verify(projectRepository).findByChanceGreaterThan(chance);
    }

    @Test
    void findByFundingRateLessThanShouldReturnProjects() {
        BigDecimal rate = BigDecimal.valueOf(100.0);
        when(projectRepository.findByFundingRateLessThan(rate)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByFundingRateLessThan(rate);

        assertEquals(1, result.size());
        verify(projectRepository).findByFundingRateLessThan(rate);
    }

    @Test
    void findByHourlyRateMueuBetweenShouldReturnProjects() {
        BigDecimal min = BigDecimal.valueOf(50.0);
        BigDecimal max = BigDecimal.valueOf(150.0);
        when(projectRepository.findByHourlyRateMueuBetween(min, max)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByHourlyRateMueuBetween(min, max);

        assertEquals(1, result.size());
        verify(projectRepository).findByHourlyRateMueuBetween(min, max);
    }

    @Test
    void findByProjectNameContainingIgnoreCaseShouldReturnProjects() {
        String name = "test";
        when(projectRepository.findByProjectNameContainingIgnoreCase(name)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByProjectNameContainingIgnoreCase(name);

        assertEquals(1, result.size());
        verify(projectRepository).findByProjectNameContainingIgnoreCase(name);
    }

    @Test
    void findByFundingLabelStartingWithShouldReturnProjects() {
        String prefix = "Test";
        when(projectRepository.findByFundingLabelStartingWith(prefix)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByFundingLabelStartingWith(prefix);

        assertEquals(1, result.size());
        verify(projectRepository).findByFundingLabelStartingWith(prefix);
    }

    @Test
    void findByTitleEndingWithShouldReturnProjects() {
        String suffix = "Project";
        when(projectRepository.findByTitleEndingWith(suffix)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByTitleEndingWith(suffix);

        assertEquals(1, result.size());
        verify(projectRepository).findByTitleEndingWith(suffix);
    }

    @Test
    void findByCustomerIdOrderByStartDateDescShouldReturnProjects() {
        Long customerId = 1L;
        when(projectRepository.findByCustomerIdOrderByStartDateDesc(customerId)).thenReturn(Arrays.asList(testProject));

        List<Project> result = projectService.getProjectsByCustomerIdOrderByStartDateDesc(customerId);

        assertEquals(1, result.size());
        verify(projectRepository).findByCustomerIdOrderByStartDateDesc(customerId);
    }
}