package com.iws_manager.iws_manager_api.controllers;

import java.util.List;

import com.iws_manager.iws_manager_api.models.ReminderLevel;
import com.iws_manager.iws_manager_api.services.interfaces.ReminderLevelService;

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
@RequestMapping("/api/v1/reminder-levels")
public class ReminderLevelController {
    private final ReminderLevelService reminderLevelService;

    @Autowired
    public ReminderLevelController(ReminderLevelService reminderLevelService) {
        this.reminderLevelService = reminderLevelService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody ReminderLevel reminderLevel){
        ReminderLevel createdReminderLevel = reminderLevelService.create(reminderLevel);
        return new ResponseEntity<>(createdReminderLevel, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReminderLevel> getById(@PathVariable Long id){
        return reminderLevelService.findById(id)
                .map(reminderLevel -> new ResponseEntity<>(reminderLevel, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<ReminderLevel>> getAll(){
        List<ReminderLevel> reminderLevels = reminderLevelService.findAll();
        return new ResponseEntity<>(reminderLevels, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReminderLevel> update(@PathVariable Long id, @RequestBody ReminderLevel reminderLevelDetails){
        try {
            ReminderLevel updatedReminderLevel = reminderLevelService.update(id, reminderLevelDetails);
            return new ResponseEntity<>(updatedReminderLevel, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try {
            reminderLevelService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}