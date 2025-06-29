package com.iws_manager.iws_manager_api.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.iws_manager.iws_manager_api.models.base.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "qualificationfz")
@AttributeOverride(name = "id", column = @Column(name = "qualificationfzid"))
public class QualificationFZ extends BaseEntity{

    @Column(name = "qualificationfz", length = 255)
    private String qualification;

    /**
     * Relación temporalmente comentada porque se necesita implementar employees
     * TODO: Implementar cuando se complete employees
     */
    // @OneToMany(mappedBy = "qualificationFZ", fetch = FetchType.LAZY)
    // @ToString.Exclude
    // private Set<Employee> employees = new HashSet<>();

}
