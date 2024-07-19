package com.lgsk.imgreet.entity;

import com.lgsk.imgreet.base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "TEMPLATE_REPORTS")
public class TemplateReport extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000, nullable = false)
    private String reason;

    private char done;

    @ManyToOne
    @JoinColumn(name = "TEMPLATE_ID", nullable = false)
    private Template template;

}
