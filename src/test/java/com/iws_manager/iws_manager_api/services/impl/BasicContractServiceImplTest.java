package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.BasicContract;
import com.iws_manager.iws_manager_api.repositories.BasicContractRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BasicContractServiceImplTest {

    @Mock
    private BasicContractRepository basicContractRepository;

    @InjectMocks
    private BasicContractServiceImpl basicContractService;

    private BasicContract sampleBasicContract;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() throws Exception {
        closeable = MockitoAnnotations.openMocks(this);
        sampleBasicContract = new BasicContract();
        sampleBasicContract.setId(1L);
        sampleBasicContract.setContractNo(1001);
        sampleBasicContract.setContractLabel("CON-2023-001");
        sampleBasicContract.setContractTitle("Annual Maintenance");
        sampleBasicContract.setDate(LocalDate.now());
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testCreateValidBasicContract() {
        when(basicContractRepository.save(sampleBasicContract)).thenReturn(sampleBasicContract);
        BasicContract result = basicContractService.create(sampleBasicContract);
        assertEquals(sampleBasicContract, result);
        verify(basicContractRepository).save(sampleBasicContract);
    }

    @Test
    void testCreateNullBasicContractThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> basicContractService.create(null));
        verifyNoInteractions(basicContractRepository);
    }

    @Test
    void testFindByIdValid() {
        when(basicContractRepository.findById(1L)).thenReturn(Optional.of(sampleBasicContract));
        Optional<BasicContract> result = basicContractService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("Annual Maintenance", result.get().getContractTitle());
    }

    @Test
    void testFindByIdNotFound() {
        when(basicContractRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<BasicContract> result = basicContractService.findById(99L);
        assertFalse(result.isPresent());
    }

    @Test
    void testFindByIdNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> basicContractService.findById(null));
    }

    @Test
    void testFindAll() {
        List<BasicContract> expected = List.of(sampleBasicContract);
        when(basicContractRepository.findAll()).thenReturn(expected);
        List<BasicContract> result = basicContractService.findAll();
        assertEquals(expected.size(), result.size());
        assertEquals(expected.get(0), result.get(0));
    }

    @Test
    void testUpdateValid() {
        BasicContract updatedDetails = new BasicContract();
        updatedDetails.setContractTitle("Updated Contract");
        updatedDetails.setContractNo(1002);

        when(basicContractRepository.findById(1L)).thenReturn(Optional.of(sampleBasicContract));
        when(basicContractRepository.save(any(BasicContract.class))).thenAnswer(i -> i.getArgument(0));

        BasicContract result = basicContractService.update(1L, updatedDetails);
        assertEquals("Updated Contract", result.getContractTitle());
        assertEquals(1002, result.getContractNo());
        verify(basicContractRepository).save(sampleBasicContract);
    }

    @Test
    void testUpdateWithNullIdThrowsException() {
        assertThrows(IllegalArgumentException.class, 
            () -> basicContractService.update(null, sampleBasicContract));
    }

    @Test
    void testUpdateWithNullBasicContractThrowsException() {
        assertThrows(IllegalArgumentException.class, 
            () -> basicContractService.update(1L, null));
    }

    @Test
    void testUpdateNotFoundThrowsException() {
        when(basicContractRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, 
            () -> basicContractService.update(1L, sampleBasicContract));
    }

    @Test
    void testDeleteValid() {
        when(basicContractRepository.existsById(1L)).thenReturn(true);
        
        basicContractService.delete(1L);
        
        verify(basicContractRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> basicContractService.delete(null));
        verifyNoInteractions(basicContractRepository);
    }

    @Test
    void testGetByCustomerId() {
        List<BasicContract> expected = List.of(sampleBasicContract);
        when(basicContractRepository.findByCustomerId(1L)).thenReturn(expected);
        List<BasicContract> result = basicContractService.getByCustomerId(1L);
        assertEquals(expected, result);
    }

    @Test
    void testGetByCustomerIdOrderByContractNoAsc() {
        List<BasicContract> expected = List.of(sampleBasicContract);
        when(basicContractRepository.findByCustomerIdOrderByContractNoAsc(1L))
            .thenReturn(expected);
        List<BasicContract> result = basicContractService.getByCustomerIdOrderByContractNoAsc(1L);
        assertEquals(expected, result);
    }

    @Test
    void testGetByDateBetween() {
        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(1);
        List<BasicContract> expected = List.of(sampleBasicContract);
        
        when(basicContractRepository.findByDateBetween(startDate, endDate))
            .thenReturn(expected);
        
        List<BasicContract> result = basicContractService.getByDateBetween(startDate, endDate);
        assertEquals(expected, result);
    }

    @Test
    void getNextContractNoWhenMaxContractNoExistsShouldReturnMaxPlusOne() {
        // Arrange
        Integer maxContractNo = 100;
        when(basicContractRepository.findMaxContractNoOptional())
            .thenReturn(Optional.of(maxContractNo));

        // Act
        Integer result = basicContractService.getNextContractNo();

        // Assert
        assertEquals(101, result);
        verify(basicContractRepository, times(1)).findMaxContractNoOptional();
    }

    @Test
    void getNextContractNoWhenNoContractsExistShouldReturnOne() {
        // Arrange
        when(basicContractRepository.findMaxContractNoOptional())
            .thenReturn(Optional.empty());

        // Act
        Integer result = basicContractService.getNextContractNo();

        // Assert
        assertEquals(1, result);
        verify(basicContractRepository, times(1)).findMaxContractNoOptional();
    }

    @Test
    void getNextContractNoWhenMaxContractNoIsNullShouldReturnOne() {
        // Arrange
        when(basicContractRepository.findMaxContractNoOptional())
            .thenReturn(Optional.ofNullable(null));

        // Act
        Integer result = basicContractService.getNextContractNo();

        // Assert
        assertEquals(1, result);
        verify(basicContractRepository, times(1)).findMaxContractNoOptional();
    }

    @Test
    void getNextContractNoWhenMaxContractNoIsZeroShouldReturnOne() {
        // Arrange
        when(basicContractRepository.findMaxContractNoOptional())
            .thenReturn(Optional.of(0));

        // Act
        Integer result = basicContractService.getNextContractNo();

        // Assert
        assertEquals(1, result);
        verify(basicContractRepository, times(1)).findMaxContractNoOptional();
    }
}