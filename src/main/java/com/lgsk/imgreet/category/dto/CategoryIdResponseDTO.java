package com.lgsk.imgreet.category.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryIdResponseDTO {

    private Long id;

    private String type;
}
