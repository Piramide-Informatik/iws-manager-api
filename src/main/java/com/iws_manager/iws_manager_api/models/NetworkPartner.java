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
@EqualsAndHashCode(callSuper = true, exclude = {"contactperson", "partner", "network"})
@EntityListeners(AuditingEntityListener.class)
@Table(name = "networkpartner")
@AttributeOverride(name = "id", column = @Column(name = "networkpartnerid"))
public class NetworkPartner extends BaseEntity {

    @Column(name = "`comment`", length = 255, nullable = true)
    private String comment;

    @ManyToOne
    @JoinColumn(
        name = "contactid",
        referencedColumnName = "contactpersonid",
        nullable = true,
        foreignKey = @ForeignKey(name = "contactid_contactpersonid") 
    )
    private ContactPerson contactperson;

    @ManyToOne
    @JoinColumn(
        name = "partnerid",
        referencedColumnName = "customerid",
        nullable = true,
        foreignKey = @ForeignKey(name = "customerid_partnerid")
    )
    private Customer partner;

    @ManyToOne
    @JoinColumn(
        name = "networkid",
        referencedColumnName = "networkid",
        nullable = true,
        foreignKey = @ForeignKey(name = "networkid_networkid")
    )
    private Network network;

    @Column(name = "partnerno", nullable = true)
    private Integer partnerno;
}
