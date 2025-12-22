package com.iws_manager.iws_manager_api.mappers;

import com.iws_manager.iws_manager_api.dtos.orderemployee.OrderEmployeeRequestDTO;
import com.iws_manager.iws_manager_api.dtos.orderemployee.OrderEmployeeResponseDTO;
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
            
            // Employee (puede ser null)
            toEmployeeBasicDTO(entity.getEmployee()),
            
            // Order (asumimos que nunca es null, pero por seguridad)
            toOrderReferenceDTO(entity.getOrder()),
            
            // QualificationFZ (puede ser null)
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
        
        // Employee - CARGAR desde la base de datos
        if (dto.employee() != null && dto.employee().id() != null) {
            Employee employee = employeeRepository.findById(dto.employee().id())
                .orElseThrow(() -> new EntityNotFoundException(
                    "Employee not found with id: " + dto.employee().id()));
            entity.setEmployee(employee);
        } else {
            // Si employee viene como null o con id null, desasignamos
            entity.setEmployee(null);
        }
        
        // Order - CARGAR desde la base de datos
        if (dto.order() != null && dto.order().id() != null) {
            Order order = orderRepository.findById(dto.order().id())
                .orElseThrow(() -> new EntityNotFoundException(
                    "Order not found with id: " + dto.order().id()));
            entity.setOrder(order);
        } else if (dto.order() != null) {
            // Si order viene pero sin id, es un error
            throw new IllegalArgumentException("Order ID is required when order is provided");
        }
        // Nota: Para UPDATE, si order no viene en el DTO, no lo cambiamos
        
        // QualificationFZ - CARGAR desde la base de datos
        if (dto.qualificationFZ() != null && dto.qualificationFZ().id() != null) {
            QualificationFZ qualificationFZ = qualificationFZRepository.findById(dto.qualificationFZ().id())
                .orElseThrow(() -> new EntityNotFoundException(
                    "QualificationFZ not found with id: " + dto.qualificationFZ().id()));
            entity.setQualificationFZ(qualificationFZ);
        } else {
            // Si qualificationFZ viene como null o con id null, desasignamos
            entity.setQualificationFZ(null);
        }
        
        // Campos simples
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
    
    // ========== Método para PATCH (actualización parcial) ==========
    
    public void applyPartialUpdate(OrderEmployee entity, OrderEmployeeRequestDTO dto) {
        if (dto == null) return;
        
        // Employee - CARGAR desde la base de datos si se proporciona
        if (dto.employee() != null) {
            if (dto.employee().id() != null) {
                Employee employee = employeeRepository.findById(dto.employee().id())
                    .orElseThrow(() -> new EntityNotFoundException(
                        "Employee not found with id: " + dto.employee().id()));
                entity.setEmployee(employee);
            } else {
                // Si viene employee con id null, desasignamos
                entity.setEmployee(null);
            }
        }
        
        // Order - CARGAR desde la base de datos si se proporciona
        if (dto.order() != null && dto.order().id() != null) {
            Order order = orderRepository.findById(dto.order().id())
                .orElseThrow(() -> new EntityNotFoundException(
                    "Order not found with id: " + dto.order().id()));
            entity.setOrder(order);
        }
        
        // QualificationFZ - CARGAR desde la base de datos si se proporciona
        if (dto.qualificationFZ() != null) {
            if (dto.qualificationFZ().id() != null) {
                QualificationFZ qualificationFZ = qualificationFZRepository.findById(dto.qualificationFZ().id())
                    .orElseThrow(() -> new EntityNotFoundException(
                        "QualificationFZ not found with id: " + dto.qualificationFZ().id()));
                entity.setQualificationFZ(qualificationFZ);
            } else {
                // Si viene qualificationFZ con id null, desasignamos
                entity.setQualificationFZ(null);
            }
        }
        
        // Campos simples - solo si vienen en el DTO
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
    
    // ========== Métodos auxiliares privados ==========
    
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