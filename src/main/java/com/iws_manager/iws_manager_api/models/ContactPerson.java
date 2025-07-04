package com.iws_manager.iws_manager_api.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.iws_manager.iws_manager_api.models.base.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

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
@AttributeOverride(name = "id", column = @Column(name = "contactpersonid"))
public class ContactPerson extends BaseEntity{

    /**
     * Customer to whom the contact person is linked.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerid", referencedColumnName = "customerid")
    private Customer customer;

    /**
     * Email of the contact person.
     */
    @Column(name = "email", length = 255)
    private String email;

    /**
     * First name of the contact person.
     */
    @Column(name = "firstname", length = 255)
    private String firstName;

    /**
     * Indicates whether the contact person is responsible for invoicing.
     */
    @Column(name = "forinvoicing")
    private Integer forInvoicing;

    /**
     * Job position or role of the contact person.
     */
    @Column(name = "`function`", length = 255)
    private String function;

    /**
     * Last name of the contact person.
     */
    @Column(name = "lastname", length = 255)
    private String lastName;

    /**
     * Salutation of the contact person (e.g., Dr., Eng.).
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "salutationid",  referencedColumnName = "salutationid")
    private Salutation salutation;

    /**
     * Title of the contact person (e.g., Mr., Mrs.).
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "titleid",  referencedColumnName = "titleid")
    private Title title;
}
