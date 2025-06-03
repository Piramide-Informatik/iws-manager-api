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
@EqualsAndHashCode(callSuper = true, exclude = {"branch", "companytype", "country", "state"})
@EntityListeners(AuditingEntityListener.class)
@Table(name = "customer")
@AttributeOverride(name = "id", column = @Column(name = "customerid"))
public class Customer extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @Column(name = "city", nullable = false)
    private String city;

    @ManyToOne
    @JoinColumn(name = "companytype_id", nullable = false)
    private CompanyType companytype;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Column(name = "customerno", nullable = false)
    private String customerno;

    @Column(name = "customername1", nullable = false)
    private String customername1;
    
    @Column(name = "customername2", nullable = false)
    private String customername2;

    @Column(name = "email1", nullable = false)
    private String email1;

    @Column(name = "email2", nullable = false)
    private String email2;

    @Column(name = "email3", nullable = false)
    private String email3;

    @Column(name = "email4", nullable = false)
    private String email4;

    @Column(name = "homepage", nullable = false)
    private String homepage;

    @Column(name = "hoursperweek", nullable = false)
    private Double hoursperweek;

    @Column(name = "maxhoursmonth", nullable = false)
    private Double maxhoursmonth;

    @Column(name = "maxhoursyear", nullable = false)
    private Double maxhoursyear;

    @Column(name = "note", nullable = false)
    private String note;

    @Column(name = "phone", nullable = false)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private State state;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "taxno", nullable = false)
    private String taxno;

    @Column(name = "taxoffice", nullable = false)
    private String taxoffice;

    @Column(name = "zipcode", nullable = false)
    private String zipcode;
}