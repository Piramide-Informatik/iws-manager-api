package com.iws_manager.iws_manager_api.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.iws_manager.iws_manager_api.models.base.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@Table(name="approvalstatus")
@AttributeOverride(name= "id" ,column = @Column(name="approvalstatusid"))
public class ApprovalStatus extends BaseEntity {

    @Column(name = "approvalstatus", length = 255)
    private String status;

    @Column(name = "forprojects", columnDefinition = "SMALLINT")
    private Short forProjects;

    @Column(name = "fornetworks", columnDefinition = "SMALLINT")
    private Short forNetworks;

    @Column(name = "sequenceno", columnDefinition = "INT")
    private Integer sequenceNo;
}
