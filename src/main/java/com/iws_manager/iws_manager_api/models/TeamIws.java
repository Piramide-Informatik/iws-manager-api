package com.iws_manager.iws_manager_api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.iws_manager.iws_manager_api.models.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "teamiws")
@AttributeOverride(name = "id", column = @Column(name = "teamiwsid"))
public class TeamIws extends BaseEntity {

    @Column(name = "teamiws", nullable = true, length = 255)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamleaderid", nullable = true)
    @JsonIgnoreProperties(value = {"teamIws", "team", "hibernateLazyInitializer", "handler"}, allowSetters = true)
    private EmployeeIws teamLeader;
}
