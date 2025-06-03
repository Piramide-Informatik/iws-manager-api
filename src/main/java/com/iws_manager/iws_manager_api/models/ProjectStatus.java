package com.iws_manager.iws_manager_api.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.iws_manager.iws_manager_api.models.base.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a German federal project status (Bundesland).
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "projectstatus")
@AttributeOverride(name = "id", column = @Column(name = "statusid"))
public class ProjectStatus extends BaseEntity{

    /**
     * Official name of the project-status (e.g., "Bavaria", "Berlin").
     */
    @Column(name = "projectstatus", nullable = false)
    private String name;
}