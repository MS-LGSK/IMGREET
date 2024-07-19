package com.lgsk.imgreet.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "CATEGORIES")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String type;

    @Column(length = 255, nullable = false)
    private String subType;

    @Column(nullable = false)
    private boolean free;
}
