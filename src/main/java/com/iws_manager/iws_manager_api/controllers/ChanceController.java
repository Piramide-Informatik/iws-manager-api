package com.iws_manager.iws_manager_api.controllers;

import java.util.List;

import com.iws_manager.iws_manager_api.models.Chance;
import com.iws_manager.iws_manager_api.services.interfaces.ChanceService;

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

@RestController
@RequestMapping("/api/v1/chances")
public class ChanceController {
    private final ChanceService chanceService;

    @Autowired
    public ChanceController(ChanceService chanceService) {
        this.chanceService = chanceService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody Chance chance){
        Chance createdChance = chanceService.create(chance);
        return new ResponseEntity<>(createdChance, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chance> getById(@PathVariable Long id){
        return chanceService.findById(id)
                .map(chance -> new ResponseEntity<>(chance, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Chance>> getAll(){
        List<Chance> chances = chanceService.findAll();
        return new ResponseEntity<>(chances, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Chance> update(@PathVariable Long id, @RequestBody Chance chanceDetails){
        try {
            Chance updatedChance = chanceService.update(id, chanceDetails);
            return new ResponseEntity<>(updatedChance, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try {
            chanceService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}