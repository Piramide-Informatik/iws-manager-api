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
    private final Long TEST_ID = 1L;
    private final LocalDate TEST_DATE = LocalDate.of(2023, 6, 15);
    private final BigDecimal TEST_AMOUNT = new BigDecimal("1000.50");
    private final Integer TEST_INT = 5;
    private final String TEST_STRING = "test";

    @BeforeEach
    void setUp() {
        testDebt = new Debt();
        testDebt.setId(TEST_ID);
        testDebt.setDebtTitle(TEST_STRING);
        testDebt.setDate(TEST_DATE);
        testDebt.setGrossAmount(TEST_AMOUNT);
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
        when(debtRepository.findById(TEST_ID)).thenReturn(Optional.of(testDebt));
        
        Optional<Debt> result = debtService.findById(TEST_ID);
        
        assertThat(result).contains(testDebt);
    }

    @Test
    void findByIdShouldReturnEmptyWhenNotExists() {
        when(debtRepository.findById(TEST_ID)).thenReturn(Optional.empty());
        
        Optional<Debt> result = debtService.findById(TEST_ID);
        
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
        when(debtRepository.findAll()).thenReturn(debts);
        
        List<Debt> result = debtService.findAll();
        
        assertThat(result).hasSize(2).contains(testDebt);
    }

    @Test
    void updateShouldUpdateExistingDebt() {
        Debt updatedData = new Debt();
        updatedData.setDebtTitle("Updated");
        
        when(debtRepository.findById(TEST_ID)).thenReturn(Optional.of(testDebt));
        when(debtRepository.save(testDebt)).thenReturn(testDebt);
        
        Debt result = debtService.update(TEST_ID, updatedData);
        
        assertThat(result.getDebtTitle()).isEqualTo("Updated");
        verify(debtRepository).save(testDebt);
    }

    @Test
    void updateShouldThrowWhenDebtNotFound() {
        when(debtRepository.findById(TEST_ID)).thenReturn(Optional.empty());
        
        assertThatThrownBy(() -> debtService.update(TEST_ID, new Debt()))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Debt not found with id: " + TEST_ID);
    }

    @Test
    void updateShouldThrowWhenIdOrDebtIsNull() {
        assertAll(
            () -> assertThatThrownBy(() -> debtService.update(null, new Debt()))
                .isInstanceOf(IllegalArgumentException.class),
            () -> assertThatThrownBy(() -> debtService.update(TEST_ID, null))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void deleteShouldCallDeleteOnRepository() {
        doNothing().when(debtRepository).deleteById(TEST_ID);
        
        debtService.delete(TEST_ID);
        
        verify(debtRepository).deleteById(TEST_ID);
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
        when(debtRepository.findByBillingEnd(TEST_DATE)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByBillingEnd(TEST_DATE);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByBillingMonthsShouldReturnDebts() {
        when(debtRepository.findByBillingMonths(TEST_INT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByBillingMonths(TEST_INT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByBillingStartShouldReturnDebts() {
        when(debtRepository.findByBillingStart(TEST_DATE)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByBillingStart(TEST_DATE);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByCommentShouldReturnDebts() {
        when(debtRepository.findByComment(TEST_STRING)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByComment(TEST_STRING);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByConfDateLevel1ShouldReturnDebts() {
        when(debtRepository.findByConfDateLevel1(TEST_DATE)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByConfDateLevel1(TEST_DATE);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByConfDateLevel2ShouldReturnDebts() {
        when(debtRepository.findByConfDateLevel2(TEST_DATE)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByConfDateLevel2(TEST_DATE);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByCustomerIdShouldReturnDebts() {
        when(debtRepository.findByCustomerId(TEST_ID)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByCustomerId(TEST_ID);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByDateShouldReturnDebts() {
        when(debtRepository.findByDate(TEST_DATE)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByDate(TEST_DATE);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByDebtNoShouldReturnDebts() {
        when(debtRepository.findByDebtNo(TEST_INT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByDebtNo(TEST_INT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByDebtTitleShouldReturnDebts() {
        when(debtRepository.findByDebtTitle(TEST_STRING)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByDebtTitle(TEST_STRING);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByDonationShouldReturnDebts() {
        when(debtRepository.findByDonation(TEST_AMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByDonation(TEST_AMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByFundinglabelShouldReturnDebts() {
        when(debtRepository.findByFundinglabel(TEST_STRING)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByFundinglabel(TEST_STRING);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByGrossAmountShouldReturnDebts() {
        when(debtRepository.findByGrossAmount(TEST_AMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByGrossAmount(TEST_AMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByIwsDeptAmount1ShouldReturnDebts() {
        when(debtRepository.findByIwsDeptAmount1(TEST_AMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByIwsDeptAmount1(TEST_AMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByIwsDeptAmount2ShouldReturnDebts() {
        when(debtRepository.findByIwsDeptAmount2(TEST_AMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByIwsDeptAmount2(TEST_AMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByIwsPercentShouldReturnDebts() {
        when(debtRepository.findByIwsPercent(TEST_AMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByIwsPercent(TEST_AMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByKmui0838ShouldReturnDebts() {
        when(debtRepository.findByKmui0838(TEST_AMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByKmui0838(TEST_AMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByKmui0847ShouldReturnDebts() {
        when(debtRepository.findByKmui0847(TEST_AMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByKmui0847(TEST_AMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByKmui0848ShouldReturnDebts() {
        when(debtRepository.findByKmui0848(TEST_AMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByKmui0848(TEST_AMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByKmui0850ShouldReturnDebts() {
        when(debtRepository.findByKmui0850(TEST_AMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByKmui0850(TEST_AMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByKmui0856ShouldReturnDebts() {
        when(debtRepository.findByKmui0856(TEST_AMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByKmui0856(TEST_AMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByKmui0860ShouldReturnDebts() {
        when(debtRepository.findByKmui0860(TEST_AMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByKmui0860(TEST_AMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByLastPaymentDateShouldReturnDebts() {
        when(debtRepository.findByLastPaymentDate(TEST_DATE)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByLastPaymentDate(TEST_DATE);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByNetAmountShouldReturnDebts() {
        when(debtRepository.findByNetAmount(TEST_AMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByNetAmount(TEST_AMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByOpenAmountShouldReturnDebts() {
        when(debtRepository.findByOpenAmount(TEST_AMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByOpenAmount(TEST_AMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByOrderIdShouldReturnDebts() {
        when(debtRepository.findByOrderId(TEST_ID)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByOrderId(TEST_ID);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByPayedAmountShouldReturnDebts() {
        when(debtRepository.findByPayedAmount(TEST_AMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByPayedAmount(TEST_AMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByProjectCostsShouldReturnDebts() {
        when(debtRepository.findByProjectCosts(TEST_AMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByProjectCosts(TEST_AMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByProjectEndShouldReturnDebts() {
        when(debtRepository.findByProjectEnd(TEST_DATE)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByProjectEnd(TEST_DATE);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByProjectIdShouldReturnDebts() {
        when(debtRepository.findByProjectId(TEST_ID)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByProjectId(TEST_ID);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByProjectStartShouldReturnDebts() {
        when(debtRepository.findByProjectStart(TEST_DATE)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByProjectStart(TEST_DATE);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByPromoterIdShouldReturnDebts() {
        when(debtRepository.findByPromoterId(TEST_ID)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByPromoterId(TEST_ID);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByRequestNoShouldReturnDebts() {
        when(debtRepository.findByRequestNo(TEST_INT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByRequestNo(TEST_INT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByTaxAmountShouldReturnDebts() {
        when(debtRepository.findByTaxAmount(TEST_AMOUNT)).thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByTaxAmount(TEST_AMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByDebtTitleContainingIgnoreCaseShouldReturnDebts() {
        when(debtRepository.findByDebtTitleContainingIgnoreCase(TEST_STRING))
            .thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByDebtTitleContainingIgnoreCase(TEST_STRING);
        
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
        when(debtRepository.findByCommentContainingIgnoreCase(TEST_STRING))
            .thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByCommentContainingIgnoreCase(TEST_STRING);
        
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
        when(debtRepository.findByBillingStartBetween(TEST_DATE, TEST_DATE.plusDays(1)))
            .thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByBillingStartBetween(TEST_DATE, TEST_DATE.plusDays(1));
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByBillingStartBetweenShouldThrowWhenDatesInvalid() {
        assertAll(
            () -> assertThatThrownBy(() -> debtService.getByBillingStartBetween(null, TEST_DATE))
                .isInstanceOf(IllegalArgumentException.class),
            () -> assertThatThrownBy(() -> debtService.getByBillingStartBetween(TEST_DATE, TEST_DATE.minusDays(1)))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void getByBillingEndBetweenShouldReturnDebts() {
        when(debtRepository.findByBillingEndBetween(TEST_DATE, TEST_DATE.plusDays(1)))
            .thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByBillingEndBetween(TEST_DATE, TEST_DATE.plusDays(1));
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByDateBetweenShouldReturnDebts() {
        when(debtRepository.findByDateBetween(TEST_DATE, TEST_DATE.plusDays(1)))
            .thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByDateBetween(TEST_DATE, TEST_DATE.plusDays(1));
        
        assertThat(result).containsExactly(testDebt);
    }

    // Amount comparison tests
    @Test
    void getByGrossAmountGreaterThanShouldReturnDebts() {
        when(debtRepository.findByGrossAmountGreaterThan(TEST_AMOUNT))
            .thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByGrossAmountGreaterThan(TEST_AMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByGrossAmountGreaterThanShouldThrowWhenAmountInvalid() {
        assertThatThrownBy(() -> debtService.getByGrossAmountGreaterThan(null))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getByGrossAmountLessThanShouldReturnDebts() {
        when(debtRepository.findByGrossAmountLessThan(TEST_AMOUNT))
            .thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByGrossAmountLessThan(TEST_AMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByGrossAmountBetweenShouldReturnDebts() {
        when(debtRepository.findByGrossAmountBetween(TEST_AMOUNT, TEST_AMOUNT.add(BigDecimal.ONE)))
            .thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByGrossAmountBetween(TEST_AMOUNT, TEST_AMOUNT.add(BigDecimal.ONE));
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByGrossAmountBetweenShouldThrowWhenRangeInvalid() {
        assertAll(
            () -> assertThatThrownBy(() -> debtService.getByGrossAmountBetween(null, TEST_AMOUNT))
                .isInstanceOf(IllegalArgumentException.class),
            () -> assertThatThrownBy(() -> debtService.getByGrossAmountBetween(TEST_AMOUNT, TEST_AMOUNT.subtract(BigDecimal.ONE)))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void getByNetAmountGreaterThanShouldReturnDebts() {
        when(debtRepository.findByNetAmountGreaterThan(TEST_AMOUNT))
            .thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByNetAmountGreaterThan(TEST_AMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByNetAmountLessThanShouldReturnDebts() {
        when(debtRepository.findByNetAmountLessThan(TEST_AMOUNT))
            .thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByNetAmountLessThan(TEST_AMOUNT);
        
        assertThat(result).containsExactly(testDebt);
    }

    @Test
    void getByNetAmountBetweenShouldReturnDebts() {
        when(debtRepository.findByNetAmountBetween(TEST_AMOUNT, TEST_AMOUNT.add(BigDecimal.ONE)))
            .thenReturn(Collections.singletonList(testDebt));
        
        List<Debt> result = debtService.getByNetAmountBetween(TEST_AMOUNT, TEST_AMOUNT.add(BigDecimal.ONE));
        
        assertThat(result).containsExactly(testDebt);
    }

    // Special calculation tests
    @Test
    void calculateTotalDebtByCustomerShouldReturnSum() {
        when(debtRepository.sumGrossAmountByCustomer(TEST_ID))
            .thenReturn(new BigDecimal("5000.00"));
        
        BigDecimal result = debtService.calculateTotalDebtByCustomer(TEST_ID);
        
        assertThat(result).isEqualByComparingTo("5000.00");
    }

    @Test
    void calculateTotalDebtByCustomerShouldReturnZeroWhenNoDebts() {
        when(debtRepository.sumGrossAmountByCustomer(TEST_ID)).thenReturn(null);
        
        BigDecimal result = debtService.calculateTotalDebtByCustomer(TEST_ID);
        
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
        when(debtRepository.sumOpenAmountByProject(TEST_ID))
            .thenReturn(new BigDecimal("3000.00"));
        
        BigDecimal result = debtService.calculateTotalOpenAmountByProject(TEST_ID);
        
        assertThat(result).isEqualByComparingTo("3000.00");
    }

    @Test
    void calculateTotalOpenAmountByProjectShouldReturnZeroWhenNoDebts() {
        when(debtRepository.sumOpenAmountByProject(TEST_ID)).thenReturn(null);
        
        BigDecimal result = debtService.calculateTotalOpenAmountByProject(TEST_ID);
        
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
        overdueDebt.setDate(TEST_DATE.minusDays(1));
        overdueDebt.setGrossAmount(new BigDecimal("1000.00"));
        overdueDebt.setPayedAmount(new BigDecimal("500.00"));
        
        when(debtRepository.findOverdueDebts(TEST_DATE))
            .thenReturn(Collections.singletonList(overdueDebt));
        
        List<Debt> result = debtService.findOverdueDebts(TEST_DATE);
        
        assertThat(result).hasSize(1).containsExactly(overdueDebt);
    }

    @Test
    void findOverdueDebtsShouldThrowWhenCurrentDateIsNull() {
        assertThatThrownBy(() -> debtService.findOverdueDebts(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Current date cannot be null");
    }
}