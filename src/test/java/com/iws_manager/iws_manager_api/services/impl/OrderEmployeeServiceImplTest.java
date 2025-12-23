package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.dtos.orderemployee.OrderEmployeeRequestDTO;
import com.iws_manager.iws_manager_api.dtos.orderemployee.OrderEmployeeResponseDTO;
import com.iws_manager.iws_manager_api.dtos.shared.BasicReferenceDTO;
import com.iws_manager.iws_manager_api.dtos.shared.EmployeeBasicDTO;
import com.iws_manager.iws_manager_api.dtos.shared.OrderReferenceDTO;
import com.iws_manager.iws_manager_api.dtos.shared.QualificationFZReferenceDTO;
import com.iws_manager.iws_manager_api.mappers.OrderEmployeeMapper;
import com.iws_manager.iws_manager_api.models.*;
import com.iws_manager.iws_manager_api.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
    private static final String HOURLY_RATE_50 = "50.00";
    private static final String HOURLY_RATE_40 = "40.00";
    private static final Integer ORDER_EMPLOYEE_NO = 1001;
    private static final Integer VERSION = 1;
    private static final Integer EMPLOYEE_NO = 10001;
    private static final Integer ORDER_NO = 20001;
    private static final String EMPLOYEE_LABEL = "John Doe (10001)";
    private static final String ORDER_LABEL = "Order Label";
    private static final String ORDER_TITLE = "Order Title";
    private static final String ACRONYM = "ACRO";

    @Mock
    private OrderEmployeeRepository orderEmployeeRepository;

    @Mock
    private OrderEmployeeMapper orderEmployeeMapper;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private QualificationFZRepository qualificationFZRepository;

    @InjectMocks
    private OrderEmployeeServiceV2Impl orderEmployeeService;

    private OrderEmployee sampleOrderEmployee;
    private OrderEmployeeRequestDTO sampleRequestDTO;
    private OrderEmployeeResponseDTO sampleResponseDTO;
    private Employee sampleEmployee;
    private Order sampleOrder;
    private QualificationFZ sampleQualificationFZ;
    private BasicReferenceDTO employeeRefDTO;
    private BasicReferenceDTO orderRefDTO;
    private BasicReferenceDTO qualificationRefDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Crear entidades
        sampleEmployee = new Employee();
        sampleEmployee.setId(EMPLOYEE_ID);
        sampleEmployee.setFirstname("John");
        sampleEmployee.setLastname("Doe");
        sampleEmployee.setEmployeeno(EMPLOYEE_NO);
        sampleEmployee.setLabel("John Doe");
        
        sampleOrder = new Order();
        sampleOrder.setId(ORDER_ID);
        sampleOrder.setOrderNo(ORDER_NO);
        sampleOrder.setAcronym(ACRONYM);
        sampleOrder.setOrderLabel(ORDER_LABEL);
        sampleOrder.setOrderTitle(ORDER_TITLE);
        
        sampleQualificationFZ = new QualificationFZ();
        sampleQualificationFZ.setId(QUALIFICATION_FZ_ID);
        sampleQualificationFZ.setQualification("Senior Developer");
        
        // Crear entidad OrderEmployee para los mocks del repository
        sampleOrderEmployee = new OrderEmployee();
        sampleOrderEmployee.setId(1L);
        sampleOrderEmployee.setOrderemployeeno(ORDER_EMPLOYEE_NO);
        sampleOrderEmployee.setQualificationkmui(QUALIFICATION_K_MUI);
        sampleOrderEmployee.setTitle(TITLE);
        sampleOrderEmployee.setHourlyrate(HOURLY_RATE);
        sampleOrderEmployee.setPlannedhours(PLANNED_HOURS);
        sampleOrderEmployee.setEmployee(sampleEmployee);
        sampleOrderEmployee.setOrder(sampleOrder);
        sampleOrderEmployee.setQualificationFZ(sampleQualificationFZ);
        sampleOrderEmployee.setVersion(VERSION);
        
        // Crear DTOs de referencia - CORREGIDO: BasicReferenceDTO(id, version)
        employeeRefDTO = new BasicReferenceDTO(EMPLOYEE_ID, VERSION);
        orderRefDTO = new BasicReferenceDTO(ORDER_ID, VERSION);
        qualificationRefDTO = new BasicReferenceDTO(QUALIFICATION_FZ_ID, VERSION);
        
        // Crear RequestDTO
        sampleRequestDTO = new OrderEmployeeRequestDTO(
            HOURLY_RATE,
            PLANNED_HOURS,
            QUALIFICATION_K_MUI,
            TITLE,
            ORDER_EMPLOYEE_NO,
            orderRefDTO,
            qualificationRefDTO,
            employeeRefDTO
        );
        
        // Crear ResponseDTO
        // Necesito ver EmployeeBasicDTO para saber su constructor
        // Asumo que es (Long id, Integer employeeno, String firstname, String lastname, String label, Integer version)
        EmployeeBasicDTO employeeBasicDTO = new EmployeeBasicDTO(
            EMPLOYEE_ID, 
            EMPLOYEE_NO,
            "John", 
            "Doe", 
            EMPLOYEE_LABEL,
            VERSION
        );
        
        // Necesito ver OrderReferenceDTO para saber su constructor
        // Asumo que es (Long id, String acronym, String orderLabel, Integer orderNo, String orderTitle, ProjectReferenceDTO project, Integer version)
        OrderReferenceDTO orderReferenceDTO = new OrderReferenceDTO(
            ORDER_ID,
            ACRONYM,
            ORDER_LABEL,
            ORDER_NO,
            ORDER_TITLE,
            null, // project es null en este ejemplo
            VERSION
        );
        
        QualificationFZReferenceDTO qualificationRefResponseDTO = new QualificationFZReferenceDTO(
            QUALIFICATION_FZ_ID,
            "Senior Developer"
        );
        
        sampleResponseDTO = new OrderEmployeeResponseDTO(
            1L,
            ORDER_EMPLOYEE_NO,
            HOURLY_RATE,
            PLANNED_HOURS,
            QUALIFICATION_K_MUI,
            TITLE,
            VERSION,
            employeeBasicDTO,
            orderReferenceDTO,
            qualificationRefResponseDTO
        );
    }

    // ========== BASIC CRUD ==========

    @Test
    void testCreate() {
        when(orderEmployeeMapper.toEntity(sampleRequestDTO)).thenReturn(sampleOrderEmployee);
        when(orderEmployeeRepository.save(any(OrderEmployee.class))).thenReturn(sampleOrderEmployee);
        when(orderEmployeeMapper.toResponseDTO(sampleOrderEmployee)).thenReturn(sampleResponseDTO);

        OrderEmployeeResponseDTO created = orderEmployeeService.create(sampleRequestDTO);
        
        assertNotNull(created);
        assertEquals(QUALIFICATION_K_MUI, created.qualificationkmui());
        assertEquals(TITLE, created.title());
        assertEquals(HOURLY_RATE, created.hourlyrate());
        assertEquals(PLANNED_HOURS, created.plannedhours());
        verify(orderEmployeeRepository, times(1)).save(sampleOrderEmployee);
        verify(orderEmployeeMapper, times(1)).toEntity(sampleRequestDTO);
        verify(orderEmployeeMapper, times(1)).toResponseDTO(sampleOrderEmployee);
    }

    @Test
    void testCreateThrowsIllegalArgumentExceptionWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> orderEmployeeService.create(null));
        verify(orderEmployeeRepository, never()).save(any(OrderEmployee.class));
    }

    @Test
    void testCreateThrowsIllegalArgumentExceptionWhenHourlyRateNull() {
        OrderEmployeeRequestDTO invalidRequest = new OrderEmployeeRequestDTO(
            null, // hourlyrate null
            PLANNED_HOURS,
            QUALIFICATION_K_MUI,
            TITLE,
            ORDER_EMPLOYEE_NO,
            orderRefDTO,
            qualificationRefDTO,
            employeeRefDTO
        );
        
        assertThrows(IllegalArgumentException.class, () -> orderEmployeeService.create(invalidRequest));
        verify(orderEmployeeRepository, never()).save(any(OrderEmployee.class));
    }

    @Test
    void testCreateThrowsIllegalArgumentExceptionWhenOrderNull() {
        OrderEmployeeRequestDTO invalidRequest = new OrderEmployeeRequestDTO(
            HOURLY_RATE,
            PLANNED_HOURS,
            QUALIFICATION_K_MUI,
            TITLE,
            ORDER_EMPLOYEE_NO,
            null, // order null
            qualificationRefDTO,
            employeeRefDTO
        );
        
        assertThrows(IllegalArgumentException.class, () -> orderEmployeeService.create(invalidRequest));
        verify(orderEmployeeRepository, never()).save(any(OrderEmployee.class));
    }

    @Test
    void testGetById() {
        when(orderEmployeeRepository.findById(1L)).thenReturn(Optional.of(sampleOrderEmployee));
        when(orderEmployeeMapper.toResponseDTO(sampleOrderEmployee)).thenReturn(sampleResponseDTO);

        OrderEmployeeResponseDTO result = orderEmployeeService.getById(1L);
        
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(QUALIFICATION_K_MUI, result.qualificationkmui());
        assertEquals(TITLE, result.title());
        verify(orderEmployeeRepository, times(1)).findById(1L);
        verify(orderEmployeeMapper, times(1)).toResponseDTO(sampleOrderEmployee);
    }

    @Test
    void testGetByIdThrowsEntityNotFoundExceptionWhenNotFound() {
        when(orderEmployeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderEmployeeService.getById(1L));
        verify(orderEmployeeRepository, times(1)).findById(1L);
        verify(orderEmployeeMapper, never()).toResponseDTO(any());
    }

    @Test
    void testGetAll() {
        List<OrderEmployee> orderEmployees = List.of(sampleOrderEmployee);
        when(orderEmployeeRepository.findAllByOrderByIdAsc()).thenReturn(orderEmployees);
        when(orderEmployeeMapper.toResponseDTOList(orderEmployees)).thenReturn(List.of(sampleResponseDTO));

        List<OrderEmployeeResponseDTO> result = orderEmployeeService.getAll();

        assertEquals(1, result.size());
        assertEquals(sampleResponseDTO.id(), result.get(0).id());
        assertEquals(sampleResponseDTO.qualificationkmui(), result.get(0).qualificationkmui());
        verify(orderEmployeeRepository, times(1)).findAllByOrderByIdAsc();
        verify(orderEmployeeMapper, times(1)).toResponseDTOList(orderEmployees);
    }

    @Test
    void testUpdate() {
        OrderEmployeeRequestDTO updatedRequest = new OrderEmployeeRequestDTO(
            new BigDecimal(HOURLY_RATE_50),
            new BigDecimal("180.00"),
            "Updated Qualification",
            "Updated Title",
            1002,
            orderRefDTO,
            qualificationRefDTO,
            employeeRefDTO
        );

        OrderEmployee updatedEntity = new OrderEmployee();
        updatedEntity.setId(1L);
        updatedEntity.setQualificationkmui("Updated Qualification");
        updatedEntity.setTitle("Updated Title");
        updatedEntity.setHourlyrate(new BigDecimal(HOURLY_RATE_50));
        updatedEntity.setPlannedhours(new BigDecimal("180.00"));
        updatedEntity.setOrderemployeeno(1002);

        OrderEmployeeResponseDTO updatedResponse = new OrderEmployeeResponseDTO(
            1L,
            1002,
            new BigDecimal(HOURLY_RATE_50),
            new BigDecimal("180.00"),
            "Updated Qualification",
            "Updated Title",
            VERSION + 1,
            sampleResponseDTO.employee(),
            sampleResponseDTO.order(),
            sampleResponseDTO.qualificationFZ()
        );

        when(orderEmployeeRepository.findById(1L)).thenReturn(Optional.of(sampleOrderEmployee));
        doNothing().when(orderEmployeeMapper).updateEntityFromDTO(sampleOrderEmployee, updatedRequest);
        when(orderEmployeeRepository.save(sampleOrderEmployee)).thenReturn(updatedEntity);
        when(orderEmployeeMapper.toResponseDTO(updatedEntity)).thenReturn(updatedResponse);

        OrderEmployeeResponseDTO result = orderEmployeeService.update(1L, updatedRequest);
        
        assertNotNull(result);
        assertEquals("Updated Qualification", result.qualificationkmui());
        assertEquals("Updated Title", result.title());
        assertEquals(new BigDecimal(HOURLY_RATE_50), result.hourlyrate());
        assertEquals(new BigDecimal("180.00"), result.plannedhours());
        verify(orderEmployeeRepository, times(1)).findById(1L);
        verify(orderEmployeeMapper, times(1)).updateEntityFromDTO(sampleOrderEmployee, updatedRequest);
        verify(orderEmployeeRepository, times(1)).save(sampleOrderEmployee);
    }

    @Test
    void testUpdateThrowsEntityNotFoundExceptionWhenIdNull() {
        assertThrows(EntityNotFoundException.class, () -> orderEmployeeService.update(null, sampleRequestDTO));
        verify(orderEmployeeRepository, never()).save(any(OrderEmployee.class));
    }

    @Test
    void testUpdateThrowsIllegalArgumentExceptionWhenOrderEmployeeNull() {
        assertThrows(IllegalArgumentException.class, () -> orderEmployeeService.update(1L, null));
        verify(orderEmployeeRepository, never()).findById(anyLong());
        verify(orderEmployeeRepository, never()).save(any(OrderEmployee.class));
    }

    @Test
    void testUpdateThrowsIllegalArgumentExceptionWhenNoDataProvided() {
        OrderEmployeeRequestDTO emptyRequest = new OrderEmployeeRequestDTO(
            null, null, null, null, null, null, null, null
        );
        
        assertThrows(IllegalArgumentException.class, () -> orderEmployeeService.update(1L, emptyRequest));
        
        verify(orderEmployeeRepository, never()).findById(anyLong());
        verify(orderEmployeeRepository, never()).save(any(OrderEmployee.class));
    }

    @Test
    void testUpdateThrowsEntityNotFoundExceptionWhenNotFound() {
        when(orderEmployeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderEmployeeService.update(1L, sampleRequestDTO));
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
    void testDeleteThrowsEntityNotFoundExceptionWhenNotFound() {
        when(orderEmployeeRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> orderEmployeeService.delete(1L));
        verify(orderEmployeeRepository, times(1)).existsById(1L);
        verify(orderEmployeeRepository, never()).deleteById(anyLong());
    }

    // ========== GET BY EMPLOYEE ==========

    @Test
    void testGetByEmployeeId() {
        List<OrderEmployee> entities = List.of(sampleOrderEmployee);
        when(orderEmployeeRepository.findByEmployeeId(EMPLOYEE_ID)).thenReturn(entities);
        when(orderEmployeeMapper.toResponseDTOList(entities)).thenReturn(List.of(sampleResponseDTO));

        List<OrderEmployeeResponseDTO> result = orderEmployeeService.getByEmployeeId(EMPLOYEE_ID);
        
        assertEquals(1, result.size());
        assertEquals(sampleResponseDTO.id(), result.get(0).id());
        verify(orderEmployeeRepository, times(1)).findByEmployeeId(EMPLOYEE_ID);
        verify(orderEmployeeMapper, times(1)).toResponseDTOList(entities);
    }

    @Test
    void testGetByEmployeeIdThrowsIllegalArgumentExceptionWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> orderEmployeeService.getByEmployeeId(null));
        verify(orderEmployeeRepository, never()).findByEmployeeId(anyLong());
    }

    @Test
    void testGetByEmployeeIdOrderByIdAsc() {
        List<OrderEmployee> entities = List.of(sampleOrderEmployee);
        when(orderEmployeeRepository.findByEmployeeIdOrderByIdAsc(EMPLOYEE_ID)).thenReturn(entities);
        when(orderEmployeeMapper.toResponseDTOList(entities)).thenReturn(List.of(sampleResponseDTO));

        List<OrderEmployeeResponseDTO> result = orderEmployeeService.getByEmployeeIdOrderByIdAsc(EMPLOYEE_ID);
        
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByEmployeeIdOrderByIdAsc(EMPLOYEE_ID);
    }

    // ========== GET BY ORDER ==========

    @Test
    void testGetByOrderId() {
        List<OrderEmployee> entities = List.of(sampleOrderEmployee);
        when(orderEmployeeRepository.findByOrderId(ORDER_ID)).thenReturn(entities);
        when(orderEmployeeMapper.toResponseDTOList(entities)).thenReturn(List.of(sampleResponseDTO));

        List<OrderEmployeeResponseDTO> result = orderEmployeeService.getByOrderId(ORDER_ID);
        
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByOrderId(ORDER_ID);
    }

    @Test
    void testGetByOrderIdThrowsIllegalArgumentExceptionWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> orderEmployeeService.getByOrderId(null));
        verify(orderEmployeeRepository, never()).findByOrderId(anyLong());
    }

    // ========== CALCULATION OPERATIONS ==========

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

    // ========== EXISTENCE OPERATIONS ==========

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

    // ========== COUNT OPERATIONS ==========

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

    // ========== PARTIAL UPDATE ==========

    @Test
    void testPartialUpdate() {
        OrderEmployeeRequestDTO partialRequest = new OrderEmployeeRequestDTO(
            new BigDecimal(HOURLY_RATE_50),
            null,
            "Updated Qualification",
            null,
            null,
            null,
            null,
            null
        );

        OrderEmployee updatedEntity = new OrderEmployee();
        updatedEntity.setId(1L);
        updatedEntity.setQualificationkmui("Updated Qualification");
        updatedEntity.setHourlyrate(new BigDecimal(HOURLY_RATE_50));
        updatedEntity.setPlannedhours(PLANNED_HOURS);
        updatedEntity.setTitle(TITLE);
        updatedEntity.setOrderemployeeno(ORDER_EMPLOYEE_NO);
        updatedEntity.setEmployee(sampleEmployee);
        updatedEntity.setOrder(sampleOrder);
        updatedEntity.setQualificationFZ(sampleQualificationFZ);
        updatedEntity.setVersion(VERSION + 1);

        OrderEmployeeResponseDTO updatedResponse = new OrderEmployeeResponseDTO(
            1L,
            ORDER_EMPLOYEE_NO,
            new BigDecimal(HOURLY_RATE_50),
            PLANNED_HOURS,
            "Updated Qualification",
            TITLE,
            VERSION + 1,
            sampleResponseDTO.employee(),
            sampleResponseDTO.order(),
            sampleResponseDTO.qualificationFZ()
        );

        when(orderEmployeeRepository.findById(1L)).thenReturn(Optional.of(sampleOrderEmployee));
        when(orderEmployeeRepository.save(sampleOrderEmployee)).thenReturn(updatedEntity);
        when(orderEmployeeMapper.toResponseDTO(updatedEntity)).thenReturn(updatedResponse);

        OrderEmployeeResponseDTO result = orderEmployeeService.partialUpdate(1L, partialRequest);
        
        assertNotNull(result);
        assertEquals("Updated Qualification", result.qualificationkmui());
        assertEquals(new BigDecimal(HOURLY_RATE_50), result.hourlyrate());
        assertEquals(PLANNED_HOURS, result.plannedhours());
        assertEquals(TITLE, result.title());
        verify(orderEmployeeRepository, times(1)).findById(1L);
        verify(orderEmployeeRepository, times(1)).save(sampleOrderEmployee);
    }

    @Test
    void testPartialUpdateThrowsEntityNotFoundExceptionWhenNotFound() {
        when(orderEmployeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderEmployeeService.partialUpdate(1L, sampleRequestDTO));
        verify(orderEmployeeRepository, times(1)).findById(1L);
        verify(orderEmployeeMapper, never()).applyPartialUpdate(any(), any());
        verify(orderEmployeeRepository, never()).save(any(OrderEmployee.class));
    }

    // ========== GET BY QUALIFICATION KMUI ==========

    @Test
    void testGetByQualificationkmui() {
        List<OrderEmployee> entities = List.of(sampleOrderEmployee);
        when(orderEmployeeRepository.findByQualificationkmui(QUALIFICATION_K_MUI)).thenReturn(entities);
        when(orderEmployeeMapper.toResponseDTOList(entities)).thenReturn(List.of(sampleResponseDTO));

        List<OrderEmployeeResponseDTO> result = orderEmployeeService.getByQualificationkmui(QUALIFICATION_K_MUI);
        
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByQualificationkmui(QUALIFICATION_K_MUI);
    }

    @Test
    void testGetByQualificationkmuiContainingIgnoreCase() {
        List<OrderEmployee> entities = List.of(sampleOrderEmployee);
        when(orderEmployeeRepository.findByQualificationkmuiContainingIgnoreCase(KEYWORD)).thenReturn(entities);
        when(orderEmployeeMapper.toResponseDTOList(entities)).thenReturn(List.of(sampleResponseDTO));

        List<OrderEmployeeResponseDTO> result = orderEmployeeService.getByQualificationkmuiContainingIgnoreCase(KEYWORD);
        
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByQualificationkmuiContainingIgnoreCase(KEYWORD);
    }

    // ========== GET BY TITLE ==========

    @Test
    void testGetByTitle() {
        List<OrderEmployee> entities = List.of(sampleOrderEmployee);
        when(orderEmployeeRepository.findByTitle(TITLE)).thenReturn(entities);
        when(orderEmployeeMapper.toResponseDTOList(entities)).thenReturn(List.of(sampleResponseDTO));

        List<OrderEmployeeResponseDTO> result = orderEmployeeService.getByTitle(TITLE);
        
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByTitle(TITLE);
    }

    // ========== RANGE QUERIES ==========

    @Test
    void testGetByHourlyrateBetween() {
        BigDecimal minRate = new BigDecimal(HOURLY_RATE_40);
        BigDecimal maxRate = new BigDecimal(HOURLY_RATE_50);
        List<OrderEmployee> entities = List.of(sampleOrderEmployee);
        
        when(orderEmployeeRepository.findByHourlyrateBetween(minRate, maxRate)).thenReturn(entities);
        when(orderEmployeeMapper.toResponseDTOList(entities)).thenReturn(List.of(sampleResponseDTO));

        List<OrderEmployeeResponseDTO> result = orderEmployeeService.getByHourlyrateBetween(minRate, maxRate);
        
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByHourlyrateBetween(minRate, maxRate);
    }

    @Test
    void testGetByHourlyrateBetweenThrowsExceptionWhenMinGreaterThanMax() {
        BigDecimal minRate = new BigDecimal(HOURLY_RATE_50);
        BigDecimal maxRate = new BigDecimal(HOURLY_RATE_40);

        assertThrows(IllegalArgumentException.class,
                () -> orderEmployeeService.getByHourlyrateBetween(minRate, maxRate));
        verify(orderEmployeeRepository, never()).findByHourlyrateBetween(any(), any());
    }

    @Test
    void testGetByPlannedhoursBetween() {
        BigDecimal minHours = new BigDecimal("150.00");
        BigDecimal maxHours = new BigDecimal("170.00");
        List<OrderEmployee> entities = List.of(sampleOrderEmployee);
        
        when(orderEmployeeRepository.findByPlannedhoursBetween(minHours, maxHours)).thenReturn(entities);
        when(orderEmployeeMapper.toResponseDTOList(entities)).thenReturn(List.of(sampleResponseDTO));

        List<OrderEmployeeResponseDTO> result = orderEmployeeService.getByPlannedhoursBetween(minHours, maxHours);
        
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByPlannedhoursBetween(minHours, maxHours);
    }

    // ========== COMBINED CRITERIA ==========

    @Test
    void testGetByEmployeeIdAndOrderId() {
        List<OrderEmployee> entities = List.of(sampleOrderEmployee);
        when(orderEmployeeRepository.findByEmployeeIdAndOrderId(EMPLOYEE_ID, ORDER_ID)).thenReturn(entities);
        when(orderEmployeeMapper.toResponseDTOList(entities)).thenReturn(List.of(sampleResponseDTO));

        List<OrderEmployeeResponseDTO> result = orderEmployeeService.getByEmployeeIdAndOrderId(EMPLOYEE_ID, ORDER_ID);
        
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByEmployeeIdAndOrderId(EMPLOYEE_ID, ORDER_ID);
    }

    @Test
    void testGetByOrderIdAndQualificationFZId() {
        List<OrderEmployee> entities = List.of(sampleOrderEmployee);
        when(orderEmployeeRepository.findByOrderIdAndQualificationFZId(ORDER_ID, QUALIFICATION_FZ_ID)).thenReturn(entities);
        when(orderEmployeeMapper.toResponseDTOList(entities)).thenReturn(List.of(sampleResponseDTO));

        List<OrderEmployeeResponseDTO> result = orderEmployeeService.getByOrderIdAndQualificationFZId(ORDER_ID, QUALIFICATION_FZ_ID);
        
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findByOrderIdAndQualificationFZId(ORDER_ID, QUALIFICATION_FZ_ID);
    }

    // ========== ORDERING OPERATIONS ==========

    @Test
    void testGetAllOrderByHourlyrateAsc() {
        List<OrderEmployee> entities = List.of(sampleOrderEmployee);
        when(orderEmployeeRepository.findAllByOrderByHourlyrateAsc()).thenReturn(entities);
        when(orderEmployeeMapper.toResponseDTOList(entities)).thenReturn(List.of(sampleResponseDTO));

        List<OrderEmployeeResponseDTO> result = orderEmployeeService.getAllOrderByHourlyrateAsc();
        
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findAllByOrderByHourlyrateAsc();
    }

    @Test
    void testGetAllOrderByTitleAsc() {
        List<OrderEmployee> entities = List.of(sampleOrderEmployee);
        when(orderEmployeeRepository.findAllByOrderByTitleAsc()).thenReturn(entities);
        when(orderEmployeeMapper.toResponseDTOList(entities)).thenReturn(List.of(sampleResponseDTO));

        List<OrderEmployeeResponseDTO> result = orderEmployeeService.getAllOrderByTitleAsc();
        
        assertEquals(1, result.size());
        verify(orderEmployeeRepository, times(1)).findAllByOrderByTitleAsc();
    }

    // ========== VALIDATION TESTS ==========

    @Test
    void testValidateStringThrowsIllegalArgumentExceptionWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> 
            orderEmployeeService.getByQualificationkmui(null));
        verify(orderEmployeeRepository, never()).findByQualificationkmui(any());
    }

    @Test
    void testValidateBigDecimalThrowsIllegalArgumentExceptionWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> 
            orderEmployeeService.getByHourlyrateGreaterThan(null));
        verify(orderEmployeeRepository, never()).findByHourlyrateGreaterThan(any());
    }

    @Test
    void testGetByPlannedhoursBetweenThrowsExceptionWhenMinGreaterThanMax() {
        BigDecimal minHours = new BigDecimal("200.00");
        BigDecimal maxHours = new BigDecimal("100.00");

        assertThrows(IllegalArgumentException.class,
                () -> orderEmployeeService.getByPlannedhoursBetween(minHours, maxHours));
        verify(orderEmployeeRepository, never()).findByPlannedhoursBetween(any(), any());
    }
}