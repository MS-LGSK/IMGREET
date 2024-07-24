package com.lgsk.imgreet.login.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {

    private final UserDTO userDTO;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return userDTO.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getName() {
        return userDTO.getNickname();
    }

    public String getOAuthId() {
        return userDTO.getOAuthId();
    }


//    private final OAuth2Response oAuth2Response;
//    private final String role;
//
//    @Override
//    public Map<String, Object> getAttributes() {
//        return null;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> collection = new ArrayList<>();
//
//        collection.add(new GrantedAuthority() {
//
//            @Override
//            public String getAuthority() {
//
//                return role;
//            }
//        });
//
//        return collection;
//    }
//
//    @Override
//    public String getName() {       // 닉네임
//        return oAuth2Response.getNickname();
//    }
//
//    public String getOAuthId() {
//        return oAuth2Response.getProvider() + "_" + oAuth2Response.getProviderId();
//    }
}
