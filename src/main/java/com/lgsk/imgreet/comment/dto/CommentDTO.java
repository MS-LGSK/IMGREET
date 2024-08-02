package com.lgsk.imgreet.comment.dto;

import com.lgsk.imgreet.entity.Greet;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class CommentDTO {
    private Long id;

    private Greet greet;

    private String ipAddress;

    private String nickname;

    private String password;

    private String content;
}
