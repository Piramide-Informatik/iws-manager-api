package com.iws_manager.iws_manager_api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.iws_manager.iws_manager_api.models.base.BaseEntity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
@Table(name = "user")
@AttributeOverride(name ="id", column = @Column(name = "userid"))
public class User extends BaseEntity{

    /**
     * Indicates if the account is active.
     * Default: true
     */
    @Column(name="active",nullable = false)
    private boolean active = true;

    @Column(name="firstname", length = 255)
    private String firstName;

    @Column(name = "lastname", length = 255)
    private String lastName;
    /**
     * User's email address (used as username). Must be unique.
     * Example: "user@company.com"
     */
    @Column(name = "mail", length = 255 ,unique = true, nullable = false)
    private String email;

    /**
     * Encrypted user password (using BCrypt hashing).
     */
    @Column(name = "password", length = 255,nullable = false)
    private String password;

    @Column(name = "username", length = 255)
    private String username;

    @ManyToMany
    @JoinTable(
            name = "userrole",
            joinColumns = @JoinColumn(name = "userid"),
            inverseJoinColumns = @JoinColumn(name = "roleid")
    )
    @JsonIgnore
    private List<Role> roles = new ArrayList<>();

}