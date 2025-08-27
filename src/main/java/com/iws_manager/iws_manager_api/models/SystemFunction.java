package com.iws_manager.iws_manager_api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iws_manager.iws_manager_api.models.base.BaseEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
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
@Table(name="systemfunction")
@AttributeOverride(name = "id", column = @Column(name = "functionid"))
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SystemFunction extends BaseEntity {
    @Column(name = "function", nullable = false)
    private String functionName;

    @Column(name = "sequenceno")
    private Short sequenceNo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "moduleid", nullable = false)
    private SystemModule module;//


}
