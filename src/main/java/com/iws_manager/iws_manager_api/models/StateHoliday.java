package com.iws_manager.iws_manager_api.models;

import com.iws_manager.iws_manager_api.models.base.BaseEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="stateholiday")
@AttributeOverride(name = "id", column = @Column(name = "stateholidayid"))
public class StateHoliday extends BaseEntity {
    private Boolean isholiday;

    @ManyToOne
    @JoinColumn(name = "publicholidayid")
    private PublicHoliday publicHoliday;

    @ManyToOne
    @JoinColumn(name = "stateid")
    private State state;
}
