package com.iws_manager.iws_manager_api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iws_manager.iws_manager_api.models.base.BaseEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @OneToMany(fetch=FetchType.LAZY, mappedBy = "publicHoliday", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("publicHoliday")
    private List<StateHoliday> stateHolidays;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @OneToMany(fetch=FetchType.LAZY, mappedBy = "publicHoliday", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("publicHoliday")
    private List<HolidayYear> holidayYears;

}
