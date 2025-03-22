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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId", nullable = false)
    private Integer customerId;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @JoinColumn(name = "forInvoincing", nullable = false)
    private Integer forInvoincing;

    @JoinColumn(name = "function", nullable = false)
    private String function;

    @Column(name = "lastName", nullable = false)
    private String lastName;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salutationId", nullable = false)
    private Integer salutationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "titleId", nullable = false)
    private Integer titleId;
}