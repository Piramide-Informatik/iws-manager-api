package com.iws_manager.iws_manager_api.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.iws_manager.iws_manager_api.models.base.BaseEntity;

import jakarta.persistence.AttributeOverride;
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

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "promoter")
@AttributeOverride(name = "id", column = @Column(name = "promoterid"))
public class Promoter extends BaseEntity{

    @Column(name = "city", length = 255)
    private String city;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "countryid", referencedColumnName = "countryid")
    private Country country;

    @Column(name = "promoter", length = 255)
    private String projectPromoter;
    
    @Column(name = "promotername1", length = 255)
    private String promoterName1;

    @Column(name = "promotername2", length = 255)
    private String promoterName2;
    
    @Column(name = "promoterno", length = 255)
    private Integer promoterNo;

    @Column(name = "street", length = 255)
    private String street;

    @Column(name = "zipcode", length = 255)
    private String zipCode;
}
