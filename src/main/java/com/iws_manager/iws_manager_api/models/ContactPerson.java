package com.iws_manager.iws_manager_api.models;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "contactPerson")
public class ContactPerson {

    @Id // contactPersonId
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(updatable = false, nullable = false, unique = true, length = 36)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @JoinColumn(name = "forInvoincing", nullable = false)
    private Integer forInvoincing;

    @JoinColumn(name = "function", nullable = false)
    private String function;

    @Column(name = "lastName", nullable = false)
    private String lastName;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salutation_id", nullable = false)
    private Salutation salutation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "title_id", nullable = false)
    private Title title;
}