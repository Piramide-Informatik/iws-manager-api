package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.ProjectCost;
import com.iws_manager.iws_manager_api.services.interfaces.ProjectCostService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProjectCostControllerTest {

    private static final Long ID = 1L;
    private static final Long PROJECT_ID = 2L;
    private static final Long PROJECT_PERIOD_ID = 3L;
    private static final Byte APPROVE_OR_PLAN = 1;
    private static final Byte PLANNED_TYPE = 2;

    // Constantes para valores numéricos repetidos
    private static final String COSTS_VALUE = "15000.50";
    private static final String TOTAL_VALUE_1 = "35000.25";
    private static final String TOTAL_VALUE_2 = "20000.75";
    private static final String AMOUNT_10000 = "10000.00";
    private static final String AMOUNT_30000 = "30000.00";

    private static final BigDecimal COSTS = new BigDecimal(COSTS_VALUE);
    private static final BigDecimal TOTAL_1 = new BigDecimal(TOTAL_VALUE_1);
    private static final BigDecimal TOTAL_2 = new BigDecimal(TOTAL_VALUE_2);
    private static final BigDecimal AMOUNT_10K = new BigDecimal(AMOUNT_10000);
    private static final BigDecimal AMOUNT_30K = new BigDecimal(AMOUNT_30000);

    @Mock
    private ProjectCostService projectCostService;

    @InjectMocks
    private ProjectCostController projectCostController;

    private ProjectCost projectCost;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        projectCost = new ProjectCost();
        projectCost.setId(ID);
        projectCost.setApproveOrPlan(APPROVE_OR_PLAN);
        projectCost.setCosts(COSTS);
    }

    // Basic CRUD operations tests

    @Test
    void createReturnsCreatedProjectCost() {
        when(projectCostService.create(any(ProjectCost.class))).thenReturn(projectCost);

        ResponseEntity<ProjectCost> response = projectCostController.create(projectCost);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(projectCost, response.getBody());
        verify(projectCostService, times(1)).create(any(ProjectCost.class));
    }

    @Test
    void getByIdFound() {
        when(projectCostService.getById(ID)).thenReturn(Optional.of(projectCost));

        ResponseEntity<ProjectCost> response = projectCostController.getById(ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projectCost, response.getBody());
    }

    @Test
    void getByIdNotFound() {
        when(projectCostService.getById(ID)).thenReturn(Optional.empty());

        ResponseEntity<ProjectCost> response = projectCostController.getById(ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAllReturnsList() {
        List<ProjectCost> list = Arrays.asList(projectCost);
        when(projectCostService.getAll()).thenReturn(list);

        ResponseEntity<List<ProjectCost>> response = projectCostController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void updateFound() {
        when(projectCostService.update(eq(ID), any(ProjectCost.class))).thenReturn(projectCost);

        ResponseEntity<ProjectCost> response = projectCostController.update(ID, projectCost);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projectCost, response.getBody());
    }

    @Test
    void updateNotFound() {
        when(projectCostService.update(eq(ID), any(ProjectCost.class)))
                .thenThrow(new EntityNotFoundException("ProjectCost not found with id: " + ID));

        assertThrows(EntityNotFoundException.class, () -> projectCostController.update(ID, projectCost));
        verify(projectCostService, times(1)).update(eq(ID), any(ProjectCost.class));
    }

    @Test
    void deleteFound() {
        doNothing().when(projectCostService).delete(ID);

        ResponseEntity<Void> response = projectCostController.delete(ID);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(projectCostService, times(1)).delete(ID);
    }

    @Test
    void deleteNotFound() {
        doThrow(new EntityNotFoundException("ProjectCost not found with id: " + ID))
                .when(projectCostService).delete(ID);

        assertThrows(EntityNotFoundException.class, () -> projectCostController.delete(ID));
        verify(projectCostService, times(1)).delete(ID);
    }

    // Get operations by project tests

    @Test
    void getByProjectIdReturnsList() {
        List<ProjectCost> list = Arrays.asList(projectCost);
        when(projectCostService.getByProjectId(PROJECT_ID)).thenReturn(list);

        ResponseEntity<List<ProjectCost>> response = projectCostController.getByProjectId(PROJECT_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByProjectIdOrderByCostsAscReturnsList() {
        List<ProjectCost> list = Arrays.asList(projectCost);
        when(projectCostService.getByProjectIdOrderByCostsAsc(PROJECT_ID)).thenReturn(list);

        ResponseEntity<List<ProjectCost>> response = projectCostController.getByProjectIdOrderByCostsAsc(PROJECT_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByProjectIdOrderByCostsDescReturnsList() {
        List<ProjectCost> list = Arrays.asList(projectCost);
        when(projectCostService.getByProjectIdOrderByCostsDesc(PROJECT_ID)).thenReturn(list);

        ResponseEntity<List<ProjectCost>> response = projectCostController.getByProjectIdOrderByCostsDesc(PROJECT_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByProjectIdOrderByProjectPeriodIdAscReturnsList() {
        List<ProjectCost> list = Arrays.asList(projectCost);
        when(projectCostService.getByProjectIdOrderByProjectPeriodIdAsc(PROJECT_ID)).thenReturn(list);

        ResponseEntity<List<ProjectCost>> response = projectCostController
                .getByProjectIdOrderByProjectPeriodIdAsc(PROJECT_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByProjectIdOrderByApproveOrPlanAscReturnsList() {
        List<ProjectCost> list = Arrays.asList(projectCost);
        when(projectCostService.getByProjectIdOrderByApproveOrPlanAsc(PROJECT_ID)).thenReturn(list);

        ResponseEntity<List<ProjectCost>> response = projectCostController
                .getByProjectIdOrderByApproveOrPlanAsc(PROJECT_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    // Get operations by project period tests

    @Test
    void getByProjectPeriodIdReturnsList() {
        List<ProjectCost> list = Arrays.asList(projectCost);
        when(projectCostService.getByProjectPeriodId(PROJECT_PERIOD_ID)).thenReturn(list);

        ResponseEntity<List<ProjectCost>> response = projectCostController.getByProjectPeriodId(PROJECT_PERIOD_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByProjectPeriodIdOrderByCostsAscReturnsList() {
        List<ProjectCost> list = Arrays.asList(projectCost);
        when(projectCostService.getByProjectPeriodIdOrderByCostsAsc(PROJECT_PERIOD_ID)).thenReturn(list);

        ResponseEntity<List<ProjectCost>> response = projectCostController
                .getByProjectPeriodIdOrderByCostsAsc(PROJECT_PERIOD_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByProjectPeriodIdOrderByCostsDescReturnsList() {
        List<ProjectCost> list = Arrays.asList(projectCost);
        when(projectCostService.getByProjectPeriodIdOrderByCostsDesc(PROJECT_PERIOD_ID)).thenReturn(list);

        ResponseEntity<List<ProjectCost>> response = projectCostController
                .getByProjectPeriodIdOrderByCostsDesc(PROJECT_PERIOD_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    // Get operations by type tests

    @Test
    void getByApproveOrPlanReturnsList() {
        List<ProjectCost> list = Arrays.asList(projectCost);
        when(projectCostService.getByApproveOrPlan(APPROVE_OR_PLAN)).thenReturn(list);

        ResponseEntity<List<ProjectCost>> response = projectCostController.getByApproveOrPlan(APPROVE_OR_PLAN);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getApprovedCostsReturnsList() {
        List<ProjectCost> list = Arrays.asList(projectCost);
        when(projectCostService.getApprovedCosts()).thenReturn(list);

        ResponseEntity<List<ProjectCost>> response = projectCostController.getApprovedCosts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getPlannedCostsReturnsList() {
        projectCost.setApproveOrPlan(PLANNED_TYPE);
        List<ProjectCost> list = Arrays.asList(projectCost);
        when(projectCostService.getPlannedCosts()).thenReturn(list);

        ResponseEntity<List<ProjectCost>> response = projectCostController.getPlannedCosts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    // Get operations by project and period tests

    @Test
    void getByProjectIdAndProjectPeriodIdReturnsList() {
        List<ProjectCost> list = Arrays.asList(projectCost);
        when(projectCostService.getByProjectIdAndProjectPeriodId(PROJECT_ID, PROJECT_PERIOD_ID)).thenReturn(list);

        ResponseEntity<List<ProjectCost>> response = projectCostController.getByProjectIdAndProjectPeriodId(PROJECT_ID,
                PROJECT_PERIOD_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getApprovedCostsByProjectAndPeriodReturnsList() {
        List<ProjectCost> list = Arrays.asList(projectCost);
        when(projectCostService.getApprovedCostsByProjectAndPeriod(PROJECT_ID, PROJECT_PERIOD_ID)).thenReturn(list);

        ResponseEntity<List<ProjectCost>> response = projectCostController
                .getApprovedCostsByProjectAndPeriod(PROJECT_ID, PROJECT_PERIOD_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getPlannedCostsByProjectAndPeriodReturnsList() {
        projectCost.setApproveOrPlan(PLANNED_TYPE);
        List<ProjectCost> list = Arrays.asList(projectCost);
        when(projectCostService.getPlannedCostsByProjectAndPeriod(PROJECT_ID, PROJECT_PERIOD_ID)).thenReturn(list);

        ResponseEntity<List<ProjectCost>> response = projectCostController.getPlannedCostsByProjectAndPeriod(PROJECT_ID,
                PROJECT_PERIOD_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    // Get operations by project and type tests

    @Test
    void getByProjectIdAndApproveOrPlanReturnsList() {
        List<ProjectCost> list = Arrays.asList(projectCost);
        when(projectCostService.getByProjectIdAndApproveOrPlan(PROJECT_ID, APPROVE_OR_PLAN)).thenReturn(list);

        ResponseEntity<List<ProjectCost>> response = projectCostController.getByProjectIdAndApproveOrPlan(PROJECT_ID,
                APPROVE_OR_PLAN);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getApprovedCostsByProjectReturnsList() {
        List<ProjectCost> list = Arrays.asList(projectCost);
        when(projectCostService.getApprovedCostsByProject(PROJECT_ID)).thenReturn(list);

        ResponseEntity<List<ProjectCost>> response = projectCostController.getApprovedCostsByProject(PROJECT_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getPlannedCostsByProjectReturnsList() {
        projectCost.setApproveOrPlan(PLANNED_TYPE);
        List<ProjectCost> list = Arrays.asList(projectCost);
        when(projectCostService.getPlannedCostsByProject(PROJECT_ID)).thenReturn(list);

        ResponseEntity<List<ProjectCost>> response = projectCostController.getPlannedCostsByProject(PROJECT_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    // Get operations by project, period and type tests

    @Test
    void getByProjectIdAndProjectPeriodIdAndApproveOrPlanReturnsList() {
        List<ProjectCost> list = Arrays.asList(projectCost);
        when(projectCostService.getByProjectIdAndProjectPeriodIdAndApproveOrPlan(PROJECT_ID, PROJECT_PERIOD_ID,
                APPROVE_OR_PLAN)).thenReturn(list);

        ResponseEntity<List<ProjectCost>> response = projectCostController
                .getByProjectIdAndProjectPeriodIdAndApproveOrPlan(PROJECT_ID, PROJECT_PERIOD_ID, APPROVE_OR_PLAN);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    // Project totals tests

    @Test
    void getProjectTotalsReturnsList() {
        List<ProjectCost> list = Arrays.asList(projectCost);
        when(projectCostService.getProjectTotals(PROJECT_ID)).thenReturn(list);

        ResponseEntity<List<ProjectCost>> response = projectCostController.getProjectTotals(PROJECT_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getApprovedProjectTotalsReturnsList() {
        List<ProjectCost> list = Arrays.asList(projectCost);
        when(projectCostService.getApprovedProjectTotals(PROJECT_ID)).thenReturn(list);

        ResponseEntity<List<ProjectCost>> response = projectCostController.getApprovedProjectTotals(PROJECT_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getPlannedProjectTotalsReturnsList() {
        projectCost.setApproveOrPlan(PLANNED_TYPE);
        List<ProjectCost> list = Arrays.asList(projectCost);
        when(projectCostService.getPlannedProjectTotals(PROJECT_ID)).thenReturn(list);

        ResponseEntity<List<ProjectCost>> response = projectCostController.getPlannedProjectTotals(PROJECT_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    // Sum operations tests

    @Test
    void getTotalCostsByProjectReturnsBigDecimal() {
        when(projectCostService.getTotalCostsByProject(PROJECT_ID)).thenReturn(TOTAL_1);

        ResponseEntity<BigDecimal> response = projectCostController.getTotalCostsByProject(PROJECT_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(TOTAL_1, response.getBody());
    }

    @Test
    void getTotalApprovedCostsByProjectReturnsBigDecimal() {
        when(projectCostService.getTotalApprovedCostsByProject(PROJECT_ID)).thenReturn(COSTS);

        ResponseEntity<BigDecimal> response = projectCostController.getTotalApprovedCostsByProject(PROJECT_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(COSTS, response.getBody());
    }

    @Test
    void getTotalPlannedCostsByProjectReturnsBigDecimal() {
        when(projectCostService.getTotalPlannedCostsByProject(PROJECT_ID)).thenReturn(TOTAL_2);

        ResponseEntity<BigDecimal> response = projectCostController.getTotalPlannedCostsByProject(PROJECT_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(TOTAL_2, response.getBody());
    }

    @Test
    void getTotalCostsByProjectAndPeriodReturnsBigDecimal() {
        when(projectCostService.getTotalCostsByProjectAndPeriod(PROJECT_ID, PROJECT_PERIOD_ID)).thenReturn(COSTS);

        ResponseEntity<BigDecimal> response = projectCostController.getTotalCostsByProjectAndPeriod(PROJECT_ID,
                PROJECT_PERIOD_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(COSTS, response.getBody());
    }

    @Test
    void getTotalApprovedCostsByProjectAndPeriodReturnsBigDecimal() {
        when(projectCostService.getTotalApprovedCostsByProjectAndPeriod(PROJECT_ID, PROJECT_PERIOD_ID))
                .thenReturn(COSTS);

        ResponseEntity<BigDecimal> response = projectCostController.getTotalApprovedCostsByProjectAndPeriod(PROJECT_ID,
                PROJECT_PERIOD_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(COSTS, response.getBody());
    }

    @Test
    void getTotalPlannedCostsByProjectAndPeriodReturnsBigDecimal() {
        when(projectCostService.getTotalPlannedCostsByProjectAndPeriod(PROJECT_ID, PROJECT_PERIOD_ID))
                .thenReturn(TOTAL_2);

        ResponseEntity<BigDecimal> response = projectCostController.getTotalPlannedCostsByProjectAndPeriod(PROJECT_ID,
                PROJECT_PERIOD_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(TOTAL_2, response.getBody());
    }

    // Check existence operations tests

    @Test
    void existsByProjectAndPeriodReturnsTrue() {
        when(projectCostService.existsByProjectAndPeriod(PROJECT_ID, PROJECT_PERIOD_ID)).thenReturn(true);

        ResponseEntity<Boolean> response = projectCostController.existsByProjectAndPeriod(PROJECT_ID,
                PROJECT_PERIOD_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
    }

    @Test
    void existsByProjectAndPeriodReturnsFalse() {
        when(projectCostService.existsByProjectAndPeriod(PROJECT_ID, PROJECT_PERIOD_ID)).thenReturn(false);

        ResponseEntity<Boolean> response = projectCostController.existsByProjectAndPeriod(PROJECT_ID,
                PROJECT_PERIOD_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(false, response.getBody());
    }

    @Test
    void existsApprovedCostsByProjectAndPeriodReturnsTrue() {
        when(projectCostService.existsApprovedCostsByProjectAndPeriod(PROJECT_ID, PROJECT_PERIOD_ID)).thenReturn(true);

        ResponseEntity<Boolean> response = projectCostController.existsApprovedCostsByProjectAndPeriod(PROJECT_ID,
                PROJECT_PERIOD_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
    }

    @Test
    void existsPlannedCostsByProjectAndPeriodReturnsTrue() {
        when(projectCostService.existsPlannedCostsByProjectAndPeriod(PROJECT_ID, PROJECT_PERIOD_ID)).thenReturn(true);

        ResponseEntity<Boolean> response = projectCostController.existsPlannedCostsByProjectAndPeriod(PROJECT_ID,
                PROJECT_PERIOD_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
    }

    // Ordering operations tests

    @Test
    void getAllOrderByCostsAscReturnsList() {
        List<ProjectCost> list = Arrays.asList(projectCost);
        when(projectCostService.getAllOrderByCostsAsc()).thenReturn(list);

        ResponseEntity<List<ProjectCost>> response = projectCostController.getAllOrderByCostsAsc();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getAllOrderByCostsDescReturnsList() {
        List<ProjectCost> list = Arrays.asList(projectCost);
        when(projectCostService.getAllOrderByCostsDesc()).thenReturn(list);

        ResponseEntity<List<ProjectCost>> response = projectCostController.getAllOrderByCostsDesc();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getAllOrderByProjectIdAscReturnsList() {
        List<ProjectCost> list = Arrays.asList(projectCost);
        when(projectCostService.getAllOrderByProjectIdAsc()).thenReturn(list);

        ResponseEntity<List<ProjectCost>> response = projectCostController.getAllOrderByProjectIdAsc();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getAllOrderByProjectPeriodIdAscReturnsList() {
        List<ProjectCost> list = Arrays.asList(projectCost);
        when(projectCostService.getAllOrderByProjectPeriodIdAsc()).thenReturn(list);

        ResponseEntity<List<ProjectCost>> response = projectCostController.getAllOrderByProjectPeriodIdAsc();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    // Range operations tests

    @Test
    void getByCostsGreaterThanReturnsList() {
        List<ProjectCost> list = Arrays.asList(projectCost);
        when(projectCostService.getByCostsGreaterThan(AMOUNT_10K)).thenReturn(list);

        ResponseEntity<List<ProjectCost>> response = projectCostController.getByCostsGreaterThan(AMOUNT_10K);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByCostsLessThanReturnsList() {
        List<ProjectCost> list = Arrays.asList(projectCost);
        when(projectCostService.getByCostsLessThan(AMOUNT_30K)).thenReturn(list);

        ResponseEntity<List<ProjectCost>> response = projectCostController.getByCostsLessThan(AMOUNT_30K);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByCostsBetweenReturnsList() {
        List<ProjectCost> list = Arrays.asList(projectCost);
        when(projectCostService.getByCostsBetween(AMOUNT_10K, AMOUNT_30K)).thenReturn(list);

        ResponseEntity<List<ProjectCost>> response = projectCostController.getByCostsBetween(AMOUNT_10K, AMOUNT_30K);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    // Validation and business logic tests

    @Test
    void validateProjectCostReturnsTrue() {
        when(projectCostService.validateProjectCost(any(ProjectCost.class))).thenReturn(true);

        ResponseEntity<Boolean> response = projectCostController.validateProjectCost(projectCost);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
    }

    @Test
    void canCreateProjectCostReturnsTrue() {
        when(projectCostService.canCreateProjectCost(PROJECT_ID, PROJECT_PERIOD_ID, APPROVE_OR_PLAN)).thenReturn(true);

        ResponseEntity<Boolean> response = projectCostController.canCreateProjectCost(PROJECT_ID, PROJECT_PERIOD_ID,
                APPROVE_OR_PLAN);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
    }

    @Test
    void canCreateProjectCostReturnsFalse() {
        when(projectCostService.canCreateProjectCost(PROJECT_ID, PROJECT_PERIOD_ID, APPROVE_OR_PLAN)).thenReturn(false);

        ResponseEntity<Boolean> response = projectCostController.canCreateProjectCost(PROJECT_ID, PROJECT_PERIOD_ID,
                APPROVE_OR_PLAN);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(false, response.getBody());
    }

    // Bulk operations tests

    @Test
    void createAllReturnsList() {
        List<ProjectCost> list = Arrays.asList(projectCost);
        when(projectCostService.createAll(anyList())).thenReturn(list);

        ResponseEntity<List<ProjectCost>> response = projectCostController.createAll(list);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void deleteByProjectIdReturnsNoContent() {
        doNothing().when(projectCostService).deleteByProjectId(PROJECT_ID);

        ResponseEntity<Void> response = projectCostController.deleteByProjectId(PROJECT_ID);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(projectCostService, times(1)).deleteByProjectId(PROJECT_ID);
    }

    @Test
    void deleteByProjectPeriodIdReturnsNoContent() {
        doNothing().when(projectCostService).deleteByProjectPeriodId(PROJECT_PERIOD_ID);

        ResponseEntity<Void> response = projectCostController.deleteByProjectPeriodId(PROJECT_PERIOD_ID);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(projectCostService, times(1)).deleteByProjectPeriodId(PROJECT_PERIOD_ID);
    }

    // Utility methods tests

    @Test
    void getMaxIdReturnsLong() {
        Long maxId = 100L;
        when(projectCostService.getMaxId()).thenReturn(maxId);

        ResponseEntity<Long> response = projectCostController.getMaxId();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(maxId, response.getBody());
    }

    @Test
    void getByProjectIdWithNullCostsReturnsList() {
        projectCost.setCosts(null);
        List<ProjectCost> list = Arrays.asList(projectCost);
        when(projectCostService.getByProjectIdWithNullCosts(PROJECT_ID)).thenReturn(list);

        ResponseEntity<List<ProjectCost>> response = projectCostController.getByProjectIdWithNullCosts(PROJECT_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    // Edge cases and error handling tests

    @Test
    void createThrowsIllegalArgumentException() {
        when(projectCostService.create(any(ProjectCost.class)))
                .thenThrow(new IllegalArgumentException("Invalid approveOrPlan value"));

        assertThrows(IllegalArgumentException.class, () -> projectCostController.create(projectCost));
        verify(projectCostService, times(1)).create(any(ProjectCost.class));
    }

    @Test
    void getByCostsBetweenThrowsIllegalArgumentException() {
        when(projectCostService.getByCostsBetween(AMOUNT_30K, AMOUNT_10K))
                .thenThrow(new IllegalArgumentException("Min amount cannot be greater than max amount"));

        assertThrows(IllegalArgumentException.class,
                () -> projectCostController.getByCostsBetween(AMOUNT_30K, AMOUNT_10K));
        verify(projectCostService, times(1)).getByCostsBetween(AMOUNT_30K, AMOUNT_10K);
    }

    @Test
    void getAllReturnsEmptyList() {
        when(projectCostService.getAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<ProjectCost>> response = projectCostController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }
}