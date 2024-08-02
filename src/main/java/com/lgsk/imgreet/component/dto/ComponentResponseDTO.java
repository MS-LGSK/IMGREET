package com.lgsk.imgreet.component.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ComponentResponseDTO {

    private String content;

    private float x;

    private float y;

    private float width;

    private float height;

    private float rotation;
}
