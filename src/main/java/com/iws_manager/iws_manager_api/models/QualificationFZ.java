package com.iws_manager.iws_manager_api.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.iws_manager.iws_manager_api.models.base.BaseEntity;

import java.util.HashSet;
import java.util.Set;

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

    /* Eliminar este codigo una vez se haya implementado el servicio en el repositorio de employee */
    // @OneToMany(mappedBy = "qualificationFZ", fetch = FetchType.LAZY)
    // @ToString.Exclude
    // private Set<Employee> employees = new HashSet<>();
}
