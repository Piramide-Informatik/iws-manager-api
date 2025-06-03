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
    @JoinColumn(name = "branchid", referencedColumnName = "branchid", nullable = true)
    private Branch branch;

    @Column(name = "city", nullable = true, length = 255)
    private String city;

    @ManyToOne
    @JoinColumn(name = "companytypeid", referencedColumnName = "companytypeid", nullable = true)
    private CompanyType companytype;

    @ManyToOne
    @JoinColumn(name = "countryid", referencedColumnName = "countryid")
    private Country country;

    @Column(name = "customerno", nullable = true)
    private Integer customerno;

    @Column(name = "customername1", nullable = true, length = 255)
    private String customername1;
    
    @Column(name = "customername2", nullable = true, length = 255)
    private String customername2;

    @Column(name = "email1", nullable = true, length = 255)
    private String email1;

    @Column(name = "email2", nullable = true, length = 255)
    private String email2;

    @Column(name = "email3", nullable = true, length = 255)
    private String email3;

    @Column(name = "email4", nullable = true, length = 255)
    private String email4;

    @Column(name = "homepage", nullable = true, length = 255)
    private String homepage;

    @Column(name = "hoursperweek", nullable = true, columnDefinition = "DECIMAL(5,2)")
    private Double hoursperweek;

    @Column(name = "maxhoursmonth", nullable = true, columnDefinition = "DECIMAL(5,2)")
    private Double maxhoursmonth;

    @Column(name = "maxhoursyear", nullable = true, columnDefinition = "DECIMAL(5,2)")
    private Double maxhoursyear;

    @Column(name = "note", columnDefinition = "TEXT", nullable = true)
    private String note;

    @Column(name = "phone", nullable = true, length = 255)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "stateid", referencedColumnName = "stateid", nullable = true)
    private State state;

    @Column(name = "street", nullable = true, length = 255)
    private String street;

    @Column(name = "taxno", nullable = true, length = 255)
    private String taxno;

    @Column(name = "taxoffice", nullable = true, length = 255)
    private String taxoffice;

    @Column(name = "zipcode", nullable = true, length = 255)
    private String zipcode;
}