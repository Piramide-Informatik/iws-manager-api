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
@Table(name = "projectcostcenter")
@AttributeOverride(name = "id", column = @Column(name = "projectcostcenterid"))
public class ProjectCostCenter extends BaseEntity{

    @Column(name = "costcenter", length = 255)
    private String costCenter;

    @Column(name = "kmuino", length = 255)
    private String kmuino;

    @Column(name = "sequenceno")
    private Integer sequenceno;
}
