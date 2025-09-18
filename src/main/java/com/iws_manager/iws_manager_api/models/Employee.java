package com.iws_manager.iws_manager_api.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.iws_manager.iws_manager_api.models.base.BaseEntity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true, exclude = {
    "salutation", "title", "customer", "qualificationFZ"
}) // Agregar una vez este implementado "employmentContracts", "projects", "absenceDays"
@EntityListeners(AuditingEntityListener.class)
@Table(name = "employee")
@AttributeOverride(name = "id", column = @Column(name = "employeeid", nullable = false))
public class Employee extends BaseEntity {

    @Column(name = "coentrepreneursince")
    private LocalDate coentrepreneursince;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerid", referencedColumnName = "customerid")
    private Customer customer;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "employeeno")
    private Integer employeeno;

    @Column(name = "firstname", length = 255)
    private String firstname;

    @Column(name = "generalmanagersince")
    private LocalDate generalmanagersince;

    @Column(name = "label", length = 255)
    private String label;

    @Column(name = "lastname", length = 255)
    private String lastname;

    @Column(name = "phone", length = 255)
    private String phone;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "qualificationfzid", referencedColumnName = "qualificationfzid")
    private QualificationFZ qualificationFZ;

    @Column(name = "qualificationkmui", length = 255)
    private String qualificationkmui;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "salutationid", referencedColumnName = "salutationid")
    private Salutation salutation;

    @Column(name = "shareholdersince")
    private LocalDate shareholdersince;

    @Column(name = "soleproprietorsince")
    private LocalDate soleproprietorsince;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "titleid", referencedColumnName = "titleid")
    private Title title;
}