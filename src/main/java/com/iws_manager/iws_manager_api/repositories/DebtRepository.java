package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebtRepository extends JpaRepository<Debt, Long> {
    //property Methods
    List<Debt> findByBillingEnd(LocalDate billingEnd);
    List<Debt> findByBillingMonths(Integer billingMonths);
    List<Debt> findByBillingStart(LocalDate billingStart);
    List<Debt> findByComment(String comment);
    List<Debt> findByConfDateLevel1(LocalDate confDateLevel1);
    List<Debt> findByConfDateLevel2(LocalDate confDateLevel2);
    List<Debt> findByCustomer_Id(Long customerId);
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
    List<Debt> findByOrder_Id(Long orderId);
    List<Debt> findByPayedAmount(BigDecimal payedAmount);
    List<Debt> findByProjectCosts(BigDecimal projectCosts);
    List<Debt> findByProjectEnd(LocalDate projectEnd);
    List<Debt> findByProject_Id(Long projectId);
    List<Debt> findByProjectStart(LocalDate projectStart);
    List<Debt> findByPromoter_Id(Long promoterId);
    List<Debt> findByRequestNo(Integer requestNo);
    List<Debt> findByTaxAmount(BigDecimal taxAmount);
}