package com.lgsk.imgreet.template.service;

import com.lgsk.imgreet.categoryDetail.repository.CategoryDetailRepository;
import com.lgsk.imgreet.component.dto.ComponentDTO;
import com.lgsk.imgreet.component.repository.ComponentRepository;
import com.lgsk.imgreet.entity.CategoryDetail;
import com.lgsk.imgreet.entity.Component;
import com.lgsk.imgreet.entity.Template;
import com.lgsk.imgreet.entity.User;
import com.lgsk.imgreet.login.repository.UserRepository;

import com.lgsk.imgreet.template.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TemplateService {

    private final TemplateRepository templateRepository;
    private final UserRepository userRepository;
    private final ComponentRepository componentRepository;
    private final CategoryDetailRepository categoryDetailRepository;

    public void saveTemplate(Long userId, List<ComponentDTO> componentDTOList) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));

        Template template = Template.builder()
                .creatorId(findUser.getId())
                .build();

        Template savedTemplate = templateRepository.save(template);

        // 생성된 template id를 가지고 template component 저장
        List<Component> components = componentDTOList.stream()
                .map(dto -> {
                    CategoryDetail categoryDetail = categoryDetailRepository.findById(dto.getCategoryDetailId())
                            .orElseThrow(() -> new IllegalStateException("존재하지 않는 서브타입니다."));

                    return Component.builder()
                            .content(dto.getContent())
                            .x(dto.getX())
                            .y(dto.getY())
                            .width(dto.getWidth())
                            .height(dto.getHeight())
                            .rotation(dto.getRotation())
                            .categoryDetail(categoryDetail)
                            .template(savedTemplate)
                            .build();
                }).collect(Collectors.toList());

        componentRepository.saveAll(components);
    }
}
