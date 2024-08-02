package com.lgsk.imgreet.template.dto;

import com.lgsk.imgreet.component.dto.ComponentResponseDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetTemplateComponentDTO {

    private Long id;

    private String title;

    private List<ComponentResponseDTO> componentList;
}
