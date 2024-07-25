package com.lgsk.imgreet.category.controller;


import com.lgsk.imgreet.category.model.CategoryDTO;
import com.lgsk.imgreet.category.model.CategoryIdResponseDTO;
import com.lgsk.imgreet.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public void saveCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.saveCategory(categoryDTO);
    }

    @GetMapping
    public String getAllCategoryId(Model model) {
        List<CategoryIdResponseDTO> response = categoryService.getAllCategoryId();
        model.addAttribute("categoryId", response);
        return "/";         // 초대장 페이지 생성 후, 해당 페이지로 변경 필요
    }
}
