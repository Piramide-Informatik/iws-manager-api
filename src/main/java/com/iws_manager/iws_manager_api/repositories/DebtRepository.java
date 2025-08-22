package com.iws_manager.iws_manager_api.repositories;

import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;

import com.iws_manager.iws_manager_api.models.Debt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebtRepository extends JpaRepository<Debt, Long> {
    //findAll
    List<Debt> findAllByOrderByDebtNoAsc();

    //properties Methods
    List<Debt> findByBillingEnd(LocalDate billingEnd);
    List<Debt> findByBillingMonths(Integer billingMonths);
    List<Debt> findByBillingStart(LocalDate billingStart);
    List<Debt> findByComment(String comment);
    List<Debt> findByConfDateLevel1(LocalDate confDateLevel1);
    List<Debt> findByConfDateLevel2(LocalDate confDateLevel2);
    List<Debt> findByCustomerId(Long customerId);
    List<Debt> findByDate(LocalDate date);
    List<Debt> findByDebtNo(Integer debtNo);
    List<Debt> findByDebtTitle(String debtTitle);
    List<Debt> findByDonation(BigDecimal donation);
    List<Debt> findByFundinglabel(String fundinglabel);
    List<Debt> findByGrossAmount(BigDecimal grossAmount);
    List<Debt> findByIwsDeptAmount1(BigDecimal iwsDeptAmount1);
    List<Debt> findByIwsDeptAmount2(BigDecimal iwsDeptAmount2);
    List<Debt> findByIwsPercent(BigDecimal iwsPercent);
    List<Debt> findByKmui0838(BigDecimal kmui0838);
    List<Debt> findByKmui0847(BigDecimal kmui0847);
    List<Debt> findByKmui0848(BigDecimal kmui0848);
    List<Debt> findByKmui0850(BigDecimal kmui0850);
    List<Debt> findByKmui0856(BigDecimal kmui0856);
    List<Debt> findByKmui0860(BigDecimal kmui0860);
    List<Debt> findByLastPaymentDate(LocalDate lastPaymentDate);
    List<Debt> findByNetAmount(BigDecimal netAmount);
    List<Debt> findByOpenAmount(BigDecimal openAmount);
    List<Debt> findByOrderId(Long orderId);
    List<Debt> findByPayedAmount(BigDecimal payedAmount);
    List<Debt> findByProjectCosts(BigDecimal projectCosts);
    List<Debt> findByProjectEnd(LocalDate projectEnd);
    List<Debt> findByProjectId(Long projectId);
    List<Debt> findByProjectStart(LocalDate projectStart);
    List<Debt> findByPromoterId(Long promoterId);
    List<Debt> findByRequestNo(Integer requestNo);
    List<Debt> findByTaxAmount(BigDecimal taxAmount);

    // Additional Methods
    List<Debt> findByDebtTitleContainingIgnoreCase(String titlePart);
    List<Debt> findByCommentContainingIgnoreCase(String commentPart);

    List<Debt> findByBillingStartBetween(LocalDate startDate, LocalDate endDate);
    List<Debt> findByBillingEndBetween(LocalDate startDate, LocalDate endDate);
    List<Debt> findByDateBetween(LocalDate startDate, LocalDate endDate);

    List<Debt> findByGrossAmountGreaterThan(BigDecimal amount);
    List<Debt> findByGrossAmountLessThan(BigDecimal amount);
    List<Debt> findByGrossAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);
    
    List<Debt> findByNetAmountGreaterThan(BigDecimal amount);
    List<Debt> findByNetAmountLessThan(BigDecimal amount);
    List<Debt> findByNetAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);

    //queries
    @Query("SELECT SUM(d.grossAmount) FROM Debt d WHERE d.customer.id = :customerId")
    BigDecimal sumGrossAmountByCustomer(@Param("customerId") Long customerId);

    @Query("SELECT SUM(d.grossAmount - d.payedAmount) FROM Debt d WHERE d.project.id = :projectId")
    BigDecimal sumOpenAmountByProject(@Param("projectId") Long projectId);

    @Query("SELECT d FROM Debt d WHERE d.date < :currentDate AND d.grossAmount > d.payedAmount ORDER BY d.date ASC")
    List<Debt> findOverdueDebts(@Param("currentDate") LocalDate currentDate);
}