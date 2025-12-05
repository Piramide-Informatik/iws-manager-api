package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.ProjectEmployee;
import com.iws_manager.iws_manager_api.repositories.ProjectEmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ProjectEmployeeServiceImplTest {

    private static final String QUALIFICATION_K_MUI = "Senior Developer";
    private static final BigDecimal HOURLY_RATE = new BigDecimal("45.50");
    private static final BigDecimal PLANNED_HOURS = new BigDecimal("160.00");
    private static final Long EMPLOYEE_ID = 1L;
    private static final Long PROJECT_ID = 2L;
    private static final String KEYWORD = "Developer";
    private static final String HOURLY_RATE_50 = "50.00";
    private static final String HOURLY_RATE_40 = "40.00";

    @Mock
    private ProjectEmployeeRepository projectEmployeeRepository;

    @InjectMocks
    private ProjectEmployeeServiceImpl projectEmployeeService;

    private ProjectEmployee sampleProjectEmployee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleProjectEmployee = new ProjectEmployee();
        sampleProjectEmployee.setId(1L);
        sampleProjectEmployee.setQualificationkmui(QUALIFICATION_K_MUI);
        sampleProjectEmployee.setHourlyrate(HOURLY_RATE);
        sampleProjectEmployee.setPlannedhours(PLANNED_HOURS);
    }

    // Basic CRUD operations tests

    @Test
    void testCreate() {
        when(projectEmployeeRepository.save(any(ProjectEmployee.class))).thenReturn(sampleProjectEmployee);
        ProjectEmployee created = projectEmployeeService.create(sampleProjectEmployee);
        assertNotNull(created);
        assertEquals(QUALIFICATION_K_MUI, created.getQualificationkmui());
        verify(projectEmployeeRepository, times(1)).save(sampleProjectEmployee);
    }

    @Test
    void testCreateThrowsIllegalArgumentExceptionWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> projectEmployeeService.create(null));
        verify(projectEmployeeRepository, never()).save(any(ProjectEmployee.class));
    }

    @Test
    void testGetById() {
        when(projectEmployeeRepository.findById(1L)).thenReturn(Optional.of(sampleProjectEmployee));
        Optional<ProjectEmployee> result = projectEmployeeService.getById(1L);
        assertTrue(result.isPresent());
        assertEquals(QUALIFICATION_K_MUI, result.get().getQualificationkmui());
        verify(projectEmployeeRepository, times(1)).findById(1L);
    }

    @Test
    void testGetByIdThrowsIllegalArgumentExceptionWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> projectEmployeeService.getById(null));
        verify(projectEmployeeRepository, never()).findById(anyLong());
    }

    @Test
    void testGetAll() {
        List<ProjectEmployee> projectEmployees = List.of(sampleProjectEmployee);
        when(projectEmployeeRepository.findAllByOrderByIdAsc()).thenReturn(projectEmployees);

        List<ProjectEmployee> result = projectEmployeeService.getAll();

        assertEquals(1, result.size());
        verify(projectEmployeeRepository, times(1)).findAllByOrderByIdAsc();
    }

    @Test
    void testUpdate() {
        ProjectEmployee updated = new ProjectEmployee();
        updated.setQualificationkmui("Updated Qualification");
        updated.setHourlyrate(new BigDecimal(HOURLY_RATE_50));
        updated.setPlannedhours(new BigDecimal("180.00"));

        when(projectEmployeeRepository.findById(1L)).thenReturn(Optional.of(sampleProjectEmployee));
        when(projectEmployeeRepository.save(any(ProjectEmployee.class))).thenReturn(updated);

        ProjectEmployee result = projectEmployeeService.update(1L, updated);
        assertNotNull(result);
        assertEquals("Updated Qualification", result.getQualificationkmui());
        assertEquals(new BigDecimal(HOURLY_RATE_50), result.getHourlyrate());
        verify(projectEmployeeRepository, times(1)).findById(1L);
        verify(projectEmployeeRepository, times(1)).save(any(ProjectEmployee.class));
    }

    @Test
    void testUpdateThrowsIllegalArgumentExceptionWhenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> projectEmployeeService.update(null, sampleProjectEmployee));
        verify(projectEmployeeRepository, never()).findById(anyLong());
        verify(projectEmployeeRepository, never()).save(any(ProjectEmployee.class));
    }

    @Test
    void testUpdateThrowsIllegalArgumentExceptionWhenProjectEmployeeNull() {
        assertThrows(IllegalArgumentException.class, () -> projectEmployeeService.update(1L, null));
        verify(projectEmployeeRepository, never()).findById(anyLong());
        verify(projectEmployeeRepository, never()).save(any(ProjectEmployee.class));
    }

    @Test
    void testUpdateThrowsEntityNotFoundExceptionWhenNotFound() {
        when(projectEmployeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> projectEmployeeService.update(1L, sampleProjectEmployee));
        verify(projectEmployeeRepository, times(1)).findById(1L);
        verify(projectEmployeeRepository, never()).save(any(ProjectEmployee.class));
    }

    @Test
    void testDelete() {
        when(projectEmployeeRepository.existsById(1L)).thenReturn(true);
        projectEmployeeService.delete(1L);
        verify(projectEmployeeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteThrowsIllegalArgumentExceptionWhenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> projectEmployeeService.delete(null));
        verify(projectEmployeeRepository, never()).existsById(anyLong());
        verify(projectEmployeeRepository, never()).deleteById(anyLong());
    }

    @Test
    void testDeleteThrowsEntityNotFoundExceptionWhenNotFound() {
        when(projectEmployeeRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> projectEmployeeService.delete(1L));
        verify(projectEmployeeRepository, times(1)).existsById(1L);
        verify(projectEmployeeRepository, never()).deleteById(anyLong());
    }

    // Get operations by employee tests
    @Test
    void testGetByEmployeeId() {
        when(projectEmployeeRepository.findByEmployeeId(EMPLOYEE_ID)).thenReturn(List.of(sampleProjectEmployee));
        List<ProjectEmployee> result = projectEmployeeService.getByEmployeeId(EMPLOYEE_ID);
        assertEquals(1, result.size());
        verify(projectEmployeeRepository, times(1)).findByEmployeeId(EMPLOYEE_ID);
    }

    @Test
    void testGetByEmployeeIdThrowsIllegalArgumentExceptionWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> projectEmployeeService.getByEmployeeId(null));
        verify(projectEmployeeRepository, never()).findByEmployeeId(anyLong());
    }

    @Test
    void testGetByEmployeeIdOrderByIdAsc() {
        when(projectEmployeeRepository.findByEmployeeIdOrderByIdAsc(EMPLOYEE_ID))
                .thenReturn(List.of(sampleProjectEmployee));
        List<ProjectEmployee> result = projectEmployeeService.getByEmployeeIdOrderByIdAsc(EMPLOYEE_ID);
        assertEquals(1, result.size());
        verify(projectEmployeeRepository, times(1)).findByEmployeeIdOrderByIdAsc(EMPLOYEE_ID);
    }

    // Get operations by project tests
    @Test
    void testGetByProjectId() {
        when(projectEmployeeRepository.findByProjectId(PROJECT_ID)).thenReturn(List.of(sampleProjectEmployee));
        List<ProjectEmployee> result = projectEmployeeService.getByProjectId(PROJECT_ID);
        assertEquals(1, result.size());
        verify(projectEmployeeRepository, times(1)).findByProjectId(PROJECT_ID);
    }

    @Test
    void testGetByProjectIdOrderByIdAsc() {
        when(projectEmployeeRepository.findByProjectIdOrderByIdAsc(PROJECT_ID))
                .thenReturn(List.of(sampleProjectEmployee));
        List<ProjectEmployee> result = projectEmployeeService.getByProjectIdOrderByIdAsc(PROJECT_ID);
        assertEquals(1, result.size());
        verify(projectEmployeeRepository, times(1)).findByProjectIdOrderByIdAsc(PROJECT_ID);
    }

    // Get operations by qualificationkmui tests
    @Test
    void testGetByQualificationkmui() {
        when(projectEmployeeRepository.findByQualificationkmui(QUALIFICATION_K_MUI))
                .thenReturn(List.of(sampleProjectEmployee));
        List<ProjectEmployee> result = projectEmployeeService.getByQualificationkmui(QUALIFICATION_K_MUI);
        assertEquals(1, result.size());
        verify(projectEmployeeRepository, times(1)).findByQualificationkmui(QUALIFICATION_K_MUI);
    }

    @Test
    void testGetByQualificationkmuiContainingIgnoreCase() {
        when(projectEmployeeRepository.findByQualificationkmuiContainingIgnoreCase(KEYWORD))
                .thenReturn(List.of(sampleProjectEmployee));
        List<ProjectEmployee> result = projectEmployeeService.getByQualificationkmuiContainingIgnoreCase(KEYWORD);
        assertEquals(1, result.size());
        verify(projectEmployeeRepository, times(1)).findByQualificationkmuiContainingIgnoreCase(KEYWORD);
    }

    @Test
    void testGetByQualificationkmuiOrderByIdAsc() {
        when(projectEmployeeRepository.findByQualificationkmuiOrderByIdAsc(QUALIFICATION_K_MUI))
                .thenReturn(List.of(sampleProjectEmployee));
        List<ProjectEmployee> result = projectEmployeeService.getByQualificationkmuiOrderByIdAsc(QUALIFICATION_K_MUI);
        assertEquals(1, result.size());
        verify(projectEmployeeRepository, times(1)).findByQualificationkmuiOrderByIdAsc(QUALIFICATION_K_MUI);
    }

    // Get operations with combined criteria tests
    @Test
    void testGetByEmployeeIdAndProjectId() {
        when(projectEmployeeRepository.findByEmployeeIdAndProjectId(EMPLOYEE_ID, PROJECT_ID))
                .thenReturn(List.of(sampleProjectEmployee));
        List<ProjectEmployee> result = projectEmployeeService.getByEmployeeIdAndProjectId(EMPLOYEE_ID, PROJECT_ID);
        assertEquals(1, result.size());
        verify(projectEmployeeRepository, times(1)).findByEmployeeIdAndProjectId(EMPLOYEE_ID, PROJECT_ID);
    }

    @Test
    void testGetByProjectIdAndQualificationkmui() {
        when(projectEmployeeRepository.findByProjectIdAndQualificationkmui(PROJECT_ID, QUALIFICATION_K_MUI))
                .thenReturn(List.of(sampleProjectEmployee));
        List<ProjectEmployee> result = projectEmployeeService.getByProjectIdAndQualificationkmui(PROJECT_ID,
                QUALIFICATION_K_MUI);
        assertEquals(1, result.size());
        verify(projectEmployeeRepository, times(1)).findByProjectIdAndQualificationkmui(PROJECT_ID,
                QUALIFICATION_K_MUI);
    }

    @Test
    void testGetByEmployeeIdAndQualificationkmui() {
        when(projectEmployeeRepository.findByEmployeeIdAndQualificationkmui(EMPLOYEE_ID, QUALIFICATION_K_MUI))
                .thenReturn(List.of(sampleProjectEmployee));
        List<ProjectEmployee> result = projectEmployeeService.getByEmployeeIdAndQualificationkmui(EMPLOYEE_ID,
                QUALIFICATION_K_MUI);
        assertEquals(1, result.size());
        verify(projectEmployeeRepository, times(1)).findByEmployeeIdAndQualificationkmui(EMPLOYEE_ID,
                QUALIFICATION_K_MUI);
    }

    @Test
    void testGetByEmployeeProjectAndQualification() {
        when(projectEmployeeRepository.findByEmployeeProjectAndQualification(EMPLOYEE_ID, PROJECT_ID,
                QUALIFICATION_K_MUI))
                .thenReturn(List.of(sampleProjectEmployee));
        List<ProjectEmployee> result = projectEmployeeService.getByEmployeeProjectAndQualification(EMPLOYEE_ID,
                PROJECT_ID, QUALIFICATION_K_MUI);
        assertEquals(1, result.size());
        verify(projectEmployeeRepository, times(1)).findByEmployeeProjectAndQualification(EMPLOYEE_ID, PROJECT_ID,
                QUALIFICATION_K_MUI);
    }

    // Get operations by hourlyrate range tests
    @Test
    void testGetByHourlyrateGreaterThan() {
        when(projectEmployeeRepository.findByHourlyrateGreaterThan(HOURLY_RATE))
                .thenReturn(List.of(sampleProjectEmployee));
        List<ProjectEmployee> result = projectEmployeeService.getByHourlyrateGreaterThan(HOURLY_RATE);
        assertEquals(1, result.size());
        verify(projectEmployeeRepository, times(1)).findByHourlyrateGreaterThan(HOURLY_RATE);
    }

    @Test
    void testGetByHourlyrateLessThan() {
        BigDecimal higherRate = new BigDecimal("100.00");
        when(projectEmployeeRepository.findByHourlyrateLessThan(higherRate)).thenReturn(List.of(sampleProjectEmployee));
        List<ProjectEmployee> result = projectEmployeeService.getByHourlyrateLessThan(higherRate);
        assertEquals(1, result.size());
        verify(projectEmployeeRepository, times(1)).findByHourlyrateLessThan(higherRate);
    }

    @Test
    void testGetByHourlyrateBetween() {
        BigDecimal minRate = new BigDecimal(HOURLY_RATE_40);
        BigDecimal maxRate = new BigDecimal(HOURLY_RATE_50);
        when(projectEmployeeRepository.findByHourlyrateBetween(minRate, maxRate))
                .thenReturn(List.of(sampleProjectEmployee));
        List<ProjectEmployee> result = projectEmployeeService.getByHourlyrateBetween(minRate, maxRate);
        assertEquals(1, result.size());
        verify(projectEmployeeRepository, times(1)).findByHourlyrateBetween(minRate, maxRate);
    }

    @Test
    void testGetByHourlyrateBetweenThrowsExceptionWhenMinGreaterThanMax() {
        BigDecimal minRate = new BigDecimal(HOURLY_RATE_50);
        BigDecimal maxRate = new BigDecimal(HOURLY_RATE_40);

        assertThrows(IllegalArgumentException.class,
                () -> projectEmployeeService.getByHourlyrateBetween(minRate, maxRate));
        verify(projectEmployeeRepository, never()).findByHourlyrateBetween(any(), any());
    }

    // Get operations by plannedhours range tests
    @Test
    void testGetByPlannedhoursGreaterThan() {
        when(projectEmployeeRepository.findByPlannedhoursGreaterThan(PLANNED_HOURS))
                .thenReturn(List.of(sampleProjectEmployee));
        List<ProjectEmployee> result = projectEmployeeService.getByPlannedhoursGreaterThan(PLANNED_HOURS);
        assertEquals(1, result.size());
        verify(projectEmployeeRepository, times(1)).findByPlannedhoursGreaterThan(PLANNED_HOURS);
    }

    @Test
    void testGetByPlannedhoursLessThan() {
        BigDecimal higherHours = new BigDecimal("200.00");
        when(projectEmployeeRepository.findByPlannedhoursLessThan(higherHours))
                .thenReturn(List.of(sampleProjectEmployee));
        List<ProjectEmployee> result = projectEmployeeService.getByPlannedhoursLessThan(higherHours);
        assertEquals(1, result.size());
        verify(projectEmployeeRepository, times(1)).findByPlannedhoursLessThan(higherHours);
    }

    @Test
    void testGetByPlannedhoursBetween() {
        BigDecimal minHours = new BigDecimal("150.00");
        BigDecimal maxHours = new BigDecimal("170.00");
        when(projectEmployeeRepository.findByPlannedhoursBetween(minHours, maxHours))
                .thenReturn(List.of(sampleProjectEmployee));
        List<ProjectEmployee> result = projectEmployeeService.getByPlannedhoursBetween(minHours, maxHours);
        assertEquals(1, result.size());
        verify(projectEmployeeRepository, times(1)).findByPlannedhoursBetween(minHours, maxHours);
    }

    @Test
    void testGetByPlannedhoursBetweenThrowsExceptionWhenMinGreaterThanMax() {
        BigDecimal minHours = new BigDecimal("170.00");
        BigDecimal maxHours = new BigDecimal("150.00");

        assertThrows(IllegalArgumentException.class,
                () -> projectEmployeeService.getByPlannedhoursBetween(minHours, maxHours));
        verify(projectEmployeeRepository, never()).findByPlannedhoursBetween(any(), any());
    }

    // Get operations by estimated cost range tests
    @Test
    void testGetByEstimatedCostGreaterThan() {
        BigDecimal minCost = new BigDecimal("1000.00");
        when(projectEmployeeRepository.findByEstimatedCostGreaterThan(minCost))
                .thenReturn(List.of(sampleProjectEmployee));
        List<ProjectEmployee> result = projectEmployeeService.getByEstimatedCostGreaterThan(minCost);
        assertEquals(1, result.size());
        verify(projectEmployeeRepository, times(1)).findByEstimatedCostGreaterThan(minCost);
    }

    @Test
    void testGetByEstimatedCostLessThan() {
        BigDecimal maxCost = new BigDecimal("10000.00");
        when(projectEmployeeRepository.findByEstimatedCostLessThan(maxCost))
                .thenReturn(List.of(sampleProjectEmployee));
        List<ProjectEmployee> result = projectEmployeeService.getByEstimatedCostLessThan(maxCost);
        assertEquals(1, result.size());
        verify(projectEmployeeRepository, times(1)).findByEstimatedCostLessThan(maxCost);
    }

    @Test
    void testGetByEstimatedCostBetween() {
        BigDecimal minCost = new BigDecimal("1000.00");
        BigDecimal maxCost = new BigDecimal("5000.00");
        when(projectEmployeeRepository.findByEstimatedCostBetween(minCost, maxCost))
                .thenReturn(List.of(sampleProjectEmployee));
        List<ProjectEmployee> result = projectEmployeeService.getByEstimatedCostBetween(minCost, maxCost);
        assertEquals(1, result.size());
        verify(projectEmployeeRepository, times(1)).findByEstimatedCostBetween(minCost, maxCost);
    }

    @Test
    void testGetByEstimatedCostBetweenThrowsExceptionWhenMinGreaterThanMax() {
        BigDecimal minCost = new BigDecimal("5000.00");
        BigDecimal maxCost = new BigDecimal("1000.00");

        assertThrows(IllegalArgumentException.class,
                () -> projectEmployeeService.getByEstimatedCostBetween(minCost, maxCost));
        verify(projectEmployeeRepository, never()).findByEstimatedCostBetween(any(), any());
    }

    // Get operations with minimum rate and hours tests
    @Test
    void testGetWithMinimumRateAndHours() {
        BigDecimal minRate = new BigDecimal(HOURLY_RATE_40);
        BigDecimal minHours = new BigDecimal("150.00");
        when(projectEmployeeRepository.findWithMinimumRateAndHours(minRate, minHours))
                .thenReturn(List.of(sampleProjectEmployee));
        List<ProjectEmployee> result = projectEmployeeService.getWithMinimumRateAndHours(minRate, minHours);
        assertEquals(1, result.size());
        verify(projectEmployeeRepository, times(1)).findWithMinimumRateAndHours(minRate, minHours);
    }

    // Get operations by qualification containing keyword tests
    @Test
    void testGetByQualificationContaining() {
        when(projectEmployeeRepository.findByQualificationContaining(KEYWORD))
                .thenReturn(List.of(sampleProjectEmployee));
        List<ProjectEmployee> result = projectEmployeeService.getByQualificationContaining(KEYWORD);
        assertEquals(1, result.size());
        verify(projectEmployeeRepository, times(1)).findByQualificationContaining(KEYWORD);
    }

    // Get operations by multiple qualifications tests
    @Test
    void testGetByQualificationsIn() {
        List<String> qualifications = Arrays.asList("Senior Developer", "Junior Developer");
        when(projectEmployeeRepository.findByQualificationsIn(qualifications))
                .thenReturn(List.of(sampleProjectEmployee));
        List<ProjectEmployee> result = projectEmployeeService.getByQualificationsIn(qualifications);
        assertEquals(1, result.size());
        verify(projectEmployeeRepository, times(1)).findByQualificationsIn(qualifications);
    }

    @Test
    void testGetByQualificationsInThrowsIllegalArgumentExceptionWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> projectEmployeeService.getByQualificationsIn(null));
        verify(projectEmployeeRepository, never()).findByQualificationsIn(any());
    }

    @Test
    void testGetByQualificationsInThrowsIllegalArgumentExceptionWhenEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> projectEmployeeService.getByQualificationsIn(Collections.emptyList()));
        verify(projectEmployeeRepository, never()).findByQualificationsIn(any());
    }

    // Ordering operations tests
    @Test
    void testGetAllOrderByHourlyrateAsc() {
        when(projectEmployeeRepository.findAllByOrderByHourlyrateAsc()).thenReturn(List.of(sampleProjectEmployee));
        List<ProjectEmployee> result = projectEmployeeService.getAllOrderByHourlyrateAsc();
        assertEquals(1, result.size());
        verify(projectEmployeeRepository, times(1)).findAllByOrderByHourlyrateAsc();
    }

    @Test
    void testGetAllOrderByHourlyrateDesc() {
        when(projectEmployeeRepository.findAllByOrderByHourlyrateDesc()).thenReturn(List.of(sampleProjectEmployee));
        List<ProjectEmployee> result = projectEmployeeService.getAllOrderByHourlyrateDesc();
        assertEquals(1, result.size());
        verify(projectEmployeeRepository, times(1)).findAllByOrderByHourlyrateDesc();
    }

    @Test
    void testGetAllOrderByPlannedhoursAsc() {
        when(projectEmployeeRepository.findAllByOrderByPlannedhoursAsc()).thenReturn(List.of(sampleProjectEmployee));
        List<ProjectEmployee> result = projectEmployeeService.getAllOrderByPlannedhoursAsc();
        assertEquals(1, result.size());
        verify(projectEmployeeRepository, times(1)).findAllByOrderByPlannedhoursAsc();
    }

    @Test
    void testGetAllOrderByPlannedhoursDesc() {
        when(projectEmployeeRepository.findAllByOrderByPlannedhoursDesc()).thenReturn(List.of(sampleProjectEmployee));
        List<ProjectEmployee> result = projectEmployeeService.getAllOrderByPlannedhoursDesc();
        assertEquals(1, result.size());
        verify(projectEmployeeRepository, times(1)).findAllByOrderByPlannedhoursDesc();
    }

    @Test
    void testGetAllOrderByQualificationkmuiAsc() {
        when(projectEmployeeRepository.findAllByOrderByQualificationkmuiAsc())
                .thenReturn(List.of(sampleProjectEmployee));
        List<ProjectEmployee> result = projectEmployeeService.getAllOrderByQualificationkmuiAsc();
        assertEquals(1, result.size());
        verify(projectEmployeeRepository, times(1)).findAllByOrderByQualificationkmuiAsc();
    }

    @Test
    void testGetAllOrderByEstimatedCostAsc() {
        when(projectEmployeeRepository.findAllByOrderByEstimatedCostAsc()).thenReturn(List.of(sampleProjectEmployee));
        List<ProjectEmployee> result = projectEmployeeService.getAllOrderByEstimatedCostAsc();
        assertEquals(1, result.size());
        verify(projectEmployeeRepository, times(1)).findAllByOrderByEstimatedCostAsc();
    }

    @Test
    void testGetAllOrderByEstimatedCostDesc() {
        when(projectEmployeeRepository.findAllByOrderByEstimatedCostDesc()).thenReturn(List.of(sampleProjectEmployee));
        List<ProjectEmployee> result = projectEmployeeService.getAllOrderByEstimatedCostDesc();
        assertEquals(1, result.size());
        verify(projectEmployeeRepository, times(1)).findAllByOrderByEstimatedCostDesc();
    }

    // Calculation operations tests
    @Test
    void testCalculateTotalCostByProject() {
        BigDecimal totalCost = new BigDecimal("7280.00"); // 45.50 * 160.00
        when(projectEmployeeRepository.calculateTotalCostByProject(PROJECT_ID)).thenReturn(totalCost);
        BigDecimal result = projectEmployeeService.calculateTotalCostByProject(PROJECT_ID);
        assertEquals(totalCost, result);
        verify(projectEmployeeRepository, times(1)).calculateTotalCostByProject(PROJECT_ID);
    }

    @Test
    void testCalculateTotalCostByProjectReturnsZeroWhenNull() {
        when(projectEmployeeRepository.calculateTotalCostByProject(PROJECT_ID)).thenReturn(null);
        BigDecimal result = projectEmployeeService.calculateTotalCostByProject(PROJECT_ID);
        assertEquals(BigDecimal.ZERO, result);
        verify(projectEmployeeRepository, times(1)).calculateTotalCostByProject(PROJECT_ID);
    }

    @Test
    void testCalculateTotalCostByEmployee() {
        BigDecimal totalCost = new BigDecimal("7280.00");
        when(projectEmployeeRepository.calculateTotalCostByEmployee(EMPLOYEE_ID)).thenReturn(totalCost);
        BigDecimal result = projectEmployeeService.calculateTotalCostByEmployee(EMPLOYEE_ID);
        assertEquals(totalCost, result);
        verify(projectEmployeeRepository, times(1)).calculateTotalCostByEmployee(EMPLOYEE_ID);
    }

    @Test
    void testCalculateTotalPlannedHoursByProject() {
        BigDecimal totalHours = new BigDecimal("320.00");
        when(projectEmployeeRepository.calculateTotalPlannedHoursByProject(PROJECT_ID)).thenReturn(totalHours);
        BigDecimal result = projectEmployeeService.calculateTotalPlannedHoursByProject(PROJECT_ID);
        assertEquals(totalHours, result);
        verify(projectEmployeeRepository, times(1)).calculateTotalPlannedHoursByProject(PROJECT_ID);
    }

    @Test
    void testCalculateTotalPlannedHoursByEmployee() {
        BigDecimal totalHours = new BigDecimal("320.00");
        when(projectEmployeeRepository.calculateTotalPlannedHoursByEmployee(EMPLOYEE_ID)).thenReturn(totalHours);
        BigDecimal result = projectEmployeeService.calculateTotalPlannedHoursByEmployee(EMPLOYEE_ID);
        assertEquals(totalHours, result);
        verify(projectEmployeeRepository, times(1)).calculateTotalPlannedHoursByEmployee(EMPLOYEE_ID);
    }

    // Count operations tests
    @Test
    void testCountByProject() {
        Long count = 5L;
        when(projectEmployeeRepository.countByProject(PROJECT_ID)).thenReturn(count);
        Long result = projectEmployeeService.countByProject(PROJECT_ID);
        assertEquals(count, result);
        verify(projectEmployeeRepository, times(1)).countByProject(PROJECT_ID);
    }

    @Test
    void testCountByEmployee() {
        Long count = 3L;
        when(projectEmployeeRepository.countByEmployee(EMPLOYEE_ID)).thenReturn(count);
        Long result = projectEmployeeService.countByEmployee(EMPLOYEE_ID);
        assertEquals(count, result);
        verify(projectEmployeeRepository, times(1)).countByEmployee(EMPLOYEE_ID);
    }

    @Test
    void testCountByProjectAndEmployee() {
        Long count = 2L;
        when(projectEmployeeRepository.countByProjectAndEmployee(PROJECT_ID, EMPLOYEE_ID)).thenReturn(count);
        Long result = projectEmployeeService.countByProjectAndEmployee(PROJECT_ID, EMPLOYEE_ID);
        assertEquals(count, result);
        verify(projectEmployeeRepository, times(1)).countByProjectAndEmployee(PROJECT_ID, EMPLOYEE_ID);
    }

    // Get average hourlyrate operations tests
    @Test
    void testGetAverageHourlyRateByProject() {
        BigDecimal averageRate = new BigDecimal("47.50");
        when(projectEmployeeRepository.findAverageHourlyRateByProject(PROJECT_ID)).thenReturn(averageRate);
        BigDecimal result = projectEmployeeService.getAverageHourlyRateByProject(PROJECT_ID);
        assertEquals(averageRate, result);
        verify(projectEmployeeRepository, times(1)).findAverageHourlyRateByProject(PROJECT_ID);
    }

    @Test
    void testGetAverageHourlyRateByProjectReturnsZeroWhenNull() {
        when(projectEmployeeRepository.findAverageHourlyRateByProject(PROJECT_ID)).thenReturn(null);
        BigDecimal result = projectEmployeeService.getAverageHourlyRateByProject(PROJECT_ID);
        assertEquals(BigDecimal.ZERO, result);
        verify(projectEmployeeRepository, times(1)).findAverageHourlyRateByProject(PROJECT_ID);
    }

    @Test
    void testGetAverageHourlyRateByEmployee() {
        BigDecimal averageRate = new BigDecimal("47.50");
        when(projectEmployeeRepository.findAverageHourlyRateByEmployee(EMPLOYEE_ID)).thenReturn(averageRate);
        BigDecimal result = projectEmployeeService.getAverageHourlyRateByEmployee(EMPLOYEE_ID);
        assertEquals(averageRate, result);
        verify(projectEmployeeRepository, times(1)).findAverageHourlyRateByEmployee(EMPLOYEE_ID);
    }

    // Get statistics operations tests
    @Test
    void testGetProjectStatistics() {
        Object[] stats = new Object[] { 5L, new BigDecimal("800.00"), new BigDecimal("45.50"),
                new BigDecimal("36400.00") };
        when(projectEmployeeRepository.getProjectStatistics(PROJECT_ID)).thenReturn(stats);
        Object[] result = projectEmployeeService.getProjectStatistics(PROJECT_ID);
        assertArrayEquals(stats, result);
        verify(projectEmployeeRepository, times(1)).getProjectStatistics(PROJECT_ID);
    }

    @Test
    void testGetEmployeeStatistics() {
        Object[] stats = new Object[] { 3L, new BigDecimal("480.00"), new BigDecimal("45.50"),
                new BigDecimal("21840.00") };
        when(projectEmployeeRepository.getEmployeeStatistics(EMPLOYEE_ID)).thenReturn(stats);
        Object[] result = projectEmployeeService.getEmployeeStatistics(EMPLOYEE_ID);
        assertArrayEquals(stats, result);
        verify(projectEmployeeRepository, times(1)).getEmployeeStatistics(EMPLOYEE_ID);
    }

    // Validation and business logic tests
    @Test
    void testValidateProjectEmployee() {
        boolean result = projectEmployeeService.validateProjectEmployee(sampleProjectEmployee);
        assertTrue(result);
    }

    @Test
    void testValidateProjectEmployeeThrowsExceptionWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> projectEmployeeService.validateProjectEmployee(null));
    }

    // Edge cases and additional tests
    @Test
    void testGetByIdReturnsEmptyOptionalWhenNotFound() {
        when(projectEmployeeRepository.findById(999L)).thenReturn(Optional.empty());
        Optional<ProjectEmployee> result = projectEmployeeService.getById(999L);
        assertFalse(result.isPresent());
        verify(projectEmployeeRepository, times(1)).findById(999L);
    }

    @Test
    void testGetAllReturnsEmptyList() {
        when(projectEmployeeRepository.findAllByOrderByIdAsc()).thenReturn(Collections.emptyList());
        List<ProjectEmployee> result = projectEmployeeService.getAll();
        assertTrue(result.isEmpty());
        verify(projectEmployeeRepository, times(1)).findAllByOrderByIdAsc();
    }

    @Test
    void testGetWithNullValues() {
        ProjectEmployee nullProjectEmployee = new ProjectEmployee();
        boolean result = projectEmployeeService.validateProjectEmployee(nullProjectEmployee);
        assertTrue(result);
    }

    @Test
    void testCalculateTotalCostByEmployeeReturnsZeroWhenNull() {
        when(projectEmployeeRepository.calculateTotalCostByEmployee(EMPLOYEE_ID)).thenReturn(null);
        BigDecimal result = projectEmployeeService.calculateTotalCostByEmployee(EMPLOYEE_ID);
        assertEquals(BigDecimal.ZERO, result);
        verify(projectEmployeeRepository, times(1)).calculateTotalCostByEmployee(EMPLOYEE_ID);
    }

    @Test
    void testCalculateTotalPlannedHoursByProjectReturnsZeroWhenNull() {
        when(projectEmployeeRepository.calculateTotalPlannedHoursByProject(PROJECT_ID)).thenReturn(null);
        BigDecimal result = projectEmployeeService.calculateTotalPlannedHoursByProject(PROJECT_ID);
        assertEquals(BigDecimal.ZERO, result);
        verify(projectEmployeeRepository, times(1)).calculateTotalPlannedHoursByProject(PROJECT_ID);
    }

    @Test
    void testCalculateTotalPlannedHoursByEmployeeReturnsZeroWhenNull() {
        when(projectEmployeeRepository.calculateTotalPlannedHoursByEmployee(EMPLOYEE_ID)).thenReturn(null);
        BigDecimal result = projectEmployeeService.calculateTotalPlannedHoursByEmployee(EMPLOYEE_ID);
        assertEquals(BigDecimal.ZERO, result);
        verify(projectEmployeeRepository, times(1)).calculateTotalPlannedHoursByEmployee(EMPLOYEE_ID);
    }

    @Test
    void testGetAverageHourlyRateByEmployeeReturnsZeroWhenNull() {
        when(projectEmployeeRepository.findAverageHourlyRateByEmployee(EMPLOYEE_ID)).thenReturn(null);
        BigDecimal result = projectEmployeeService.getAverageHourlyRateByEmployee(EMPLOYEE_ID);
        assertEquals(BigDecimal.ZERO, result);
        verify(projectEmployeeRepository, times(1)).findAverageHourlyRateByEmployee(EMPLOYEE_ID);
    }
}