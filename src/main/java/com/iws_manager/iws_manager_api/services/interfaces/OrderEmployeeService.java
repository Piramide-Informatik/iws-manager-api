package com.iws_manager.iws_manager_api.services.interfaces;

import com.iws_manager.iws_manager_api.models.OrderEmployee;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderEmployeeService {

    // Basic CRUD operations
    OrderEmployee create(OrderEmployee orderEmployee);

    Optional<OrderEmployee> getById(Long id);

    List<OrderEmployee> getAll();

    OrderEmployee update(Long id, OrderEmployee orderEmployee);

    void delete(Long id);

    // Get operations by employee
    List<OrderEmployee> getByEmployeeId(Long employeeId);

    List<OrderEmployee> getByEmployeeIdOrderByIdAsc(Long employeeId);

    // Get operations by order
    List<OrderEmployee> getByOrderId(Long orderId);

    List<OrderEmployee> getByOrderIdOrderByIdAsc(Long orderId);

    // Get operations by qualificationFZ
    List<OrderEmployee> getByQualificationFZId(Long qualificationFZId);

    List<OrderEmployee> getByQualificationFZIdOrderByIdAsc(Long qualificationFZId);

    // Get operations by qualificationkmui
    List<OrderEmployee> getByQualificationkmui(String qualificationkmui);

    List<OrderEmployee> getByQualificationkmuiContainingIgnoreCase(String qualificationkmui);

    List<OrderEmployee> getByQualificationkmuiOrderByIdAsc(String qualificationkmui);

    // Get operations by title
    List<OrderEmployee> getByTitle(String title);

    List<OrderEmployee> getByTitleContainingIgnoreCase(String title);

    List<OrderEmployee> getByTitleOrderByIdAsc(String title);

    // Get operations with combined criteria
    List<OrderEmployee> getByEmployeeIdAndOrderId(Long employeeId, Long orderId);

    List<OrderEmployee> getByOrderIdAndQualificationFZId(Long orderId, Long qualificationFZId);

    List<OrderEmployee> getByEmployeeIdAndQualificationFZId(Long employeeId, Long qualificationFZId);

    List<OrderEmployee> getByEmployeeOrderAndQualification(Long employeeId, Long orderId, Long qualificationFZId);

    // Get operations by hourlyrate range
    List<OrderEmployee> getByHourlyrateGreaterThan(BigDecimal hourlyrate);

    List<OrderEmployee> getByHourlyrateLessThan(BigDecimal hourlyrate);

    List<OrderEmployee> getByHourlyrateBetween(BigDecimal minHourlyrate, BigDecimal maxHourlyrate);

    // Get operations by plannedhours range
    List<OrderEmployee> getByPlannedhoursGreaterThan(BigDecimal plannedhours);

    List<OrderEmployee> getByPlannedhoursLessThan(BigDecimal plannedhours);

    List<OrderEmployee> getByPlannedhoursBetween(BigDecimal minPlannedhours, BigDecimal maxPlannedhours);

    // Get operations with minimum rate and hours
    List<OrderEmployee> getWithMinimumRateAndHours(BigDecimal minRate, BigDecimal minHours);

    // Get operations by qualification or title containing keyword
    List<OrderEmployee> getByQualificationOrTitleContaining(String keyword);

    // Ordering operations
    List<OrderEmployee> getAllOrderByHourlyrateAsc();

    List<OrderEmployee> getAllOrderByHourlyrateDesc();

    List<OrderEmployee> getAllOrderByPlannedhoursAsc();

    List<OrderEmployee> getAllOrderByPlannedhoursDesc();

    List<OrderEmployee> getAllOrderByQualificationkmuiAsc();

    List<OrderEmployee> getAllOrderByTitleAsc();

    // Calculation operations
    BigDecimal calculateTotalCostByOrder(Long orderId);

    BigDecimal calculateTotalCostByEmployee(Long employeeId);

    // Check existence operations
    boolean existsByEmployeeIdAndOrderId(Long employeeId, Long orderId);

    boolean existsByOrderId(Long orderId);

    boolean existsByEmployeeId(Long employeeId);

    boolean existsByQualificationFZId(Long qualificationFZId);

    // Count operations
    Long countByOrder(Long orderId);

    Long countByEmployee(Long employeeId);

    // Get distinct employees by order
    List<com.iws_manager.iws_manager_api.models.Employee> getDistinctEmployeesByOrder(Long orderId);

    // Get hourly rates and planned hours by order (projection)
    List<Object[]> getHourlyRatesAndPlannedHoursByOrder(Long orderId);

    // Validation and business logic
    boolean validateOrderEmployee(OrderEmployee orderEmployee);
}