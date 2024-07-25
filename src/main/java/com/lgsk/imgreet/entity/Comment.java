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
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Table(name = "COMMENTS")
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "GREET_ID")
    private Greet greet;

    @NotNull
    @Column(length = 45)
    private String ipAddress;

    @NotNull
    @Column(length = 255)
    private String nickname;

    @NotNull
    @Column(length = 255)
    private String password;

    @NotNull
    @Column(length = 2000)
    private String content;

}
