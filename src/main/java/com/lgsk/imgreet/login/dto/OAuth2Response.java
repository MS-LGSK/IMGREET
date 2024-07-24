package com.lgsk.imgreet.login.dto;

public interface OAuth2Response {

    String getProvider();
    Long getProviderId();

    String getNickname();
}
