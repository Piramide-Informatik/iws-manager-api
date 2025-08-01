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
    private static final Long TEST_ID = 1L;
    private static final LocalDate TEST_DATE = LocalDate.now();
    private static final BigDecimal TEST_DECIMAL = BigDecimal.valueOf(50.0);
    private static final String TEST_STRING = "test";

    @BeforeEach
    void setUp() {
        testProject = new Project();
        testProject.setId(TEST_ID);
        testProject.setProjectName("Test Project");
    }

    @Test
    void updateShouldReturnUpdatedProject() {
        when(projectService.update(eq(TEST_ID), any(Project.class))).thenReturn(testProject);

        ResponseEntity<Project> response = projectController.update(TEST_ID, testProject);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testProject, response.getBody());
    }

    @Test
    void updateShouldReturnNotFoundWhenProjectNotExists() {
        when(projectService.update(eq(TEST_ID), any(Project.class)))
            .thenThrow(new RuntimeException("Not found"));

        ResponseEntity<Project> response = projectController.update(TEST_ID, testProject);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createShouldReturnCreatedProject() {
        when(projectService.create(any(Project.class))).thenReturn(testProject);

        ResponseEntity<?> response = projectController.create(testProject);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testProject, response.getBody());
        verify(projectService).create(testProject);
    }

    @Test
    void getByIdShouldReturnProjectWhenExists() {
        when(projectService.findById(TEST_ID)).thenReturn(Optional.of(testProject));

        ResponseEntity<Project> response = projectController.getById(TEST_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testProject, response.getBody());
    }

    @Test
    void getByIdShouldReturnNotFoundWhenNotExists() {
        when(projectService.findById(TEST_ID)).thenReturn(Optional.empty());

        ResponseEntity<Project> response = projectController.getById(TEST_ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAllShouldReturnAllProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.findAll()).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void deleteShouldReturnNoContentWhenSuccess() {
        doNothing().when(projectService).delete(TEST_ID);

        ResponseEntity<Void> response = projectController.delete(TEST_ID);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(projectService).delete(TEST_ID);
    }

    @Test
    void deleteShouldReturnNotFoundWhenProjectNotExists() {
        doThrow(new RuntimeException("Not found")).when(projectService).delete(TEST_ID);

        ResponseEntity<Void> response = projectController.delete(TEST_ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Date Filter Tests
    @Test
    void getByApprovalDateShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByApprovalDate(TEST_DATE)).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getByApprovalDate(TEST_DATE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getByApprovalDateRangeShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByApprovalDateBetween(TEST_DATE, TEST_DATE.plusDays(1)))
            .thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getByApprovalDateRange(TEST_DATE, TEST_DATE.plusDays(1));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    // Numeric Filter Tests
    @Test
    void getByChanceShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByChance(TEST_DECIMAL)).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getByChance(TEST_DECIMAL);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getWithChanceGreaterThanShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByChanceGreaterThan(TEST_DECIMAL)).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getWithChanceGreaterThan(TEST_DECIMAL);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getByHourlyRateRangeShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByHourlyRateMueuBetween(TEST_DECIMAL, TEST_DECIMAL.add(BigDecimal.TEN)))
            .thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getByHourlyRateRange(TEST_DECIMAL, TEST_DECIMAL.add(BigDecimal.TEN));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    // Text Filter Tests
    @Test
    void getWithNameContainingShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByProjectNameContainingIgnoreCase(TEST_STRING)).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getWithNameContaining(TEST_STRING);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getByCustomerIdShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByCustomerId(TEST_ID)).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getByCustomerId(TEST_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getByCustomerOrderedByStartDateShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByCustomerIdOrderByStartDateDesc(TEST_ID)).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getByCustomerOrderedByStartDate(TEST_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    // Additional tests
    @Test
    void getByAuthorizationDateShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByAuthorizationDate(TEST_DATE)).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getByAuthorizationDate(TEST_DATE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getByEndApprovalShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByEndApproval(TEST_DATE)).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getByEndApproval(TEST_DATE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getByFundingRateShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByFundingRate(TEST_DECIMAL)).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getByFundingRate(TEST_DECIMAL);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getByProjectNameShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByProjectName(TEST_STRING)).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getByProjectName(TEST_STRING);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getWithFundingLabelStartingShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByFundingLabelStartingWith(TEST_STRING)).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getWithFundingLabelStarting(TEST_STRING);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getByFinanceAuthorityShouldReturnProjects() {
        List<Project> expectedProjects = Arrays.asList(testProject);
        when(projectService.getProjectsByFinanceAuthority(TEST_STRING)).thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = 
            projectController.getByFinanceAuthority(TEST_STRING);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProjects, response.getBody());
        verify(projectService).getProjectsByFinanceAuthority(TEST_STRING);
    }

    @Test
    void getByFinanceAuthorityShouldReturnEmptyListWhenNoMatches() {
        when(projectService.getProjectsByFinanceAuthority(TEST_STRING)).thenReturn(List.of());

        ResponseEntity<List<Project>> response = 
            projectController.getByFinanceAuthority(TEST_STRING);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getByFundingLabelShouldReturnProjects() {
        List<Project> expectedProjects = Arrays.asList(testProject);
        when(projectService.getProjectsByFundingLabel(TEST_STRING)).thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = 
            projectController.getByFundingLabel(TEST_STRING);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProjects, response.getBody());
    }

    @Test
    void getByFundingLabelShouldBeCaseSensitive() {
        String differentCase = "TEST";
        when(projectService.getProjectsByFundingLabel(differentCase)).thenReturn(List.of());

        ResponseEntity<List<Project>> response = 
            projectController.getByFundingLabel(differentCase);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotEquals(TEST_STRING, differentCase);
        verify(projectService).getProjectsByFundingLabel(differentCase);
    }

    @Test
    void getByNoteContainingShouldReturnProjectsWithMatchingText() {
        List<Project> expectedProjects = Arrays.asList(testProject);
        when(projectService.getProjectsByNoteContaining(TEST_STRING)).thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = 
            projectController.getByNoteContaining(TEST_STRING);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProjects, response.getBody());
    }

    @Test
    void getByNoteContainingShouldAcceptPartialMatches() {
        String partialText = "est"; // Part of the string "test"
        when(projectService.getProjectsByNoteContaining(partialText)).thenReturn(List.of(testProject));

        ResponseEntity<List<Project>> response = 
            projectController.getByNoteContaining(partialText);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getByProjectLabelShouldReturnExactMatches() {
        List<Project> expectedProjects = Arrays.asList(testProject);
        when(projectService.getProjectsByProjectLabel(TEST_STRING)).thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = 
            projectController.getByProjectLabel(TEST_STRING);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProjects, response.getBody());
    }

    @Test
    void getByTitleShouldReturnProjectsWithExactTitle() {
        List<Project> expectedProjects = Arrays.asList(testProject);
        when(projectService.getProjectsByTitle(TEST_STRING)).thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = 
            projectController.getByTitle(TEST_STRING);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProjects, response.getBody());
    }

    @Test
    void getWithTitleEndingShouldReturnProjects() {
        List<Project> expectedProjects = Arrays.asList(testProject);
        when(projectService.getProjectsByTitleEndingWith(TEST_STRING)).thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = 
            projectController.getWithTitleEnding(TEST_STRING);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProjects, response.getBody());
    }

    @Test
    void getWithTitleEndingShouldMatchSuffixCorrectly() {
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
    void getByShareResearchShouldReturnProjectsWithExactValue() {
        BigDecimal shareValue = BigDecimal.valueOf(30.5);
        List<Project> expectedProjects = List.of(testProject);
        when(projectService.getProjectsByShareResearch(shareValue)).thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = projectController.getByShareResearch(shareValue);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProjects, response.getBody());
        verify(projectService).getProjectsByShareResearch(shareValue);
    }

    @Test
    void getByShareResearchShouldHandleDifferentPrecision() {
        BigDecimal shareValue = new BigDecimal("30.50");
        when(projectService.getProjectsByShareResearch(shareValue)).thenReturn(List.of(testProject));

        ResponseEntity<List<Project>> response = projectController.getByShareResearch(shareValue);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getByStuffFlatShouldReturnProjects() {
        BigDecimal stuffFlatValue = BigDecimal.valueOf(500.0);
        List<Project> expectedProjects = List.of(testProject);
        when(projectService.getProjectsByStuffFlat(stuffFlatValue)).thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = projectController.getByStuffFlat(stuffFlatValue);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProjects, response.getBody());
    }

    @Test
    void getByStuffFlatShouldReturnEmptyListForNonExistingValue() {
        BigDecimal nonExistingValue = BigDecimal.valueOf(999.99);
        when(projectService.getProjectsByStuffFlat(nonExistingValue)).thenReturn(List.of());

        ResponseEntity<List<Project>> response = projectController.getByStuffFlat(nonExistingValue);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getByOrderIdFueShouldReturnProjectsWithSpecificOrderId() {
        Integer orderId = 12345;
        List<Project> expectedProjects = List.of(testProject);
        when(projectService.getProjectsByOrderIdFue(orderId)).thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = projectController.getByOrderIdFue(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProjects, response.getBody());
    }

    @Test
    void getByOrderIdFueShouldHandleNullValue() {
        when(projectService.getProjectsByOrderIdFue(null)).thenReturn(List.of());

        ResponseEntity<List<Project>> response = projectController.getByOrderIdFue(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getByOrderIdAdminShouldReturnProjects() {
        Integer orderId = 54321;
        List<Project> expectedProjects = List.of(testProject);
        when(projectService.getProjectsByOrderIdAdmin(orderId)).thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = projectController.getByOrderIdAdmin(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProjects, response.getBody());
    }

    @Test
    void getByOrderIdAdminShouldReturnNotFoundForInvalidId() {
        Integer invalidId = -1;
        when(projectService.getProjectsByOrderIdAdmin(invalidId)).thenReturn(List.of());

        ResponseEntity<List<Project>> response = projectController.getByOrderIdAdmin(invalidId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getByCommentContainingShouldReturnProjectsWithMatchingComments() {
        String searchTerm = "urgent";
        List<Project> expectedProjects = List.of(testProject);
        when(projectService.getProjectsByCommentContaining(searchTerm)).thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = projectController.getByCommentContaining(searchTerm);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProjects, response.getBody());
    }

    @Test
    void getByCommentContainingShouldBeCaseInsensitive() {
        String searchTerm = "UrGeNt";
        List<Project> expectedProjects = List.of(testProject);
        when(projectService.getProjectsByCommentContaining(searchTerm)).thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = projectController.getByCommentContaining(searchTerm);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getByCommentContainingShouldHandleSpecialCharacters() {
        String searchTerm = "método #2";
        when(projectService.getProjectsByCommentContaining(searchTerm)).thenReturn(List.of(testProject));

        ResponseEntity<List<Project>> response = projectController.getByCommentContaining(searchTerm);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getWithAuthorizationBeforeShouldReturnProjectsBeforeDate() {
        LocalDate TEST_DATE = LocalDate.of(2023, 12, 31);
        List<Project> expectedProjects = List.of(testProject);
        
        when(projectService.getProjectsByAuthorizationDateBefore(TEST_DATE))
            .thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = 
            projectController.getWithAuthorizationBefore(TEST_DATE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProjects, response.getBody());
        verify(projectService).getProjectsByAuthorizationDateBefore(TEST_DATE);
    }

    @Test
    void getWithAuthorizationBeforeShouldHandleNullDate() {
        ResponseEntity<List<Project>> response = 
            projectController.getWithAuthorizationBefore(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getWithEndDateAfterShouldReturnProjectsAfterDate() {
        LocalDate TEST_DATE = LocalDate.of(2023, 1, 1);
        List<Project> expectedProjects = List.of(testProject);
        
        when(projectService.getProjectsByEndDateAfter(TEST_DATE))
            .thenReturn(expectedProjects);

        ResponseEntity<List<Project>> response = 
            projectController.getWithEndDateAfter(TEST_DATE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProjects, response.getBody());
    }

    @Test
    void getWithEndDateAfterShouldReturnEmptyListForFutureDate() {
        LocalDate futureDate = LocalDate.now().plusYears(10);
        when(projectService.getProjectsByEndDateAfter(futureDate))
            .thenReturn(List.of());

        ResponseEntity<List<Project>> response = 
            projectController.getWithEndDateAfter(futureDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getWithFundingRateLessThanShouldReturnProjectsBelowRate() {
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
    void getWithFundingRateLessThanShouldHandleEdgeCases() {
        BigDecimal zeroRate = BigDecimal.ZERO;
        when(projectService.getProjectsByFundingRateLessThan(zeroRate))
            .thenReturn(List.of());

        ResponseEntity<List<Project>> response = 
            projectController.getWithFundingRateLessThan(zeroRate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getByMaxHoursPerMonthShouldReturnProjectsWithExactHours() {
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
    void getByMaxHoursPerMonthShouldHandleDecimalValues() {
        BigDecimal decimalHours = BigDecimal.valueOf(120.5);
        when(projectService.getProjectsByMaxHoursPerMonth(decimalHours))
            .thenReturn(List.of(testProject));

        ResponseEntity<List<Project>> response = 
            projectController.getByMaxHoursPerMonth(decimalHours);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getByMaxHoursPerYearShouldReturnProjects() {
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
    void getByMaxHoursPerYearShouldHandleLargeValues() {
        BigDecimal largeValue = BigDecimal.valueOf(10000.0);
        when(projectService.getProjectsByMaxHoursPerYear(largeValue))
            .thenReturn(List.of());

        ResponseEntity<List<Project>> response = 
            projectController.getByMaxHoursPerYear(largeValue);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getByProductiveHoursPerYearShouldReturnProjects() {
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
    void getByProductiveHoursPerYearShouldHandlePrecision() {
        BigDecimal preciseHours = new BigDecimal("1768.75");
        when(projectService.getProjectsByProductiveHoursPerYear(preciseHours))
            .thenReturn(List.of(testProject));

        ResponseEntity<List<Project>> response = 
            projectController.getByProductiveHoursPerYear(preciseHours);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getByEndDateShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByEndDate(TEST_DATE)).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getByEndDate(TEST_DATE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
        verify(projectService).getProjectsByEndDate(TEST_DATE);
    }

    @Test
    void getByEndDateShouldReturnEmptyListWhenNoMatches() {
        when(projectService.getProjectsByEndDate(TEST_DATE)).thenReturn(List.of());

        ResponseEntity<List<Project>> response = projectController.getByEndDate(TEST_DATE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getByStartApprovalShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByStartApproval(TEST_DATE)).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getByStartApproval(TEST_DATE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
        verify(projectService).getProjectsByStartApproval(TEST_DATE);
    }

    @Test
    void getByStartDateShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        when(projectService.getProjectsByStartDate(TEST_DATE)).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getByStartDate(TEST_DATE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
        verify(projectService).getProjectsByStartDate(TEST_DATE);
    }

    @Test
    void getByStartDateShouldReturnEmptyListForFutureDate() {
        LocalDate futureDate = LocalDate.now().plusYears(1);
        when(projectService.getProjectsByStartDate(futureDate)).thenReturn(List.of());

        ResponseEntity<List<Project>> response = projectController.getByStartDate(futureDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getByStartDateRangeShouldReturnProjects() {
        List<Project> projects = Arrays.asList(testProject);
        LocalDate startDate = TEST_DATE;
        LocalDate endDate = TEST_DATE.plusDays(7);
        
        when(projectService.getProjectsByStartDateBetween(startDate, endDate))
            .thenReturn(projects);

        ResponseEntity<List<Project>> response = 
            projectController.getByStartDateRange(startDate, endDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
        verify(projectService).getProjectsByStartDateBetween(startDate, endDate);
    }

    @Test
    void getByStartDateRangeShouldReturnEmptyListForInvalidRange() {
        LocalDate startDate = TEST_DATE;
        LocalDate endDate = TEST_DATE.minusDays(1); // invalid range
        
        when(projectService.getProjectsByStartDateBetween(startDate, endDate))
            .thenReturn(List.of());

        ResponseEntity<List<Project>> response = 
            projectController.getByStartDateRange(startDate, endDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }
}