package com.lgsk.imgreet.category.service;


import com.lgsk.imgreet.category.model.CategoryDTO;
import com.lgsk.imgreet.category.model.CategoryIdResponseDTO;
import com.lgsk.imgreet.category.repository.CategoryRepository;
import com.lgsk.imgreet.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void saveCategory(CategoryDTO dto) {
        Category category = Category.builder()
                .type(dto.getType())
                .free(dto.isFree())
                .build();

        categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryIdResponseDTO> getAllCategoryId() {
        List<Category> categories = categoryRepository.findAll();

        List<CategoryIdResponseDTO> response = new ArrayList<>();

        for (Category category : categories) {
            CategoryIdResponseDTO dto = CategoryIdResponseDTO.builder()
                    .id(category.getId())
                    .build();

            response.add(dto);
        }
        return response;
    }

}
