package com.lgsk.imgreet.entity;

import com.lgsk.imgreet.base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Getter
@Table(name = "ATTENDEES")
public class Attendance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "GREET_ID", nullable = false)
    private Greet greet;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String phone;

    @Column(nullable = false)
    private Long attendanceNumber;
}
