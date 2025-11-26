package com.iws_manager.iws_manager_api.models;

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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "projectpackage")
@AttributeOverride(name = "id", column = @Column(name = "projectpackageid"))
public class ProjectPackage extends BaseEntity {
    @Column(name = "startdate", columnDefinition = "DATE")
    private LocalDate startDate;

    @Column(name = "enddate", columnDefinition = "DATE")
    private LocalDate endDate;

    @Column(name="packageno", length = 255)
    private String packageNo;

    @Column(name="packageserial", length = 255)
    private String packageSerial;

    @Column(name="packagetitle", length = 255)
    private String packageTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectid")
    private Project project;
}
