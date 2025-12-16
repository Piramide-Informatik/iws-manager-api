package com.iws_manager.iws_manager_api.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.BasicContract;
import com.iws_manager.iws_manager_api.models.ContractOrderCommission;
import com.iws_manager.iws_manager_api.repositories.ContractOrderCommissionRepository;
import com.iws_manager.iws_manager_api.repositories.OrderCommissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iws_manager.iws_manager_api.models.Order;
import com.iws_manager.iws_manager_api.repositories.OrderRepository;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    private static final Long ORDER_1_ID = 1L;
    private static final Long ORDER_2_ID = 2L;
    private static final String ACRONYM_1 = "ACR-001";
    private static final String ACRONYM_2 = "ACR-002";
    private static final Integer ORDER_NO_1 = 1001;
    private static final Integer ORDER_NO_2 = 1002;
    private static final BigDecimal ORDER_VALUE_1 = new BigDecimal("5000.00");
    private static final BigDecimal ORDER_VALUE_2 = new BigDecimal("10000.00");
    private static final LocalDate APPROVAL_DATE_1 = LocalDate.of(2023, 10, 15);
    private static final String CONTRACT_DATA_1 = "DATA-1";
    private static final String CONTRACT_DATA_2 = "DATA-2";
    private static final String CONTRACT_DATA_A = "DATA-A";
    private static final String CONTRACT_DATA_B = "DATA-B";
    private static final BigDecimal FIX_COMMISSION_1 = new BigDecimal("100.00");
    private static final BigDecimal MAX_COMMISSION_1 = new BigDecimal("200.00");
    private static final String ORDER_TITLE_1 = "Orden 1";
    private static final String ORDER_TITLE_2 = "Orden 2";
    private static final String ORDER_LABEL_1 = "ETIQUETA-1";
    private static final LocalDate SIGNATURE_DATE_1 = LocalDate.of(2023, 10, 1);

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderCommissionRepository orderCommissionRepository;

    @Mock
    private ContractOrderCommissionRepository contractOrderCommissionRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order1;
    private Order order2;
    private ContractOrderCommission contractCommission;
    private BasicContract basicContract;

    @BeforeEach
    void setUp() {
        basicContract = new BasicContract();
        basicContract.setId(1L);

        order1 = new Order();
        order1.setId(ORDER_1_ID);
        order1.setAcronym(ACRONYM_1);
        order1.setOrderNo(ORDER_NO_1);
        order1.setOrderValue(ORDER_VALUE_1);
        order1.setApprovalDate(APPROVAL_DATE_1);
        order1.setContractData1(CONTRACT_DATA_1);
        order1.setContractData2(CONTRACT_DATA_A);
        order1.setFixCommission(FIX_COMMISSION_1);
        order1.setMaxCommission(MAX_COMMISSION_1);
        order1.setOrderTitle(ORDER_TITLE_1);
        order1.setOrderLabel(ORDER_LABEL_1);
        order1.setSignatureDate(SIGNATURE_DATE_1);
        order1.setBasiccontract(basicContract);

        order2 = new Order();
        order2.setId(ORDER_2_ID);
        order2.setAcronym(ACRONYM_2);
        order2.setOrderNo(ORDER_NO_2);
        order2.setOrderValue(ORDER_VALUE_2);
        order2.setApprovalDate(null);
        order2.setContractData1(CONTRACT_DATA_2);
        order2.setContractData2(CONTRACT_DATA_B);
        order2.setOrderTitle(ORDER_TITLE_2);
        contractCommission = new ContractOrderCommission();
        contractCommission.setId(1L);
        contractCommission.setCommission(new BigDecimal("100.00"));
        contractCommission.setFromOrderValue(new BigDecimal("1000.00"));
        contractCommission.setMinCommission(new BigDecimal("50.00"));
        contractCommission.setBasicContract(basicContract);
    }

    /* **********************
     * CRUD TESTING
     * **********************/
    @Test
    void createShouldSaveOrder() {
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(orderCommissionRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        Order result = orderService.create(order1);

        verify(orderRepository).save(order1);
        verify(orderCommissionRepository).saveAll(anyList());
        assertNotNull(result);
        assertEquals(ORDER_1_ID, result.getId());
    }

    @Test
    void findByIdShouldReturnOrder() {
        when(orderRepository.findById(ORDER_1_ID)).thenReturn(Optional.of(order1));
        Optional<Order> result = orderService.findById(ORDER_1_ID);
        assertTrue(result.isPresent());
    }

    @Test
    void findAllShouldReturnAllOrders() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));
        List<Order> result = orderService.findAll();
        assertEquals(2, result.size());
    }

    @Test
    void updateShouldUpdateOrder() {
        when(orderRepository.findById(ORDER_1_ID)).thenReturn(Optional.of(order1));
        when(orderRepository.save(any(Order.class))).thenReturn(order1);
        Order updatedOrder = orderService.update(ORDER_1_ID, order1);
        assertEquals(ORDER_TITLE_1, updatedOrder.getOrderTitle());
    }

    @Test
    void deleteShouldInvokeDelete() {
        when(orderRepository.existsById(ORDER_1_ID)).thenReturn(true);
        orderService.delete(ORDER_1_ID);
        verify(orderRepository, times(1)).deleteById(ORDER_1_ID);
    }

    /* **********************
     * PROPERTIES
     * **********************/
    @Test
    void getByAcronymShouldReturnOrders() {
        when(orderRepository.findByAcronym(ACRONYM_1)).thenReturn(Collections.singletonList(order1));
        List<Order> result = orderService.getByAcronym(ACRONYM_1);
        assertEquals(1, result.size());
    }

    @Test
    void getByContractData1ShouldReturnOrders() {
        when(orderRepository.findByContractData1(CONTRACT_DATA_1)).thenReturn(Collections.singletonList(order1));
        List<Order> result = orderService.getByContractData1(CONTRACT_DATA_1);
        assertEquals(CONTRACT_DATA_1, result.get(0).getContractData1());
    }

    @Test
    void getByContractData2ShouldReturnOrders() {
        when(orderRepository.findByContractData2(CONTRACT_DATA_A)).thenReturn(Collections.singletonList(order1));
        List<Order> result = orderService.getByContractData2(CONTRACT_DATA_A);
        assertEquals(CONTRACT_DATA_A, result.get(0).getContractData2());
    }

    @Test
    void getByFixCommissionShouldReturnOrders() {
        when(orderRepository.findByFixCommission(FIX_COMMISSION_1))
            .thenReturn(Collections.singletonList(order1));
        List<Order> result = orderService.getByFixCommission(FIX_COMMISSION_1);
        assertEquals(0, FIX_COMMISSION_1.compareTo(result.get(0).getFixCommission()));
    }

    @Test
    void getByMaxCommissionShouldReturnOrders() {
        when(orderRepository.findByMaxCommission(MAX_COMMISSION_1))
            .thenReturn(Collections.singletonList(order1));
        List<Order> result = orderService.getByMaxCommission(MAX_COMMISSION_1);
        assertEquals(0, MAX_COMMISSION_1.compareTo(result.get(0).getMaxCommission()));
    }

    @Test
    void getByOrderTitleShouldReturnOrders() {
        when(orderRepository.findByOrderTitle(ORDER_TITLE_1)).thenReturn(Collections.singletonList(order1));
        List<Order> result = orderService.getByOrderTitle(ORDER_TITLE_1);
        assertEquals(ORDER_TITLE_1, result.get(0).getOrderTitle());
    }

    @Test
    void getByOrderLabelShouldReturnOrders() {
        when(orderRepository.findByOrderLabel(ORDER_LABEL_1)).thenReturn(Collections.singletonList(order1));
        List<Order> result = orderService.getByOrderLabel(ORDER_LABEL_1);
        assertEquals(ORDER_LABEL_1, result.get(0).getOrderLabel());
    }

    @Test
    void getBySignatureDateShouldReturnOrders() {
        when(orderRepository.findBySignatureDate(SIGNATURE_DATE_1)).thenReturn(Collections.singletonList(order1));
        List<Order> result = orderService.getBySignatureDate(SIGNATURE_DATE_1);
        assertEquals(SIGNATURE_DATE_1, result.get(0).getSignatureDate());
    }

    /* **********************
     * TESTS FOR SPECIAL FUNCTIONS (RANGE, NULL CHECKS)
     * **********************/
    @Test
    void getByOrderValueBetweenShouldReturnOrders() {
        BigDecimal start = new BigDecimal("4000.00");
        BigDecimal end = new BigDecimal("6000.00");
        when(orderRepository.findByOrderValueBetween(start, end))
            .thenReturn(Collections.singletonList(order1));
        List<Order> result = orderService.getByOrderValueBetween(start, end);
        assertEquals(1, result.size());
    }

    @Test
    void getByApprovalDateBetweenShouldReturnOrders() {
        LocalDate start = LocalDate.of(2023, 10, 1);
        LocalDate end = LocalDate.of(2023, 10, 31);
        when(orderRepository.findByApprovalDateBetween(start, end)).thenReturn(Collections.singletonList(order1));
        List<Order> result = orderService.getByApprovalDateBetween(start, end);
        assertEquals(1, result.size());
    }

    @Test
    void getByApprovalDateIsNullShouldReturnOrders() {
        when(orderRepository.findByApprovalDateIsNull()).thenReturn(Collections.singletonList(order2));
        List<Order> result = orderService.getByApprovalDateIsNull();
        assertNull(result.get(0).getApprovalDate());
    }

    @Test
    void getByApprovalDateIsNotNullShouldReturnOrders() {
        when(orderRepository.findByApprovalDateIsNotNull()).thenReturn(Collections.singletonList(order1));
        List<Order> result = orderService.getByApprovalDateIsNotNull();
        assertNotNull(result.get(0).getApprovalDate());
    }

    @Test
    void getByCustomerIdOrderByOrderTitleAscShouldReturnSortedOrders() {
        Order orderA = new Order();
        orderA.setOrderTitle("A - Orden");
        Order orderB = new Order();
        orderB.setOrderTitle("B - Orden");
        
        when(orderRepository.findByCustomerIdOrderByOrderTitleAsc(1L))
            .thenReturn(Arrays.asList(orderA, orderB));
        
        List<Order> result = orderService.getByCustomerIdOrderByOrderTitleAsc(1L);
        
        assertEquals(2, result.size());
        assertEquals("A - Orden", result.get(0).getOrderTitle());
        assertEquals("B - Orden", result.get(1).getOrderTitle());
    }

    @Test
    void getByCustomerIdOrderByOrderLabelAscShouldReturnSortedOrders() {
        Order orderX = new Order();
        orderX.setOrderLabel("X - Etiqueta");
        Order orderY = new Order();
        orderY.setOrderLabel("Y - Etiqueta");
        
        when(orderRepository.findByCustomerIdOrderByOrderLabelAsc(1L))
            .thenReturn(Arrays.asList(orderX, orderY));
        
        List<Order> result = orderService.getByCustomerIdOrderByOrderLabelAsc(1L);
        
        assertEquals(2, result.size());
        assertEquals("X - Etiqueta", result.get(0).getOrderLabel());
        assertEquals("Y - Etiqueta", result.get(1).getOrderLabel());
    }

    @Test
    void getByCustomerIdOrderByShouldThrowExceptionWhenCustomerIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> 
            orderService.getByCustomerIdOrderByOrderTitleAsc(null));
        assertThrows(IllegalArgumentException.class, () -> 
            orderService.getByCustomerIdOrderByOrderLabelAsc(null));
    }
}