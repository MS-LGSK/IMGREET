package com.lgsk.imgreet.greet.service;

import com.lgsk.imgreet.base.entity.Role;
import com.lgsk.imgreet.entity.Greet;
import com.lgsk.imgreet.entity.User;
import com.lgsk.imgreet.greet.dto.GreetRequestDTO;
import com.lgsk.imgreet.login.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.LimitExceededException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class GreetServiceTests {
    @Autowired
    GreetService greetService;
    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    @DisplayName("초대장 생성 서비스 로직 테스트")
    void testSaveGreet() {
        User user1 = userRepository.save(User.builder()
                .role(Role.FREE_USER)
                .nickname("shin")
                .password("1234")
                .subscribeDate(LocalDateTime.now())
                .build());

        GreetRequestDTO dto = GreetRequestDTO.builder()
                .user(user1)
                .title("캐리비안베이에 초대 합니다.")
                .imageUrl("image_url")
                .expireDate(LocalDateTime.now().plusDays(5))
                .allowComments(false)
                .build();
        try {
            Greet greet1 = greetService.saveGreet(dto);

            assertNotNull(greet1);
            assertEquals("캐리비안베이에 초대 합니다.", greet1.getTitle());
            greetService.saveGreet(dto);
            assertEquals(2, greetService.getAllGreets().size());
            greetService.saveGreet(dto);
            assertEquals(3, greetService.getAllGreets().size());
        } catch (LimitExceededException ignored) {

        }
        assertThrows(LimitExceededException.class, () -> greetService.saveGreet(dto));
    }
}