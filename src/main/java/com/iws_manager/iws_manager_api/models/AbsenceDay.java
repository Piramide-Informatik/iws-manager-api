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
@EqualsAndHashCode(callSuper = true, exclude = { "employee" })
@EntityListeners(AuditingEntityListener.class)
@Table(name = "absenceday")
@AttributeOverride(name = "id", column = @Column(name = "absencedayid"))
public class AbsenceDay extends BaseEntity{

    @Column(name = "absencedate")
    private LocalDate absenceDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "absencetypeid", referencedColumnName = "absencetypeid")
    private AbsenceType absenceType;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeid", referencedColumnName = "employeeid")
    private Employee employee;
}
