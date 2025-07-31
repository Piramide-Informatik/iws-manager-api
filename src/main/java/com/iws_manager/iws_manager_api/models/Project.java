package com.iws_manager.iws_manager_api.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.iws_manager.iws_manager_api.models.base.BaseEntity;

import jakarta.persistence.*;
import jakarta.websocket.Decoder;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "project")
@AttributeOverride(name = "id", column = @Column(name = "projectid"))
public class Project extends BaseEntity{

    @Column(name = "approvaldate", nullable = true, columnDefinition = "DATE")
    private LocalDate approvalDate;

    @Column(name = "authorisationdate", nullable = true, columnDefinition = "DATE")
    private LocalDate authorizationDate;

    @Column(name = "chance", nullable = true, columnDefinition = "DECIMAL(5,2)")
    private BigDecimal chance;

    @Column(name = "`comment`", nullable = true, length = 255)
    private String comment;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerid", referencedColumnName = "customerid")
    private Customer customer;

    @Column(name = "date1", nullable = true, columnDefinition = "DATE")
    private LocalDate date1;

    @Column(name = "date2", nullable = true, columnDefinition = "DATE")
    private LocalDate date2;

    @Column(name = "date3", nullable = true, columnDefinition = "DATE")
    private LocalDate date3;
    
    @Column(name = "date4", nullable = true, columnDefinition = "DATE")
    private LocalDate date4;

    @Column(name = "date5", nullable = true, columnDefinition = "DATE")
    private LocalDate date5;

    @Column(name = "datelevel1", nullable = true, columnDefinition = "DATE")
    private LocalDate dateLevel1;

    @Column(name = "datelevel2", nullable = true, columnDefinition = "DATE")
    private LocalDate dateLevel2;

    @Column(name = "donation", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal donation;

    // @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "empiws20id", referencedColumnName = "empiws20id")
    // private Empiws20 empiws20;

    // @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "empiws30id", referencedColumnName = "empiws30id")
    // private Empiws30 empiws30;

    // @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "empiws50id", referencedColumnName = "empiws50id")
    // private Empiws50 empiws50;
 
    @Column(name = "endapproval", nullable = true, columnDefinition = "DATE")
    private LocalDate endApproval;

    @Column(name = "enddate", nullable = true, columnDefinition = "DATE")
    private LocalDate endDate;

    @Column(name = "fincanceauthority", length = 255)
    private String fincanceAuthority;

    @Column(name = "fundinglabel", length = 255)
    private String fundingLabel;

    // @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "fundingprogramid", referencedColumnName = "fundingprogramid")
    // private FundingProgram fundingprogram;

    @Column(name = "fundingrate", nullable = true, columnDefinition = "DECIMAL(5,2)")
    private BigDecimal fundingRate;

    @Column(name = "hourlyratemueu", nullable = true, columnDefinition = "DECIMAL(5,2)")
    private BigDecimal hourlyRateMueu;

    @Column(name = "income1", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal income1;

    @Column(name = "income2", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal income2;

    @Column(name = "income3", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal income3;

    @Column(name = "income4", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal income4;

    @Column(name = "income5", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal income5;

    @Column(name = "maxhourspermonth", nullable = true, columnDefinition = "DECIMAL(5,2)")
    private BigDecimal maxHoursPerMonth;

    @Column(name = "maxhoursperyear", nullable = true, columnDefinition = "DECIMAL(5,2)")
    private BigDecimal maxHoursPerYear;

    // @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "networkid", referencedColumnName = "networkid")
    // private Network network;

    @Column(name = "orderidfue", nullable = true)
    private Integer orderIdFue;

    @Column(name = "orderidadmin", nullable = true)
    private Integer orderIdAdmin;

    @Column(name = "stuffflat", nullable = true, columnDefinition = "DECIMAL(5,2)")
    private BigDecimal stuffFlat;

    @Column(name = "productivehoursperyear", nullable = true, columnDefinition = "DECIMAL(8,2)")
    private BigDecimal productiveHoursPerYear;

    @Column(name = "projectlabel", length = 255)
    private String projectLabel;

    @Column(name = "projectname", length = 255)
    private String projectName;
    
    // @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "promoterid", referencedColumnName = "promoterid")
    // private Promoter promoter;

    @Column(name = "note", nullable = true, columnDefinition = "TEXT")
    private String note;

    @Column(name = "shareresearch", nullable = true, columnDefinition = "DECIMAL(5,2)")
    private BigDecimal shareResearch;

    @Column(name = "startapproval", nullable = true, columnDefinition = "DATE")
    private LocalDate startApproval;

    @Column(name = "startdate", nullable = true, columnDefinition = "DATE")
    private LocalDate startDate;
    
    // @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "statusid", referencedColumnName = "statusid")
    // private Status status;

    @Column(name = "title", length = 255)
    private String title;
}
