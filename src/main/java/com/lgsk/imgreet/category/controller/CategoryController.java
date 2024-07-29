package com.lgsk.imgreet.category.controller;


import com.lgsk.imgreet.category.dto.CategoryDTO;
import com.lgsk.imgreet.category.dto.CategoryIdResponseDTO;
import com.lgsk.imgreet.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
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
