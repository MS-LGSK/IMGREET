package com.lgsk.imgreet.greet.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class UpdateGreetRequestDTO {

    @NotNull
    private Long id;

    @NotNull
    private Long userId;

    private String title;

    private String url;

    private String imageUrl;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime expireDate;

    private Boolean allowComments;
}
