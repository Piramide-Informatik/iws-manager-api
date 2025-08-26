package com.iws_manager.iws_manager_api.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.iws_manager.iws_manager_api.models.base.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@EqualsAndHashCode(callSuper = true, exclude = {"biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat"})
@EntityListeners(AuditingEntityListener.class)
@Table(name = "invoice")
@AttributeOverride(name = "id", column = @Column(name = "invoiceid"))
public class Invoice extends BaseEntity {

    @Column(name = "amountgross", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal amountGross;

    @Column(name = "amountnet", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal amountNet;

    @Column(name = "amountopen", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal amountOpen;

    @Column(name = "amountpaid", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal amountPaid;

    @Column(name = "amounttax", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal amountTax;

    // @ManyToOne
    // @JoinColumn(name = "billerid", referencedColumnName = "billerid", nullable = true)
    // private Biller biller;

    @ManyToOne
    @JoinColumn(name = "cancelledinvoiceid", referencedColumnName = "invoiceid", nullable = true)
    private Invoice cancelledInvoice;

    @Column(name = "comment", nullable = true, length = 255)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "customerid", referencedColumnName = "customerid", nullable = true)
    private Customer customer;

    @Column(name = "invoicedate", nullable = true)
    private Integer invoiceDate;

    @Column(name = "invoiceno", nullable = true)
    private Integer invoiceNo;

    @Lob
    @Column(name = "invoicepdf", nullable = true, columnDefinition = "BLOB")
    private byte[] invoicePdf;

    @Column(name = "invoicetitle", nullable = true, length = 255)
    private String invoiceTitle;

    // @ManyToOne
    // @JoinColumn(name = "invoicetypeid", referencedColumnName = "invoicetypeid", nullable = true)
    // private InvoiceType invoiceType;

    @Column(name = "iscancellation", nullable = true, columnDefinition = "SMALLINT")
    private Short isCancellation;

    // @ManyToOne
    // @JoinColumn(name = "networkid", referencedColumnName = "networkid", nullable = true)
    // private Network network;

    @Column(name = "note", columnDefinition = "TEXT", nullable = true)
    private String note;

    @ManyToOne
    @JoinColumn(name = "orderid", referencedColumnName = "orderid", nullable = true)
    private Order order;

    // @ManyToOne
    // @JoinColumn(name = "payconditionid", referencedColumnName = "payconditionid", nullable = true)
    // private PayCondition payCondition;

    @Column(name = "paydeadline", nullable = true, columnDefinition = "DATE")
    private LocalDate payDeadline;

    @Column(name = "paymentdate", nullable = true, columnDefinition = "DATE")
    private LocalDate paymentDate;

    @Column(name = "taxrate", nullable = true, columnDefinition = "DECIMAL(5,2)")
    private BigDecimal taxRate;

    // @ManyToOne
    // @JoinColumn(name = "vatid", referencedColumnName = "vatid", nullable = true)
    // private Vat vat;
}