package com.lgsk.imgreet.category.repository;

import com.lgsk.imgreet.entity.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

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
        assertThat(categoryIdList.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    @DisplayName("카테고리 별 subType 가져오기")
    void getSubType() {
        // given
        Category category_text = Category.category("Text", "가로 텍스트", true);
        Category category_shape1 = Category.category("Shape", "원", true);
        Category category_shape2 = Category.category("Shape", "삼각형", true);
        Category category_shape3 = Category.category("Shape", "사각형", true);

        categoryRepository.save(category_text);
        categoryRepository.save(category_shape1);
        categoryRepository.save(category_shape2);
        categoryRepository.save(category_shape3);

        // when
        List<String> response = categoryRepository.findSubTypeByType("Shape");

        for(String r : response) System.out.println("r = " + r);

        // then
        assertThat(response.size()).isEqualTo(3);
    }

}