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
@Table(name = "contractstatus")
@AttributeOverride(name = "id", column = @Column(name = "contractstatusid"))
public class ContractStatus extends BaseEntity{

    @Column(name = "chance", nullable = true, columnDefinition = "DECIMAL(5,2)")
    private BigDecimal chance;
    
    @Column(name = "contractstatus", nullable = true, length = 255)
    private String contractStatus;
}
