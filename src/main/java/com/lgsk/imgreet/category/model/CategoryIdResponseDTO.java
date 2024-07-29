package com.lgsk.imgreet.category.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryIdResponseDTO {

    private Long id;

    private String type;
}
