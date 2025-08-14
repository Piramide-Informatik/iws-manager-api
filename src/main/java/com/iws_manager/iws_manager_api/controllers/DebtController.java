package com.iws_manager.iws_manager_api.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.iws_manager.iws_manager_api.models.Debt;
import com.iws_manager.iws_manager_api.services.interfaces.DebtService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;

@RestController
@RequestMapping("/api/v1/debts")
public class DebtController {
    private final DebtService debtService;

    @Autowired
    public DebtController(DebtService debtService) {
        this.debtService = debtService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody Debt debt){
        Debt createDebt = debtService.create(debt);
        return new ResponseEntity<>(createDebt, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Debt> getById(@PathVariable Long id){
        return  debtService.findById(id)
                .map( debt -> new ResponseEntity<>(debt, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Debt>> getAll(){
        List<Debt> debts = debtService.findAll();
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Debt> update(@PathVariable Long id, @RequestBody Debt debtDetails){
        try {
            Debt updateDebt = debtService.update(id, debtDetails);
            return new ResponseEntity<>(updateDebt, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try {
            debtService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // PROPERTIES

    @GetMapping("/by-billingend/{billingEnd}")
    public ResponseEntity<List<Debt>> getByBillingEnd(@PathVariable LocalDate billingEnd) {
        List<Debt> debts = debtService.getByBillingEnd(billingEnd);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-billingmonths/{billingMonths}")
    public ResponseEntity<List<Debt>> getByBillingMonths(@PathVariable Integer billingMonths) {
        List<Debt> debts = debtService.getByBillingMonths(billingMonths);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-billingstart/{billingStart}")
    public ResponseEntity<List<Debt>> getByBillingStart(@PathVariable LocalDate billingStart) {
        List<Debt> debts = debtService.getByBillingStart(billingStart);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-comment/{comment}")
    public ResponseEntity<List<Debt>> getByComment(@PathVariable String comment) {
        List<Debt> debts = debtService.getByComment(comment);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-confdatelevel1/{confDateLevel1}")
    public ResponseEntity<List<Debt>> getByConfDateLevel1(@PathVariable LocalDate confDateLevel1) {
        List<Debt> debts = debtService.getByConfDateLevel1(confDateLevel1);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-confdatelevel2/{confDateLevel2}")
    public ResponseEntity<List<Debt>> getByConfDateLevel2(@PathVariable LocalDate confDateLevel2) {
        List<Debt> debts = debtService.getByConfDateLevel2(confDateLevel2);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-customer/{customerId}")
    public ResponseEntity<List<Debt>> getByCustomerId(@PathVariable Long customerId) {
        List<Debt> debts = debtService.getByCustomerId(customerId);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-date/{date}")
    public ResponseEntity<List<Debt>> getByDate(@PathVariable LocalDate date) {
        List<Debt> debts = debtService.getByDate(date);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-debtno/{debtNo}")
    public ResponseEntity<List<Debt>> getByDebtNo(@PathVariable Integer debtNo) {
        List<Debt> debts = debtService.getByDebtNo(debtNo);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-debttitle/{debtTitle}")
    public ResponseEntity<List<Debt>> getByDebtTitle(@PathVariable String debtTitle) {
        List<Debt> debts = debtService.getByDebtTitle(debtTitle);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-donation/{donation}")
    public ResponseEntity<List<Debt>> getByDonation(@PathVariable BigDecimal donation) {
        List<Debt> debts = debtService.getByDonation(donation);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-fundinglabel/{fundinglabel}")
    public ResponseEntity<List<Debt>> getByFundinglabel(@PathVariable String fundinglabel) {
        List<Debt> debts = debtService.getByFundinglabel(fundinglabel);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-grossamount/{grossAmount}")
    public ResponseEntity<List<Debt>> getByGrossAmount(@PathVariable BigDecimal grossAmount) {
        List<Debt> debts = debtService.getByGrossAmount(grossAmount);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-iwsdeptamount1/{iwsDeptAmount1}")
    public ResponseEntity<List<Debt>> getByIwsDeptAmount1(@PathVariable BigDecimal iwsDeptAmount1) {
        List<Debt> debts = debtService.getByIwsDeptAmount1(iwsDeptAmount1);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-iwsdeptamount2/{iwsDeptAmount2}")
    public ResponseEntity<List<Debt>> getByIwsDeptAmount2(@PathVariable BigDecimal iwsDeptAmount2) {
        List<Debt> debts = debtService.getByIwsDeptAmount2(iwsDeptAmount2);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-iwspercent/{iwsPercent}")
    public ResponseEntity<List<Debt>> getByIwsPercent(@PathVariable BigDecimal iwsPercent) {
        List<Debt> debts = debtService.getByIwsPercent(iwsPercent);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-kmui0838/{kmui0838}")
    public ResponseEntity<List<Debt>> getByKmui0838(@PathVariable BigDecimal kmui0838) {
        List<Debt> debts = debtService.getByKmui0838(kmui0838);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-kmui0847/{kmui0847}")
    public ResponseEntity<List<Debt>> getByKmui0847(@PathVariable BigDecimal kmui0847) {
        List<Debt> debts = debtService.getByKmui0847(kmui0847);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-kmui0848/{kmui0848}")
    public ResponseEntity<List<Debt>> getByKmui0848(@PathVariable BigDecimal kmui0848) {
        List<Debt> debts = debtService.getByKmui0848(kmui0848);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-kmui0850/{kmui0850}")
    public ResponseEntity<List<Debt>> getByKmui0850(@PathVariable BigDecimal kmui0850) {
        List<Debt> debts = debtService.getByKmui0850(kmui0850);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-kmui0856/{kmui0856}")
    public ResponseEntity<List<Debt>> getByKmui0856(@PathVariable BigDecimal kmui0856) {
        List<Debt> debts = debtService.getByKmui0856(kmui0856);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-kmui0860/{kmui0860}")
    public ResponseEntity<List<Debt>> getByKmui0860(@PathVariable BigDecimal kmui0860) {
        List<Debt> debts = debtService.getByKmui0860(kmui0860);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-lastpaymentdate/{lastPaymentDate}")
    public ResponseEntity<List<Debt>> getByLastPaymentDate(@PathVariable LocalDate lastPaymentDate) {
        List<Debt> debts = debtService.getByLastPaymentDate(lastPaymentDate);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-netamount/{netAmount}")
    public ResponseEntity<List<Debt>> getByNetAmount(@PathVariable BigDecimal netAmount) {
        List<Debt> debts = debtService.getByNetAmount(netAmount);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-openamount/{openAmount}")
    public ResponseEntity<List<Debt>> getByOpenAmount(@PathVariable BigDecimal openAmount) {
        List<Debt> debts = debtService.getByOpenAmount(openAmount);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-orderid/{orderId}")
    public ResponseEntity<List<Debt>> getByOrderId(@PathVariable Long orderId) {
        List<Debt> debts = debtService.getByOrderId(orderId);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-payedamount/{payedAmount}")
    public ResponseEntity<List<Debt>> getByPayedAmount(@PathVariable BigDecimal payedAmount) {
        List<Debt> debts = debtService.getByPayedAmount(payedAmount);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-projectcosts/{projectCosts}")
    public ResponseEntity<List<Debt>> getByProjectCosts(@PathVariable BigDecimal projectCosts) {
        List<Debt> debts = debtService.getByProjectCosts(projectCosts);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-projectend/{projectEnd}")
    public ResponseEntity<List<Debt>> getByProjectEnd(@PathVariable LocalDate projectEnd) {
        List<Debt> debts = debtService.getByProjectEnd(projectEnd);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-projectid/{projectId}")
    public ResponseEntity<List<Debt>> getByProjectId(@PathVariable Long projectId) {
        List<Debt> debts = debtService.getByProjectId(projectId);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-projectstart/{projectStart}")
    public ResponseEntity<List<Debt>> getByProjectStart(@PathVariable LocalDate projectStart) {
        List<Debt> debts = debtService.getByProjectStart(projectStart);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-promoterid/{promoterId}")
    public ResponseEntity<List<Debt>> getByPromoterId(@PathVariable Long promoterId) {
        List<Debt> debts = debtService.getByPromoterId(promoterId);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-requestno/{requestNo}")
    public ResponseEntity<List<Debt>> getByRequestNo(@PathVariable Integer requestNo) {
        List<Debt> debts = debtService.getByRequestNo(requestNo);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-taxamount/{taxAmount}")
    public ResponseEntity<List<Debt>> getByTaxAmount(@PathVariable BigDecimal taxAmount) {
        List<Debt> debts = debtService.getByTaxAmount(taxAmount);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/search/title/{titlePart}")
    public ResponseEntity<List<Debt>> getByDebtTitleContaining(@PathVariable String titlePart) {
        List<Debt> debts = debtService.getByDebtTitleContainingIgnoreCase(titlePart);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/search/comment/{commentPart}")
    public ResponseEntity<List<Debt>> getByCommentContaining(@PathVariable String commentPart) {
        List<Debt> debts = debtService.getByCommentContainingIgnoreCase(commentPart);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-billingstart-range")
    public ResponseEntity<List<Debt>> getDebtsByBillingStartRange(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) {
        if (start.isAfter(end)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        List<Debt> debts = debtService.getByBillingStartBetween(start, end);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-billingend-range")
    public ResponseEntity<List<Debt>> getDebtsByBillingEndRange(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) {
        if (start.isAfter(end)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        List<Debt> debts = debtService.getByBillingEndBetween(start, end);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-date-range")
    public ResponseEntity<List<Debt>> getDebtsByDateRange(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) {
        if (start.isAfter(end)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        List<Debt> debts = debtService.getByDateBetween(start, end);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-grossamount-greater")
    public ResponseEntity<List<Debt>> getDebtsWithGrossAmountGreaterThan(
        @RequestParam BigDecimal amount
    ) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        List<Debt> debts = debtService.getByGrossAmountGreaterThan(amount);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-grossamount-less")
    public ResponseEntity<List<Debt>> getDebtsWithGrossAmountLessThan(
        @RequestParam BigDecimal amount
    ) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        List<Debt> debts = debtService.getByGrossAmountLessThan(amount);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-grossamount-range")
    public ResponseEntity<List<Debt>> getDebtsWithGrossAmountBetween(
        @RequestParam BigDecimal min,
        @RequestParam BigDecimal max
    ) {
        if (min.compareTo(BigDecimal.ZERO) < 0 || max.compareTo(min) < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        List<Debt> debts = debtService.getByGrossAmountBetween(min, max);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-netamount-greater")
    public ResponseEntity<List<Debt>> getDebtsWithNetAmountGreaterThan(
        @RequestParam BigDecimal amount) {
        
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        List<Debt> debts = debtService.getByNetAmountGreaterThan(amount);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-netamount-less")
    public ResponseEntity<List<Debt>> getDebtsWithNetAmountLessThan(
        @RequestParam BigDecimal amount) {
        
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        List<Debt> debts = debtService.getByNetAmountLessThan(amount);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/by-netamount-range")
    public ResponseEntity<List<Debt>> getDebtsWithNetAmountBetween(
        @RequestParam BigDecimal min,
        @RequestParam BigDecimal max) {
        
        if (min.compareTo(BigDecimal.ZERO) < 0 || max.compareTo(min) < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        List<Debt> debts = debtService.getByNetAmountBetween(min, max);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/total-by-customer/{customerId}")
    public ResponseEntity<BigDecimal> getTotalDebtByCustomer(
        @PathVariable Long customerId) {
        
        try {
            BigDecimal total = debtService.calculateTotalDebtByCustomer(customerId);
            return new ResponseEntity<>(total, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/total-open-by-project/{projectId}")
    public ResponseEntity<BigDecimal> getTotalOpenAmountByProject(
        @PathVariable Long projectId) {
        
        try {
            BigDecimal total = debtService.calculateTotalOpenAmountByProject(projectId);
            return new ResponseEntity<>(total, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Debt>> getOverdueDebts(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate currentDate) {
        
        LocalDate referenceDate = (currentDate != null) ? currentDate : LocalDate.now();
        List<Debt> overdueDebts = debtService.findOverdueDebts(referenceDate);
        
        return new ResponseEntity<>(overdueDebts, HttpStatus.OK);
    }
}
