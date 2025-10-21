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
@Table(name = "vat")
@AttributeOverride(name = "id", column = @Column(name = "vatid"))
public class Vat extends BaseEntity {

    @Column(name = "vatlabel", nullable = true, length = 255)
    private String label;
}