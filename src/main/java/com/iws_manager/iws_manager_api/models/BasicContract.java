package com.iws_manager.iws_manager_api.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.iws_manager.iws_manager_api.models.base.BaseEntity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "basiccontract")
@AttributeOverride(name = "id", column = @Column(name = "basiccontractid"))
public class BasicContract extends BaseEntity{

    @Column(name = "confirmationdate", nullable =  true, columnDefinition = "DATE")
    private LocalDate confirmationDate;

    @Column(name = "contractlabel", nullable =  true, length = 255)
    private String contractLabel;

    @Column(name = "contractno", nullable =  true)
    private Integer contractNo;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contractstatusid", referencedColumnName = "contractstatusid")
    private ContractStatus contractStatus;

    @Column(name = "contracttitle", nullable =  true, length = 255)
    private String contractTitle;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerid", referencedColumnName = "customerid")
    private Customer customer;

    @Column(name = "`date`", nullable =  true, columnDefinition = "DATE")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fundingprogramid", referencedColumnName = "fundingprogramid")
    private FundingProgram fundingProgram;

    // @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "iwsemployeeid", referencedColumnName = "iwsemployeeid")
    // private IwsEmployee iwsEmployee;
}
