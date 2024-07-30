package com.lgsk.imgreet.greet.dto;

import com.lgsk.imgreet.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GreetRequestDTO {
    @NotNull
    private User user;

    @NotNull
    private String title;

    @NotNull
    private String url;

    @NotNull
    private String imageUrl;

    @NotNull
    private LocalDateTime expireDate;

    @NotNull
    private boolean allowComments;
}