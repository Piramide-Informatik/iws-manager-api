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
@Table(name = "paycondition")
@AttributeOverride(name = "id", column = @Column(name = "payconditionid"))
public class PayCondition extends BaseEntity {

    @Column(name = "deadline", nullable = true)
    private Integer deadline;

    @Column(name = "paycondition", nullable = true, length = 255)
    private String name;
}