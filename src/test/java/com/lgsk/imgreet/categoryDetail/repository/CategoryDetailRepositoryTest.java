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

import java.util.List;

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
//    @Transactional
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

    @Test
    @DisplayName("서브 타입에 속해있는 카테고리 아이디 가져오기")
    @Transactional
    void getSubType() {
        // given
        Category shape = Category.builder()
                .type("SHAPE")
                .free(true)
                .build();

        Category saveCategory = categoryRepository.save(shape);

        categoryDetailRepository.save(
                CategoryDetail.builder()
                        .subType("원")
                        .category(saveCategory)
                        .build());

        categoryDetailRepository.save(
                CategoryDetail.builder()
                        .subType("삼각형")
                        .category(saveCategory)
                        .build());

        categoryDetailRepository.save(
                CategoryDetail.builder()
                        .subType("사각형")
                        .category(saveCategory)
                        .build());

        // when
        List<CategoryDetail> response = categoryDetailRepository.findAllByCategoryId(saveCategory.getId());

        // then
        assertThat(response.size()).isEqualTo(3);

    }

    @Test
    @DisplayName("카테고리 & 서브타입 데이터 추가")
    void addData() {
        Category category_text = Category.builder()
                .type("TEXT")
                .free(true)
                .build();

        Category category_shape = Category.builder()
                .type("SHAPE")
                .free(true)
                .build();

        Category category_image = Category.builder()
                .type("IMAGE")
                .free(true)
                .build();

        Category saveText = categoryRepository.save(category_text);
        Category saveShape = categoryRepository.save(category_shape);
        Category saveImage = categoryRepository.save(category_image);

        categoryDetailRepository.save(
                CategoryDetail.builder()
                        .subType("가로 텍스트")
                        .category(saveText)
                        .build());

        categoryDetailRepository.save(
                CategoryDetail.builder()
                        .subType("세로 텍스트")
                        .category(saveText)
                        .build());

        categoryDetailRepository.save(
                CategoryDetail.builder()
                        .subType("원")
                        .category(saveShape)
                        .build());

        categoryDetailRepository.save(
                CategoryDetail.builder()
                        .subType("사각형")
                        .category(saveShape)
                        .build());

        categoryDetailRepository.save(
                CategoryDetail.builder()
                        .subType("삼각형")
                        .category(saveShape)
                        .build());
    }

}