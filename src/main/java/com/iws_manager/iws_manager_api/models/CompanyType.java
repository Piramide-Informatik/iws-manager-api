package com.iws_manager.iws_manager_api.models;

import com.iws_manager.iws_manager_api.models.base.BaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "companytype")
@AttributeOverride(name = "id", column = @Column(name = "companytypeid"))
public class CompanyType extends BaseEntity{
    /**
     * TypeName of the company type (e.g., Public, Private).
     */
    @Column(name = "companytype", nullable = true, length = 255)
    private String name;
}
