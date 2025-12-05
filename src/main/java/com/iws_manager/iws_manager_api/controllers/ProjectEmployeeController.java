package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.ProjectEmployee;
import com.iws_manager.iws_manager_api.services.interfaces.ProjectEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/project-employees")
public class ProjectEmployeeController {

    private final ProjectEmployeeService projectEmployeeService;

    @Autowired
    public ProjectEmployeeController(ProjectEmployeeService projectEmployeeService) {
        this.projectEmployeeService = projectEmployeeService;
    }

    // ===== BASIC CRUD OPERATIONS =====

    @PostMapping
    public ResponseEntity<ProjectEmployee> create(@RequestBody ProjectEmployee projectEmployee) {
        ProjectEmployee created = projectEmployeeService.create(projectEmployee);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectEmployee> getById(@PathVariable Long id) {
        return projectEmployeeService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ProjectEmployee>> getAll() {
        return ResponseEntity.ok(projectEmployeeService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectEmployee> update(@PathVariable Long id, @RequestBody ProjectEmployee projectEmployee) {
        return ResponseEntity.ok(projectEmployeeService.update(id, projectEmployee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        projectEmployeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ===== GET OPERATIONS BY EMPLOYEE =====

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<ProjectEmployee>> getByEmployeeId(@PathVariable Long employeeId) {
        return ResponseEntity.ok(projectEmployeeService.getByEmployeeId(employeeId));
    }

    @GetMapping("/employee/{employeeId}/ordered")
    public ResponseEntity<List<ProjectEmployee>> getByEmployeeIdOrderByIdAsc(@PathVariable Long employeeId) {
        return ResponseEntity.ok(projectEmployeeService.getByEmployeeIdOrderByIdAsc(employeeId));
    }

    // ===== GET OPERATIONS BY PROJECT =====

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<ProjectEmployee>> getByProjectId(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectEmployeeService.getByProjectId(projectId));
    }

    @GetMapping("/project/{projectId}/ordered")
    public ResponseEntity<List<ProjectEmployee>> getByProjectIdOrderByIdAsc(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectEmployeeService.getByProjectIdOrderByIdAsc(projectId));
    }

    // ===== GET OPERATIONS BY QUALIFICATION K MUI =====

    @GetMapping("/qualification-kmui/{qualificationkmui}")
    public ResponseEntity<List<ProjectEmployee>> getByQualificationkmui(@PathVariable String qualificationkmui) {
        return ResponseEntity.ok(projectEmployeeService.getByQualificationkmui(qualificationkmui));
    }

    @GetMapping("/qualification-kmui/contains/{keyword}")
    public ResponseEntity<List<ProjectEmployee>> getByQualificationkmuiContainingIgnoreCase(
            @PathVariable String keyword) {
        return ResponseEntity.ok(projectEmployeeService.getByQualificationkmuiContainingIgnoreCase(keyword));
    }

    @GetMapping("/qualification-kmui/{qualificationkmui}/ordered")
    public ResponseEntity<List<ProjectEmployee>> getByQualificationkmuiOrderByIdAsc(
            @PathVariable String qualificationkmui) {
        return ResponseEntity.ok(projectEmployeeService.getByQualificationkmuiOrderByIdAsc(qualificationkmui));
    }

    // ===== GET OPERATIONS WITH COMBINED CRITERIA =====

    @GetMapping("/employee/{employeeId}/project/{projectId}")
    public ResponseEntity<List<ProjectEmployee>> getByEmployeeIdAndProjectId(
            @PathVariable Long employeeId, @PathVariable Long projectId) {
        return ResponseEntity.ok(projectEmployeeService.getByEmployeeIdAndProjectId(employeeId, projectId));
    }

    @GetMapping("/project/{projectId}/qualification-kmui/{qualificationkmui}")
    public ResponseEntity<List<ProjectEmployee>> getByProjectIdAndQualificationkmui(
            @PathVariable Long projectId, @PathVariable String qualificationkmui) {
        return ResponseEntity
                .ok(projectEmployeeService.getByProjectIdAndQualificationkmui(projectId, qualificationkmui));
    }

    @GetMapping("/employee/{employeeId}/qualification-kmui/{qualificationkmui}")
    public ResponseEntity<List<ProjectEmployee>> getByEmployeeIdAndQualificationkmui(
            @PathVariable Long employeeId, @PathVariable String qualificationkmui) {
        return ResponseEntity
                .ok(projectEmployeeService.getByEmployeeIdAndQualificationkmui(employeeId, qualificationkmui));
    }

    @GetMapping("/employee/{employeeId}/project/{projectId}/qualification-kmui/{qualificationkmui}")
    public ResponseEntity<List<ProjectEmployee>> getByEmployeeProjectAndQualification(
            @PathVariable Long employeeId, @PathVariable Long projectId, @PathVariable String qualificationkmui) {
        return ResponseEntity.ok(
                projectEmployeeService.getByEmployeeProjectAndQualification(employeeId, projectId, qualificationkmui));
    }

    // ===== GET OPERATIONS BY HOURLY RATE RANGE =====

    @GetMapping("/hourly-rate/greater-than")
    public ResponseEntity<List<ProjectEmployee>> getByHourlyrateGreaterThan(@RequestParam BigDecimal hourlyrate) {
        return ResponseEntity.ok(projectEmployeeService.getByHourlyrateGreaterThan(hourlyrate));
    }

    @GetMapping("/hourly-rate/less-than")
    public ResponseEntity<List<ProjectEmployee>> getByHourlyrateLessThan(@RequestParam BigDecimal hourlyrate) {
        return ResponseEntity.ok(projectEmployeeService.getByHourlyrateLessThan(hourlyrate));
    }

    @GetMapping("/hourly-rate/between")
    public ResponseEntity<List<ProjectEmployee>> getByHourlyrateBetween(
            @RequestParam BigDecimal minHourlyrate, @RequestParam BigDecimal maxHourlyrate) {
        return ResponseEntity.ok(projectEmployeeService.getByHourlyrateBetween(minHourlyrate, maxHourlyrate));
    }

    // ===== GET OPERATIONS BY PLANNED HOURS RANGE =====

    @GetMapping("/planned-hours/greater-than")
    public ResponseEntity<List<ProjectEmployee>> getByPlannedhoursGreaterThan(@RequestParam BigDecimal plannedhours) {
        return ResponseEntity.ok(projectEmployeeService.getByPlannedhoursGreaterThan(plannedhours));
    }

    @GetMapping("/planned-hours/less-than")
    public ResponseEntity<List<ProjectEmployee>> getByPlannedhoursLessThan(@RequestParam BigDecimal plannedhours) {
        return ResponseEntity.ok(projectEmployeeService.getByPlannedhoursLessThan(plannedhours));
    }

    @GetMapping("/planned-hours/between")
    public ResponseEntity<List<ProjectEmployee>> getByPlannedhoursBetween(
            @RequestParam BigDecimal minPlannedhours, @RequestParam BigDecimal maxPlannedhours) {
        return ResponseEntity.ok(projectEmployeeService.getByPlannedhoursBetween(minPlannedhours, maxPlannedhours));
    }

    // ===== GET OPERATIONS BY ESTIMATED COST RANGE =====

    @GetMapping("/estimated-cost/greater-than")
    public ResponseEntity<List<ProjectEmployee>> getByEstimatedCostGreaterThan(@RequestParam BigDecimal minCost) {
        return ResponseEntity.ok(projectEmployeeService.getByEstimatedCostGreaterThan(minCost));
    }

    @GetMapping("/estimated-cost/less-than")
    public ResponseEntity<List<ProjectEmployee>> getByEstimatedCostLessThan(@RequestParam BigDecimal maxCost) {
        return ResponseEntity.ok(projectEmployeeService.getByEstimatedCostLessThan(maxCost));
    }

    @GetMapping("/estimated-cost/between")
    public ResponseEntity<List<ProjectEmployee>> getByEstimatedCostBetween(
            @RequestParam BigDecimal minCost, @RequestParam BigDecimal maxCost) {
        return ResponseEntity.ok(projectEmployeeService.getByEstimatedCostBetween(minCost, maxCost));
    }

    // ===== GET OPERATIONS WITH MINIMUM RATE AND HOURS =====

    @GetMapping("/minimum-rate-hours")
    public ResponseEntity<List<ProjectEmployee>> getWithMinimumRateAndHours(
            @RequestParam BigDecimal minRate, @RequestParam BigDecimal minHours) {
        return ResponseEntity.ok(projectEmployeeService.getWithMinimumRateAndHours(minRate, minHours));
    }

    // ===== GET OPERATIONS BY QUALIFICATION CONTAINING KEYWORD =====

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<ProjectEmployee>> getByQualificationContaining(@PathVariable String keyword) {
        return ResponseEntity.ok(projectEmployeeService.getByQualificationContaining(keyword));
    }

    // ===== GET OPERATIONS BY MULTIPLE QUALIFICATIONS =====

    @GetMapping("/qualifications/in")
    public ResponseEntity<List<ProjectEmployee>> getByQualificationsIn(@RequestParam List<String> qualifications) {
        return ResponseEntity.ok(projectEmployeeService.getByQualificationsIn(qualifications));
    }

    // ===== ORDERING OPERATIONS =====

    @GetMapping("/ordered/hourly-rate-asc")
    public ResponseEntity<List<ProjectEmployee>> getAllOrderByHourlyrateAsc() {
        return ResponseEntity.ok(projectEmployeeService.getAllOrderByHourlyrateAsc());
    }

    @GetMapping("/ordered/hourly-rate-desc")
    public ResponseEntity<List<ProjectEmployee>> getAllOrderByHourlyrateDesc() {
        return ResponseEntity.ok(projectEmployeeService.getAllOrderByHourlyrateDesc());
    }

    @GetMapping("/ordered/planned-hours-asc")
    public ResponseEntity<List<ProjectEmployee>> getAllOrderByPlannedhoursAsc() {
        return ResponseEntity.ok(projectEmployeeService.getAllOrderByPlannedhoursAsc());
    }

    @GetMapping("/ordered/planned-hours-desc")
    public ResponseEntity<List<ProjectEmployee>> getAllOrderByPlannedhoursDesc() {
        return ResponseEntity.ok(projectEmployeeService.getAllOrderByPlannedhoursDesc());
    }

    @GetMapping("/ordered/qualification-kmui-asc")
    public ResponseEntity<List<ProjectEmployee>> getAllOrderByQualificationkmuiAsc() {
        return ResponseEntity.ok(projectEmployeeService.getAllOrderByQualificationkmuiAsc());
    }

    @GetMapping("/ordered/estimated-cost-asc")
    public ResponseEntity<List<ProjectEmployee>> getAllOrderByEstimatedCostAsc() {
        return ResponseEntity.ok(projectEmployeeService.getAllOrderByEstimatedCostAsc());
    }

    @GetMapping("/ordered/estimated-cost-desc")
    public ResponseEntity<List<ProjectEmployee>> getAllOrderByEstimatedCostDesc() {
        return ResponseEntity.ok(projectEmployeeService.getAllOrderByEstimatedCostDesc());
    }

    // ===== CALCULATION OPERATIONS =====

    @GetMapping("/project/{projectId}/total-cost")
    public ResponseEntity<BigDecimal> calculateTotalCostByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectEmployeeService.calculateTotalCostByProject(projectId));
    }

    @GetMapping("/employee/{employeeId}/total-cost")
    public ResponseEntity<BigDecimal> calculateTotalCostByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(projectEmployeeService.calculateTotalCostByEmployee(employeeId));
    }

    @GetMapping("/project/{projectId}/total-planned-hours")
    public ResponseEntity<BigDecimal> calculateTotalPlannedHoursByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectEmployeeService.calculateTotalPlannedHoursByProject(projectId));
    }

