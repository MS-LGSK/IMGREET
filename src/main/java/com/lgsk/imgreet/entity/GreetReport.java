package com.lgsk.imgreet.entity;

import com.lgsk.imgreet.base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "GREET_REPORTS")
public class GreetReport extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000, nullable = false)
    private String reason;

    private char done;

    @ManyToOne
    @JoinColumn(name = "GREET_ID", nullable = false)
    private Greet greet;
}
