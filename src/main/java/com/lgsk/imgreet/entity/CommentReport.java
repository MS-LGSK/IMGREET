package com.lgsk.imgreet.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "COMMENT_REPORTS")
public class CommentReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "COMMENT_ID", nullable = false)
    private Comment commentId;

    @Column(length = 1000, nullable = false)
    private String reason;

    private Boolean done;
}
