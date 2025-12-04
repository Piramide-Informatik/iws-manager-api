package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.OrderEmployee;
import com.iws_manager.iws_manager_api.repositories.OrderEmployeeRepository;
import com.iws_manager.iws_manager_api.services.interfaces.OrderEmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link OrderEmployeeService} interface for managing
 * OrderEmployee entities.
 * Provides CRUD operations and business logic for OrderEmployee management.
 * 
 * <p>
 * This service implementation is transactional by default, with read-only
 * operations
 * optimized for database performance.
 * </p>
 */
@Service
@Transactional
public class OrderEmployeeServiceImpl implements OrderEmployeeService {

    private final OrderEmployeeRepository orderEmployeeRepository;

    /**
     * Constructs a new OrderEmployeeService with the required repository
     * dependency.
     * 
     * @param orderEmployeeRepository the repository for OrderEmployee entity
     *                                operations
     */
    @Autowired
    public OrderEmployeeServiceImpl(OrderEmployeeRepository orderEmployeeRepository) {
        this.orderEmployeeRepository = orderEmployeeRepository;
    }

    /**
     * Creates and persists a new OrderEmployee entity.
     * 
     * @param orderEmployee the OrderEmployee entity to be created
     * @return the persisted OrderEmployee entity with generated ID
     * @throws IllegalArgumentException if the orderEmployee parameter is null
     */
    @Override
    public OrderEmployee create(OrderEmployee orderEmployee) {
        if (orderEmployee == null) {
            throw new IllegalArgumentException("OrderEmployee cannot be null");
        }

        // Validate business rules before creation
        validateOrderEmployee(orderEmployee);

        return orderEmployeeRepository.save(orderEmployee);
    }

