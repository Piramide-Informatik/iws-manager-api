package com.iws_manager.iws_manager_api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "projectperiod")
@AttributeOverride(name ="id", column = @Column(name = "projectperiodid"))
public class ProjectPeriod extends BaseEntity {
    @Column(name="packageno", length = 255)
    private Short periodNo;

    @Column(name = "start", columnDefinition = "DATE")
    private LocalDate startDate;

    @Column(name = "end", columnDefinition = "DATE")
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"customer", "hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "projectid")
    private Project project;
}
