package com.lgsk.imgreet.comment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeleteCommentRequestDTO {

    @NotNull
    private Long commentId;

    @NotNull
    private String password;
}