    @GetMapping("/employee/{employeeId}/total-planned-hours")
    public ResponseEntity<BigDecimal> calculateTotalPlannedHoursByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(projectEmployeeService.calculateTotalPlannedHoursByEmployee(employeeId));
    }

    // ===== COUNT OPERATIONS =====

    @GetMapping("/count/project/{projectId}")
    public ResponseEntity<Long> countByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectEmployeeService.countByProject(projectId));
    }

    @GetMapping("/count/employee/{employeeId}")
    public ResponseEntity<Long> countByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(projectEmployeeService.countByEmployee(employeeId));
    }

    @GetMapping("/count/project/{projectId}/employee/{employeeId}")
    public ResponseEntity<Long> countByProjectAndEmployee(
            @PathVariable Long projectId, @PathVariable Long employeeId) {
        return ResponseEntity.ok(projectEmployeeService.countByProjectAndEmployee(projectId, employeeId));
    }

    // ===== GET DISTINCT OPERATIONS =====

    @GetMapping("/project/{projectId}/distinct-employees")
    public ResponseEntity<List<com.iws_manager.iws_manager_api.models.Employee>> getDistinctEmployeesByProject(
            @PathVariable Long projectId) {
        return ResponseEntity.ok(projectEmployeeService.getDistinctEmployeesByProject(projectId));
    }

    @GetMapping("/employee/{employeeId}/distinct-projects")
    public ResponseEntity<List<com.iws_manager.iws_manager_api.models.Project>> getDistinctProjectsByEmployee(
            @PathVariable Long employeeId) {
        return ResponseEntity.ok(projectEmployeeService.getDistinctProjectsByEmployee(employeeId));
    }

    // ===== GET AVERAGE HOURLY RATE OPERATIONS =====

    @GetMapping("/project/{projectId}/average-hourly-rate")
    public ResponseEntity<BigDecimal> getAverageHourlyRateByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectEmployeeService.getAverageHourlyRateByProject(projectId));
    }

    @GetMapping("/employee/{employeeId}/average-hourly-rate")
    public ResponseEntity<BigDecimal> getAverageHourlyRateByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(projectEmployeeService.getAverageHourlyRateByEmployee(employeeId));
    }

    // ===== GET STATISTICS OPERATIONS =====

    @GetMapping("/project/{projectId}/statistics")
    public ResponseEntity<Object[]> getProjectStatistics(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectEmployeeService.getProjectStatistics(projectId));
    }

    @GetMapping("/employee/{employeeId}/statistics")
    public ResponseEntity<Object[]> getEmployeeStatistics(@PathVariable Long employeeId) {
        return ResponseEntity.ok(projectEmployeeService.getEmployeeStatistics(employeeId));
    }

    // ===== VALIDATION =====

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateProjectEmployee(@RequestBody ProjectEmployee projectEmployee) {
        return ResponseEntity.ok(projectEmployeeService.validateProjectEmployee(projectEmployee));
    }
}