package com.lgsk.imgreet.categoryDetail.service;


import com.lgsk.imgreet.category.repository.CategoryRepository;
import com.lgsk.imgreet.categoryDetail.model.SubTypeDTO;
import com.lgsk.imgreet.categoryDetail.model.SubTypeResponseDTO;
import com.lgsk.imgreet.categoryDetail.repository.CategoryDetailRepository;
import com.lgsk.imgreet.entity.Category;
import com.lgsk.imgreet.entity.CategoryDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryDetailService {

    private final CategoryDetailRepository categoryDetailRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void saveSubType(Long categoryId, SubTypeDTO dto) {

        Category category = checkCategoryId(categoryId);

        CategoryDetail categoryDetail = CategoryDetail.builder()
                .subType(dto.getSubType())
                .category(category)
                .build();

        categoryDetailRepository.save(categoryDetail);
    }

    public List<SubTypeResponseDTO> getSubType(Long categoryId) {
        checkCategoryId(categoryId);

        List<SubTypeResponseDTO> response = new ArrayList<>();

        List<CategoryDetail> details = categoryDetailRepository.findAllByCategoryId(categoryId);

        for(CategoryDetail detail : details) {
            SubTypeResponseDTO dto = SubTypeResponseDTO.builder()
                    .subType(detail.getSubType())
                    .categoryDetailId(detail.getCategory().getId())
                    .build();

            response.add(dto);
        }
        return response;
    }

    private Category checkCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 카테고리입니다."));
        return category;
    }
}
