package com.iws_manager.iws_manager_api.models.base;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Base entity class that provides common fields and behavior for all JPA entities in the application.
 * <p>
 * This abstract class serves as the foundation for all domain entities, including:
 * <ul>
 *   <li>Auto-generated primary key (ID)</li>
 *   <li>Automatic audit tracking (creation and modification timestamps)</li>
 *   <li>Common JPA configuration</li>
 * </ul>
 * 
 * <p>Subclasses should extend this class and add their specific domain fields and relationships.
 *
 * @MappedSuperclass Indicates that this class provides mapping information for its subclasses,
 *                   but is not an entity itself.
 * @EntityListeners Enables automatic auditing through Spring Data JPA's auditing features.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class BaseEntity {

    /**
     * The primary key identifier for the entity.
     * <p>
     * Uses database auto-increment strategy for ID generation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    /**
     * Timestamp indicating when the entity was created.
     * <p>
     * Automatically set by Spring Data auditing on initial persist.
     * Cannot be modified after creation.
     */
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating when the entity was last modified.
     * <p>
     * Automatically updated by Spring Data auditing on each update.
     */
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

     /**
     * Version number for optimistic locking control.
     * <p>
     * This field is automatically managed by JPA to implement optimistic locking.
     * It gets incremented automatically on each update to the entity, preventing
     * concurrent modifications (lost updates).
     * 
     * <p>When an entity is updated, the version in memory is compared with the version
     * in the database. If they differ, an OptimisticLockException is thrown, indicating
     * the entity was modified by another transaction.
     */
    @Version
    @Column(name = "version", nullable = false, columnDefinition = "bigint default 0")
    private Long version;
}
