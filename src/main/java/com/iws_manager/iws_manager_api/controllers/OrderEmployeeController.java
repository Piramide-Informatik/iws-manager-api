package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.OrderEmployee;
import com.iws_manager.iws_manager_api.services.interfaces.OrderEmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order-employees")
public class OrderEmployeeController {

    private final OrderEmployeeService orderEmployeeService;

    @Autowired
    public OrderEmployeeController(OrderEmployeeService orderEmployeeService) {
        this.orderEmployeeService = orderEmployeeService;
    }

    // ===== BASIC CRUD OPERATIONS =====

    @PostMapping
    public ResponseEntity<OrderEmployee> create(@RequestBody OrderEmployee orderEmployee) {
        OrderEmployee created = orderEmployeeService.create(orderEmployee);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderEmployee> getById(@PathVariable Long id) {
        return orderEmployeeService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<OrderEmployee>> getAll() {
        return ResponseEntity.ok(orderEmployeeService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderEmployee> update(@PathVariable Long id, @RequestBody OrderEmployee orderEmployee) {
        return ResponseEntity.ok(orderEmployeeService.update(id, orderEmployee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderEmployeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ===== GET OPERATIONS BY EMPLOYEE =====

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<OrderEmployee>> getByEmployeeId(@PathVariable Long employeeId) {
        return ResponseEntity.ok(orderEmployeeService.getByEmployeeId(employeeId));
    }

    @GetMapping("/employee/{employeeId}/ordered")
    public ResponseEntity<List<OrderEmployee>> getByEmployeeIdOrderByIdAsc(@PathVariable Long employeeId) {
        return ResponseEntity.ok(orderEmployeeService.getByEmployeeIdOrderByIdAsc(employeeId));
    }

    // ===== GET OPERATIONS BY ORDER =====

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderEmployee>> getByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderEmployeeService.getByOrderId(orderId));
    }

    @GetMapping("/order/{orderId}/ordered")
    public ResponseEntity<List<OrderEmployee>> getByOrderIdOrderByIdAsc(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderEmployeeService.getByOrderIdOrderByIdAsc(orderId));
    }

    // ===== GET OPERATIONS BY QUALIFICATION FZ =====

    @GetMapping("/qualification-fz/{qualificationFZId}")
    public ResponseEntity<List<OrderEmployee>> getByQualificationFZId(@PathVariable Long qualificationFZId) {
        return ResponseEntity.ok(orderEmployeeService.getByQualificationFZId(qualificationFZId));
    }

    @GetMapping("/qualification-fz/{qualificationFZId}/ordered")
    public ResponseEntity<List<OrderEmployee>> getByQualificationFZIdOrderByIdAsc(
            @PathVariable Long qualificationFZId) {
        return ResponseEntity.ok(orderEmployeeService.getByQualificationFZIdOrderByIdAsc(qualificationFZId));
    }

    // ===== GET OPERATIONS BY QUALIFICATION K MUI =====

    @GetMapping("/qualification-kmui/{qualificationkmui}")
    public ResponseEntity<List<OrderEmployee>> getByQualificationkmui(@PathVariable String qualificationkmui) {
        return ResponseEntity.ok(orderEmployeeService.getByQualificationkmui(qualificationkmui));
    }

    @GetMapping("/qualification-kmui/contains/{keyword}")
    public ResponseEntity<List<OrderEmployee>> getByQualificationkmuiContainingIgnoreCase(
            @PathVariable String keyword) {
        return ResponseEntity.ok(orderEmployeeService.getByQualificationkmuiContainingIgnoreCase(keyword));
    }

    @GetMapping("/qualification-kmui/{qualificationkmui}/ordered")
    public ResponseEntity<List<OrderEmployee>> getByQualificationkmuiOrderByIdAsc(
            @PathVariable String qualificationkmui) {
        return ResponseEntity.ok(orderEmployeeService.getByQualificationkmuiOrderByIdAsc(qualificationkmui));
    }

    // ===== GET OPERATIONS BY TITLE =====

    @GetMapping("/title/{title}")
    public ResponseEntity<List<OrderEmployee>> getByTitle(@PathVariable String title) {
        return ResponseEntity.ok(orderEmployeeService.getByTitle(title));
    }

    @GetMapping("/title/contains/{keyword}")
    public ResponseEntity<List<OrderEmployee>> getByTitleContainingIgnoreCase(@PathVariable String keyword) {
        return ResponseEntity.ok(orderEmployeeService.getByTitleContainingIgnoreCase(keyword));
    }

    @GetMapping("/title/{title}/ordered")
    public ResponseEntity<List<OrderEmployee>> getByTitleOrderByIdAsc(@PathVariable String title) {
        return ResponseEntity.ok(orderEmployeeService.getByTitleOrderByIdAsc(title));
    }

    // ===== GET OPERATIONS WITH COMBINED CRITERIA =====

    @GetMapping("/employee/{employeeId}/order/{orderId}")
    public ResponseEntity<List<OrderEmployee>> getByEmployeeIdAndOrderId(
            @PathVariable Long employeeId, @PathVariable Long orderId) {
        return ResponseEntity.ok(orderEmployeeService.getByEmployeeIdAndOrderId(employeeId, orderId));
    }

    @GetMapping("/order/{orderId}/qualification-fz/{qualificationFZId}")
    public ResponseEntity<List<OrderEmployee>> getByOrderIdAndQualificationFZId(
            @PathVariable Long orderId, @PathVariable Long qualificationFZId) {
        return ResponseEntity.ok(orderEmployeeService.getByOrderIdAndQualificationFZId(orderId, qualificationFZId));
    }

    @GetMapping("/employee/{employeeId}/qualification-fz/{qualificationFZId}")
    public ResponseEntity<List<OrderEmployee>> getByEmployeeIdAndQualificationFZId(
            @PathVariable Long employeeId, @PathVariable Long qualificationFZId) {
        return ResponseEntity
                .ok(orderEmployeeService.getByEmployeeIdAndQualificationFZId(employeeId, qualificationFZId));
    }

    @GetMapping("/employee/{employeeId}/order/{orderId}/qualification-fz/{qualificationFZId}")
    public ResponseEntity<List<OrderEmployee>> getByEmployeeOrderAndQualification(
            @PathVariable Long employeeId, @PathVariable Long orderId, @PathVariable Long qualificationFZId) {
        return ResponseEntity
                .ok(orderEmployeeService.getByEmployeeOrderAndQualification(employeeId, orderId, qualificationFZId));
    }

    // ===== GET OPERATIONS BY HOURLY RATE RANGE =====

    @GetMapping("/hourly-rate/greater-than")
    public ResponseEntity<List<OrderEmployee>> getByHourlyrateGreaterThan(@RequestParam BigDecimal hourlyrate) {
        return ResponseEntity.ok(orderEmployeeService.getByHourlyrateGreaterThan(hourlyrate));
    }

    @GetMapping("/hourly-rate/less-than")
    public ResponseEntity<List<OrderEmployee>> getByHourlyrateLessThan(@RequestParam BigDecimal hourlyrate) {
        return ResponseEntity.ok(orderEmployeeService.getByHourlyrateLessThan(hourlyrate));
    }

    @GetMapping("/hourly-rate/between")
    public ResponseEntity<List<OrderEmployee>> getByHourlyrateBetween(
            @RequestParam BigDecimal minHourlyrate, @RequestParam BigDecimal maxHourlyrate) {
        return ResponseEntity.ok(orderEmployeeService.getByHourlyrateBetween(minHourlyrate, maxHourlyrate));
    }

    // ===== GET OPERATIONS BY PLANNED HOURS RANGE =====

    @GetMapping("/planned-hours/greater-than")
    public ResponseEntity<List<OrderEmployee>> getByPlannedhoursGreaterThan(@RequestParam BigDecimal plannedhours) {
        return ResponseEntity.ok(orderEmployeeService.getByPlannedhoursGreaterThan(plannedhours));
    }

    @GetMapping("/planned-hours/less-than")
    public ResponseEntity<List<OrderEmployee>> getByPlannedhoursLessThan(@RequestParam BigDecimal plannedhours) {
        return ResponseEntity.ok(orderEmployeeService.getByPlannedhoursLessThan(plannedhours));
    }

    @GetMapping("/planned-hours/between")
    public ResponseEntity<List<OrderEmployee>> getByPlannedhoursBetween(
            @RequestParam BigDecimal minPlannedhours, @RequestParam BigDecimal maxPlannedhours) {
        return ResponseEntity.ok(orderEmployeeService.getByPlannedhoursBetween(minPlannedhours, maxPlannedhours));
    }

    // ===== GET OPERATIONS WITH MINIMUM RATE AND HOURS =====

    @GetMapping("/minimum-rate-hours")
    public ResponseEntity<List<OrderEmployee>> getWithMinimumRateAndHours(
            @RequestParam BigDecimal minRate, @RequestParam BigDecimal minHours) {
        return ResponseEntity.ok(orderEmployeeService.getWithMinimumRateAndHours(minRate, minHours));
    }

    // ===== GET OPERATIONS BY QUALIFICATION OR TITLE CONTAINING KEYWORD =====

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<OrderEmployee>> getByQualificationOrTitleContaining(@PathVariable String keyword) {
        return ResponseEntity.ok(orderEmployeeService.getByQualificationOrTitleContaining(keyword));
    }

    // ===== ORDERING OPERATIONS =====

    @GetMapping("/ordered/hourly-rate-asc")
    public ResponseEntity<List<OrderEmployee>> getAllOrderByHourlyrateAsc() {
        return ResponseEntity.ok(orderEmployeeService.getAllOrderByHourlyrateAsc());
    }

    @GetMapping("/ordered/hourly-rate-desc")
    public ResponseEntity<List<OrderEmployee>> getAllOrderByHourlyrateDesc() {
        return ResponseEntity.ok(orderEmployeeService.getAllOrderByHourlyrateDesc());
    }

    @GetMapping("/ordered/planned-hours-asc")
    public ResponseEntity<List<OrderEmployee>> getAllOrderByPlannedhoursAsc() {
        return ResponseEntity.ok(orderEmployeeService.getAllOrderByPlannedhoursAsc());
    }

    @GetMapping("/ordered/planned-hours-desc")
    public ResponseEntity<List<OrderEmployee>> getAllOrderByPlannedhoursDesc() {
        return ResponseEntity.ok(orderEmployeeService.getAllOrderByPlannedhoursDesc());
    }

    @GetMapping("/ordered/qualification-kmui-asc")
    public ResponseEntity<List<OrderEmployee>> getAllOrderByQualificationkmuiAsc() {
        return ResponseEntity.ok(orderEmployeeService.getAllOrderByQualificationkmuiAsc());
    }

    @GetMapping("/ordered/title-asc")
    public ResponseEntity<List<OrderEmployee>> getAllOrderByTitleAsc() {
        return ResponseEntity.ok(orderEmployeeService.getAllOrderByTitleAsc());
    }

    // ===== CALCULATION OPERATIONS =====

    @GetMapping("/order/{orderId}/total-cost")
    public ResponseEntity<BigDecimal> calculateTotalCostByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderEmployeeService.calculateTotalCostByOrder(orderId));
    }

    @GetMapping("/employee/{employeeId}/total-cost")
    public ResponseEntity<BigDecimal> calculateTotalCostByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(orderEmployeeService.calculateTotalCostByEmployee(employeeId));
    }

    // ===== CHECK EXISTENCE OPERATIONS =====

    @GetMapping("/exists/employee/{employeeId}/order/{orderId}")
    public ResponseEntity<Boolean> existsByEmployeeIdAndOrderId(
            @PathVariable Long employeeId, @PathVariable Long orderId) {
        return ResponseEntity.ok(orderEmployeeService.existsByEmployeeIdAndOrderId(employeeId, orderId));
    }

    @GetMapping("/exists/order/{orderId}")
    public ResponseEntity<Boolean> existsByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderEmployeeService.existsByOrderId(orderId));
    }

    @GetMapping("/exists/employee/{employeeId}")
    public ResponseEntity<Boolean> existsByEmployeeId(@PathVariable Long employeeId) {
        return ResponseEntity.ok(orderEmployeeService.existsByEmployeeId(employeeId));
    }

    @GetMapping("/exists/qualification-fz/{qualificationFZId}")
    public ResponseEntity<Boolean> existsByQualificationFZId(@PathVariable Long qualificationFZId) {
        return ResponseEntity.ok(orderEmployeeService.existsByQualificationFZId(qualificationFZId));
    }

    // ===== COUNT OPERATIONS =====

    @GetMapping("/count/order/{orderId}")
    public ResponseEntity<Long> countByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderEmployeeService.countByOrder(orderId));
    }

    @GetMapping("/count/employee/{employeeId}")
    public ResponseEntity<Long> countByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(orderEmployeeService.countByEmployee(employeeId));
    }

    // ===== VALIDATION =====

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateOrderEmployee(@RequestBody OrderEmployee orderEmployee) {
        return ResponseEntity.ok(orderEmployeeService.validateOrderEmployee(orderEmployee));
    }
}