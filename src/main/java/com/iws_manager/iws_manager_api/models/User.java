package com.iws_manager.iws_manager_api.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.iws_manager.iws_manager_api.models.base.BaseEntity;


import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a system user for authentication purposes.
 * Supports Basic Authentication with email/password credentials.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "app_user")
public class User extends BaseEntity{

    /**
     * User's email address (used as username). Must be unique.
     * Example: "user@company.com"
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Encrypted user password (using BCrypt hashing).
     */
    @Column(nullable = false)
    private String password;

    /**
     * Indicates if the account is active.
     * Default: true
     */
    @Column(nullable = false)
    private boolean active = true;
}