package com.lgsk.imgreet.categoryDetail.repository;

import com.lgsk.imgreet.entity.CategoryDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryDetailRepository extends JpaRepository<CategoryDetail, Long> {
    List<CategoryDetail> findAllByCategoryId(Long categoryId);
}
