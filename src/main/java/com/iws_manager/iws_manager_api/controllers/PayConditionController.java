package com.iws_manager.iws_manager_api.controllers;

import java.math.BigDecimal;

import com.iws_manager.iws_manager_api.models.PayCondition;
import com.iws_manager.iws_manager_api.services.interfaces.PayConditionService;

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
@RequestMapping("/api/v1/pay-condition")
public class PayConditionController {
    private final PayConditionService payConditionService;

    @Autowired
    public PayConditionController(PayConditionService payConditionService) {
        this.payConditionService = payConditionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody PayCondition payCondition){
        PayCondition createPayCondition = payConditionService.create(payCondition);
        return new ResponseEntity<>(createPayCondition, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayCondition> getById(@PathVariable Long id){
        return  payConditionService.findById(id)
                .map( payCondition -> new ResponseEntity<>(payCondition, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<PayCondition>> getAll(){
        List<PayCondition> payConditions = payConditionService.findAll();
        return new ResponseEntity<>(payConditions, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PayCondition> update(@PathVariable Long id, @RequestBody PayCondition payConditionDetails){
        try {
            PayCondition updatePayCondition = payConditionService.update(id, payConditionDetails);
            return new ResponseEntity<>(updatePayCondition, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try {
            payConditionService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<List<PayCondition>> getByName(@PathVariable String name) {
        List<PayCondition> payConditions = payConditionService.getByName(name);
        return new ResponseEntity<>(payConditions, HttpStatus.OK);
    }

    @GetMapping("/by-deadline/{deadline}")
    public ResponseEntity<List<PayCondition>> getByDeadline(@PathVariable Integer deadline) {
        List<PayCondition> payConditions = payConditionService.getByDeadline(deadline);
        return new ResponseEntity<>(payConditions, HttpStatus.OK);
    }
}
