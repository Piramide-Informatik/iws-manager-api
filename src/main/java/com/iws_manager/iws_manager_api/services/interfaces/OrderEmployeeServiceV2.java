package com.iws_manager.iws_manager_api.services.interfaces;

import com.iws_manager.iws_manager_api.dtos.orderemployee.OrderEmployeeRequestDTO;
import com.iws_manager.iws_manager_api.dtos.orderemployee.OrderEmployeeResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface OrderEmployeeServiceV2 {

    // ========== BASIC CRUD OPERATIONS ==========
    OrderEmployeeResponseDTO create(OrderEmployeeRequestDTO orderEmployeeDTO);
    OrderEmployeeResponseDTO getById(Long id);
    List<OrderEmployeeResponseDTO> getAll();
    OrderEmployeeResponseDTO update(Long id, OrderEmployeeRequestDTO orderEmployeeDTO);
    OrderEmployeeResponseDTO partialUpdate(Long id, OrderEmployeeRequestDTO orderEmployeeDTO);
    void delete(Long id);

    // ========== GET BY EMPLOYEE ==========
    List<OrderEmployeeResponseDTO> getByEmployeeId(Long employeeId);
    List<OrderEmployeeResponseDTO> getByEmployeeIdOrderByIdAsc(Long employeeId);

    // ========== GET BY ORDER ==========
    List<OrderEmployeeResponseDTO> getByOrderId(Long orderId);
    List<OrderEmployeeResponseDTO> getByOrderIdOrderByIdAsc(Long orderId);

    // ========== GET BY QUALIFICATION FZ ==========
    List<OrderEmployeeResponseDTO> getByQualificationFZId(Long qualificationFZId);
    List<OrderEmployeeResponseDTO> getByQualificationFZIdOrderByIdAsc(Long qualificationFZId);

    // ========== GET BY QUALIFICATION KMUI ==========
    List<OrderEmployeeResponseDTO> getByQualificationkmui(String qualificationkmui);
    List<OrderEmployeeResponseDTO> getByQualificationkmuiContainingIgnoreCase(String qualificationkmui);
    List<OrderEmployeeResponseDTO> getByQualificationkmuiOrderByIdAsc(String qualificationkmui);

    // ========== GET BY TITLE ==========
    List<OrderEmployeeResponseDTO> getByTitle(String title);
    List<OrderEmployeeResponseDTO> getByTitleContainingIgnoreCase(String title);
    List<OrderEmployeeResponseDTO> getByTitleOrderByIdAsc(String title);

    // ========== GET WITH COMBINED CRITERIA ==========
    List<OrderEmployeeResponseDTO> getByEmployeeIdAndOrderId(Long employeeId, Long orderId);
    List<OrderEmployeeResponseDTO> getByOrderIdAndQualificationFZId(Long orderId, Long qualificationFZId);
    List<OrderEmployeeResponseDTO> getByEmployeeIdAndQualificationFZId(Long employeeId, Long qualificationFZId);
    List<OrderEmployeeResponseDTO> getByEmployeeOrderAndQualification(Long employeeId, Long orderId, Long qualificationFZId);

    // ========== GET BY HOURLY RATE RANGE ==========
    List<OrderEmployeeResponseDTO> getByHourlyrateGreaterThan(BigDecimal hourlyrate);
    List<OrderEmployeeResponseDTO> getByHourlyrateLessThan(BigDecimal hourlyrate);
    List<OrderEmployeeResponseDTO> getByHourlyrateBetween(BigDecimal minHourlyrate, BigDecimal maxHourlyrate);

    // ========== GET BY PLANNED HOURS RANGE ==========
    List<OrderEmployeeResponseDTO> getByPlannedhoursGreaterThan(BigDecimal plannedhours);
    List<OrderEmployeeResponseDTO> getByPlannedhoursLessThan(BigDecimal plannedhours);
    List<OrderEmployeeResponseDTO> getByPlannedhoursBetween(BigDecimal minPlannedhours, BigDecimal maxPlannedhours);

    // ========== GET WITH MINIMUM RATE AND HOURS ==========
    List<OrderEmployeeResponseDTO> getWithMinimumRateAndHours(BigDecimal minRate, BigDecimal minHours);

    // ========== GET BY QUALIFICATION OR TITLE CONTAINING ==========
    List<OrderEmployeeResponseDTO> getByQualificationOrTitleContaining(String keyword);

    // ========== ORDERING OPERATIONS ==========
    List<OrderEmployeeResponseDTO> getAllOrderByHourlyrateAsc();
    List<OrderEmployeeResponseDTO> getAllOrderByHourlyrateDesc();
    List<OrderEmployeeResponseDTO> getAllOrderByPlannedhoursAsc();
    List<OrderEmployeeResponseDTO> getAllOrderByPlannedhoursDesc();
    List<OrderEmployeeResponseDTO> getAllOrderByQualificationkmuiAsc();
    List<OrderEmployeeResponseDTO> getAllOrderByTitleAsc();

    // ========== CALCULATION OPERATIONS ==========
    BigDecimal calculateTotalCostByOrder(Long orderId);
    BigDecimal calculateTotalCostByEmployee(Long employeeId);

    // ========== EXISTENCE OPERATIONS ==========
    boolean existsByEmployeeIdAndOrderId(Long employeeId, Long orderId);
    boolean existsByOrderId(Long orderId);
    boolean existsByEmployeeId(Long employeeId);
    boolean existsByQualificationFZId(Long qualificationFZId);

    // ========== COUNT OPERATIONS ==========
    Long countByOrder(Long orderId);
    Long countByEmployee(Long employeeId);
}