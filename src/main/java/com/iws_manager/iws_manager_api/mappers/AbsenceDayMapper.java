package com.iws_manager.iws_manager_api.mappers;

import com.iws_manager.iws_manager_api.dtos.absenceday.*;
import com.iws_manager.iws_manager_api.dtos.shared.AbsenceTypeInfoDTO;
import com.iws_manager.iws_manager_api.dtos.shared.BasicReferenceDTO;
import com.iws_manager.iws_manager_api.dtos.shared.EmployeeBasicDTO;
import com.iws_manager.iws_manager_api.dtos.shared.EmployeeInfoDTO;
import com.iws_manager.iws_manager_api.models.AbsenceDay;
import com.iws_manager.iws_manager_api.models.AbsenceType;
import com.iws_manager.iws_manager_api.models.Employee;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AbsenceDayMapper {

    // Entity -> AbsenceDayInfoDTO (for lists)
    public AbsenceDayInfoDTO toInfoDTO(AbsenceDay absenceDay) {
        if (absenceDay == null) {
            return null;
        }

        return new AbsenceDayInfoDTO(
            absenceDay.getId(),
            absenceDay.getAbsenceDate(),
            toAbsenceTypeInfoDTO(absenceDay.getAbsenceType()),
            toEmployeeBasicDTO(absenceDay.getEmployee()),
            absenceDay.getVersion()
        );
    }

    // Entity -> AbsenceDayDetailDTO (for details)
    public AbsenceDayDetailDTO toDetailDTO(AbsenceDay absenceDay) {
        if (absenceDay == null) {
            return null;
        }

        return new AbsenceDayDetailDTO(
            absenceDay.getId(),
            absenceDay.getAbsenceDate(),
            absenceDay.getCreatedAt(),
            absenceDay.getUpdatedAt(),
            absenceDay.getVersion(),
            toAbsenceTypeInfoDTO(absenceDay.getAbsenceType()),
            toEmployeeInfoDTO(absenceDay.getEmployee())
        );
    }

    // AbsenceDayRequestDTO -> Entity (for creation)
    public AbsenceDay toEntity(AbsenceDayRequestDTO requestDTO) {
        if (requestDTO == null) {
            return null;
        }

        AbsenceDay absenceDay = new AbsenceDay();
        absenceDay.setAbsenceDate(requestDTO.absenceDate());
        
        return absenceDay;
    }

    // List<Entity> -> List<AbsenceDayInfoDTO>
    public List<AbsenceDayInfoDTO> toInfoDTOList(List<AbsenceDay> absenceDays) {
        if (absenceDays == null) {
            return List.of();
        }
        
        return absenceDays.stream()
                .map(this::toInfoDTO)
                .collect(Collectors.toList());
    }

    // List<Entity> -> List<AbsenceDayDetailDTO>
    public List<AbsenceDayDetailDTO> toDetailDTOList(List<AbsenceDay> absenceDays) {
        if (absenceDays == null) {
            return List.of();
        }
        
        return absenceDays.stream()
                .map(this::toDetailDTO)
                .collect(Collectors.toList());
    }

    // NUEVO MÉTODO: Employee -> EmployeeBasicDTO
    private EmployeeBasicDTO toEmployeeBasicDTO(Employee employee) {
        if (employee == null) {
            return null;
        }

        return new EmployeeBasicDTO(
            employee.getId(),
            employee.getEmployeeno(),
            employee.getFirstname(),
            employee.getLastname(),
            employee.getLabel(),
            employee.getVersion()
        );
    }

    // Helper: AbsenceType -> AbsenceTypeInfoDTO
    private AbsenceTypeInfoDTO toAbsenceTypeInfoDTO(AbsenceType absenceType) {
        if (absenceType == null) {
            return null;
        }

        return new AbsenceTypeInfoDTO(
            absenceType.getId(),
            absenceType.getName(),
            absenceType.getLabel(),
            absenceType.getHours(),
            absenceType.getIsHoliday(),
            absenceType.getShareOfDay(),
            absenceType.getVersion()
        );
    }

    // Helper: Employee -> EmployeeInfoDTO
    private EmployeeInfoDTO toEmployeeInfoDTO(Employee employee) {
        if (employee == null) {
            return null;
        }

        return new EmployeeInfoDTO(
            employee.getId(),
            employee.getFirstname(),
            employee.getLastname(),
            employee.getEmail(),
            employee.getEmployeeno(),
            employee.getLabel(),
            employee.getPhone(),
            employee.getCoentrepreneursince(),
            employee.getGeneralmanagersince(),
            employee.getShareholdersince(),
            employee.getSoleproprietorsince(),
            employee.getQualificationkmui(),
            employee.getVersion(),
            null, // customer
            null, // qualificationFZ
            null, // salutation
            null, // title
            null  // employeeCategory
        );
    }

    private BasicReferenceDTO toBasicReferenceDTO(Object entity) {
        if (entity == null) {
            return null;
        }
        
        if (entity instanceof AbsenceType absenceType) {
            return new BasicReferenceDTO(
                absenceType.getId(),
                absenceType.getVersion()
            );
        } else if (entity instanceof Employee employee) {
            return new BasicReferenceDTO(
                employee.getId(),
                employee.getVersion()
            );
        }
        
        return null;
    }

    public AbsenceDayCountDTO toCountDTO(Object[] result) {
        if (result == null || result.length < 2) {
            return null;
        }

        AbsenceType absenceType = (AbsenceType) result[0];
        Long count = (Long) result[1];

        return new AbsenceDayCountDTO(
            toAbsenceTypeInfoDTO(absenceType),
            count
        );
    }

    public List<AbsenceDayCountDTO> toCountDTOList(List<Object[]> results) {
        if (results == null) {
            return List.of();
        }
        
        return results.stream()
                .map(this::toCountDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDTO(AbsenceDay entity, AbsenceDayRequestDTO dto) {
        if (entity == null || dto == null) {
            return;
        }

        if (dto.absenceDate() != null) {
            entity.setAbsenceDate(dto.absenceDate());
        }
    }
}