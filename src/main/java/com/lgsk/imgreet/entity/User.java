package com.lgsk.imgreet.entity;

import com.lgsk.imgreet.base.entity.BaseEntity;
import com.lgsk.imgreet.base.entity.Role;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "USERS")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    private LocalDateTime subscribeDate;

    @ManyToOne
    @JoinColumn(name = "SUBSCRIBE_ID", nullable = false)
    private Subscribe subscribe;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Bookmark> bookmarks = new ArrayList<>();
}
