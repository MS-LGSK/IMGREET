package com.lgsk.imgreet.login.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDTO {
    private String oauthId;
    private String nickname;
    private String role;
}
