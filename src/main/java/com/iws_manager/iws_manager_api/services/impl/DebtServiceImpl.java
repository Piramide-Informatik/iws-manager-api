package com.iws_manager.iws_manager_api.services.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.Debt;
import com.iws_manager.iws_manager_api.repositories.DebtRepository;
import com.iws_manager.iws_manager_api.services.interfaces.DebtService;

/**
 * Implementation of the {@link DebtService} interface for managing Branch entities.
 * Provides CRUD operations and business logic for Debt management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class DebtServiceImpl implements DebtService {

    private final DebtRepository debtRepository;
    
    /**
     * Constructs a new DebtService with the required repository dependency.
     * 
     * @param debtRepository the repository for Debt entity operations
     */
    @Autowired
    public DebtServiceImpl(DebtRepository debtRepository) {
        this.debtRepository = debtRepository;
    }


    /**
     * Creates and persists a new Debt entity.
     * @param debt the Debt entity to be created
     * @return the persisted Debt entity with generated ID
     * @throws IllegalArgumentException if the Debt parameter is null
     */
    @Override
    public Debt create(Debt debt) {
        if (debt == null) {
            throw new IllegalArgumentException("Debt cannot be null");
        }
        return debtRepository.save(debt);
    }

    /**
     * Retrieves a Debt by its unique identifier.
     * 
     * @param id the ID of the Debt to retrieve
     * @return an Optional containing the found Debt, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Debt> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return debtRepository.findById(id);
    }

    /**
     * Retrieves all Debt entities from the database.
     * 
     * @return a List of all Debt entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Debt> findAll() {
        return debtRepository.findAllByOrderByDebtNoAsc(); 
    }

    /**
     * Updates an existing Debt entity.
     * 
     * @param id the ID of the Debt to update
     * @param branchDetails the Debt object containing updated fields
     * @return the updated Debt entity
     * @throws RuntimeException if no Debt exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public Debt update(Long id, Debt debtDetails) {
        if (id == null || debtDetails == null) {
            throw new IllegalArgumentException("ID and debt details cannot be null");
        }
        
        return  debtRepository.findById(id)
                .map(existingDebt -> {
                    existingDebt.setBillingEnd(debtDetails.getBillingEnd());
                    existingDebt.setBillingMonths(debtDetails.getBillingMonths());
                    existingDebt.setBillingStart(debtDetails.getBillingStart());
                    existingDebt.setComment(debtDetails.getComment());
                    existingDebt.setConfDateLevel1(debtDetails.getConfDateLevel1());
                    existingDebt.setConfDateLevel2(debtDetails.getConfDateLevel2());
                    existingDebt.setCustomer(debtDetails.getCustomer());
                    existingDebt.setDate(debtDetails.getDate());
                    existingDebt.setDebtNo(debtDetails.getDebtNo());
                    existingDebt.setDebtTitle(debtDetails.getDebtTitle());
                    existingDebt.setDonation(debtDetails.getDonation());
                    existingDebt.setFundinglabel(debtDetails.getFundinglabel());
                    existingDebt.setGrossAmount(debtDetails.getGrossAmount());
                    existingDebt.setIwsDeptAmount1(debtDetails.getIwsDeptAmount1());
                    existingDebt.setIwsDeptAmount2(debtDetails.getIwsDeptAmount2());
                    existingDebt.setIwsPercent(debtDetails.getIwsPercent());
                    existingDebt.setKmui0838(debtDetails.getKmui0838());
                    existingDebt.setKmui0848(debtDetails.getKmui0848());
                    existingDebt.setKmui0847(debtDetails.getKmui0847());
                    existingDebt.setKmui0850(debtDetails.getKmui0850());
                    existingDebt.setKmui0856(debtDetails.getKmui0856());
                    existingDebt.setKmui0860(debtDetails.getKmui0860());
                    existingDebt.setLastPaymentDate(debtDetails.getLastPaymentDate());
                    existingDebt.setNetAmount(debtDetails.getNetAmount());
                    existingDebt.setOpenAmount(debtDetails.getOpenAmount());
                    existingDebt.setOrder(debtDetails.getOrder());
                    existingDebt.setPayedAmount(debtDetails.getPayedAmount());
                    existingDebt.setProjectCosts(debtDetails.getProjectCosts());
                    existingDebt.setProjectEnd(debtDetails.getProjectEnd());
                    existingDebt.setProject(debtDetails.getProject());
                    existingDebt.setProjectStart(debtDetails.getProjectStart());
                    existingDebt.setPromoter(debtDetails.getPromoter());
                    existingDebt.setRequestNo(debtDetails.getRequestNo());
                    existingDebt.setTaxAmount(debtDetails.getTaxAmount());
                   
                    return debtRepository.save(existingDebt);
                })
                .orElseThrow(() -> new RuntimeException("Debt not found with id: " + id));
    }

    /**
     * Deletes a Debt entity by its ID.
     * 
     * @param id the ID of the Debt to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        debtRepository.deleteById(id);
    }

    //properties
    @Override
    public List<Debt> getByBillingEnd(LocalDate billingEnd) {
        return debtRepository.findByBillingEnd(billingEnd);
    }

    @Override
    public List<Debt> getByBillingMonths(Integer billingMonths) {
        return debtRepository.findByBillingMonths(billingMonths);
    }

    @Override
    public List<Debt> getByBillingStart(LocalDate billingStart) {
        return debtRepository.findByBillingStart(billingStart);
    }

    @Override
    public List<Debt> getByComment(String comment) {
        return debtRepository.findByComment(comment);
    }

    @Override
    public List<Debt> getByConfDateLevel1(LocalDate confDateLevel1) {
        return debtRepository.findByConfDateLevel1(confDateLevel1);
    }

    @Override
    public List<Debt> getByConfDateLevel2(LocalDate confDateLevel2) {
        return debtRepository.findByConfDateLevel2(confDateLevel2);
    }
   
    @Override
    public List<Debt> getByCustomerId(Long customerId) {
        return debtRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Debt> getByDate(LocalDate date) {
        return debtRepository.findByDate(date);
    }

    @Override
    public List<Debt> getByDebtNo(Integer debtNo) {
        return debtRepository.findByDebtNo(debtNo);
    }

    @Override
    public List<Debt> getByDebtTitle(String debtTitle) {
        return debtRepository.findByDebtTitle(debtTitle);
    }

    @Override
    public List<Debt> getByDonation(BigDecimal donation) {
        return debtRepository.findByDonation(donation);
    }

    @Override
    public List<Debt> getByFundinglabel(String fundinglabel) {
        return debtRepository.findByFundinglabel(fundinglabel);
    }

    @Override
    public List<Debt> getByGrossAmount(BigDecimal grossAmount) {
        return debtRepository.findByGrossAmount(grossAmount);
    }

    @Override
    public List<Debt> getByIwsDeptAmount1(BigDecimal iwsDeptAmount1) {
        return debtRepository.findByIwsDeptAmount1(iwsDeptAmount1);
    }

    @Override
    public List<Debt> getByIwsDeptAmount2(BigDecimal iwsDeptAmount2) {
        return debtRepository.findByIwsDeptAmount2(iwsDeptAmount2);
    }

    @Override
    public List<Debt> getByIwsPercent(BigDecimal iwsPercent) {
        return debtRepository.findByIwsPercent(iwsPercent);
    }

    @Override
    public List<Debt> getByKmui0838(BigDecimal kmui0838) {
        return debtRepository.findByKmui0838(kmui0838);
    }

    @Override
    public List<Debt> getByKmui0847(BigDecimal kmui0847) {
        return debtRepository.findByKmui0847(kmui0847);
    }

    @Override
    public List<Debt> getByKmui0848(BigDecimal kmui0848) {
        return debtRepository.findByKmui0848(kmui0848);
    }

    @Override
    public List<Debt> getByKmui0850(BigDecimal kmui0850) {
        return debtRepository.findByKmui0850(kmui0850);
    }

    @Override
    public List<Debt> getByKmui0856(BigDecimal kmui0856) {
        return debtRepository.findByKmui0856(kmui0856);
    }

    @Override
    public List<Debt> getByKmui0860(BigDecimal kmui0860) {
        return debtRepository.findByKmui0860(kmui0860);
    }

    @Override
    public List<Debt> getByLastPaymentDate(LocalDate lastPaymentDate) {
        return debtRepository.findByLastPaymentDate(lastPaymentDate);
    }

    @Override
    public List<Debt> getByNetAmount(BigDecimal netAmount) {
        return debtRepository.findByNetAmount(netAmount);
    }

    @Override
    public List<Debt> getByOpenAmount(BigDecimal openAmount) {
        return debtRepository.findByOpenAmount(openAmount);
    }

    @Override
    public List<Debt> getByOrderId(Long orderId) {
        return debtRepository.findByOrderId(orderId);
    }

    @Override
    public List<Debt> getByPayedAmount(BigDecimal payedAmount) {
        return debtRepository.findByPayedAmount(payedAmount);
    }

    @Override
    public List<Debt> getByProjectCosts(BigDecimal projectCosts) {
        return debtRepository.findByProjectCosts(projectCosts);
    }

    @Override
    public List<Debt> getByProjectEnd(LocalDate projectEnd) {
        return debtRepository.findByProjectEnd(projectEnd);
    }

    @Override
    public List<Debt> getByProjectId(Long projectId) {
        return debtRepository.findByProjectId(projectId);
    }

    @Override
    public List<Debt> getByProjectStart(LocalDate projectStart) {
        return debtRepository.findByProjectStart(projectStart);
    }

    @Override
    public List<Debt> getByPromoterId(Long promoterId) {
        return debtRepository.findByPromoterId(promoterId);
    }

    @Override
    public List<Debt> getByRequestNo(Integer requestNo) {
        return debtRepository.findByRequestNo(requestNo);
    }

    @Override
    public List<Debt> getByTaxAmount(BigDecimal taxAmount) {
        return debtRepository.findByTaxAmount(taxAmount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Debt> getByDebtTitleContainingIgnoreCase(String titlePart) {
        if (titlePart == null) {
            throw new IllegalArgumentException("Title part cannot be null");
        }
        return debtRepository.findByDebtTitleContainingIgnoreCase(titlePart);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Debt> getByCommentContainingIgnoreCase(String commentPart) {
        if (commentPart == null) {
            throw new IllegalArgumentException("Comment part cannot be null");
        }
        return debtRepository.findByCommentContainingIgnoreCase(commentPart);
    }

    /**
     * Finds debts where billing start date is between the specified dates (inclusive)
     * 
     * @param startDate the start of the date range (inclusive)
     * @param endDate the end of the date range (inclusive)
     * @return List of matching Debt entities
     * @throws IllegalArgumentException if dates are null or invalid
     */
    @Override
    @Transactional(readOnly = true)
    public List<Debt> getByBillingStartBetween(LocalDate startDate, LocalDate endDate) {
        validateDateRange(startDate, endDate);
        return debtRepository.findByBillingStartBetween(startDate, endDate);
    }

    /**
     * Finds debts where billing end date is between the specified dates (inclusive)
     * 
     * @param startDate the start of the date range (inclusive)
     * @param endDate the end of the date range (inclusive)
     * @return List of matching Debt entities
     * @throws IllegalArgumentException if dates are null or invalid
     */
    @Override
    @Transactional(readOnly = true)
    public List<Debt> getByBillingEndBetween(LocalDate startDate, LocalDate endDate) {
        validateDateRange(startDate, endDate);
        return debtRepository.findByBillingEndBetween(startDate, endDate);
    }

    /**
     * Finds debts where date is between the specified dates (inclusive)
     * 
     * @param startDate the start of the date range (inclusive)
     * @param endDate the end of the date range (inclusive)
     * @return List of matching Debt entities
     * @throws IllegalArgumentException if dates are null or invalid
     */
    @Override
    @Transactional(readOnly = true)
    public List<Debt> getByDateBetween(LocalDate startDate, LocalDate endDate) {
        validateDateRange(startDate, endDate);
        return debtRepository.findByDateBetween(startDate, endDate);
    }

    /**
     * Validates that a date range is valid
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @throws IllegalArgumentException if dates are null or invalid
     */
    private void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Both start and end dates must be provided");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }
    }

    /**
     * Finds debts with gross amount greater than the specified value
     * 
     * @param amount the minimum amount (exclusive)
     * @return List of matching Debt entities
     * @throws IllegalArgumentException if amount is null or negative
     */
    @Override
    @Transactional(readOnly = true)
    public List<Debt> getByGrossAmountGreaterThan(BigDecimal amount) {
        validateAmount(amount);
        return debtRepository.findByGrossAmountGreaterThan(amount);
    }

    /**
     * Finds debts with gross amount less than the specified value
     * 
     * @param amount the maximum amount (exclusive)
     * @return List of matching Debt entities
     * @throws IllegalArgumentException if amount is null or negative
     */
    @Override
    @Transactional(readOnly = true)
    public List<Debt> getByGrossAmountLessThan(BigDecimal amount) {
        validateAmount(amount);
        return debtRepository.findByGrossAmountLessThan(amount);
    }

    /**
     * Finds debts with gross amount between the specified values (inclusive)
     * 
     * @param minAmount the minimum amount (inclusive)
     * @param maxAmount the maximum amount (inclusive)
     * @return List of matching Debt entities
     * @throws IllegalArgumentException if amounts are null, negative or invalid range
     */
    @Override
    @Transactional(readOnly = true)
    public List<Debt> getByGrossAmountBetween(BigDecimal minAmount, BigDecimal maxAmount) {
        validateAmountRange(minAmount, maxAmount);
        return debtRepository.findByGrossAmountBetween(minAmount, maxAmount);
    }

    /**
     * Validates that an amount is valid (not null and non-negative)
     * 
     * @param amount the amount to validate
     * @throws IllegalArgumentException if amount is invalid
     */
    private void validateAmount(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
    }

    /**
     * Validates that an amount range is valid
     * 
     * @param minAmount the minimum amount
     * @param maxAmount the maximum amount
     * @throws IllegalArgumentException if amounts are invalid or range is incorrect
     */
    private void validateAmountRange(BigDecimal minAmount, BigDecimal maxAmount) {
        validateAmount(minAmount);
        validateAmount(maxAmount);
        
        if (minAmount.compareTo(maxAmount) > 0) {
            throw new IllegalArgumentException("Minimum amount must be less than or equal to maximum amount");
        }
    }

    /**
     * Finds debts with net amount greater than the specified value
     * 
     * @param amount the minimum amount (exclusive)
     * @return List of matching Debt entities
     * @throws IllegalArgumentException if amount is null or negative
     */
    @Override
    @Transactional(readOnly = true)
    public List<Debt> getByNetAmountGreaterThan(BigDecimal amount) {
        validateAmount(amount);
        return debtRepository.findByNetAmountGreaterThan(amount);
    }

    /**
     * Finds debts with net amount less than the specified value
     * 
     * @param amount the maximum amount (exclusive)
     * @return List of matching Debt entities
     * @throws IllegalArgumentException if amount is null or negative
     */
    @Override
    @Transactional(readOnly = true)
    public List<Debt> getByNetAmountLessThan(BigDecimal amount) {
        validateAmount(amount);
        return debtRepository.findByNetAmountLessThan(amount);
    }

    /**
     * Finds debts with net amount between the specified values (inclusive)
     * 
     * @param minAmount the minimum amount (inclusive)
     * @param maxAmount the maximum amount (inclusive)
     * @return List of matching Debt entities
     * @throws IllegalArgumentException if amounts are null, negative or invalid range
     */
    @Override
    @Transactional(readOnly = true)
    public List<Debt> getByNetAmountBetween(BigDecimal minAmount, BigDecimal maxAmount) {
        validateAmountRange(minAmount, maxAmount);
        return debtRepository.findByNetAmountBetween(minAmount, maxAmount);
    }

    /**
     * Calculates the total debt amount (sum of grossAmount) for a specific customer
     * 
     * @param customerId the ID of the customer
     * @return total debt amount as BigDecimal
     * @throws IllegalArgumentException if customerId is null
     */
    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateTotalDebtByCustomer(Long customerId) {
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }
        
        BigDecimal total = debtRepository.sumGrossAmountByCustomer(customerId);
        return total != null ? total : BigDecimal.ZERO;
    }

    /**
     * Calculates the total open amount (sum of grossAmount - payedAmount) for a specific project
     * 
     * @param projectId the ID of the project
     * @return total open amount as BigDecimal
     * @throws IllegalArgumentException if projectId is null
     */
    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateTotalOpenAmountByProject(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        
        BigDecimal total = debtRepository.sumOpenAmountByProject(projectId);
        return total != null ? total : BigDecimal.ZERO;
    }

    /**
     * Finds all overdue debts (where date < currentDate AND grossAmount > payedAmount)
     * 
     * @param currentDate the reference date to determine overdue debts
     * @return List of overdue Debt entities ordered by date
     * @throws IllegalArgumentException if currentDate is null
     */
    @Override
    @Transactional(readOnly = true)
    public List<Debt> findOverdueDebts(LocalDate currentDate) {
        if (currentDate == null) {
            throw new IllegalArgumentException("Current date cannot be null");
        }
        
        return debtRepository.findOverdueDebts(currentDate);
    }
}