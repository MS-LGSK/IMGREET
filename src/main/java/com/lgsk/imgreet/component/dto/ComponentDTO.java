package com.lgsk.imgreet.component.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ComponentDTO {

    private String content;

    private float x;

    private float y;

    private float width;

    private float height;

    private float rotation;

    private Long categoryDetailId;
}
