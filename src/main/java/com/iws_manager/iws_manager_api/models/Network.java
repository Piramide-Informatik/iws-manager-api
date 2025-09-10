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
@Table(name = "network")
@AttributeOverride(name = "id", column = @Column(name = "networkid"))
public class Network extends BaseEntity {

    @Column(name = "network", nullable = true, length = 255)
    private String name;
}