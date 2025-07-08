package com.iws_manager.iws_manager_api.models;

import java.time.LocalDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.iws_manager.iws_manager_api.models.base.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a contract associated with a customer.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "employmentcontract")
@AttributeOverride(name = "id", column = @Column(name = "contractid"))
public class EmploymentContract extends BaseEntity{

    /**
     * Customer to whom the contract is linked.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerid", referencedColumnName = "customerid")
    private Customer customer;

    /**
     * Customer to whom the contract is linked.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employeeid", referencedColumnName = "employeeid")
    private Employee employee;

    /**
     * HourlyRate of the contract.
     */
   @Column(name = "hourlyrate", nullable = true, columnDefinition = "DECIMAL(5,2)")
    private Double hourlyRate;

    /**
     * HourlyRealRate of the contract.
     */
   @Column(name = "hourlyrealrate", nullable = true, columnDefinition = "DECIMAL(5,2)")
    private Double hourlyRealRate;

    /**
     * HoursPerWeek of the contract.
     */
   @Column(name = "hoursperweek", nullable = true, columnDefinition = "DECIMAL(5,2)")
    private Double hoursPerWeek;

    /**
     * MaxHoursPerDay of the contract.
     */
   @Column(name = "maxhoursperday", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private Double maxHoursPerDay;

    /**
     * MaxHoursPerMonth of the contract.
     */
   @Column(name = "maxhourspermonth", nullable = true, columnDefinition = "DECIMAL(5,2)")
    private Double maxHoursPerMonth;

    /**
     * Salary per month of the contract.
     */
   @Column(name = "salarypermonth", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private Double salaryPerMonth;

    /**
     * Special payment of the contract.
     */
   @Column(name = "specialpayment", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private Double specialPayment;

    /**
     * Indicates whether the contract is responsible for invoicing.
     */
    @Column(name = "startdate")
    private LocalDate startDate;

     /**
     * Work short time of the contract.
     */
   @Column(name = "workshorttime", nullable = true, columnDefinition = "DECIMAL(5,2)")
    private Double workShortTime;
}
