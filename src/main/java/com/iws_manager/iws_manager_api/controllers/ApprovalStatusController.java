package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.ApprovalStatus;
import com.iws_manager.iws_manager_api.services.interfaces.ApprovalStatusService;
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

@RestController
@RequestMapping("/api/v1/approvalstatuses")
public class ApprovalStatusController {
    private final ApprovalStatusService approvalStatusService;

    @Autowired
    public ApprovalStatusController(ApprovalStatusService approvalStatusService) {
        this.approvalStatusService = approvalStatusService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createApprovalStatus(@RequestBody ApprovalStatus approvalStatus) {
        ApprovalStatus createdApprovalStatus = approvalStatusService.create(approvalStatus);
        return new ResponseEntity<>(createdApprovalStatus, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApprovalStatus> getApprovalStatusById(@PathVariable Long id){
        return approvalStatusService.findById(id)
                .map(approvalStatus -> new ResponseEntity<>(approvalStatus,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<ApprovalStatus>> getAllApprovalStatus() {
        List<ApprovalStatus> approvalStatuses = approvalStatusService.findAll();
        return new ResponseEntity<>(approvalStatuses,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApprovalStatus> updateApprovalStatus(
            @PathVariable Long id,
            @RequestBody ApprovalStatus approvalStatusDetails){
        try {
            ApprovalStatus updatedApprovalStatus = approvalStatusService.update(id, approvalStatusDetails);
            return new ResponseEntity<>(updatedApprovalStatus, HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteApprovalStatus(@PathVariable Long id) {
        approvalStatusService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
