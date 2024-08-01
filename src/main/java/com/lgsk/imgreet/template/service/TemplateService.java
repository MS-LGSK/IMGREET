package com.lgsk.imgreet.template.service;

import com.lgsk.imgreet.categoryDetail.repository.CategoryDetailRepository;
import com.lgsk.imgreet.component.dto.ComponentDTO;
import com.lgsk.imgreet.component.repository.ComponentRepository;
import com.lgsk.imgreet.component.service.ComponentService;
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
    private final ComponentService componentService;

    public void saveTemplate(String title, String imageUrl, Long userId, List<ComponentDTO> componentDTOList) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));

        String imagePath = componentService.captureCanvasAsImage(title, imageUrl);

        Template template = Template.builder()
                .title(title)
                .imageUrl(imagePath)
                .creatorId(findUser.getId())
                .build();

        Template savedTemplate = templateRepository.save(template);

        // 생성된 template id를 가지고 template component 저장
        componentService.saveTemplateComponent(savedTemplate.getId(), componentDTOList);
    }
}
