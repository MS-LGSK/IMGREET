package com.lgsk.imgreet.component.service;

import com.lgsk.imgreet.categoryDetail.repository.CategoryDetailRepository;
import com.lgsk.imgreet.component.model.ComponentResponseDTO;
import com.lgsk.imgreet.component.repository.ComponentRepository;
import com.lgsk.imgreet.entity.CategoryDetail;
import com.lgsk.imgreet.entity.Component;
import com.lgsk.imgreet.component.model.ComponentDTO;
import com.lgsk.imgreet.entity.Greet;
import com.lgsk.imgreet.entity.Template;
import com.lgsk.imgreet.greet.repository.GreetRepository;
import com.lgsk.imgreet.template.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ComponentService {

    private final ComponentRepository componentRepository;
    private final CategoryDetailRepository categoryDetailRepository;
    private final GreetRepository greetRepository;
    private final TemplateRepository templateRepository;

    public void saveGreetComponent(Long greetId, List<ComponentDTO> componentDTOList) {
        Greet greet = greetRepository.findById(greetId)
                .orElseThrow(() -> new IllegalStateException("초대장이 존재하지 않습니다."));

        List<Component> components = componentDTOList.stream()
                .map(dto -> {
                    CategoryDetail categoryDetail = categoryDetailRepository.findById(dto.getCategoryDetailId())
                            .orElseThrow(() -> new IllegalStateException("존재하지 않는 카테고리 서브타입입니다."));
                    return Component.builder()
                            .content(dto.getContent())
                            .x(dto.getX())
                            .y(dto.getY())
                            .width(dto.getWidth())
                            .height(dto.getHeight())
                            .rotation(dto.getRotation())
                            .categoryDetail(categoryDetail)
                            .greet(greet)
                            .build();
                })
                .collect(Collectors.toList());

        componentRepository.saveAll(components);
    }

    @Transactional(readOnly = true)
    public List<ComponentResponseDTO> getGreetComponent(Long greetId) {
        Greet greet = greetRepository.findById(greetId)
                .orElseThrow(() -> new IllegalStateException("초대장이 존재하지 않습니다."));

        List<Component> componentList = componentRepository.findAllByGreetId(greet.getId());

        return componentList.stream()
                .map(dto -> ComponentResponseDTO.builder()
                        .content(dto.getContent())
                        .x(dto.getX())
                        .y(dto.getY())
                        .width(dto.getWidth())
                        .height(dto.getHeight())
                        .rotation(dto.getRotation())
                        .build())
                .collect(Collectors.toList());
    }

    public void saveTemplateComponent(Long templateId, List<ComponentDTO> componentDTOList) {
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 템플릿입니다."));

        List<Component> components = componentDTOList.stream()
                .map(dto -> {
                    CategoryDetail categoryDetail = categoryDetailRepository.findById(dto.getCategoryDetailId())
                            .orElseThrow(() -> new IllegalStateException("존재하지 않는 카테고리 서브타입입니다."));
                    return Component.builder()
                            .content(dto.getContent())
                            .x(dto.getX())
                            .y(dto.getY())
                            .width(dto.getWidth())
                            .height(dto.getHeight())
                            .rotation(dto.getRotation())
                            .categoryDetail(categoryDetail)
                            .template(template)
                            .build();
                })
                .collect(Collectors.toList());

        componentRepository.saveAll(components);
    }

    @Transactional(readOnly = true)
    public List<ComponentResponseDTO> getTemplateComponent(Long templateId) {
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 템플릿입니다."));

        List<Component> componentList = componentRepository.findAllByTemplateId(template.getId());

        return componentList.stream()
                .map(dto -> ComponentResponseDTO.builder()
                        .content(dto.getContent())
                        .x(dto.getX())
                        .y(dto.getY())
                        .width(dto.getWidth())
                        .height(dto.getHeight())
                        .rotation(dto.getRotation())
                        .build())
                .collect(Collectors.toList());
    }
}