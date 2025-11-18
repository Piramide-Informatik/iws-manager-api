package com.iws_manager.iws_manager_api.mappers;

import com.iws_manager.iws_manager_api.dtos.employee.*;
import com.iws_manager.iws_manager_api.dtos.shared.BasicReferenceDTO;
import com.iws_manager.iws_manager_api.models.Employee;
import com.iws_manager.iws_manager_api.models.Customer;
import com.iws_manager.iws_manager_api.models.QualificationFZ;
import com.iws_manager.iws_manager_api.models.Salutation;
import com.iws_manager.iws_manager_api.models.Title;
import com.iws_manager.iws_manager_api.models.EmployeeCategory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {

    // Convert Entity → DTO básico (para listados)
    public EmployeeDTO toDTO(Employee entity) {
        if (entity == null) {
            return null;
        }
        
        return new EmployeeDTO(
            entity.getId(),
            entity.getFirstname(),
            entity.getLastname(),
            entity.getEmail(),
            entity.getEmployeeno(),
            entity.getLabel(),
            entity.getCustomer() != null ? entity.getCustomer().getId() : null,
            entity.getCustomer() != null ? entity.getCustomer().getCustomername1() : null
        );
    }

    // Convert Entity → DTO detallado (para GET por ID)
    public EmployeeDetailDTO toDetailDTO(Employee entity) {
        if (entity == null) {
            return null;
        }
        
        // Construir DTOs para relaciones
        CustomerInfoDTO customerDTO = null;
        if (entity.getCustomer() != null) {
            Customer customer = entity.getCustomer();
            customerDTO = new CustomerInfoDTO(
                customer.getId(),
                customer.getCustomername1(),
                customer.getCustomername2(),
                customer.getCity()
            );
        }
        
        QualificationFZInfoDTO qualificationFZDTO = null;
        if (entity.getQualificationFZ() != null) {
            QualificationFZ qualificationFZ = entity.getQualificationFZ();
            qualificationFZDTO = new QualificationFZInfoDTO(
                qualificationFZ.getId(),
                qualificationFZ.getQualification()
            );
        }
        
        SalutationInfoDTO salutationDTO = null;
        if (entity.getSalutation() != null) {
            Salutation salutation = entity.getSalutation();
            salutationDTO = new SalutationInfoDTO(
                salutation.getId(),
                salutation.getName()
            );
        }
        
        TitleInfoDTO titleDTO = null;
        if (entity.getTitle() != null) {
            Title title = entity.getTitle();
            titleDTO = new TitleInfoDTO(
                title.getId(),
                title.getName()
            );
        }
        
        EmployeeCategoryInfoDTO employeeCategoryDTO = null;
        if (entity.getEmployeeCategory() != null) {
            EmployeeCategory employeeCategory = entity.getEmployeeCategory();
            employeeCategoryDTO = new EmployeeCategoryInfoDTO(
                employeeCategory.getId(),
                employeeCategory.getLabel(),
                employeeCategory.getTitle()
            );
        }
        
        return new EmployeeDetailDTO(
            entity.getId(),
            entity.getFirstname(),
            entity.getLastname(),
            entity.getEmail(),
            entity.getEmployeeno(),
            entity.getLabel(),
            entity.getPhone(),
            entity.getCoentrepreneursince(),
            entity.getGeneralmanagersince(),
            entity.getShareholdersince(),
            entity.getSoleproprietorsince(),
            entity.getQualificationkmui(),
            customerDTO,
            qualificationFZDTO,
            salutationDTO,
            titleDTO,
            employeeCategoryDTO
        );
    }

    // Convert DTO de entrada → Entidad
    public Employee toEntity(EmployeeInputDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Employee entity = new Employee();
        entity.setFirstname(dto.firstname());
        entity.setLastname(dto.lastname());
        entity.setEmail(dto.email());
        entity.setEmployeeno(dto.employeeno());
        entity.setLabel(dto.label());
        entity.setPhone(dto.phone());
        entity.setCoentrepreneursince(dto.coentrepreneursince());
        entity.setGeneralmanagersince(dto.generalmanagersince());
        entity.setShareholdersince(dto.shareholdersince());
        entity.setSoleproprietorsince(dto.soleproprietorsince());
        entity.setQualificationkmui(dto.qualificationkmui());
        
        // Manejar relaciones con version
        if (dto.customer() != null) {
            Customer customer = new Customer();
            customer.setId(dto.customer().id());
            customer.setVersion(dto.customer().version());
            entity.setCustomer(customer);
        }
        
        if (dto.qualificationFZ() != null) {
            QualificationFZ qualificationFZ = new QualificationFZ();
            qualificationFZ.setId(dto.qualificationFZ().id());
            qualificationFZ.setVersion(dto.qualificationFZ().version());
            entity.setQualificationFZ(qualificationFZ);
        }
        
        if (dto.salutation() != null) {
            Salutation salutation = new Salutation();
            salutation.setId(dto.salutation().id());
            salutation.setVersion(dto.salutation().version());
            entity.setSalutation(salutation);
        }
        
        if (dto.title() != null) {
            Title title = new Title();
            title.setId(dto.title().id());
            title.setVersion(dto.title().version());
            entity.setTitle(title);
        }
        
        if (dto.employeeCategory() != null) {
            EmployeeCategory employeeCategory = new EmployeeCategory();
            employeeCategory.setId(dto.employeeCategory().id());
            employeeCategory.setVersion(dto.employeeCategory().version());
            entity.setEmployeeCategory(employeeCategory);
        }
        
        return entity;
    }

    // Convert lista de entidades → lista de DTOs
    public List<EmployeeDTO> toDTOList(List<Employee> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Actualizar entidad existente con datos del DTO
    public void updateEntityFromDTO(EmployeeInputDTO dto, Employee entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setFirstname(dto.firstname());
        entity.setLastname(dto.lastname());
        entity.setEmail(dto.email());
        entity.setEmployeeno(dto.employeeno());
        entity.setLabel(dto.label());
        entity.setPhone(dto.phone());
        entity.setCoentrepreneursince(dto.coentrepreneursince());
        entity.setGeneralmanagersince(dto.generalmanagersince());
        entity.setShareholdersince(dto.shareholdersince());
        entity.setSoleproprietorsince(dto.soleproprietorsince());
        entity.setQualificationkmui(dto.qualificationkmui());
        
        // Actualizar relaciones con version
        if (dto.customer() != null) {
            Customer customer = new Customer();
            customer.setId(dto.customer().id());
            customer.setVersion(dto.customer().version());
            entity.setCustomer(customer);
        }
        
        if (dto.qualificationFZ() != null) {
            QualificationFZ qualificationFZ = new QualificationFZ();
            qualificationFZ.setId(dto.qualificationFZ().id());
            qualificationFZ.setVersion(dto.qualificationFZ().version());
            entity.setQualificationFZ(qualificationFZ);
        }
        
        if (dto.salutation() != null) {
            Salutation salutation = new Salutation();
            salutation.setId(dto.salutation().id());
            salutation.setVersion(dto.salutation().version());
            entity.setSalutation(salutation);
        }
        
        if (dto.title() != null) {
            Title title = new Title();
            title.setId(dto.title().id());
            title.setVersion(dto.title().version());
            entity.setTitle(title);
        }
        
        if (dto.employeeCategory() != null) {
            EmployeeCategory employeeCategory = new EmployeeCategory();
            employeeCategory.setId(dto.employeeCategory().id());
            employeeCategory.setVersion(dto.employeeCategory().version());
            entity.setEmployeeCategory(employeeCategory);
        }
    }
}