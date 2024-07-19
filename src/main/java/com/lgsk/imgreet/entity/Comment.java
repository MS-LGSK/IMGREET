package com.lgsk.imgreet.entity;

import com.lgsk.imgreet.base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name ="COMMENTS")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "GREET_ID", nullable = false)
    private Greet greet;

    @Column(length = 45, nullable = false)
    private String ipAddress;

    @Column(length = 255, nullable = false)
    private String nickname;

    @Column(length = 255, nullable = false)
    private String password;

    @Column(length = 2000, nullable = false)
    private String content;

}
