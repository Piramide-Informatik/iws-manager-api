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
@Table(name = "companyType")
public class CompanyType extends BaseEntity{
    /**
     * TypeName of the company type (e.g., Public, Private).
     */
    @Column(name = "name", nullable = false, length = 100)
    private String name;
}
