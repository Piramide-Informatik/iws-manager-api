package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.Contractor;
import com.iws_manager.iws_manager_api.services.interfaces.ContractorService;
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
@RequestMapping("/api/v1/contractors")
public class ContractorController {
    private final ContractorService contractorService;

    @Autowired
    public ContractorController(ContractorService contractorService) {
        this.contractorService = contractorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createContractor(@RequestBody Contractor contractor){
        Contractor createContractor = contractorService.create(contractor);
        return new ResponseEntity<>(createContractor, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contractor> getContractorById(@PathVariable Long id){
        return  contractorService.findById(id)
                .map( contractor -> new ResponseEntity<>(contractor, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Contractor>> getAllContractor(){
        List<Contractor> contractors = contractorService.findAll();
        return new ResponseEntity<>(contractors, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contractor> updateContractor(
            @PathVariable Long id,
            @RequestBody Contractor contractorDetails){
        try {
            Contractor updateContractor = contractorService.update(id, contractorDetails);
            return new ResponseEntity<>(updateContractor, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContractor(@PathVariable Long id){
        contractorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Contractor>> getContractorsByCustomerId(@PathVariable Long customerId) {
        List<Contractor> contractors = contractorService.getContractorsByCustomerId(customerId);
        return new ResponseEntity<>(contractors, HttpStatus.OK);
    }

    @GetMapping("/country/{countryId}")
    public ResponseEntity<List<Contractor>> getContractorsByCountryId(@PathVariable Long countryId) {
        List<Contractor> contractors = contractorService.getContractorsByCountryId(countryId);
        return new ResponseEntity<>(contractors, HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}/ordered-by-label")
    public ResponseEntity<List<Contractor>> getByCustomerIdOrderByLabelAsc(@PathVariable Long customerId) {
        List<Contractor> contractors = contractorService.getByCustomerIdOrderByLabelAsc(customerId);
        return new ResponseEntity<>(contractors, HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}/ordered-by-name")
    public ResponseEntity<List<Contractor>> getByCustomerIdOrderByNameAsc(@PathVariable Long customerId) {
        List<Contractor> contractors = contractorService.getByCustomerIdOrderByNameAsc(customerId);
        return new ResponseEntity<>(contractors, HttpStatus.OK);
    }
}
