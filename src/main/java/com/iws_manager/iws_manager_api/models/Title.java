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
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "title")
@AttributeOverride(name = "id", column = @Column(name = "titleid"))
public class Title extends BaseEntity{

    @Column(name = "title", length = 255)
    private String name;

    @OneToMany(mappedBy = "title", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Employee> employees = new HashSet<>();
}
