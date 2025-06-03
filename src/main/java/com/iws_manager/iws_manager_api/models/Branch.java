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
@Table(name = "branch")
@AttributeOverride(name = "id", column = @Column(name = "branchid"))
public class Branch extends BaseEntity{

    @Column(name = "branch", length = 255)
    private String name;
}
