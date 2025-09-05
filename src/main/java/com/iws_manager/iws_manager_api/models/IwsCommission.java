package com.iws_manager.iws_manager_api.models;

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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "iwscommission")
@AttributeOverride(name = "id", column = @Column(name = "iwscommissionid"))
public class IwsCommission extends BaseEntity {

    @Column(name = "commission", nullable = true, columnDefinition = "DECIMAL(5,2)")
    private BigDecimal commission;

    @Column(name = "fromordervalue", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal fromOrderValue;

    @Column(name = "invoicetext", nullable = true, columnDefinition = "TEXT")
    private String invoiceText;

    @Column(name = "mincommission", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal minCommission;

    @Column(name = "paydeadline")
    private Integer payDeadline;
}
