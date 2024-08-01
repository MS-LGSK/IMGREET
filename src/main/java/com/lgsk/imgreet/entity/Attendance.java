package com.lgsk.imgreet.entity;

import com.lgsk.imgreet.base.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "ATTENDEES")
@SuperBuilder(toBuilder = true)
public class Attendance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "GREET_ID", nullable = false)
    private Greet greet;

    @Column(nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String phone;

    @Column(nullable = false)
    private Long attendanceNumber;
}
