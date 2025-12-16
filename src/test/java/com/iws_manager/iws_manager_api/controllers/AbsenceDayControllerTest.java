package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.dtos.absenceday.*;
import com.iws_manager.iws_manager_api.dtos.shared.AbsenceTypeInfoDTO;
import com.iws_manager.iws_manager_api.dtos.shared.BasicReferenceDTO;
import com.iws_manager.iws_manager_api.dtos.shared.EmployeeBasicDTO;
import com.iws_manager.iws_manager_api.dtos.shared.EmployeeInfoDTO;
import com.iws_manager.iws_manager_api.mappers.AbsenceDayMapper;
import com.iws_manager.iws_manager_api.models.AbsenceDay;
import com.iws_manager.iws_manager_api.models.Employee;
import com.iws_manager.iws_manager_api.models.AbsenceType;
import com.iws_manager.iws_manager_api.services.v2.interfaces.AbsenceDayServiceV2;
import com.iws_manager.iws_manager_api.controllers.v2.AbsenceDayControllerV2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AbsenceDayControllerTest {

    private static final Long ABSENCE_DAY_ID = 1L;
    private static final Long EMPLOYEE_ID = 10L;
    private static final Long ABSENCE_TYPE_ID = 20L;
    private static final LocalDate TEST_DATE = LocalDate.of(2024, 1, 15);
    private static final LocalDate START_DATE = LocalDate.of(2024, 1, 1);
    private static final LocalDate END_DATE = LocalDate.of(2024, 1, 31);
    private static final int YEAR = 2024;

    @Mock
    private AbsenceDayServiceV2 absenceDayService;

    @Mock
    private AbsenceDayMapper absenceDayMapper;

    @InjectMocks
    private AbsenceDayControllerV2 absenceDayControllerV2;

    private AbsenceDay absenceDay;
    private Employee employee;
    private AbsenceType absenceType;
    private AbsenceDayRequestDTO requestDTO;
    private AbsenceDayInfoDTO infoDTO;
    private AbsenceDayDetailDTO detailDTO;
    private AbsenceDayCountDTO countDTO;

    @BeforeEach
    void setUp() {
        // Setup entities
        employee = new Employee();
        employee.setId(EMPLOYEE_ID);
        employee.setVersion(1);
        employee.setEmployeeno(1001);
        employee.setFirstname("Ana");
        employee.setLastname("Müller");
        employee.setLabel("Desarrolladora Senior");
        
        absenceType = new AbsenceType();
        absenceType.setId(ABSENCE_TYPE_ID);
        absenceType.setVersion(0);
        absenceType.setName("Vacaciones");
        absenceType.setLabel("VAC");
        absenceType.setHours((byte) 8);
        absenceType.setIsHoliday((byte) 0);
        absenceType.setShareOfDay(new BigDecimal("1.0"));
        
        absenceDay = new AbsenceDay();
        absenceDay.setId(ABSENCE_DAY_ID);
        absenceDay.setAbsenceDate(TEST_DATE);
        absenceDay.setEmployee(employee);
        absenceDay.setAbsenceType(absenceType);
        absenceDay.setCreatedAt(LocalDateTime.now());
        absenceDay.setUpdatedAt(LocalDateTime.now());
        absenceDay.setVersion(0);

        // Setup DTOs
        requestDTO = new AbsenceDayRequestDTO(
            TEST_DATE,
            new BasicReferenceDTO(ABSENCE_TYPE_ID, 0),
            new BasicReferenceDTO(EMPLOYEE_ID, 1)
        );

        infoDTO = new AbsenceDayInfoDTO(
            ABSENCE_DAY_ID,
            TEST_DATE,
            new AbsenceTypeInfoDTO(
                ABSENCE_TYPE_ID,
                "Vacaciones",
                "VAC",
                (byte) 8,
                (byte) 0,
                new BigDecimal("1.0"),
                0
            ),
            new EmployeeBasicDTO(
                EMPLOYEE_ID,
                1001,
                "Ana",
                "Müller",
                "Desarrolladora Senior",
                1
            ),
            0
        );

        detailDTO = new AbsenceDayDetailDTO(
            ABSENCE_DAY_ID,
            TEST_DATE,
            LocalDateTime.now(),
            LocalDateTime.now(),
            0,
            new AbsenceTypeInfoDTO(
                ABSENCE_TYPE_ID,
                "Vacaciones",
                "VAC",
                (byte) 8,
                (byte) 0,
                new BigDecimal("1.0"),
                0
            ),
            new EmployeeInfoDTO(
                EMPLOYEE_ID,
                "Ana",
                "Müller",
                "ana.mueller@example.com",
                1001,
                "Desarrolladora Senior",
                "+123456789",
                null,
                null,
                null,
                null,
                "Java, Spring Boot",
                1,
                null, null, null, null, null
            )
        );

        countDTO = new AbsenceDayCountDTO(
            new AbsenceTypeInfoDTO(
                ABSENCE_TYPE_ID,
                "Vacaciones",
                "VAC",
                (byte) 8,
                (byte) 0,
                new BigDecimal("1.0"),
                0
            ),
            5L
        );
    }

    @Test
    void create_ShouldReturnCreatedAbsenceDayDetailDTO_WhenValidRequest() {
        // Arrange
        when(absenceDayService.createFromDTO(requestDTO)).thenReturn(absenceDay);
        when(absenceDayMapper.toDetailDTO(absenceDay)).thenReturn(detailDTO);

        // Act
        ResponseEntity<AbsenceDayDetailDTO> response = absenceDayControllerV2.create(requestDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(detailDTO, response.getBody());
        
        verify(absenceDayService).createFromDTO(requestDTO);
        verify(absenceDayMapper).toDetailDTO(absenceDay);
        
        verify(absenceDayMapper, never()).toEntity(any());
    }

    @Test
    void getById_ShouldReturnAbsenceDayDetailDTO_WhenFound() {
        // Arrange
        when(absenceDayService.findById(ABSENCE_DAY_ID)).thenReturn(Optional.of(absenceDay));
        when(absenceDayMapper.toDetailDTO(absenceDay)).thenReturn(detailDTO);

        // Act
        ResponseEntity<AbsenceDayDetailDTO> response = absenceDayControllerV2.getById(ABSENCE_DAY_ID);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(detailDTO, response.getBody());
        
        verify(absenceDayService).findById(ABSENCE_DAY_ID);
        verify(absenceDayMapper).toDetailDTO(absenceDay);
    }

    @Test
    void getById_ShouldReturnNotFound_WhenNotFound() {
        // Arrange
        when(absenceDayService.findById(ABSENCE_DAY_ID)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<AbsenceDayDetailDTO> response = absenceDayControllerV2.getById(ABSENCE_DAY_ID);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        
        verify(absenceDayService).findById(ABSENCE_DAY_ID);
        verify(absenceDayMapper, never()).toDetailDTO(any());
    }

    @Test
    void getAll_ShouldReturnListOfAbsenceDayInfoDTOs() {
        // Arrange
        List<AbsenceDay> absenceDays = Arrays.asList(absenceDay);
        List<AbsenceDayInfoDTO> infoDTOs = Arrays.asList(infoDTO);
        
        when(absenceDayService.findAll()).thenReturn(absenceDays);
        when(absenceDayMapper.toInfoDTOList(absenceDays)).thenReturn(infoDTOs);

        // Act
        ResponseEntity<List<AbsenceDayInfoDTO>> response = absenceDayControllerV2.getAll();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(infoDTO, response.getBody().get(0));
        
        verify(absenceDayService).findAll();
        verify(absenceDayMapper).toInfoDTOList(absenceDays);
    }

    @Test
    void getAll_ShouldReturnEmptyList() {
        // Arrange
        when(absenceDayService.findAll()).thenReturn(Collections.emptyList());
        when(absenceDayMapper.toInfoDTOList(any())).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<AbsenceDayInfoDTO>> response = absenceDayControllerV2.getAll();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        
        verify(absenceDayService).findAll();
        verify(absenceDayMapper).toInfoDTOList(any());
    }

    @Test
    void update_ShouldReturnUpdatedAbsenceDayDetailDTO() {
        // Arrange
        when(absenceDayService.updateFromDTO(ABSENCE_DAY_ID, requestDTO)).thenReturn(absenceDay);
        when(absenceDayMapper.toDetailDTO(absenceDay)).thenReturn(detailDTO);

        // Act
        ResponseEntity<AbsenceDayDetailDTO> response = 
            absenceDayControllerV2.update(ABSENCE_DAY_ID, requestDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(detailDTO, response.getBody());
        
        verify(absenceDayService).updateFromDTO(ABSENCE_DAY_ID, requestDTO);
        verify(absenceDayMapper).toDetailDTO(absenceDay);
    }

    @Test
    void delete_ShouldReturnNoContent() {
        // Act
        ResponseEntity<Void> response = absenceDayControllerV2.delete(ABSENCE_DAY_ID);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        
        verify(absenceDayService).delete(ABSENCE_DAY_ID);
    }

    @Test
    void getByEmployeeId_ShouldReturnListOfAbsenceDayInfoDTOs() {
        // Arrange
        List<AbsenceDay> absenceDays = Arrays.asList(absenceDay);
        List<AbsenceDayInfoDTO> infoDTOs = Arrays.asList(infoDTO);
        
        when(absenceDayService.getByEmployeeId(EMPLOYEE_ID)).thenReturn(absenceDays);
        when(absenceDayMapper.toInfoDTOList(absenceDays)).thenReturn(infoDTOs);

        // Act
        ResponseEntity<List<AbsenceDayInfoDTO>> response = 
            absenceDayControllerV2.getByEmployeeId(EMPLOYEE_ID);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(infoDTO, response.getBody().get(0));
        
        verify(absenceDayService).getByEmployeeId(EMPLOYEE_ID);
        verify(absenceDayMapper).toInfoDTOList(absenceDays);
    }

    @Test
    void getByEmployeeIdAndDateRange_ShouldReturnListOfAbsenceDayInfoDTOs() {
        // Arrange
        List<AbsenceDay> absenceDays = Arrays.asList(absenceDay);
        List<AbsenceDayInfoDTO> infoDTOs = Arrays.asList(infoDTO);
        
        when(absenceDayService.getByEmployeeIdAndDateRange(EMPLOYEE_ID, START_DATE, END_DATE))
            .thenReturn(absenceDays);
        when(absenceDayMapper.toInfoDTOList(absenceDays)).thenReturn(infoDTOs);

        // Act
        ResponseEntity<List<AbsenceDayInfoDTO>> response = 
            absenceDayControllerV2.getByEmployeeIdAndDateRange(EMPLOYEE_ID, START_DATE, END_DATE);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        
        verify(absenceDayService).getByEmployeeIdAndDateRange(EMPLOYEE_ID, START_DATE, END_DATE);
        verify(absenceDayMapper).toInfoDTOList(absenceDays);
    }

    @Test
    void getByEmployeeIdAndAbsenceTypeId_ShouldReturnListOfAbsenceDayInfoDTOs() {
        // Arrange
        List<AbsenceDay> absenceDays = Arrays.asList(absenceDay);
        List<AbsenceDayInfoDTO> infoDTOs = Arrays.asList(infoDTO);
        
        when(absenceDayService.getByEmployeeIdAndAbsenceTypeId(EMPLOYEE_ID, ABSENCE_TYPE_ID))
            .thenReturn(absenceDays);
        when(absenceDayMapper.toInfoDTOList(absenceDays)).thenReturn(infoDTOs);

        // Act
        ResponseEntity<List<AbsenceDayInfoDTO>> response = 
            absenceDayControllerV2.getByEmployeeIdAndAbsenceTypeId(EMPLOYEE_ID, ABSENCE_TYPE_ID);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        
        verify(absenceDayService).getByEmployeeIdAndAbsenceTypeId(EMPLOYEE_ID, ABSENCE_TYPE_ID);
        verify(absenceDayMapper).toInfoDTOList(absenceDays);
    }

    @Test
    void existsByEmployeeIdAndAbsenceDate_ShouldReturnTrue() {
        // Arrange
        when(absenceDayService.existsByEmployeeIdAndAbsenceDate(EMPLOYEE_ID, TEST_DATE))
            .thenReturn(true);

        // Act
        ResponseEntity<Boolean> response = 
            absenceDayControllerV2.existsByEmployeeIdAndAbsenceDate(EMPLOYEE_ID, TEST_DATE);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
        
        verify(absenceDayService).existsByEmployeeIdAndAbsenceDate(EMPLOYEE_ID, TEST_DATE);
    }

    @Test
    void getByEmployeeIdAndYear_ShouldReturnListOfAbsenceDayInfoDTOs() {
        // Arrange
        List<AbsenceDay> absenceDays = Arrays.asList(absenceDay);
        List<AbsenceDayInfoDTO> infoDTOs = Arrays.asList(infoDTO);
        
        when(absenceDayService.getByEmployeeIdAndYear(EMPLOYEE_ID, YEAR)).thenReturn(absenceDays);
        when(absenceDayMapper.toInfoDTOList(absenceDays)).thenReturn(infoDTOs);

        // Act
        ResponseEntity<List<AbsenceDayInfoDTO>> response = 
            absenceDayControllerV2.getByEmployeeIdAndYear(EMPLOYEE_ID, YEAR);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        
        verify(absenceDayService).getByEmployeeIdAndYear(EMPLOYEE_ID, YEAR);
        verify(absenceDayMapper).toInfoDTOList(absenceDays);
    }

    @Test
    void countByEmployeeIdAndAbsenceTypeIdAndYear_ShouldReturnCount() {
        // Arrange
        long expectedCount = 5L;
        when(absenceDayService.countByEmployeeIdAndAbsenceTypeIdAndYear(EMPLOYEE_ID, ABSENCE_TYPE_ID, YEAR))
            .thenReturn(expectedCount);

        // Act
        ResponseEntity<Long> response = 
            absenceDayControllerV2.countByEmployeeIdAndAbsenceTypeIdAndYear(EMPLOYEE_ID, ABSENCE_TYPE_ID, YEAR);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCount, response.getBody());
        
        verify(absenceDayService).countByEmployeeIdAndAbsenceTypeIdAndYear(EMPLOYEE_ID, ABSENCE_TYPE_ID, YEAR);
    }

    @Test
    void countByTypeForEmployee_ShouldReturnListOfAbsenceDayCountDTOs() {
        // Arrange
        Object[] resultArray = new Object[]{absenceType, 5L};
        List<Object[]> results = Collections.singletonList(resultArray);
        List<AbsenceDayCountDTO> countDTOs = Collections.singletonList(countDTO);
        
        when(absenceDayService.countAbsenceDaysByTypeForEmployee(EMPLOYEE_ID)).thenReturn(results);
        when(absenceDayMapper.toCountDTOList(results)).thenReturn(countDTOs);

        // Act
        ResponseEntity<List<AbsenceDayCountDTO>> response = 
            absenceDayControllerV2.countByTypeForEmployee(EMPLOYEE_ID);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(countDTO, response.getBody().get(0));
        
        verify(absenceDayService).countAbsenceDaysByTypeForEmployee(EMPLOYEE_ID);
        verify(absenceDayMapper).toCountDTOList(results);
    }

    @Test
    void countByTypeForEmployeeAndYear_ShouldReturnListOfAbsenceDayCountDTOs() {
        // Arrange
        Object[] resultArray = new Object[]{absenceType, 5L};
        List<Object[]> results = Collections.singletonList(resultArray);
        
        List<AbsenceDayCountDTO> countDTOs = Collections.singletonList(countDTO);
        
        when(absenceDayService.countAbsenceDaysByTypeForEmployeeAndYear(EMPLOYEE_ID, YEAR))
            .thenReturn(results);
        when(absenceDayMapper.toCountDTOList(results)).thenReturn(countDTOs);

        // Act
        ResponseEntity<List<AbsenceDayCountDTO>> response = 
            absenceDayControllerV2.countByTypeForEmployeeAndYear(EMPLOYEE_ID, YEAR);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(countDTO, response.getBody().get(0));
        
        verify(absenceDayService).countAbsenceDaysByTypeForEmployeeAndYear(EMPLOYEE_ID, YEAR);
        verify(absenceDayMapper).toCountDTOList(results);
    }
    @Test
    void filter_ShouldReturnListOfAbsenceDayInfoDTOs() {
        // Arrange
        AbsenceDayFilterDTO filterDTO = new AbsenceDayFilterDTO(
            EMPLOYEE_ID,
            ABSENCE_TYPE_ID,
            START_DATE,
            END_DATE,
            YEAR,
            false
        );
        
        List<AbsenceDay> absenceDays = Arrays.asList(absenceDay);
        List<AbsenceDayInfoDTO> infoDTOs = Arrays.asList(infoDTO);
        
        when(absenceDayService.filter(filterDTO)).thenReturn(absenceDays);
        when(absenceDayMapper.toInfoDTOList(absenceDays)).thenReturn(infoDTOs);

        // Act
        ResponseEntity<List<AbsenceDayInfoDTO>> response = 
            absenceDayControllerV2.filter(filterDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(infoDTO, response.getBody().get(0));
        
        verify(absenceDayService).filter(filterDTO);
        verify(absenceDayMapper).toInfoDTOList(absenceDays);
    }

    @Test
    void getByIdWithRelations_ShouldReturnAbsenceDayDetailDTO_WhenFound() {
        // Arrange
        when(absenceDayService.findById(ABSENCE_DAY_ID)).thenReturn(Optional.of(absenceDay));
        when(absenceDayMapper.toDetailDTO(absenceDay)).thenReturn(detailDTO);

        // Act
        ResponseEntity<AbsenceDayDetailDTO> response = 
            absenceDayControllerV2.getByIdWithRelations(ABSENCE_DAY_ID);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(detailDTO, response.getBody());
        
        verify(absenceDayService).findById(ABSENCE_DAY_ID);
        verify(absenceDayMapper).toDetailDTO(absenceDay);
    }

    @Test
    void getByIdWithRelations_ShouldReturnNotFound_WhenNotFound() {
        // Arrange
        when(absenceDayService.findById(ABSENCE_DAY_ID)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<AbsenceDayDetailDTO> response = 
            absenceDayControllerV2.getByIdWithRelations(ABSENCE_DAY_ID);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        
        verify(absenceDayService).findById(ABSENCE_DAY_ID);
        verify(absenceDayMapper, never()).toDetailDTO(any());
    }

    @Test
    void create_ShouldPropagateIllegalArgumentException() {
        // Arrange
        when(absenceDayService.createFromDTO(requestDTO))
            .thenThrow(new IllegalArgumentException("Invalid data"));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class, 
            () -> absenceDayControllerV2.create(requestDTO)
        );
        
        assertEquals("Invalid data", exception.getMessage());
        
        verify(absenceDayService).createFromDTO(requestDTO);
        verify(absenceDayMapper, never()).toDetailDTO(any());
    }

    @Test
    void update_ShouldPropagateEntityNotFoundException() {
        // Arrange
        when(absenceDayService.updateFromDTO(ABSENCE_DAY_ID, requestDTO))
            .thenThrow(new jakarta.persistence.EntityNotFoundException("Not found"));

        // Act & Assert
        assertThrows(jakarta.persistence.EntityNotFoundException.class, () -> 
            absenceDayControllerV2.update(ABSENCE_DAY_ID, requestDTO));
        
        verify(absenceDayService).updateFromDTO(ABSENCE_DAY_ID, requestDTO);
    }

    @Test
    void delete_ShouldPropagateEntityNotFoundException() {
        // Arrange
        doThrow(new jakarta.persistence.EntityNotFoundException("Not found"))
            .when(absenceDayService).delete(ABSENCE_DAY_ID);

        // Act & Assert
        assertThrows(jakarta.persistence.EntityNotFoundException.class, () -> 
            absenceDayControllerV2.delete(ABSENCE_DAY_ID));
        
        verify(absenceDayService).delete(ABSENCE_DAY_ID);
    }
}