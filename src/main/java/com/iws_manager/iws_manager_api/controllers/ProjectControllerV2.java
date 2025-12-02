package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.dtos.project.ProjectResponseDTO;
import com.iws_manager.iws_manager_api.dtos.project.ProjectRequestDTO;
import com.iws_manager.iws_manager_api.services.interfaces.ProjectServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v2/projects")
public class ProjectControllerV2 {

    private final ProjectServiceV2 projectServiceV2;

    @Autowired
    public ProjectControllerV2(ProjectServiceV2 projectServiceV2) {
        this.projectServiceV2 = projectServiceV2;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProjectResponseDTO> create(@RequestBody ProjectRequestDTO projectRequest) {
        ProjectResponseDTO createdProject = projectServiceV2.create(projectRequest);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getById(@PathVariable Long id) {
        return projectServiceV2.findById(id)
                .map(project -> new ResponseEntity<>(project, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getAll() {
        List<ProjectResponseDTO> projects = projectServiceV2.findAll();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> update(@PathVariable Long id,
            @RequestBody ProjectRequestDTO projectDetails) {
        try {
            ProjectResponseDTO updatedProject = projectServiceV2.update(id, projectDetails);
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        projectServiceV2.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Date Fields
    @GetMapping("/by-approval-date/{approvalDate}")
    public ResponseEntity<List<ProjectResponseDTO>> getByApprovalDate(
            @PathVariable LocalDate approvalDate) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByApprovalDate(approvalDate);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-authorization-date/{authorizationDate}")
    public ResponseEntity<List<ProjectResponseDTO>> getByAuthorizationDate(
            @PathVariable LocalDate authorizationDate) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByAuthorizationDate(authorizationDate);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-end-approval/{endApproval}")
    public ResponseEntity<List<ProjectResponseDTO>> getByEndApproval(
            @PathVariable LocalDate endApproval) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByEndApproval(endApproval);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-end-date/{endDate}")
    public ResponseEntity<List<ProjectResponseDTO>> getByEndDate(
            @PathVariable LocalDate endDate) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByEndDate(endDate);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-start-approval/{startApproval}")
    public ResponseEntity<List<ProjectResponseDTO>> getByStartApproval(
            @PathVariable LocalDate startApproval) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByStartApproval(startApproval);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-start-date/{startDate}")
    public ResponseEntity<List<ProjectResponseDTO>> getByStartDate(
            @PathVariable LocalDate startDate) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByStartDate(startDate);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    // Number Fields
    @GetMapping("/by-chance/{chance}")
    public ResponseEntity<List<ProjectResponseDTO>> getByChance(@PathVariable BigDecimal chance) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByChance(chance);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-funding-rate/{fundingRate}")
    public ResponseEntity<List<ProjectResponseDTO>> getByFundingRate(@PathVariable BigDecimal fundingRate) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByFundingRate(fundingRate);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-hourly-rate/{hourlyRateMueu}")
    public ResponseEntity<List<ProjectResponseDTO>> getByHourlyRate(@PathVariable BigDecimal hourlyRateMueu) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByHourlyRateMueu(hourlyRateMueu);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-max-hours-month/{maxHoursPerMonth}")
    public ResponseEntity<List<ProjectResponseDTO>> getByMaxHoursPerMonth(@PathVariable BigDecimal maxHoursPerMonth) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByMaxHoursPerMonth(maxHoursPerMonth);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-max-hours-year/{maxHoursPerYear}")
    public ResponseEntity<List<ProjectResponseDTO>> getByMaxHoursPerYear(@PathVariable BigDecimal maxHoursPerYear) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByMaxHoursPerYear(maxHoursPerYear);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-productive-hours-year/{productiveHoursPerYear}")
    public ResponseEntity<List<ProjectResponseDTO>> getByProductiveHoursPerYear(
            @PathVariable BigDecimal productiveHoursPerYear) {
        List<ProjectResponseDTO> projects = projectServiceV2
                .getProjectsByProductiveHoursPerYear(productiveHoursPerYear);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-share-research/{shareResearch}")
    public ResponseEntity<List<ProjectResponseDTO>> getByShareResearch(@PathVariable BigDecimal shareResearch) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByShareResearch(shareResearch);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-stuff-flat/{stuffFlat}")
    public ResponseEntity<List<ProjectResponseDTO>> getByStuffFlat(@PathVariable BigDecimal stuffFlat) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByStuffFlat(stuffFlat);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    // Order Fields
    @GetMapping("/by-order-fue/{orderIdFue}")
    public ResponseEntity<List<ProjectResponseDTO>> getByOrderIdFue(@PathVariable Long orderIdFue) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByOrderIdFue(orderIdFue);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-order-admin/{orderIdAdmin}")
    public ResponseEntity<List<ProjectResponseDTO>> getByOrderIdAdmin(@PathVariable Long orderIdAdmin) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByOrderIdAdmin(orderIdAdmin);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    // Text Fields
    @GetMapping("/with-comment-containing")
    public ResponseEntity<List<ProjectResponseDTO>> getByCommentContaining(@RequestParam String keyword) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByCommentContaining(keyword);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-finance-authority/{authority}")
    public ResponseEntity<List<ProjectResponseDTO>> getByFinanceAuthority(@PathVariable String authority) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByFinanceAuthority(authority);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-funding-label/{label}")
    public ResponseEntity<List<ProjectResponseDTO>> getByFundingLabel(@PathVariable String label) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByFundingLabel(label);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/with-note-containing")
    public ResponseEntity<List<ProjectResponseDTO>> getByNoteContaining(@RequestParam String text) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByNoteContaining(text);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-project-label/{projectLabel}")
    public ResponseEntity<List<ProjectResponseDTO>> getByProjectLabel(@PathVariable String projectLabel) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByProjectLabel(projectLabel);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-project-name/{projectName}")
    public ResponseEntity<List<ProjectResponseDTO>> getByProjectName(@PathVariable String projectName) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByProjectName(projectName);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-title/{title}")
    public ResponseEntity<List<ProjectResponseDTO>> getByTitle(@PathVariable String title) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByTitle(title);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    // Entity Relationships
    @GetMapping("/by-customer/{customerId}")
    public ResponseEntity<List<ProjectResponseDTO>> getByCustomerId(@PathVariable Long customerId) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByCustomerId(customerId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-empiws20/{empiws20Id}")
    public ResponseEntity<List<ProjectResponseDTO>> getByEmpiws20Id(@PathVariable Long empiws20Id) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByEmpiws20Id(empiws20Id);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-funding-program/{fundingProgramId}")
    public ResponseEntity<List<ProjectResponseDTO>> getByFundingProgramId(@PathVariable Long fundingProgramId) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByFundingProgramId(fundingProgramId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-promoter/{promoterId}")
    public ResponseEntity<List<ProjectResponseDTO>> getProjectsByPromoterId(@PathVariable Long promoterId) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByPromoterId(promoterId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-status/{statusId}")
    public ResponseEntity<List<ProjectResponseDTO>> getProjectsByStatusId(@PathVariable Long statusId) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByStatusId(statusId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    // Date Helpers
    @GetMapping("/by-approval-date-range")
    public ResponseEntity<List<ProjectResponseDTO>> getByApprovalDateRange(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByApprovalDateBetween(start, end);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-start-date-range")
    public ResponseEntity<List<ProjectResponseDTO>> getByStartDateRange(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByStartDateBetween(start, end);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/with-authorization-before")
    public ResponseEntity<List<ProjectResponseDTO>> getWithAuthorizationBefore(@RequestParam LocalDate date) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByAuthorizationDateBefore(date);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/with-end-date-after")
    public ResponseEntity<List<ProjectResponseDTO>> getWithEndDateAfter(@RequestParam LocalDate date) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByEndDateAfter(date);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    // Number Helpers
    @GetMapping("/with-chance-greater-than")
    public ResponseEntity<List<ProjectResponseDTO>> getWithChanceGreaterThan(@RequestParam BigDecimal chance) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByChanceGreaterThan(chance);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/with-funding-rate-less-than")
    public ResponseEntity<List<ProjectResponseDTO>> getWithFundingRateLessThan(@RequestParam BigDecimal fundingRate) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByFundingRateLessThan(fundingRate);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/by-hourly-rate-range")
    public ResponseEntity<List<ProjectResponseDTO>> getByHourlyRateRange(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByHourlyRateMueuBetween(min, max);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    // Text Helpers
    @GetMapping("/with-name-containing")
    public ResponseEntity<List<ProjectResponseDTO>> getWithNameContaining(@RequestParam String name) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByProjectNameContainingIgnoreCase(name);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/with-funding-label-starting")
    public ResponseEntity<List<ProjectResponseDTO>> getWithFundingLabelStarting(@RequestParam String prefix) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByFundingLabelStartingWith(prefix);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/with-title-ending")
    public ResponseEntity<List<ProjectResponseDTO>> getWithTitleEnding(@RequestParam String suffix) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByTitleEndingWith(suffix);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    // Special Queries
    @GetMapping("/by-customer/{customerId}/ordered-by-start-date")
    public ResponseEntity<List<ProjectResponseDTO>> getByCustomerOrderedByStartDate(@PathVariable Long customerId) {
        List<ProjectResponseDTO> projects = projectServiceV2.getProjectsByCustomerIdOrderByStartDateDesc(customerId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }
}