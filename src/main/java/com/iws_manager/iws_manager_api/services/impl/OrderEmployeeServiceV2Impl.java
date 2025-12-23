package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.dtos.orderemployee.OrderEmployeeRequestDTO;
import com.iws_manager.iws_manager_api.dtos.orderemployee.OrderEmployeeResponseDTO;
import com.iws_manager.iws_manager_api.dtos.shared.ProjectReferenceDTO;
import com.iws_manager.iws_manager_api.mappers.OrderEmployeeMapper;
import com.iws_manager.iws_manager_api.models.Employee;
import com.iws_manager.iws_manager_api.models.Order;
import com.iws_manager.iws_manager_api.models.OrderEmployee;
import com.iws_manager.iws_manager_api.models.QualificationFZ;
import com.iws_manager.iws_manager_api.repositories.OrderEmployeeRepository;
import com.iws_manager.iws_manager_api.services.interfaces.OrderEmployeeServiceV2;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderEmployeeServiceV2Impl implements OrderEmployeeServiceV2 {

    private final OrderEmployeeRepository orderEmployeeRepository;
    private final OrderEmployeeMapper orderEmployeeMapper;

    private static final String orderEmployeeNotFound = "OrderEmployee not found with id: ";
    private static final String employeeIdString = "Employee ID";
    private static final String orderIdString = "Order ID";
    private static final String qualificationFZIdString = "Qualification FZ ID";
    private static final String qualificationkmuiString = "Qualification K MUI";
    private static final String titleString = "Title";

    // ========== BASIC CRUD ==========
    @Override
    public OrderEmployeeResponseDTO create(OrderEmployeeRequestDTO orderEmployeeDTO) {
        validateForCreate(orderEmployeeDTO);
        
        OrderEmployee entity = orderEmployeeMapper.toEntity(orderEmployeeDTO);
        OrderEmployee savedEntity = orderEmployeeRepository.save(entity);
        return orderEmployeeMapper.toResponseDTO(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderEmployeeResponseDTO getById(Long id) {
        OrderEmployee entity = orderEmployeeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(orderEmployeeNotFound + id));
        return orderEmployeeMapper.toResponseDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployeeResponseDTO> getAll() {
        List<OrderEmployee> entities = orderEmployeeRepository.findAllByOrderByIdAsc();
        return orderEmployeeMapper.toResponseDTOList(entities);
    }

    @Override
    public OrderEmployeeResponseDTO update(Long id, OrderEmployeeRequestDTO orderEmployeeDTO) {
        validateForUpdate(orderEmployeeDTO);
        
        OrderEmployee entity = orderEmployeeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(orderEmployeeNotFound + id));
        
        orderEmployeeMapper.updateEntityFromDTO(entity, orderEmployeeDTO);
        OrderEmployee updatedEntity = orderEmployeeRepository.save(entity);
        return orderEmployeeMapper.toResponseDTO(updatedEntity);
    }

    @Override
    public OrderEmployeeResponseDTO partialUpdate(Long id, OrderEmployeeRequestDTO orderEmployeeDTO) {
        OrderEmployee entity = orderEmployeeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(orderEmployeeNotFound + id));
        
        applyPartialUpdate(entity, orderEmployeeDTO);
        OrderEmployee updatedEntity = orderEmployeeRepository.save(entity);
        return orderEmployeeMapper.toResponseDTO(updatedEntity);
    }

    @Override
    public void delete(Long id) {
        if (!orderEmployeeRepository.existsById(id)) {
            throw new EntityNotFoundException(orderEmployeeNotFound + id);
        }
        orderEmployeeRepository.deleteById(id);
    }

    // ========== GET BY EMPLOYEE ==========
    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployeeResponseDTO> getByEmployeeId(Long employeeId) {
        validateId(employeeId, employeeIdString);
        List<OrderEmployee> entities = orderEmployeeRepository.findByEmployeeId(employeeId);
        return orderEmployeeMapper.toResponseDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployeeResponseDTO> getByEmployeeIdOrderByIdAsc(Long employeeId) {
        validateId(employeeId, employeeIdString);
        List<OrderEmployee> entities = orderEmployeeRepository.findByEmployeeIdOrderByIdAsc(employeeId);
        return orderEmployeeMapper.toResponseDTOList(entities);
    }

    // ========== GET BY ORDER ==========
    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployeeResponseDTO> getByOrderId(Long orderId) {
        validateId(orderId, orderIdString);
        List<OrderEmployee> entities = orderEmployeeRepository.findByOrderId(orderId);
        return orderEmployeeMapper.toResponseDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployeeResponseDTO> getByOrderIdOrderByIdAsc(Long orderId) {
        validateId(orderId, orderIdString);
        List<OrderEmployee> entities = orderEmployeeRepository.findByOrderIdOrderByIdAsc(orderId);
        return orderEmployeeMapper.toResponseDTOList(entities);
    }

    // ========== GET BY QUALIFICATION FZ ==========
    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployeeResponseDTO> getByQualificationFZId(Long qualificationFZId) {
        validateId(qualificationFZId, qualificationFZIdString);
        List<OrderEmployee> entities = orderEmployeeRepository.findByQualificationFZId(qualificationFZId);
        return orderEmployeeMapper.toResponseDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployeeResponseDTO> getByQualificationFZIdOrderByIdAsc(Long qualificationFZId) {
        validateId(qualificationFZId, qualificationFZIdString);
        List<OrderEmployee> entities = orderEmployeeRepository.findByQualificationFZIdOrderByIdAsc(qualificationFZId);
        return orderEmployeeMapper.toResponseDTOList(entities);
    }

    // ========== GET BY QUALIFICATION KMUI ==========
    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployeeResponseDTO> getByQualificationkmui(String qualificationkmui) {
        validateString(qualificationkmui, qualificationkmuiString);
        List<OrderEmployee> entities = orderEmployeeRepository.findByQualificationkmui(qualificationkmui);
        return orderEmployeeMapper.toResponseDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployeeResponseDTO> getByQualificationkmuiContainingIgnoreCase(String qualificationkmui) {
        validateString(qualificationkmui, qualificationkmuiString);
        List<OrderEmployee> entities = orderEmployeeRepository.findByQualificationkmuiContainingIgnoreCase(qualificationkmui);
        return orderEmployeeMapper.toResponseDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployeeResponseDTO> getByQualificationkmuiOrderByIdAsc(String qualificationkmui) {
        validateString(qualificationkmui, qualificationkmuiString);
        List<OrderEmployee> entities = orderEmployeeRepository.findByQualificationkmuiOrderByIdAsc(qualificationkmui);
        return orderEmployeeMapper.toResponseDTOList(entities);
    }

    // ========== GET BY TITLE ==========
    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployeeResponseDTO> getByTitle(String title) {
        validateString(title, titleString);
        List<OrderEmployee> entities = orderEmployeeRepository.findByTitle(title);
        return orderEmployeeMapper.toResponseDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployeeResponseDTO> getByTitleContainingIgnoreCase(String title) {
        validateString(title, titleString);
        List<OrderEmployee> entities = orderEmployeeRepository.findByTitleContainingIgnoreCase(title);
        return orderEmployeeMapper.toResponseDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployeeResponseDTO> getByTitleOrderByIdAsc(String title) {
        validateString(title, titleString);
        List<OrderEmployee> entities = orderEmployeeRepository.findByTitleOrderByIdAsc(title);
        return orderEmployeeMapper.toResponseDTOList(entities);
    }

    // ========== GET WITH COMBINED CRITERIA ==========
    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployeeResponseDTO> getByEmployeeIdAndOrderId(Long employeeId, Long orderId) {
        validateId(employeeId, employeeIdString);
        validateId(orderId, orderIdString);
        List<OrderEmployee> entities = orderEmployeeRepository.findByEmployeeIdAndOrderId(employeeId, orderId);
        return orderEmployeeMapper.toResponseDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployeeResponseDTO> getByOrderIdAndQualificationFZId(Long orderId, Long qualificationFZId) {
        validateId(orderId, orderIdString);
        validateId(qualificationFZId, qualificationFZIdString);
        List<OrderEmployee> entities = orderEmployeeRepository.findByOrderIdAndQualificationFZId(orderId, qualificationFZId);
        return orderEmployeeMapper.toResponseDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployeeResponseDTO> getByEmployeeIdAndQualificationFZId(Long employeeId, Long qualificationFZId) {
        validateId(employeeId, employeeIdString);
        validateId(qualificationFZId, qualificationFZIdString);
        List<OrderEmployee> entities = orderEmployeeRepository.findByEmployeeIdAndQualificationFZId(employeeId, qualificationFZId);
        return orderEmployeeMapper.toResponseDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployeeResponseDTO> getByEmployeeOrderAndQualification(Long employeeId, Long orderId, Long qualificationFZId) {
        validateId(employeeId, employeeIdString);
        validateId(orderId, orderIdString);
        validateId(qualificationFZId, qualificationFZIdString);
        List<OrderEmployee> entities = orderEmployeeRepository.findByEmployeeOrderAndQualification(employeeId, orderId, qualificationFZId);
        return orderEmployeeMapper.toResponseDTOList(entities);
    }

    // ========== GET BY HOURLY RATE RANGE ==========
    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployeeResponseDTO> getByHourlyrateGreaterThan(BigDecimal hourlyrate) {
        validateBigDecimal(hourlyrate, "Hourly rate");
        List<OrderEmployee> entities = orderEmployeeRepository.findByHourlyrateGreaterThan(hourlyrate);
        return orderEmployeeMapper.toResponseDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployeeResponseDTO> getByHourlyrateLessThan(BigDecimal hourlyrate) {
        validateBigDecimal(hourlyrate, "Hourly rate");
        List<OrderEmployee> entities = orderEmployeeRepository.findByHourlyrateLessThan(hourlyrate);
        return orderEmployeeMapper.toResponseDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployeeResponseDTO> getByHourlyrateBetween(BigDecimal minHourlyrate, BigDecimal maxHourlyrate) {
        validateBigDecimal(minHourlyrate, "Min hourly rate");
        validateBigDecimal(maxHourlyrate, "Max hourly rate");
        
        if (minHourlyrate.compareTo(maxHourlyrate) > 0) {
            throw new IllegalArgumentException("Min hourly rate cannot be greater than max hourly rate");
        }
        
        List<OrderEmployee> entities = orderEmployeeRepository.findByHourlyrateBetween(minHourlyrate, maxHourlyrate);
        return orderEmployeeMapper.toResponseDTOList(entities);
    }

    // ========== GET BY PLANNED HOURS RANGE ==========
    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployeeResponseDTO> getByPlannedhoursGreaterThan(BigDecimal plannedhours) {
        validateBigDecimal(plannedhours, "Planned hours");
        List<OrderEmployee> entities = orderEmployeeRepository.findByPlannedhoursGreaterThan(plannedhours);
        return orderEmployeeMapper.toResponseDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployeeResponseDTO> getByPlannedhoursLessThan(BigDecimal plannedhours) {
        validateBigDecimal(plannedhours, "Planned hours");
        List<OrderEmployee> entities = orderEmployeeRepository.findByPlannedhoursLessThan(plannedhours);
        return orderEmployeeMapper.toResponseDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployeeResponseDTO> getByPlannedhoursBetween(BigDecimal minPlannedhours, BigDecimal maxPlannedhours) {
        validateBigDecimal(minPlannedhours, "Min planned hours");
        validateBigDecimal(maxPlannedhours, "Max planned hours");
        
        if (minPlannedhours.compareTo(maxPlannedhours) > 0) {
            throw new IllegalArgumentException("Min planned hours cannot be greater than max planned hours");
        }
        
        List<OrderEmployee> entities = orderEmployeeRepository.findByPlannedhoursBetween(minPlannedhours, maxPlannedhours);
        return orderEmployeeMapper.toResponseDTOList(entities);
    }

    // ========== GET WITH MINIMUM RATE AND HOURS ==========
    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployeeResponseDTO> getWithMinimumRateAndHours(BigDecimal minRate, BigDecimal minHours) {
        validateBigDecimal(minRate, "Min rate");
        validateBigDecimal(minHours, "Min hours");
        List<OrderEmployee> entities = orderEmployeeRepository.findWithMinimumRateAndHours(minRate, minHours);
        return orderEmployeeMapper.toResponseDTOList(entities);
    }

    // ========== GET BY QUALIFICATION OR TITLE ==========
    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployeeResponseDTO> getByQualificationOrTitleContaining(String keyword) {
        validateString(keyword, "Keyword");
        List<OrderEmployee> entities = orderEmployeeRepository.findByQualificationOrTitleContaining(keyword);
        return orderEmployeeMapper.toResponseDTOList(entities);
    }

    // ========== ORDERING OPERATIONS ==========
    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployeeResponseDTO> getAllOrderByHourlyrateAsc() {
        List<OrderEmployee> entities = orderEmployeeRepository.findAllByOrderByHourlyrateAsc();
        return orderEmployeeMapper.toResponseDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployeeResponseDTO> getAllOrderByHourlyrateDesc() {
        List<OrderEmployee> entities = orderEmployeeRepository.findAllByOrderByHourlyrateDesc();
        return orderEmployeeMapper.toResponseDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployeeResponseDTO> getAllOrderByPlannedhoursAsc() {
        List<OrderEmployee> entities = orderEmployeeRepository.findAllByOrderByPlannedhoursAsc();
        return orderEmployeeMapper.toResponseDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployeeResponseDTO> getAllOrderByPlannedhoursDesc() {
        List<OrderEmployee> entities = orderEmployeeRepository.findAllByOrderByPlannedhoursDesc();
        return orderEmployeeMapper.toResponseDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployeeResponseDTO> getAllOrderByQualificationkmuiAsc() {
        List<OrderEmployee> entities = orderEmployeeRepository.findAllByOrderByQualificationkmuiAsc();
        return orderEmployeeMapper.toResponseDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEmployeeResponseDTO> getAllOrderByTitleAsc() {
        List<OrderEmployee> entities = orderEmployeeRepository.findAllByOrderByTitleAsc();
        return orderEmployeeMapper.toResponseDTOList(entities);
    }

    // ========== CALCULATION OPERATIONS ==========
    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateTotalCostByOrder(Long orderId) {
        validateId(orderId, orderIdString);
        BigDecimal total = orderEmployeeRepository.calculateTotalCostByOrder(orderId);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateTotalCostByEmployee(Long employeeId) {
        validateId(employeeId, employeeIdString);
        BigDecimal total = orderEmployeeRepository.calculateTotalCostByEmployee(employeeId);
        return total != null ? total : BigDecimal.ZERO;
    }

    // ========== EXISTENCE OPERATIONS ==========
    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmployeeIdAndOrderId(Long employeeId, Long orderId) {
        validateId(employeeId, employeeIdString);
        validateId(orderId, orderIdString);
        return orderEmployeeRepository.existsByEmployeeIdAndOrderId(employeeId, orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByOrderId(Long orderId) {
        validateId(orderId, orderIdString);
        return orderEmployeeRepository.existsByOrderId(orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmployeeId(Long employeeId) {
        validateId(employeeId, employeeIdString);
        return orderEmployeeRepository.existsByEmployeeId(employeeId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByQualificationFZId(Long qualificationFZId) {
        validateId(qualificationFZId, qualificationFZIdString);
        return orderEmployeeRepository.existsByQualificationFZId(qualificationFZId);
    }

    // ========== COUNT OPERATIONS ==========
    @Override
    @Transactional(readOnly = true)
    public Long countByOrder(Long orderId) {
        validateId(orderId, orderIdString);
        return orderEmployeeRepository.countByOrder(orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByEmployee(Long employeeId) {
        validateId(employeeId, employeeIdString);
        return orderEmployeeRepository.countByEmployee(employeeId);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectReferenceDTO getProjectByOrderEmployeeId(Long orderEmployeeId) {
        OrderEmployee orderEmployee = orderEmployeeRepository.findById(orderEmployeeId)
            .orElseThrow(() -> new EntityNotFoundException(
                "OrderEmployee not found with id: " + orderEmployeeId));
        
        if (orderEmployee.getOrder() == null) {
            throw new EntityNotFoundException(
                "OrderEmployee with id " + orderEmployeeId + " has no associated order");
        }
        
        if (orderEmployee.getOrder().getProject() == null) {
            throw new EntityNotFoundException(
                "Order with id " + orderEmployee.getOrder().getId() + " has no associated project");
        }
        
        return orderEmployeeMapper.toProjectReferenceDTO(orderEmployee.getOrder().getProject());
    }

    // ========== MÉTODOS PRIVADOS DE VALIDACIÓN ==========
    private void validateForCreate(OrderEmployeeRequestDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("OrderEmployee data cannot be null");
        }
        
        if (dto.hourlyrate() == null) {
            throw new IllegalArgumentException("Hourly rate is required for creation");
        }
        
        if (dto.plannedhours() == null) {
            throw new IllegalArgumentException("Planned hours is required for creation");
        }
        
        if (dto.order() == null || dto.order().id() == null) {
            throw new IllegalArgumentException("Order is required for creation");
        }
    }
    
    private void validateForUpdate(OrderEmployeeRequestDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("OrderEmployee data cannot be null");
        }
        
        boolean hasData = dto.hourlyrate() != null || 
                         dto.plannedhours() != null ||
                         dto.order() != null ||
                         dto.employee() != null ||
                         dto.qualificationFZ() != null ||
                         dto.title() != null ||
                         dto.qualificationkmui() != null ||
                         dto.orderemployeeno() != null;
        
        if (!hasData) {
            throw new IllegalArgumentException("At least one field must be provided for update");
        }
    }
    
    private void applyPartialUpdate(OrderEmployee entity, OrderEmployeeRequestDTO dto) {
        if (dto.employee() != null) {
            if (dto.employee().id() != null) {
                Employee employee = new Employee();
                employee.setId(dto.employee().id());
                entity.setEmployee(employee);
            } else {
                entity.setEmployee(null);
            }
        }
        
        if (dto.order() != null && dto.order().id() != null) {
            Order order = new Order();
            order.setId(dto.order().id());
            entity.setOrder(order);
        }
        
        if (dto.qualificationFZ() != null) {
            if (dto.qualificationFZ().id() != null) {
                QualificationFZ qualificationFZ = new QualificationFZ();
                qualificationFZ.setId(dto.qualificationFZ().id());
                entity.setQualificationFZ(qualificationFZ);
            } else {
                entity.setQualificationFZ(null);
            }
        }
        
        if (dto.orderemployeeno() != null) {
            entity.setOrderemployeeno(dto.orderemployeeno());
        }
        
        if (dto.hourlyrate() != null) {
            entity.setHourlyrate(dto.hourlyrate());
        }
        
        if (dto.plannedhours() != null) {
            entity.setPlannedhours(dto.plannedhours());
        }
        
        if (dto.qualificationkmui() != null) {
            entity.setQualificationkmui(dto.qualificationkmui());
        }
        
        if (dto.title() != null) {
            entity.setTitle(dto.title());
        }
    }
    
    private void validateId(Long id, String fieldName) {
        if (id == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null");
        }
    }
    
    private void validateString(String value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null");
        }
    }
    
    private void validateBigDecimal(BigDecimal value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null");
        }
    }
}