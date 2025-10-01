package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.PublicHoliday;
import com.iws_manager.iws_manager_api.models.State;
import com.iws_manager.iws_manager_api.services.interfaces.PublicHolidayService;
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
@RequestMapping("/api/v1/holidays")
public class PublicHolidayController {
    private final PublicHolidayService publicHolidayService;

    @Autowired
    public PublicHolidayController(PublicHolidayService publicHolidayService) {
        this.publicHolidayService = publicHolidayService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createPublicHoliday(@RequestBody PublicHoliday publicHoliday) {
        if (publicHoliday.getName() == null || publicHoliday.getName().trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error","PublicHoliday is required");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        PublicHoliday createdPublicHoliday = publicHolidayService.create(publicHoliday);
        return new ResponseEntity<>(createdPublicHoliday, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicHoliday> getPublicHolidayById(@PathVariable Long id){
        return publicHolidayService.findById(id)
                .map(publicHoliday -> new ResponseEntity<>(publicHoliday,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<PublicHoliday>> getAllByOrderBySequenceNo() {
        List<PublicHoliday> publicHolidays = publicHolidayService.findAllByOrderBySequenceNo();
        return new ResponseEntity<>(publicHolidays,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicHoliday> updatePublicHoliday(
            @PathVariable Long id,
            @RequestBody PublicHoliday publicHolidayDetails){
        try {
            PublicHoliday updatedPublicHoliday = publicHolidayService.update(id, publicHolidayDetails);
            return new ResponseEntity<>(updatedPublicHoliday, HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deletePublicHoliday(@PathVariable Long id) {
        publicHolidayService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/states")
    @ResponseStatus(HttpStatus.OK)
    public List<State> getStates(@PathVariable Long id) {
        return publicHolidayService.getStatesWithSelection(id);
    }

    @PostMapping("/{id}/states")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveSelections(@PathVariable Long id, @RequestBody List<Long> selectedStates) {
        publicHolidayService.saveStateSelections(id, selectedStates);
    }

    @GetMapping("/by-name-asc")
    public ResponseEntity<List<PublicHoliday>> getAll() {
        List<PublicHoliday> publicHolidays = publicHolidayService.findAll();
        return new ResponseEntity<>(publicHolidays,HttpStatus.OK);
    }

    @GetMapping("/by-sequenceno-desc")
    public ResponseEntity<List<PublicHoliday>> getAllByOrderBySequenceNoDesc() {
        List<PublicHoliday> publicHolidays = publicHolidayService.findAllByOrderBySequenceNoDesc();
        return new ResponseEntity<>(publicHolidays,HttpStatus.OK);
    }
}
