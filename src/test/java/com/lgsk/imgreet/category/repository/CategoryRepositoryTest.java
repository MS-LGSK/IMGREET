package com.lgsk.imgreet.category.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.lgsk.imgreet.entity.Category;

@SpringBootTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @Transactional
    @DisplayName("카테고리 생성")
    void save() {
        Category category = Category.builder()
                .type("TEXT")
                .free(true)
                .build();

        categoryRepository.save(category);
    }

    @Test
    @Transactional
    @DisplayName("모든 카테고리 아이디 가져오기")
    void getAllCategoryId() {
        // given
        Category category_text = Category.builder()
                .type("TEXT")
                .free(true)
                .build();

        Category category_shape = Category.builder()
                .type("SHAPE")
                .free(true)
                .build();

        categoryRepository.save(category_text);
        categoryRepository.save(category_shape);

        // when
        List<Category> categoryIdList = categoryRepository.findAll();

        // then
        assertThat(categoryIdList.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    @DisplayName("지도 및 댓글 카테고리 생성")
    void saveMapAndComments() {

        // given
        Category categoryMap = Category.builder()
            .type("지도")
            .free(false)
            .build();

        Category categoryComment = Category.builder()
            .type("댓글")
            .free(true)
            .build();

        categoryRepository.save(categoryMap);
        categoryRepository.save(categoryComment);

        // when
        List<Category> categories = categoryRepository.findAll();

        // then
        Assertions.assertThat(categories.stream()
            .filter(detail -> detail.getType().equals("지도"))
            .count()).isEqualTo(1);

        Assertions.assertThat(categories.stream()
            .filter(detail -> detail.getType().equals("댓글"))
            .count()).isEqualTo(1);
    }
}