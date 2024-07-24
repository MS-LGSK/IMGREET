package com.lgsk.imgreet.login.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDTO {
    private String oAuthId;
    private String nickname;
    private String role;
}
