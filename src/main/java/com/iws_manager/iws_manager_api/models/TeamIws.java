package com.iws_manager.iws_manager_api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.iws_manager.iws_manager_api.models.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString(exclude = {"teamLeader"}) //No teamLeader in the object
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "teamiws")
@AttributeOverride(name = "id", column = @Column(name = "teamiwsid"))
public class TeamIws extends BaseEntity {

    @Column(name = "teamiws", nullable = true, length = 255)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER) 
    @JoinColumn(
        name = "teamleaderid", 
        referencedColumnName = "employeeiwsid",
        foreignKey = @ForeignKey(name = "employeeiwsid_teamleaderid")
    )
    @JsonIgnoreProperties({"teamIws", "user", "hibernateLazyInitializer", "handler"})
    private EmployeeIws teamLeader;
}
