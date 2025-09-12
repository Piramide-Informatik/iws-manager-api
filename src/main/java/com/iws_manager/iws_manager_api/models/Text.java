package com.iws_manager.iws_manager_api.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.iws_manager.iws_manager_api.models.base.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a text template in the IWS system.
 * This entity is mapped to the 'text' table in the database.
 * It contains text templates with labels and content.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "`text`")
@AttributeOverride(name = "id", column = @Column(name = "textid"))
public class Text extends BaseEntity {

    @Column(name = "label", nullable = true, length = 255)
    private String label;

    @Lob
    @Column(name = "`text`", nullable = true, columnDefinition = "TEXT")
    private String content;
}