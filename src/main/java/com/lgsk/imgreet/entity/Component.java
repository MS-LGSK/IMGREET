package com.lgsk.imgreet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "COMPONENTS")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000, nullable = false)
    private String content;

    @NotNull
    private float x;

    @NotNull
    private float y;

    @NotNull
    private float width;

    @NotNull
    private float height;

    @NotNull
    private float rotation;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_DETAIL_ID", nullable = false)
    private CategoryDetail categoryDetail;

    @ManyToOne
    @JoinColumn(name = "GREET_ID")
    private Greet greet;

    @ManyToOne
    @JoinColumn(name = "TEMPLATE_ID")
    private Template template;
}
