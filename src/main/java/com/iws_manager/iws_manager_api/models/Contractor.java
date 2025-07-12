package com.iws_manager.iws_manager_api.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.iws_manager.iws_manager_api.models.base.BaseEntity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "contractor")
@AttributeOverride(name = "id", column = @Column(name = "contractorid"))
public class Contractor extends BaseEntity{

    @Column(name = "city", length = 255)
    private String city;

    @Column(name = "contractorlabel", length = 255)
    private String label;
    
    @Column(name = "contractorname", length = 255)
    private String name;

    @Column(name = "contractorno", length = 255)
    private String number;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "countryid", referencedColumnName = "countryid")
    private Country country;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerid", referencedColumnName = "customerid")
    private Customer customer;

    @Column(name = "street", length = 255)
    private String street;

    @Column(name = "taxno", length = 255)
    private String taxNumber;

    @Column(name = "zipcode", length = 255)
    private String zipCode;

    @OneToMany(mappedBy = "contractor", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Subcontract> subcontracts = new ArrayList<>();
}
