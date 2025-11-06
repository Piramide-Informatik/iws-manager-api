package com.iws_manager.iws_manager_api.controllers;

import java.time.LocalDate;

import com.iws_manager.iws_manager_api.models.HolidayYear;
import com.iws_manager.iws_manager_api.services.interfaces.HolidayYearService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/holiday-year")
public class HolidayYearController {
    private final HolidayYearService holidayYearService;

    @Autowired
    public HolidayYearController(HolidayYearService holidayYearService) {
        this.holidayYearService = holidayYearService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createHolidayYear(@RequestBody HolidayYear holidayYear){
        HolidayYear createHolidayYear = holidayYearService.create(holidayYear);
        return new ResponseEntity<>(createHolidayYear, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<HolidayYear>> getAll() {
        List<HolidayYear> holidayYears = holidayYearService.getAll();
        return new ResponseEntity<>(holidayYears,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HolidayYear> getHolidayYearById(@PathVariable Long id){
        return  holidayYearService.findById(id)
                .map( holidayYear -> new ResponseEntity<>(holidayYear, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HolidayYear> updateHolidayYear(
            @PathVariable Long id,
            @RequestBody HolidayYear holidayYearDetails){
        try {
            HolidayYear updateHolidayYear = holidayYearService.update(id, holidayYearDetails);
            return new ResponseEntity<>(updateHolidayYear, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/public-holiday/{id}")
    public List<HolidayYear> getByPublicHoliday(@PathVariable Long id) {
        return holidayYearService.getByPublicHolidayId(id);
    }

    @PostMapping("/public-holiday/{id}/next")
    @ResponseStatus(HttpStatus.CREATED)
    public HolidayYear createNextYear(@PathVariable Long id) {
        return holidayYearService.createNextYear(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteHolidayYear(@PathVariable Long id) {
        holidayYearService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<List<HolidayYear>> getByYear(@PathVariable String year) {
        try {
            LocalDate yearDate = LocalDate.parse(year + "-01-01"); 
            List<HolidayYear> holidayYears = holidayYearService.getByYear(yearDate);
            return new ResponseEntity<>(holidayYears, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsByYearAndPublicHoliday(
            @RequestParam LocalDate year,
            @RequestParam Long publicHolidayId) {
        boolean exists = holidayYearService.existsByYearAndPublicHoliday(year, publicHolidayId);
        return ResponseEntity.ok(exists);
    }
}
