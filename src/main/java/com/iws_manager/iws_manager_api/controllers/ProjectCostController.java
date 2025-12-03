package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.ProjectCost;
import com.iws_manager.iws_manager_api.services.interfaces.ProjectCostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/project-costs")
public class ProjectCostController {
    private final ProjectCostService projectCostService;

    @Autowired
    public ProjectCostController(ProjectCostService projectCostService) {
        this.projectCostService = projectCostService;
    }

    // Basic CRUD operations

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProjectCost> create(@RequestBody ProjectCost projectCost) {
        ProjectCost createdProjectCost = projectCostService.create(projectCost);
        return new ResponseEntity<>(createdProjectCost, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectCost> getById(@PathVariable Long id) {
        return projectCostService.getById(id)
                .map(projectCost -> new ResponseEntity<>(projectCost, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<ProjectCost>> getAll() {
        List<ProjectCost> projectCosts = projectCostService.getAll();
        return new ResponseEntity<>(projectCosts, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectCost> update(@PathVariable Long id, @RequestBody ProjectCost projectCostDetails) {
        ProjectCost updatedProjectCost = projectCostService.update(id, projectCostDetails);
        return new ResponseEntity<>(updatedProjectCost, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        projectCostService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get operations by project

    @GetMapping("/by-project/{projectId}")
    public ResponseEntity<List<ProjectCost>> getByProjectId(@PathVariable Long projectId) {
        List<ProjectCost> projectCosts = projectCostService.getByProjectId(projectId);
        return new ResponseEntity<>(projectCosts, HttpStatus.OK);
    }

    @GetMapping("/by-project/{projectId}/order-by-costs-asc")
    public ResponseEntity<List<ProjectCost>> getByProjectIdOrderByCostsAsc(@PathVariable Long projectId) {
        List<ProjectCost> projectCosts = projectCostService.getByProjectIdOrderByCostsAsc(projectId);
        return new ResponseEntity<>(projectCosts, HttpStatus.OK);
    }

    @GetMapping("/by-project/{projectId}/order-by-costs-desc")
    public ResponseEntity<List<ProjectCost>> getByProjectIdOrderByCostsDesc(@PathVariable Long projectId) {
        List<ProjectCost> projectCosts = projectCostService.getByProjectIdOrderByCostsDesc(projectId);
        return new ResponseEntity<>(projectCosts, HttpStatus.OK);
    }

    @GetMapping("/by-project/{projectId}/order-by-period-asc")
    public ResponseEntity<List<ProjectCost>> getByProjectIdOrderByProjectPeriodIdAsc(@PathVariable Long projectId) {
        List<ProjectCost> projectCosts = projectCostService.getByProjectIdOrderByProjectPeriodIdAsc(projectId);
        return new ResponseEntity<>(projectCosts, HttpStatus.OK);
    }

    @GetMapping("/by-project/{projectId}/order-by-type-asc")
    public ResponseEntity<List<ProjectCost>> getByProjectIdOrderByApproveOrPlanAsc(@PathVariable Long projectId) {
        List<ProjectCost> projectCosts = projectCostService.getByProjectIdOrderByApproveOrPlanAsc(projectId);
        return new ResponseEntity<>(projectCosts, HttpStatus.OK);
    }

    // Get operations by project period

    @GetMapping("/by-period/{projectPeriodId}")
    public ResponseEntity<List<ProjectCost>> getByProjectPeriodId(@PathVariable Long projectPeriodId) {
        List<ProjectCost> projectCosts = projectCostService.getByProjectPeriodId(projectPeriodId);
        return new ResponseEntity<>(projectCosts, HttpStatus.OK);
    }

    @GetMapping("/by-period/{projectPeriodId}/order-by-costs-asc")
    public ResponseEntity<List<ProjectCost>> getByProjectPeriodIdOrderByCostsAsc(@PathVariable Long projectPeriodId) {
        List<ProjectCost> projectCosts = projectCostService.getByProjectPeriodIdOrderByCostsAsc(projectPeriodId);
        return new ResponseEntity<>(projectCosts, HttpStatus.OK);
    }

    @GetMapping("/by-period/{projectPeriodId}/order-by-costs-desc")
    public ResponseEntity<List<ProjectCost>> getByProjectPeriodIdOrderByCostsDesc(@PathVariable Long projectPeriodId) {
        List<ProjectCost> projectCosts = projectCostService.getByProjectPeriodIdOrderByCostsDesc(projectPeriodId);
        return new ResponseEntity<>(projectCosts, HttpStatus.OK);
    }

    // Get operations by type (approved=1 or planned=2)

    @GetMapping("/by-type/{approveOrPlan}")
    public ResponseEntity<List<ProjectCost>> getByApproveOrPlan(@PathVariable Byte approveOrPlan) {
        List<ProjectCost> projectCosts = projectCostService.getByApproveOrPlan(approveOrPlan);
        return new ResponseEntity<>(projectCosts, HttpStatus.OK);
    }

    @GetMapping("/approved")
    public ResponseEntity<List<ProjectCost>> getApprovedCosts() {
        List<ProjectCost> projectCosts = projectCostService.getApprovedCosts();
        return new ResponseEntity<>(projectCosts, HttpStatus.OK);
    }

    @GetMapping("/planned")
    public ResponseEntity<List<ProjectCost>> getPlannedCosts() {
        List<ProjectCost> projectCosts = projectCostService.getPlannedCosts();
        return new ResponseEntity<>(projectCosts, HttpStatus.OK);
    }

    // Get operations by project and period

    @GetMapping("/by-project/{projectId}/and-period/{projectPeriodId}")
    public ResponseEntity<List<ProjectCost>> getByProjectIdAndProjectPeriodId(
            @PathVariable Long projectId,
            @PathVariable Long projectPeriodId) {
        List<ProjectCost> projectCosts = projectCostService.getByProjectIdAndProjectPeriodId(projectId,
                projectPeriodId);
        return new ResponseEntity<>(projectCosts, HttpStatus.OK);
    }

    @GetMapping("/by-project/{projectId}/and-period/{projectPeriodId}/approved")
    public ResponseEntity<List<ProjectCost>> getApprovedCostsByProjectAndPeriod(
            @PathVariable Long projectId,
            @PathVariable Long projectPeriodId) {
        List<ProjectCost> projectCosts = projectCostService.getApprovedCostsByProjectAndPeriod(projectId,
                projectPeriodId);
        return new ResponseEntity<>(projectCosts, HttpStatus.OK);
    }

    @GetMapping("/by-project/{projectId}/and-period/{projectPeriodId}/planned")
    public ResponseEntity<List<ProjectCost>> getPlannedCostsByProjectAndPeriod(
            @PathVariable Long projectId,
            @PathVariable Long projectPeriodId) {
        List<ProjectCost> projectCosts = projectCostService.getPlannedCostsByProjectAndPeriod(projectId,
                projectPeriodId);
        return new ResponseEntity<>(projectCosts, HttpStatus.OK);
    }

    // Get operations by project and type

    @GetMapping("/by-project/{projectId}/and-type/{approveOrPlan}")
    public ResponseEntity<List<ProjectCost>> getByProjectIdAndApproveOrPlan(
            @PathVariable Long projectId,
            @PathVariable Byte approveOrPlan) {
        List<ProjectCost> projectCosts = projectCostService.getByProjectIdAndApproveOrPlan(projectId, approveOrPlan);
        return new ResponseEntity<>(projectCosts, HttpStatus.OK);
    }

    @GetMapping("/by-project/{projectId}/approved")
    public ResponseEntity<List<ProjectCost>> getApprovedCostsByProject(@PathVariable Long projectId) {
        List<ProjectCost> projectCosts = projectCostService.getApprovedCostsByProject(projectId);
        return new ResponseEntity<>(projectCosts, HttpStatus.OK);
    }

    @GetMapping("/by-project/{projectId}/planned")
    public ResponseEntity<List<ProjectCost>> getPlannedCostsByProject(@PathVariable Long projectId) {
        List<ProjectCost> projectCosts = projectCostService.getPlannedCostsByProject(projectId);
        return new ResponseEntity<>(projectCosts, HttpStatus.OK);
    }

    // Get operations by project, period and type

    @GetMapping("/by-project/{projectId}/and-period/{projectPeriodId}/and-type/{approveOrPlan}")
    public ResponseEntity<List<ProjectCost>> getByProjectIdAndProjectPeriodIdAndApproveOrPlan(
            @PathVariable Long projectId,
            @PathVariable Long projectPeriodId,
            @PathVariable Byte approveOrPlan) {
        List<ProjectCost> projectCosts = projectCostService.getByProjectIdAndProjectPeriodIdAndApproveOrPlan(
                projectId, projectPeriodId, approveOrPlan);
        return new ResponseEntity<>(projectCosts, HttpStatus.OK);
    }

    // Special methods for project totals (projectperiodid = 0)

    @GetMapping("/by-project/{projectId}/totals")
    public ResponseEntity<List<ProjectCost>> getProjectTotals(@PathVariable Long projectId) {
        List<ProjectCost> projectCosts = projectCostService.getProjectTotals(projectId);
        return new ResponseEntity<>(projectCosts, HttpStatus.OK);
    }

    @GetMapping("/by-project/{projectId}/totals/approved")
    public ResponseEntity<List<ProjectCost>> getApprovedProjectTotals(@PathVariable Long projectId) {
        List<ProjectCost> projectCosts = projectCostService.getApprovedProjectTotals(projectId);
        return new ResponseEntity<>(projectCosts, HttpStatus.OK);
    }

    @GetMapping("/by-project/{projectId}/totals/planned")
    public ResponseEntity<List<ProjectCost>> getPlannedProjectTotals(@PathVariable Long projectId) {
        List<ProjectCost> projectCosts = projectCostService.getPlannedProjectTotals(projectId);
        return new ResponseEntity<>(projectCosts, HttpStatus.OK);
    }

    // Sum operations

    @GetMapping("/by-project/{projectId}/total-costs")
    public ResponseEntity<BigDecimal> getTotalCostsByProject(@PathVariable Long projectId) {
        BigDecimal totalCosts = projectCostService.getTotalCostsByProject(projectId);
        return new ResponseEntity<>(totalCosts, HttpStatus.OK);
    }

    @GetMapping("/by-project/{projectId}/total-costs/approved")
    public ResponseEntity<BigDecimal> getTotalApprovedCostsByProject(@PathVariable Long projectId) {
        BigDecimal totalCosts = projectCostService.getTotalApprovedCostsByProject(projectId);
        return new ResponseEntity<>(totalCosts, HttpStatus.OK);
    }

    @GetMapping("/by-project/{projectId}/total-costs/planned")
    public ResponseEntity<BigDecimal> getTotalPlannedCostsByProject(@PathVariable Long projectId) {
        BigDecimal totalCosts = projectCostService.getTotalPlannedCostsByProject(projectId);
        return new ResponseEntity<>(totalCosts, HttpStatus.OK);
    }

    @GetMapping("/by-project/{projectId}/and-period/{projectPeriodId}/total-costs")
    public ResponseEntity<BigDecimal> getTotalCostsByProjectAndPeriod(
            @PathVariable Long projectId,
            @PathVariable Long projectPeriodId) {
        BigDecimal totalCosts = projectCostService.getTotalCostsByProjectAndPeriod(projectId, projectPeriodId);
        return new ResponseEntity<>(totalCosts, HttpStatus.OK);
    }

    @GetMapping("/by-project/{projectId}/and-period/{projectPeriodId}/total-costs/approved")
    public ResponseEntity<BigDecimal> getTotalApprovedCostsByProjectAndPeriod(
            @PathVariable Long projectId,
            @PathVariable Long projectPeriodId) {
        BigDecimal totalCosts = projectCostService.getTotalApprovedCostsByProjectAndPeriod(projectId, projectPeriodId);
        return new ResponseEntity<>(totalCosts, HttpStatus.OK);
    }

    @GetMapping("/by-project/{projectId}/and-period/{projectPeriodId}/total-costs/planned")
    public ResponseEntity<BigDecimal> getTotalPlannedCostsByProjectAndPeriod(
            @PathVariable Long projectId,
            @PathVariable Long projectPeriodId) {
        BigDecimal totalCosts = projectCostService.getTotalPlannedCostsByProjectAndPeriod(projectId, projectPeriodId);
        return new ResponseEntity<>(totalCosts, HttpStatus.OK);
    }

    // Check existence operations

    @GetMapping("/by-project/{projectId}/and-period/{projectPeriodId}/exists")
    public ResponseEntity<Boolean> existsByProjectAndPeriod(
            @PathVariable Long projectId,
            @PathVariable Long projectPeriodId) {
        boolean exists = projectCostService.existsByProjectAndPeriod(projectId, projectPeriodId);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    @GetMapping("/by-project/{projectId}/and-period/{projectPeriodId}/exists/approved")
    public ResponseEntity<Boolean> existsApprovedCostsByProjectAndPeriod(
            @PathVariable Long projectId,
            @PathVariable Long projectPeriodId) {
        boolean exists = projectCostService.existsApprovedCostsByProjectAndPeriod(projectId, projectPeriodId);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    @GetMapping("/by-project/{projectId}/and-period/{projectPeriodId}/exists/planned")
    public ResponseEntity<Boolean> existsPlannedCostsByProjectAndPeriod(
            @PathVariable Long projectId,
            @PathVariable Long projectPeriodId) {
        boolean exists = projectCostService.existsPlannedCostsByProjectAndPeriod(projectId, projectPeriodId);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    // Ordering operations

    @GetMapping("/order-by-costs-asc")
    public ResponseEntity<List<ProjectCost>> getAllOrderByCostsAsc() {
        List<ProjectCost> projectCosts = projectCostService.getAllOrderByCostsAsc();
        return new ResponseEntity<>(projectCosts, HttpStatus.OK);
    }

    @GetMapping("/order-by-costs-desc")
    public ResponseEntity<List<ProjectCost>> getAllOrderByCostsDesc() {
        List<ProjectCost> projectCosts = projectCostService.getAllOrderByCostsDesc();
        return new ResponseEntity<>(projectCosts, HttpStatus.OK);
    }

    @GetMapping("/order-by-project-asc")
    public ResponseEntity<List<ProjectCost>> getAllOrderByProjectIdAsc() {
        List<ProjectCost> projectCosts = projectCostService.getAllOrderByProjectIdAsc();
        return new ResponseEntity<>(projectCosts, HttpStatus.OK);
    }

    @GetMapping("/order-by-period-asc")
    public ResponseEntity<List<ProjectCost>> getAllOrderByProjectPeriodIdAsc() {
        List<ProjectCost> projectCosts = projectCostService.getAllOrderByProjectPeriodIdAsc();
        return new ResponseEntity<>(projectCosts, HttpStatus.OK);
    }

    // Range operations

    @GetMapping("/costs-greater-than")
    public ResponseEntity<List<ProjectCost>> getByCostsGreaterThan(@RequestParam BigDecimal amount) {
        List<ProjectCost> projectCosts = projectCostService.getByCostsGreaterThan(amount);
        return new ResponseEntity<>(projectCosts, HttpStatus.OK);
    }

    @GetMapping("/costs-less-than")
    public ResponseEntity<List<ProjectCost>> getByCostsLessThan(@RequestParam BigDecimal amount) {
        List<ProjectCost> projectCosts = projectCostService.getByCostsLessThan(amount);
        return new ResponseEntity<>(projectCosts, HttpStatus.OK);
    }

    @GetMapping("/costs-between")
    public ResponseEntity<List<ProjectCost>> getByCostsBetween(
            @RequestParam BigDecimal minAmount,
            @RequestParam BigDecimal maxAmount) {
        List<ProjectCost> projectCosts = projectCostService.getByCostsBetween(minAmount, maxAmount);
        return new ResponseEntity<>(projectCosts, HttpStatus.OK);
    }

    // Validation and business logic

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateProjectCost(@RequestBody ProjectCost projectCost) {
        boolean isValid = projectCostService.validateProjectCost(projectCost);
        return new ResponseEntity<>(isValid, HttpStatus.OK);
    }

    @GetMapping("/can-create")
    public ResponseEntity<Boolean> canCreateProjectCost(
            @RequestParam Long projectId,
            @RequestParam Long projectPeriodId,
            @RequestParam Byte approveOrPlan) {
        boolean canCreate = projectCostService.canCreateProjectCost(projectId, projectPeriodId, approveOrPlan);
        return new ResponseEntity<>(canCreate, HttpStatus.OK);
    }

    // Bulk operations

    @PostMapping("/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<ProjectCost>> createAll(@RequestBody List<ProjectCost> projectCosts) {
        List<ProjectCost> createdProjectCosts = projectCostService.createAll(projectCosts);
        return new ResponseEntity<>(createdProjectCosts, HttpStatus.CREATED);
    }

    @DeleteMapping("/by-project/{projectId}")
    public ResponseEntity<Void> deleteByProjectId(@PathVariable Long projectId) {
        projectCostService.deleteByProjectId(projectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/by-period/{projectPeriodId}")
    public ResponseEntity<Void> deleteByProjectPeriodId(@PathVariable Long projectPeriodId) {
        projectCostService.deleteByProjectPeriodId(projectPeriodId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Utility methods

    @GetMapping("/max-id")
    public ResponseEntity<Long> getMaxId() {
        Long maxId = projectCostService.getMaxId();
        return new ResponseEntity<>(maxId, HttpStatus.OK);
    }

    @GetMapping("/by-project/{projectId}/with-null-costs")
    public ResponseEntity<List<ProjectCost>> getByProjectIdWithNullCosts(@PathVariable Long projectId) {
        List<ProjectCost> projectCosts = projectCostService.getByProjectIdWithNullCosts(projectId);
        return new ResponseEntity<>(projectCosts, HttpStatus.OK);
    }

    // Global exception handler (opcional, pero recomendado para manejar excepciones
    // del servicio)

    @ExceptionHandler({ IllegalArgumentException.class, jakarta.persistence.EntityNotFoundException.class })
    public ResponseEntity<Map<String, String>> handleException(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        errorResponse.put("timestamp", java.time.LocalDateTime.now().toString());

        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (ex instanceof jakarta.persistence.EntityNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(errorResponse, status);
    }
}