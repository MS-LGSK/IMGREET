package com.lgsk.imgreet.categoryDetail.controller;

import com.lgsk.imgreet.categoryDetail.model.SubTypeDTO;
import com.lgsk.imgreet.categoryDetail.model.SubTypeResponseDTO;
import com.lgsk.imgreet.categoryDetail.service.CategoryDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    public String getSubType(@PathVariable("category_id") Long categoryId, Model model) {
        List<SubTypeResponseDTO> response = categoryDetailService.getSubType(categoryId);
        model.addAttribute("subType", response);
        return "/";             // 초대장 생성 페이지 만든 후, 해당 페이지로 변경해주기
    }
}
