package com.lgsk.imgreet.categoryDetail.service;


import com.lgsk.imgreet.category.repository.CategoryRepository;
import com.lgsk.imgreet.categoryDetail.model.SubTypeDTO;
import com.lgsk.imgreet.categoryDetail.repository.CategoryDetailRepository;
import com.lgsk.imgreet.entity.Category;
import com.lgsk.imgreet.entity.CategoryDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryDetailService {

    private final CategoryDetailRepository categoryDetailRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void saveSubType(Long categoryId, SubTypeDTO dto) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 카테고리입니다."));

        CategoryDetail categoryDetail = CategoryDetail.builder()
                .subType(dto.getSubType())
                .category(category)
                .build();

        categoryDetailRepository.save(categoryDetail);
    }
}
