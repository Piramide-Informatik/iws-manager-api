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
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderEmployeeMapper {
    
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
        
        // Employee - puede ser null para desasignar
        if (dto.employee() != null && dto.employee().id() != null) {
            Employee employee = new Employee();
            employee.setId(dto.employee().id());
            entity.setEmployee(employee);
        } else {
            // Si employee viene como null o con id null, desasignamos
            entity.setEmployee(null);
        }
        
        // Order - requerido para CREATE, pero para UPDATE podría ser opcional
        if (dto.order() != null && dto.order().id() != null) {
            Order order = new Order();
            order.setId(dto.order().id());
            entity.setOrder(order);
        }
        // Nota: Para UPDATE, si order no viene, no lo cambiamos
        
        // QualificationFZ - puede ser null para desasignar
        if (dto.qualificationFZ() != null && dto.qualificationFZ().id() != null) {
            QualificationFZ qualificationFZ = new QualificationFZ();
            qualificationFZ.setId(dto.qualificationFZ().id());
            entity.setQualificationFZ(qualificationFZ);
        } else {
            entity.setQualificationFZ(null);
        }
        
        // Campos simples - solo actualizar si vienen en el DTO
        // Para CREATE, estos campos vienen; para UPDATE, pueden venir o no
        
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
            toProjectReferenceDTO(order.getProject())
        );
    }
    
    private ProjectReferenceDTO toProjectReferenceDTO(Project project) {
        if (project == null) return null;
        
        return new ProjectReferenceDTO(
            project.getId(),
            project.getProjectName(),
            project.getProjectLabel(),
            project.getTitle()
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