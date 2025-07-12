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
@Table(name = "subcontract")
@AttributeOverride(name = "id", column = @Column(name = "subcontractid"))
public class Subcontract extends BaseEntity{

    @Column(name = "afamonths", nullable = true, columnDefinition = "SMALLINT")
    private Integer afamonths;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contractorid", referencedColumnName = "contractorid")
    private Contractor contractor;

    @Column(name = "contracttitle", length = 255)
    private String contractTitle;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerid", referencedColumnName = "customerid")
    private Customer customer;

    @Column(name = "`date`", nullable = true, columnDefinition = "DATE")
    private LocalDate date;

    @Column(name = "`description`", length = 255)
    private String description;

    @Column(name = "invoiceamount", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal invoiceAmount;

    @Column(name = "invoicedate", length = 255)
    private LocalDate invoiceDate;

    @Column(name = "invoicegross", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal invoiceGross;

    @Column(name = "invoicenet", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal invoiceNet;

    @Column(name = "invoiceno", length = 255)
    private String invoiceNo;

    @Column(name = "isafa", nullable = true, columnDefinition = "SMALLINT")
    private Boolean isAfa;

    @Column(name = "netorgross", nullable = true, columnDefinition = "TINYINT")
    private Boolean netOrGross;

    @Column(name = "note", length = 255)
    private String note;

    /* Implement project cost center */
    // @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "projectcostcenterid", referencedColumnName = "projectcostcenterid")
    // private ProjectCostCenter projectCostCenter;
}
