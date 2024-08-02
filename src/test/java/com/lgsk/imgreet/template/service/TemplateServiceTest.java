package com.lgsk.imgreet.template.service;

import com.lgsk.imgreet.base.entity.Role;
import com.lgsk.imgreet.component.dto.ComponentDTO;
import com.lgsk.imgreet.component.repository.ComponentRepository;
import com.lgsk.imgreet.entity.Component;
import com.lgsk.imgreet.entity.Greet;
import com.lgsk.imgreet.entity.Template;
import com.lgsk.imgreet.entity.User;
import com.lgsk.imgreet.greet.repository.GreetRepository;
import com.lgsk.imgreet.login.repository.UserRepository;
import com.lgsk.imgreet.template.repository.TemplateRepository;
import com.lgsk.imgreet.template.service.TemplateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TemplateServiceTest {
    @Autowired
    private TemplateService templateService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GreetRepository greetRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private ComponentRepository componentRepository;

    User user;

    Greet greet;

    List<ComponentDTO> componentDTOList = new ArrayList<>();

    @BeforeEach
    void setup() {
        // given
        user = userRepository.save(User.builder()
                .oauthId("kakao_123")
                .role(Role.FREE_USER)
                .nickname("테스트")
                .build());

        greet = greetRepository.save(Greet.builder()
                .title("테스트")
                .expireDate(LocalDateTime.now())
                .allowComments(true)
                .user(user)
                .build());

        ComponentDTO componentDTO1 = ComponentDTO.builder()
                .content("Content 1")
                .x(10f)
                .y(20f)
                .width(100f)
                .height(50f)
                .rotation(0f)
                .categoryDetailId(21L)
                .build();

        ComponentDTO componentDTO2 = ComponentDTO.builder()
                .content("Content 2")
                .x(15f)
                .y(25f)
                .width(150f)
                .height(75f)
                .rotation(0f)
                .categoryDetailId(22L)
                .build();

        componentDTOList = Arrays.asList(componentDTO1, componentDTO2);
    }

    @Test
    @DisplayName("template 저장(component 함께 저장)")
    @Transactional
    void saveTemplate() {
        // when
        templateService.saveTemplate("제목", "/path", user.getId(), componentDTOList);

        // then
        List<Template> templates = templateRepository.findAll();
        Template savedTemplate = templates.get(0);

        assertThat(savedTemplate.getCreatorId()).isEqualTo(user.getId());

        List<Component> components = componentRepository.findAllByTemplateId(savedTemplate.getId());
        assertThat(components.size()).isEqualTo(2);

    }
}