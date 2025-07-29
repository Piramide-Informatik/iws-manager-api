package com.iws_manager.iws_manager_api.models;

import java.time.LocalDate;
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
@Table(name = "subcontractyear")
@AttributeOverride(name = "id", column = @Column(name = "subcontractyearid"))
public class SubcontractYear extends BaseEntity{

    @Column(name = "months", columnDefinition = "TINYINT")
    private Integer months;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subcontractid", referencedColumnName = "subcontractid")
    private Subcontract subcontract;

    @Column(name = "`year`", columnDefinition = "DATE")
    private LocalDate year;
}
