package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.Project;
import com.iws_manager.iws_manager_api.models.ProjectCost;
import com.iws_manager.iws_manager_api.models.ProjectPeriod;
import com.iws_manager.iws_manager_api.repositories.ProjectCostRepository;

import com.iws_manager.iws_manager_api.services.impl.ProjectCostServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectCostServiceImplTest {

    @Mock
    private ProjectCostRepository projectCostRepository;

    @InjectMocks
    private ProjectCostServiceImpl projectCostService;

    // Constantes para IDs y valores de prueba
    private static final Long PROJECT_ID_1 = 1L;
    private static final Long PROJECT_ID_99 = 99L;
    private static final Long PROJECT_PERIOD_ID_2024 = 2024L;
    private static final Long PROJECT_COST_ID_1 = 1L;
    private static final Long PROJECT_COST_ID_2 = 2L;
    private static final Long PROJECT_COST_ID_3 = 3L;
    private static final Long PROJECT_COST_ID_5 = 5L;

    // Constantes para valores BigDecimal
    private static final BigDecimal COST_10000_50 = new BigDecimal("10000.50");
    private static final BigDecimal COST_15000_75 = new BigDecimal("15000.75");
    private static final BigDecimal COST_20000_00 = new BigDecimal("20000.00");
    private static final BigDecimal COST_25000_50 = new BigDecimal("25000.50");
    private static final BigDecimal COST_15000_00 = new BigDecimal("15000.00");
    private static final BigDecimal COST_15000_50 = new BigDecimal("15000.50");
    private static final BigDecimal COST_10000_00 = new BigDecimal("10000.00");
    private static final BigDecimal COST_8000_50 = new BigDecimal("8000.50");
    private static final BigDecimal COST_2000_00 = new BigDecimal("2000.00");
    private static final BigDecimal COST_5000_00 = new BigDecimal("5000.00");
    private static final BigDecimal COST_1000_00 = new BigDecimal("1000.00");
    private static final BigDecimal COST_3000_00 = new BigDecimal("3000.00");
    private static final BigDecimal COST_6000_00 = new BigDecimal("6000.00");
    private static final BigDecimal COST_12000_00 = new BigDecimal("12000.00");

    // Constantes para tipos de costo
    private static final Byte APPROVED_TYPE = 1;
    private static final Byte PLANNED_TYPE = 2;
    private static final Byte INVALID_TYPE = 3;

    private ProjectCost projectCost;
    private Project project;
    private ProjectPeriod projectPeriod;

    @BeforeEach
    void setUp() {
        // Crear objetos para las entidades relacionadas
        project = new Project();
        project.setId(PROJECT_ID_1);

        projectPeriod = new ProjectPeriod();
        projectPeriod.setId(PROJECT_PERIOD_ID_2024);

        projectCost = new ProjectCost();
        projectCost.setId(PROJECT_COST_ID_1);
        projectCost.setApproveOrPlan(APPROVED_TYPE);
        projectCost.setCosts(COST_10000_50);
        projectCost.setProject(project);
        projectCost.setProjectPeriod(projectPeriod);
    }

    // ========== CRUD Operations Tests ==========

    @Test
    void createShouldSaveProjectCost() {
        when(projectCostRepository.save(projectCost)).thenReturn(projectCost);

        ProjectCost result = projectCostService.create(projectCost);

        assertEquals(projectCost, result);
        verify(projectCostRepository).save(projectCost);
    }

    @Test
    void createShouldThrowWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> projectCostService.create(null));
    }

    @Test
    void getByIdShouldReturnProjectCost() {
        when(projectCostRepository.findById(PROJECT_COST_ID_1)).thenReturn(Optional.of(projectCost));

        Optional<ProjectCost> result = projectCostService.getById(PROJECT_COST_ID_1);

        assertTrue(result.isPresent());
        assertEquals(projectCost, result.get());
    }

    @Test
    void getByIdShouldThrowWhenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> projectCostService.getById(null));
    }

    @Test
    void getAllShouldReturnList() {
        when(projectCostRepository.findAll()).thenReturn(List.of(projectCost));

        List<ProjectCost> result = projectCostService.getAll();

        assertEquals(1, result.size());
        assertEquals(projectCost, result.get(0));
    }

    @Test
    void updateShouldModifyAndSaveProjectCost() {
        // Crear un ProjectCost actualizado con todas las entidades requeridas
        ProjectCost updated = new ProjectCost();
        updated.setApproveOrPlan(PLANNED_TYPE);
        updated.setCosts(COST_15000_75);
        updated.setProject(project);
        updated.setProjectPeriod(projectPeriod);

        when(projectCostRepository.findById(PROJECT_COST_ID_1)).thenReturn(Optional.of(projectCost));
        when(projectCostRepository.save(any(ProjectCost.class))).thenReturn(updated);

        ProjectCost result = projectCostService.update(PROJECT_COST_ID_1, updated);

        assertEquals(PLANNED_TYPE, result.getApproveOrPlan());
        assertEquals(COST_15000_75, result.getCosts());
        verify(projectCostRepository).save(any(ProjectCost.class));
    }

    @Test
    void updateShouldThrowWhenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> projectCostService.update(null, projectCost));
    }

    @Test
    void updateShouldThrowWhenDetailsNull() {
        assertThrows(IllegalArgumentException.class, () -> projectCostService.update(PROJECT_COST_ID_1, null));
    }

    @Test
    void updateShouldThrowWhenNotFound() {
        when(projectCostRepository.findById(PROJECT_ID_99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> projectCostService.update(PROJECT_ID_99, projectCost));
    }

    @Test
    void deleteShouldDeleteProjectCost() {
        when(projectCostRepository.existsById(PROJECT_COST_ID_1)).thenReturn(true);
        doNothing().when(projectCostRepository).deleteById(PROJECT_COST_ID_1);

        projectCostService.delete(PROJECT_COST_ID_1);

        verify(projectCostRepository).deleteById(PROJECT_COST_ID_1);
    }

    @Test
    void deleteShouldThrowWhenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> projectCostService.delete(null));
    }

    @Test
    void deleteShouldThrowWhenNotFound() {
        when(projectCostRepository.existsById(PROJECT_ID_99)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> projectCostService.delete(PROJECT_ID_99));
    }

    // ========== Get Operations by Project Tests ==========

    @Test
    void getByProjectIdShouldReturnList() {
        when(projectCostRepository.findByProjectId(PROJECT_ID_1)).thenReturn(List.of(projectCost));

        List<ProjectCost> result = projectCostService.getByProjectId(PROJECT_ID_1);

        assertEquals(1, result.size());
        assertEquals(projectCost, result.get(0));
    }

    @Test
    void getByProjectIdShouldThrowWhenProjectIdNull() {
        assertThrows(IllegalArgumentException.class, () -> projectCostService.getByProjectId(null));
    }

    @Test
    void getByProjectIdOrderByCostsAscShouldReturnSortedList() {
        ProjectCost cost1 = createValidProjectCost(PROJECT_COST_ID_1, COST_5000_00);
        ProjectCost cost2 = createValidProjectCost(PROJECT_COST_ID_2, COST_10000_00);

        when(projectCostRepository.findByProjectIdOrderByProjectPeriodIdAsc(PROJECT_ID_1))
                .thenReturn(Arrays.asList(cost1, cost2));

        List<ProjectCost> result = projectCostService.getByProjectIdOrderByCostsAsc(PROJECT_ID_1);

        assertEquals(2, result.size());
        assertEquals(COST_5000_00, result.get(0).getCosts());
        assertEquals(COST_10000_00, result.get(1).getCosts());
    }

    @Test
    void getByProjectIdOrderByCostsDescShouldReturnSortedList() {
        ProjectCost cost1 = createValidProjectCost(PROJECT_COST_ID_1, COST_5000_00);
        ProjectCost cost2 = createValidProjectCost(PROJECT_COST_ID_2, COST_10000_00);

        when(projectCostRepository.findByProjectIdOrderByProjectPeriodIdAsc(PROJECT_ID_1))
                .thenReturn(Arrays.asList(cost1, cost2));

        List<ProjectCost> result = projectCostService.getByProjectIdOrderByCostsDesc(PROJECT_ID_1);

        assertEquals(2, result.size());
        assertEquals(COST_10000_00, result.get(0).getCosts());
        assertEquals(COST_5000_00, result.get(1).getCosts());
    }

    @Test
    void getByProjectIdOrderByProjectPeriodIdAscShouldReturnList() {
        when(projectCostRepository.findByProjectIdOrderByProjectPeriodIdAsc(PROJECT_ID_1))
                .thenReturn(List.of(projectCost));

        List<ProjectCost> result = projectCostService.getByProjectIdOrderByProjectPeriodIdAsc(PROJECT_ID_1);

        assertEquals(1, result.size());
        assertEquals(projectCost, result.get(0));
    }

    @Test
    void getByProjectIdOrderByApproveOrPlanAscShouldReturnList() {
        when(projectCostRepository.findByProjectIdOrderByApproveOrPlanAsc(PROJECT_ID_1))
                .thenReturn(List.of(projectCost));

        List<ProjectCost> result = projectCostService.getByProjectIdOrderByApproveOrPlanAsc(PROJECT_ID_1);

        assertEquals(1, result.size());
        assertEquals(projectCost, result.get(0));
    }

    // ========== Get Operations by Project Period Tests ==========

    @Test
    void getByProjectPeriodIdShouldReturnList() {
        when(projectCostRepository.findByProjectPeriodId(PROJECT_PERIOD_ID_2024)).thenReturn(List.of(projectCost));

        List<ProjectCost> result = projectCostService.getByProjectPeriodId(PROJECT_PERIOD_ID_2024);

        assertEquals(1, result.size());
        assertEquals(projectCost, result.get(0));
    }

    @Test
    void getByProjectPeriodIdShouldThrowWhenPeriodIdNull() {
        assertThrows(IllegalArgumentException.class, () -> projectCostService.getByProjectPeriodId(null));
    }

    // ========== Get Operations by Type Tests ==========

    @Test
    void getByApproveOrPlanShouldReturnList() {
        when(projectCostRepository.findByApproveOrPlan(APPROVED_TYPE)).thenReturn(List.of(projectCost));

        List<ProjectCost> result = projectCostService.getByApproveOrPlan(APPROVED_TYPE);

        assertEquals(1, result.size());
        assertEquals(projectCost, result.get(0));
    }

    @Test
    void getByApproveOrPlanShouldThrowWhenTypeNull() {
        assertThrows(IllegalArgumentException.class, () -> projectCostService.getByApproveOrPlan(null));
    }

    @Test
    void getApprovedCostsShouldReturnList() {
        when(projectCostRepository.findByApproveOrPlan(APPROVED_TYPE)).thenReturn(List.of(projectCost));

        List<ProjectCost> result = projectCostService.getApprovedCosts();

        assertEquals(1, result.size());
        assertEquals(APPROVED_TYPE, result.get(0).getApproveOrPlan());
    }

    @Test
    void getPlannedCostsShouldReturnList() {
        ProjectCost plannedCost = createValidProjectCost(PROJECT_COST_ID_2, COST_20000_00);
        plannedCost.setApproveOrPlan(PLANNED_TYPE);

        when(projectCostRepository.findByApproveOrPlan(PLANNED_TYPE)).thenReturn(List.of(plannedCost));

        List<ProjectCost> result = projectCostService.getPlannedCosts();

        assertEquals(1, result.size());
        assertEquals(PLANNED_TYPE, result.get(0).getApproveOrPlan());
    }

    // ========== Get Operations by Project and Period Tests ==========

    @Test
    void getByProjectIdAndProjectPeriodIdShouldReturnList() {
        when(projectCostRepository.findByProjectIdAndProjectPeriodId(PROJECT_ID_1, PROJECT_PERIOD_ID_2024))
                .thenReturn(List.of(projectCost));

        List<ProjectCost> result = projectCostService.getByProjectIdAndProjectPeriodId(PROJECT_ID_1,
                PROJECT_PERIOD_ID_2024);

        assertEquals(1, result.size());
        assertEquals(projectCost, result.get(0));
    }

    @Test
    void getByProjectIdAndProjectPeriodIdShouldThrowWhenNull() {
        assertThrows(IllegalArgumentException.class,
                () -> projectCostService.getByProjectIdAndProjectPeriodId(null, PROJECT_PERIOD_ID_2024));
        assertThrows(IllegalArgumentException.class,
                () -> projectCostService.getByProjectIdAndProjectPeriodId(PROJECT_ID_1, null));
    }

    @Test
    void getApprovedCostsByProjectAndPeriodShouldReturnList() {
        when(projectCostRepository.findApprovedCostsByProjectAndPeriod(PROJECT_ID_1, PROJECT_PERIOD_ID_2024))
                .thenReturn(List.of(projectCost));

        List<ProjectCost> result = projectCostService.getApprovedCostsByProjectAndPeriod(PROJECT_ID_1,
                PROJECT_PERIOD_ID_2024);

        assertEquals(1, result.size());
        assertEquals(APPROVED_TYPE, result.get(0).getApproveOrPlan());
    }

    @Test
    void getPlannedCostsByProjectAndPeriodShouldReturnList() {
        ProjectCost plannedCost = createValidProjectCost(PROJECT_COST_ID_2, COST_20000_00);
        plannedCost.setApproveOrPlan(PLANNED_TYPE);

        when(projectCostRepository.findPlannedCostsByProjectAndPeriod(PROJECT_ID_1, PROJECT_PERIOD_ID_2024))
                .thenReturn(List.of(plannedCost));

        List<ProjectCost> result = projectCostService.getPlannedCostsByProjectAndPeriod(PROJECT_ID_1,
                PROJECT_PERIOD_ID_2024);

        assertEquals(1, result.size());
        assertEquals(PLANNED_TYPE, result.get(0).getApproveOrPlan());
    }

    // ========== Get Operations by Project and Type Tests ==========

    @Test
    void getByProjectIdAndApproveOrPlanShouldReturnList() {
        when(projectCostRepository.findByProjectIdAndApproveOrPlan(PROJECT_ID_1, APPROVED_TYPE))
                .thenReturn(List.of(projectCost));

        List<ProjectCost> result = projectCostService.getByProjectIdAndApproveOrPlan(PROJECT_ID_1, APPROVED_TYPE);

        assertEquals(1, result.size());
        assertEquals(projectCost, result.get(0));
    }

    @Test
    void getApprovedCostsByProjectShouldReturnList() {
        when(projectCostRepository.findByProjectIdAndApproveOrPlan(PROJECT_ID_1, APPROVED_TYPE))
                .thenReturn(List.of(projectCost));

        List<ProjectCost> result = projectCostService.getApprovedCostsByProject(PROJECT_ID_1);

        assertEquals(1, result.size());
        assertEquals(APPROVED_TYPE, result.get(0).getApproveOrPlan());
    }

    @Test
    void getPlannedCostsByProjectShouldReturnList() {
        ProjectCost plannedCost = createValidProjectCost(PROJECT_COST_ID_2, COST_20000_00);
        plannedCost.setApproveOrPlan(PLANNED_TYPE);

        when(projectCostRepository.findByProjectIdAndApproveOrPlan(PROJECT_ID_1, PLANNED_TYPE))
                .thenReturn(List.of(plannedCost));

        List<ProjectCost> result = projectCostService.getPlannedCostsByProject(PROJECT_ID_1);

        assertEquals(1, result.size());
        assertEquals(PLANNED_TYPE, result.get(0).getApproveOrPlan());
    }

    // ========== Get Operations by Project, Period and Type Tests ==========

    @Test
    void getByProjectIdAndProjectPeriodIdAndApproveOrPlanShouldReturnList() {
        when(projectCostRepository.findByProjectIdAndProjectPeriodIdAndApproveOrPlan(PROJECT_ID_1,
                PROJECT_PERIOD_ID_2024, APPROVED_TYPE))
                .thenReturn(List.of(projectCost));

        List<ProjectCost> result = projectCostService.getByProjectIdAndProjectPeriodIdAndApproveOrPlan(PROJECT_ID_1,
                PROJECT_PERIOD_ID_2024, APPROVED_TYPE);

        assertEquals(1, result.size());
        assertEquals(projectCost, result.get(0));
    }

    @Test
    void getByProjectIdAndProjectPeriodIdAndApproveOrPlanShouldThrowWhenNull() {
        assertThrows(IllegalArgumentException.class,
                () -> projectCostService.getByProjectIdAndProjectPeriodIdAndApproveOrPlan(null, PROJECT_PERIOD_ID_2024,
                        APPROVED_TYPE));
        assertThrows(IllegalArgumentException.class,
                () -> projectCostService.getByProjectIdAndProjectPeriodIdAndApproveOrPlan(PROJECT_ID_1, null,
                        APPROVED_TYPE));
        assertThrows(IllegalArgumentException.class,
                () -> projectCostService.getByProjectIdAndProjectPeriodIdAndApproveOrPlan(PROJECT_ID_1,
                        PROJECT_PERIOD_ID_2024, null));
    }

    // ========== Project Totals Tests ==========

    @Test
    void getProjectTotalsShouldReturnList() {
        when(projectCostRepository.findProjectTotals(PROJECT_ID_1)).thenReturn(List.of(projectCost));

        List<ProjectCost> result = projectCostService.getProjectTotals(PROJECT_ID_1);

        assertEquals(1, result.size());
        assertEquals(projectCost, result.get(0));
    }

    @Test
    void getApprovedProjectTotalsShouldReturnList() {
        when(projectCostRepository.findApprovedProjectTotals(PROJECT_ID_1)).thenReturn(List.of(projectCost));

        List<ProjectCost> result = projectCostService.getApprovedProjectTotals(PROJECT_ID_1);

        assertEquals(1, result.size());
        assertEquals(APPROVED_TYPE, result.get(0).getApproveOrPlan());
    }

    @Test
    void getPlannedProjectTotalsShouldReturnList() {
        ProjectCost plannedCost = createValidProjectCost(PROJECT_COST_ID_2, COST_20000_00);
        plannedCost.setApproveOrPlan(PLANNED_TYPE);

        when(projectCostRepository.findPlannedProjectTotals(PROJECT_ID_1)).thenReturn(List.of(plannedCost));

        List<ProjectCost> result = projectCostService.getPlannedProjectTotals(PROJECT_ID_1);

        assertEquals(1, result.size());
        assertEquals(PLANNED_TYPE, result.get(0).getApproveOrPlan());
    }

    // ========== Sum Operations Tests ==========

    @Test
    void getTotalCostsByProjectShouldReturnSum() {
        when(projectCostRepository.sumCostsByProject(PROJECT_ID_1)).thenReturn(COST_25000_50);

        BigDecimal result = projectCostService.getTotalCostsByProject(PROJECT_ID_1);

        assertEquals(COST_25000_50, result);
    }

    @Test
    void getTotalApprovedCostsByProjectShouldReturnSum() {
        when(projectCostRepository.sumApprovedCostsByProject(PROJECT_ID_1)).thenReturn(COST_15000_50);

        BigDecimal result = projectCostService.getTotalApprovedCostsByProject(PROJECT_ID_1);

        assertEquals(COST_15000_50, result);
    }

    @Test
    void getTotalPlannedCostsByProjectShouldReturnSum() {
        when(projectCostRepository.sumPlannedCostsByProject(PROJECT_ID_1)).thenReturn(COST_10000_00);

        BigDecimal result = projectCostService.getTotalPlannedCostsByProject(PROJECT_ID_1);

        assertEquals(COST_10000_00, result);
    }

    @Test
    void getTotalCostsByProjectAndPeriodShouldReturnSum() {
        when(projectCostRepository.sumCostsByProjectAndPeriod(PROJECT_ID_1, PROJECT_PERIOD_ID_2024))
                .thenReturn(COST_10000_50);

        BigDecimal result = projectCostService.getTotalCostsByProjectAndPeriod(PROJECT_ID_1, PROJECT_PERIOD_ID_2024);

        assertEquals(COST_10000_50, result);
    }

    @Test
    void getTotalApprovedCostsByProjectAndPeriodShouldReturnSum() {
        when(projectCostRepository.sumApprovedCostsByProjectAndPeriod(PROJECT_ID_1, PROJECT_PERIOD_ID_2024))
                .thenReturn(COST_8000_50);

        BigDecimal result = projectCostService.getTotalApprovedCostsByProjectAndPeriod(PROJECT_ID_1,
                PROJECT_PERIOD_ID_2024);

        assertEquals(COST_8000_50, result);
    }

    @Test
    void getTotalPlannedCostsByProjectAndPeriodShouldReturnSum() {
        when(projectCostRepository.sumPlannedCostsByProjectAndPeriod(PROJECT_ID_1, PROJECT_PERIOD_ID_2024))
                .thenReturn(COST_2000_00);

        BigDecimal result = projectCostService.getTotalPlannedCostsByProjectAndPeriod(PROJECT_ID_1,
                PROJECT_PERIOD_ID_2024);

        assertEquals(COST_2000_00, result);
    }

    // ========== Existence Check Tests ==========

    @Test
    void existsByProjectAndPeriodShouldReturnTrue() {
        when(projectCostRepository.existsByProjectAndPeriod(PROJECT_ID_1, PROJECT_PERIOD_ID_2024)).thenReturn(true);

        boolean result = projectCostService.existsByProjectAndPeriod(PROJECT_ID_1, PROJECT_PERIOD_ID_2024);

        assertTrue(result);
    }

    @Test
    void existsApprovedCostsByProjectAndPeriodShouldReturnTrue() {
        when(projectCostRepository.existsApprovedCostsByProjectAndPeriod(PROJECT_ID_1, PROJECT_PERIOD_ID_2024))
                .thenReturn(true);

        boolean result = projectCostService.existsApprovedCostsByProjectAndPeriod(PROJECT_ID_1, PROJECT_PERIOD_ID_2024);

        assertTrue(result);
    }

    @Test
    void existsPlannedCostsByProjectAndPeriodShouldReturnTrue() {
        when(projectCostRepository.existsPlannedCostsByProjectAndPeriod(PROJECT_ID_1, PROJECT_PERIOD_ID_2024))
                .thenReturn(true);

        boolean result = projectCostService.existsPlannedCostsByProjectAndPeriod(PROJECT_ID_1, PROJECT_PERIOD_ID_2024);

        assertTrue(result);
    }

    // ========== Ordering Operations Tests ==========

    @Test
    void getAllOrderByCostsAscShouldReturnSortedList() {
        ProjectCost cost1 = createValidProjectCost(PROJECT_COST_ID_1, COST_1000_00);
        ProjectCost cost2 = createValidProjectCost(PROJECT_COST_ID_2, COST_5000_00);

        when(projectCostRepository.findAllByOrderByCostsAsc()).thenReturn(Arrays.asList(cost1, cost2));

        List<ProjectCost> result = projectCostService.getAllOrderByCostsAsc();

        assertEquals(2, result.size());
        assertEquals(COST_1000_00, result.get(0).getCosts());
        assertEquals(COST_5000_00, result.get(1).getCosts());
    }

    @Test
    void getAllOrderByCostsDescShouldReturnSortedList() {
        ProjectCost cost1 = createValidProjectCost(PROJECT_COST_ID_1, COST_1000_00);
        ProjectCost cost2 = createValidProjectCost(PROJECT_COST_ID_2, COST_5000_00);

        when(projectCostRepository.findAllByOrderByCostsAsc()).thenReturn(Arrays.asList(cost1, cost2));

        List<ProjectCost> result = projectCostService.getAllOrderByCostsDesc();

        assertEquals(2, result.size());
        assertEquals(COST_5000_00, result.get(0).getCosts());
        assertEquals(COST_1000_00, result.get(1).getCosts());
    }

    @Test
    void getAllOrderByProjectIdAscShouldReturnList() {
        when(projectCostRepository.findAllByOrderByProjectIdAsc()).thenReturn(List.of(projectCost));

        List<ProjectCost> result = projectCostService.getAllOrderByProjectIdAsc();

        assertEquals(1, result.size());
        assertEquals(projectCost, result.get(0));
    }

    @Test
    void getAllOrderByProjectPeriodIdAscShouldReturnList() {
        when(projectCostRepository.findAllByOrderByProjectPeriodIdAsc()).thenReturn(List.of(projectCost));

        List<ProjectCost> result = projectCostService.getAllOrderByProjectPeriodIdAsc();

        assertEquals(1, result.size());
        assertEquals(projectCost, result.get(0));
    }

    // ========== Range Operations Tests ==========

    @Test
    void getByCostsGreaterThanShouldReturnFilteredList() {
        ProjectCost cost1 = createValidProjectCost(PROJECT_COST_ID_1, COST_5000_00);
        ProjectCost cost2 = createValidProjectCost(PROJECT_COST_ID_2, COST_10000_00);

        when(projectCostRepository.findAll()).thenReturn(Arrays.asList(cost1, cost2));

        List<ProjectCost> result = projectCostService.getByCostsGreaterThan(COST_6000_00);

        assertEquals(1, result.size());
        assertEquals(COST_10000_00, result.get(0).getCosts());
    }

    @Test
    void getByCostsLessThanShouldReturnFilteredList() {
        ProjectCost cost1 = createValidProjectCost(PROJECT_COST_ID_1, COST_5000_00);
        ProjectCost cost2 = createValidProjectCost(PROJECT_COST_ID_2, COST_10000_00);

        when(projectCostRepository.findAll()).thenReturn(Arrays.asList(cost1, cost2));

        List<ProjectCost> result = projectCostService.getByCostsLessThan(COST_6000_00);

        assertEquals(1, result.size());
        assertEquals(COST_5000_00, result.get(0).getCosts());
    }

    @Test
    void getByCostsBetweenShouldReturnFilteredList() {
        ProjectCost cost1 = createValidProjectCost(PROJECT_COST_ID_1, COST_5000_00);
        ProjectCost cost2 = createValidProjectCost(PROJECT_COST_ID_2, COST_10000_00);
        ProjectCost cost3 = createValidProjectCost(PROJECT_COST_ID_3, COST_15000_00);

        when(projectCostRepository.findAll()).thenReturn(Arrays.asList(cost1, cost2, cost3));

        List<ProjectCost> result = projectCostService.getByCostsBetween(COST_6000_00, COST_12000_00);

        assertEquals(1, result.size());
        assertEquals(COST_10000_00, result.get(0).getCosts());
    }

    @Test
    void getByCostsBetweenShouldThrowWhenMinGreaterThanMax() {
        assertThrows(IllegalArgumentException.class,
                () -> projectCostService.getByCostsBetween(COST_10000_00, COST_5000_00));
    }

    // ========== Validation Tests ==========

    @Test
    void validateProjectCostShouldReturnTrueForValid() {
        // Usar el projectCost configurado en setUp que tiene todas las entidades
        // requeridas
        boolean result = projectCostService.validateProjectCost(projectCost);
        assertTrue(result);
    }

    @Test
    void validateProjectCostShouldThrowWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> projectCostService.validateProjectCost(null));
    }

    @Test
    void validateProjectCostShouldThrowWhenApproveOrPlanNull() {
        ProjectCost invalidCost = new ProjectCost();
        invalidCost.setApproveOrPlan(null);

        assertThrows(IllegalArgumentException.class, () -> projectCostService.validateProjectCost(invalidCost));
    }

    @Test
    void validateProjectCostShouldThrowWhenInvalidApproveOrPlan() {
        ProjectCost invalidCost = new ProjectCost();
        invalidCost.setApproveOrPlan(INVALID_TYPE);

        assertThrows(IllegalArgumentException.class, () -> projectCostService.validateProjectCost(invalidCost));
    }

    @Test
    void validateProjectCostShouldThrowWhenProjectNull() {
        ProjectCost invalidCost = new ProjectCost();
        invalidCost.setApproveOrPlan(APPROVED_TYPE);
        invalidCost.setProject(null);

        assertThrows(IllegalArgumentException.class, () -> projectCostService.validateProjectCost(invalidCost));
    }

    @Test
    void validateProjectCostShouldThrowWhenProjectPeriodNull() {
        ProjectCost invalidCost = new ProjectCost();
        invalidCost.setApproveOrPlan(APPROVED_TYPE);
        invalidCost.setProject(new Project());
        invalidCost.setProjectPeriod(null);

        assertThrows(IllegalArgumentException.class, () -> projectCostService.validateProjectCost(invalidCost));
    }

    // ========== Can Create Tests ==========

    @Test
    void canCreateProjectCostShouldReturnFalseWhenNull() {
        boolean result = projectCostService.canCreateProjectCost(null, PROJECT_PERIOD_ID_2024, APPROVED_TYPE);
        assertFalse(result);

        result = projectCostService.canCreateProjectCost(PROJECT_ID_1, null, APPROVED_TYPE);
        assertFalse(result);

        result = projectCostService.canCreateProjectCost(PROJECT_ID_1, PROJECT_PERIOD_ID_2024, null);
        assertFalse(result);
    }

    @Test
    void canCreateProjectCostShouldReturnTrueWhenCostExists() {
        when(projectCostRepository.findByProjectIdAndProjectPeriodIdAndApproveOrPlan(PROJECT_ID_1,
                PROJECT_PERIOD_ID_2024, APPROVED_TYPE))
                .thenReturn(List.of(projectCost));

        boolean result = projectCostService.canCreateProjectCost(PROJECT_ID_1, PROJECT_PERIOD_ID_2024, APPROVED_TYPE);

        assertTrue(result);
    }

    @Test
    void canCreateProjectCostShouldReturnFalseWhenCostNotExists() {
        when(projectCostRepository.findByProjectIdAndProjectPeriodIdAndApproveOrPlan(PROJECT_ID_1,
                PROJECT_PERIOD_ID_2024, APPROVED_TYPE))
                .thenReturn(Collections.emptyList());

        boolean result = projectCostService.canCreateProjectCost(PROJECT_ID_1, PROJECT_PERIOD_ID_2024, APPROVED_TYPE);

        assertFalse(result);
    }

    // ========== Bulk Operations Tests ==========

    @Test
    void createAllShouldSaveAllProjectCosts() {
        List<ProjectCost> costs = Arrays.asList(
                createValidProjectCost(PROJECT_COST_ID_1, COST_10000_00),
                createValidProjectCost(PROJECT_COST_ID_2, COST_20000_00));

        when(projectCostRepository.saveAll(costs)).thenReturn(costs);

        List<ProjectCost> result = projectCostService.createAll(costs);

        assertEquals(2, result.size());
        verify(projectCostRepository).saveAll(costs);
    }

    @Test
    void createAllShouldThrowWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> projectCostService.createAll(null));
    }

    @Test
    void createAllShouldThrowWhenEmpty() {
        assertThrows(IllegalArgumentException.class, () -> projectCostService.createAll(Collections.emptyList()));
    }

    @Test
    void deleteByProjectIdShouldDeleteAll() {
        List<ProjectCost> costs = List.of(projectCost);
        when(projectCostRepository.findByProjectId(PROJECT_ID_1)).thenReturn(costs);
        doNothing().when(projectCostRepository).deleteAll(costs);

        projectCostService.deleteByProjectId(PROJECT_ID_1);

        verify(projectCostRepository).deleteAll(costs);
    }

    @Test
    void deleteByProjectPeriodIdShouldDeleteAll() {
        List<ProjectCost> costs = List.of(projectCost);
        when(projectCostRepository.findByProjectPeriodId(PROJECT_PERIOD_ID_2024)).thenReturn(costs);
        doNothing().when(projectCostRepository).deleteAll(costs);

        projectCostService.deleteByProjectPeriodId(PROJECT_PERIOD_ID_2024);

        verify(projectCostRepository).deleteAll(costs);
    }

    // ========== Utility Methods Tests ==========

    @Test
    void getMaxIdShouldReturnMaximumId() {
        ProjectCost cost1 = createValidProjectCost(PROJECT_COST_ID_1, COST_1000_00);
        ProjectCost cost2 = createValidProjectCost(PROJECT_COST_ID_5, COST_5000_00);
        ProjectCost cost3 = createValidProjectCost(PROJECT_COST_ID_3, COST_3000_00);

        when(projectCostRepository.findAll()).thenReturn(Arrays.asList(cost1, cost2, cost3));

        Long result = projectCostService.getMaxId();

        assertEquals(PROJECT_COST_ID_5, result);
    }

    @Test
    void getMaxIdShouldReturnZeroWhenEmpty() {
        when(projectCostRepository.findAll()).thenReturn(Collections.emptyList());

        Long result = projectCostService.getMaxId();

        assertEquals(0L, result);
    }

    @Test
    void getByProjectIdWithNullCostsShouldReturnFilteredList() {
        ProjectCost costWithNull = createValidProjectCost(PROJECT_COST_ID_1, null);
        ProjectCost costWithValue = createValidProjectCost(PROJECT_COST_ID_2, COST_5000_00);

        when(projectCostRepository.findByProjectId(PROJECT_ID_1))
                .thenReturn(Arrays.asList(costWithNull, costWithValue));

        List<ProjectCost> result = projectCostService.getByProjectIdWithNullCosts(PROJECT_ID_1);

        assertEquals(1, result.size());
        assertNull(result.get(0).getCosts());
    }

    // ========== Helper Method ==========

    private ProjectCost createValidProjectCost(Long id, BigDecimal costs) {
        ProjectCost newCost = new ProjectCost();
        newCost.setId(id);
        newCost.setApproveOrPlan(APPROVED_TYPE);
        newCost.setCosts(costs);

        Project project1 = new Project();
        project1.setId(PROJECT_ID_1);
        newCost.setProject(project1);

        ProjectPeriod period = new ProjectPeriod();
        period.setId(PROJECT_PERIOD_ID_2024);
        newCost.setProjectPeriod(period);

        return newCost;
    }
}