    /**
     * Retrieves an OrderEmployee by its unique identifier.
     * 
     * @param id the ID of the OrderEmployee to retrieve
     * @return an Optional containing the found OrderEmployee, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrderEmployee> getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return orderEmployeeRepository.findById(id);
    }

    /**
     * Retrieves all OrderEmployee entities from the database.
     * 
     * @return a List of all OrderEmployee entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployee> getAll() {
        return orderEmployeeRepository.findAllByOrderByIdAsc();
    }

    /**
     * Updates an existing OrderEmployee entity.
     * 
     * @param id                   the ID of the OrderEmployee to update
     * @param orderEmployeeDetails the OrderEmployee object containing updated
     *                             fields
     * @return the updated OrderEmployee entity
     * @throws RuntimeException         if no OrderEmployee exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public OrderEmployee update(Long id, OrderEmployee orderEmployeeDetails) {
        if (id == null || orderEmployeeDetails == null) {
            throw new IllegalArgumentException("ID and order employee details cannot be null");
        }

        return orderEmployeeRepository.findById(id)
                .map(existingOrderEmployee -> {
                    // Validate business rules before update
                    validateOrderEmployee(orderEmployeeDetails);

                    // Update fields
                    existingOrderEmployee.setEmployee(orderEmployeeDetails.getEmployee());
                    existingOrderEmployee.setHourlyrate(orderEmployeeDetails.getHourlyrate());
                    existingOrderEmployee.setPlannedhours(orderEmployeeDetails.getPlannedhours());
                    existingOrderEmployee.setOrder(orderEmployeeDetails.getOrder());
                    existingOrderEmployee.setQualificationFZ(orderEmployeeDetails.getQualificationFZ());
                    existingOrderEmployee.setQualificationkmui(orderEmployeeDetails.getQualificationkmui());
                    existingOrderEmployee.setTitle(orderEmployeeDetails.getTitle());

                    return orderEmployeeRepository.save(existingOrderEmployee);
                })
                .orElseThrow(() -> new EntityNotFoundException("OrderEmployee not found with id: " + id));
    }

    /**
     * Deletes an OrderEmployee entity by its ID.
     * 
     * @param id the ID of the OrderEmployee to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (!orderEmployeeRepository.existsById(id)) {
            throw new EntityNotFoundException("OrderEmployee not found with id: " + id);
        }
        orderEmployeeRepository.deleteById(id);
    }

    // Get operations by employee
    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployee> getByEmployeeId(Long employeeId) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        return orderEmployeeRepository.findByEmployeeId(employeeId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployee> getByEmployeeIdOrderByIdAsc(Long employeeId) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        return orderEmployeeRepository.findByEmployeeIdOrderByIdAsc(employeeId);
    }

    // Get operations by order
    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployee> getByOrderId(Long orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }
        return orderEmployeeRepository.findByOrderId(orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployee> getByOrderIdOrderByIdAsc(Long orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }
        return orderEmployeeRepository.findByOrderIdOrderByIdAsc(orderId);
    }

    // Get operations by qualificationFZ
    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployee> getByQualificationFZId(Long qualificationFZId) {
        if (qualificationFZId == null) {
            throw new IllegalArgumentException("Qualification FZ ID cannot be null");
        }
        return orderEmployeeRepository.findByQualificationFZId(qualificationFZId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployee> getByQualificationFZIdOrderByIdAsc(Long qualificationFZId) {
        if (qualificationFZId == null) {
            throw new IllegalArgumentException("Qualification FZ ID cannot be null");
        }
        return orderEmployeeRepository.findByQualificationFZIdOrderByIdAsc(qualificationFZId);
    }

    // Get operations by qualificationkmui
    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployee> getByQualificationkmui(String qualificationkmui) {
        if (qualificationkmui == null) {
            throw new IllegalArgumentException("Qualification K MUI cannot be null");
        }
        return orderEmployeeRepository.findByQualificationkmui(qualificationkmui);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployee> getByQualificationkmuiContainingIgnoreCase(String qualificationkmui) {
        if (qualificationkmui == null) {
            throw new IllegalArgumentException("Qualification K MUI cannot be null");
        }
        return orderEmployeeRepository.findByQualificationkmuiContainingIgnoreCase(qualificationkmui);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployee> getByQualificationkmuiOrderByIdAsc(String qualificationkmui) {
        if (qualificationkmui == null) {
            throw new IllegalArgumentException("Qualification K MUI cannot be null");
        }
        return orderEmployeeRepository.findByQualificationkmuiOrderByIdAsc(qualificationkmui);
    }

    // Get operations by title
    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployee> getByTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Title cannot be null");
        }
        return orderEmployeeRepository.findByTitle(title);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployee> getByTitleContainingIgnoreCase(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Title cannot be null");
        }
        return orderEmployeeRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployee> getByTitleOrderByIdAsc(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Title cannot be null");
        }
        return orderEmployeeRepository.findByTitleOrderByIdAsc(title);
    }

    // Get operations with combined criteria
    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployee> getByEmployeeIdAndOrderId(Long employeeId, Long orderId) {
        if (employeeId == null || orderId == null) {
            throw new IllegalArgumentException("Employee ID and Order ID cannot be null");
        }
        return orderEmployeeRepository.findByEmployeeIdAndOrderId(employeeId, orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployee> getByOrderIdAndQualificationFZId(Long orderId, Long qualificationFZId) {
        if (orderId == null || qualificationFZId == null) {
            throw new IllegalArgumentException("Order ID and Qualification FZ ID cannot be null");
        }
        return orderEmployeeRepository.findByOrderIdAndQualificationFZId(orderId, qualificationFZId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployee> getByEmployeeIdAndQualificationFZId(Long employeeId, Long qualificationFZId) {
        if (employeeId == null || qualificationFZId == null) {
            throw new IllegalArgumentException("Employee ID and Qualification FZ ID cannot be null");
        }
        return orderEmployeeRepository.findByEmployeeIdAndQualificationFZId(employeeId, qualificationFZId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployee> getByEmployeeOrderAndQualification(Long employeeId, Long orderId,
            Long qualificationFZId) {
        if (employeeId == null || orderId == null || qualificationFZId == null) {
            throw new IllegalArgumentException("Employee ID, Order ID and Qualification FZ ID cannot be null");
        }
        return orderEmployeeRepository.findByEmployeeOrderAndQualification(employeeId, orderId, qualificationFZId);
    }

    // Get operations by hourlyrate range
    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployee> getByHourlyrateGreaterThan(BigDecimal hourlyrate) {
        if (hourlyrate == null) {
            throw new IllegalArgumentException("Hourly rate cannot be null");
        }
        return orderEmployeeRepository.findByHourlyrateGreaterThan(hourlyrate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployee> getByHourlyrateLessThan(BigDecimal hourlyrate) {
        if (hourlyrate == null) {
            throw new IllegalArgumentException("Hourly rate cannot be null");
        }
        return orderEmployeeRepository.findByHourlyrateLessThan(hourlyrate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployee> getByHourlyrateBetween(BigDecimal minHourlyrate, BigDecimal maxHourlyrate) {
        if (minHourlyrate == null || maxHourlyrate == null) {
            throw new IllegalArgumentException("Min hourly rate and max hourly rate cannot be null");
        }
        if (minHourlyrate.compareTo(maxHourlyrate) > 0) {
            throw new IllegalArgumentException("Min hourly rate cannot be greater than max hourly rate");
        }
        return orderEmployeeRepository.findByHourlyrateBetween(minHourlyrate, maxHourlyrate);
    }

    // Get operations by plannedhours range
    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployee> getByPlannedhoursGreaterThan(BigDecimal plannedhours) {
        if (plannedhours == null) {
            throw new IllegalArgumentException("Planned hours cannot be null");
        }
        return orderEmployeeRepository.findByPlannedhoursGreaterThan(plannedhours);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployee> getByPlannedhoursLessThan(BigDecimal plannedhours) {
        if (plannedhours == null) {
            throw new IllegalArgumentException("Planned hours cannot be null");
        }
        return orderEmployeeRepository.findByPlannedhoursLessThan(plannedhours);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployee> getByPlannedhoursBetween(BigDecimal minPlannedhours, BigDecimal maxPlannedhours) {
        if (minPlannedhours == null || maxPlannedhours == null) {
            throw new IllegalArgumentException("Min planned hours and max planned hours cannot be null");
        }
        if (minPlannedhours.compareTo(maxPlannedhours) > 0) {
            throw new IllegalArgumentException("Min planned hours cannot be greater than max planned hours");
        }
        return orderEmployeeRepository.findByPlannedhoursBetween(minPlannedhours, maxPlannedhours);
    }

    // Get operations with minimum rate and hours
    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployee> getWithMinimumRateAndHours(BigDecimal minRate, BigDecimal minHours) {
        if (minRate == null || minHours == null) {
            throw new IllegalArgumentException("Min rate and min hours cannot be null");
        }
        return orderEmployeeRepository.findWithMinimumRateAndHours(minRate, minHours);
    }

    // Get operations by qualification or title containing keyword
    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployee> getByQualificationOrTitleContaining(String keyword) {
        if (keyword == null) {
            throw new IllegalArgumentException("Keyword cannot be null");
        }
        return orderEmployeeRepository.findByQualificationOrTitleContaining(keyword);
    }

    // Ordering operations
    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployee> getAllOrderByHourlyrateAsc() {
        return orderEmployeeRepository.findAllByOrderByHourlyrateAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployee> getAllOrderByHourlyrateDesc() {
        return orderEmployeeRepository.findAllByOrderByHourlyrateDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployee> getAllOrderByPlannedhoursAsc() {
        return orderEmployeeRepository.findAllByOrderByPlannedhoursAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployee> getAllOrderByPlannedhoursDesc() {
        return orderEmployeeRepository.findAllByOrderByPlannedhoursDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployee> getAllOrderByQualificationkmuiAsc() {
        return orderEmployeeRepository.findAllByOrderByQualificationkmuiAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployee> getAllOrderByTitleAsc() {
        return orderEmployeeRepository.findAllByOrderByTitleAsc();
    }

    // Calculation operations
    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateTotalCostByOrder(Long orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }
        BigDecimal total = orderEmployeeRepository.calculateTotalCostByOrder(orderId);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateTotalCostByEmployee(Long employeeId) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        BigDecimal total = orderEmployeeRepository.calculateTotalCostByEmployee(employeeId);
        return total != null ? total : BigDecimal.ZERO;
    }

    // Check existence operations
    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmployeeIdAndOrderId(Long employeeId, Long orderId) {
        if (employeeId == null || orderId == null) {
            throw new IllegalArgumentException("Employee ID and Order ID cannot be null");
        }
        return orderEmployeeRepository.existsByEmployeeIdAndOrderId(employeeId, orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByOrderId(Long orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }
        return orderEmployeeRepository.existsByOrderId(orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmployeeId(Long employeeId) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        return orderEmployeeRepository.existsByEmployeeId(employeeId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByQualificationFZId(Long qualificationFZId) {
        if (qualificationFZId == null) {
            throw new IllegalArgumentException("Qualification FZ ID cannot be null");
        }
        return orderEmployeeRepository.existsByQualificationFZId(qualificationFZId);
    }

    // Count operations
    @Override
    @Transactional(readOnly = true)
    public Long countByOrder(Long orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }
        return orderEmployeeRepository.countByOrder(orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByEmployee(Long employeeId) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        return orderEmployeeRepository.countByEmployee(employeeId);
    }

    // Get distinct employees by order
    @Override
    @Transactional(readOnly = true)
    public List<com.iws_manager.iws_manager_api.models.Employee> getDistinctEmployeesByOrder(Long orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }
        return orderEmployeeRepository.findDistinctEmployeesByOrder(orderId);
    }

    // Get hourly rates and planned hours by order (projection)
    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getHourlyRatesAndPlannedHoursByOrder(Long orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }
        return orderEmployeeRepository.findHourlyRatesAndPlannedHoursByOrder(orderId);
    }

    // Validation and business logic
    @Override
    public boolean validateOrderEmployee(OrderEmployee orderEmployee) {
        if (orderEmployee == null) {
            throw new IllegalArgumentException("OrderEmployee cannot be null");
        }

        // Basic validation
        // if (orderEmployee.getEmployee() == null) {
        // throw new IllegalArgumentException("Employee is required");
        // }

        // if (orderEmployee.getOrder() == null) {
        // throw new IllegalArgumentException("Order is required");
        // }

        return true;
    }
}