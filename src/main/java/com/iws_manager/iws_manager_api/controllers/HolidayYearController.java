package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.HolidayYear;
import com.iws_manager.iws_manager_api.services.interfaces.HolidayYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/holiday-year")
public class HolidayYearController {
    private final HolidayYearService holidayYearService;

    @Autowired
    public HolidayYearController(HolidayYearService holidayYearService) {
        this.holidayYearService = holidayYearService;
    }

    @GetMapping
    public ResponseEntity<List<HolidayYear>> getAll() {
        List<HolidayYear> holidayYears = holidayYearService.getAll();
        return new ResponseEntity<>(holidayYears,HttpStatus.OK);
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
}
