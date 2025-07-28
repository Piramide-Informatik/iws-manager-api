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
@EntityListeners(AuditingEntityListener.class)
@Table(name="approvalstatus")
@AttributeOverride(name= "id" ,column = @Column(name="approvalstatusid"))
public class ApprovalStatus extends BaseEntity {

    @Column(name = "approvalstatus", length = 255)
    private String name;

    @Column(name = "forprojects", columnDefinition = "SMALLINT")
    private Short forProjects;

    @Column(name = "fornetworks", columnDefinition = "SMALLINT")
    private Short forNetworks;

    @Column(name = "sequenceno", columnDefinition = "INT")
    private Integer sequenceNo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApprovalStatus that = (ApprovalStatus) o;

        if (this.getId() != null && that.getId() != null) {
            return this.getId().equals(that.getId());
        }

        return Objects.equals(name, that.name) &&
                Objects.equals(forProjects, that.forProjects) &&
                Objects.equals(forNetworks, that.forNetworks) &&
                Objects.equals(sequenceNo, that.sequenceNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getId() != null ? getId() : name, forProjects, forNetworks, sequenceNo
        );
    }
}
