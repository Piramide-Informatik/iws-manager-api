package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.OrderEmployee;
import com.iws_manager.iws_manager_api.repositories.OrderEmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class OrderEmployeeServiceImplTest {

    private static final String QUALIFICATION_K_MUI = "Senior Developer";
    private static final String TITLE = "FZ-Kurzbezeichnung FuE-Tätigkeit";
    private static final BigDecimal HOURLY_RATE = new BigDecimal("45.50");
    private static final BigDecimal PLANNED_HOURS = new BigDecimal("160.00");
    private static final Long EMPLOYEE_ID = 1L;
    private static final Long ORDER_ID = 2L;
    private static final Long QUALIFICATION_FZ_ID = 3L;
    private static final String KEYWORD = "Developer";

    @Mock
    private OrderEmployeeRepository orderEmployeeRepository;

    @InjectMocks
    private OrderEmployeeServiceImpl orderEmployeeService;

    private OrderEmployee sampleOrderEmployee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleOrderEmployee = new OrderEmployee();
        sampleOrderEmployee.setId(1L);
        sampleOrderEmployee.setQualificationkmui(QUALIFICATION_K_MUI);
        sampleOrderEmployee.setTitle(TITLE);
        sampleOrderEmployee.setHourlyrate(HOURLY_RATE);
        sampleOrderEmployee.setPlannedhours(PLANNED_HOURS);
    }

    // Basic CRUD operations tests

    @Test
    void testCreate() {
        when(orderEmployeeRepository.save(any(OrderEmployee.class))).thenReturn(sampleOrderEmployee);
        OrderEmployee created = orderEmployeeService.create(sampleOrderEmployee);
        assertNotNull(created);
        assertEquals(QUALIFICATION_K_MUI, created.getQualificationkmui());
        assertEquals(TITLE, created.getTitle());
        verify(orderEmployeeRepository, times(1)).save(sampleOrderEmployee);
    }

    @Test
    void testCreateThrowsIllegalArgumentExceptionWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> orderEmployeeService.create(null));
        verify(orderEmployeeRepository, never()).save(any(OrderEmployee.class));
    }

    @Test
    void testGetById() {
        when(orderEmployeeRepository.findById(1L)).thenReturn(Optional.of(sampleOrderEmployee));
        Optional<OrderEmployee> result = orderEmployeeService.getById(1L);
        assertTrue(result.isPresent());
        assertEquals(QUALIFICATION_K_MUI, result.get().getQualificationkmui());
        verify(orderEmployeeRepository, times(1)).findById(1L);
    }

    @Test
    void testGetByIdThrowsIllegalArgumentExceptionWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> orderEmployeeService.getById(null));
        verify(orderEmployeeRepository, never()).findById(anyLong());
    }

    @Test
    void testGetAll() {
        List<OrderEmployee> orderEmployees = List.of(sampleOrderEmployee);
        when(orderEmployeeRepository.findAllByOrderByIdAsc()).thenReturn(orderEmployees);

        List<OrderEmployee> result = orderEmployeeService.getAll();

        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findAllByOrderByIdAsc();
    }

    @Test
    void testUpdate() {
        OrderEmployee updated = new OrderEmployee();
        updated.setQualificationkmui("Updated Qualification");
        updated.setTitle("Updated Title");
        updated.setHourlyrate(new BigDecimal("50.00"));
        updated.setPlannedhours(new BigDecimal("180.00"));

        when(orderEmployeeRepository.findById(1L)).thenReturn(Optional.of(sampleOrderEmployee));
        when(orderEmployeeRepository.save(any(OrderEmployee.class))).thenReturn(updated);

        OrderEmployee result = orderEmployeeService.update(1L, updated);
        assertNotNull(result);
        assertEquals("Updated Qualification", result.getQualificationkmui());
        assertEquals("Updated Title", result.getTitle());
        verify(orderEmployeeRepository, times(1)).findById(1L);
        verify(orderEmployeeRepository, times(1)).save(any(OrderEmployee.class));
    }

    @Test
    void testUpdateThrowsIllegalArgumentExceptionWhenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> orderEmployeeService.update(null, sampleOrderEmployee));
        verify(orderEmployeeRepository, never()).findById(anyLong());
        verify(orderEmployeeRepository, never()).save(any(OrderEmployee.class));
    }

    @Test
    void testUpdateThrowsIllegalArgumentExceptionWhenOrderEmployeeNull() {
        assertThrows(IllegalArgumentException.class, () -> orderEmployeeService.update(1L, null));
        verify(orderEmployeeRepository, never()).findById(anyLong());
        verify(orderEmployeeRepository, never()).save(any(OrderEmployee.class));
    }

    @Test
    void testUpdateThrowsEntityNotFoundExceptionWhenNotFound() {
        when(orderEmployeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderEmployeeService.update(1L, sampleOrderEmployee));
        verify(orderEmployeeRepository, times(1)).findById(1L);
        verify(orderEmployeeRepository, never()).save(any(OrderEmployee.class));
    }

    @Test
    void testDelete() {
        when(orderEmployeeRepository.existsById(1L)).thenReturn(true);
        orderEmployeeService.delete(1L);
        verify(orderEmployeeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteThrowsIllegalArgumentExceptionWhenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> orderEmployeeService.delete(null));
        verify(orderEmployeeRepository, never()).existsById(anyLong());
        verify(orderEmployeeRepository, never()).deleteById(anyLong());
    }

    @Test
    void testDeleteThrowsEntityNotFoundExceptionWhenNotFound() {
        when(orderEmployeeRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> orderEmployeeService.delete(1L));
        verify(orderEmployeeRepository, times(1)).existsById(1L);
        verify(orderEmployeeRepository, never()).deleteById(anyLong());
    }

    @Test
    void testGetByEmployeeId() {
        when(orderEmployeeRepository.findByEmployeeId(EMPLOYEE_ID)).thenReturn(List.of(sampleOrderEmployee));
        List<OrderEmployee> result = orderEmployeeService.getByEmployeeId(EMPLOYEE_ID);
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByEmployeeId(EMPLOYEE_ID);
    }

    @Test
    void testGetByEmployeeIdThrowsIllegalArgumentExceptionWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> orderEmployeeService.getByEmployeeId(null));
        verify(orderEmployeeRepository, never()).findByEmployeeId(anyLong());
    }

    @Test
    void testGetByEmployeeIdOrderByIdAsc() {
        when(orderEmployeeRepository.findByEmployeeIdOrderByIdAsc(EMPLOYEE_ID))
                .thenReturn(List.of(sampleOrderEmployee));
        List<OrderEmployee> result = orderEmployeeService.getByEmployeeIdOrderByIdAsc(EMPLOYEE_ID);
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByEmployeeIdOrderByIdAsc(EMPLOYEE_ID);
    }

    @Test
    void testGetByOrderId() {
        when(orderEmployeeRepository.findByOrderId(ORDER_ID)).thenReturn(List.of(sampleOrderEmployee));
        List<OrderEmployee> result = orderEmployeeService.getByOrderId(ORDER_ID);
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByOrderId(ORDER_ID);
    }

    @Test
    void testGetByOrderIdOrderByIdAsc() {
        when(orderEmployeeRepository.findByOrderIdOrderByIdAsc(ORDER_ID)).thenReturn(List.of(sampleOrderEmployee));
        List<OrderEmployee> result = orderEmployeeService.getByOrderIdOrderByIdAsc(ORDER_ID);
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByOrderIdOrderByIdAsc(ORDER_ID);
    }

    @Test
    void testGetByQualificationFZId() {
        when(orderEmployeeRepository.findByQualificationFZId(QUALIFICATION_FZ_ID))
                .thenReturn(List.of(sampleOrderEmployee));
        List<OrderEmployee> result = orderEmployeeService.getByQualificationFZId(QUALIFICATION_FZ_ID);
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByQualificationFZId(QUALIFICATION_FZ_ID);
    }

    @Test
    void testGetByQualificationFZIdOrderByIdAsc() {
        when(orderEmployeeRepository.findByQualificationFZIdOrderByIdAsc(QUALIFICATION_FZ_ID))
                .thenReturn(List.of(sampleOrderEmployee));
        List<OrderEmployee> result = orderEmployeeService.getByQualificationFZIdOrderByIdAsc(QUALIFICATION_FZ_ID);
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByQualificationFZIdOrderByIdAsc(QUALIFICATION_FZ_ID);
    }

    @Test
    void testGetByQualificationkmui() {
        when(orderEmployeeRepository.findByQualificationkmui(QUALIFICATION_K_MUI))
                .thenReturn(List.of(sampleOrderEmployee));
        List<OrderEmployee> result = orderEmployeeService.getByQualificationkmui(QUALIFICATION_K_MUI);
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByQualificationkmui(QUALIFICATION_K_MUI);
    }

    @Test
    void testGetByQualificationkmuiContainingIgnoreCase() {
        when(orderEmployeeRepository.findByQualificationkmuiContainingIgnoreCase(KEYWORD))
                .thenReturn(List.of(sampleOrderEmployee));
        List<OrderEmployee> result = orderEmployeeService.getByQualificationkmuiContainingIgnoreCase(KEYWORD);
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByQualificationkmuiContainingIgnoreCase(KEYWORD);
    }

    @Test
    void testGetByQualificationkmuiOrderByIdAsc() {
        when(orderEmployeeRepository.findByQualificationkmuiOrderByIdAsc(QUALIFICATION_K_MUI))
                .thenReturn(List.of(sampleOrderEmployee));
        List<OrderEmployee> result = orderEmployeeService.getByQualificationkmuiOrderByIdAsc(QUALIFICATION_K_MUI);
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByQualificationkmuiOrderByIdAsc(QUALIFICATION_K_MUI);
    }

    @Test
    void testGetByTitle() {
        when(orderEmployeeRepository.findByTitle(TITLE)).thenReturn(List.of(sampleOrderEmployee));
        List<OrderEmployee> result = orderEmployeeService.getByTitle(TITLE);
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByTitle(TITLE);
    }

    @Test
    void testGetByTitleContainingIgnoreCase() {
        when(orderEmployeeRepository.findByTitleContainingIgnoreCase(KEYWORD)).thenReturn(List.of(sampleOrderEmployee));
        List<OrderEmployee> result = orderEmployeeService.getByTitleContainingIgnoreCase(KEYWORD);
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByTitleContainingIgnoreCase(KEYWORD);
    }

    @Test
    void testGetByTitleOrderByIdAsc() {
        when(orderEmployeeRepository.findByTitleOrderByIdAsc(TITLE)).thenReturn(List.of(sampleOrderEmployee));
        List<OrderEmployee> result = orderEmployeeService.getByTitleOrderByIdAsc(TITLE);
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByTitleOrderByIdAsc(TITLE);
    }

    @Test
    void testGetByEmployeeIdAndOrderId() {
        when(orderEmployeeRepository.findByEmployeeIdAndOrderId(EMPLOYEE_ID, ORDER_ID))
                .thenReturn(List.of(sampleOrderEmployee));
        List<OrderEmployee> result = orderEmployeeService.getByEmployeeIdAndOrderId(EMPLOYEE_ID, ORDER_ID);
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByEmployeeIdAndOrderId(EMPLOYEE_ID, ORDER_ID);
    }

    @Test
    void testGetByOrderIdAndQualificationFZId() {
        when(orderEmployeeRepository.findByOrderIdAndQualificationFZId(ORDER_ID, QUALIFICATION_FZ_ID))
                .thenReturn(List.of(sampleOrderEmployee));
        List<OrderEmployee> result = orderEmployeeService.getByOrderIdAndQualificationFZId(ORDER_ID,
                QUALIFICATION_FZ_ID);
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByOrderIdAndQualificationFZId(ORDER_ID, QUALIFICATION_FZ_ID);
    }

    @Test
    void testGetByEmployeeIdAndQualificationFZId() {
        when(orderEmployeeRepository.findByEmployeeIdAndQualificationFZId(EMPLOYEE_ID, QUALIFICATION_FZ_ID))
                .thenReturn(List.of(sampleOrderEmployee));
        List<OrderEmployee> result = orderEmployeeService.getByEmployeeIdAndQualificationFZId(EMPLOYEE_ID,
                QUALIFICATION_FZ_ID);
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByEmployeeIdAndQualificationFZId(EMPLOYEE_ID,
                QUALIFICATION_FZ_ID);
    }

    @Test
    void testGetByEmployeeOrderAndQualification() {
        when(orderEmployeeRepository.findByEmployeeOrderAndQualification(EMPLOYEE_ID, ORDER_ID, QUALIFICATION_FZ_ID))
                .thenReturn(List.of(sampleOrderEmployee));
        List<OrderEmployee> result = orderEmployeeService.getByEmployeeOrderAndQualification(EMPLOYEE_ID, ORDER_ID,
                QUALIFICATION_FZ_ID);
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByEmployeeOrderAndQualification(EMPLOYEE_ID, ORDER_ID,
                QUALIFICATION_FZ_ID);
    }

    @Test
    void testGetByHourlyrateGreaterThan() {
        when(orderEmployeeRepository.findByHourlyrateGreaterThan(HOURLY_RATE)).thenReturn(List.of(sampleOrderEmployee));
        List<OrderEmployee> result = orderEmployeeService.getByHourlyrateGreaterThan(HOURLY_RATE);
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByHourlyrateGreaterThan(HOURLY_RATE);
    }

    @Test
    void testGetByHourlyrateLessThan() {
        BigDecimal higherRate = new BigDecimal("100.00");
        when(orderEmployeeRepository.findByHourlyrateLessThan(higherRate)).thenReturn(List.of(sampleOrderEmployee));
        List<OrderEmployee> result = orderEmployeeService.getByHourlyrateLessThan(higherRate);
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByHourlyrateLessThan(higherRate);
    }

    @Test
    void testGetByHourlyrateBetween() {
        BigDecimal minRate = new BigDecimal("40.00");
        BigDecimal maxRate = new BigDecimal("50.00");
        when(orderEmployeeRepository.findByHourlyrateBetween(minRate, maxRate))
                .thenReturn(List.of(sampleOrderEmployee));
        List<OrderEmployee> result = orderEmployeeService.getByHourlyrateBetween(minRate, maxRate);
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByHourlyrateBetween(minRate, maxRate);
    }

    @Test
    void testGetByHourlyrateBetweenThrowsExceptionWhenMinGreaterThanMax() {
        BigDecimal minRate = new BigDecimal("50.00");
        BigDecimal maxRate = new BigDecimal("40.00");

        assertThrows(IllegalArgumentException.class,
                () -> orderEmployeeService.getByHourlyrateBetween(minRate, maxRate));
        verify(orderEmployeeRepository, never()).findByHourlyrateBetween(any(), any());
    }

    @Test
    void testGetByPlannedhoursGreaterThan() {
        when(orderEmployeeRepository.findByPlannedhoursGreaterThan(PLANNED_HOURS))
                .thenReturn(List.of(sampleOrderEmployee));
        List<OrderEmployee> result = orderEmployeeService.getByPlannedhoursGreaterThan(PLANNED_HOURS);
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByPlannedhoursGreaterThan(PLANNED_HOURS);
    }

    @Test
    void testGetByPlannedhoursLessThan() {
        BigDecimal higherHours = new BigDecimal("200.00");
        when(orderEmployeeRepository.findByPlannedhoursLessThan(higherHours)).thenReturn(List.of(sampleOrderEmployee));
        List<OrderEmployee> result = orderEmployeeService.getByPlannedhoursLessThan(higherHours);
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByPlannedhoursLessThan(higherHours);
    }

    @Test
    void testGetByPlannedhoursBetween() {
        BigDecimal minHours = new BigDecimal("150.00");
        BigDecimal maxHours = new BigDecimal("170.00");
        when(orderEmployeeRepository.findByPlannedhoursBetween(minHours, maxHours))
                .thenReturn(List.of(sampleOrderEmployee));
        List<OrderEmployee> result = orderEmployeeService.getByPlannedhoursBetween(minHours, maxHours);
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByPlannedhoursBetween(minHours, maxHours);
    }

    @Test
    void testGetWithMinimumRateAndHours() {
        BigDecimal minRate = new BigDecimal("40.00");
        BigDecimal minHours = new BigDecimal("150.00");
        when(orderEmployeeRepository.findWithMinimumRateAndHours(minRate, minHours))
                .thenReturn(List.of(sampleOrderEmployee));
        List<OrderEmployee> result = orderEmployeeService.getWithMinimumRateAndHours(minRate, minHours);
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findWithMinimumRateAndHours(minRate, minHours);
    }

    @Test
    void testGetByQualificationOrTitleContaining() {
        when(orderEmployeeRepository.findByQualificationOrTitleContaining(KEYWORD))
                .thenReturn(List.of(sampleOrderEmployee));
        List<OrderEmployee> result = orderEmployeeService.getByQualificationOrTitleContaining(KEYWORD);
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByQualificationOrTitleContaining(KEYWORD);
    }

    @Test
    void testGetAllOrderByHourlyrateAsc() {
        when(orderEmployeeRepository.findAllByOrderByHourlyrateAsc()).thenReturn(List.of(sampleOrderEmployee));
        List<OrderEmployee> result = orderEmployeeService.getAllOrderByHourlyrateAsc();
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findAllByOrderByHourlyrateAsc();
    }

    @Test
    void testGetAllOrderByHourlyrateDesc() {
        when(orderEmployeeRepository.findAllByOrderByHourlyrateDesc()).thenReturn(List.of(sampleOrderEmployee));
        List<OrderEmployee> result = orderEmployeeService.getAllOrderByHourlyrateDesc();
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findAllByOrderByHourlyrateDesc();
    }

    @Test
    void testGetAllOrderByPlannedhoursAsc() {
        when(orderEmployeeRepository.findAllByOrderByPlannedhoursAsc()).thenReturn(List.of(sampleOrderEmployee));
        List<OrderEmployee> result = orderEmployeeService.getAllOrderByPlannedhoursAsc();
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findAllByOrderByPlannedhoursAsc();
    }

    @Test
    void testGetAllOrderByPlannedhoursDesc() {
        when(orderEmployeeRepository.findAllByOrderByPlannedhoursDesc()).thenReturn(List.of(sampleOrderEmployee));
        List<OrderEmployee> result = orderEmployeeService.getAllOrderByPlannedhoursDesc();
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findAllByOrderByPlannedhoursDesc();
    }

    @Test
    void testGetAllOrderByQualificationkmuiAsc() {
        when(orderEmployeeRepository.findAllByOrderByQualificationkmuiAsc()).thenReturn(List.of(sampleOrderEmployee));
        List<OrderEmployee> result = orderEmployeeService.getAllOrderByQualificationkmuiAsc();
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findAllByOrderByQualificationkmuiAsc();
    }

    @Test
    void testGetAllOrderByTitleAsc() {
        when(orderEmployeeRepository.findAllByOrderByTitleAsc()).thenReturn(List.of(sampleOrderEmployee));
        List<OrderEmployee> result = orderEmployeeService.getAllOrderByTitleAsc();
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findAllByOrderByTitleAsc();
    }

    @Test
    void testCalculateTotalCostByOrder() {
        BigDecimal totalCost = new BigDecimal("7280.00"); // 45.50 * 160.00
        when(orderEmployeeRepository.calculateTotalCostByOrder(ORDER_ID)).thenReturn(totalCost);
        BigDecimal result = orderEmployeeService.calculateTotalCostByOrder(ORDER_ID);
        assertEquals(totalCost, result);
        verify(orderEmployeeRepository, times(1)).calculateTotalCostByOrder(ORDER_ID);
    }

    @Test
    void testCalculateTotalCostByOrderReturnsZeroWhenNull() {
        when(orderEmployeeRepository.calculateTotalCostByOrder(ORDER_ID)).thenReturn(null);
        BigDecimal result = orderEmployeeService.calculateTotalCostByOrder(ORDER_ID);
        assertEquals(BigDecimal.ZERO, result);
        verify(orderEmployeeRepository, times(1)).calculateTotalCostByOrder(ORDER_ID);
    }

    @Test
    void testCalculateTotalCostByEmployee() {
        BigDecimal totalCost = new BigDecimal("7280.00");
        when(orderEmployeeRepository.calculateTotalCostByEmployee(EMPLOYEE_ID)).thenReturn(totalCost);
        BigDecimal result = orderEmployeeService.calculateTotalCostByEmployee(EMPLOYEE_ID);
        assertEquals(totalCost, result);
        verify(orderEmployeeRepository, times(1)).calculateTotalCostByEmployee(EMPLOYEE_ID);
    }

    @Test
    void testExistsByEmployeeIdAndOrderId() {
        when(orderEmployeeRepository.existsByEmployeeIdAndOrderId(EMPLOYEE_ID, ORDER_ID)).thenReturn(true);
        boolean result = orderEmployeeService.existsByEmployeeIdAndOrderId(EMPLOYEE_ID, ORDER_ID);
        assertTrue(result);
        verify(orderEmployeeRepository, times(1)).existsByEmployeeIdAndOrderId(EMPLOYEE_ID, ORDER_ID);
    }

    @Test
    void testExistsByOrderId() {
        when(orderEmployeeRepository.existsByOrderId(ORDER_ID)).thenReturn(true);
        boolean result = orderEmployeeService.existsByOrderId(ORDER_ID);
        assertTrue(result);
        verify(orderEmployeeRepository, times(1)).existsByOrderId(ORDER_ID);
    }

    @Test
    void testExistsByEmployeeId() {
        when(orderEmployeeRepository.existsByEmployeeId(EMPLOYEE_ID)).thenReturn(true);
        boolean result = orderEmployeeService.existsByEmployeeId(EMPLOYEE_ID);
        assertTrue(result);
        verify(orderEmployeeRepository, times(1)).existsByEmployeeId(EMPLOYEE_ID);
    }

    @Test
    void testExistsByQualificationFZId() {
        when(orderEmployeeRepository.existsByQualificationFZId(QUALIFICATION_FZ_ID)).thenReturn(true);
        boolean result = orderEmployeeService.existsByQualificationFZId(QUALIFICATION_FZ_ID);
        assertTrue(result);
        verify(orderEmployeeRepository, times(1)).existsByQualificationFZId(QUALIFICATION_FZ_ID);
    }

    @Test
    void testCountByOrder() {
        Long count = 5L;
        when(orderEmployeeRepository.countByOrder(ORDER_ID)).thenReturn(count);
        Long result = orderEmployeeService.countByOrder(ORDER_ID);
        assertEquals(count, result);
        verify(orderEmployeeRepository, times(1)).countByOrder(ORDER_ID);
    }

    @Test
    void testCountByEmployee() {
        Long count = 3L;
        when(orderEmployeeRepository.countByEmployee(EMPLOYEE_ID)).thenReturn(count);
        Long result = orderEmployeeService.countByEmployee(EMPLOYEE_ID);
        assertEquals(count, result);
        verify(orderEmployeeRepository, times(1)).countByEmployee(EMPLOYEE_ID);
    }

    @Test
    void testValidateOrderEmployee() {
        boolean result = orderEmployeeService.validateOrderEmployee(sampleOrderEmployee);
        assertTrue(result);
    }

    @Test
    void testValidateOrderEmployeeThrowsExceptionWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> orderEmployeeService.validateOrderEmployee(null));
    }

    @Test
    void testGetByIdReturnsEmptyOptionalWhenNotFound() {
        when(orderEmployeeRepository.findById(999L)).thenReturn(Optional.empty());
        Optional<OrderEmployee> result = orderEmployeeService.getById(999L);
        assertFalse(result.isPresent());
        verify(orderEmployeeRepository, times(1)).findById(999L);
    }

    @Test
    void testGetAllReturnsEmptyList() {
        when(orderEmployeeRepository.findAllByOrderByIdAsc()).thenReturn(Collections.emptyList());
        List<OrderEmployee> result = orderEmployeeService.getAll();
        assertTrue(result.isEmpty());
        verify(orderEmployeeRepository, times(1)).findAllByOrderByIdAsc();
    }

    @Test
    void testGetWithNullValues() {
        OrderEmployee nullOrderEmployee = new OrderEmployee();
        boolean result = orderEmployeeService.validateOrderEmployee(nullOrderEmployee);
        assertTrue(result);
    }
}