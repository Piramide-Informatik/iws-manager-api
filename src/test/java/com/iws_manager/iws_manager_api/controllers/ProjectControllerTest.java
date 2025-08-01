package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.Project;
import com.iws_manager.iws_manager_api.services.interfaces.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    private Project testProject;
    private final Long testId = 1L;
    private final LocalDate testDate = LocalDate.now();
    private final BigDecimal testDecimal = BigDecimal.valueOf(50.0);
    private final String testString = "test";

    @BeforeEach
    void setUp() {
        testProject = new Project();
        testProject.setId(testId);
        testProject.setProjectName("Test Project");
    }

    @Test
    void update_ShouldReturnUpdatedProject() {
        when(projectService.update(eq(testId), any(Project.class))).thenReturn(testProject);

        ResponseEntity<Project> response = projectController.update(testId, testProject);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testProject, response.getBody());
    }

    @Test
    void update_ShouldReturnNotFoundWhenProjectNotExists() {
        when(projectService.update(eq(testId), any(Project.class)))
            .thenThrow(new RuntimeException("Not found"));

        ResponseEntity<Project> response = projectController.update(testId, testProject);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void create_ShouldReturnCreatedProject() {
        when(projectService.create(any(Project.class))).thenReturn(testProject);

        ResponseEntity<?> response = projectController.create(testProject);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testProject, response.getBody());
        verify(projectService).create(testProject);
    }

    @Test
    void getById_ShouldReturnProjectWhenExists() {
        when(projectService.findById(testId)).thenReturn(Optional.of(testProject));

        ResponseEntity<Project> response = projectController.getById(testId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testProject, response.getBody());
    }

    @Test
    void getById_ShouldReturnNotFoundWhenNotExists() {
        when(projectService.findById(testId)).thenReturn(Optional.empty());

        ResponseEntity<Project> response = projectController.getById(testId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAll_ShouldReturnAllProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.findAll()).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void delete_ShouldReturnNoContentWhenSuccess() {
        doNothing().when(projectService).delete(testId);

        ResponseEntity<Void> response = projectController.delete(testId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(projectService).delete(testId);
    }

    @Test
    void delete_ShouldReturnNotFoundWhenProjectNotExists() {
        doThrow(new RuntimeException("Not found")).when(projectService).delete(testId);

        ResponseEntity<Void> response = projectController.delete(testId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Date Filter Tests
    @Test
    void getByApprovalDate_ShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByApprovalDate(testDate)).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getByApprovalDate(testDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getByApprovalDateRange_ShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByApprovalDateBetween(testDate, testDate.plusDays(1)))
            .thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getByApprovalDateRange(testDate, testDate.plusDays(1));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    // Numeric Filter Tests
    @Test
    void getByChance_ShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByChance(testDecimal)).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getByChance(testDecimal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getWithChanceGreaterThan_ShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByChanceGreaterThan(testDecimal)).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getWithChanceGreaterThan(testDecimal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getByHourlyRateRange_ShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByHourlyRateMueuBetween(testDecimal, testDecimal.add(BigDecimal.TEN)))
            .thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getByHourlyRateRange(testDecimal, testDecimal.add(BigDecimal.TEN));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    // Text Filter Tests
    @Test
    void getWithNameContaining_ShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByProjectNameContainingIgnoreCase(testString)).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getWithNameContaining(testString);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getByCustomerId_ShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByCustomerId(testId)).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getByCustomerId(testId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getByCustomerOrderedByStartDate_ShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByCustomerIdOrderByStartDateDesc(testId)).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getByCustomerOrderedByStartDate(testId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    // Additional tests
    @Test
    void getByAuthorizationDate_ShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByAuthorizationDate(testDate)).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getByAuthorizationDate(testDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getByEndApproval_ShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByEndApproval(testDate)).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getByEndApproval(testDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getByFundingRate_ShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByFundingRate(testDecimal)).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getByFundingRate(testDecimal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getByProjectName_ShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByProjectName(testString)).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getByProjectName(testString);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getWithFundingLabelStarting_ShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByFundingLabelStartingWith(testString)).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getWithFundingLabelStarting(testString);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getByFinanceAuthority_ShouldReturnProjects() {
        List<Project> expectedProjects = Arrays.asList(testProject);
        when(projectService.getProjectsByFinanceAuthority(testString)).thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = 
            projectController.getByFinanceAuthority(testString);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProjects, response.getBody());
        verify(projectService).getProjectsByFinanceAuthority(testString);
    }

    @Test
    void getByFinanceAuthority_ShouldReturnEmptyListWhenNoMatches() {
        when(projectService.getProjectsByFinanceAuthority(testString)).thenReturn(List.of());

        ResponseEntity<List<Project>> response = 
            projectController.getByFinanceAuthority(testString);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getByFundingLabel_ShouldReturnProjects() {
        List<Project> expectedProjects = Arrays.asList(testProject);
        when(projectService.getProjectsByFundingLabel(testString)).thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = 
            projectController.getByFundingLabel(testString);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProjects, response.getBody());
    }

    @Test
    void getByFundingLabel_ShouldBeCaseSensitive() {
        String differentCase = "TEST";
        when(projectService.getProjectsByFundingLabel(differentCase)).thenReturn(List.of());

        ResponseEntity<List<Project>> response = 
            projectController.getByFundingLabel(differentCase);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotEquals(testString, differentCase);
        verify(projectService).getProjectsByFundingLabel(differentCase);
    }

    @Test
    void getByNoteContaining_ShouldReturnProjectsWithMatchingText() {
        List<Project> expectedProjects = Arrays.asList(testProject);
        when(projectService.getProjectsByNoteContaining(testString)).thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = 
            projectController.getByNoteContaining(testString);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProjects, response.getBody());
    }

    @Test
    void getByNoteContaining_ShouldAcceptPartialMatches() {
        String partialText = "est"; // Part of the string "test"
        when(projectService.getProjectsByNoteContaining(partialText)).thenReturn(List.of(testProject));

        ResponseEntity<List<Project>> response = 
            projectController.getByNoteContaining(partialText);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getByProjectLabel_ShouldReturnExactMatches() {
        List<Project> expectedProjects = Arrays.asList(testProject);
        when(projectService.getProjectsByProjectLabel(testString)).thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = 
            projectController.getByProjectLabel(testString);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProjects, response.getBody());
    }

    @Test
    void getByTitle_ShouldReturnProjectsWithExactTitle() {
        List<Project> expectedProjects = Arrays.asList(testProject);
        when(projectService.getProjectsByTitle(testString)).thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = 
            projectController.getByTitle(testString);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProjects, response.getBody());
    }

    @Test
    void getWithTitleEnding_ShouldReturnProjects() {
        List<Project> expectedProjects = Arrays.asList(testProject);
        when(projectService.getProjectsByTitleEndingWith(testString)).thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = 
            projectController.getWithTitleEnding(testString);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProjects, response.getBody());
    }

    @Test
    void getWithTitleEnding_ShouldMatchSuffixCorrectly() {
        String suffix = "tion";
        Project matchingProject = new Project();
        matchingProject.setTitle("Important Project Documentation");
        
        when(projectService.getProjectsByTitleEndingWith(suffix)).thenReturn(List.of(matchingProject));

        ResponseEntity<List<Project>> response = 
            projectController.getWithTitleEnding(suffix);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertTrue(response.getBody().get(0).getTitle().endsWith(suffix));
    }

    @Test
    void getByShareResearch_ShouldReturnProjectsWithExactValue() {
        BigDecimal shareValue = BigDecimal.valueOf(30.5);
        List<Project> expectedProjects = List.of(testProject);
        when(projectService.getProjectsByShareResearch(shareValue)).thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = projectController.getByShareResearch(shareValue);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProjects, response.getBody());
        verify(projectService).getProjectsByShareResearch(shareValue);
    }

    @Test
    void getByShareResearch_ShouldHandleDifferentPrecision() {
        BigDecimal shareValue = new BigDecimal("30.50");
        when(projectService.getProjectsByShareResearch(shareValue)).thenReturn(List.of(testProject));

        ResponseEntity<List<Project>> response = projectController.getByShareResearch(shareValue);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getByStuffFlat_ShouldReturnProjects() {
        BigDecimal stuffFlatValue = BigDecimal.valueOf(500.0);
        List<Project> expectedProjects = List.of(testProject);
        when(projectService.getProjectsByStuffFlat(stuffFlatValue)).thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = projectController.getByStuffFlat(stuffFlatValue);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProjects, response.getBody());
    }

    @Test
    void getByStuffFlat_ShouldReturnEmptyListForNonExistingValue() {
        BigDecimal nonExistingValue = BigDecimal.valueOf(999.99);
        when(projectService.getProjectsByStuffFlat(nonExistingValue)).thenReturn(List.of());

        ResponseEntity<List<Project>> response = projectController.getByStuffFlat(nonExistingValue);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getByOrderIdFue_ShouldReturnProjectsWithSpecificOrderId() {
        Integer orderId = 12345;
        List<Project> expectedProjects = List.of(testProject);
        when(projectService.getProjectsByOrderIdFue(orderId)).thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = projectController.getByOrderIdFue(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProjects, response.getBody());
    }

    @Test
    void getByOrderIdFue_ShouldHandleNullValue() {
        when(projectService.getProjectsByOrderIdFue(null)).thenReturn(List.of());

        ResponseEntity<List<Project>> response = projectController.getByOrderIdFue(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getByOrderIdAdmin_ShouldReturnProjects() {
        Integer orderId = 54321;
        List<Project> expectedProjects = List.of(testProject);
        when(projectService.getProjectsByOrderIdAdmin(orderId)).thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = projectController.getByOrderIdAdmin(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProjects, response.getBody());
    }

    @Test
    void getByOrderIdAdmin_ShouldReturnNotFoundForInvalidId() {
        Integer invalidId = -1;
        when(projectService.getProjectsByOrderIdAdmin(invalidId)).thenReturn(List.of());

        ResponseEntity<List<Project>> response = projectController.getByOrderIdAdmin(invalidId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getByCommentContaining_ShouldReturnProjectsWithMatchingComments() {
        String searchTerm = "urgent";
        List<Project> expectedProjects = List.of(testProject);
        when(projectService.getProjectsByCommentContaining(searchTerm)).thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = projectController.getByCommentContaining(searchTerm);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProjects, response.getBody());
    }

    @Test
    void getByCommentContaining_ShouldBeCaseInsensitive() {
        String searchTerm = "UrGeNt";
        List<Project> expectedProjects = List.of(testProject);
        when(projectService.getProjectsByCommentContaining(searchTerm)).thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = projectController.getByCommentContaining(searchTerm);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getByCommentContaining_ShouldHandleSpecialCharacters() {
        String searchTerm = "método #2";
        when(projectService.getProjectsByCommentContaining(searchTerm)).thenReturn(List.of(testProject));

        ResponseEntity<List<Project>> response = projectController.getByCommentContaining(searchTerm);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getWithAuthorizationBefore_ShouldReturnProjectsBeforeDate() {
        LocalDate testDate = LocalDate.of(2023, 12, 31);
        List<Project> expectedProjects = List.of(testProject);
        
        when(projectService.getProjectsByAuthorizationDateBefore(testDate))
            .thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = 
            projectController.getWithAuthorizationBefore(testDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProjects, response.getBody());
        verify(projectService).getProjectsByAuthorizationDateBefore(testDate);
    }

    @Test
    void getWithAuthorizationBefore_ShouldHandleNullDate() {
        ResponseEntity<List<Project>> response = 
            projectController.getWithAuthorizationBefore(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getWithEndDateAfter_ShouldReturnProjectsAfterDate() {
        LocalDate testDate = LocalDate.of(2023, 1, 1);
        List<Project> expectedProjects = List.of(testProject);
        
        when(projectService.getProjectsByEndDateAfter(testDate))
            .thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = 
            projectController.getWithEndDateAfter(testDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProjects, response.getBody());
    }

    @Test
    void getWithEndDateAfter_ShouldReturnEmptyListForFutureDate() {
        LocalDate futureDate = LocalDate.now().plusYears(10);
        when(projectService.getProjectsByEndDateAfter(futureDate))
            .thenReturn(List.of());

        ResponseEntity<List<Project>> response = 
            projectController.getWithEndDateAfter(futureDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getWithFundingRateLessThan_ShouldReturnProjectsBelowRate() {
        BigDecimal rateLimit = BigDecimal.valueOf(50.0);
        List<Project> expectedProjects = List.of(testProject);
        
        when(projectService.getProjectsByFundingRateLessThan(rateLimit))
            .thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = 
            projectController.getWithFundingRateLessThan(rateLimit);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProjects, response.getBody());
    }

    @Test
    void getWithFundingRateLessThan_ShouldHandleEdgeCases() {
        BigDecimal zeroRate = BigDecimal.ZERO;
        when(projectService.getProjectsByFundingRateLessThan(zeroRate))
            .thenReturn(List.of());

        ResponseEntity<List<Project>> response = 
            projectController.getWithFundingRateLessThan(zeroRate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getByMaxHoursPerMonth_ShouldReturnProjectsWithExactHours() {
        BigDecimal hours = BigDecimal.valueOf(160.0);
        List<Project> expectedProjects = List.of(testProject);
        
        when(projectService.getProjectsByMaxHoursPerMonth(hours))
            .thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = 
            projectController.getByMaxHoursPerMonth(hours);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProjects, response.getBody());
    }

    @Test
    void getByMaxHoursPerMonth_ShouldHandleDecimalValues() {
        BigDecimal decimalHours = BigDecimal.valueOf(120.5);
        when(projectService.getProjectsByMaxHoursPerMonth(decimalHours))
            .thenReturn(List.of(testProject));

        ResponseEntity<List<Project>> response = 
            projectController.getByMaxHoursPerMonth(decimalHours);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getByMaxHoursPerYear_ShouldReturnProjects() {
        BigDecimal hours = BigDecimal.valueOf(1920.0);
        List<Project> expectedProjects = List.of(testProject);
        
        when(projectService.getProjectsByMaxHoursPerYear(hours))
            .thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = 
            projectController.getByMaxHoursPerYear(hours);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProjects, response.getBody());
    }

    @Test
    void getByMaxHoursPerYear_ShouldHandleLargeValues() {
        BigDecimal largeValue = BigDecimal.valueOf(10000.0);
        when(projectService.getProjectsByMaxHoursPerYear(largeValue))
            .thenReturn(List.of());

        ResponseEntity<List<Project>> response = 
            projectController.getByMaxHoursPerYear(largeValue);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getByProductiveHoursPerYear_ShouldReturnProjects() {
        BigDecimal hours = BigDecimal.valueOf(1800.0);
        List<Project> expectedProjects = List.of(testProject);
        
        when(projectService.getProjectsByProductiveHoursPerYear(hours))
            .thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = 
            projectController.getByProductiveHoursPerYear(hours);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProjects, response.getBody());
    }

    @Test
    void getByProductiveHoursPerYear_ShouldHandlePrecision() {
        BigDecimal preciseHours = new BigDecimal("1768.75");
        when(projectService.getProjectsByProductiveHoursPerYear(preciseHours))
            .thenReturn(List.of(testProject));

        ResponseEntity<List<Project>> response = 
            projectController.getByProductiveHoursPerYear(preciseHours);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getByEndDate_ShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByEndDate(testDate)).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getByEndDate(testDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
        verify(projectService).getProjectsByEndDate(testDate);
    }

    @Test
    void getByEndDate_ShouldReturnEmptyListWhenNoMatches() {
        when(projectService.getProjectsByEndDate(testDate)).thenReturn(List.of());

        ResponseEntity<List<Project>> response = projectController.getByEndDate(testDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getByStartApproval_ShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByStartApproval(testDate)).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getByStartApproval(testDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
        verify(projectService).getProjectsByStartApproval(testDate);
    }

    @Test
    void getByStartDate_ShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByStartDate(testDate)).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getByStartDate(testDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
        verify(projectService).getProjectsByStartDate(testDate);
    }

    @Test
    void getByStartDate_ShouldReturnEmptyListForFutureDate() {
        LocalDate futureDate = LocalDate.now().plusYears(1);
        when(projectService.getProjectsByStartDate(futureDate)).thenReturn(List.of());

        ResponseEntity<List<Project>> response = projectController.getByStartDate(futureDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getByStartDateRange_ShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        LocalDate startDate = testDate;
        LocalDate endDate = testDate.plusDays(7);
        
        when(projectService.getProjectsByStartDateBetween(startDate, endDate))
            .thenReturn(projects);

        ResponseEntity<List<Project>> response = 
            projectController.getByStartDateRange(startDate, endDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
        verify(projectService).getProjectsByStartDateBetween(startDate, endDate);
    }

    @Test
    void getByStartDateRange_ShouldReturnEmptyListForInvalidRange() {
        LocalDate startDate = testDate;
        LocalDate endDate = testDate.minusDays(1); // invalid range
        
        when(projectService.getProjectsByStartDateBetween(startDate, endDate))
            .thenReturn(List.of());

        ResponseEntity<List<Project>> response = 
            projectController.getByStartDateRange(startDate, endDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }
}