package com.iws_manager.iws_manager_api.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a contact person associated with a customer.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "contactPerson")
public class ContactPerson {

    /**
     * Unique identifier of the contact person.
     */
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Customer to whom the contact person is linked.
     */
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    /**
     * Title of the contact person (e.g., Mr., Mrs.).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "title_id", nullable = false)
    private Title title;

    /**
     * Salutation of the contact person (e.g., Dr., Eng.).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salutation_id", nullable = false)
    private Salutation salutation;

    /**
     * First name of the contact person.
     */
    @Column(name = "firstName", nullable = false)
    private String firstName;

    /**
     * Last name of the contact person.
     */
    @Column(name = "lastName", nullable = false)
    private String lastName;

    /**
     * Indicates whether the contact person is responsible for invoicing.
     */
    @Column(name = "forInvoincing", nullable = false)
    private Integer forInvoincing;

    /**
     * Job position or role of the contact person.
     */
    @Column(name = "function", nullable = false)
    private String function;

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
