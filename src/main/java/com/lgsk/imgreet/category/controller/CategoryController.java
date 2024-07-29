package com.lgsk.imgreet.category.controller;


import com.lgsk.imgreet.category.model.CategoryDTO;
import com.lgsk.imgreet.category.model.CategoryIdResponseDTO;
import com.lgsk.imgreet.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public void saveCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.saveCategory(categoryDTO);
    }

    @GetMapping
    public List<CategoryIdResponseDTO> getAllCategoryId() {
        return categoryService.getAllCategoryId();
    }
}
