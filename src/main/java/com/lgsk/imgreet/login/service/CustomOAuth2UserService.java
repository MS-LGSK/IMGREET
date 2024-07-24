package com.lgsk.imgreet.login.service;

import com.lgsk.imgreet.base.entity.Role;
import com.lgsk.imgreet.entity.User;
import com.lgsk.imgreet.login.dto.CustomOAuth2User;
import com.lgsk.imgreet.login.dto.KakaoResponse;
import com.lgsk.imgreet.login.dto.OAuth2Response;
import com.lgsk.imgreet.login.dto.UserDTO;
import com.lgsk.imgreet.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info(oAuth2User.getAttributes());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;

        if (registrationId.equals("kakao")){
            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        String OAuthId = oAuth2Response.getProvider() + "_" + oAuth2Response.getProviderId();
        User existData = userRepository.findByNickname(OAuthId);

        if (existData == null) {

            User user = User.builder()
                    .nickname(OAuthId)                                       // 식별 ID
                    .role(Role.FREE_USER)
                    .password(oAuth2Response.getNickname())                  // 서비스에서 받아온 닉네임 값
                    .build();

            userRepository.save(user);

            UserDTO userDTO = UserDTO.builder()
                    .oAuthId(OAuthId)
                    .nickname(oAuth2Response.getNickname())
                    .role(Role.FREE_USER.toString())
                    .build();

            return new CustomOAuth2User(userDTO);
        } else {

            UserDTO userDTO = UserDTO.builder()
                    .oAuthId(existData.getNickname())
                    .nickname(oAuth2Response.getNickname())
                    .role(Role.FREE_USER.toString())
                    .build();

            return new CustomOAuth2User(userDTO);
        }
    }
}
