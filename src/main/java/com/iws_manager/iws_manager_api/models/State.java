package com.iws_manager.iws_manager_api.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.iws_manager.iws_manager_api.models.base.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a German federal state (Bundesland).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "state")
public class State extends BaseEntity{

    /**
     * Official name of the state (e.g., "Bavaria", "Berlin", "Hamburg").
     */
    @Column(name = "name", nullable = false)
    private String name;
}