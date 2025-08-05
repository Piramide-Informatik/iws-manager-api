package com.iws_manager.iws_manager_api.models;

import com.iws_manager.iws_manager_api.models.base.BaseEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="role")
@AttributeOverride(name = "id", column = @Column(name = "roleid"))
public class Role extends BaseEntity {

    @Column(name = "role", length = 255)
    private String name;
}
