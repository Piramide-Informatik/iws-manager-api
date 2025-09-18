package com.iws_manager.iws_manager_api.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.iws_manager.iws_manager_api.models.base.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "absencetype")
@AttributeOverride(name = "id", column = @Column(name = "absencetypeid"))
public class AbsenceType  extends BaseEntity{

    @Column(name = "absencetype", length = 255)
    private String name;

    @Column(name = "absencetypelabel", length = 255)
    private String label;

    @Column(name = "hours", columnDefinition = "TINYINT")
    private Byte hours;

    @Column(name = "isholiday", columnDefinition = "TINYINT")
    private Byte isHoliday;

    @Column(name = "shareofday", columnDefinition = "DECIMAL(2,1)")
    private BigDecimal shareOfDay;

}
