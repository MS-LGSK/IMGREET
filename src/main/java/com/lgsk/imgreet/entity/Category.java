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

    @Column(nullable = false)
    private boolean free;

    public static Category category(String type, boolean free) {
        Category category = new Category();
        category.type = type;
        category.free = free;

        return category;
    }
}
