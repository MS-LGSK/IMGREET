package com.lgsk.imgreet.category.repository;

import com.lgsk.imgreet.entity.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
}