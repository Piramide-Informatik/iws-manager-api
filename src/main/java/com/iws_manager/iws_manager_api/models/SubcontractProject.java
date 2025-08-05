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
@Table(name = "subcontractproject")
@AttributeOverride(name = "id", column = @Column(name = "subcontractprojectid"))
public class SubcontractProject extends BaseEntity{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subcontractyearid", referencedColumnName = "subcontractyearid")
    private SubcontractYear subcontractYear;

    @Column(name = "amount", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal amount;

    @Column(name = "months", nullable = true, columnDefinition = "TINYINT")
    private Integer months;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "projectid", referencedColumnName = "projectid")
    private Project project;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subcontractid", referencedColumnName = "subcontractid")
    private Subcontract subcontract;

    @Column(name = "`share`", nullable = true, columnDefinition = "DECIMAL(5,2)")
    private BigDecimal share;
    
    @Column(name = "`year`", nullable = true, columnDefinition = "DATE")
    private LocalDate year;
}
