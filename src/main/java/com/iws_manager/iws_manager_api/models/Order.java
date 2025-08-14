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
@Table(name = "`order`")
@AttributeOverride(name = "id", column = @Column(name = "orderid"))
public class Order extends BaseEntity{

    @Column(name = "acronym", nullable = true, length = 255)
    private String acronym;
    
    @Column(name = "approvaldate", nullable = true, columnDefinition = "DATE")
    private LocalDate approvalDate;

    @Lob
    @Column(name = "approvalpdf", nullable = true, columnDefinition = "BLOB")
    private byte[] approvalPdf;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "approvalstatusid", referencedColumnName = "approvalstatusid")
    private ApprovalStatus approvalStatus;
    
    // @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "basiccontractid", referencedColumnName = "basiccontractid")
    // private BasicContract basiccontract;
    
    @Column(name = "contractdata1", nullable = true, length = 255)
    private String contractData1;
    
    @Column(name = "contractdata2", nullable = true, length = 255)
    private String contractData2;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contractorid", referencedColumnName = "contractorid")
    private Contractor contractor;
    
    @Lob
    @Column(name = "contractpdf", nullable = true, columnDefinition = "BLOB")
    private byte[] contractPdf;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contractstatusid", referencedColumnName = "contractstatusid")
    private ContractStatus contractStatus;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerid", referencedColumnName = "customerid")
    private Customer customer;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employeeiwsid", referencedColumnName = "employeeiwsid")
    private EmployeeIws employeeIws;
    
    @Column(name = "fixcommission", nullable = true, columnDefinition = "DECIMAL(5,2)")
    private BigDecimal fixCommission;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fundingprogramid", referencedColumnName = "fundingprogramid")
    private FundingProgram fundingProgram;
    
    @Column(name = "iwsprovision", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal iwsProvision;
    
    @Column(name = "maxcommission", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal maxCommission;
    
    @Column(name = "nextdeptdate", nullable = true, columnDefinition = "DATE")
    private LocalDate nextDeptDate;
    
    @Column(name = "noofdepts", nullable = true, columnDefinition = "SMALLINT")
    private Integer noOfDepts;
    
    @Column(name = "orderdate", nullable = true, columnDefinition = "DATE")
    private LocalDate orderDate;
    
    @Column(name = "orderlabel", nullable = true, length = 255)
    private String orderLabel;
    
    @Column(name = "orderno", nullable = true)
    private Integer orderNo;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "ordertypeid", 
        referencedColumnName = "costtypeid",
        foreignKey = @ForeignKey(name = "ordertypeid_ordertypeid")
    )
    private CostType orderType;
    
    @Column(name = "ordertitle", nullable = true, length = 255)
    private String orderTitle;
    
    @Column(name = "ordervalue", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal orderValue;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "projectid", referencedColumnName = "projectid")
    private Project project;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "promoterid", referencedColumnName = "promoterid")
    private Promoter promoter;
    
    @Column(name = "signaturedate", nullable = true, columnDefinition = "DATE")
    private LocalDate signatureDate;
    
}
