package com.iws_manager.iws_manager_api.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Represents a Country in the IWS system constants.
 * This entity is mapped to the 'country' table in the database.
 * It contains information about the country unique identifier, country label, country name,ísDefaults,
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "countryLabel", nullable = false)
    private String countryLabel;

    @Column(name = "countryName", nullable = false)
    private String countryName;

    @Column(name = "isDefault")
    private Integer isDefault;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
