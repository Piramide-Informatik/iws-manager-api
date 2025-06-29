package com.iws_manager.iws_manager_api.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.iws_manager.iws_manager_api.models.base.BaseEntity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "absenceday")
@AttributeOverride(name = "id", column = @Column(name = "absencedayid"))
public class AbsenceDay extends BaseEntity{

    @Column(name = "absencedate")
    private LocalDate absencedate;

    /**
     * Relación temporalmente comentada porque se necesita implementar AbsenceType
     * TODO: Implementar cuando se complete AbsenceType
     */
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "absencetypeid", referencedColumnName = "absencetypeid")
    // private AbsenceType absenceType;


    /**
     * Relación temporalmente comentada porque se necesita implementar employees
     * TODO: Implementar cuando se complete employees
     */
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "employeeid", referencedColumnName = "employeeid")
    // private Employee employee;
}
