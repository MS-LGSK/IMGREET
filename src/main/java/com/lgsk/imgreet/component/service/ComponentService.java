package com.lgsk.imgreet.component.service;

import com.lgsk.imgreet.categoryDetail.repository.CategoryDetailRepository;
import com.lgsk.imgreet.component.repository.ComponentRepository;
import com.lgsk.imgreet.entity.CategoryDetail;
import com.lgsk.imgreet.entity.Component;
import com.lgsk.imgreet.component.model.ComponentDTO;
import com.lgsk.imgreet.entity.Greet;
import com.lgsk.imgreet.greet.repository.GreetRepository;
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

    public void saveComponent(Long greetId, List<ComponentDTO> componentDTOList) {
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
}