package com.iws_manager.iws_manager_api.models;

import java.math.BigDecimal;

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
@Table(name = "ordercommission")
@AttributeOverride(name = "id", column = @Column(name = "ordercommissionid"))
public class OrderCommission extends BaseEntity{

    @Column(name = "commission", nullable = true, columnDefinition = "DECIMAL(5,2)")
    private BigDecimal commission;

    @Column(name = "fromordervalue", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal fromOrderValue;

    @Column(name = "mincommission", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal minCommission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderid", referencedColumnName = "orderid")
    private Order order;
}
