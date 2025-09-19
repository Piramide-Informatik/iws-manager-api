package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.Subcontract;
import com.iws_manager.iws_manager_api.services.interfaces.SubcontractService;

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

import com.iws_manager.iws_manager_api.models.SubcontractProject;

@RestController
@RequestMapping("/api/v1/subcontracts")
public class SubcontractController {
    private final SubcontractService subcontractService;

    @Autowired
    public SubcontractController(SubcontractService subcontractService) {
        this.subcontractService = subcontractService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody Subcontract subcontract){
        Subcontract createSubcontract = subcontractService.create(subcontract);
        return new ResponseEntity<>(createSubcontract, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subcontract> getById(@PathVariable Long id){
        return  subcontractService.findById(id)
                .map( subcontract -> new ResponseEntity<>(subcontract, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Subcontract>> getAll(){
        List<Subcontract> subcontracts = subcontractService.findAll();
        return new ResponseEntity<>(subcontracts, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subcontract> update(@PathVariable Long id, @RequestBody Subcontract subcontractDetails){
        try {
            Subcontract updateSubcontract = subcontractService.update(id, subcontractDetails);
            return new ResponseEntity<>(updateSubcontract, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
         subcontractService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/contractor/{contractorId}")
    public ResponseEntity<List<Subcontract>> getSubcontractsByContractorId(@PathVariable Long contractorId) {
        List<Subcontract> subcontracts = subcontractService.findByContractorId(contractorId);
        return new ResponseEntity<>(subcontracts, HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Subcontract>> getSubcontractsByCustomerId(@PathVariable Long customerId) {
        List<Subcontract> subcontracts = subcontractService.findByCustomerId(customerId);
        return new ResponseEntity<>(subcontracts, HttpStatus.OK);
    }

    @GetMapping("/projectcostcenter/{projectcostcenterId}")
    public ResponseEntity<List<Subcontract>> getSubcontractsByProjectCostCenterId(@PathVariable Long projectcostcenterId) {
        List<Subcontract> subcontracts = subcontractService.findByProjectCostCenterId(projectcostcenterId);
        return new ResponseEntity<>(subcontracts, HttpStatus.OK);
    }

   @PutMapping("/{id}/recalculate-subcontractproject")
    public ResponseEntity<Subcontract> updateAndRecalculate(
            @PathVariable Long id,
            @RequestBody Subcontract body
    ) {
        Subcontract result = subcontractService.updateAndRecalculate(id, body);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/recalculate-subcontractproject")
    public ResponseEntity<List<SubcontractProject>> recalculateSubcontractProjects(@PathVariable Long id) {
        List<SubcontractProject> projects = subcontractService.recalculateSubcontractProjects(id);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }
}
