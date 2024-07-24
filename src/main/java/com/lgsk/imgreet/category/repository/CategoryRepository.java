package com.lgsk.imgreet.category.repository;

import com.lgsk.imgreet.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c.id FROM Category c")
    List<Long> findAllCategoryId();

}