package com.lgsk.imgreet.login.dto;


import java.util.Map;

public class KakaoResponse implements OAuth2Response {

    private final Map<String, Object> attributes;
    private final Map<String, Object> attributesAccount;
    private final Map<String, Object> attributesProfile;

    public KakaoResponse(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.attributesAccount = (Map<String, Object>) attributes.get("kakao_account");
        this.attributesProfile = (Map<String, Object>) attributesAccount.get("profile");
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public Long getProviderId() {
        return (Long) attributes.get("id");
    }

    @Override
    public String getNickname() {
        return attributesProfile.get("nickname").toString();
    }


}
