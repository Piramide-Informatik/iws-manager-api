package com.iws_manager.iws_manager_api.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.iws_manager.iws_manager_api.models.base.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "debt")
@AttributeOverride(name = "id", column = @Column(name = "debtid"))
public class Debt extends BaseEntity{

    @Column(name = "billingend", nullable = true, columnDefinition = "DATE")
    private LocalDate billingEnd;
    
    @Column(name = "billingmonths", nullable = true, columnDefinition = "SMALLINT")
    private Integer billingMonths;

    @Column(name = "billingstart", nullable = true, columnDefinition = "DATE")
    private LocalDate billingStart;
    
    @Column(name = "`comment`", length = 255)
    private String comment;
    
    @Column(name = "confdatelevel1", nullable = true, columnDefinition = "DATE")
    private LocalDate confDateLevel1;

    @Column(name = "confdatelevel2", nullable = true, columnDefinition = "DATE")
    private LocalDate confDateLevel2;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerid", referencedColumnName = "customerid")
    private Customer customer;

    @Column(name = "`date`", nullable = true, columnDefinition = "DATE")
    private LocalDate date;

    @Column(name = "debtno", nullable = true)
    private Integer debtNo;

    @Column(name = "debttitle", nullable = true, length = 255)
    private String debtTitle;

    @Column(name = "donation", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal donation;

    @Column(name = "fundinglabel", nullable = true, length = 255)
    private String fundinglabel;

    @Column(name = "grossamount", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal grossAmount;

    @Column(name = "iwsdeptamount1", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal iwsDeptAmount1;

    @Column(name = "iwsdeptamount2", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal iwsDeptAmount2;

    @Column(name = "iwspercent", nullable = true, columnDefinition = "DECIMAL(5,2)")
    private BigDecimal iwsPercent;

    @Column(name = "kmui0838", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal kmui0838;

    @Column(name = "kmui0848", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal kmui0848;

    @Column(name = "kmui0847", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal kmui0847;

    @Column(name = "kmui0850", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal kmui0850;

    @Column(name = "kmui0856", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal kmui0856;

    @Column(name = "kmui0860", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal kmui0860;

    @Column(name = "lastpaymentdate", nullable = true, columnDefinition = "DATE")
    private LocalDate lastPaymentDate;

    @Column(name = "netamount", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal netAmount;

    @Column(name = "openamount", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal openAmount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orderid", referencedColumnName = "orderid")
    private Order order;

    @Column(name = "payedamount", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal payedAmount;

    @Column(name = "projectcosts", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal projectCosts;

    @Column(name = "projectend", nullable = true, columnDefinition = "DATE")
    private LocalDate projectEnd;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "projectid", referencedColumnName = "projectid")
    private Project project;

    @Column(name = "projectstart", nullable = true, columnDefinition = "DATE")
    private LocalDate projectStart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "promoterid", referencedColumnName = "promoterid")
    private Promoter promoter;

    @Column(name = "requestno", nullable = true)
    private Integer requestNo;
    
    @Column(name = "taxamount", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal taxAmount;
}
