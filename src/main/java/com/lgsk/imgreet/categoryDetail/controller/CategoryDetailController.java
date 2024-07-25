package com.lgsk.imgreet.categoryDetail.controller;

import com.lgsk.imgreet.categoryDetail.model.SubTypeDTO;
import com.lgsk.imgreet.categoryDetail.service.CategoryDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class CategoryDetailController {

    private final CategoryDetailService categoryDetailService;

    @PostMapping("/{category_id}")
    public void saveSubType(@PathVariable("category_id") Long categoryId, @RequestBody SubTypeDTO subTypeDTO) {
        categoryDetailService.saveSubType(categoryId, subTypeDTO);
    }
}
