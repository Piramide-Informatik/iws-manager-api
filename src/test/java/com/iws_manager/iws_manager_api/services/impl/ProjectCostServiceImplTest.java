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

    private ProjectCost projectCost;
    private Project project;
    private ProjectPeriod projectPeriod;

    @BeforeEach
    void setUp() {
        // Crear objetos mock para las entidades relacionadas
        project = new Project();
        project.setId(1L);

        projectPeriod = new ProjectPeriod();
        projectPeriod.setId(2024L);

        projectCost = new ProjectCost();
        projectCost.setId(1L);
        projectCost.setApproveOrPlan((byte) 1); // Approved
        projectCost.setCosts(new BigDecimal("10000.50"));
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
        when(projectCostRepository.findById(1L)).thenReturn(Optional.of(projectCost));

        Optional<ProjectCost> result = projectCostService.getById(1L);

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
        updated.setApproveOrPlan((byte) 2); // Planned
        updated.setCosts(new BigDecimal("15000.75"));
        updated.setProject(project);
        updated.setProjectPeriod(projectPeriod);

        when(projectCostRepository.findById(1L)).thenReturn(Optional.of(projectCost));
        when(projectCostRepository.save(any(ProjectCost.class))).thenReturn(updated);

        ProjectCost result = projectCostService.update(1L, updated);

        assertEquals((byte) 2, result.getApproveOrPlan());
        assertEquals(new BigDecimal("15000.75"), result.getCosts());
        verify(projectCostRepository).save(any(ProjectCost.class));
    }

    @Test
    void updateShouldThrowWhenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> projectCostService.update(null, projectCost));
    }

    @Test
    void updateShouldThrowWhenDetailsNull() {
        assertThrows(IllegalArgumentException.class, () -> projectCostService.update(1L, null));
    }

    @Test
    void updateShouldThrowWhenNotFound() {
        when(projectCostRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> projectCostService.update(99L, projectCost));
    }

    @Test
    void deleteShouldDeleteProjectCost() {
        when(projectCostRepository.existsById(1L)).thenReturn(true);
        doNothing().when(projectCostRepository).deleteById(1L);

        projectCostService.delete(1L);

        verify(projectCostRepository).deleteById(1L);
    }

    @Test
    void deleteShouldThrowWhenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> projectCostService.delete(null));
    }

    @Test
    void deleteShouldThrowWhenNotFound() {
        when(projectCostRepository.existsById(99L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> projectCostService.delete(99L));
    }

    // ========== Get Operations by Project Tests ==========

    @Test
    void getByProjectIdShouldReturnList() {
        when(projectCostRepository.findByProjectId(1L)).thenReturn(List.of(projectCost));

        List<ProjectCost> result = projectCostService.getByProjectId(1L);

        assertEquals(1, result.size());
        assertEquals(projectCost, result.get(0));
    }

    @Test
    void getByProjectIdShouldThrowWhenProjectIdNull() {
        assertThrows(IllegalArgumentException.class, () -> projectCostService.getByProjectId(null));
    }

    @Test
    void getByProjectIdOrderByCostsAscShouldReturnSortedList() {
        ProjectCost cost1 = createValidProjectCost(1L, new BigDecimal("5000.00"));
        ProjectCost cost2 = createValidProjectCost(2L, new BigDecimal("10000.00"));

        when(projectCostRepository.findByProjectIdOrderByProjectPeriodIdAsc(1L))
                .thenReturn(Arrays.asList(cost1, cost2));

        List<ProjectCost> result = projectCostService.getByProjectIdOrderByCostsAsc(1L);

        assertEquals(2, result.size());
        assertEquals(new BigDecimal("5000.00"), result.get(0).getCosts());
        assertEquals(new BigDecimal("10000.00"), result.get(1).getCosts());
    }

    @Test
    void getByProjectIdOrderByCostsDescShouldReturnSortedList() {
        ProjectCost cost1 = createValidProjectCost(1L, new BigDecimal("5000.00"));
        ProjectCost cost2 = createValidProjectCost(2L, new BigDecimal("10000.00"));

        when(projectCostRepository.findByProjectIdOrderByProjectPeriodIdAsc(1L))
                .thenReturn(Arrays.asList(cost1, cost2));

        List<ProjectCost> result = projectCostService.getByProjectIdOrderByCostsDesc(1L);

        assertEquals(2, result.size());
        assertEquals(new BigDecimal("10000.00"), result.get(0).getCosts());
        assertEquals(new BigDecimal("5000.00"), result.get(1).getCosts());
    }

    @Test
    void getByProjectIdOrderByProjectPeriodIdAscShouldReturnList() {
        when(projectCostRepository.findByProjectIdOrderByProjectPeriodIdAsc(1L))
                .thenReturn(List.of(projectCost));

        List<ProjectCost> result = projectCostService.getByProjectIdOrderByProjectPeriodIdAsc(1L);

        assertEquals(1, result.size());
        assertEquals(projectCost, result.get(0));
    }

    @Test
    void getByProjectIdOrderByApproveOrPlanAscShouldReturnList() {
        when(projectCostRepository.findByProjectIdOrderByApproveOrPlanAsc(1L))
                .thenReturn(List.of(projectCost));

        List<ProjectCost> result = projectCostService.getByProjectIdOrderByApproveOrPlanAsc(1L);

        assertEquals(1, result.size());
        assertEquals(projectCost, result.get(0));
    }

    // ========== Get Operations by Project Period Tests ==========

    @Test
    void getByProjectPeriodIdShouldReturnList() {
        when(projectCostRepository.findByProjectPeriodId(2024L)).thenReturn(List.of(projectCost));

        List<ProjectCost> result = projectCostService.getByProjectPeriodId(2024L);

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
        when(projectCostRepository.findByApproveOrPlan((byte) 1)).thenReturn(List.of(projectCost));

        List<ProjectCost> result = projectCostService.getByApproveOrPlan((byte) 1);

        assertEquals(1, result.size());
        assertEquals(projectCost, result.get(0));
    }

    @Test
    void getByApproveOrPlanShouldThrowWhenTypeNull() {
        assertThrows(IllegalArgumentException.class, () -> projectCostService.getByApproveOrPlan(null));
    }

    @Test
    void getApprovedCostsShouldReturnList() {
        when(projectCostRepository.findByApproveOrPlan((byte) 1)).thenReturn(List.of(projectCost));

        List<ProjectCost> result = projectCostService.getApprovedCosts();

        assertEquals(1, result.size());
        assertEquals((byte) 1, result.get(0).getApproveOrPlan());
    }

    @Test
    void getPlannedCostsShouldReturnList() {
        ProjectCost plannedCost = createValidProjectCost(2L, new BigDecimal("20000.00"));
        plannedCost.setApproveOrPlan((byte) 2);

        when(projectCostRepository.findByApproveOrPlan((byte) 2)).thenReturn(List.of(plannedCost));

        List<ProjectCost> result = projectCostService.getPlannedCosts();

        assertEquals(1, result.size());
        assertEquals((byte) 2, result.get(0).getApproveOrPlan());
    }

    // ========== Get Operations by Project and Period Tests ==========

    @Test
    void getByProjectIdAndProjectPeriodIdShouldReturnList() {
        when(projectCostRepository.findByProjectIdAndProjectPeriodId(1L, 2024L))
                .thenReturn(List.of(projectCost));

        List<ProjectCost> result = projectCostService.getByProjectIdAndProjectPeriodId(1L, 2024L);

        assertEquals(1, result.size());
        assertEquals(projectCost, result.get(0));
    }

    @Test
    void getByProjectIdAndProjectPeriodIdShouldThrowWhenNull() {
        assertThrows(IllegalArgumentException.class,
                () -> projectCostService.getByProjectIdAndProjectPeriodId(null, 2024L));
        assertThrows(IllegalArgumentException.class,
                () -> projectCostService.getByProjectIdAndProjectPeriodId(1L, null));
    }

    @Test
    void getApprovedCostsByProjectAndPeriodShouldReturnList() {
        when(projectCostRepository.findApprovedCostsByProjectAndPeriod(1L, 2024L))
                .thenReturn(List.of(projectCost));

        List<ProjectCost> result = projectCostService.getApprovedCostsByProjectAndPeriod(1L, 2024L);

        assertEquals(1, result.size());
        assertEquals((byte) 1, result.get(0).getApproveOrPlan());
    }

    @Test
    void getPlannedCostsByProjectAndPeriodShouldReturnList() {
        ProjectCost plannedCost = createValidProjectCost(2L, new BigDecimal("20000.00"));
        plannedCost.setApproveOrPlan((byte) 2);

        when(projectCostRepository.findPlannedCostsByProjectAndPeriod(1L, 2024L))
                .thenReturn(List.of(plannedCost));

        List<ProjectCost> result = projectCostService.getPlannedCostsByProjectAndPeriod(1L, 2024L);

        assertEquals(1, result.size());
        assertEquals((byte) 2, result.get(0).getApproveOrPlan());
    }

    // ========== Get Operations by Project and Type Tests ==========

    @Test
    void getByProjectIdAndApproveOrPlanShouldReturnList() {
        when(projectCostRepository.findByProjectIdAndApproveOrPlan(1L, (byte) 1))
                .thenReturn(List.of(projectCost));

        List<ProjectCost> result = projectCostService.getByProjectIdAndApproveOrPlan(1L, (byte) 1);

        assertEquals(1, result.size());
        assertEquals(projectCost, result.get(0));
    }

    @Test
    void getApprovedCostsByProjectShouldReturnList() {
        when(projectCostRepository.findByProjectIdAndApproveOrPlan(1L, (byte) 1))
                .thenReturn(List.of(projectCost));

        List<ProjectCost> result = projectCostService.getApprovedCostsByProject(1L);

        assertEquals(1, result.size());
        assertEquals((byte) 1, result.get(0).getApproveOrPlan());
    }

    @Test
    void getPlannedCostsByProjectShouldReturnList() {
        ProjectCost plannedCost = createValidProjectCost(2L, new BigDecimal("20000.00"));
        plannedCost.setApproveOrPlan((byte) 2);

        when(projectCostRepository.findByProjectIdAndApproveOrPlan(1L, (byte) 2))
                .thenReturn(List.of(plannedCost));

        List<ProjectCost> result = projectCostService.getPlannedCostsByProject(1L);

        assertEquals(1, result.size());
        assertEquals((byte) 2, result.get(0).getApproveOrPlan());
    }

    // ========== Get Operations by Project, Period and Type Tests ==========

    @Test
    void getByProjectIdAndProjectPeriodIdAndApproveOrPlanShouldReturnList() {
        when(projectCostRepository.findByProjectIdAndProjectPeriodIdAndApproveOrPlan(1L, 2024L, (byte) 1))
                .thenReturn(List.of(projectCost));

        List<ProjectCost> result = projectCostService.getByProjectIdAndProjectPeriodIdAndApproveOrPlan(1L, 2024L,
                (byte) 1);

        assertEquals(1, result.size());
        assertEquals(projectCost, result.get(0));
    }

    @Test
    void getByProjectIdAndProjectPeriodIdAndApproveOrPlanShouldThrowWhenNull() {
        assertThrows(IllegalArgumentException.class,
                () -> projectCostService.getByProjectIdAndProjectPeriodIdAndApproveOrPlan(null, 2024L, (byte) 1));
        assertThrows(IllegalArgumentException.class,
                () -> projectCostService.getByProjectIdAndProjectPeriodIdAndApproveOrPlan(1L, null, (byte) 1));
        assertThrows(IllegalArgumentException.class,
                () -> projectCostService.getByProjectIdAndProjectPeriodIdAndApproveOrPlan(1L, 2024L, null));
    }

    // ========== Project Totals Tests ==========

    @Test
    void getProjectTotalsShouldReturnList() {
        when(projectCostRepository.findProjectTotals(1L)).thenReturn(List.of(projectCost));

        List<ProjectCost> result = projectCostService.getProjectTotals(1L);

        assertEquals(1, result.size());
        assertEquals(projectCost, result.get(0));
    }

    @Test
    void getApprovedProjectTotalsShouldReturnList() {
        when(projectCostRepository.findApprovedProjectTotals(1L)).thenReturn(List.of(projectCost));

        List<ProjectCost> result = projectCostService.getApprovedProjectTotals(1L);

        assertEquals(1, result.size());
        assertEquals((byte) 1, result.get(0).getApproveOrPlan());
    }

    @Test
    void getPlannedProjectTotalsShouldReturnList() {
        ProjectCost plannedCost = createValidProjectCost(2L, new BigDecimal("20000.00"));
        plannedCost.setApproveOrPlan((byte) 2);

        when(projectCostRepository.findPlannedProjectTotals(1L)).thenReturn(List.of(plannedCost));

        List<ProjectCost> result = projectCostService.getPlannedProjectTotals(1L);

        assertEquals(1, result.size());
        assertEquals((byte) 2, result.get(0).getApproveOrPlan());
    }

    // ========== Sum Operations Tests ==========

    @Test
    void getTotalCostsByProjectShouldReturnSum() {
        when(projectCostRepository.sumCostsByProject(1L)).thenReturn(new BigDecimal("25000.50"));

        BigDecimal result = projectCostService.getTotalCostsByProject(1L);

        assertEquals(new BigDecimal("25000.50"), result);
    }

    @Test
    void getTotalApprovedCostsByProjectShouldReturnSum() {
        when(projectCostRepository.sumApprovedCostsByProject(1L)).thenReturn(new BigDecimal("15000.50"));

        BigDecimal result = projectCostService.getTotalApprovedCostsByProject(1L);

        assertEquals(new BigDecimal("15000.50"), result);
    }

    @Test
    void getTotalPlannedCostsByProjectShouldReturnSum() {
        when(projectCostRepository.sumPlannedCostsByProject(1L)).thenReturn(new BigDecimal("10000.00"));

        BigDecimal result = projectCostService.getTotalPlannedCostsByProject(1L);

        assertEquals(new BigDecimal("10000.00"), result);
    }

    @Test
    void getTotalCostsByProjectAndPeriodShouldReturnSum() {
        when(projectCostRepository.sumCostsByProjectAndPeriod(1L, 2024L)).thenReturn(new BigDecimal("10000.50"));

        BigDecimal result = projectCostService.getTotalCostsByProjectAndPeriod(1L, 2024L);

        assertEquals(new BigDecimal("10000.50"), result);
    }

    @Test
    void getTotalApprovedCostsByProjectAndPeriodShouldReturnSum() {
        when(projectCostRepository.sumApprovedCostsByProjectAndPeriod(1L, 2024L)).thenReturn(new BigDecimal("8000.50"));

        BigDecimal result = projectCostService.getTotalApprovedCostsByProjectAndPeriod(1L, 2024L);

        assertEquals(new BigDecimal("8000.50"), result);
    }

    @Test
    void getTotalPlannedCostsByProjectAndPeriodShouldReturnSum() {
        when(projectCostRepository.sumPlannedCostsByProjectAndPeriod(1L, 2024L)).thenReturn(new BigDecimal("2000.00"));

        BigDecimal result = projectCostService.getTotalPlannedCostsByProjectAndPeriod(1L, 2024L);

        assertEquals(new BigDecimal("2000.00"), result);
    }

    // ========== Existence Check Tests ==========

    @Test
    void existsByProjectAndPeriodShouldReturnTrue() {
        when(projectCostRepository.existsByProjectAndPeriod(1L, 2024L)).thenReturn(true);

        boolean result = projectCostService.existsByProjectAndPeriod(1L, 2024L);

        assertTrue(result);
    }

    @Test
    void existsApprovedCostsByProjectAndPeriodShouldReturnTrue() {
        when(projectCostRepository.existsApprovedCostsByProjectAndPeriod(1L, 2024L)).thenReturn(true);

        boolean result = projectCostService.existsApprovedCostsByProjectAndPeriod(1L, 2024L);

        assertTrue(result);
    }

    @Test
    void existsPlannedCostsByProjectAndPeriodShouldReturnTrue() {
        when(projectCostRepository.existsPlannedCostsByProjectAndPeriod(1L, 2024L)).thenReturn(true);

        boolean result = projectCostService.existsPlannedCostsByProjectAndPeriod(1L, 2024L);

        assertTrue(result);
    }

    // ========== Ordering Operations Tests ==========

    @Test
    void getAllOrderByCostsAscShouldReturnSortedList() {
        ProjectCost cost1 = createValidProjectCost(1L, new BigDecimal("1000.00"));
        ProjectCost cost2 = createValidProjectCost(2L, new BigDecimal("5000.00"));

        when(projectCostRepository.findAllByOrderByCostsAsc()).thenReturn(Arrays.asList(cost1, cost2));

        List<ProjectCost> result = projectCostService.getAllOrderByCostsAsc();

        assertEquals(2, result.size());
        assertEquals(new BigDecimal("1000.00"), result.get(0).getCosts());
        assertEquals(new BigDecimal("5000.00"), result.get(1).getCosts());
    }

    @Test
    void getAllOrderByCostsDescShouldReturnSortedList() {
        ProjectCost cost1 = createValidProjectCost(1L, new BigDecimal("1000.00"));
        ProjectCost cost2 = createValidProjectCost(2L, new BigDecimal("5000.00"));

        when(projectCostRepository.findAllByOrderByCostsAsc()).thenReturn(Arrays.asList(cost1, cost2));

        List<ProjectCost> result = projectCostService.getAllOrderByCostsDesc();

        assertEquals(2, result.size());
        assertEquals(new BigDecimal("5000.00"), result.get(0).getCosts());
        assertEquals(new BigDecimal("1000.00"), result.get(1).getCosts());
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
        ProjectCost cost1 = createValidProjectCost(1L, new BigDecimal("5000.00"));
        ProjectCost cost2 = createValidProjectCost(2L, new BigDecimal("10000.00"));

        when(projectCostRepository.findAll()).thenReturn(Arrays.asList(cost1, cost2));

        List<ProjectCost> result = projectCostService.getByCostsGreaterThan(new BigDecimal("6000.00"));

        assertEquals(1, result.size());
        assertEquals(new BigDecimal("10000.00"), result.get(0).getCosts());
    }

    @Test
    void getByCostsLessThanShouldReturnFilteredList() {
        ProjectCost cost1 = createValidProjectCost(1L, new BigDecimal("5000.00"));
        ProjectCost cost2 = createValidProjectCost(2L, new BigDecimal("10000.00"));

        when(projectCostRepository.findAll()).thenReturn(Arrays.asList(cost1, cost2));

        List<ProjectCost> result = projectCostService.getByCostsLessThan(new BigDecimal("6000.00"));

        assertEquals(1, result.size());
        assertEquals(new BigDecimal("5000.00"), result.get(0).getCosts());
    }

    @Test
    void getByCostsBetweenShouldReturnFilteredList() {
        ProjectCost cost1 = createValidProjectCost(1L, new BigDecimal("5000.00"));
        ProjectCost cost2 = createValidProjectCost(2L, new BigDecimal("10000.00"));
        ProjectCost cost3 = createValidProjectCost(3L, new BigDecimal("15000.00"));

        when(projectCostRepository.findAll()).thenReturn(Arrays.asList(cost1, cost2, cost3));

        List<ProjectCost> result = projectCostService.getByCostsBetween(new BigDecimal("6000.00"),
                new BigDecimal("12000.00"));

        assertEquals(1, result.size());
        assertEquals(new BigDecimal("10000.00"), result.get(0).getCosts());
    }

    @Test
    void getByCostsBetweenShouldThrowWhenMinGreaterThanMax() {
        assertThrows(IllegalArgumentException.class,
                () -> projectCostService.getByCostsBetween(new BigDecimal("10000.00"), new BigDecimal("5000.00")));
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
        invalidCost.setApproveOrPlan((byte) 3);

        assertThrows(IllegalArgumentException.class, () -> projectCostService.validateProjectCost(invalidCost));
    }

    @Test
    void validateProjectCostShouldThrowWhenProjectNull() {
        ProjectCost invalidCost = new ProjectCost();
        invalidCost.setApproveOrPlan((byte) 1);
        invalidCost.setProject(null);

        assertThrows(IllegalArgumentException.class, () -> projectCostService.validateProjectCost(invalidCost));
    }

    @Test
    void validateProjectCostShouldThrowWhenProjectPeriodNull() {
        ProjectCost invalidCost = new ProjectCost();
        invalidCost.setApproveOrPlan((byte) 1);
        invalidCost.setProject(new Project());
        invalidCost.setProjectPeriod(null);

        assertThrows(IllegalArgumentException.class, () -> projectCostService.validateProjectCost(invalidCost));
    }

    // ========== Can Create Tests ==========

    @Test
    void canCreateProjectCostShouldReturnFalseWhenNull() {
        boolean result = projectCostService.canCreateProjectCost(null, 2024L, (byte) 1);
        assertFalse(result);

        result = projectCostService.canCreateProjectCost(1L, null, (byte) 1);
        assertFalse(result);

        result = projectCostService.canCreateProjectCost(1L, 2024L, null);
        assertFalse(result);
    }

    @Test
    void canCreateProjectCostShouldReturnTrueWhenCostExists() {
        when(projectCostRepository.findByProjectIdAndProjectPeriodIdAndApproveOrPlan(1L, 2024L, (byte) 1))
                .thenReturn(List.of(projectCost));

        boolean result = projectCostService.canCreateProjectCost(1L, 2024L, (byte) 1);

        assertTrue(result);
    }

    @Test
    void canCreateProjectCostShouldReturnFalseWhenCostNotExists() {
        when(projectCostRepository.findByProjectIdAndProjectPeriodIdAndApproveOrPlan(1L, 2024L, (byte) 1))
                .thenReturn(Collections.emptyList());

        boolean result = projectCostService.canCreateProjectCost(1L, 2024L, (byte) 1);

        assertFalse(result);
    }

    // ========== Bulk Operations Tests ==========

    @Test
    void createAllShouldSaveAllProjectCosts() {
        List<ProjectCost> costs = Arrays.asList(
                createValidProjectCost(1L, new BigDecimal("10000.00")),
                createValidProjectCost(2L, new BigDecimal("20000.00")));

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
        when(projectCostRepository.findByProjectId(1L)).thenReturn(costs);
        doNothing().when(projectCostRepository).deleteAll(costs);

        projectCostService.deleteByProjectId(1L);

        verify(projectCostRepository).deleteAll(costs);
    }

    @Test
    void deleteByProjectPeriodIdShouldDeleteAll() {
        List<ProjectCost> costs = List.of(projectCost);
        when(projectCostRepository.findByProjectPeriodId(2024L)).thenReturn(costs);
        doNothing().when(projectCostRepository).deleteAll(costs);

        projectCostService.deleteByProjectPeriodId(2024L);

        verify(projectCostRepository).deleteAll(costs);
    }

    // ========== Utility Methods Tests ==========

    @Test
    void getMaxIdShouldReturnMaximumId() {
        ProjectCost cost1 = createValidProjectCost(1L, new BigDecimal("1000.00"));
        ProjectCost cost2 = createValidProjectCost(5L, new BigDecimal("5000.00"));
        ProjectCost cost3 = createValidProjectCost(3L, new BigDecimal("3000.00"));

        when(projectCostRepository.findAll()).thenReturn(Arrays.asList(cost1, cost2, cost3));

        Long result = projectCostService.getMaxId();

        assertEquals(5L, result);
    }

    @Test
    void getMaxIdShouldReturnZeroWhenEmpty() {
        when(projectCostRepository.findAll()).thenReturn(Collections.emptyList());

        Long result = projectCostService.getMaxId();

        assertEquals(0L, result);
    }

    @Test
    void getByProjectIdWithNullCostsShouldReturnFilteredList() {
        ProjectCost costWithNull = createValidProjectCost(1L, null);
        ProjectCost costWithValue = createValidProjectCost(2L, new BigDecimal("5000.00"));

        when(projectCostRepository.findByProjectId(1L)).thenReturn(Arrays.asList(costWithNull, costWithValue));

        List<ProjectCost> result = projectCostService.getByProjectIdWithNullCosts(1L);

        assertEquals(1, result.size());
        assertNull(result.get(0).getCosts());
    }

    // ========== Helper Method ==========

    private ProjectCost createValidProjectCost(Long id, BigDecimal costs) {
        ProjectCost newCost = new ProjectCost();
        newCost.setId(id);
        newCost.setApproveOrPlan((byte) 1);
        newCost.setCosts(costs);

        Project project = new Project();
        project.setId(1L);
        newCost.setProject(project);

        ProjectPeriod period = new ProjectPeriod();
        period.setId(2024L);
        newCost.setProjectPeriod(period);

        return newCost;
    }
}