package com.iws_manager.iws_manager_api.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.iws_manager.iws_manager_api.models.base.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a German federal state (Bundesland).
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "state")
@AttributeOverride(name = "id", column = @Column(name = "stateid"))
public class State extends BaseEntity{

    /**
     * Official name of the state (e.g., "Bavaria", "Berlin", "Hamburg").
     */
    @Column(name = "statename", nullable = true, length = 255)
    private String name;
}