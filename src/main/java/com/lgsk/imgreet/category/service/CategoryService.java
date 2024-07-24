package com.lgsk.imgreet.category.service;


import com.lgsk.imgreet.category.model.CategoryDTO;
import com.lgsk.imgreet.category.repository.CategoryRepository;
import com.lgsk.imgreet.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public void saveCategory(CategoryDTO dto) {
        Category category = Category.builder()
                .type(dto.getType())
                .free(dto.isFree())
                .build();

        categoryRepository.save(category);
    }

    public List<Long> getAllCategoryId() {
        return categoryRepository.findAllCategoryId();
    }

}
