package com.iws_manager.iws_manager_api.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.iws_manager.iws_manager_api.models.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity representing a contact person associated with a customer.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "contactperson")
public class ContactPerson extends BaseEntity{

    /**
     * Customer to whom the contact person is linked.
     */
    @ManyToOne
    @JoinColumn(name = "customerid", referencedColumnName = "id", nullable = false)
    private Customer customer;

    /**
     * First name of the contact person.
     */
    @Column(name = "firstname", nullable = true, length = 255)
    private String firstName;

    /**
     * Indicates whether the contact person is responsible for invoicing.
     */
    @Column(name = "forinvoincing", nullable = true)
    private Integer forInvoincing;

    /**
     * Job position or role of the contact person.
     */
    @Column(name = "`function`", nullable = true, length = 255)
    private String function;

    /**
     * Last name of the contact person.
     */
    @Column(name = "lastname", nullable = true, length = 255)
    private String lastName;

    /**
     * Salutation of the contact person (e.g., Dr., Eng.).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salutationid",  referencedColumnName = "id", nullable = false)
    private Salutation salutation;

    /**
     * Title of the contact person (e.g., Mr., Mrs.).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "titleid",  referencedColumnName = "id", nullable = false)
    private Title title;
}
