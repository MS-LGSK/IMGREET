package com.lgsk.imgreet.template.service;

import com.lgsk.imgreet.base.entity.Role;
import com.lgsk.imgreet.component.dto.ComponentDTO;
import com.lgsk.imgreet.component.repository.ComponentRepository;
import com.lgsk.imgreet.entity.Component;
import com.lgsk.imgreet.entity.Template;
import com.lgsk.imgreet.entity.User;
import com.lgsk.imgreet.login.repository.UserRepository;
import com.lgsk.imgreet.template.repository.TemplateRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TemplateServiceTest {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ComponentRepository componentRepository;

    User user;
    ComponentDTO componentDTO1;
    ComponentDTO componentDTO2;

    @BeforeEach
    void setup() {
        user = userRepository.save(User.builder()
                .oauthId("kakao_123")
                .role(Role.FREE_USER)
                .nickname("테스트")
                .build());

        componentDTO1 = ComponentDTO.builder()
                .content("Content 1")
                .x(10f)
                .y(20f)
                .width(100f)
                .height(50f)
                .rotation(0f)
                .categoryDetailId(21L)
                .build();

        componentDTO2 = ComponentDTO.builder()
                .content("Content 2")
                .x(15f)
                .y(25f)
                .width(150f)
                .height(75f)
                .rotation(0f)
                .categoryDetailId(22L)
                .build();
    }

    @Test
    @DisplayName("template 저장 (component와 함께 저장)")
    @Transactional
    void saveTemplate() {
        // given
        List<ComponentDTO> components = Arrays.asList(componentDTO1, componentDTO2);

        // when
        templateService.saveTemplate(user.getId(), components);

        // then
        // 1. template가 저장이 되었는지
        List<Template> templates = templateRepository.findAll();
        assertThat(templates.size()).isEqualTo(1);

        // 2. template 작성자가 동일한지
        Template savedTemplate = templates.get(0);
        assertThat(user.getId()).isEqualTo(savedTemplate.getCreatorId());

        // 3. component 수가 일치하는지
        List<Component> componentList = componentRepository.findAllByTemplateId(savedTemplate.getId());
        assertThat(componentList.size()).isEqualTo(2);

        // 4. 컴포넌트 요소 내용이 일치하는지
        Component component1 = componentList.get(0);
        assertThat(component1.getContent()).isEqualTo("Content 1");
        assertThat(component1.getX()).isEqualTo(10f);
        assertThat(component1.getY()).isEqualTo(20f);
        assertThat(component1.getWidth()).isEqualTo(100f);
        assertThat(component1.getHeight()).isEqualTo(50f);
        assertThat(component1.getRotation()).isEqualTo(0f);
        assertThat(component1.getCategoryDetail().getId()).isEqualTo(21L);
        assertThat(component1.getTemplate().getId()).isEqualTo(savedTemplate.getId());

        Component component2 = componentList.get(1);
        assertThat(component2.getContent()).isEqualTo("Content 2");
        assertThat(component2.getX()).isEqualTo(15f);
        assertThat(component2.getY()).isEqualTo(25f);
        assertThat(component2.getWidth()).isEqualTo(150f);
        assertThat(component2.getHeight()).isEqualTo(75f);
        assertThat(component2.getRotation()).isEqualTo(0f);
        assertThat(component2.getCategoryDetail().getId()).isEqualTo(22L);
        assertThat(component2.getTemplate().getId()).isEqualTo(savedTemplate.getId());
    }

}