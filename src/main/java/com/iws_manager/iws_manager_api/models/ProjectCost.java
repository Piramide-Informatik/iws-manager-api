package com.iws_manager.iws_manager_api.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.iws_manager.iws_manager_api.models.base.BaseEntity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "projectcost")
@AttributeOverride(name = "id", column = @Column(name = "projectcostid"))
public class ProjectCost extends BaseEntity {

    @Column(name = "approveorplan", columnDefinition = "TINYINT")
    private Byte approveOrPlan;

    @Column(name = "costs", precision = 10, scale = 2)
    private BigDecimal costs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectid", referencedColumnName = "projectid")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectperiodid", referencedColumnName = "projectperiodid")
    private ProjectPeriod projectPeriod;
}