package com.iws_manager.iws_manager_api.models;

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
@Table(name = "costtype")
@AttributeOverride(name = "id", column = @Column(name = "costtypeid"))
public class CostType extends BaseEntity{

    @Column(name = "costtype", nullable = true, length = 255)
    private String type;

    @Column(name = "sequencemo", nullable = true)
    private Integer sequenceNo;
}
