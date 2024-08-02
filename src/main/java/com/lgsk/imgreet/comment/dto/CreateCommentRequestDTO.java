package com.lgsk.imgreet.comment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateCommentRequestDTO {

    @NotNull
    private Long greetId;

    @NotNull
    private String ipAddress;

    @NotNull
    private String nickname;

    @NotNull
    private String password;

    @NotNull
    private String content;
}
