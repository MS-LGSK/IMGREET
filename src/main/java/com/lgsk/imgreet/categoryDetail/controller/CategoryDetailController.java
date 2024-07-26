package com.lgsk.imgreet.categoryDetail.controller;

import com.lgsk.imgreet.categoryDetail.model.SubTypeDTO;
import com.lgsk.imgreet.categoryDetail.model.SubTypeResponseDTO;
import com.lgsk.imgreet.categoryDetail.service.CategoryDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryDetailController {

    private final CategoryDetailService categoryDetailService;

    @PostMapping("/{category_id}")
    public void saveSubType(@PathVariable("category_id") Long categoryId, @RequestBody SubTypeDTO subTypeDTO) {
        categoryDetailService.saveSubType(categoryId, subTypeDTO);
    }

    @GetMapping("/{category_id}")
    @ResponseBody
    public List<SubTypeResponseDTO> getSubType(@PathVariable("category_id") Long categoryId) {
        return categoryDetailService.getSubType(categoryId);
    }
}
