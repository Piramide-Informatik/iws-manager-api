package com.iws_manager.iws_manager_api.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.iws_manager.iws_manager_api.models.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@EntityListeners(AuditingEntityListener.class)
@Table(name = "vatrate")
@AttributeOverride(name = "id", column = @Column(name = "vatrateid"))
public class VatRate extends BaseEntity {

    @Column(name = "fromdate", nullable = true, columnDefinition = "DATE")
    private LocalDate fromdate;

    @Column(name = "rate", nullable = true, columnDefinition = "DECIMAL(5,2)")
    private BigDecimal rate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vatid", referencedColumnName = "vatid", nullable = true )
    private Vat vat;
}