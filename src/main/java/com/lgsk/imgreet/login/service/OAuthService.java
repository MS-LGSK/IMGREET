package com.lgsk.imgreet.login.service;

import com.lgsk.imgreet.base.entity.Role;
import com.lgsk.imgreet.entity.User;
import com.lgsk.imgreet.login.dto.UserDTO;
import com.lgsk.imgreet.login.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");


        String oAuthId;
        String nickname;
        if ("kakao".equals(registrationId)) {
            oAuthId = registrationId + "_" + attributes.get("id");
            nickname = profile.toString();
        } else {
            throw new OAuth2AuthenticationException("허용되지 않는 인증입니다.");
        }

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        UserDTO userDTO;
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByOauthId(oAuthId));
        String accessToken = userRequest.getAccessToken().getTokenValue();

        if (optionalUser.isPresent()) {
            userDTO = UserDTO.builder()
                    .nickname(optionalUser.get().getNickname())
                    .role(optionalUser.get().getRole().toString())
                    .build();

        } else {
            User user = User.builder()
                    .oauthId(oAuthId)
                    .nickname(nickname)
                    .role(Role.FREE_USER)
                    .build();

            userRepository.save(user);

            userDTO = UserDTO.builder()
                    .nickname(nickname)
                    .role(Role.FREE_USER.toString())
                    .build();
        }

        httpSession.setAttribute("user", userDTO);
        httpSession.setAttribute("access_token", accessToken);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(userDTO.getRole().toString()))
                , oAuth2User.getAttributes()
                , userNameAttributeName
        );
    }
}
