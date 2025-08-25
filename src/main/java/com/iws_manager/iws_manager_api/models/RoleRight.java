package com.iws_manager.iws_manager_api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.iws_manager.iws_manager_api.models.base.BaseEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="roleright")
@AttributeOverride(name = "id",column = @Column(name = "rolerightid"))
public class RoleRight extends BaseEntity {
    @Column(name = "accessright", columnDefinition = "INT")
    private Integer accessRight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleid", referencedColumnName = "roleid")
    @JsonBackReference
    private Role role;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "systemfunctionid")
//    private SystemFunction systemFunction;

}
