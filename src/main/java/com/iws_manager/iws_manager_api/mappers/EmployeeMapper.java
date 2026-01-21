package com.iws_manager.iws_manager_api.mappers;

import com.iws_manager.iws_manager_api.dtos.employee.*;
import com.iws_manager.iws_manager_api.dtos.shared.CustomerInfoDTO;
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

    private void setBasicFieldsFromDTO(EmployeeInputDTO dto, Employee entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setFirstname(dto.firstname());
        entity.setLastname(dto.lastname());
        entity.setEmail(dto.email());
        entity.setLabel(dto.label());
        entity.setPhone(dto.phone());
        entity.setCoentrepreneursince(dto.coentrepreneursince());
        entity.setGeneralmanagersince(dto.generalmanagersince());
        entity.setShareholdersince(dto.shareholdersince());
        entity.setSoleproprietorsince(dto.soleproprietorsince());
        entity.setQualificationkmui(dto.qualificationkmui());
    }

    // Método para setear relaciones (elimina duplicación)
    private void setRelationsFromDTO(EmployeeInputDTO dto, Employee entity) {
        if (dto == null || entity == null) {
            return;
        }

        // Customer
        if (dto.customer() != null && dto.customer().id() != null) {
            Customer customer = new Customer();
            customer.setId(dto.customer().id());
            customer.setVersion(dto.customer().version());
            entity.setCustomer(customer);
        } else {
            entity.setCustomer(null); // Desasignar si viene null o sin id
        }

        // QualificationFZ
        if (dto.qualificationFZ() != null && dto.qualificationFZ().id() != null) {
            QualificationFZ qualificationFZ = new QualificationFZ();
            qualificationFZ.setId(dto.qualificationFZ().id());
            qualificationFZ.setVersion(dto.qualificationFZ().version());
            entity.setQualificationFZ(qualificationFZ);
        } else {
            entity.setQualificationFZ(null);
        }

        // Salutation
        if (dto.salutation() != null && dto.salutation().id() != null) {
            Salutation salutation = new Salutation();
            salutation.setId(dto.salutation().id());
            salutation.setVersion(dto.salutation().version());
            entity.setSalutation(salutation);
        } else {
            entity.setSalutation(null);
        }

        // Title
        if (dto.title() != null && dto.title().id() != null) {
            Title title = new Title();
            title.setId(dto.title().id());
            title.setVersion(dto.title().version());
            entity.setTitle(title);
        } else {
            entity.setTitle(null);
        }

        // EmployeeCategory
        if (dto.employeeCategory() != null && dto.employeeCategory().id() != null) {
            EmployeeCategory employeeCategory = new EmployeeCategory();
            employeeCategory.setId(dto.employeeCategory().id());
            employeeCategory.setVersion(dto.employeeCategory().version());
            entity.setEmployeeCategory(employeeCategory);
        } else {
            entity.setEmployeeCategory(null);
        }
    }

    // Convert DTO de entrada → Entidad (simplificado)
    public Employee toEntity(EmployeeInputDTO dto) {
        if (dto == null) {
            return null;
        }

        Employee entity = new Employee();
        setBasicFieldsFromDTO(dto, entity);
        setRelationsFromDTO(dto, entity);

        return entity;
    }

    // Actualizar entidad existente con datos del DTO (simplificado)
    public void updateEntityFromDTO(EmployeeInputDTO dto, Employee entity) {
        if (dto == null || entity == null) {
            return;
        }

        setBasicFieldsFromDTO(dto, entity);
        setRelationsFromDTO(dto, entity);
    }

    // Los demás métodos se mantienen igual...
    public EmployeeDTO toDTO(Employee entity) {
        if (entity == null) {
            return null;
        }

        QualificationFZInfoDTO qualificationFZDTO = buildQualificationFZInfoDTO(entity.getQualificationFZ());
        EmployeeCategoryInfoDTO employeeCategoryDTO = buildEmployeeCategoryInfoDTO(entity.getEmployeeCategory());
        return new EmployeeDTO(
                entity.getId(),
                entity.getFirstname(),
                entity.getLastname(),
                entity.getEmail(),
                entity.getEmployeeno(),
                entity.getLabel(),
                entity.getVersion(),
                // New fields
                entity.getGeneralmanagersince(),
                entity.getShareholdersince(),
                entity.getSoleproprietorsince(),
                entity.getCoentrepreneursince(),
                entity.getQualificationkmui(),
                // Relations
                qualificationFZDTO,
                employeeCategoryDTO,
                // Needed fields
                entity.getCustomer() != null ? entity.getCustomer().getId() : null,
                entity.getCustomer() != null ? entity.getCustomer().getCustomername1() : null);

    }

    public EmployeeDetailDTO toDetailDTO(Employee entity) {
        if (entity == null) {
            return null;
        }

        CustomerInfoDTO customerDTO = buildCustomerInfoDTO(entity.getCustomer());
        QualificationFZInfoDTO qualificationFZDTO = buildQualificationFZInfoDTO(entity.getQualificationFZ());
        SalutationInfoDTO salutationDTO = buildSalutationInfoDTO(entity.getSalutation());
        TitleInfoDTO titleDTO = buildTitleInfoDTO(entity.getTitle());
        EmployeeCategoryInfoDTO employeeCategoryDTO = buildEmployeeCategoryInfoDTO(entity.getEmployeeCategory());

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
                entity.getVersion(),
                customerDTO,
                qualificationFZDTO,
                salutationDTO,
                titleDTO,
                employeeCategoryDTO);
    }

    // Métodos helper para construir DTOs de relaciones
    private CustomerInfoDTO buildCustomerInfoDTO(Customer customer) {
        if (customer == null) {
            return null;
        }
        return new CustomerInfoDTO(
                customer.getId(),
                customer.getCustomername1(),
                customer.getCustomername2(),
                customer.getCity());
    }

    private QualificationFZInfoDTO buildQualificationFZInfoDTO(QualificationFZ qualificationFZ) {
        if (qualificationFZ == null) {
            return null;
        }
        return new QualificationFZInfoDTO(
                qualificationFZ.getId(),
                qualificationFZ.getQualification());
    }

    private SalutationInfoDTO buildSalutationInfoDTO(Salutation salutation) {
        if (salutation == null) {
            return null;
        }
        return new SalutationInfoDTO(
                salutation.getId(),
                salutation.getName());
    }

    private TitleInfoDTO buildTitleInfoDTO(Title title) {
        if (title == null) {
            return null;
        }
        return new TitleInfoDTO(
                title.getId(),
                title.getName());
    }

    private EmployeeCategoryInfoDTO buildEmployeeCategoryInfoDTO(EmployeeCategory employeeCategory) {
        if (employeeCategory == null) {
            return null;
        }
        return new EmployeeCategoryInfoDTO(
                employeeCategory.getId(),
                employeeCategory.getLabel(),
                employeeCategory.getTitle());
    }

    public List<EmployeeDTO> toDTOList(List<Employee> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public EmployeeNameDTO toNameDTO(Employee entity) {
        if (entity == null) {
            return null;
        }

        return new EmployeeNameDTO(
                entity.getId(),
                entity.getEmployeeno(),
                entity.getFirstname(),
                entity.getLastname(),
                buildFullName(entity.getFirstname(), entity.getLastname()));
    }

    private String buildFullName(String firstname, String lastname) {
        boolean hasLastName = lastname != null && !lastname.trim().isEmpty();
        boolean hasFirstName = firstname != null && !firstname.trim().isEmpty();

        if (hasLastName && hasFirstName) {
            return lastname.trim() + ", " + firstname.trim();
        } else if (hasLastName) {
            return lastname.trim();
        } else if (hasFirstName) {
            return firstname.trim();
        }
        return "";
    }

    public List<EmployeeNameDTO> toNameDTOList(List<Employee> entities) {
        if (entities == null) {
            return List.of();
        }

        return entities.stream()
                .map(this::toNameDTO)
                .collect(Collectors.toList());
    }

}
