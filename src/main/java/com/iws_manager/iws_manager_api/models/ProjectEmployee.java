package com.iws_manager.iws_manager_api.models;

import com.iws_manager.iws_manager_api.models.Employee;
import com.iws_manager.iws_manager_api.models.Project;
import com.iws_manager.iws_manager_api.models.base.BaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "projectemployee")
@AttributeOverride(name = "id", column = @Column(name = "projectemployeeid", nullable = false))
public class ProjectEmployee extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeid", referencedColumnName = "employeeid")
    private Employee employee;

    // Redundancia con Employee
    // @Column(name = "employeeno")
    // private Integer employeeno;

    @Column(name = "hourlyrate", precision = 5, scale = 2)
    private BigDecimal hourlyrate;

    @Column(name = "plannedhours", precision = 10, scale = 2)
    private BigDecimal plannedhours;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectid", referencedColumnName = "projectid")
    private Project project;

    @Column(name = "qualificationkmui", length = 255)
    private String qualificationkmui;

}