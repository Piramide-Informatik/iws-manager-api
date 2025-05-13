package com.iws_manager.iws_manager_api.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
public class State {

    /**
     * Unique identifier of the state.
     */
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Official name of the state (e.g., "Bavaria", "Berlin", "Hamburg").
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Timestamp of when the record was created.
     */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp of the last update to the record.
     */
    @LastModifiedDate
    private LocalDateTime updatedAt;
}