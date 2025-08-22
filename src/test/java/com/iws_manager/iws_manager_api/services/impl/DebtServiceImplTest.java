package com.iws_manager.iws_manager_api.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import com.iws_manager.iws_manager_api.models.Debt;
import com.iws_manager.iws_manager_api.repositories.DebtRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DebtServiceImplTest {

    @Mock
    private DebtRepository debtRepository;

    @InjectMocks
    private DebtServiceImpl debtService;

    private Debt testDebt;
    private static final Long TESTID = 1L;
    private static final LocalDate TESTDATE = LocalDate.of(2023, 6, 15);
    private static final BigDecimal TESTAMOUNT = new BigDecimal("1000.50");
    private static final Integer TESTINT = 5;
    private static final String TESTSTRING = "test";

    @BeforeEach
    void setUp() {
        testDebt = new Debt();
        testDebt.setId(TESTID);
        testDebt.setDebtTitle(TESTSTRING);
        testDebt.setDate(TESTDATE);
        testDebt.setGrossAmount(TESTAMOUNT);
        testDebt.setPayedAmount(new BigDecimal("500.00"));
    }

    // CRUD Operations Tests
    @Test
    void createShouldSaveAndReturnDebt() {
        when(debtRepository.save(any(Debt.class))).thenReturn(testDebt);
        
        Debt result = debtService.create(testDebt);
        
        assertThat(result).isEqualTo(testDebt);
        verify(debtRepository).save(testDebt);
    }

    @Test
    void createShouldThrowWhenDebtIsNull() {
        assertThatThrownBy(() -> debtService.create(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Debt cannot be null");
    }

    @Test
    void findByIdShouldReturnDebtWhenExists() {
        when(debtRepository.findById(TESTID)).thenReturn(Optional.of(testDebt));
        
        Optional<Debt> result = debtService.findById(TESTID);
        
        assertThat(result).contains(testDebt);
    }

    @Test
    void findByIdShouldReturnEmptyWhenNotExists() {
        when(debtRepository.findById(TESTID)).thenReturn(Optional.empty());
        
        Optional<Debt> result = debtService.findById(TESTID);
        
        assertThat(result).isEmpty();
    }

    @Test
    void findByIdShouldThrowWhenIdIsNull() {
        assertThatThrownBy(() -> debtService.findById(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("ID cannot be null");
    }

    @Test
    void findAllShouldReturnAllDebts() {
        List<Debt> debts = Arrays.asList(testDebt, new Debt());
        when(debtRepository.findAllByOrderByDebtNoAsc()).thenReturn(debts);
        
        List<Debt> result = debtService.findAll();
        
        assertThat(result).hasSize(2).contains(testDebt);
    }

    @Test
    void updateShouldUpdateExistingDebt() {
        Debt updatedData = new Debt();
        updatedData.setDebtTitle("Updated");
        
        when(debtRepository.findById(TESTID)).thenReturn(Optional.of(testDebt));
        when(debtRepository.save(testDebt)).thenReturn(testDebt);
        
        Debt result = debtService.update(TESTID, updatedData);
        
        assertThat(result.getDebtTitle()).isEqualTo("Updated");
        verify(debtRepository).save(testDebt);
    }

    @Test
    void updateShouldThrowWhenDebtNotFound() {
        when(debtRepository.findById(TESTID)).thenReturn(Optional.empty());
        
        assertThatThrownBy(() -> debtService.update(TESTID, new Debt()))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Debt not found with id: " + TESTID);
    }

    @Test
    void updateShouldThrowWhenIdOrDebtIsNull() {
        assertAll(
            () -> assertThatThrownBy(() -> debtService.update(null, new Debt()))
                .isInstanceOf(IllegalArgumentException.class),
            () -> assertThatThrownBy(() -> debtService.update(TESTID, null))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void deleteShouldCallDeleteOnRepository() {
        doNothing().when(debtRepository).deleteById(TESTID);
        
        debtService.delete(TESTID);
        
        verify(debtRepository).deleteById(TESTID);
    }

    @Test
    void deleteShouldThrowWhenIdIsNull() {
        assertThatThrownBy(() -> debtService.delete(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("ID cannot be null");
    }

    // Tests for each property-based search method
    @Test
    void getByBillingEndShouldReturnDebts() {
        when(debtRepository.findByBillingEnd(TESTDATE)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByBillingEnd(TESTDATE);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByBillingMonthsShouldReturnDebts() {
        when(debtRepository.findByBillingMonths(TESTINT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByBillingMonths(TESTINT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByBillingStartShouldReturnDebts() {
        when(debtRepository.findByBillingStart(TESTDATE)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByBillingStart(TESTDATE);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByCommentShouldReturnDebts() {
        when(debtRepository.findByComment(TESTSTRING)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByComment(TESTSTRING);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByConfDateLevel1ShouldReturnDebts() {
        when(debtRepository.findByConfDateLevel1(TESTDATE)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByConfDateLevel1(TESTDATE);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByConfDateLevel2ShouldReturnDebts() {
        when(debtRepository.findByConfDateLevel2(TESTDATE)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByConfDateLevel2(TESTDATE);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByCustomerIdShouldReturnDebts() {
        when(debtRepository.findByCustomerId(TESTID)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByCustomerId(TESTID);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByDateShouldReturnDebts() {
        when(debtRepository.findByDate(TESTDATE)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByDate(TESTDATE);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByDebtNoShouldReturnDebts() {
        when(debtRepository.findByDebtNo(TESTINT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByDebtNo(TESTINT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByDebtTitleShouldReturnDebts() {
        when(debtRepository.findByDebtTitle(TESTSTRING)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByDebtTitle(TESTSTRING);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByDonationShouldReturnDebts() {
        when(debtRepository.findByDonation(TESTAMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByDonation(TESTAMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByFundinglabelShouldReturnDebts() {
        when(debtRepository.findByFundinglabel(TESTSTRING)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByFundinglabel(TESTSTRING);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByGrossAmountShouldReturnDebts() {
        when(debtRepository.findByGrossAmount(TESTAMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByGrossAmount(TESTAMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByIwsDeptAmount1ShouldReturnDebts() {
        when(debtRepository.findByIwsDeptAmount1(TESTAMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByIwsDeptAmount1(TESTAMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByIwsDeptAmount2ShouldReturnDebts() {
        when(debtRepository.findByIwsDeptAmount2(TESTAMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByIwsDeptAmount2(TESTAMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByIwsPercentShouldReturnDebts() {
        when(debtRepository.findByIwsPercent(TESTAMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByIwsPercent(TESTAMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByKmui0838ShouldReturnDebts() {
        when(debtRepository.findByKmui0838(TESTAMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByKmui0838(TESTAMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByKmui0847ShouldReturnDebts() {
        when(debtRepository.findByKmui0847(TESTAMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByKmui0847(TESTAMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByKmui0848ShouldReturnDebts() {
        when(debtRepository.findByKmui0848(TESTAMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByKmui0848(TESTAMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByKmui0850ShouldReturnDebts() {
        when(debtRepository.findByKmui0850(TESTAMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByKmui0850(TESTAMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByKmui0856ShouldReturnDebts() {
        when(debtRepository.findByKmui0856(TESTAMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByKmui0856(TESTAMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByKmui0860ShouldReturnDebts() {
        when(debtRepository.findByKmui0860(TESTAMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByKmui0860(TESTAMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByLastPaymentDateShouldReturnDebts() {
        when(debtRepository.findByLastPaymentDate(TESTDATE)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByLastPaymentDate(TESTDATE);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByNetAmountShouldReturnDebts() {
        when(debtRepository.findByNetAmount(TESTAMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByNetAmount(TESTAMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByOpenAmountShouldReturnDebts() {
        when(debtRepository.findByOpenAmount(TESTAMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByOpenAmount(TESTAMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByOrderIdShouldReturnDebts() {
        when(debtRepository.findByOrderId(TESTID)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByOrderId(TESTID);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByPayedAmountShouldReturnDebts() {
        when(debtRepository.findByPayedAmount(TESTAMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByPayedAmount(TESTAMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByProjectCostsShouldReturnDebts() {
        when(debtRepository.findByProjectCosts(TESTAMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByProjectCosts(TESTAMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByProjectEndShouldReturnDebts() {
        when(debtRepository.findByProjectEnd(TESTDATE)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByProjectEnd(TESTDATE);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByProjectIdShouldReturnDebts() {
        when(debtRepository.findByProjectId(TESTID)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByProjectId(TESTID);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByProjectStartShouldReturnDebts() {
        when(debtRepository.findByProjectStart(TESTDATE)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByProjectStart(TESTDATE);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByPromoterIdShouldReturnDebts() {
        when(debtRepository.findByPromoterId(TESTID)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByPromoterId(TESTID);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByRequestNoShouldReturnDebts() {
        when(debtRepository.findByRequestNo(TESTINT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByRequestNo(TESTINT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByTaxAmountShouldReturnDebts() {
        when(debtRepository.findByTaxAmount(TESTAMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByTaxAmount(TESTAMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByDebtTitleContainingIgnoreCaseShouldReturnDebts() {
        when(debtRepository.findByDebtTitleContainingIgnoreCase(TESTSTRING))
            .thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByDebtTitleContainingIgnoreCase(TESTSTRING);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByDebtTitleContainingIgnoreCaseShouldThrowWhenTitleIsNull() {
        assertThatThrownBy(() -> debtService.getByDebtTitleContainingIgnoreCase(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Title part cannot be null");
    }

    @Test
    void getByCommentContainingIgnoreCaseShouldReturnDebts() {
        when(debtRepository.findByCommentContainingIgnoreCase(TESTSTRING))
            .thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByCommentContainingIgnoreCase(TESTSTRING);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByCommentContainingIgnoreCaseShouldThrowWhenCommentIsNull() {
        assertThatThrownBy(() -> debtService.getByCommentContainingIgnoreCase(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Comment part cannot be null");
    }

    // Range-based search tests
    @Test
    void getByBillingStartBetweenShouldReturnDebts() {
        when(debtRepository.findByBillingStartBetween(TESTDATE, TESTDATE.plusDays(1)))
            .thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByBillingStartBetween(TESTDATE, TESTDATE.plusDays(1));
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByBillingStartBetweenShouldThrowWhenDatesInvalid() {
        assertAll(
            () -> assertThatThrownBy(() -> debtService.getByBillingStartBetween(null, TESTDATE))
                .isInstanceOf(IllegalArgumentException.class),
            () -> assertThatThrownBy(() -> debtService.getByBillingStartBetween(TESTDATE, TESTDATE.minusDays(1)))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void getByBillingEndBetweenShouldReturnDebts() {
        when(debtRepository.findByBillingEndBetween(TESTDATE, TESTDATE.plusDays(1)))
            .thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByBillingEndBetween(TESTDATE, TESTDATE.plusDays(1));
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByDateBetweenShouldReturnDebts() {
        when(debtRepository.findByDateBetween(TESTDATE, TESTDATE.plusDays(1)))
            .thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByDateBetween(TESTDATE, TESTDATE.plusDays(1));
        
        assertThat(result).containsExactly(testDebt);
    }

    // Amount comparison tests
    @Test
    void getByGrossAmountGreaterThanShouldReturnDebts() {
        when(debtRepository.findByGrossAmountGreaterThan(TESTAMOUNT))
            .thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByGrossAmountGreaterThan(TESTAMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByGrossAmountGreaterThanShouldThrowWhenAmountInvalid() {
        assertThatThrownBy(() -> debtService.getByGrossAmountGreaterThan(null))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getByGrossAmountLessThanShouldReturnDebts() {
        when(debtRepository.findByGrossAmountLessThan(TESTAMOUNT))
            .thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByGrossAmountLessThan(TESTAMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByGrossAmountBetweenShouldReturnDebts() {
        when(debtRepository.findByGrossAmountBetween(TESTAMOUNT, TESTAMOUNT.add(BigDecimal.ONE)))
            .thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByGrossAmountBetween(TESTAMOUNT, TESTAMOUNT.add(BigDecimal.ONE));
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByGrossAmountBetweenShouldThrowWhenRangeInvalid() {
        assertAll(
            () -> assertThatThrownBy(() -> debtService.getByGrossAmountBetween(null, TESTAMOUNT))
                .isInstanceOf(IllegalArgumentException.class),
            () -> assertThatThrownBy(() -> debtService.getByGrossAmountBetween(TESTAMOUNT, TESTAMOUNT.subtract(BigDecimal.ONE)))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void getByNetAmountGreaterThanShouldReturnDebts() {
        when(debtRepository.findByNetAmountGreaterThan(TESTAMOUNT))
            .thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByNetAmountGreaterThan(TESTAMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByNetAmountLessThanShouldReturnDebts() {
        when(debtRepository.findByNetAmountLessThan(TESTAMOUNT))
            .thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByNetAmountLessThan(TESTAMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByNetAmountBetweenShouldReturnDebts() {
        when(debtRepository.findByNetAmountBetween(TESTAMOUNT, TESTAMOUNT.add(BigDecimal.ONE)))
            .thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByNetAmountBetween(TESTAMOUNT, TESTAMOUNT.add(BigDecimal.ONE));
        
        assertThat(result).containsExactly(testDebt);
    }

    // Special calculation tests
    @Test
    void calculateTotalDebtByCustomerShouldReturnSum() {
        when(debtRepository.sumGrossAmountByCustomer(TESTID))
            .thenReturn(new BigDecimal("5000.00"));
        
        BigDecimal result = debtService.calculateTotalDebtByCustomer(TESTID);
        
        assertThat(result).isEqualByComparingTo("5000.00");
    }

    @Test
    void calculateTotalDebtByCustomerShouldReturnZeroWhenNoDebts() {
        when(debtRepository.sumGrossAmountByCustomer(TESTID)).thenReturn(null);
        
        BigDecimal result = debtService.calculateTotalDebtByCustomer(TESTID);
        
        assertThat(result).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void calculateTotalDebtByCustomerShouldThrowWhenCustomerIdIsNull() {
        assertThatThrownBy(() -> debtService.calculateTotalDebtByCustomer(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Customer ID cannot be null");
    }

    @Test
    void calculateTotalOpenAmountByProjectShouldReturnSum() {
        when(debtRepository.sumOpenAmountByProject(TESTID))
            .thenReturn(new BigDecimal("3000.00"));
        
        BigDecimal result = debtService.calculateTotalOpenAmountByProject(TESTID);
        
        assertThat(result).isEqualByComparingTo("3000.00");
    }

    @Test
    void calculateTotalOpenAmountByProjectShouldReturnZeroWhenNoDebts() {
        when(debtRepository.sumOpenAmountByProject(TESTID)).thenReturn(null);
        
        BigDecimal result = debtService.calculateTotalOpenAmountByProject(TESTID);
        
        assertThat(result).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void calculateTotalOpenAmountByProjectShouldThrowWhenProjectIdIsNull() {
        assertThatThrownBy(() -> debtService.calculateTotalOpenAmountByProject(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Project ID cannot be null");
    }

    @Test
    void findOverdueDebtsShouldReturnDebts() {
        Debt overdueDebt = new Debt();
        overdueDebt.setDate(TESTDATE.minusDays(1));
        overdueDebt.setGrossAmount(new BigDecimal("1000.00"));
        overdueDebt.setPayedAmount(new BigDecimal("500.00"));
        
        when(debtRepository.findOverdueDebts(TESTDATE))
            .thenReturn(Collections.singletonList(overdueDebt));
        
        List<Debt> result = debtService.findOverdueDebts(TESTDATE);
        
        assertThat(result).hasSize(1).containsExactly(overdueDebt);
    }

    @Test
    void findOverdueDebtsShouldThrowWhenCurrentDateIsNull() {
        assertThatThrownBy(() -> debtService.findOverdueDebts(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Current date cannot be null");
    }
}