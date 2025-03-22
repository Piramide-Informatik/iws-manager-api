package com.iws_manager.iws_manager_api.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "country")

public class Country {

    @Id // countryid
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(updatable = false, nullable = false, unique = true, length = 36)
    private String uuid;

    @Column(name = "countryLabel", nullable = false)
    private String countryLabel;

    @Column(name = "countryName", nullable = false)
    private String countryName;

    @Column(name = "isDefault", nullable = false)
    private String isDefault;
}
