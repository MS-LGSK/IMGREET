package com.lgsk.imgreet.greet.service;

import com.lgsk.imgreet.base.entity.Role;
import com.lgsk.imgreet.entity.Greet;
import com.lgsk.imgreet.entity.User;
import com.lgsk.imgreet.greet.dto.CreateGreetRequestDTO;
import com.lgsk.imgreet.greet.dto.UpdateGreetRequestDTO;
import com.lgsk.imgreet.login.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.LimitExceededException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class GreetServiceTests {
    @Autowired
    GreetService greetService;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    @Transactional
    void setup() {
        userRepository.save(User.builder()
                .role(Role.FREE_USER)
                .nickname("shin")
                .oauthId("kakao_1234567890")
                .password("1234")
                .subscribeDate(LocalDateTime.now())
                .build());

        Map<String, Object> attributes = Map.of(
                "id", "1234567890",
                "name", "shin"
        );

        // 사용자 정보 생성
        OAuth2User oAuth2User = new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(Role.FREE_USER.getValue())),
                attributes,
                "name"
        );

        OAuth2AuthenticationToken authToken = new OAuth2AuthenticationToken(
                oAuth2User,
                oAuth2User.getAuthorities(),
                "client-registration-id"
        );

        // SecurityContext에 Authentication 객체 설정
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    @Test
    @Transactional
    @DisplayName("초대장 생성 서비스 로직 테스트")
    void testSaveGreet() {
        CreateGreetRequestDTO createDTO = CreateGreetRequestDTO.builder()
                .title("캐리비안베이에 초대 합니다.")
                .expireDate(LocalDateTime.now().plusDays(5))
                .allowComments(false)
                .build();

        try {
            Greet tmpGreet = greetService.temporarySaveGreet(createDTO);
            UpdateGreetRequestDTO dto = UpdateGreetRequestDTO.builder()
                    .id(tmpGreet.getId())
                    .title(tmpGreet.getTitle())
                    .userId(tmpGreet.getUser().getId())
                    .imageUrl("image_url")
                    .expireDate(tmpGreet.getExpireDate())
                    .allowComments(tmpGreet.getAllowComments())
                    .build();
            Greet greet1 = greetService.saveGreet(dto);

            assertNotNull(greet1);
            assertEquals("캐리비안베이에 초대 합니다.", greet1.getTitle());
            greetService.temporarySaveGreet(createDTO);
            assertEquals(2, greetService.getAllGreets().size());
            greetService.temporarySaveGreet(createDTO);
            assertEquals(3, greetService.getAllGreets().size());
        } catch (LimitExceededException | UserPrincipalNotFoundException ignored) {
        }
        assertThrows(LimitExceededException.class, () -> greetService.temporarySaveGreet(createDTO));
    }
}