package com.iws_manager.iws_manager_api.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.iws_manager.iws_manager_api.models.base.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a Salutation in the IWS system constants.
 * This entity is mapped to the 'salutation' table in the database.
 * It contains information about the salutation unique identifier and name,
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "salutation")
@AttributeOverride(name = "id", column = @Column(name = "salutationid"))
public class Salutation extends BaseEntity{

    @Column(name = "salutation", length = 255)
    private String name;

    @OneToMany(mappedBy = "salutation", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Employee> employees = new HashSet<>();
}
