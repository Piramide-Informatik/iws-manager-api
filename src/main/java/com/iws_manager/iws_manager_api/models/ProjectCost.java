package com.iws_manager.iws_manager_api.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.iws_manager.iws_manager_api.models.base.BaseEntity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @Column(name = "approveorplan", nullable = false)
    private Byte approveOrPlan;

    @Column(name = "costs", precision = 10, scale = 2)
    private BigDecimal costs;

    @Column(name = "projectid")
    private Integer projectId;

    @Column(name = "projectperiodid")
    private Integer projectPeriodId;
}