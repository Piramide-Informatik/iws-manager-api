package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.Subcontract;
import com.iws_manager.iws_manager_api.models.SubcontractProject;
import com.iws_manager.iws_manager_api.repositories.SubcontractRepository;
import com.iws_manager.iws_manager_api.repositories.SubcontractProjectRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubcontractServiceImplTest {

    @Mock
    private SubcontractRepository subcontractRepository;

    @Mock
    private SubcontractProjectRepository subcontractProjectRepository;

    @InjectMocks
    private SubcontractServiceImpl subcontractService;

    private Subcontract sampleSubcontract;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        sampleSubcontract = new Subcontract();
        sampleSubcontract.setId(1L);
        sampleSubcontract.setContractTitle("Test Contract");
        sampleSubcontract.setInvoiceAmount(new BigDecimal("1000.00"));
        sampleSubcontract.setDate(LocalDate.now());
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testCreateValidSubcontract() {
        when(subcontractRepository.save(sampleSubcontract)).thenReturn(sampleSubcontract);
        Subcontract result = subcontractService.create(sampleSubcontract);
        assertEquals(sampleSubcontract, result);
    }

    @Test
    void testCreateNullSubcontractThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> subcontractService.create(null));
    }

    @Test
    void testFindByIdValid() {
        when(subcontractRepository.findById(1L)).thenReturn(Optional.of(sampleSubcontract));
        Optional<Subcontract> result = subcontractService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("Test Contract", result.get().getContractTitle());
    }

    @Test
    void testFindByIdNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> subcontractService.findById(null));
    }

    @Test
    void testFindAll() {
        List<Subcontract> expected = List.of(sampleSubcontract);
        when(subcontractRepository.findAllByOrderByContractTitleAsc()).thenReturn(expected);
        List<Subcontract> result = subcontractService.findAll();
        assertEquals(expected.size(), result.size());
        assertEquals(expected.get(0), result.get(0));
    }

    @Test
    void testUpdateValid() {
        Subcontract updatedDetails = new Subcontract();
        updatedDetails.setContractTitle("Updated");
        updatedDetails.setInvoiceAmount(new BigDecimal("2000.00"));

        when(subcontractRepository.findById(1L)).thenReturn(Optional.of(sampleSubcontract));
        when(subcontractRepository.save(any(Subcontract.class))).thenAnswer(i -> i.getArgument(0));

        Subcontract result = subcontractService.update(1L, updatedDetails);
        assertEquals("Updated", result.getContractTitle());
        assertEquals(new BigDecimal("2000.00"), result.getInvoiceAmount());
    }

    @Test
    void testUpdateWithNullIdThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> subcontractService.update(null, sampleSubcontract));
    }

    @Test
    void testUpdateWithNullSubcontractThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> subcontractService.update(1L, null));
    }

    @Test
    void testUpdateNotFoundThrowsException() {
        when(subcontractRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> subcontractService.update(1L, sampleSubcontract));
    }

    @Test
    void testDeleteValid() {
        assertDoesNotThrow(() -> subcontractService.delete(1L));
        verify(subcontractRepository).deleteById(1L);
    }

    @Test
    void testDeleteNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> subcontractService.delete(null));
    }

    @Test
    void testFindByContractorId() {
        List<Subcontract> expected = List.of(sampleSubcontract);
        when(subcontractRepository.findByContractorId(1L)).thenReturn(expected);
        List<Subcontract> result = subcontractService.findByContractorId(1L);
        assertEquals(expected, result);
    }

    @Test
    void testFindByCustomerId() {
        List<Subcontract> expected = List.of(sampleSubcontract);
        when(subcontractRepository.findByCustomerIdOrderByContractTitleAsc(1L)).thenReturn(expected);
        List<Subcontract> result = subcontractService.findByCustomerId(1L);
        assertEquals(expected, result);
    }

    @Test
    void testFindByProjectCostCenterId() {
        List<Subcontract> expected = List.of(sampleSubcontract);
        when(subcontractRepository.findByProjectCostCenterId(1L)).thenReturn(expected);
        List<Subcontract> result = subcontractService.findByProjectCostCenterId(1L);
        assertEquals(expected, result);
    }

    @Test
    void testRecalculateWhenNetOrGrossTrue() {
        sampleSubcontract.setNetOrGross(true);
        sampleSubcontract.setInvoiceGross(new BigDecimal("500.00")); // init value

        SubcontractProject project1 = new SubcontractProject();
        project1.setId(101L);
        project1.setShare(new BigDecimal("0.5"));
        project1.setAmount(new BigDecimal("250.00"));

        SubcontractProject project2 = new SubcontractProject();
        project2.setId(102L);
        project2.setShare(new BigDecimal("0.5"));
        project2.setAmount(new BigDecimal("250.00"));

        List<SubcontractProject> projects = Arrays.asList(project1, project2);

        when(subcontractRepository.findById(1L)).thenReturn(Optional.of(sampleSubcontract));
        when(subcontractProjectRepository.findBySubcontractId(1L)).thenReturn(projects);

        subcontractService.recalculateSubcontractProjects(1L);

        // invoiceGross should be 0
        assertEquals(BigDecimal.ZERO, sampleSubcontract.getInvoiceGross());

        // All amounts should be 0
        assertEquals(BigDecimal.ZERO, project1.getAmount());
        assertEquals(BigDecimal.ZERO, project2.getAmount());

        verify(subcontractRepository).save(sampleSubcontract);
        verify(subcontractProjectRepository).saveAll(projects);
    }

    @Test
    void testRecalculateWhenNetOrGrossFalse() {
        sampleSubcontract.setNetOrGross(false);
        sampleSubcontract.setInvoiceGross(new BigDecimal("1000.00"));

        SubcontractProject project1 = new SubcontractProject();
        project1.setId(101L);
        project1.setShare(new BigDecimal("0.25"));

        SubcontractProject project2 = new SubcontractProject();
        project2.setId(102L);
        project2.setShare(new BigDecimal("0.75"));

        List<SubcontractProject> projects = Arrays.asList(project1, project2);

        when(subcontractRepository.findById(1L)).thenReturn(Optional.of(sampleSubcontract));
        when(subcontractProjectRepository.findBySubcontractId(1L)).thenReturn(projects);

        subcontractService.recalculateSubcontractProjects(1L);

        // amount debe ser invoiceGross * share
        assertEquals(0, project1.getAmount().compareTo(new BigDecimal("250.00")));
        assertEquals(0, project2.getAmount().compareTo(new BigDecimal("750.00")));

        verify(subcontractRepository).save(sampleSubcontract);
        verify(subcontractProjectRepository).saveAll(projects);
    }

    @Test
    void testRecalculateSubcontractNotFoundThrows_Exception() {
        when(subcontractRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> subcontractService.recalculateSubcontractProjects(1L));
        verify(subcontractProjectRepository, never()).findBySubcontractId(anyLong());
    }

}
