package com.lgsk.imgreet.category.repository;

import com.lgsk.imgreet.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c.id FROM Category c")
    List<Long> findAllCategoryId();

    @Query("SELECT c.subType FROM Category c WHERE c.type = :type")
    List<String> findSubTypeByType(String type);
}
