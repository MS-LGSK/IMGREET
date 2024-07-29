package com.lgsk.imgreet.category.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryDTO {

    private String type;

    private boolean free;
}
