package com.iws_manager.iws_manager_api.mappers;

import com.iws_manager.iws_manager_api.dtos.orderemployee.OrderEmployeeRequestDTO;
import com.iws_manager.iws_manager_api.dtos.orderemployee.OrderEmployeeResponseDTO;
import com.iws_manager.iws_manager_api.dtos.shared.BasicReferenceDTO;
import com.iws_manager.iws_manager_api.dtos.shared.EmployeeBasicDTO;
import com.iws_manager.iws_manager_api.dtos.shared.OrderReferenceDTO;
import com.iws_manager.iws_manager_api.dtos.shared.ProjectReferenceDTO;
import com.iws_manager.iws_manager_api.dtos.shared.QualificationFZReferenceDTO;
import com.iws_manager.iws_manager_api.models.Employee;
import com.iws_manager.iws_manager_api.models.Order;
import com.iws_manager.iws_manager_api.models.OrderEmployee;
import com.iws_manager.iws_manager_api.models.Project;
import com.iws_manager.iws_manager_api.models.QualificationFZ;
import com.iws_manager.iws_manager_api.repositories.EmployeeRepository;
import com.iws_manager.iws_manager_api.repositories.OrderRepository;
import com.iws_manager.iws_manager_api.repositories.QualificationFZRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderEmployeeMapper {
    
    private final EmployeeRepository employeeRepository;
    private final OrderRepository orderRepository;
    private final QualificationFZRepository qualificationFZRepository;
    
    // ========== Entity → ResponseDTO ==========
    
    public OrderEmployeeResponseDTO toResponseDTO(OrderEmployee entity) {
        if (entity == null) return null;
        
        return new OrderEmployeeResponseDTO(
            entity.getId(),
            entity.getOrderemployeeno(),
            entity.getHourlyrate(),
            entity.getPlannedhours(),
            entity.getQualificationkmui(),
            entity.getTitle(),
            entity.getVersion(),
            toEmployeeBasicDTO(entity.getEmployee()),
            toOrderReferenceDTO(entity.getOrder()),
            toQualificationFZReferenceDTO(entity.getQualificationFZ())
        );
    }
    
    public List<OrderEmployeeResponseDTO> toResponseDTOList(List<OrderEmployee> entities) {
        if (entities == null) return List.of();
        
        return entities.stream()
            .map(this::toResponseDTO)
            .toList();
    }
    
    // ========== RequestDTO → Entity (para CREATE) ==========
    
    public OrderEmployee toEntity(OrderEmployeeRequestDTO dto) {
        if (dto == null) return null;
        
        OrderEmployee entity = new OrderEmployee();
        updateEntityFromDTO(entity, dto);
        return entity;
    }
    
    // ========== Actualizar Entity desde DTO (para CREATE y UPDATE) ==========
    
    public void updateEntityFromDTO(OrderEmployee entity, OrderEmployeeRequestDTO dto) {
        if (dto == null) return;
        
        // Para UPDATE, validamos que haya al menos un campo
        validateDtoHasData(dto);
        
        // Procesar relaciones
        updateEmployee(entity, dto.employee(), true);
        updateOrder(entity, dto.order(), true);
        updateQualificationFZ(entity, dto.qualificationFZ(), true);
        
        // Procesar campos simples (siempre que no sean null)
        updateSimpleFields(entity, dto);
    }
    
    // ========== Método para PATCH (actualización parcial) ==========
    
    public void applyPartialUpdate(OrderEmployee entity, OrderEmployeeRequestDTO dto) {
        if (dto == null) return;
        
        // Para PATCH, no validamos que haya datos - puede venir solo un campo
        
        // Procesar relaciones (solo si se proporcionan)
        updateEmployee(entity, dto.employee(), false);
        updateOrder(entity, dto.order(), false);
        updateQualificationFZ(entity, dto.qualificationFZ(), false);
        
        // Procesar campos simples (solo si se proporcionan)
        updateSimpleFields(entity, dto);
    }
    
    // ========== Métodos privados helper para actualización ==========
    
    private void updateEmployee(OrderEmployee entity, BasicReferenceDTO employeeRef, boolean isUpdate) {
        if (employeeRef != null) {
            if (employeeRef.id() != null) {
                Employee employee = employeeRepository.findById(employeeRef.id())
                    .orElseThrow(() -> new EntityNotFoundException(
                        "Employee not found with id: " + employeeRef.id()));
                entity.setEmployee(employee);
            } else {
                // Si viene con id null, desasignamos
                entity.setEmployee(null);
            }
        } else if (isUpdate) {
            // Para UPDATE, si employee viene como null, lo desasignamos
            entity.setEmployee(null);
        }
        // Para PATCH, si employee no viene, no hacemos nada
    }
    
    private void updateOrder(OrderEmployee entity, BasicReferenceDTO orderRef, boolean isUpdate) {
        if (orderRef != null) {
            if (orderRef.id() != null) {
                Order order = orderRepository.findById(orderRef.id())
                    .orElseThrow(() -> new EntityNotFoundException(
                        "Order not found with id: " + orderRef.id()));
                entity.setOrder(order);
            } else if (isUpdate) {
                // Para UPDATE, si order viene sin id, es error
                throw new IllegalArgumentException("Order ID is required when order is provided");
            }
            // Para PATCH, si order viene sin id, no hacemos nada
        } else if (isUpdate && entity.getOrder() == null) {
            // Para UPDATE, si order es null y la entidad no tiene order, es error
            // (esto es importante para CREATE - el order es obligatorio)
            throw new IllegalArgumentException("Order is required for creation/update");
        }
        // Para PATCH, si order no viene, no hacemos nada
    }
    
    private void updateQualificationFZ(OrderEmployee entity, BasicReferenceDTO qualificationRef, boolean isUpdate) {
        if (qualificationRef != null) {
            if (qualificationRef.id() != null) {
                QualificationFZ qualificationFZ = qualificationFZRepository.findById(qualificationRef.id())
                    .orElseThrow(() -> new EntityNotFoundException(
                        "QualificationFZ not found with id: " + qualificationRef.id()));
                entity.setQualificationFZ(qualificationFZ);
            } else {
                // Si viene con id null, desasignamos
                entity.setQualificationFZ(null);
            }
        } else if (isUpdate) {
            // Para UPDATE, si qualificationFZ viene como null, lo desasignamos
            entity.setQualificationFZ(null);
        }
        // Para PATCH, si qualificationFZ no viene, no hacemos nada
    }
    
    private void updateSimpleFields(OrderEmployee entity, OrderEmployeeRequestDTO dto) {
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
    
    private void validateDtoHasData(OrderEmployeeRequestDTO dto) {
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
    
    // ========== Métodos auxiliares privados para conversión a DTO ==========
    
    private EmployeeBasicDTO toEmployeeBasicDTO(Employee employee) {
        if (employee == null) return null;
        
        return new EmployeeBasicDTO(
            employee.getId(),
            employee.getEmployeeno(),
            employee.getFirstname(),
            employee.getLastname(),
            employee.getLabel(),
            employee.getVersion()
        );
    }
    
    private OrderReferenceDTO toOrderReferenceDTO(Order order) {
        if (order == null) return null;
        
        return new OrderReferenceDTO(
            order.getId(),
            order.getAcronym(),
            order.getOrderLabel(),
            order.getOrderNo(),
            order.getOrderTitle(),
            toProjectReferenceDTO(order.getProject()),
            order.getVersion()
        );
    }
    
    public ProjectReferenceDTO toProjectReferenceDTO(Project project) {
        if (project == null) return null;
        
        return new ProjectReferenceDTO(
            project.getId(),
            project.getProjectName(),
            project.getProjectLabel(),
            project.getTitle(),
            project.getVersion()
        );
    }
    
    private QualificationFZReferenceDTO toQualificationFZReferenceDTO(QualificationFZ qualificationFZ) {
        if (qualificationFZ == null) return null;
        
        return new QualificationFZReferenceDTO(
            qualificationFZ.getId(),
            qualificationFZ.getQualification()
        );
    }
}