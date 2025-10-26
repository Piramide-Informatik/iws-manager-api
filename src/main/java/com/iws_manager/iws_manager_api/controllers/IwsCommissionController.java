package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.IwsCommission;
import com.iws_manager.iws_manager_api.services.interfaces.IwsCommissionService;
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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/iws-commissions")
public class IwsCommissionController {
    private final IwsCommissionService iwsCommissionService;

    @Autowired
    public IwsCommissionController(IwsCommissionService iwsCommissionService) {
        this.iwsCommissionService = iwsCommissionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createIwsCommission(@RequestBody IwsCommission commission) {

        if (commission.getCommission() == null ) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Commission is required");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        IwsCommission createdCommission = iwsCommissionService.create(commission);
        return new ResponseEntity<>(createdCommission, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IwsCommission> getIwsCommissionById(@PathVariable Long id) {
        return iwsCommissionService.findById(id)
                .map(commission -> new ResponseEntity<>(commission, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<IwsCommission>> getAllIwsCommissions() {
        List<IwsCommission> iwsCommission = iwsCommissionService.findAll();
        return new ResponseEntity<>(iwsCommission, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IwsCommission> updateIwsCommission(
            @PathVariable Long id,
            @RequestBody IwsCommission iwsCommissionDetails) {
        try {
            IwsCommission updateIwsCommission = iwsCommissionService.update(id, iwsCommissionDetails);
            return new ResponseEntity<>(updateIwsCommission, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteIwsCommission(@PathVariable Long id) {
        iwsCommissionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/fromOrderValue/min/{value}")
    public ResponseEntity<List<IwsCommission>> getByFromOrderValueLessThanEqual(@PathVariable BigDecimal value) {
        List<IwsCommission> results = iwsCommissionService.findByFromOrderValueLessThanEqual(value);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/fromOrderValue/max/{value}")
    public ResponseEntity<List<IwsCommission>> getByFromOrderValueGreaterThanEqual(@PathVariable BigDecimal value) {
        List<IwsCommission> results = iwsCommissionService.findByFromOrderValueGreaterThanEqual(value);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
