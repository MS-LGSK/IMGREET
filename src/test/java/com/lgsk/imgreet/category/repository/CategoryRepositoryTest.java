package com.lgsk.imgreet.category.repository;

import com.lgsk.imgreet.entity.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @Transactional
    @DisplayName("카테고리 생성")
    void save() {
        Category category = Category.category("Text", "가로 텍스트", true);
        categoryRepository.save(category);
    }

    @Test
    @Transactional
    @DisplayName("모든 카테고리 아이디 가져오기")
    void getAllCategoryId() {
        // given
        Category category_text = Category.category("Text", "가로 텍스트", true);
        Category category_shape = Category.category("Shape", "원", true);

        categoryRepository.save(category_text);
        categoryRepository.save(category_shape);

        // when
        List<Long> categoryIdList = categoryRepository.findAllCategoryId();

        // then
        Assertions.assertThat(categoryIdList.size()).isEqualTo(2);
    }
}