package com.lgsk.imgreet.template.service;

import com.lgsk.imgreet.entity.Template;
import com.lgsk.imgreet.entity.User;
import com.lgsk.imgreet.login.repository.UserRepository;

import com.lgsk.imgreet.template.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TemplateService {

    private final TemplateRepository templateRepository;
    private final UserRepository userRepository;

    public void saveTemplate(Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));

        Template template = Template.builder()
                .creatorId(findUser.getId())
                .build();

        templateRepository.save(template);
    }
}
