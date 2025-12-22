package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.dtos.orderemployee.OrderEmployeeRequestDTO;
import com.iws_manager.iws_manager_api.dtos.orderemployee.OrderEmployeeResponseDTO;
import com.iws_manager.iws_manager_api.services.interfaces.OrderEmployeeServiceV2;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v2/order-employees")
@RequiredArgsConstructor
public class OrderEmployeeControllerV2 {

    private final OrderEmployeeServiceV2 orderEmployeeService;

    // ===== BASIC CRUD OPERATIONS =====

    @PostMapping
    public ResponseEntity<OrderEmployeeResponseDTO> create(@Valid @RequestBody OrderEmployeeRequestDTO orderEmployeeDTO) {
        OrderEmployeeResponseDTO created = orderEmployeeService.create(orderEmployeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderEmployeeResponseDTO> getById(@PathVariable Long id) {
        OrderEmployeeResponseDTO orderEmployee = orderEmployeeService.getById(id);
        return ResponseEntity.ok(orderEmployee);
    }

    @GetMapping
    public ResponseEntity<List<OrderEmployeeResponseDTO>> getAll() {
        List<OrderEmployeeResponseDTO> orderEmployees = orderEmployeeService.getAll();
        return ResponseEntity.ok(orderEmployees);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderEmployeeResponseDTO> update(
            @PathVariable Long id, 
            @Valid @RequestBody OrderEmployeeRequestDTO orderEmployeeDTO) {
        OrderEmployeeResponseDTO updated = orderEmployeeService.update(id, orderEmployeeDTO);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderEmployeeResponseDTO> partialUpdate(
            @PathVariable Long id,
            @Valid @RequestBody OrderEmployeeRequestDTO orderEmployeeDTO) {
        OrderEmployeeResponseDTO updated = orderEmployeeService.partialUpdate(id, orderEmployeeDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderEmployeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ===== GET OPERATIONS BY EMPLOYEE =====

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<OrderEmployeeResponseDTO>> getByEmployeeId(@PathVariable Long employeeId) {
        List<OrderEmployeeResponseDTO> orderEmployees = orderEmployeeService.getByEmployeeId(employeeId);
        return ResponseEntity.ok(orderEmployees);
    }

    @GetMapping("/employee/{employeeId}/ordered")
    public ResponseEntity<List<OrderEmployeeResponseDTO>> getByEmployeeIdOrderByIdAsc(@PathVariable Long employeeId) {
        List<OrderEmployeeResponseDTO> orderEmployees = orderEmployeeService.getByEmployeeIdOrderByIdAsc(employeeId);
        return ResponseEntity.ok(orderEmployees);
    }

    // ===== GET OPERATIONS BY ORDER =====

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderEmployeeResponseDTO>> getByOrderId(@PathVariable Long orderId) {
        List<OrderEmployeeResponseDTO> orderEmployees = orderEmployeeService.getByOrderId(orderId);
        return ResponseEntity.ok(orderEmployees);
    }

    @GetMapping("/order/{orderId}/ordered")
    public ResponseEntity<List<OrderEmployeeResponseDTO>> getByOrderIdOrderByIdAsc(@PathVariable Long orderId) {
        List<OrderEmployeeResponseDTO> orderEmployees = orderEmployeeService.getByOrderIdOrderByIdAsc(orderId);
        return ResponseEntity.ok(orderEmployees);
    }

    // ===== GET OPERATIONS BY QUALIFICATION FZ =====

    @GetMapping("/qualification-fz/{qualificationFZId}")
    public ResponseEntity<List<OrderEmployeeResponseDTO>> getByQualificationFZId(@PathVariable Long qualificationFZId) {
        List<OrderEmployeeResponseDTO> orderEmployees = orderEmployeeService.getByQualificationFZId(qualificationFZId);
        return ResponseEntity.ok(orderEmployees);
    }

    @GetMapping("/qualification-fz/{qualificationFZId}/ordered")
    public ResponseEntity<List<OrderEmployeeResponseDTO>> getByQualificationFZIdOrderByIdAsc(
            @PathVariable Long qualificationFZId) {
        List<OrderEmployeeResponseDTO> orderEmployees = orderEmployeeService.getByQualificationFZIdOrderByIdAsc(qualificationFZId);
        return ResponseEntity.ok(orderEmployees);
    }

    // ===== GET OPERATIONS BY QUALIFICATION K MUI =====

    @GetMapping("/qualification-kmui/{qualificationkmui}")
    public ResponseEntity<List<OrderEmployeeResponseDTO>> getByQualificationkmui(@PathVariable String qualificationkmui) {
        List<OrderEmployeeResponseDTO> orderEmployees = orderEmployeeService.getByQualificationkmui(qualificationkmui);
        return ResponseEntity.ok(orderEmployees);
    }

    @GetMapping("/qualification-kmui/contains/{keyword}")
    public ResponseEntity<List<OrderEmployeeResponseDTO>> getByQualificationkmuiContainingIgnoreCase(
            @PathVariable String keyword) {
        List<OrderEmployeeResponseDTO> orderEmployees = orderEmployeeService.getByQualificationkmuiContainingIgnoreCase(keyword);
        return ResponseEntity.ok(orderEmployees);
    }

    @GetMapping("/qualification-kmui/{qualificationkmui}/ordered")
    public ResponseEntity<List<OrderEmployeeResponseDTO>> getByQualificationkmuiOrderByIdAsc(
            @PathVariable String qualificationkmui) {
        List<OrderEmployeeResponseDTO> orderEmployees = orderEmployeeService.getByQualificationkmuiOrderByIdAsc(qualificationkmui);
        return ResponseEntity.ok(orderEmployees);
    }

    // ===== GET OPERATIONS BY TITLE =====

    @GetMapping("/title/{title}")
    public ResponseEntity<List<OrderEmployeeResponseDTO>> getByTitle(@PathVariable String title) {
        List<OrderEmployeeResponseDTO> orderEmployees = orderEmployeeService.getByTitle(title);
        return ResponseEntity.ok(orderEmployees);
    }

    @GetMapping("/title/contains/{keyword}")
    public ResponseEntity<List<OrderEmployeeResponseDTO>> getByTitleContainingIgnoreCase(@PathVariable String keyword) {
        List<OrderEmployeeResponseDTO> orderEmployees = orderEmployeeService.getByTitleContainingIgnoreCase(keyword);
        return ResponseEntity.ok(orderEmployees);
    }

    @GetMapping("/title/{title}/ordered")
    public ResponseEntity<List<OrderEmployeeResponseDTO>> getByTitleOrderByIdAsc(@PathVariable String title) {
        List<OrderEmployeeResponseDTO> orderEmployees = orderEmployeeService.getByTitleOrderByIdAsc(title);
        return ResponseEntity.ok(orderEmployees);
    }

    // ===== GET OPERATIONS WITH COMBINED CRITERIA =====

    @GetMapping("/employee/{employeeId}/order/{orderId}")
    public ResponseEntity<List<OrderEmployeeResponseDTO>> getByEmployeeIdAndOrderId(
            @PathVariable Long employeeId, @PathVariable Long orderId) {
        List<OrderEmployeeResponseDTO> orderEmployees = orderEmployeeService.getByEmployeeIdAndOrderId(employeeId, orderId);
        return ResponseEntity.ok(orderEmployees);
    }

    @GetMapping("/order/{orderId}/qualification-fz/{qualificationFZId}")
    public ResponseEntity<List<OrderEmployeeResponseDTO>> getByOrderIdAndQualificationFZId(
            @PathVariable Long orderId, @PathVariable Long qualificationFZId) {
        List<OrderEmployeeResponseDTO> orderEmployees = orderEmployeeService.getByOrderIdAndQualificationFZId(orderId, qualificationFZId);
        return ResponseEntity.ok(orderEmployees);
    }

    @GetMapping("/employee/{employeeId}/qualification-fz/{qualificationFZId}")
    public ResponseEntity<List<OrderEmployeeResponseDTO>> getByEmployeeIdAndQualificationFZId(
            @PathVariable Long employeeId, @PathVariable Long qualificationFZId) {
        List<OrderEmployeeResponseDTO> orderEmployees = orderEmployeeService.getByEmployeeIdAndQualificationFZId(employeeId, qualificationFZId);
        return ResponseEntity.ok(orderEmployees);
    }

    @GetMapping("/employee/{employeeId}/order/{orderId}/qualification-fz/{qualificationFZId}")
    public ResponseEntity<List<OrderEmployeeResponseDTO>> getByEmployeeOrderAndQualification(
            @PathVariable Long employeeId, @PathVariable Long orderId, @PathVariable Long qualificationFZId) {
        List<OrderEmployeeResponseDTO> orderEmployees = orderEmployeeService.getByEmployeeOrderAndQualification(employeeId, orderId, qualificationFZId);
        return ResponseEntity.ok(orderEmployees);
    }

    // ===== GET OPERATIONS BY HOURLY RATE RANGE =====

    @GetMapping("/hourly-rate/greater-than")
    public ResponseEntity<List<OrderEmployeeResponseDTO>> getByHourlyrateGreaterThan(@RequestParam BigDecimal hourlyrate) {
        List<OrderEmployeeResponseDTO> orderEmployees = orderEmployeeService.getByHourlyrateGreaterThan(hourlyrate);
        return ResponseEntity.ok(orderEmployees);
    }

    @GetMapping("/hourly-rate/less-than")
    public ResponseEntity<List<OrderEmployeeResponseDTO>> getByHourlyrateLessThan(@RequestParam BigDecimal hourlyrate) {
        List<OrderEmployeeResponseDTO> orderEmployees = orderEmployeeService.getByHourlyrateLessThan(hourlyrate);
        return ResponseEntity.ok(orderEmployees);
    }

    @GetMapping("/hourly-rate/between")
    public ResponseEntity<List<OrderEmployeeResponseDTO>> getByHourlyrateBetween(
            @RequestParam BigDecimal minHourlyrate, @RequestParam BigDecimal maxHourlyrate) {
        List<OrderEmployeeResponseDTO> orderEmployees = orderEmployeeService.getByHourlyrateBetween(minHourlyrate, maxHourlyrate);
        return ResponseEntity.ok(orderEmployees);
    }

    // ===== GET OPERATIONS BY PLANNED HOURS RANGE =====

    @GetMapping("/planned-hours/greater-than")
    public ResponseEntity<List<OrderEmployeeResponseDTO>> getByPlannedhoursGreaterThan(@RequestParam BigDecimal plannedhours) {
        List<OrderEmployeeResponseDTO> orderEmployees = orderEmployeeService.getByPlannedhoursGreaterThan(plannedhours);
        return ResponseEntity.ok(orderEmployees);
    }

    @GetMapping("/planned-hours/less-than")
    public ResponseEntity<List<OrderEmployeeResponseDTO>> getByPlannedhoursLessThan(@RequestParam BigDecimal plannedhours) {
        List<OrderEmployeeResponseDTO> orderEmployees = orderEmployeeService.getByPlannedhoursLessThan(plannedhours);
        return ResponseEntity.ok(orderEmployees);
    }

    @GetMapping("/planned-hours/between")
    public ResponseEntity<List<OrderEmployeeResponseDTO>> getByPlannedhoursBetween(
            @RequestParam BigDecimal minPlannedhours, @RequestParam BigDecimal maxPlannedhours) {
        List<OrderEmployeeResponseDTO> orderEmployees = orderEmployeeService.getByPlannedhoursBetween(minPlannedhours, maxPlannedhours);
        return ResponseEntity.ok(orderEmployees);
    }

    // ===== GET OPERATIONS WITH MINIMUM RATE AND HOURS =====

    @GetMapping("/minimum-rate-hours")
    public ResponseEntity<List<OrderEmployeeResponseDTO>> getWithMinimumRateAndHours(
            @RequestParam BigDecimal minRate, @RequestParam BigDecimal minHours) {
        List<OrderEmployeeResponseDTO> orderEmployees = orderEmployeeService.getWithMinimumRateAndHours(minRate, minHours);
        return ResponseEntity.ok(orderEmployees);
    }

    // ===== GET OPERATIONS BY QUALIFICATION OR TITLE CONTAINING KEYWORD =====

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<OrderEmployeeResponseDTO>> getByQualificationOrTitleContaining(@PathVariable String keyword) {
        List<OrderEmployeeResponseDTO> orderEmployees = orderEmployeeService.getByQualificationOrTitleContaining(keyword);
        return ResponseEntity.ok(orderEmployees);
    }

    // ===== ORDERING OPERATIONS =====

    @GetMapping("/ordered/hourly-rate-asc")
    public ResponseEntity<List<OrderEmployeeResponseDTO>> getAllOrderByHourlyrateAsc() {
        List<OrderEmployeeResponseDTO> orderEmployees = orderEmployeeService.getAllOrderByHourlyrateAsc();
        return ResponseEntity.ok(orderEmployees);
    }

    @GetMapping("/ordered/hourly-rate-desc")
    public ResponseEntity<List<OrderEmployeeResponseDTO>> getAllOrderByHourlyrateDesc() {
        List<OrderEmployeeResponseDTO> orderEmployees = orderEmployeeService.getAllOrderByHourlyrateDesc();
        return ResponseEntity.ok(orderEmployees);
    }

    @GetMapping("/ordered/planned-hours-asc")
    public ResponseEntity<List<OrderEmployeeResponseDTO>> getAllOrderByPlannedhoursAsc() {
        List<OrderEmployeeResponseDTO> orderEmployees = orderEmployeeService.getAllOrderByPlannedhoursAsc();
        return ResponseEntity.ok(orderEmployees);
    }

    @GetMapping("/ordered/planned-hours-desc")
    public ResponseEntity<List<OrderEmployeeResponseDTO>> getAllOrderByPlannedhoursDesc() {
        List<OrderEmployeeResponseDTO> orderEmployees = orderEmployeeService.getAllOrderByPlannedhoursDesc();
        return ResponseEntity.ok(orderEmployees);
    }

    @GetMapping("/ordered/qualification-kmui-asc")
    public ResponseEntity<List<OrderEmployeeResponseDTO>> getAllOrderByQualificationkmuiAsc() {
        List<OrderEmployeeResponseDTO> orderEmployees = orderEmployeeService.getAllOrderByQualificationkmuiAsc();
        return ResponseEntity.ok(orderEmployees);
    }

    @GetMapping("/ordered/title-asc")
    public ResponseEntity<List<OrderEmployeeResponseDTO>> getAllOrderByTitleAsc() {
        List<OrderEmployeeResponseDTO> orderEmployees = orderEmployeeService.getAllOrderByTitleAsc();
        return ResponseEntity.ok(orderEmployees);
    }

    // ===== CALCULATION OPERATIONS =====

    @GetMapping("/order/{orderId}/total-cost")
    public ResponseEntity<BigDecimal> calculateTotalCostByOrder(@PathVariable Long orderId) {
        BigDecimal totalCost = orderEmployeeService.calculateTotalCostByOrder(orderId);
        return ResponseEntity.ok(totalCost);
    }

    @GetMapping("/employee/{employeeId}/total-cost")
    public ResponseEntity<BigDecimal> calculateTotalCostByEmployee(@PathVariable Long employeeId) {
        BigDecimal totalCost = orderEmployeeService.calculateTotalCostByEmployee(employeeId);
        return ResponseEntity.ok(totalCost);
    }

    // ===== CHECK EXISTENCE OPERATIONS =====

    @GetMapping("/exists/employee/{employeeId}/order/{orderId}")
    public ResponseEntity<Boolean> existsByEmployeeIdAndOrderId(
            @PathVariable Long employeeId, @PathVariable Long orderId) {
        boolean exists = orderEmployeeService.existsByEmployeeIdAndOrderId(employeeId, orderId);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/exists/order/{orderId}")
    public ResponseEntity<Boolean> existsByOrderId(@PathVariable Long orderId) {
        boolean exists = orderEmployeeService.existsByOrderId(orderId);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/exists/employee/{employeeId}")
    public ResponseEntity<Boolean> existsByEmployeeId(@PathVariable Long employeeId) {
        boolean exists = orderEmployeeService.existsByEmployeeId(employeeId);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/exists/qualification-fz/{qualificationFZId}")
    public ResponseEntity<Boolean> existsByQualificationFZId(@PathVariable Long qualificationFZId) {
        boolean exists = orderEmployeeService.existsByQualificationFZId(qualificationFZId);
        return ResponseEntity.ok(exists);
    }

    // ===== COUNT OPERATIONS =====

    @GetMapping("/count/order/{orderId}")
    public ResponseEntity<Long> countByOrder(@PathVariable Long orderId) {
        Long count = orderEmployeeService.countByOrder(orderId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/employee/{employeeId}")
    public ResponseEntity<Long> countByEmployee(@PathVariable Long employeeId) {
        Long count = orderEmployeeService.countByEmployee(employeeId);
        return ResponseEntity.ok(count);
    }

}