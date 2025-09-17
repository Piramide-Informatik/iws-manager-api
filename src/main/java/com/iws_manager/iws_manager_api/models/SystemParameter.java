package com.iws_manager.iws_manager_api.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.iws_manager.iws_manager_api.models.base.BaseEntity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

/**
 * Represents a system parameter in the IWS system.
 * This entity is mapped to the 'system' table in the database.
 * It contains system variables with either character or numeric values.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "`system`") 
@AttributeOverride(name = "id", column = @Column(name = "systemid"))
public class SystemParameter extends BaseEntity {

    @Column(name = "`name`", nullable = false, length = 255)
    private String name;

    @Column(name = "valuechar", nullable = true, length = 255)
    private String valueChar;

    @Column(name = "valuenum", nullable = true, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal valueNum;
}