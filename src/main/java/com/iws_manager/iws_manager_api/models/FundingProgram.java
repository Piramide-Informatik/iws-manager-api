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
@EntityListeners(AuditingEntityListener.class)
@Table(name = "fundingprogram")
@AttributeOverride(name = "id", column = @Column(name = "fundingprogramid"))
public class FundingProgram extends BaseEntity {

    @Column(name = "defaultfundingrate")
    private Double defaultFundingRate;

    @Column(name = "defaulthoursperyear")
    private Double defaultHoursPerYear;

    @Column(name = "defaultresearchshare")
    private Double defaultResearchShare;

    @Column(name = "defaultstuffflat")
    private Double defaultStuffFlat;

    @Column(name = "fundingprogram", length = 255)
    private String fundingProgram;
}
