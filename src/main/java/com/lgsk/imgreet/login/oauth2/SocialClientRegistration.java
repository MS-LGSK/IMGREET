package com.lgsk.imgreet.login.oauth2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.stereotype.Component;

@Component
public final class SocialClientRegistration {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    public ClientRegistration kakaoClientRegistration() {
        return ClientRegistration.withRegistrationId("kakao")
                .clientId(clientId)
                .redirectUri("http://localhost:8080/login/oauth2/code/kakao")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .scope("profile_nickname", "friends")
                .authorizationUri("https://kauth.kakao.com/oauth/authorize")
                .tokenUri("https://kauth.kakao.com/oauth/token")
                .userInfoUri("https://kapi.kakao.com/v2/user/me")
                .userNameAttributeName("id")
                .build();

    }
}
//        private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
//        private String registrationId;
//        private String clientId;
//        private String clientSecret;
//        private ClientAuthenticationMethod clientAuthenticationMethod;
//        private AuthorizationGrantType authorizationGrantType;
//        private String redirectUri;
//        private Set<String> scopes = Collections.emptySet();
//        private String clientName;
//        private ProviderDetails providerDetails = new ProviderDetails();
//
//        public class ProviderDetails implements Serializable {
//            private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
//            private String authorizationUri;
//            private String tokenUri;
//            private UserInfoEndpoint userInfoEndpoint = new UserInfoEndpoint();
//            private String jwkSetUri;
//            private String issuerUri;
//            private Map<String, Object> configurationMetadata = Collections.emptyMap();
//        }
//    }
//}
