package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.AbsenceType;
import com.iws_manager.iws_manager_api.services.interfaces.AbsenceTypeService;
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
@RequestMapping("/api/v1/absensetypes")
public class AbsenceTypeController {
    private final AbsenceTypeService absenceTypeService;

    @Autowired
    public AbsenceTypeController(AbsenceTypeService absenceTypeService) {
        this.absenceTypeService = absenceTypeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createAbsenseType(@RequestBody AbsenceType absenceType){
        AbsenceType createdAbsenseType = absenceTypeService.create(absenceType);
        return new ResponseEntity<>(createdAbsenseType,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbsenceType> getAbsenseTypeById(@PathVariable Long id){
        return absenceTypeService.findById(id)
                .map(absenceType -> new ResponseEntity<>(absenceType, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<AbsenceType>> getAllAbsenceTypes() {
        List<AbsenceType> absenceTypes = absenceTypeService.findAll();
        return new ResponseEntity<>(absenceTypes, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AbsenceType> updateAbsenseType(@PathVariable Long id, @RequestBody AbsenceType absenceTypeDetails){
        try {
            AbsenceType updateAbsenceType = absenceTypeService.update(id, absenceTypeDetails);
            return new ResponseEntity<>(updateAbsenceType, HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteAbsenseType(@PathVariable Long id) {
        absenceTypeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
