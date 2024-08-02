package com.lgsk.imgreet.comment.dto;

import com.lgsk.imgreet.entity.Greet;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentResponseDTO {
    private Long id;

    private Greet greet;

    private String ipAddress;

    private String nickname;

    private String password;

    private LocalDateTime createdDate;

    private String content;
}
