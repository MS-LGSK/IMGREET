package com.lgsk.imgreet.categoryDetail.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SubTypeResponseDTO {

    private String subType;
    private Long categoryDetailId;
}
