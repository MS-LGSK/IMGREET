package com.lgsk.imgreet.greet.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateGreetRequestDTO {

    @NotNull
    private Long greetId;

    @NotNull
    private Long userId;

    private String title;

    private String url;

    private String imageUrl;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime expireDate;

    private Boolean allowComments;
}
