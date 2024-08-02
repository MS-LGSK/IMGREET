package com.lgsk.imgreet.component.service;

import com.lgsk.imgreet.base.entity.Role;
import com.lgsk.imgreet.component.dto.ComponentDTO;
import com.lgsk.imgreet.component.dto.ComponentResponseDTO;
import com.lgsk.imgreet.component.repository.ComponentRepository;
import com.lgsk.imgreet.entity.Component;
import com.lgsk.imgreet.entity.Greet;
import com.lgsk.imgreet.entity.Template;
import com.lgsk.imgreet.entity.User;
import com.lgsk.imgreet.greet.repository.GreetRepository;
import com.lgsk.imgreet.login.repository.UserRepository;
import com.lgsk.imgreet.template.repository.TemplateRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class ComponentServiceTest {

    @Autowired
    private ComponentService componentService;

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

    @BeforeEach
    public void setUp() {
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
    }

    @Test
    @DisplayName("초대장 컴포넌트 저장")
    @Transactional
    void saveGreetComponent() {
        // when
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

        // when
        List<ComponentDTO> lists = Arrays.asList(componentDTO1, componentDTO2);
        componentService.saveGreetComponent(greet.getId(), lists);

        // then
        List<Component> findComponents = componentRepository.findAll();
        assertThat(findComponents).hasSize(2);

        // Verify first component
        Component component1 = findComponents.get(0);
        assertThat(component1.getContent()).isEqualTo("Content 1");
        assertThat(component1.getX()).isEqualTo(10f);
        assertThat(component1.getY()).isEqualTo(20f);
        assertThat(component1.getWidth()).isEqualTo(100f);
        assertThat(component1.getHeight()).isEqualTo(50f);
        assertThat(component1.getRotation()).isEqualTo(0f);
        assertThat(component1.getCategoryDetail().getId()).isEqualTo(21L);
        assertThat(component1.getGreet().getId()).isEqualTo(greet.getId());

        Component component2 = findComponents.get(1);
        assertThat(component2.getContent()).isEqualTo("Content 2");
        assertThat(component2.getX()).isEqualTo(15f);
        assertThat(component2.getY()).isEqualTo(25f);
        assertThat(component2.getWidth()).isEqualTo(150f);
        assertThat(component2.getHeight()).isEqualTo(75f);
        assertThat(component2.getRotation()).isEqualTo(0f);
        assertThat(component2.getCategoryDetail().getId()).isEqualTo(22L);
        assertThat(component2.getGreet().getId()).isEqualTo(greet.getId());
    }

    @Test
    @DisplayName("Check nullable=false")
    @Transactional
    void checkNullContent() {
        ComponentDTO dto = ComponentDTO.builder()
                // content 내용 제외
                .x(10f)
                .y(20f)
                .width(100f)
                .height(50f)
                .rotation(0f)
                .categoryDetailId(21L)
                .build();

        // when
        List<ComponentDTO> lists = Arrays.asList(dto);
        componentService.saveGreetComponent(greet.getId(), lists);

        // then
        List<Component> findComponents = componentRepository.findAll();
        assertThat(findComponents).hasSize(1);
    }

    @Test
    @DisplayName("Check @NotNull Validation")
    @Transactional
    void checkNullWidth() {
        ComponentDTO dto = ComponentDTO.builder()
                .content("Content")
                .x(10f)
                .y(20f)
                // width 값 제외
                .height(50f)
                .rotation(0f)
                .categoryDetailId(21L)
                .build();

        // when
        List<ComponentDTO> lists = Arrays.asList(dto);
        componentService.saveGreetComponent(greet.getId(), lists);

        // then
        List<Component> findComponents = componentRepository.findAll();
        assertThat(findComponents).hasSize(1);
    }

    @Test
    @DisplayName("save template component")
    @Transactional
    void saveTemplateComponent() {
        // given
        User user = userRepository.save(User.builder()
                .oauthId("kakao_123")
                .role(Role.FREE_USER)
                .nickname("테스트")
                .build());

        Template template = templateRepository.save(Template.builder()
                .creatorId(user.getId())
                .build());

        // when
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

        List<ComponentDTO> lists = Arrays.asList(componentDTO1, componentDTO2);
        componentService.saveTemplateComponent(template.getId(), lists);

        // then
        List<Component> findComponents = componentRepository.findAllByTemplateId(template.getId());
        assertThat(findComponents).hasSize(2);

        Component component1 = findComponents.get(0);
        assertThat(component1.getContent()).isEqualTo("Content 1");
        assertThat(component1.getX()).isEqualTo(10f);
        assertThat(component1.getY()).isEqualTo(20f);
        assertThat(component1.getWidth()).isEqualTo(100f);
        assertThat(component1.getHeight()).isEqualTo(50f);
        assertThat(component1.getRotation()).isEqualTo(0f);
        assertThat(component1.getCategoryDetail().getId()).isEqualTo(21L);
        assertThat(component1.getTemplate().getId()).isEqualTo(template.getId());

        Component component2 = findComponents.get(1);
        assertThat(component2.getContent()).isEqualTo("Content 2");
        assertThat(component2.getX()).isEqualTo(15f);
        assertThat(component2.getY()).isEqualTo(25f);
        assertThat(component2.getWidth()).isEqualTo(150f);
        assertThat(component2.getHeight()).isEqualTo(75f);
        assertThat(component2.getRotation()).isEqualTo(0f);
        assertThat(component2.getCategoryDetail().getId()).isEqualTo(22L);
        assertThat(component2.getTemplate().getId()).isEqualTo(template.getId());
    }

    @Test
    @DisplayName("template component 가져오기")
    void getTemplateComponent() {
        // given
        User user = userRepository.save(User.builder()
                .oauthId("kakao_123")
                .role(Role.FREE_USER)
                .nickname("테스트")
                .build());

        Template template = templateRepository.save(Template.builder()
                .creatorId(user.getId())
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

        List<ComponentDTO> lists = Arrays.asList(componentDTO1, componentDTO2);
        componentService.saveTemplateComponent(template.getId(), lists);

        // when
        List<ComponentResponseDTO> templateComponentList = componentService.getTemplateComponent(template.getId());

        // then
        assertThat(templateComponentList.size()).isEqualTo(2);
    }
}