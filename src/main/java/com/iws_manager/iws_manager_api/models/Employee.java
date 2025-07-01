package com.iws_manager.iws_manager_api.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.iws_manager.iws_manager_api.models.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToOne(fetch = FetchType.LAZY)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qualificationfzid", referencedColumnName = "qualificationfzid")
    private QualificationFZ qualificationFZ;

    @Column(name = "qualificationkmui", length = 255)
    private String qualificationkmui;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salutationid", referencedColumnName = "salutationid")
    private Salutation salutation;

    @Column(name = "shareholdersince")
    private LocalDate shareholdersince;

    @Column(name = "soleproprietorsince")
    private LocalDate soleproprietorsince;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "titleid", referencedColumnName = "titleid")
    private Title title;

        /* Crear servicio */
    // @OneToMany(
    //     mappedBy = "employee",
    //     fetch = FetchType.LAZY,
    //     cascade = CascadeType.ALL,
    //     orphanRemoval = true
    // )
    //private Set<AbsenceDay> absenceDays = new HashSet<>();

    /**
     * Relación temporalmente comentada porque se necesita implementar employee contract
     * TODO: Implementar cuando se complete employee contract
     */
    // @OneToMany(
    //     mappedBy = "employee",
    //     fetch = FetchType.LAZY,
    //     cascade = CascadeType.ALL,
    //     orphanRemoval = true
    // )
    // private Set<EmploymentContract> employmentContracts = new HashSet<>();

    /**
     * Relación temporalmente comentada porque se necesita implementar project
     * TODO: Implementar cuando se complete project
     */
    // @ManyToMany(fetch = FetchType.LAZY)
    // @JoinTable(
    //     name = "projectemployee",
    //     joinColumns = @JoinColumn(name = "employeeid"),
    //     inverseJoinColumns = @JoinColumn(name = "projectid")
    // )
    // private Set<Project> projects = new HashSet<>();

    // Relación comentada temporalmente para evitar ciclos hasta que se implemente el servicio correspondiente.
    // TODO: Volver a habilitar cuando se cree el servicio para gestionar AbsenceDay desde Employee.
    // public void addAbsenceDay(AbsenceDay absenceDay) {
    //     absenceDays.add(absenceDay);
    //     absenceDay.setEmployee(this);
    // }

    // public void removeAbsenceDay(AbsenceDay absenceDay) {
    //     absenceDays.remove(absenceDay);
    //     absenceDay.setEmployee(null);
    // }

    /**
     * Relación temporalmente comentada porque se necesita implementar contract
     * TODO: Implementar cuando se complete contract
     */
    // public void addEmploymentContract(EmploymentContract contract) {
    //     employmentContracts.add(contract);
    //     contract.setEmployee(this);
    // }

    /**
     * Relación temporalmente comentada porque se necesita implementar project
     * TODO: Implementar cuando se complete project
     */
    // public void addProject(Project project) {
    //     projects.add(project);
    //     project.getEmployees().add(this);
    // }

    // public void removeProject(Project project) {
    //     projects.remove(project);
    //     project.getEmployees().remove(this);
    // }
}