package com.lgsk.imgreet.categoryDetail.repository;

import com.lgsk.imgreet.category.repository.CategoryRepository;
import com.lgsk.imgreet.entity.Category;
import com.lgsk.imgreet.entity.CategoryDetail;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryDetailRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryDetailRepository categoryDetailRepository;

    @Test
    @DisplayName("서브타입 저장하기")
    @Transactional
    void saveSubType() {
        // given
        Category text = Category.builder()
                .type("TEXT")
                .free(true)
                .build();

        Category saveCategory = categoryRepository.save(text);

        // when
        CategoryDetail horizontal_text = CategoryDetail.builder()
                .subType("가로 텍스트")
                .category(saveCategory)
                .build();

        CategoryDetail saveCategoryDetail = categoryDetailRepository.save(horizontal_text);

        // then
        assertThat(saveCategoryDetail).isSameAs(horizontal_text);
    }

}