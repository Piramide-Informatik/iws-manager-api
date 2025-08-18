package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.BasicContract;
import com.iws_manager.iws_manager_api.services.interfaces.BasicContractService;

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

import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/basiccontracts")
public class BasicContractController {
    private final BasicContractService basicContractService;

    @Autowired
    public BasicContractController(BasicContractService basicContractService) {
        this.basicContractService = basicContractService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody BasicContract basicContract){
        BasicContract createBasicContract = basicContractService.create(basicContract);
        return new ResponseEntity<>(createBasicContract, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BasicContract> getById(@PathVariable Long id){
        return  basicContractService.findById(id)
                .map( basicContract -> new ResponseEntity<>(basicContract, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<BasicContract>> getAll(){
        List<BasicContract> basicContracts = basicContractService.findAll();
        return new ResponseEntity<>(basicContracts, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BasicContract> update(@PathVariable Long id, @RequestBody BasicContract basicContractDetails){
        try {
            BasicContract updateBasicContract = basicContractService.update(id, basicContractDetails);
            return new ResponseEntity<>(updateBasicContract, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try {
            basicContractService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //properties
    @GetMapping("/by-confirmationdate/{confirmationDate}")
    public ResponseEntity<List<BasicContract>> getByConfirmationDate(@PathVariable LocalDate confirmationDate) {
        List<BasicContract> basicContracts = basicContractService.getByConfirmationDate(confirmationDate);
        return new ResponseEntity<>(basicContracts, HttpStatus.OK);
    }

    @GetMapping("/by-contractlabel/{contractLabel}")
    public ResponseEntity<List<BasicContract>> getByContractLabel(@PathVariable String contractLabel) {
        List<BasicContract> basicContracts = basicContractService.getByContractLabel(contractLabel);
        return new ResponseEntity<>(basicContracts, HttpStatus.OK);
    }

    @GetMapping("/by-contractno/{contractNo}")
    public ResponseEntity<List<BasicContract>> getByContractNo(@PathVariable Integer contractNo) {
        List<BasicContract> basicContracts = basicContractService.getByContractNo(contractNo);
        return new ResponseEntity<>(basicContracts, HttpStatus.OK);
    }

    @GetMapping("/by-contractstatus/{contractStatusId}")
    public ResponseEntity<List<BasicContract>> getByContractStatusId(@PathVariable Long contractStatusId) {
        List<BasicContract> basicContracts = basicContractService.getByContractStatusId(contractStatusId);
        return new ResponseEntity<>(basicContracts, HttpStatus.OK);
    }

    @GetMapping("/by-contracttitle/{contractTitle}")
    public ResponseEntity<List<BasicContract>> getByContractTitle(@PathVariable String contractTitle) {
        List<BasicContract> basicContracts = basicContractService.getByContractTitle(contractTitle);
        return new ResponseEntity<>(basicContracts, HttpStatus.OK);
    }

    @GetMapping("/by-customer/{customerId}")
    public ResponseEntity<List<BasicContract>> getByCustomerId(@PathVariable Long customerId) {
        List<BasicContract> basicContracts = basicContractService.getByCustomerId(customerId);
        return new ResponseEntity<>(basicContracts, HttpStatus.OK);
    }

    @GetMapping("/by-date/{date}")
    public ResponseEntity<List<BasicContract>> getByDate(@PathVariable LocalDate date) {
        List<BasicContract> basicContracts = basicContractService.getByDate(date);
        return new ResponseEntity<>(basicContracts, HttpStatus.OK);
    }

    @GetMapping("/by-fundingprogram/{fundingProgramId}")
    public ResponseEntity<List<BasicContract>> getByFundingProgramId(@PathVariable Long fundingProgramId) {
        List<BasicContract> basicContracts = basicContractService.getByFundingProgramId(fundingProgramId);
        return new ResponseEntity<>(basicContracts, HttpStatus.OK);
    }

    @GetMapping("/by-employeeiws/{employeeIwsId}")
    public ResponseEntity<List<BasicContract>> getByEmployeeIwsId(@PathVariable Long employeeIwsId) {
        List<BasicContract> basicContracts = basicContractService.getByEmployeeIwsId(employeeIwsId);
        return new ResponseEntity<>(basicContracts, HttpStatus.OK);
    }

    // Customer-specific ordered finders
    @GetMapping("/by-customer/{customerId}/ordered-by-contractno")
    public ResponseEntity<List<BasicContract>> getByCustomerIdOrderByContractNoAsc(@PathVariable Long customerId) {
        List<BasicContract> basicContracts = basicContractService.getByCustomerIdOrderByContractNoAsc(customerId);
        return new ResponseEntity<>(basicContracts, HttpStatus.OK);
    }

    @GetMapping("/by-customer/{customerId}/ordered-by-contractlabel")
    public ResponseEntity<List<BasicContract>> getByCustomerIdOrderByContractLabelAsc(@PathVariable Long customerId) {
        List<BasicContract> basicContracts = basicContractService.getByCustomerIdOrderByContractLabelAsc(customerId);
        return new ResponseEntity<>(basicContracts, HttpStatus.OK);
    }
   
    // Date range queries

     @GetMapping("/by-date-range")
    public ResponseEntity<List<BasicContract>> getByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<BasicContract> contracts = basicContractService.getByDateBetween(startDate, endDate);
        return new ResponseEntity<>(contracts, HttpStatus.OK);
    }

    @GetMapping("/by-confirmation-date-range")
    public ResponseEntity<List<BasicContract>> getByConfirmationDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<BasicContract> contracts = basicContractService.getByConfirmationDateBetween(startDate, endDate);
        return new ResponseEntity<>(contracts, HttpStatus.OK);
    }

    // Filtered search endpoint
    @GetMapping("/filtered")
    public ResponseEntity<List<BasicContract>> findFilteredContracts(
            @RequestParam Long customerId,
            @RequestParam(required = false) Long contractStatusId,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        
        List<BasicContract> contracts = basicContractService.findFilteredContracts(
            customerId, contractStatusId, startDate, endDate);
            
        return new ResponseEntity<>(contracts, HttpStatus.OK);
    }
}
