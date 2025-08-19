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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AutoCloseable.class)
@Table(name="publicholiday")
@AttributeOverride(name = "id", column = @Column(name = "publicholidayid"))
public class PublicHoliday extends BaseEntity {

    @Column(name = "date", columnDefinition = "DATE")
    private LocalDate date;

    @Column(name = "publicholiday", length = 255)
    private String name;

    @Column(name="fixdate", columnDefinition = "TINYINT")
    private Boolean isFixedDate;

    @Column(name = "sequenceno",columnDefinition = "INT")
    private Integer sequenceNo;

    @ManyToOne
    @JoinColumn(name="stateid")
    private State states;
}
