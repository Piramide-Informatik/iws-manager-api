package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.dtos.absenceday.*;
import com.iws_manager.iws_manager_api.mappers.AbsenceDayMapper;
import com.iws_manager.iws_manager_api.services.v2.interfaces.AbsenceDayServiceV2;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v2/absence-days")
public class AbsenceDayControllerV2 {

    private final AbsenceDayServiceV2 absenceDayService;
    private final AbsenceDayMapper absenceDayMapper;

    @Autowired
    public AbsenceDayControllerV2(
            AbsenceDayServiceV2 absenceDayService,
            AbsenceDayMapper absenceDayMapper) {
        this.absenceDayService = absenceDayService;
        this.absenceDayMapper = absenceDayMapper;
    }

    // ========== CRUD Operations ==========

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AbsenceDayDetailDTO> create(
            @Valid @RequestBody AbsenceDayRequestDTO requestDTO) {
        
        var createdAbsenceDay = absenceDayService.createFromDTO(requestDTO);
        var responseDTO = absenceDayMapper.toDetailDTO(createdAbsenceDay);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbsenceDayDetailDTO> getById(@PathVariable Long id) {
        return absenceDayService.findById(id)
                .map(absenceDayMapper::toDetailDTO)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<AbsenceDayInfoDTO>> getAll() {
        var absenceDays = absenceDayService.findAll();
        var responseDTOs = absenceDayMapper.toInfoDTOList(absenceDays);
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AbsenceDayDetailDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody AbsenceDayRequestDTO requestDTO) {
        
        var updatedAbsenceDay = absenceDayService.updateFromDTO(id, requestDTO);
        var responseDTO = absenceDayMapper.toDetailDTO(updatedAbsenceDay);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        absenceDayService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // ========== Query Operations ==========

    @GetMapping("/by-employee/{employeeId}")
    public ResponseEntity<List<AbsenceDayInfoDTO>> getByEmployeeId(@PathVariable Long employeeId) {
        var absenceDays = absenceDayService.getByEmployeeId(employeeId);
        var responseDTOs = absenceDayMapper.toInfoDTOList(absenceDays);
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @GetMapping("/by-employee/{employeeId}/date-range")
    public ResponseEntity<List<AbsenceDayInfoDTO>> getByEmployeeIdAndDateRange(
            @PathVariable Long employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        var absenceDays = absenceDayService.getByEmployeeIdAndDateRange(
                employeeId, startDate, endDate);
        var responseDTOs = absenceDayMapper.toInfoDTOList(absenceDays);
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @GetMapping("/by-employee/{employeeId}/absence-type/{absenceTypeId}")
    public ResponseEntity<List<AbsenceDayInfoDTO>> getByEmployeeIdAndAbsenceTypeId(
            @PathVariable Long employeeId,
            @PathVariable Long absenceTypeId) {
        
        var absenceDays = absenceDayService.getByEmployeeIdAndAbsenceTypeId(
                employeeId, absenceTypeId);
        var responseDTOs = absenceDayMapper.toInfoDTOList(absenceDays);
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsByEmployeeIdAndAbsenceDate(
            @RequestParam Long employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate absenceDate) {
        
        boolean exists = absenceDayService.existsByEmployeeIdAndAbsenceDate(employeeId, absenceDate);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/by-employee/{employeeId}/year/{year}")
    public ResponseEntity<List<AbsenceDayInfoDTO>> getByEmployeeIdAndYear(
            @PathVariable Long employeeId,
            @PathVariable int year) {
        
        var absenceDays = absenceDayService.getByEmployeeIdAndYear(employeeId, year);
        var responseDTOs = absenceDayMapper.toInfoDTOList(absenceDays);
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @GetMapping("/count/by-employee/{employeeId}/absence-type/{absenceTypeId}/year/{year}")
    public ResponseEntity<Long> countByEmployeeIdAndAbsenceTypeIdAndYear(
            @PathVariable Long employeeId,
            @PathVariable Long absenceTypeId,
            @PathVariable int year) {
        
        long count = absenceDayService.countByEmployeeIdAndAbsenceTypeIdAndYear(
            employeeId, absenceTypeId, year);
        return ResponseEntity.ok(count);
    }

    // ========== New V2 Operations ==========

    @GetMapping("/count/by-type/employee/{employeeId}")
    public ResponseEntity<List<AbsenceDayCountDTO>> countByTypeForEmployee(
            @PathVariable Long employeeId) {
        
        var results = absenceDayService.countAbsenceDaysByTypeForEmployee(employeeId);
        var countDTOs = absenceDayMapper.toCountDTOList(results);
        return new ResponseEntity<>(countDTOs, HttpStatus.OK);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<AbsenceDayInfoDTO>> filter(
            @Valid @RequestBody AbsenceDayFilterDTO filterDTO) {
        
        var absenceDays = absenceDayService.filter(filterDTO);
        var responseDTOs = absenceDayMapper.toInfoDTOList(absenceDays);
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}/detail")
    public ResponseEntity<AbsenceDayDetailDTO> getByIdWithRelations(@PathVariable Long id) {
        return absenceDayService.findByIdWithRelations(id)
                .map(absenceDayMapper::toDetailDTO)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}