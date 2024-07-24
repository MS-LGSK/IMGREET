package com.lgsk.imgreet.category.service;


import com.lgsk.imgreet.category.model.CategoryDTO;
import com.lgsk.imgreet.category.repository.CategoryRepository;
import com.lgsk.imgreet.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void saveCategory(CategoryDTO categoryDTO) {
        Category category = Category.category(categoryDTO.getType(), categoryDTO.getSubType(), categoryDTO.isFree());
        categoryRepository.save(category);
    }

    public List<Long> getAllCategoryId() {
        return categoryRepository.findAllCategoryId();
    }
}
