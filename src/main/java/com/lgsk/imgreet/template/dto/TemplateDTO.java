package com.lgsk.imgreet.template.dto;

import com.lgsk.imgreet.component.dto.ComponentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemplateDTO {

    private String title;

    private String imageUrl;

    private List<ComponentDTO> componentList;
}
