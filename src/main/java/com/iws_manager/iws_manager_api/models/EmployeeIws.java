package com.iws_manager.iws_manager_api.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.iws_manager.iws_manager_api.models.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString(exclude = {"teamIws"}) 
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "employeeiws")
@AttributeOverride(name = "id", column = @Column(name = "employeeiwsid"))
public class EmployeeIws extends BaseEntity{

    @Column(name = "`active`", nullable = true, columnDefinition = "SMALLINT")
    private Integer active;
    
    @Column(name = "employeelabel", nullable = true, length = 255)
    private String employeeLabel;

    @Column(name = "employeeno", nullable = true)
    private Integer employeeNo;
    
    @Column(name = "enddate", nullable = true, columnDefinition = "DATE")
    private LocalDate endDate;
    
    @Column(name = "firstname", nullable = true, length = 255)
    private String firstname;
    
    @Column(name = "lastname", nullable = true, length = 255)
    private String lastname;
    
    @Column(name = "mail", nullable = true, length = 255)
    private String mail;

    @Column(name = "startdate", nullable = true, columnDefinition = "DATE")
    private LocalDate startDate;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "teamiwsid"
    )
    private TeamIws teamIws;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userid", referencedColumnName = "userid")
    private User user;
}
