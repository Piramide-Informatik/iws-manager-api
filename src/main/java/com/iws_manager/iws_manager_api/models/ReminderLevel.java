package com.iws_manager.iws_manager_api.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.iws_manager.iws_manager_api.models.base.BaseEntity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
@Table(name = "reminderlevel")
@AttributeOverride(name = "id", column = @Column(name = "reminderlevelid"))
public class ReminderLevel extends BaseEntity {

    @Column(name = "fee", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal fee;

    @Column(name = "interestrate", columnDefinition = "DECIMAL(5,2)")
    private BigDecimal interestRate;

    @Column(name = "levelno",  columnDefinition = "SMALLINT")
    private Short levelNo;

    @Column(name = "payperiod", columnDefinition = "SMALLINT")
    private Short payPeriod;

    @Column(name = "remindertext", length = 255)
    private String reminderText;

    @Column(name = "remindertitle", length = 255)
    private String reminderTitle;
}