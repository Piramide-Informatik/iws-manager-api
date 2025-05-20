package com.iws_manager.iws_manager_api.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.iws_manager.iws_manager_api.models.base.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a Country in the IWS system constants.
 * This entity is mapped to the 'country' table in the database.
 * It contains information about the country unique identifier, country label, country name,ísDefaults,
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "country")
public class Country extends BaseEntity{

    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "isDefault")
    private Boolean isDefault;
}
