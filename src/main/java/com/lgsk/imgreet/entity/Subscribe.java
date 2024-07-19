package com.lgsk.imgreet.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "SUBSCRIBES")
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 10, nullable = false)
    private String name;

    @Column(nullable = false)
    private int period;

    @Column(nullable = false)
    private int price;

}
