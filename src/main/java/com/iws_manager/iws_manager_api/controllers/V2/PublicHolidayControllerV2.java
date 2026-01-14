package com.iws_manager.iws_manager_api.controllers.V2;

import com.iws_manager.iws_manager_api.dtos.publicholiday.SimpleHolidayDTO;
import com.iws_manager.iws_manager_api.models.PublicHoliday;
import com.iws_manager.iws_manager_api.models.State;
import com.iws_manager.iws_manager_api.services.V2.interfaces.PublicHolidayServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v2/holidays")
public class PublicHolidayControllerV2 {

    private final PublicHolidayServiceV2 publicHolidayServiceV2;

    @Autowired
    public PublicHolidayControllerV2(PublicHolidayServiceV2 publicHolidayServiceV2) {
        this.publicHolidayServiceV2 = publicHolidayServiceV2;
    }

    // ========== ENDPOINTS V1 (MIGRADOS - MISMOS QUE V1) ==========

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createPublicHoliday(@RequestBody PublicHoliday publicHoliday) {
        PublicHoliday createdPublicHoliday = publicHolidayServiceV2.create(publicHoliday);
        return new ResponseEntity<>(createdPublicHoliday, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicHoliday> getPublicHolidayById(@PathVariable Long id) {
        return publicHolidayServiceV2.findById(id)
                .map(publicHoliday -> new ResponseEntity<>(publicHoliday, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<PublicHoliday>> getAllByOrderBySequenceNo() {
        List<PublicHoliday> publicHolidays = publicHolidayServiceV2.findAllByOrderBySequenceNo();
        return new ResponseEntity<>(publicHolidays, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicHoliday> updatePublicHoliday(
            @PathVariable Long id,
            @RequestBody PublicHoliday publicHolidayDetails) {
        try {
            PublicHoliday updatedPublicHoliday = publicHolidayServiceV2.update(id, publicHolidayDetails);
            return new ResponseEntity<>(updatedPublicHoliday, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deletePublicHoliday(@PathVariable Long id) {
        publicHolidayServiceV2.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/states")
    @ResponseStatus(HttpStatus.OK)
    public List<State> getStates(@PathVariable Long id) {
        return publicHolidayServiceV2.getStatesWithSelection(id);
    }

    @PostMapping("/{id}/states")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveSelections(@PathVariable Long id, @RequestBody List<Long> selectedStates) {
        publicHolidayServiceV2.saveStateSelections(id, selectedStates);
    }

    @GetMapping("/by-name-asc")
    public ResponseEntity<List<PublicHoliday>> getAll() {
        List<PublicHoliday> publicHolidays = publicHolidayServiceV2.findAll();
        return new ResponseEntity<>(publicHolidays, HttpStatus.OK);
    }

    @GetMapping("/by-sequenceno-desc")
    public ResponseEntity<List<PublicHoliday>> getAllByOrderBySequenceNoDesc() {
        List<PublicHoliday> publicHolidays = publicHolidayServiceV2.findAllByOrderBySequenceNoDesc();
        return new ResponseEntity<>(publicHolidays, HttpStatus.OK);
    }

    @GetMapping("/next-sequence-no")
    public ResponseEntity<Long> getNextSequenceNo() {
        Long next = publicHolidayServiceV2.getNextSequenceNo();
        return ResponseEntity.ok(next);
    }

    // ========== ENDPOINTS V2 (NUEVOS) ==========

    /**
     * V2 Endpoint: Obtiene todos los días feriados incluyendo sábados y domingos
     * Solo devuelve fecha y nombre
     * GET /api/v2/holidays/all-with-weekends?year=2024
     */
    @GetMapping("/all-with-weekends")
    public ResponseEntity<List<SimpleHolidayDTO>> getAllHolidaysWithWeekends(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Long stateId) {

        List<SimpleHolidayDTO> holidays = publicHolidayServiceV2.getSimpleHolidaysWithWeekends(year, stateId);
        return ResponseEntity.ok(holidays);
    }

    /**
     * V2 Endpoint: Obtiene feriados en un rango de fechas incluyendo sábados y
     * domingos
     * Solo devuelve fecha y nombre
     * GET /api/v2/holidays/range?startDate=2024-01-01&endDate=2024-12-31
     */
    @GetMapping("/range")
    public ResponseEntity<List<SimpleHolidayDTO>> getHolidaysInRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Long stateId) {

        List<SimpleHolidayDTO> holidays = publicHolidayServiceV2.getSimpleHolidaysInRange(startDate, endDate, stateId);
        return ResponseEntity.ok(holidays);
    }

    /**
     * V2 Endpoint: Obtiene solo fines de semana (sábados y domingos)
     * GET /api/v2/holidays/weekends-only?year=2024
     */
    @GetMapping("/weekends-only")
    public ResponseEntity<List<SimpleHolidayDTO>> getWeekendsOnly(
            @RequestParam(required = false) Integer year) {

        List<SimpleHolidayDTO> weekends = publicHolidayServiceV2.getWeekendsOnly(year);
        return ResponseEntity.ok(weekends);
    }

    /**
     * V2 Endpoint: Obtiene solo feriados de la base de datos
     * GET /api/v2/holidays/database?year=2024
     */
    @GetMapping("/database")
    public ResponseEntity<List<SimpleHolidayDTO>> getDatabaseHolidays(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Long stateId) {

        List<SimpleHolidayDTO> holidays = publicHolidayServiceV2.getDatabaseHolidays(year, stateId);
        return ResponseEntity.ok(holidays);
    }

    /**
     * V2 Endpoint: Obtiene solo feriados de la base de datos en un rango de fechas
     * GET /api/v2/holidays/database-range?startDate=2024-01-01&endDate=2024-12-31
     */
    @GetMapping("/database-range")
    public ResponseEntity<List<SimpleHolidayDTO>> getDatabaseHolidaysInRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Long stateId) {

        List<SimpleHolidayDTO> holidays = publicHolidayServiceV2.getDatabaseHolidaysInRange(startDate, endDate,
                stateId);
        return ResponseEntity.ok(holidays);
    }

    /**
     * V2 Endpoint: Obtiene solo fines de semana en un rango de fechas
     * GET /api/v2/holidays/weekends-range?startDate=2024-01-01&endDate=2024-12-31
     */
    @GetMapping("/weekends-range")
    public ResponseEntity<List<SimpleHolidayDTO>> getWeekendsInRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<SimpleHolidayDTO> weekends = publicHolidayServiceV2.getWeekendsInRange(startDate, endDate);
        return ResponseEntity.ok(weekends);
    }
}