package com.iws_manager.iws_manager_api.services.interfaces;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.Debt;

public interface DebtService {
    Debt create(Debt debt);
    Optional<Debt> findById(Long id);
    List<Debt> findAll();
    Debt update(Long id, Debt debtDetails);
    void delete(Long id);

    //properties Methods
    List<Debt> getByBillingEnd(LocalDate billingEnd);
    List<Debt> getByBillingMonths(Integer billingMonths);
    List<Debt> getByBillingStart(LocalDate billingStart);
    List<Debt> getByComment(String comment);
    List<Debt> getByConfDateLevel1(LocalDate confDateLevel1);
    List<Debt> getByConfDateLevel2(LocalDate confDateLevel2);
    List<Debt> getByCustomerId(Long customerId);
    List<Debt> getByDate(LocalDate date);
    List<Debt> getByDebtNo(Integer debtNo);
    List<Debt> getByDebtTitle(String debtTitle);
    List<Debt> getByDonation(BigDecimal donation);
    List<Debt> getByFundinglabel(String fundinglabel);
    List<Debt> getByGrossAmount(BigDecimal grossAmount);
    List<Debt> getByIwsDeptAmount1(BigDecimal iwsDeptAmount1);
    List<Debt> getByIwsDeptAmount2(BigDecimal iwsDeptAmount2);
    List<Debt> getByIwsPercent(BigDecimal iwsPercent);
    List<Debt> getByKmui0838(BigDecimal kmui0838);
    List<Debt> getByKmui0847(BigDecimal kmui0847);
    List<Debt> getByKmui0848(BigDecimal kmui0848);
    List<Debt> getByKmui0850(BigDecimal kmui0850);
    List<Debt> getByKmui0856(BigDecimal kmui0856);
    List<Debt> getByKmui0860(BigDecimal kmui0860);
    List<Debt> getByLastPaymentDate(LocalDate lastPaymentDate);
    List<Debt> getByNetAmount(BigDecimal netAmount);
    List<Debt> getByOpenAmount(BigDecimal openAmount);
    List<Debt> getByOrderId(Long orderId);
    List<Debt> getByPayedAmount(BigDecimal payedAmount);
    List<Debt> getByProjectCosts(BigDecimal projectCosts);
    List<Debt> getByProjectEnd(LocalDate projectEnd);
    List<Debt> getByProjectId(Long projectId);
    List<Debt> getByProjectStart(LocalDate projectStart);
    List<Debt> getByPromoterId(Long promoterId);
    List<Debt> getByRequestNo(Integer requestNo);
    List<Debt> getByTaxAmount(BigDecimal taxAmount);

    // Additional Methods
    List<Debt> getByDebtTitleContainingIgnoreCase(String titlePart);
    List<Debt> getByCommentContainingIgnoreCase(String commentPart);

    List<Debt> getByBillingStartBetween(LocalDate startDate, LocalDate endDate);
    List<Debt> getByBillingEndBetween(LocalDate startDate, LocalDate endDate);
    List<Debt> getByDateBetween(LocalDate startDate, LocalDate endDate);

    List<Debt> getByGrossAmountGreaterThan(BigDecimal amount);
    List<Debt> getByGrossAmountLessThan(BigDecimal amount);
    List<Debt> getByGrossAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);
    
    List<Debt> getByNetAmountGreaterThan(BigDecimal amount);
    List<Debt> getByNetAmountLessThan(BigDecimal amount);
    List<Debt> getByNetAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);

    BigDecimal calculateTotalDebtByCustomer(Long customerId);
    BigDecimal calculateTotalOpenAmountByProject(Long projectId);
    List<Debt> findOverdueDebts(LocalDate currentDate);
}
