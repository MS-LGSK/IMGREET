package com.lgsk.imgreet.template.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TemplateResponseDTO {

    private Long id;

    private String title;

    private String imageUrl;
}
