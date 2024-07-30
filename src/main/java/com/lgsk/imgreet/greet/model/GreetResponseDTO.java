package com.lgsk.imgreet.greet.model;

import com.lgsk.imgreet.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GreetResponseDTO {
    private Long id;
    private User user;
    private String title;
    private String url;
    private String imageUrl;
    private LocalDateTime expireDate;
    private boolean allowComments;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
