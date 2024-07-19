package com.lgsk.imgreet.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "COMPONENTS")
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(nullable = false)
    private float x;

    @Column(nullable = false)
    private float y;

    @Column(nullable = false)
    private float width;

    @Column(nullable = false)
    private float height;

    @Column(nullable = false)
    private float rotation;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "GREET_ID")
    private Greet greet;

    @ManyToOne
    @JoinColumn(name = "TEMPLATE_ID")
    private Template template;
}
