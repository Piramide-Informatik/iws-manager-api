package com.iws_manager.iws_manager_api.controllers;

import java.math.BigDecimal;

import com.iws_manager.iws_manager_api.models.Project;
import com.iws_manager.iws_manager_api.services.interfaces.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody Project project){
        Project createProject = projectService.create(project);
        return new ResponseEntity<>(createProject, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getById(@PathVariable Long id){
        return  projectService.findById(id)
                .map( project -> new ResponseEntity<>(project, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAll(){
        List<Project> projects = projectService.findAll();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> update(@PathVariable Long id, @RequestBody Project projectDetails){
        try {
            Project updateProject = projectService.update(id, projectDetails);
            return new ResponseEntity<>(updateProject, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try {
            projectService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // @GetMapping("/approvaldate/{approvalDate}")
    // public ResponseEntity<List<Project>> getProjectsByApprovalDate(@PathVariable LocalDate approvalDate) {
    //     List<Project> projects = projectService.getProjectsByApprovalDate(approvalDate);
    //     return new ResponseEntity<>(projects, HttpStatus.OK);
    // }

    @GetMapping("/by-approval-date/{approvalDate}")
    public ResponseEntity<List<Project>> getByApprovalDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate approvalDate) {
        List<Project> projects = projectService.getProjectsByApprovalDate(approvalDate);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-authorization-date/{authorizationDate}")
    public ResponseEntity<List<Project>> getByAuthorizationDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate authorizationDate) {
        List<Project> projects = projectService.getProjectsByAuthorizationDate(authorizationDate);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-end-approval/{endApproval}")
    public ResponseEntity<List<Project>> getByEndApproval(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endApproval) {
        List<Project> projects = projectService.getProjectsByEndApproval(endApproval);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-end-date/{endDate}")
    public ResponseEntity<List<Project>> getByEndDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Project> projects = projectService.getProjectsByEndDate(endDate);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-start-approval/{startApproval}")
    public ResponseEntity<List<Project>> getByStartApproval(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startApproval) {
        List<Project> projects = projectService.getProjectsByStartApproval(startApproval);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-start-date/{startDate}")
    public ResponseEntity<List<Project>> getByStartDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
        List<Project> projects = projectService.getProjectsByStartDate(startDate);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-chance/{chance}")
    public ResponseEntity<List<Project>> getByChance(@PathVariable BigDecimal chance) {
        List<Project> projects = projectService.getProjectsByChance(chance);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-funding-rate/{fundingRate}")
    public ResponseEntity<List<Project>> getByFundingRate(@PathVariable BigDecimal fundingRate) {
        List<Project> projects = projectService.getProjectsByFundingRate(fundingRate);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-hourly-rate/{hourlyRateMueu}")
    public ResponseEntity<List<Project>> getByHourlyRate(@PathVariable BigDecimal hourlyRateMueu) {
        List<Project> projects = projectService.getProjectsByHourlyRateMueu(hourlyRateMueu);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

     @GetMapping("/by-max-hours-month/{maxHoursPerMonth}")
    public ResponseEntity<List<Project>> getByMaxHoursPerMonth(@PathVariable BigDecimal maxHoursPerMonth) {
        List<Project> projects = projectService.getProjectsByMaxHoursPerMonth(maxHoursPerMonth);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-max-hours-year/{maxHoursPerYear}")
    public ResponseEntity<List<Project>> getByMaxHoursPerYear(@PathVariable BigDecimal maxHoursPerYear) {
        List<Project> projects = projectService.getProjectsByMaxHoursPerYear(maxHoursPerYear);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-productive-hours-year/{productiveHoursPerYear}")
    public ResponseEntity<List<Project>> getByProductiveHoursPerYear(@PathVariable BigDecimal productiveHoursPerYear) {
        List<Project> projects = projectService.getProjectsByProductiveHoursPerYear(productiveHoursPerYear);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-share-research/{shareResearch}")
    public ResponseEntity<List<Project>> getByShareResearch(@PathVariable BigDecimal shareResearch) {
        List<Project> projects = projectService.getProjectsByShareResearch(shareResearch);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-stuff-flat/{stuffFlat}")
    public ResponseEntity<List<Project>> getByStuffFlat(@PathVariable BigDecimal stuffFlat) {
        List<Project> projects = projectService.getProjectsByStuffFlat(stuffFlat);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-order-fue/{orderIdFue}")
    public ResponseEntity<List<Project>> getByOrderIdFue(@PathVariable Integer orderIdFue) {
        List<Project> projects = projectService.getProjectsByOrderIdFue(orderIdFue);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-order-admin/{orderIdAdmin}")
    public ResponseEntity<List<Project>> getByOrderIdAdmin(@PathVariable Integer orderIdAdmin) {
        List<Project> projects = projectService.getProjectsByOrderIdAdmin(orderIdAdmin);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/with-comment-containing")
    public ResponseEntity<List<Project>> getByCommentContaining(@RequestParam String keyword) {
        List<Project> projects = projectService.getProjectsByCommentContaining(keyword);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-finance-authority/{authority}")
    public ResponseEntity<List<Project>> getByFinanceAuthority(@PathVariable String authority) {
        List<Project> projects = projectService.getProjectsByFinanceAuthority(authority);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-funding-label/{label}")
    public ResponseEntity<List<Project>> getByFundingLabel(@PathVariable String label) {
        List<Project> projects = projectService.getProjectsByFundingLabel(label);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/with-note-containing")
    public ResponseEntity<List<Project>> getByNoteContaining(@RequestParam String text) {
        List<Project> projects = projectService.getProjectsByNoteContaining(text);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-project-label/{projectLabel}")
    public ResponseEntity<List<Project>> getByProjectLabel(@PathVariable String projectLabel) {
        List<Project> projects = projectService.getProjectsByProjectLabel(projectLabel);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-project-name/{projectName}")
    public ResponseEntity<List<Project>> getByProjectName(@PathVariable String projectName) {
        List<Project> projects = projectService.getProjectsByProjectName(projectName);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-title/{title}")
    public ResponseEntity<List<Project>> getByTitle(@PathVariable String title) {
        List<Project> projects = projectService.getProjectsByTitle(title);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-customer/{customerId}")
    public ResponseEntity<List<Project>> getByCustomerId(@PathVariable Long customerId) {
        List<Project> projects = projectService.getProjectsByCustomerId(customerId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    /*
     * Relaciones pendientes de implementar (actualmente comentadas)
     * Estos endpoints quedan preparados para cuando se activen las relaciones
     */

    // @GetMapping("/by-empiws20/{empiws20Id}")
    // public ResponseEntity<List<Project>> getByEmpiws20Id(@PathVariable Long empiws20Id) {
    //     // List<Project> projects = projectService.getProjectsByEmpiws20Id(empiws20Id);
    //     // return new ResponseEntity<>(projects, HttpStatus.OK);
    //     return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    // }

    @GetMapping("/by-funding-program/{fundingProgramId}")
    public ResponseEntity<List<Project>> getByFundingProgramId(@PathVariable Long fundingProgramId) {
        List<Project> projects = projectService.getProjectsByFundingProgramId(fundingProgramId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-promoter/{promoterId}")
    public ResponseEntity<List<Project>> getProjectsByPromoterId(@PathVariable Long promoterId) {
        List<Project> projects = projectService.getProjectsByPromoterId(promoterId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    // @GetMapping("/by-status/{statusId}")
    // public ResponseEntity<List<Project>> getByStatusId(@PathVariable Long statusId) {
    //     // List<Project> projects = projectService.getProjectsByStatusId(statusId);
    //     // return new ResponseEntity<>(projects, HttpStatus.OK);
    //     return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    // }

    ///by-approval-date-range?start=2023-01-01&end=2023-12-31
    @GetMapping("/by-approval-date-range")
    public ResponseEntity<List<Project>> getByApprovalDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        List<Project> projects = projectService.getProjectsByApprovalDateBetween(start, end);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-start-date-range")
    public ResponseEntity<List<Project>> getByStartDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        List<Project> projects = projectService.getProjectsByStartDateBetween(start, end);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    // Búsquedas con operadores de fecha
    @GetMapping("/with-authorization-before")
    public ResponseEntity<List<Project>> getWithAuthorizationBefore(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Project> projects = projectService.getProjectsByAuthorizationDateBefore(date);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/with-end-date-after")
    public ResponseEntity<List<Project>> getWithEndDateAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Project> projects = projectService.getProjectsByEndDateAfter(date);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    // Búsquedas con operadores numéricos
    ///with-chance-greater-than?chance=50.00
    @GetMapping("/with-chance-greater-than")
    public ResponseEntity<List<Project>> getWithChanceGreaterThan(
            @RequestParam BigDecimal chance) {
        List<Project> projects = projectService.getProjectsByChanceGreaterThan(chance);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/with-funding-rate-less-than")
    public ResponseEntity<List<Project>> getWithFundingRateLessThan(
            @RequestParam BigDecimal fundingRate) {
        List<Project> projects = projectService.getProjectsByFundingRateLessThan(fundingRate);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-hourly-rate-range")
    public ResponseEntity<List<Project>> getByHourlyRateRange(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {
        List<Project> projects = projectService.getProjectsByHourlyRateMueuBetween(min, max);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    // Búsquedas por patrones de texto
    ///with-name-containing?name=proyecto
    @GetMapping("/with-name-containing")
    public ResponseEntity<List<Project>> getWithNameContaining(
            @RequestParam String name) {
        List<Project> projects = projectService.getProjectsByProjectNameContainingIgnoreCase(name);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/with-funding-label-starting")
    public ResponseEntity<List<Project>> getWithFundingLabelStarting(
            @RequestParam String prefix) {
        List<Project> projects = projectService.getProjectsByFundingLabelStartingWith(prefix);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/with-title-ending")
    public ResponseEntity<List<Project>> getWithTitleEnding(
            @RequestParam String suffix) {
        List<Project> projects = projectService.getProjectsByTitleEndingWith(suffix);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    // Búsqueda ordenada por relación
    @GetMapping("/by-customer/{customerId}/ordered-by-start-date")
    public ResponseEntity<List<Project>> getByCustomerOrderedByStartDate(
            @PathVariable Long customerId) {
        List<Project> projects = projectService.getProjectsByCustomerIdOrderByStartDateDesc(customerId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }
}
