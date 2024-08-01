package com.lgsk.imgreet.greet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class CreateGreetRequestDTO {
    @NotNull
    private Long userId;

    @NotNull
    private String title;

    private String url;

    private String imageUrl;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime expireDate;

    @NotNull
    @JsonProperty("allowComments")
    private Boolean allowComments;
}